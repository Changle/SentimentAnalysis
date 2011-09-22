package parser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class WSJParser extends FinancialNewsParser {

	public WSJParser(String url, String encode) {
		super.setEncode(encode);
		super.readUrl(url);
	}

	@Override
	public String pickTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pickText() {
		NodeFilter nf = new HasAttributeFilter("id", "article_story_body");
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

	public static void main(String args[]) {
		String url = "http://online.wsj.com/article/SB10001424052748704473104576293460430826794.html?mod=yahoo_hs";
		WSJParser bp = new WSJParser(url, "UTF-8");
	
		System.out.println("Title: \n\n" + bp.pickTitle());
		System.out.println("Text: \n\n" + bp.pickText());
	}

}
