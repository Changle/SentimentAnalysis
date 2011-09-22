package machinelearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import util.AnalyzerUtil;

public class FeatureGenerator {

	private void initialTotalTermFreqMap() {
		for (Doc doc : AnalyzerUtil.docList) {
			for (int i = 0; i < doc.getContent().size(); i++) {
				String word = doc.getContent().get(i);
				if (!totalTermFreq.containsKey(word)) {
					totalTermFreq.put(word, 1);
				} else {
					totalTermFreq.put(word, totalTermFreq.get(word) + 1);
				}
			}
		}
	}

	public ArrayList<String> generateFeature() {
		initialTotalTermFreqMap();
		List<Entry<String, Integer>> list = AnalyzerUtil
				.convertMapToSortedList(totalTermFreq);
		if (list.size() > FEATURE_VECTOR_SIZE) {
			list = list.subList(0, FEATURE_VECTOR_SIZE);
		}
		ArrayList<String> feature = new ArrayList<String>();
		for (Entry<String, Integer> e : list) {
			feature.add(e.getKey());
		}
		return feature;
	}

	public ArrayList<String> generateFeatureBaseIG() {
		initialTotalTermFreqMap();
		int totalDoc = AnalyzerUtil.docList.size();
		ArrayList<String> feature = new ArrayList<String>();
		Hashtable<String, Integer> presence = new Hashtable<String, Integer>();
		Hashtable<String, Integer> unpresence = new Hashtable<String, Integer>();
		Hashtable<String, Integer> negativeWithTerm = new Hashtable<String, Integer>();
		Hashtable<String, Integer> negativeWithoutTerm = new Hashtable<String, Integer>();
		Hashtable<String, Integer> positiveWithTerm = new Hashtable<String, Integer>();
		Hashtable<String, Integer> positiveWithoutTerm = new Hashtable<String, Integer>();
		Hashtable<String, Double> infoGain = new Hashtable<String, Double>();
		List<String> termList = new ArrayList<String>();

		List<Entry<String, Integer>> list1 = AnalyzerUtil
				.convertMapToSortedList(totalTermFreq);
		int temp = (int) ((double) FEATURE_VECTOR_SIZE * DELTA);
		if (list1.size() > temp) {
			list1 = list1.subList(0, temp);
		}
		for (Entry<String, Integer> e : list1) {
			termList.add(e.getKey());
			String tmp = e.getKey();
			presence.put(tmp, 0);
			unpresence.put(tmp, 0);
			negativeWithTerm.put(tmp, 0);
			negativeWithoutTerm.put(tmp, 0);
			positiveWithTerm.put(tmp, 0);
			positiveWithoutTerm.put(tmp, 0);
		}

		for (Doc doc : AnalyzerUtil.docList) {
			ArrayList<String> terms = doc.getContent();
			int sent = (doc.getSentiment() > 0.0 ? 1 : -1);

			for (int i = 0; i < termList.size(); i++) {
				String term = termList.get(i);
				if (terms.contains(term)) {
					if (presence.contains(term)) {
						presence.put(term, presence.get(term) + 1);
					} else {
						presence.put(term, 1);
					}
					if (sent == 1) {
						if (positiveWithTerm.contains(term)) {
							positiveWithTerm.put(term,
									positiveWithTerm.get(term) + 1);
						} else {
							positiveWithTerm.put(term, 1);
						}
					} else {
						if (negativeWithTerm.contains(term)) {
							negativeWithTerm.put(term,
									negativeWithTerm.get(term) + 1);
						} else {
							negativeWithTerm.put(term, 1);
						}
					}

				} else {
					if (unpresence.contains(term)) {
						unpresence.put(term, unpresence.get(term) + 1);
					} else {
						unpresence.put(term, 1);
					}
					if (sent == 1) {
						if (positiveWithoutTerm.contains(term)) {
							positiveWithoutTerm.put(term,
									positiveWithoutTerm.get(term) + 1);
						} else {
							positiveWithoutTerm.put(term, 1);
						}
					} else {
						if (negativeWithoutTerm.contains(term)) {
							negativeWithoutTerm.put(term,
									negativeWithoutTerm.get(term) + 1);
						} else {
							negativeWithoutTerm.put(term, 1);
						}
					}

				}
			}
		}

		for (int i = 0; i < termList.size(); i++) {
			String term = termList.get(i);
			double probterm = (double) presence.get(term) / totalDoc;
			double probunterm = (double) unpresence.get(term) / totalDoc;
			double probPositiveTerm = (double) positiveWithTerm.get(term)
					/ (presence.get(term) + 1);
			double probPositiveUnterm = (double) positiveWithoutTerm.get(term)
					/ (unpresence.get(term) + 1);
			double probNegativeTerm = (double) negativeWithTerm.get(term)
					/ (presence.get(term) + 1);
			double probNegativeUnterm = (double) negativeWithoutTerm.get(term)
					/ (unpresence.get(term) + 1);

			double igterm = probterm
					* (probPositiveTerm * Math.log(probPositiveTerm)
							/ Math.log(2) + probNegativeTerm
							* Math.log(probNegativeTerm) / Math.log(2));
			igterm += probunterm
					* (probPositiveUnterm * Math.log(probPositiveUnterm)
							/ Math.log(2) + probNegativeUnterm
							* Math.log(probNegativeUnterm) / Math.log(2));
			infoGain.put(term, igterm);

		}

		List<Entry<String, Double>> list = AnalyzerUtil
				.convertDoubleMapToSortedList(infoGain);

		if (list.size() > FEATURE_VECTOR_SIZE) {
			list = list.subList(0, FEATURE_VECTOR_SIZE);
		}
		for (Entry<String, Double> e : list) {
			feature.add(e.getKey());
		}

		return feature;
	}

	public static void main(String args[]) {
		// FeatureGenerator fg = new FeatureGenerator();
		// AnalyzerUtil.initialDocList("./corpus/opfine_corpus/total/");

		// ArrayList<String> s = fg.generateFeatureBaseIG();
	}

	HashMap<String, Integer> totalTermFreq = new HashMap<String, Integer>();
	public final static int FEATURE_VECTOR_SIZE = 2000;
	public final static double DELTA = 1.5;
}
