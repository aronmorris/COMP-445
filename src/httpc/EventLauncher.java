package httpc;

public interface EventLauncher {

	public void register(EventListener el);
	
	public void deregister(EventListener el);
	
	public void launchEvent();
	
	public Object getUpdate(EventListener el);
	
}
