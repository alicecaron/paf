package analyse;

public class DocFrequency {
	private int documentFrequency;
	private float tfidf;
	
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
		System.out.println(this.documentFrequency);
		this.documentFrequency+=1;
	}
	public float computeTFIDF(int CORPUS_SIZE,int nbDoc) {
		System.out.println("doc frequence "+documentFrequency);
		return tfidf = (float) (documentFrequency*log2((CORPUS_SIZE/nbDoc)+1));
		//System.out.println(tfidf);
		//return tfidf.longValue();
	}
	private double log2(int i) {
		return Math.log(i)/Math.log(2);
	}
}
