package analyse;

public class DocFrequency {
	private int documentFrequency=0;

	public int getDocumentFrequency() {
		return documentFrequency;
	}
	public void setDocumentFrequency(int documentFrequency) {
		this.documentFrequency = documentFrequency;
	}
	public void upgradeDocumentFrequency(){
		this.documentFrequency+=1;
	}
}
