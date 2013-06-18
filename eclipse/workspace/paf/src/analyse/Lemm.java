package analyse;

public class Lemm {
	private String lemm;
	private int lemmFrequency;
	
	public String getLemm() {
		return lemm;
	}
	public void setLemm(String lemm) {
		this.lemm = lemm;
	}
	public int getLemmFrequency() {
		return lemmFrequency;
	}
	public void setLemmFrequency(int lemmFrequency) {
		this.lemmFrequency = lemmFrequency;
	}

	public Lemm(String lemm,int freq){
		this.lemm=lemm;
		this.lemmFrequency=freq;
	}
	public void updateFrequency(int occ){
		this.lemmFrequency+=occ;
	}
}
