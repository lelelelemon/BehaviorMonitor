package webIE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import base.PMHibernateImpl;
import bean.OnlineText;

/**
 * 标题:利用htmlparser提取网页纯文本的例子
 */
public class HTMLParserExtraction {
	public static String testHtml(String str, String className, String charset) {
		String result = null;
		try {
			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			URL l_url = new java.net.URL(str);
			HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url
					.openConnection();
			l_connection.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			l_connection.connect();
			l_urlStream = l_connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(
					l_urlStream, charset));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine;
			}
			String testText = extractText(sTotalString);
			Parser parser = getParser(sTotalString);
			String extractPostBody = FilterSpecifiedClass(parser, className);
			result = getPlainText(parser, extractPostBody);
			// System.out.println(result);
			// extractRegex(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// extract the charset of a url
	public static String extractCharset(String str) {
		String charset = "utf-8";
		try {
			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			URL l_url = new java.net.URL(str);
			HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url
					.openConnection();
			l_connection.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			l_connection.connect();

			l_urlStream = l_connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(
					l_urlStream));
			int count = 0;
			while (count <= 8 && (sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine;
				count++;
			}
			Parser parser = getParser(sTotalString);
			NodeFilter filter = new TagNameFilter("meta");
			NodeList list;
			try {
				list = parser.extractAllNodesThatMatch(filter);
				for (int i = 0; i < list.size(); i++) {
					Node node = (Node) list.elementAt(i);
					String nodeText = node.getText();
					if (nodeText.toLowerCase().indexOf("charset=") != -1) {
						// System.out.println(nodeText);
						if (nodeText.toLowerCase().indexOf("gbk") != -1) {
							return "gbk";
						} else {
							return "utf-8";
						}
					}

				}
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return charset;
	}

	public static void writeToFile(String filename, String content) {
		try {
			File file = new File(filename);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				System.out
						.println("The file doesn't exist and we create a new one");
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

	public static void main(String[] args) throws Exception {
		String url = "http://www.cnblogs.com/zi-xing/p/4456264.html";

		String title = testHtml(url, "postTitle", "utf-8");
		String context = testHtml(url, "postBody", "utf-8");
		Date date1 = new Date();
		OnlineText online1 = new OnlineText();
		online1.setTitle(title);
		online1.setContext(context);
		online1.setStart_time(date1);
		Date date2 = new Date();
		online1.setEnd_time(date2);
		PMHibernateImpl.getInstance().save(online1);
		System.out.println(title);
		// String url1 =
		// "http://zhidao.baidu.com/question/1445390293117368620.html?qbl=relate_question_0&word=htmlparser%B5%C3%B5%BD%CC%D8%B6%A8class%B5%C4%C4%DA%C8%DD";
		// testHtml(url1, "line content", "GBK");
		// String url3 =
		// "http://www.cnblogs.com/loveyakamoz/archive/2011/07/27/2118937.html";
		// testHtml(url3, "postBody", extractCharset(url3));
		// String url4 =
		// "http://blog.csdn.net/snow_angel_wmy/article/details/4626093";
		// testHtml(url4, "article_content", extractCharset(url4));
	}

	public static String FilterSpecifiedClass(Parser parser, String className) {
		Node node = null;
		NodeFilter filter = new HasAttributeFilter("class", className);
		NodeList list;

		try {

			list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				node = (Node) list.elementAt(i);
				// System.out.println("Content is :" + node.toHtml());

			}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return node.toHtml();
	}

	public static Parser getParser(String input) {
		try {
			Parser parser = new Parser();
			parser.setEncoding("GBK");
			parser.setInputHTML(input);
			return parser;
		} catch (ParserException e) {
		}
		return null;
	}

	public static String getPlainText(Parser parser, String str) {
		try {
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

	public static String extractRegex(String str) {
		String result;
		Pattern pattern = Pattern.compile("public class.*\n.*");
		Matcher matcher = pattern.matcher(str);
		// 匹配一次
		matcher.find();
		result = matcher.group();
		// System.out.println("The filter answer is " + matcher.group()); //
		// 结果：343
		return result;
	}
}
