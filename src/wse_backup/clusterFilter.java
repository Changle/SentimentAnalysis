package wse_backup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

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

public class clusterFilter {

	public boolean filter(String text, String outputpath) {
		try {

			NodeFilter t1 = new HasAttributeFilter("class", "bd");
			NodeList nodes = new Parser(text).extractAllNodesThatMatch(t1);
			Node textNode = (Node) nodes.elementAt(1);
			String tableText = textNode.toHtml();
			NodeList nodeList = null;
			NodeFilter li = new TagNameFilter("li");
			nodeList = new Parser(tableText).extractAllNodesThatMatch(li);
			for (int i = 0; i <= nodeList.size(); i++) {

				NodeFilter tt = new TagNameFilter("a");
				String linkNode = new Parser(nodeList.elementAt(i).toHtml())
						.extractAllNodesThatMatch(tt).elementAt(0).toHtml();

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
				} else if (link.contains("www.marketwatch.com")) {
					parser = new MarketwatchParser(link, encode);
				} else if (link.contains("seekingalpha.com")) {
					parser = new SeekingalphaParser(link, encode);
				} else if (link.startsWith("/news")) {
					String newLink = "http://finance.yahoo.com" + link;
					parser = new YHFinanceParser(newLink, encode);
				}
				if (parser == null) {
					System.out
							.println("Cannot find corresponding parser, link is:"
									+ link);
					continue;
				}
				title = parser.pickTitle();
				content = parser.pickText();
				System.out.println("File[" + count + "]");
				exportToXml(outputpath + count);
				// exportToPlainText(outputpath + count);
				count++;
			}
			System.out.println("parse ended.");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static int count = 2000;

	public void exportToXml(String outputpath) {
		try {

			StringBuffer sb = new StringBuffer();
			sb.append("<link>" + link + "</link>\n");
			sb.append("<title>" + title + "</title>\n");
			sb.append("<content>" + content + "</content>");

			FileUtil.writeFile(outputpath, sb.toString());

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/*
	public void exportToPlainText(String outputpath) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputpath
					+ "_TEXT"));
			StringBuffer sb = new StringBuffer();
			sb.append(link + "\n\n" + title + "\n" + content);
			bw.write(sb.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

	public static String OUTPUT_PATH = "/Users/yifei/Develop/opinionmining/SentimentAnalysis/clustering/cluster_text_new/";
	/*
	 * public static String typePool[] = { "bonds", "commodities", "currencies",
	 * "earnings", "eco_policy", "ETFs", "international",
	 * "investing_ideas_strategies", "IPOs", "legal", "mergers", "mutual_funds",
	 * "options", "pe_hf", "stocks", "upgrades_and_downgrades", "venture" };
	 */public static String typePool[] = { "bonds", "commodities",
			"currencies", "stocks", "options" };

	public static void main(String args[]) {
		try {
			for (String type : typePool) {
				System.out.println();
				count = 1;
				for (int i = 1; i < 2; i++) {
					System.out.println("-----------------\nBegin to parse: "
							+ type + i + "\n-----------------");
					BufferedReader br = new BufferedReader(new FileReader(
							"clustering/cluster/" + type + i + ".html"));
					String line = null;
					StringBuffer sb = new StringBuffer();
					while (null != (line = br.readLine())) {
						sb.append(line);
					}
					clusterFilter tf = new clusterFilter();
					tf.filter(sb.toString(), OUTPUT_PATH + type + "/");
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private String title;
	private String content;
	private String link;

}
