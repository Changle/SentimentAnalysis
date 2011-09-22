package machinelearning;

import java.io.File;
import java.io.IOException;

import libsvm.LibSVM;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.discretize.EqualWidthBinning;
import net.sf.javaml.tools.data.FileHandler;

public class Classifiers {

	/*
	 * Using Java Machine Learning Package to do Naive Bayes Classification to
	 * compare different classification method result
	 */
	public static void NaiveBayesClassifier(String trainPath, String testPath)
			throws IOException {

		Dataset data = FileHandler.loadSparseDataset(new File(trainPath), 0,
				" ", ":");

		EqualWidthBinning eb = new EqualWidthBinning(20);
		System.out.println("Start discretisation");
		eb.build(data);
		Dataset ddata = data.copy();
		eb.filter(ddata);

		boolean useLaplace = true;
		boolean useLogs = true;
		Classifier nbc = new NaiveBayesClassifier(useLaplace, useLogs, false);
		nbc.buildClassifier(data);

		Dataset dataForClassification = FileHandler.loadSparseDataset(new File(
				testPath), 0, " ", ":");

		int correct = 0, wrong = 0;

		for (Instance inst : dataForClassification) {
			eb.filter(inst);
			Object predictedClassValue = nbc.classify(inst);
			Object realClassValue = inst.classValue();
			if (predictedClassValue.equals(realClassValue))
				correct++;
			else {

				wrong++;

			}

		}
		System.out.println("correct " + correct);
		System.out.println("incorrect " + wrong);
		System.out.println("Accuracy: " + (float) correct / (correct + wrong));

	}

	/*
	 * Using Java Machine Learning Package to do KNN Classification to compare
	 * different classification method result
	 */
	public static void KNNClassifier(String trainPath, String testPath)
			throws IOException {

		Dataset data = FileHandler.loadSparseDataset(new File(trainPath), 0,
				" ", ":");

		Classifier knn = new KNearestNeighbors(5);
		knn.buildClassifier(data);

		Dataset dataForClassification = FileHandler.loadSparseDataset(new File(
				testPath), 0, " ", ":");

		int correct = 0, wrong = 0;

		for (Instance inst : dataForClassification) {
			Object predictedClassValue = knn.classify(inst);
			Object realClassValue = inst.classValue();
			if (predictedClassValue.equals(realClassValue))
				correct++;
			else
				wrong++;
		}
		System.out.println("Correct predictions  " + correct);
		System.out.println("Wrong predictions " + wrong);
		System.out.println("Accuracy: " + (float) correct / (correct + wrong));

	}

	/*
	 * Using Java Machine Learning Package to do SVM Classification
	 */
	public static void LibSVMClassifier(String trainPath, String testPath)
			throws IOException {

		/* Load a data set */
		Dataset data = FileHandler.loadSparseDataset(new File(trainPath), 0,
				" ", ":");
		/*
		 * Contruct a LibSVM classifier with default settings.
		 */
		Classifier svm = new LibSVM();
		svm.buildClassifier(data);

		Dataset dataForClassification = FileHandler.loadSparseDataset(new File(
				testPath), 0, " ", ":");

		int correct = 0, wrong = 0;

		for (Instance inst : dataForClassification) {
			Object predictedClassValue = svm.classify(inst);
			Object realClassValue = inst.classValue();
			if (predictedClassValue.equals(realClassValue))
				correct++;
			else
				wrong++;
		}
		System.out.println("Correct predictions  " + correct);
		System.out.println("Wrong predictions " + wrong);
		System.out.println("Accuracy: " + (float) correct / (correct + wrong));

	}

	public static void main(String args[]) throws IOException {

		int size = FeatureGenerator.FEATURE_VECTOR_SIZE;
		long time1 = System.currentTimeMillis();
		Classifiers
				.LibSVMClassifier(
						"/Users/yifei/Develop/opinionmining/SentimentAnalysis/supervised/ig/presence/train_"
								+ size,
						"/Users/yifei/Develop/opinionmining/SentimentAnalysis/supervised/ig/presence/test_"
								+ size);
		System.out.println("time: " + (System.currentTimeMillis() - time1));

	}

}
