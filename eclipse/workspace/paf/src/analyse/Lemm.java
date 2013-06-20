package analyse;

import java.util.Set;

public class Lemm extends Mots {
	private String lemm;

	public String getLemm() {
		return lemm;
	}
	public void setLemm(String lemm) {
		this.lemm = lemm;
	}

	public Lemm(String[] wordiz,MyDocument document){
		super(wordiz[1],document);
		this.lemm=wordiz[2];
	}
	
}
