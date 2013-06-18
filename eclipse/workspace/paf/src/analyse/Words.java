package analyse;

import java.util.HashMap;
import java.util.Map;

public class Words {
	private int corpusFrequency;
	private String word;
	private String type;
	private Map<String,DocInfo> docPresence = new HashMap<String,DocInfo>();
	private Map<String,DocFrequency> docFrequency = new HashMap<String,DocFrequency>();

	
	
	public int getCorpusFrequency() {
		return corpusFrequency;
	}
	public void setCorpusFrequency(int corpusFrequency) {
		this.corpusFrequency = corpusFrequency;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Map<String, DocInfo> getDocPresence() {
		return docPresence;
	}
	public void setDocPresence(Map<String, DocInfo> docPresence) {
		this.docPresence = docPresence;
	}

	public Words(String word,String type,String documentPath){
		this.corpusFrequency=1;
		this.word=word;
		this.type=type;
		this.docPresence.put(documentPath, new DocInfo(documentPath));
		updateDocumentFrequency(documentPath);

	}
	public void updateCorpusFrequency() {
		this.corpusFrequency+=1;
	}
	public void updateDocumentFrequency(String documentPath) {
		this.docFrequency.get(documentPath).upgradeDocumentFrequency();
	}
}
