package httpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class GetHandler extends Handler {

	private HttpURLConnection con;
	private URL url;
	private int responseCode;

	private Map<String, String> addProperties;

	private boolean verbose, properties;
	
	public GetHandler(String name) {
		super(name);

		verbose = false;
		properties = false;

	}
	
	@Override
	public void update() {
		
		//always call first to receive args[] -- see parent class
		super.update();
		
		//only start handling if this is the argument we want
		if (this.isEmpty || !this.args[0].equalsIgnoreCase("GET")) {
			return;
		}

		handleFlags(this.args);

		try {

			sendGETRequest();

		} catch(MalformedURLException | ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		
	}

	private void handleFlags(String[] args) {

		for (String s : args) {

		    switch (s) {

                //VERBOSE OPTION
				case "-v":
					verbose = true;
					break;
				//HEADERS ATTACHED
				case "-h":
					addProperties = HeaderHelper.generateHeaders(args);
					properties = true;
					break;
				default:
					continue;
			}

		}

	}


	private void sendGETRequest() throws MalformedURLException, ProtocolException, IOException {

		url = new URL(this.args[this.args.length - 1]);

		con = (HttpURLConnection) url.openConnection();

		//if user has their own desired properties, add them now to the request
		if (properties) {

			for (String key : addProperties.keySet()) {

				con.setRequestProperty(key, addProperties.get(key));

			}

		}

		con.setRequestMethod("GET");

		con.setRequestProperty("User-Agent", super.USER_AGENT);

		responseCode = con.getResponseCode();

		if (verbose) {

			HeaderHelper.printHTTPHeaders(con);

		}

		/**
		 * This part prints the reply from the server
		 */
		if (responseCode == HttpURLConnection.HTTP_OK) {

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String input;

			StringBuffer response = new StringBuffer();

			while ((input = in.readLine()) != null) {

				response.append(input + "\n");
			}
			in.close();
			System.out.println(response.toString());

		}

		else {

			System.out.println("Error " + con.getResponseCode());

		}

	}



}
