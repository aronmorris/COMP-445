package httpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetHandler extends Handler {

	private HttpURLConnection con;
	private URL url;
	private int responseCode;
	
	public GetHandler(String name) {
		super(name);

	}
	
	@Override
	public void update() {
		
		//always call first to receive args[] -- see parent class
		super.update();
		
		//only start handling if this is the argument we want
		if (this.isEmpty || !this.args[0].equalsIgnoreCase("GET")) {
			return;
		}
		
		try {
			
			url = new URL(this.args[this.args.length - 1]);
			
			con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("GET");
			
			responseCode = con.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				
				//TODO refine all this, clean up into more functions, add flags
				System.out.println(con.getHeaderFields().toString());
				
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
				while(in.readLine() != null) {
					System.out.println(in.readLine());
				}
				in.close();
						
			}
			
		} catch (MalformedURLException | ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) { //This one is in its own clause because it overrides Protocol/MalformedURL, and I want the granularity for debugging
			e.printStackTrace();
		}
		
		System.out.println("GET request");
		//TODO "GET" handling logic
		
	}
	
}
