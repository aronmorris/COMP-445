package httpc;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class HelpHandler extends Handler {

	private static final String HELP_FILE="help.xml";
	
	private Document helpDoc;
	private XPath xpath;
	
	
	public HelpHandler(String name) {
		super(name);
	
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		
		fac.setNamespaceAware(true);
		
		DocumentBuilder builder;
		
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
		//TODO prints the help texts from help.json
		
		super.update();
		
		if (this.isEmpty || !this.args[0].equalsIgnoreCase("HELP")) {
			return;
		}
		
		printHelp(this.args);

	}
	
	private void printHelp(String[] keys) {
		
		try {
		
			String key = keys.length == 1 ? "help" : keys[1];
			
			System.out.println(parseHelp(key));
		
		} catch(XPathExpressionException e) {
			e.printStackTrace();
		}
		
	}
	
	private String parseHelp(String key) throws XPathExpressionException{
		
		XPathExpression expr = xpath.compile("/root/" + key + "/line/text()");
		
		StringBuilder sb = new StringBuilder();
		
		NodeList nodes = (NodeList) expr.evaluate(helpDoc, XPathConstants.NODESET);
		
		for (int i = 0; i < nodes.getLength(); i++) {
			
			sb.append(nodes.item(i).getNodeValue() + "\n");
			
		}
		
		return sb.toString();
	}

}
