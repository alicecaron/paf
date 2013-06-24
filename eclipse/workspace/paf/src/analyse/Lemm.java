package analyse;


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
		for(Lemm l:document.getDocLemms()){
			if(l.getLemm().equals(this.lemm))
				return;
		}
		document.addLemm(this);
	}
	
}
