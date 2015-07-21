package Operation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

//extract text from website
public class TextExtraction {
	public static void testHtml(String str) {
		try {
			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			java.io.InputStream l_urlStream;
			java.net.URL l_url = new java.net.URL(str);
			java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url
					.openConnection();
			l_connection.connect();
			l_urlStream = l_connection.getInputStream();

			java.io.BufferedReader l_reader = new java.io.BufferedReader(
					new java.io.InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine;
				// System.out.println(sTotalString);
			}
			String testText = extractText(sTotalString);
			writeToFile("test", testText);
			String result = getPlainText(sTotalString);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeToFile(String filename, String content) {
		try {
			File file = new File(filename);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("The file doesn't exist and we create a new one");
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(content);
			bufferWritter.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public static String extractText(String inputHtml) throws Exception {
		StringBuffer text = new StringBuffer();
		Parser parser = Parser.createParser(new String(inputHtml.getBytes(),
				"GBK"), "GBK");
		// 遍历所有的节点
		NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
			public boolean accept(Node node) {
				return true;
			}
		});
		System.out.println(nodes.size()); // 打印节点的数量
		for (int i = 0; i < nodes.size(); i++) {
			Node nodet = nodes.elementAt(i);
			text.append(new String(nodet.toPlainTextString().getBytes("GBK"))
					+ "");
		}
		return text.toString();
	}

	public static void test5(String resource) throws Exception {
		Parser myParser = new Parser(resource);
		myParser.setEncoding("GBK");
		String filterStr = "table";
		NodeFilter filter = new TagNameFilter(filterStr);
		NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
		TableTag tabletag = (TableTag) nodeList.elementAt(11);

	}

	public static void main(String[] args) throws Exception {
		// test5("http://www.google.com");
		String url = "http://www.cnblogs.com";
		testHtml(url);
	}

	public static String getPlainText(String str) {
		try {
			Parser parser = new Parser();
			parser.setInputHTML(str);

			StringBean sb = new StringBean();
			// 设置不需要得到页面所包含的链接信息
			sb.setLinks(false);
			// 设置将不间断空格由正规空格所替代
			sb.setReplaceNonBreakingSpaces(true);
			// 设置将一序列空格由一个单一空格所代替
			sb.setCollapse(true);
			parser.visitAllNodesWith(sb);
			str = sb.getStrings();
		} catch (ParserException e) {
		}

		return str;
	}
}
