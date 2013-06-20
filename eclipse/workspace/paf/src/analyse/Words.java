package analyse;


public class Words extends Mots{
	private Lemm lemm;
	private String word;
	
	public Lemm getLemm() {
		return lemm;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public void setLemm(Lemm foundLemm) {
		this.lemm = foundLemm;
	}
	
	public Words(String[] wordiz,MyDocument document){
		super(wordiz[1], document);
		this.word=wordiz[0];
		if(!document.getDocWords().contains(this))
			document.addWord(this);
	}
}
