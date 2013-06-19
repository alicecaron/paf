package analyse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Words {
	private int corpusFrequency;
	private MyDocument doc;
	private String word;
	private String type;
	private String lemm;
	private Map<String,DocFrequency> docFrequency = new HashMap<String,DocFrequency>();

	
	public MyDocument getDoc() {
		return doc;
	}
	public void setDoc(MyDocument doc) {
		this.doc = doc;
	}
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
	public String getLemm() {
		return lemm;
	}
	public void setLemm(String lemm) {
		this.lemm = lemm;
	}
	public Words(String[] wordiz,String documentPath,Set<MyDocument> corpus){
		this.corpusFrequency=0;
		this.word=wordiz[0];
		this.type=wordiz[1];
		this.lemm=wordiz[2];
		this.docFrequency.put(documentPath, new DocFrequency(documentPath));
		linkToDoc(corpus,documentPath);
	}

	private void linkToDoc(Set<MyDocument> corpus,String documentPath) {
		for(MyDocument document:corpus){
			if(document.getFilename().equals(documentPath)){
				this.doc=document;
				return;
			}
				
		}
	}
	public void updateCorpusFrequency(String documentPath) {
		this.corpusFrequency+=1;
		updateDocumentFrequency(documentPath);
	}
	private void updateDocumentFrequency(String documentPath) {
		if(!this.docFrequency.containsKey(documentPath))
			this.docFrequency.put(documentPath, new DocFrequency(documentPath));
		this.docFrequency.get(documentPath).updateDocFrequency();
	}
}
