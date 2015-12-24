import java.io.IOException;
import java.util.Scanner;


public class Main {
	static void runClient(){
		
	}
	public static void main(String[] args) {
		if (args.length != 0){
			try {
				Server server = new Server(1026);
				Thread serverThread = new Thread(server);
				serverThread.start();
				System.out.println("Server is running now");
				Scanner keyboard = new Scanner(System.in);
				while (true){
					System.out.print("Enter quit to exit the server: ");
					String command = keyboard.nextLine();
					if (command.equalsIgnoreCase("quit")){
						server.stopServer();
						break;
					}
				}
				keyboard.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		else{
			
		}
	}

}