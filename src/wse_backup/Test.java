package wse_backup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.TokenStream;

import util.AnalyzerUtil;
import util.FileUtil;

public class Test {

	public static void main(String args[]) {

		String a = "abc&edf";
		Pattern p = Pattern.compile("[1-9.&]", Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL | Pattern.MULTILINE);
		Matcher m = p.matcher(a);
		System.out.println(m.find());

		String sb[] = FileUtil
				.readFile(
						"/Users/yifei/Develop/opinionmining/SentimentAnalysis/svm/tfidf/data_500")
				.split("\n");
		int neg = 0, pos = 0;
		for (String s : sb) {
			double sentiment = Double.parseDouble(s.split(" ")[0]);
			if (sentiment == -1)
				neg++;
			else if (sentiment == 1)
				pos++;
		}
		System.out.println("neg:" + neg + "pos:" + pos);

	}
}
