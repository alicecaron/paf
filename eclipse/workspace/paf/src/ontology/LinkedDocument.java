package ontology;

import java.util.HashSet;

import analyse.MyDocument;
import analyse.Words;

public class LinkedDocument {
	
	HashSet<String> wordsToEnhance;
	MyDocument document;
	String HTMLfile;

	public String getHTMLfile() {
		return HTMLfile;
	}

	public LinkedDocument(MyDocument doc) {
		document = doc;
		wordsToEnhance = new HashSet<String>();
		HTMLfile = doc.getFilename().replace(".txt", ".html");
	}

	public void addWord(String word) {
		wordsToEnhance.add(word);		
	}

}
