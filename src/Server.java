import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
	private ServerSocket server;
	private boolean runningFlag;
	private List<Socket> clientList;
	private Hashtable<String,ChatRoom> chatRooms;
	
	Server(int port) {
		try {
			this.server = new ServerSocket(port); 
			this.clientList = new LinkedList<>();
			this.chatRooms = new Hashtable<>();
			this.runningFlag = true;
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void handleClient(Socket client){
		this.clientList.add(client);
		new Thread(new Runnable(){
			public void run() {
				try {
					ObjectInputStream requestFromClient = new ObjectInputStream(client.getInputStream());
					while (true){
						Message request = (Message)requestFromClient.readObject();
						switch (request.getType()){
						case NewTopic:
							createChatRoom(request.getMessage(),client);
							break;
						case ToTopic:
							sendMessage(request.getDestination(),request.getMessage(),request.getSender());
							break;
						case EnterTopic:
							enterChatRoom(request.getDestination(),client);
							break;
						case QuitTopic:
							quitChatRoom(request.getDestination(),client);
							break;
						case GetTopics:
							sendTopics(client);
							break;
						default:
							break;
						}
						Thread.sleep(100); // check every 0.1 second
					}
				} catch (ClassNotFoundException | IOException | InterruptedException e) {
					System.out.println(e);
				}catch(Exception e){
					System.out.println(e);
				}finally{
					clientList.remove(client);
					try { client.close();} catch (IOException e) {}
				}
			}
		}).start();

	}
	private void createChatRoom(String roomName,Socket creator) throws Exception{
		if (this.chatRooms.containsKey(roomName))
			throw new Exception("Duplicate room name");
		ChatRoom room = new ChatRoom(roomName);
		this.chatRooms.put(room.getRoomName(),room);
		room.addUser(creator);
		room.sendMessage("Welcome to " + room.getRoomName() + " Room","SERVER");
	}
	private void sendMessage(String roomName,String content,String sender){
		ChatRoom room = this.chatRooms.get(roomName);
		room.sendMessage(content, sender);
	}
	private void enterChatRoom(String roomName,Socket client){
		ChatRoom room = this.chatRooms.get(roomName);
		room.addUser(client);
	}
	private void quitChatRoom(String roomName,Socket client){
		ChatRoom room = this.chatRooms.get(roomName);
		room.removeUser(client);
		if (room.getNumberOfUsers() == 0)
			this.chatRooms.remove(room.getRoomName());
	}
	private void sendTopics(Socket client) throws IOException{
			ObjectOutputStream responseToClient = new ObjectOutputStream(client.getOutputStream());
			String result = "";
			Enumeration<String> allTopics = this.chatRooms.keys();
			while (allTopics.hasMoreElements()){
				String topic = allTopics.nextElement();
				result += (topic + ";");
			}
			responseToClient.writeObject(new Message(MessageType.SendTopic,result,null,null));
	}
	@Override
	public void run() {
		try {
			while (this.runningFlag){
				Socket client = this.server.accept();
				this.handleClient(client);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void stopServer(){
		try {
			this.runningFlag = false;
			this.server.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
}
