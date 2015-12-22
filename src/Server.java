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
	
	
	@Override
	public void run() {
		try {
			while (this.runningFlag){
				Socket client = this.server.accept();
				this.clientList.add(client);
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
	
	public void handleClient(Socket client){
		new Thread(new Runnable(){
			public void run() {
				
			}
		}).start();
	}
}
