package analyse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Mots {
	protected int corpusFrequency;
	protected String type;
	protected Map<MyDocument,DocFrequency> docFrequency = new HashMap<MyDocument,DocFrequency>();
	private boolean filtered;
	
	public boolean getFiltered() {
		return this.filtered;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCorpusFrequency() {
		return corpusFrequency;
	}
	public void setCorpusFrequency(int corpusFrequency) {
		this.corpusFrequency = corpusFrequency;
	}
	public Map<MyDocument, DocFrequency> getDocFrequency() {
		return docFrequency;
	}
	public void setDocFrequency(Map<MyDocument, DocFrequency> docFrequency) {
		this.docFrequency = docFrequency;
	}

	public Mots(String type, MyDocument doc){
		this.type = type;
		this.corpusFrequency=0;
		this.filtered=false;
		updateCorpusFrequency(doc);
	}
	public void updateFrequency(){
		this.corpusFrequency++;
	}
	
	public void updateCorpusFrequency(MyDocument document) {
		this.corpusFrequency+=1;
		updateDocumentFrequency(document);
	}
	private void updateDocumentFrequency(MyDocument document) {
		if(!this.docFrequency.containsKey(document))
			this.docFrequency.put(document, new DocFrequency());
		this.docFrequency.get(document).updateDocFrequency();	
	}
	public void computeTFIDF(Set<MyDocument> corpus){
		int corpusSize=corpus.size();
		for(MyDocument document:corpus){
			if(this.docFrequency.containsKey(document))
				this.docFrequency.get(document).computeTFIDF(corpusSize,this.docFrequency.size(),document.getNbWords());
		}
	}
	public void setFiltered(boolean filtered) {
		this.filtered=filtered;
	}
	public boolean isVerb(){
		return this.getType().startsWith("V");
	}
}
