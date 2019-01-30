package httpc;

public class httpc {

	private static Receiver receiver;
	
	public static void main(String[] args) {
		
		initialize();
		
		receiver.sendMessage(args);
		
	}
	
	private static void initialize() {
		
		Receiver inputReceiver = new Receiver();
		
		inputReceiver.register(new PostHandler("PostHandler"));
		inputReceiver.register(new GetHandler("GetHandler"));
		inputReceiver.register(new HelpHandler("HelpHandler"));
		
		receiver = inputReceiver;
		
	}
	
}
