package parser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
//import org.htmlparser.filters.NotFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class SeekingalphaParser extends FinancialNewsParser {
	public String pickTitle() {
		NodeFilter nf = new HasAttributeFilter("id", "page_header");
		String h1 = extractFirstNodeHtml(nf, this.getHtml());
		// System.out.println(h1);
		NodeFilter nf2 = new TagNameFilter("h1");
		return extractFirstNodeText(nf2, h1);
	}

	public String pickText() {
		NodeFilter nf = new HasAttributeFilter("id", "article_body");
		String html = extractFirstNodeHtml(nf, super.getHtml());
		NodeFilter pFilter = new TagNameFilter("p");
		StringBuffer sb = new StringBuffer();
		try {
			NodeList nodes = new Parser(html).extractAllNodesThatMatch(pFilter);
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

	public SeekingalphaParser(String url, String encode) {
		super.setEncode(encode);
		super.readUrl(url);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = "http://seekingalpha.com/article/263799-friday-options-recap?source=yahoo";
		SeekingalphaParser bp = new SeekingalphaParser(url, "utf8");
		// System.out.println(bp.getHtml());
		System.out.println("Title: \n\n" + bp.pickTitle());
		System.out.println("Text: \n\n" + bp.pickText());
	}
}
