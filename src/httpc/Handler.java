package httpc;

import java.net.HttpURLConnection;


/**
 * 
 * @author aronmorris
 *
 * Handler is the parent class of all observers/event handlers for httpc. It implements the
 * fundamental logic behind all the handlers, and checks if the arguments required exist.
 * 
 * All inheritor handlers must call super.update() in their own update method to use the
 * args[] array they inherit, which this superhandler exclusively updates on their behalf.
 */
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
