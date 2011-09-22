package pos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import util.FileUtil;
import util.XmlUtil;

public class PosGram {

	private HashMap<String, Double> searchResultMap;

	public PosGram() {
		searchResultMap = new HashMap<String, Double>();
		excellent = getSearchResult(excellent_token + "+finance");
		poor = getSearchResult(poor_token + "+finance");

	}

	public ArrayList<String> generatePosGram(String src) {
		ArrayList<String> gram = new ArrayList<String>();

		InputStream modelIn = null;
		try {
			modelIn = new FileInputStream("resources/en-pos-maxent.bin");
			POSModel model = new POSModel(modelIn);
			POSTaggerME tagger = new POSTaggerME(model);

			src = FileUtil.replace(src).toLowerCase();
			src = FileUtil.removeDigit(src);
			src = FileUtil.removeWhiteSpace(src);

			String[] sent = FileUtil.distillEnglishWords(src);

			String tags[] = tagger.tag(sent);
			for (int i = 0; i < tags.length - 2; i++) {

				String current = tags[i];
				String next = tags[i + 1];
				String nnext = tags[i + 2];

				boolean ThirdIsNNorNNS = nnext.equals("NN")
						|| nnext.equals("NNS");

				if (current.equals("JJ")
						&& (next.equals("NN") || next.equals("NNS"))) {
					gram.add(sent[i] + "+" + sent[i + 1]);
					i++;
				} else if ((current.equals("RB") || current.equals("RBR") || current
						.equals("RBS")) && next.equals("JJ") && !ThirdIsNNorNNS) {
					gram.add(sent[i] + "+" + sent[i + 1]);
					i++;
				} else if (current.equals("JJ") && next.equals("JJ")
						&& !ThirdIsNNorNNS) {
					gram.add(sent[i] + "+" + sent[i + 1]);
					i++;
				} else if ((current.equals("NN") || current.equals("NNS"))
						&& next.equals("JJ") && !ThirdIsNNorNNS) {
					gram.add(sent[i] + "+" + sent[i + 1]);
					i++;
				} else if ((current.equals("RB") || current.equals("RBR") || current
						.equals("RBS"))
						&& (next.equals("VB") || next.equals("VBD")
								|| next.equals("VBN") || next.equals("VBG"))) {
					gram.add(sent[i] + "+" + sent[i + 1]);
					i++;
				}
			}

		} catch (Exception e) {
			// Model loading failed, handle the error
			e.printStackTrace();
		}
		return gram;
	}

	public double getSearchResult(String query) {
		double total = 0.01;
		String appid = "13FE26A591A48B75346A42328C60889E3D9878D8";

		String appid2 = "9CF28493DDEA94010B3DDC4E28E62912CC082267";
		String path = "http://api.bing.net/xml.aspx?AppId=" + appid2
				+ "&Query=" + query + "&Sources=Web&Version=2.0&Market=en-us"
				+ "&Adult=Moderate&Options=EnableHighlighting"
				+ "&Web.Count=1&Web.Offset=0";
		try {

			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			InputStream inputStream = conn.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream, "utf8"));
			String inputLine;
			StringBuffer sb = new StringBuffer();
			while (null != (inputLine = in.readLine())) {
				sb.append(inputLine);
				sb.append("\n");
			}
			String tmp = XmlUtil.extract(sb.toString(), "web:Total");
			total += Double.parseDouble(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public double generateScore(String query) {
		if (searchResultMap.containsKey(query)) {
			return searchResultMap.get(query);
		} else {
			double q_e = getSearchResult(query + "+" + excellent_token
					+ "+finance");
			double q_p = getSearchResult(query + "+" + poor_token + "+finance");
			double score = Math.log(q_e * poor / (q_p * excellent))
					/ Math.log(2.0);
			searchResultMap.put(query, score);
			return score;
		}
	}

	public double classify(String article) {
		ArrayList<String> grams = generatePosGram(article);
		double sum = 0.0;
		for (String gram : grams) {
			sum += generateScore(gram) /* + Math.log(alpha) / Math.log(2.0) */;
		}
		return sum;
	}

	public void traverse(File root) {
		try {
			if (!root.isHidden()) {
				if (!root.isDirectory()) {
					String raw = FileUtil.readFile(root.getAbsolutePath());
					String text = XmlUtil.extractContent(raw);
					double sentiment = XmlUtil.extractSentiment(raw);
					double sentiment2 = classify(text);

					DecimalFormat df = new DecimalFormat("###.####");

					System.out.print("File: " + root.getName() + "\tpred: "
							+ df.format(sentiment2) + "\tact: "
							+ df.format(sentiment));

					if ((sentiment > 0 && sentiment2 > 0)
							|| (sentiment < 0 && sentiment2 < 0)) {
						right++;
						total++;
						System.out.println(" - right");
					} else {
						wrong++;
						total++;
						System.out.println(" - wrong");
					}
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

	public void printResults(String path) {
		String results = "----------------------\nTotal: " + total
				+ "\nRight: " + right + "" + "\nWrong: " + wrong
				+ "\n----------------------";
		System.out.println(results);

		FileUtil.writeFile(path, results);
	}

	public void saveQuerySOToFile(String path) {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, Double> e : searchResultMap.entrySet()) {
			sb.append(e.getKey() + "\t" + e.getValue() + "\n");
		}
		FileUtil.writeFile(path, sb.toString());
	}

	public void loadQuerySOFromFile(String path) {

		String text = FileUtil.readFile(path);
		String[] str = text.split("\n");
		for (int i = 0; i < str.length; i++) {
			String[] tmp = str[i].split("\t");
			searchResultMap.put(tmp[0], Double.parseDouble(tmp[1]));
		}
	}

	private int total = 0;
	private int right = 0;
	private int wrong = 0;

	private double excellent;
	private double poor;

	public String excellent_token = "optimistic";
	public String poor_token = "pessimistic";

	public double alpha = 0.9;

	public static void main(String[] args) {
		PosGram pos = new PosGram();

		try {

			//pos.loadQuerySOFromFile("query_so_optimistic_pessimistic");
			pos.traverse(new File("corpus/opfine_corpus/unsupervised_test/"));

			// double sen = pos.classify(XmlUtil.extractContent(FileUtil
			// .readFile("corpus/opfine_corpus/total/neg_178")));
			// System.out.println(sen);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pos.saveQuerySOToFile("query_so_optimistic_pessimistic");
		}
	}

}
