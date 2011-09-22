package parser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class FoxParser extends FinancialNewsParser {
	public String pickTitle() {
		NodeFilter nf = new HasAttributeFilter("id", "article-title");
		return extractFirstNodeText(nf, super.getHtml());
	}

	public String pickText() {
		NodeFilter nf = new HasAttributeFilter("id", "introduction");
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

	public FoxParser(String url, String encode) {
		super.setEncode(encode);
		super.readUrl(url);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = "http://www.foxbusiness.com/markets/2011/04/27/cnooc-1st-quarter-revenue-5-higher-oil-prices/";
		FoxParser bp = new FoxParser(url, "utf8");

		System.out.println("Title: \n\n" + bp.pickTitle());
		System.out.println("Text: \n\n" + bp.pickText());
	}
}
