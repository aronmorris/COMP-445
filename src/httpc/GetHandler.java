package httpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
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

		for (int i = 0; i < args.length; i++) {

			//VERBOSE OPTION
			switch (args[i]) {

				case "-v":
					verbose = true;

				case "-h":
					generateHeaders(args);

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

		responseCode = con.getResponseCode();

		if (verbose) {

			printHTTPHeaders(con);

		}

		if (responseCode == HttpURLConnection.HTTP_OK) {

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

				while(in.readLine() != null) {

					System.out.println(in.readLine());

				}

				in.close();

		}

	}

	//Acquires headers from the program arguments and puts them in a map for later
	private void generateHeaders(String[] args) {

		String key, value;

		addProperties = new HashMap<String, String>();

		//exclude final arg which contains " : " as part of url http://etc.tld
		for (int i = 0; i < args.length - 1; i++) {

			if (args[i].contains(":")) {

				key = args[i].substring(0, args[i].indexOf(':'));

				value = args[i].substring(args[i].indexOf(':') + 1, args[i].length());

				addProperties.put(key, value);

			}

		}

		if (!addProperties.isEmpty()) {

			properties = true;

		}

	}

	//acquires the headers received from the request and prints to console
	private void printHTTPHeaders(HttpURLConnection con) {

		Map<String, List<String>> headers = con.getHeaderFields();

		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {

			System.out.println(entry.getKey() + ": " + entry.getValue());

		}

		System.out.println();

	}

}
