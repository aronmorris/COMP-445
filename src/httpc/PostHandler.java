package httpc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class PostHandler extends Handler {

	private Map<String, String> addProperties;

	private boolean fileSelected;
	private boolean verbose;
	private boolean fileData;

	private URL url;
	private HttpURLConnection con;
	private String inlineData;
	private byte[] encodedFile;


	public PostHandler(String name) {
		super(name);

		verbose = false;
		fileData = false;

		inlineData = null;


	}
	
	@Override
	public void update() {
		
		super.update();
		
		//only start handling if this is the argument we want
		if (this.isEmpty || !this.args[0].equalsIgnoreCase("POST")) {
			return;
		}
		
		System.out.println("POST request");


		handleFlags(args);

		try {
			sendPOSTRequest();


			if (verbose) {

				HeaderHelper.printHTTPHeaders(con);

			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String input;

			StringBuffer response = new StringBuffer();

			while ((input = in.readLine()) != null) {

				response.append(input + "\n");
			}
			in.close();

			System.out.println(response.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	private void handleFlags(String[] args) {

		for (int i = 0; i < args.length; i++) {

			switch(args[i]) {

				case "-v":
					verbose = true;
					break;
				case "-h":
					HeaderHelper.generateHeaders(args);
					break;
				case "-d":
					createInlineData(args, i);
					fileSelected = true; //set to true after using it so one can only use the one option of two
					break;
				case "-f":
					associateFileData(args, i);
					fileSelected = true;
					break;
			}
		}

	}

	private void sendPOSTRequest() throws MalformedURLException, IOException {

		url = new URL(this.args[this.args.length - 1]);

		con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("POST");

		con.setRequestProperty("User-Agent", super.USER_AGENT);

		con.setRequestProperty("Content-Type", "application/json");

		con.setDoOutput(true); //sets connection to output - necessary for POST

		DataOutputStream writer = new DataOutputStream((con.getOutputStream()));

		if (inlineData != null) {

			writer.writeBytes(inlineData);

		}

		else if (fileData) {

			writer.writeBytes(encodedFile.toString());

		}

		writer.flush();
		writer.close();

	}

	private void createInlineData(String[] args, int index) {

		if (fileSelected) {
			return;
		}

		int start = -1;
		int end = -1;

		StringBuilder sb = new StringBuilder();
		/*
		//start iterating after the discovery point of the flag, and find out what the start and end indices are
		for (int j = index + 1; j < args.length; j++) {

			if (args[j].contains("'{")) {
				start = j;
			}

			if (args[j].contains("}'")) {
				end = j;
				break; //once we have the terminus of the inline data at }', we can safely exit the loop
			}

		}
		*/
		
		//All of the above unecessary for properly formatted data - commented out
		sb.append(args[index + 1]);

		

		inlineData = sb.toString().replace("'", "");

	}


	private void associateFileData(String[] args, int findex) {

		if (fileSelected || findex == args.length - 1) { //if this is true, the other flag has been used and this one is disallowed || there is no file path
			return;
		}

		String path = args[findex + 1];
		encodedFile = null;

		try {

			encodedFile = Files.readAllBytes(Paths.get(path));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fileData = true;
		}

	}


}
