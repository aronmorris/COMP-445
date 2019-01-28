package httpc;

public class PostHandler extends Handler {

	public PostHandler(String name) {
		super(name);

	}
	
	@Override
	public void update() {
		
		super.update();
		
		if (this.isEmpty || this.args[0].equalsIgnoreCase("GET")) {
			return;
		}
		
		//TODO "POST" handling logic
		
	}

}
