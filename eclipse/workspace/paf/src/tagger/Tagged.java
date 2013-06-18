package tagger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Tagged {
	
	private ArrayList<TaggedWord> mots = new ArrayList<TaggedWord>();
	private static final String pathToLIA = "lia_tagg";
	
	public Tagged (String file){
		BufferedReader br;
		FileReader fr;
		String ligne;
		
		try {
			System.out.println(System.getProperty("user.dir"));
		String[] cmd = {"/bin/sh", "-c"," export LIA_TAGG=\""+System.getProperty("user.dir") +"/lia_tagg\" " +
				"&& export LIA_TAGG_LANG=\"french\" && iconv -c -f UTF-8 -t ISO-8859-15 "+file+" | "+pathToLIA+"/script/lia_clean | " +
				pathToLIA+"/script/lia_tagg+lemm | iconv -f ISO-8859-15 -t UTF-8 > "+file+".tagged"};

		Process p =	Runtime.getRuntime().exec(cmd);

        p.waitFor();
			
			fr = new FileReader(file+".tagged");
			br = new BufferedReader(fr);
			while((ligne = br.readLine()) !=null)
				mots.add(new TaggedWord(ligne));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		Tagged taggedText = new Tagged("test/physique_chimie_143729.txt");
		for(TaggedWord w : taggedText.getWords()){
			if(w.getType().equals("VINF")){
				System.out.println (w.getWord());
				System.out.println (w.getType());
				System.out.println (w.getLemm());
			}
		}	
		
	}
	private ArrayList<TaggedWord> getWords() {
		return mots;
	}	
}
