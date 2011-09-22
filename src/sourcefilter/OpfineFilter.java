package sourcefilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import parser.BloombergParser;
import parser.BusinessweekParser;
import parser.FinancialNewsParser;
import parser.FoxParser;
import parser.MarketwatchParser;
import parser.ReutersParser;
import parser.SeekingalphaParser;
import parser.WSJParser;
import parser.YHFinanceParser;
import parser.YHNewsParser;
import util.FileUtil;

public class OpfineFilter {

	public boolean filter(String text) {
		try {

			NodeFilter t1 = new HasAttributeFilter("class", "sortable");
			NodeList nodes = new Parser(text).extractAllNodesThatMatch(t1);
			Node textNode = (Node) nodes.elementAt(0);
			String tableText = textNode.toHtml();

			NodeList nodeList = null;
			NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
			OrFilter lastFilter = new OrFilter();
			lastFilter.setPredicates(new NodeFilter[] { tableFilter });

			nodeList = new Parser(tableText).parse(lastFilter);
			for (int i = 0; i <= nodeList.size(); i++) {
				if (nodeList.elementAt(i) instanceof TableTag) {
					TableTag tag = (TableTag) nodeList.elementAt(i);
					TableRow[] rows = tag.getRows();

					for (int j = 1; j < rows.length; j++) {
						TableRow tr = (TableRow) rows[j];
						TableColumn[] td = tr.getColumns();

						sentiment = td[1].getChild(0).toPlainTextString();
						double s = Double.parseDouble(sentiment);
						if (greaterthan25 && ((s > -3.0) && (s < 17.8))) {
							continue;
						}
						NodeFilter tt = new TagNameFilter("a");
						String linkNode = new Parser(td[2].toHtml())
								.extractAllNodesThatMatch(tt).elementAt(0)
								.toHtml();
						Pattern p = Pattern.compile("href=\"([^<>\"]*)\"",
								Pattern.CASE_INSENSITIVE);
						Matcher m = p.matcher(linkNode);
						if (m.find()) {
							link = m.group(1);
						}
						FinancialNewsParser parser = null;
						String encode = "UTF-8";

						if (link.contains("news.yahoo.com")) {
							parser = new YHNewsParser(link, encode);
						} else if (link.contains("businessweek.com")) {
							parser = new BusinessweekParser(link, encode);
						} else if (link.contains("reuters.com")) {
							parser = new ReutersParser(link, encode);
						} else if (link.contains("finance.yahoo.com")) {
							parser = new YHFinanceParser(link, encode);
						} else if (link.contains("foxbusiness.com")) {
							parser = new FoxParser(link, encode);
						} else if (link.contains("online.wsj.com")) {
							parser = new WSJParser(link, encode);
						} else if (link.contains("bloomberg.com")) {
							parser = new BloombergParser(link, encode);
						} else if (link.contains("marketwatch.com")) {
							parser = new MarketwatchParser(link, encode);
						} else if (link.contains("seekingalpha.com")) {
							parser = new SeekingalphaParser(link, encode);
						}
						if (parser == null) {
							System.out
									.println("Cannot find corresponding parser, link is:"
											+ link);
							continue;
						}
						title = parser.pickTitle();
						content = parser.pickText();
						date = td[0].getChild(1).toPlainTextString();
						// sentiment = td[1].getChild(0).toPlainTextString();
						System.out.println("File[" + count + "], " + sentiment
								+ ", Date: " + date);

						exportToXml();
						// exportToPlainText();
						count++;

					}

				}
			}

		} catch (ParserException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void exportToXml() {
		try {

			StringBuffer sb = new StringBuffer();

			String outputPath = OUTPUT_PATH + count + "_" + date;
			sb.append("<date>" + date + "</date>\n");
			sb.append("<sentiment>" + sentiment + "</sentiment>\n");
			sb.append("<link>" + link + "</link>\n");
			sb.append("<title>" + title + "</title>\n");
			sb.append("<content>" + content + "</content>");

			FileUtil.writeFile(outputPath, sb.toString());

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public String OUTPUT_PATH = "/Users/yifei/Develop/opinionmining/SentimentAnalysis/corpus/opfine_corpus/original/";

	public static void main(String args[]) {

		for (int j = 2; j < 12; j++) {
			try {
				for (int i = 1; i < 25; i++) {
					System.out.println("Begin to parse " + i + " html");
					BufferedReader br = new BufferedReader(new FileReader(
							"corpus/opfine_url_pool/may_" + j + "/" + i
									+ ".html"));
					String line = null;
					StringBuffer sb = new StringBuffer();
					while (null != (line = br.readLine())) {
						sb.append(line);
					}
					OpfineFilter tf = new OpfineFilter();
					tf.filter(sb.toString());
				}

			} catch (final Exception e) {
				e.printStackTrace();
			}

		}

	}

	private String date;
	private String sentiment;
	private String title;
	private String content;
	private String link;
	private boolean greaterthan25 = true;
	public static int count = 213;

}
