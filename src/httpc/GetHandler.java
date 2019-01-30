package httpc;

import java.net.HttpURLConnection;

public class GetHandler extends Handler {

	public GetHandler(String name) {
		super(name);

		
		
	}
	
	@Override
	public void update() {
		
		super.update();
		
		if (this.isEmpty || this.args[0].equalsIgnoreCase("POST")) {
			return;
		}
		
		System.out.println("GET request");
		//TODO "GET" handling logic
		
	}
	
}
