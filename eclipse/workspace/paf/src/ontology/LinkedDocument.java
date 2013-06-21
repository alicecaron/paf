package ontology;

import java.util.HashSet;

import analyse.MyDocument;
import analyse.Words;

public class LinkedDocument {
	
	HashSet<String> wordsToEnhance;
	MyDocument document;

	public LinkedDocument(MyDocument doc) {
		document = doc;
		wordsToEnhance = new HashSet<String>();
	}

	public void addWord(String word) {
		wordsToEnhance.add(word);		
	}

}
