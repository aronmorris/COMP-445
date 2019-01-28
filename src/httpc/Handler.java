package httpc;

public class Handler implements EventListener {

	private String name;
	private EventLauncher receiver;
	
	protected boolean isEmpty = false;
	protected String[] args;
	
	public Handler(String name) {
		this.name = name;
	}
	
	@Override
	public void update() {
		args = (String[]) receiver.getUpdate(this);
		
		//if httpc called with no arguments, don't do anything
		if (args == null || args.length == 0) {
			this.isEmpty = true;
		}
		
	}

	@Override
	public void setEventLauncher(EventLauncher el) {
		this.receiver = el;
	}

	
	
}
