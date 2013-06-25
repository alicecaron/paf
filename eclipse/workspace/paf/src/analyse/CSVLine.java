package analyse;

public class CSVLine {

	private String lemm;
	private float occurence;
	
	public String getLemm() {
		return lemm;
	}
	public void setLemm(String lemm) {
		this.lemm = lemm;
	}
	public float getOccurence() {
		return occurence;
	}
	public void setOccurence(float occurence) {
		this.occurence = occurence;
	}
	public CSVLine(String lemm, float occurence){
		this.lemm=lemm;
		this.occurence=occurence;
	}
	
	
}
