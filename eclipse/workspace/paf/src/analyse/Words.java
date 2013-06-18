package analyse;

import java.util.HashMap;
import java.util.Map;

public class Words {
	private int corpusFrequency;
	private String word;
	private String type;
//	private Map<String,DocInfo> docPresence = new HashMap<String,DocInfo>();
	private Map<String,DocFrequency> docFrequency = new HashMap<String,DocFrequency>();

	
	public Map<String, DocFrequency> getDocFrequency() {
		return docFrequency;
	}
	public void setDocFrequency(Map<String, DocFrequency> docFrequency) {
		this.docFrequency = docFrequency;
	}
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

/*	public Map<String, DocInfo> getDocPresence() {
		return docPresence;
	}
	public void setDocPresence(Map<String, DocInfo> docPresence) {
		this.docPresence = docPresence;
	}
*/
	
	public Words(String word,String type,String documentPath){
		this.corpusFrequency=0;
		this.word=word;
		this.type=type;
		//this.docPresence.put(documentPath, new DocInfo(documentPath));
		this.docFrequency.put(documentPath, new DocFrequency());
	}
	public void updateCorpusFrequency(String documentPath) {
		this.corpusFrequency+=1;
		updateDocumentFrequency(documentPath);
	}
	private void updateDocumentFrequency(String documentPath) {
		if(!this.docFrequency.containsKey(documentPath))
			this.docFrequency.put(documentPath, new DocFrequency());
		this.docFrequency.get(documentPath).updateDocFrequency();
	}
}
