enum MessageType{
	NewTopic,	//create new chat room
	ToTopic,	//send message to chat room
	EnterTopic, //enter the chat room
	QuitTopic,	//exit the chat room
}
public class Message {
	private MessageType type;
	private String message;
	private String sender;
	private String destination;
	Message(MessageType t, String m,String s,String d) throws Exception{
		this.type = t;
		this.message = m;
		this.destination = d;
		this.sender = s;
		if (this.type == MessageType.ToTopic &&( this.destination == null || this.sender == null ))
			throw new Exception("Destination or sender not set");
		
	}
	MessageType getType(){
		return this.type;
	}
	String getMessage(){
		return this.message;
	}
	String getDestination() throws Exception{
		if (this.destination == null) throw new Exception("destination not available");
		return this.destination;
	}
	String getSender() throws Exception{
		if (this.destination == null) throw new Exception("sender not available");
		return this.sender;
	}
}
