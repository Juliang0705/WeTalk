import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	
	private Socket client;
	private ObjectOutputStream requestToServer;
	private ObjectInputStream responseFromServer;
	private String[] chatRooms;
	
	Client(InetAddress address, int port) throws IOException{
		this.client = new Socket(address,port);
		this.requestToServer = new ObjectOutputStream(this.client.getOutputStream());
		this.responseFromServer = new ObjectInputStream(this.client.getInputStream());
	}
	public void createNewChatRoom(String roomName) throws Exception{
		this.requestToServer.writeObject(new Message(MessageType.NewTopic,roomName,null,null));
		
	}
	public void getAvailableChatRooms() throws Exception{
		this.requestToServer.writeObject(new Message(MessageType.GetTopics,null,null,null));
	}
	public void handleResponseFromServer() throws Exception{
		Message response = (Message) this.responseFromServer.readObject();
		switch (response.getType()){
		case normal:
			break;
		case SendTopic:
			this.chatRooms = response.getMessage().split(";");
			break;
		default:
			break;
		}
	}
}
