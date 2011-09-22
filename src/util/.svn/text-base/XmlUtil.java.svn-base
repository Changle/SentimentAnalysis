package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlUtil {

	public static double extractSentiment(String source) {
		Pattern p = Pattern.compile("<sentiment>(.*?)</sentiment>",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(source);
		if (m.find()) {
			return Double.parseDouble(m.group(1));
		} else
			return 0.0;
	}

	public static String extractTitle(String source) {
		Pattern p = Pattern.compile("<title>(.*?)</title>",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(source);
		if (m.find()) {
			return m.group(1);
		} else
			return null;
	}

	public static String extractContent(String source) {
		Pattern p = Pattern.compile("<content>(.*?)</content>",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher m = p.matcher(source);
		if (m.find()) {
			return m.group(1);
		} else
			return null;
	}

	public static String extract(String source, String tag) {
		Pattern p = Pattern.compile("<" + tag + ">(.*?)</" + tag + ">",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher m = p.matcher(source);
		if (m.find()) {
			return m.group(1);
		} else
			return null;
	}

	public static void main(String args[]) {
		// XmlUtil xu = new XmlUtil();
		// String x =
		// "<date>Fri, 6 May 15:11</date><sentiment>30.63</sentiment><link>http://www.businessweek.com/news/2011-05-06/u-s-stocks-trim-gain-as-europe-concern-offsets-jobs-report.html</link><title>U.S. Stocks Trim Gain as Europe Concern Offsets Jobs Report</title>";

		// System.out.println(xu.extractSentiment(x));
	}
}
