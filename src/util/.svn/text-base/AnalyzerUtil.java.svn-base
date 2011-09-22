package util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import machinelearning.Doc;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class AnalyzerUtil {

	/**
	 * Given a text, get token stream
	 * */
	public static TokenStream getTokenStream(String text) {
		Analyzer az = new EnglishAnalyzer(Version.LUCENE_31);
		return az.tokenStream("", new StringReader(text));
	}

	/**
	 * Given a text, get a ArrayList contains the tokens in it.
	 * */
	public static ArrayList<String> getTokenList(String text) {

		TokenStream ts = getTokenStream(text);
		ArrayList<String> alist = new ArrayList<String>();
		CharTermAttribute termAttr = ts.getAttribute(CharTermAttribute.class);
		try {
			while (ts.incrementToken()) {
				String term = termAttr.toString();
				Pattern p = Pattern.compile("[1-9]", Pattern.CASE_INSENSITIVE
						| Pattern.DOTALL | Pattern.MULTILINE);
				Matcher m = p.matcher(term);
				if (!m.find()) {
					alist.add(term);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return alist;
	}

	/**
	 * Convert a term frequency HashMap to a sorted list
	 * */
	public static List<Map.Entry<String, Integer>> convertMapToSortedList(
			HashMap<String, Integer> s) {
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
				s.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> e1,
					Map.Entry<String, Integer> e2) {
				Integer i1 = (Integer) e1.getValue();
				Integer i2 = (Integer) e2.getValue();
				return i2.compareTo(i1);
			}
		});
		return list;
	}
	
	
	public static List<Map.Entry<String, Double>> convertDoubleMapToSortedList(
			Hashtable<String, Double> infoGain) {
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(
				infoGain.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> e1,
					Map.Entry<String, Double> e2) {
				Double i1 = (Double) e1.getValue();
				Double i2 = (Double) e2.getValue();
				return i2.compareTo(i1);
			}
		});
		return list;
	}

	public static void traverse(File root) {
		try {
			if (!root.isHidden()) {
				if (!root.isDirectory()) {
					String raw = FileUtil.readFile(root.getAbsolutePath());
					String text = XmlUtil.extractContent(raw);
					double sentiment = XmlUtil.extractSentiment(raw);
					ArrayList<String> alist = AnalyzerUtil.getTokenList(text);
					docList.add(new Doc(alist, sentiment));
				} else {
					File[] files = root.listFiles();
					for (int i = 0; i < files.length; i++) {
						traverse(files[i]);
					}
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void initialDocList(String rootpath) {
		docList.clear();
		traverse(new File(rootpath));
	}

	public static ArrayList<Doc> docList = new ArrayList<Doc>();
}
