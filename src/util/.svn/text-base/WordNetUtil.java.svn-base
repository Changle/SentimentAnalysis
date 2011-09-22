package util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class WordNetUtil {

	public static void testDictionary() throws MalformedURLException {

		// String wnhome = System.getenv("WNHOME");
		String wnhome = "/usr/local/WordNet-3.0";

		String path = wnhome + File.separator + "dict";
		URL url = new URL("file", null, path);
		IDictionary dict = new Dictionary(url);
		dict.open();
		// look up first sense of the word "dog"
		IIndexWord idxWord = dict.getIndexWord("dog", POS.NOUN);
		IWordID wordID = idxWord.getWordIDs().get(0);
		IWord word = dict.getWord(wordID);
		System.out.println("Id = " + wordID);
		System.out.println("Lemma = " + word.getLemma());
		System.out.println("Gloss = " + word.getSynset().getGloss());

		getSynonyms(dict);
	}

	public static void getSynonyms(IDictionary dict) {

		IIndexWord idxWord = dict.getIndexWord("pretty", POS.ADJECTIVE);
		IWordID wordID = idxWord.getWordIDs().get(0);
		IWord word = dict.getWord(wordID);
		ISynset synset = word.getSynset();

		List<ISynsetID> hypernyms = synset.getRelatedSynsets(Pointer.HYPERNYM);
		
		List<IWord> words;
		for(ISynsetID sid:hypernyms) {
			words = dict.getSynset(sid).getWords();
				System.out.print(sid + " {");
				for(Iterator<IWord> i = words.iterator(); i.hasNext();){
				System.out.print(i.next().getLemma());
				if(i.hasNext()) System.out.print(", ");
			 }
				System.out.println("}");
		}
		
		
		for (IWord w : synset.getWords()) {
			System.out.println(w.getLemma());
		}
	}

	public static void main(String args[]) throws MalformedURLException {
		testDictionary();

	}

}
