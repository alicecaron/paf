package analyse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Words {
	private int corpusFrequency;
	//private Set<MyDocument> docs;
	private String word;
	private String type;
	private String lemm;
	private Map<MyDocument,DocFrequency> docFrequency = new HashMap<MyDocument,DocFrequency>();

	
	public Map<MyDocument, DocFrequency> getDocFrequency() {
		return docFrequency;
	}
	public void setDocFrequency(Map<MyDocument, DocFrequency> docFrequency) {
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
	public Words(String[] wordiz,MyDocument document){
		this.corpusFrequency=0;
		this.word=wordiz[0];
		this.type=wordiz[1];
		this.lemm=wordiz[2];
		this.docFrequency.put(document, new DocFrequency());
		updateCorpusFrequency(document);
		//linkToDoc(corpus,document);
	}

	/*
	 private void linkToDoc(Set<MyDocument> corpus,String documentPath) {
		for(MyDocument document:corpus){
			if(document.getFilename().equals(documentPath)){
				this.doc=document;
				return;
			}		
		}
	}
	*/
	public void updateCorpusFrequency(MyDocument document) {
		this.corpusFrequency+=1;
		updateDocumentFrequency(document);
	}
	private void updateDocumentFrequency(MyDocument document) {
		if(!this.docFrequency.containsKey(document))
			this.docFrequency.put(document, new DocFrequency());
		this.docFrequency.get(document).updateDocFrequency();
	}
}
