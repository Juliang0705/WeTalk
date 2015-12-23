
public class Main {
	/*static void runServer(){
		System.out.println("Server is running");
		try (ServerSocket server = new ServerSocket(1025);){
			while (true){
				Socket clientSocket = server.accept();
				
				System.out.println("Received a request");
				DataInputStream clientInput = new DataInputStream(clientSocket.getInputStream());
				System.out.println(clientInput.readUTF());
				System.out.println("Message over");
				String response = "Message is received\n";
				DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
				clientOutput.writeUTF(response);
				clientOutput.flush();
				System.out.println("Do it again");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	static void runClient(){
		Scanner keyboard = new Scanner(System.in);
		while(true){
			System.out.println("Enter a line: ");
			String message = keyboard.nextLine();
			System.out.println("Message created");
			try(Socket client = new Socket(InetAddress.getLocalHost(),1025)){
				DataInputStream clientInput = new DataInputStream(client.getInputStream());
				DataOutputStream clientOutput = new DataOutputStream(client.getOutputStream());
				clientOutput.writeUTF(message);
				clientOutput.flush();
				System.out.println("Message sent");
				System.out.println(clientInput.readUTF());
				System.out.println("Message is over");
			} catch (Exception e){
				System.out.println(e);
			}
		}
	}*/
	public static void main(String[] args) {
		//if (args.length == 0)
		//	runClient();
		//else
		//	runServer();
	}

}