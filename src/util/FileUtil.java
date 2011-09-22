package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileUtil {

	public static String wildcard1[] = { "!", "\"", "#", "$", "%", "&", "'",
			"(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">",
			"?", "@", "[", "", "]", "^", "_", "`", "{", "|", "}", "~", "…",
			"†", "‡", "‘", "’", "“", "”", "•", "–", "—", "™", " ", "¡", "¢",
			"¦", "§", "¨", "©", "«", "¬", "®", "°", "±", "²", "³", "´", "¶",
			"·", "¸", "¹", "»", "¼", "½", "¾", "¿", "×", "÷"

	};

	public static String wildcard2[] = { "&excl;", "&quot;", "&num;",
			"&dollar;", "&percnt;", "&amp;", "&apos;", "&lpar;", "&rpar;",
			"&ast;", "&plus;", "&comma;", "&hyphen;", "&period;", "&sol;",
			"&colon;", "&semi;", "&lt;", "&equals;", "&gt;", "&quest;",
			"&commat;", "&lsqb;", "&bsol;", "&rsqb;", "&circ;", "&lowbar;",
			"&grave;", "&lcub;", "&verbar;", "&rcub;", "&tilde;", "&hellip;",
			"&dagger;", "&Dagger;", "&lsquo;", "&rsquo;", "&ldquo;", "&rdquo;",
			"&bull;", "&ndash;", "&mdash;", "&trade;", "&nbsp;", "&iexcl;",
			"&cent;", "&brvbar;", "&sect;", "&uml;", "&copy;", "&laquo;",
			"&not;", "&reg;", "&deg;", "&plusmn;", "&sup2;", "&sup3;",
			"&acute;", "&para;", "&middot;", "&cedil;", "&sup1;", "&raquo;",
			"&frac14;", "&frac12;", "&frac34;", "&iquest;", "&times;",
			"&divide;" };
	public static String wildcard3[] = { "&#33;", "&#34;", "&#35;", "&#36;",
			"&#37;", "&#38;", "&#39;", "&#40;", "&#41;", "&#42", "&#43;",
			"&#44;", "&#45;", "&#46;", "&#47;", "&#58;", "&#59;", "&#60;",
			"&#61;", "&#62;", "&#63;", "&#64;", "&#91;", "&#92;", "&#93;",
			"&#94;", "&#95;", "&#96;", "&#123;", "&#124;", "&#125;", "&#126;",
			"&#133;", "&#134;", "&#135;", "&#145;", "&#146;", "&#147;",
			"&#148;", "&#149;", "&#150;", "&#151;", "&#153;", "&#160;",
			"&#161;", "&#162;", "&#166;", "&#167;", "&#168;", "&#169;",
			"&#171;", "&#172;", "&#174;", "&#176;", "&#177;", "&#178;",
			"&#179;", "&#180;", "&#182;", "&#183;", "&#184;", "&#185;",
			"&#187;", "&#188;", "&#189;", "&#190;", "&#191;", "&#215;",
			"&#247;" };

	public static String readFile(String path) {

		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "utf8"));
			String line = null;
			while (null != (line = br.readLine())) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String replace(String text) {
		for (int i = 0; i < wildcard1.length; i++) {
			// text = text.replace(wildcard2[i], wildcard1[i]);
			// text = text.replace(wildcard3[i], wildcard1[i]);
			text = text.replace(wildcard2[i], " ");
			text = text.replace(wildcard3[i], " ");

		}
		return text;
	}

	public static String removeDigit(String text) {
		return text.replaceAll("[0-9]+", "");
	}

	public static String removeWhiteSpace(String text) {
		return text.replaceAll("[ \t\n]+", " ");
	}

	public static String[] distillEnglishWords(String text) {

		String[] str = text.split("[\'\",\\. \t\n;\\(\\)]+");
		ArrayList<String> en = new ArrayList<String>();
		for (String s : str) {
			if (s.matches("[a-zA-Z]+")) {
				en.add(s);
			}
		}

		String[] ret = new String[en.size()];
		return en.toArray(ret);
	}

	public static void writeFile(String filename, String context) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), "utf8"));
			bw.write(context);
			bw.close();
		} catch (Exception e) {
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			File file = new File(
					"/Users/yifei/Develop/opinionmining/SentimentAnalysis/corpus/opfine_corpus/total/");
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isHidden())
					continue;
				// tring path = files[i].getAbsolutePath();
				double random = Math.random();
				files[i].renameTo(new File("corpus/opfine_corpus/total/"
						+ (int) (random * 10000000 % 1000000)));
				// System.out.println(path);
				// String text = readFile(path);
				// text = FileUtil.replace(text);
				// writeFile(
				// files[i].getAbsolutePath().replace("strong_18",
				// "strong_18_refine"), text);
			}
		} catch (Exception e) {

		}

	}
}
