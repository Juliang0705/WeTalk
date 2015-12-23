import java.net.Socket;
import java.util.*;
import java.io.*;

public class ChatRoom {
	
	private String roomName;
	private List<Socket> usersList;
	
	ChatRoom(String name){
		this.roomName = name;
		this.usersList = new LinkedList<Socket>();
	}
	void addUser(Socket user){
		this.usersList.add(user);
	}
	void removeUser(Socket user){
		this.usersList.remove(user);
	}
	final String getRoomName(){
		return this.roomName;
	}
	int getNumberOfUsers(){
		return this.usersList.size();
	}
	synchronized void sendMessage(String message,String sender){
		for (Socket user: this.usersList){
			try {
				ObjectOutputStream responseToClient = new ObjectOutputStream(user.getOutputStream());
				responseToClient.writeObject(new Message(MessageType.normal,message,sender,this.roomName));
			} catch (IOException e) {
				this.removeUser(user); // no connection 
			}
		}
	}
	
}
