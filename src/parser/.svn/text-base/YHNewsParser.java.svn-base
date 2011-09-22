package parser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class YHNewsParser extends FinancialNewsParser {
	public String pickTitle() {
		NodeFilter nf = new HasAttributeFilter("id", "yn-title");
		return extractFirstNodeText(nf, super.getHtml());
	}

	public String pickText() {
		NodeFilter nf = new HasAttributeFilter("class", "yn-story-content");
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

	public YHNewsParser(String url, String encode) {
		super.setEncode(encode);
		super.readUrl(url);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = 
			"http://news.yahoo.com/s/afp/20110427/wl_uk_afp/britainstocksopen";
		YHNewsParser bp = new YHNewsParser(url, "utf8");

		System.out.println("Title: \n\n" + bp.pickTitle());
		System.out.println("Text: \n\n" + bp.pickText());
	}
}
