package parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

public abstract class FinancialNewsParser {

	private String encode = "utf8";
	private String html = null;

	public String getHtml() {
		return html;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	// get html given an url
	public void readUrl(String url) {

		try {
			System.out.println("URL: " + url);
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setDoOutput(true);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), encode));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while (null != (line = in.readLine())) {
				sb.append(line);
				sb.append("\n");
			}
			html = sb.toString();
			// System.out.println(html);
		} catch (final Exception e) {
			// e.printStackTrace();
		}
	}

	public abstract String pickTitle();

	public abstract String pickText();

	// Helper
	public String extractFirstNodeText(NodeFilter nf, String text) {
		try {
			NodeList nodes = new Parser(text).extractAllNodesThatMatch(nf);
			Node textNode = (Node) nodes.elementAt(0);
			return textNode.toPlainTextString();

		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Helper
	public String extractFirstNodeHtml(NodeFilter nf, String text) {
		try {
			NodeList nodes = new Parser(text).extractAllNodesThatMatch(nf);
			Node textNode = (Node) nodes.elementAt(0);
			return textNode.toHtml();

		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
