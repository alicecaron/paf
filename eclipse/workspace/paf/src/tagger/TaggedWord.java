package tagger;

public class TaggedWord {
	private String word;
	private String type;
	private String lemm;
	
	public TaggedWord (String s){
		String[] t = s.split(" ");
		if (t.length==3){
			word = t[0];
			type = t[1];
			lemm = t[2];
		}else System.err.println("mot taggé incorrect");
	}

	public String getWord() {
		return word;
	}

	public String getType() {
		return type;
	}

	public String getLemm() {
		return lemm;
	}
	
	

}
