package httpc;

public class httpc {

	private static Receiver receiver;
	
	public static void main(String[] args) {
		
		initialize();
		
		receiver.sendMessage(args);
		
	}
	
	private static void initialize() {
		
		Receiver inputReceiver = new Receiver();
		
		inputReceiver.register(new PostHandler("post"));
		inputReceiver.register(new GetHandler("get"));
		inputReceiver.register(new HelpHandler("help"));
		
		receiver = inputReceiver;
		
	}
	
}
