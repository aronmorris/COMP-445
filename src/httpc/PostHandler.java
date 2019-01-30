package httpc;

public class PostHandler extends Handler {

	public PostHandler(String name) {
		super(name);

	}
	
	@Override
	public void update() {
		
		super.update();
		
		//only start handling if this is the argument we want
		if (this.isEmpty || !this.args[0].equalsIgnoreCase("POST")) {
			return;
		}
		
		System.out.println("POST request");
		//TODO "POST" handling logic
		
	}

}
