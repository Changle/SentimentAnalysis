package machinelearning;

import java.util.ArrayList;

import util.AnalyzerUtil;
import util.FileUtil;

/**
 * Given a folder's path, convert all the files in this folder into a vector
 * 
 * */
public class LibSVMFormatConvertor {

	public void convertByFrequency(String outputPath) {
		FeatureGenerator fg = new FeatureGenerator();
		AnalyzerUtil.initialDocList(CORPUS_PATH);
		ArrayList<String> feature = fg.generateFeatureBaseIG();
		TFIDF tfidf = new TFIDF(feature);
		StringBuffer sb = new StringBuffer();

		for (Doc doc : AnalyzerUtil.docList) {
			StringBuffer line = new StringBuffer();
			ArrayList<Double> vector = tfidf.getNormalizedVector(doc);
			String sentiment = (doc.getSentiment() > 0) ? "1" : "-1";
			line.append(sentiment + " ");
			for (int i = 0; i < vector.size(); i++) {
				if (vector.get(i) > 0.0) {
					line.append(i + ":" + vector.get(i) + " ");
				}
			}
			sb.append(line.toString().trim() + "\n");
		}
		FileUtil.writeFile(outputPath, sb.toString());
	}

	public void convertByPresence(String outputPath) {
		FeatureGenerator fg = new FeatureGenerator();
		AnalyzerUtil.initialDocList(CORPUS_PATH);
		ArrayList<String> feature = fg.generateFeatureBaseIG();
		TFIDF tfidf = new TFIDF(feature);
		StringBuffer sb = new StringBuffer();

		for (Doc doc : AnalyzerUtil.docList) {
			StringBuffer line = new StringBuffer();
			ArrayList<Double> vector = tfidf.getNormalizedVector(doc);
			String sentiment = (doc.getSentiment() > 0) ? "1" : "-1";
			line.append(sentiment + " ");
			for (int i = 0; i < vector.size(); i++) {
				if (vector.get(i) > 0.0) {
					line.append(i + ":1 ");
				}
			}
			sb.append(line.toString().trim() + "\n");
		}
		FileUtil.writeFile(outputPath, sb.toString());
	}

	public static String CORPUS_PATH = "corpus/opfine_corpus/total";

	public static void main(String args[]) {

		LibSVMFormatConvertor vc = new LibSVMFormatConvertor();
		vc.convertByPresence("/Users/yifei/Develop/opinionmining/SentimentAnalysis/supervised/ig/presence/data_"
				+ FeatureGenerator.FEATURE_VECTOR_SIZE);
		// vc.convert2("/Users/yifei/Develop/opinionmining/SentimentAnalysis/svm/bool/data_"
		// + FeatureGenerator.FEATURE_VECTOR_SIZE);
	}
}
