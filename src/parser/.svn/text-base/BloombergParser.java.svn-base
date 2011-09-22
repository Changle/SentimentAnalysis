package parser;


import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.util.NodeList;

public class BloombergParser extends FinancialNewsParser {

	public String pickTitle() {
		NodeFilter nf = new HasAttributeFilter("id", "primary_content");
		String html = extractFirstNodeHtml(nf, super.getHtml());
		NodeFilter titleFilter = new TagNameFilter("h1");
		return extractFirstNodeText(titleFilter, html);
	}

	public String pickText() {
		NodeFilter nf = new HasAttributeFilter("id", "story_content");
		String html = extractFirstNodeHtml(nf, super.getHtml());
		NodeFilter contextFilter = new TagNameFilter("p");

		StringBuffer sb = new StringBuffer();
		try {
			NodeList nodes = new Parser(html)
					.extractAllNodesThatMatch(contextFilter);
			for (int i = 0; i < nodes.size(); i++) {
				Node textNode = (Node) nodes.elementAt(i);
				sb.append(textNode.toPlainTextString().replace("\n", " ")
						.trim());
				sb.append("\n");
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return sb.toString();

	}

	public BloombergParser(String url, String encode) {
		super.setEncode(encode);
		super.readUrl(url);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String url = "http://www.bloomberg.com/news/2011-04-23/american-southwest-cancel-st-louis-flights-after-tornado-closes-airport.html";
		BloombergParser bp = new BloombergParser(url, "utf8");

		System.out.println("Title: \n\n" + bp.pickTitle());
		System.out.println("Text: \n\n" + bp.pickText());
	}

}
