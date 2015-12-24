import java.io.*;
import java.net.*;
import java.util.*;

public class Client implements Runnable {
	
	private Socket client;
	private boolean runningFlag;
	private ObjectOutputStream requestToServer;
	private ObjectInputStream responseFromServer;
	private List<String> enteredRoom;
	private String[] chatRooms;
	
	Client(InetAddress address, int port) throws IOException{
		this.client = new Socket(address,port);
		this.runningFlag =  true;
		this.requestToServer = new ObjectOutputStream(this.client.getOutputStream());
		this.responseFromServer = new ObjectInputStream(this.client.getInputStream());
		this.enteredRoom = new LinkedList<String> ();
	}
	public void createNewChatRoom(String roomName) throws Exception{
		this.requestToServer.writeObject(new Message(MessageType.NewTopic,roomName,null,null));
		this.requestToServer.flush();
	}
	public void sendMessage(String roomName,String sender,String content) throws IOException{
		if (this.enteredRoom.contains(roomName) ==  false){
			System.out.println("Not in the room: " + roomName);
			return;
		}
		this.requestToServer.writeObject(new Message(MessageType.ToTopic,content,roomName,sender));
		this.requestToServer.flush();
	}
	public void enterChatRoom(String roomName) throws IOException {
		this.requestToServer.writeObject(new Message(MessageType.EnterTopic,roomName,null,null));
		this.requestToServer.flush();
		if (this.enteredRoom.contains(roomName) == false)
			this.enteredRoom.add(roomName);
	}
	public void exitChatRoom(String roomName) throws IOException{
		this.requestToServer.writeObject(new Message(MessageType.QuitTopic,roomName,null,null));
		this.requestToServer.flush();
		this.enteredRoom.remove(roomName);
	}
	public void getAvailableChatRooms() throws Exception{
		this.requestToServer.writeObject(new Message(MessageType.GetTopics,null,null,null));
		this.requestToServer.flush();
	}
	public void handleResponseFromServer() throws Exception{
		Message response = (Message) this.responseFromServer.readObject();
		switch (response.getType()){
		case normal:
			String sender = response.getSender();
			String destination = response.getDestination();
			String content = response.getMessage();
			System.out.println("In " + destination + " From " + sender);
			System.out.println("----" + content);
			break;
		case SendTopic:
			this.chatRooms = response.getMessage().split(";");
			System.out.println("Available chatrooms:");
			System.out.println(this.chatRooms);
			break;
		default:
			throw new Exception("Server sent something unexpected: " + response.getType());
		}
	}
	public String[] getEnteredChatRooms(){
		return (String[]) this.enteredRoom.toArray();
	}
	public void stopClient(){
		this.runningFlag = false;
		try { this.client.close(); } catch(IOException e){}
	}
	@Override
	public void run() {
		while (this.runningFlag){
			try {
				this.handleResponseFromServer();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
