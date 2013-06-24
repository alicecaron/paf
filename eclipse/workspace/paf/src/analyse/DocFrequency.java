package analyse;

public class DocFrequency {
	private int documentFrequency;
	private float tfidf =0;

	public float getTfidf() {
		return tfidf;
	}
	public void setTfidf(float tfidf) {
		this.tfidf = tfidf;
	}
	public int getDocumentFrequency() {
		return documentFrequency;
	}
	public void setDocumentFrequency(int documentFrequency) {
		this.documentFrequency = documentFrequency;
	}
	public DocFrequency(){
		this.documentFrequency=0;
	}
	public void updateDocFrequency(){
		this.documentFrequency+=1;
	}
	public float computeTFIDF(int CORPUS_SIZE,int nbDoc) {
		this.tfidf=(float) (documentFrequency*log2(CORPUS_SIZE/nbDoc)+1);
		return tfidf;
	}
	private double log2(float i) {
		return Math.log(i)/Math.log(2);
	}
}
