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
		boolean b= false;
		for(Words w:document.getDocWords()){
			b=false;
			if(w.getWord().equals(this.word)){
				b=true;
				System.out.println(this.word);
				return;
			}
		}
		System.out.println(this.word+" "+b);
		document.addWord(this);
	}
	
}
