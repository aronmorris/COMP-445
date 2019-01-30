package httpc;


import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** 
 * @author aronmorris
 *
 * The HelpHandler class fires whenever the argument "help" is given to httpc.
 * When constructed, the handler acquires the help.xml file and creates an 
 * xpath for references to that file.
 * 
 * On update(), the handler checks that "help" is the command given, then prints
 * the desired help information by querying the xml file with the appropriate
 * parameter. No match means the default text.
 * 
 * This system means that this Handler need never be updated with new commands;
 * they can be added exclusively to help.xml
 */
public class HelpHandler extends Handler {

	private static final String HELP_FILE="help.xml";
	
	private Document helpDoc;
	private XPath xpath;
	
	
	public HelpHandler(String name) {
		super(name);
	
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		
		fac.setNamespaceAware(true);
		
		DocumentBuilder builder;
		
		//establish help document and querying system to it
		try {
			
			builder = fac.newDocumentBuilder();
			
			helpDoc = builder.parse(HELP_FILE);
			
			XPathFactory xpathFac = XPathFactory.newInstance();
			
			xpath = xpathFac.newXPath();
			
		} catch(ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update() {
		
		super.update();
		
		//dont do anything unless it's the right argument
		if (this.isEmpty || !this.args[0].equalsIgnoreCase("HELP")) {
			return;
		}
		
		printHelp(this.args);

	}
	
	private void printHelp(String[] keys) {
		
		try {
			//the key is the node called in the xml document
			//default to help if there is no specific call
			String key = keys.length == 1 ? "help" : keys[1];
			
			System.out.println(parseHelp(key));
		
		} catch(XPathExpressionException e) {
			e.printStackTrace();
		}
		
	}
	
	/** 
	 * @param key The node the class will query for data
	 * @return the string containing the particular helptext
	 * @throws XPathExpressionException
	 */
	private String parseHelp(String key) throws XPathExpressionException{
		
		//insert the node key into the query
		//if none exists, nothing gets printed
		XPathExpression expr = xpath.compile("/root/" + key + "/line/text()");
		
		StringBuilder sb = new StringBuilder();
		
		NodeList nodes = (NodeList) expr.evaluate(helpDoc, XPathConstants.NODESET);
		
		for (int i = 0; i < nodes.getLength(); i++) {
			
			sb.append(nodes.item(i).getNodeValue() + "\n");
			
		}
		
		return sb.toString();
	}

}
