package parser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NotFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class MarketwatchParser extends FinancialNewsParser {
	public String pickTitle() {
		NodeFilter nf = new HasAttributeFilter("id", "aboveleft");
		String h1 = extractFirstNodeHtml(nf, this.getHtml());
		// System.out.println(h1);
		NodeFilter nf2 = new TagNameFilter("h1");
		return extractFirstNodeText(nf2, h1);
	}

	public String pickText() {
		NodeFilter nf = new HasAttributeFilter("id", "mainstory");
		String html = extractFirstNodeHtml(nf, super.getHtml());
		NodeFilter pFilter = new TagNameFilter("p");
		NodeFilter noSpanFilter = new NotFilter(new TagNameFilter("span"));
		NodeFilter contextFilter = new AndFilter(pFilter, noSpanFilter);
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

	public MarketwatchParser(String url, String encode) {
		super.setEncode(encode);
		super.readUrl(url);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = "http://us.rd.yahoo.com/finance/external/cbsm/SIG=12mg05h3o/*http%3A//www.marketwatch.com/news/story/pfizer-parexel-fall-sales-reports/story.aspx?guid=%7BBFA21850%2D7592%2D11E0%2DB3F3%2D00212804637C%7D&siteid=yhoof";
		MarketwatchParser bp = new MarketwatchParser(url, "utf8");
		// System.out.println(bp.getHtml());
		System.out.println("Title: \n\n" + bp.pickTitle());
		System.out.println("Text: \n\n" + bp.pickText());
	}
}
