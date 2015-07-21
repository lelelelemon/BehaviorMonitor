package io;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import date.DateUtil;

public class XMLReaderTest {

	public static void main(String args[]) throws ParseException {
		// Map<String, Integer> job_conf = new XMLReaderTest()
		// .readXML("job_configure.xml");
		// // System.out.println(job_conf.size());
		// Map<String, Integer> failure_conf = new XMLReaderTest()
		// .readXML("failure_configure.xml");
		Map<String, String> failure_conf = new XMLReaderTest()
				.readXMLString("history.xml");
		for (String key : failure_conf.keySet()) {
			String value = failure_conf.get(key);
			Date date = DateUtil.parse(value);
			System.out.println(key + " " + value);
		}

	}

	public static Map<String, String> readXMLString(String filename) {
		Map<String, String> property;
		property = new HashMap<String, String>();
		Element element = null;
		File f = new File(filename);
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = null;
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			Document dt = db.parse(f);
			element = dt.getDocumentElement();
			NodeList childNodes = element.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node1 = childNodes.item(i);
				if ("item".equals(node1.getNodeName())) {
					NodeList nodeDetail = node1.getChildNodes();
					String name = "";
					String value;
					for (int j = 0; j < nodeDetail.getLength(); j++) {
						Node detail = nodeDetail.item(j);
						if ("url".equals(detail.getNodeName())) {
							name = detail.getTextContent();
						} else if ("visited_on".equals(detail.getNodeName())) {
							if (!detail.getTextContent().equals(""))
								value = detail.getTextContent();
							else
								value = null;
							property.put(name, value);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return property;
	}

}
