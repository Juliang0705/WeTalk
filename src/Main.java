import java.io.IOException;
import java.net.InetAddress;
import java.util.*;


public class Main {
	static void runServer(){
		Server server = null;
		Scanner keyboard = null;
		try {
			 server = new Server(1026);
			Thread serverThread = new Thread(server);
			serverThread.start();
			System.out.println("Server is running now");
			keyboard = new Scanner(System.in);
			while (true){
				System.out.print("Enter quit to exit the server: ");
				String command = keyboard.nextLine();
				if (command.equalsIgnoreCase("quit")){
					break;
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}finally{
			server.stopServer();
			keyboard.close();
		}
	}
	static void runClient(){
		Scanner keyboard = new Scanner(System.in);
		Client client = null;
		try {
			client = new Client(InetAddress.getLocalHost(),1026);
			Thread clientThread = new Thread(client);
			System.out.println("Before Thread starts");
			clientThread.start();
			System.out.println("Client is running");
Outer:		while (true){
				try {
					System.out.println("Options:");
					System.out.println("1) Create new chat room\n"
									 + "2) Enter a chat room\n"
									 + "3) Quit a chat room\n"
									 + "4) Get all the chat rooms name\n"
									 + "5) Get all chat rooms currently in\n"
									 + "6) Send message to a chat room\n"
									 + "7) Quit the app");
					System.out.print("Enter command: ");
					int choice = keyboard.nextInt();
					keyboard.nextLine(); // consume new line character
					String roomName = "";
					switch (choice){
					case 1:
						System.out.print("Enter new room name: ");
						roomName = keyboard.nextLine();
						client.createNewChatRoom(roomName);
						break;
					case 2:
						System.out.print("Enter the room name: ");
						roomName = keyboard.nextLine();
						client.enterChatRoom(roomName);
						break;
					case 3:
						System.out.print("Enter the room name: ");
						roomName = keyboard.nextLine();
						client.exitChatRoom(roomName);
						break;
					case 4:
						client.getAvailableChatRooms();
						break;
					case 5:
						String[] allEnteredRooms = client.getEnteredChatRooms();
						for (String s: allEnteredRooms)
							System.out.println(s);
						break;
					case 6:
						System.out.print("Enter the room name: ");
						roomName = keyboard.nextLine();
						System.out.print("Enter your name: ");
						String sender = keyboard.nextLine();
						System.out.print("Enter content: ");
						String content = keyboard.nextLine();
						System.out.println("Roomname is "+ roomName);
						client.sendMessage(roomName, sender, content);
						break;
					case 7:
						break Outer;
					default:
						System.out.println("Wrong choice");
						continue;
					}
				} catch (InputMismatchException e) {
					System.out.println(e);
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		} catch (Exception e){
			System.out.println(e);
		}finally{
			client.stopClient();
			keyboard.close();
		}
	}
	public static void main(String[] args) {
		if (args.length != 0){
			runServer();
		}
		else{
			runClient();
		}
		/*Scanner keyboard = new Scanner(System.in);
		String s = keyboard.nextLine();
		String s2 = keyboard.nextLine();
		System.out.println(s);
		System.out.println(s2);*/
	}
}