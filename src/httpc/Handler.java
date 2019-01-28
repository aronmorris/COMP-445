package httpc;

public class Handler implements EventListener {

	private String name;
	private EventLauncher receiver;
	
	public Handler(String name) {
		this.name = name;
	}
	
	@Override
	public void update() {
		String msg = (String) receiver.getUpdate(this);
		
		if (msg == null) {
			return;
		}
		
		else {
			//TODO handler logic for get/post, etc, see assignment documentation
		}
		
	}

	@Override
	public void setEventLauncher(EventLauncher el) {
		this.receiver = el;
	}

	
	
}
