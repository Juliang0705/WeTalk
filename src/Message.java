enum MessageType{
	//used by client
	NewTopic,	//create new chat room
	ToTopic,	//send message to chat room
	EnterTopic, //enter the chat room
	QuitTopic,	//exit the chat room
	GetTopics,  // get all the existing topics
	//used by server
	normal, // normal Message
	SendTopic // send all the topics
}
public class Message {
	private MessageType type;
	private String message;
	private String sender;
	private String destination;
	public Message(MessageType t, String m,String s,String d){
		this.type = t;
		this.message = m;
		this.destination = d;
		this.sender = s;
	}
	public final MessageType getType(){
		return this.type;
	}
	public final String getMessage() throws Exception{
		if (this.message == null) throw new Exception("destination not available");
		return this.message;
	}
	public final String getDestination() throws Exception{
		if (this.destination == null) throw new Exception("destination not available");
		return this.destination;
	}
	public final String getSender() throws Exception{
		if (this.destination == null) throw new Exception("sender not available");
		return this.sender;
	}
}
