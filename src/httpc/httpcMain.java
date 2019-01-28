package httpc;

public class httpcMain {

	private static Receiver receiver;
	
	public static void main(String[] args) {
		
		initialize();
		
		receiver.sendMessage(args);
		
	}
	
	private static void initialize() {
		
		Receiver inputReceiver = new Receiver();
		
		inputReceiver.register(new PostHandler("PostHandler"));
		
		inputReceiver.register(new GetHandler("GetHandler"));
		
		receiver = inputReceiver;
		
	}
	
}
