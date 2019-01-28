package httpc;

import java.util.ArrayList;
import java.util.List;

public class Receiver implements EventLauncher {

	private List<EventListener> listeners;
	private String message;
	private boolean changed;
	
	private final Object MUTEX = new Object();
	
	
	public Receiver() {
		this.listeners = new ArrayList<EventListener>();
	}
	
	@Override
	public void register(EventListener el) {
		if (el == null) {
			throw new NullPointerException();
		}
		synchronized(MUTEX) {
			if (!listeners.contains(el)) {
				listeners.add(el);
			}
		}
		
	}

	@Override
	public void deregister(EventListener el) {
		synchronized(MUTEX) {
			listeners.remove(el);
		}
		
	}

	@Override
	public void launchEvent() {
		List<EventListener> listenersLocal = null;
		
		synchronized(MUTEX) {
			if (!changed) {
				return;
			}
			listenersLocal = new ArrayList<EventListener>(this.listeners);
			this.changed = false;
		}
		
		for (EventListener el : listenersLocal) {
			
			el.update();
			
		}
		
	}

	@Override
	public Object getUpdate(EventListener el) {
		
		return this.message;
		
	}

	public void sendMessage(String msg) {
		
		this.message = msg;
		
		this.changed = true;
		
		launchEvent();
			
	}
	
}
