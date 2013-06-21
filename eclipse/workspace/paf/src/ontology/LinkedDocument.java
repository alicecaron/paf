package ontology;

import java.util.HashSet;

import analyse.MyDocument;
import analyse.Words;

public class LinkedDocument {
	
	private HashSet<String> wordsToEnhance; // mots du doc appartenant à l'ontologie
	private HashSet<String> wordsToSuggest; // les autres verbes
	
	MyDocument document;

	String HTMLfile;

	public String getHTMLfile() {
		return HTMLfile;
	}

	public LinkedDocument(MyDocument doc) {
		document = doc;
		wordsToEnhance = new HashSet<String>();
		wordsToSuggest = new HashSet<String>();
		HTMLfile = doc.getFilename().replace(".txt", ".html");
	}

	public void addWordToEnhance(String word) {
		wordsToEnhance.add(word);		
	}
	
	public void addWordToSuggest(String word) {
		wordsToSuggest.add(word);		
	}
	
	public MyDocument getDocument() {
		return document;
	}
	
	public HashSet<String> getWordsToEnhance() {
		return wordsToEnhance;
	}

	public HashSet<String> getWordsToSuggest() {
		return wordsToSuggest;
	}

}
