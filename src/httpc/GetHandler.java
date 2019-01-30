package httpc;

import java.net.HttpURLConnection;

public class GetHandler extends Handler {

	public GetHandler(String name) {
		super(name);

		
		
	}
	
	@Override
	public void update() {
		
		super.update();
		
		//only start handling if this is the argument we want
		if (this.isEmpty || !this.args[0].equalsIgnoreCase("GET")) {
			return;
		}
		
		System.out.println("GET request");
		//TODO "GET" handling logic
		
	}
	
}
