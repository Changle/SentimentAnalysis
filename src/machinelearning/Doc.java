package machinelearning;

import java.util.ArrayList;

public class Doc {

	private ArrayList<String> content;
	private double sentiment;

	public Doc(ArrayList<String> c, double s) {
		content = c;
		sentiment = s;
	}

	public ArrayList<String> getContent() {
		return content;
	}

	public void setContent(ArrayList<String> content) {
		this.content = content;
	}

	public double getSentiment() {
		return sentiment;
	}

	public void setSentiment(double sentiment) {
		this.sentiment = sentiment;
	}
}
