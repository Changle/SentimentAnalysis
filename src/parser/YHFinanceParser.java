package parser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class YHFinanceParser extends FinancialNewsParser {

	public String pickTitle() {
		NodeFilter nf = new HasAttributeFilter("id", "y-article-hd");
		String html = extractFirstNodeHtml(nf, super.getHtml());
		NodeFilter titleFilter = new TagNameFilter("h1");
		// NodeFilter subtitleFilter = new TagNameFilter("h2");
		String mainTitle = extractFirstNodeText(titleFilter, html);
		// String subTitle = extractFirstNodeText(subtitleFilter, html);
		// return (subTitle == null) ? mainTitle : (mainTitle + " - " +
		// subTitle);
		return mainTitle;
	}

	public String pickText() {
		NodeFilter nf = new HasAttributeFilter("id", "y-article-bd");
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

	public YHFinanceParser(String url, String encode) {
		super.setEncode(encode);
		super.readUrl(url);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = "http://finance.yahoo.com/news/Rodman-Reports-First-Quarter-bw-259832574.html";
		YHFinanceParser bp = new YHFinanceParser(url, "utf8");

		System.out.println("Title: \n\n" + bp.pickTitle());
		System.out.println("Text: \n\n" + bp.pickText());
	}

}
