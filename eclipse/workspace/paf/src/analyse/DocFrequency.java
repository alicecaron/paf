package analyse;

public class DocFrequency {
	private int documentFrequency;

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
}
