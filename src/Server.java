import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
	private ServerSocket server;
	private boolean runningFlag;
	private List<Socket> clientList;
	
	
	Server(int port) {
		try {
			this.server = new ServerSocket(port); 
			this.clientList = new LinkedList<>();
			this.runningFlag = true;
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void handleClient(Socket client){
		try {
			this.clientList.add(client);
			ObjectInputStream requestFromClient = new ObjectInputStream(client.getInputStream());
			new Thread(new Runnable(){
				public void run() {
					try {
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
							}
							Thread.sleep(500); // check every 0.5 second
						}
					} catch (ClassNotFoundException | IOException | InterruptedException e) {
						System.out.println(e);
					}catch(Exception e){
						System.out.println(e);
					}
				}
			}).start();
		} catch (IOException e) {
			System.out.println(e);
		}

	}
	private void createChatRoom(String roomName,Socket creator){
		
	}
	private void sendMessage(String roomName,String content,String sender){
		
	}
	private void enterChatRoom(String roomName,Socket client){
		
	}
	private void quitChatRoom(String roomName,Socket client){
		
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
