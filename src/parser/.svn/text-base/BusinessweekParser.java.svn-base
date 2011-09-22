package parser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class BusinessweekParser extends FinancialNewsParser {

	@Override
	public String pickTitle() {
		NodeFilter nf = new HasAttributeFilter("id", "story-body");
		String html = extractFirstNodeHtml(nf, super.getHtml());
		NodeFilter titleFilter = new TagNameFilter("h1");
		return extractFirstNodeText(titleFilter, html);
	}

	@Override
	public String pickText() {
		NodeFilter nf = new HasAttributeFilter("id", "story-body");
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

	public BusinessweekParser(String url, String encode) {
		super.setEncode(encode);
		super.readUrl(url);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://www.businessweek.com/news/2011-04-27/dollar-falls-to-16-month-low-versus-euro-before-fed-yen-slides.html";
		BusinessweekParser bp = new BusinessweekParser(url, "utf8");

		System.out.println("Title: \n\n" + bp.pickTitle());
		System.out.println("Text: \n\n" + bp.pickText());

	}

}
