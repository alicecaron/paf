package tagger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;

import analyse.Words;

public class TaggedDocument {
	
	private ArrayList<String> taggedDocContent = new ArrayList<String>();
	private static final String pathToLIA = "lia_tagg";
	
	public ArrayList<String> getTaggedDocContent() {
		return taggedDocContent;
	}	
	
	public TaggedDocument (String file, Set<Words> corpusWords){
		BufferedReader br;
		String ligne;
		
		try {
			String[] cmd = {"/bin/sh", "-c"," export LIA_TAGG=\""+System.getProperty("user.dir") +"/lia_tagg\" " +
				"&& export LIA_TAGG_LANG=\"french\" && iconv -c -f UTF-8 -t ISO-8859-15//TRANSLIT "+file+
				" | "+pathToLIA+"/script/lia_clean | " +
				pathToLIA+"/script/lia_tagg+lemm | iconv -f ISO-8859-15 -t UTF-8 "};
			
			Process p =	Runtime.getRuntime().exec(cmd);
			InputStreamReader isr = new InputStreamReader(p.getInputStream());
	        
			br = new BufferedReader(isr);
			while((ligne = br.readLine()) !=null)
				this.taggedDocContent.add(ligne);
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
