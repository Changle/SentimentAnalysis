package parser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class ReutersParser extends FinancialNewsParser {
	public String pickTitle() {
		NodeFilter nf = new HasAttributeFilter("class",
				"column2 gridPanel grid8");
		String html = extractFirstNodeHtml(nf, super.getHtml());
		NodeFilter titleFilter = new TagNameFilter("h1");
		return extractFirstNodeText(titleFilter, html);
	}

	public String pickText() {
		NodeFilter nf = new HasAttributeFilter("id", "articleText");
		String html = extractFirstNodeHtml(nf, super.getHtml());
		NodeFilter contextFilter = new TagNameFilter("p");
		StringBuffer sb = new StringBuffer();
		try {
			NodeList nodes = new Parser(html)
					.extractAllNodesThatMatch(contextFilter);
			/*
			 * if (nodes.size() == 0) { nodes = new Parser(html)
			 * .extractAllNodesThatMatch(contextFilter2); }
			 */
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

	public ReutersParser(String url, String encode) {
		super.setEncode(encode);
		super.readUrl(url);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = "http://www.reuters.com/article/2011/05/02/markets-canada-dollar-bonds-idUSN0220660120110502?feedType=RSS&feedName=marketsNews&rpc=43";
		ReutersParser bp = new ReutersParser(url, "utf8");

		System.out.println("Title: \n\n" + bp.pickTitle());
		System.out.println("Text: \n\n" + bp.pickText());
	}

}
