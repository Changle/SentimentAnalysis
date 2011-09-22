package machinelearning;

import java.util.ArrayList;
import java.util.HashMap;

import util.AnalyzerUtil;

public class TFIDF {

	public TFIDF(ArrayList<String> f) {
		feature = f;
		initialIDFMap();
	}

	public int calculateTermAppearTimes(String term) {
		int appearTimes = 0;
		for (Doc doc : AnalyzerUtil.docList) {
			for (int i = 0; i < doc.getContent().size(); i++) {
				String word = doc.getContent().get(i);
				if (word.equals(term)) {
					appearTimes++;
				}
			}
		}
		return appearTimes;
	}

	public void initialIDFMap() {
		for (String term : feature) {
			double idf = Math.log((double) (AnalyzerUtil.docList.size())
					/ (double) calculateTermAppearTimes(term) + 0.01);
			idfMap.put(term, idf);
		}
	}

	public double getIDF(String term) {
		return idfMap.get(term);
	}

	public double getTF(String term, ArrayList<String> doc) {
		double freq = 0;
		for (String a : doc) {
			if (term.equals(a))
				freq++;
		}
		return freq;
	}

	public ArrayList<Double> getNormalizedVector(Doc doc) {
		ArrayList<String> content = doc.getContent();
		ArrayList<Double> vector = new ArrayList<Double>();
		// before normalized:
		for (int i = 0; i < feature.size(); i++) {
			String term = feature.get(i);
			vector.add(getTF(term, content) * getIDF(term));
		}
		double sum = 0.0;
		for (double e : vector) {
			sum += e;
		}
		for (int i = 0; i < vector.size(); i++) {
			double normalizedValue = Math.sqrt(vector.get(i) / sum);
			vector.set(i, normalizedValue);
		}
		return vector;
	}

	private HashMap<String, Double> idfMap = new HashMap<String, Double>();
	private ArrayList<String> feature;
}
