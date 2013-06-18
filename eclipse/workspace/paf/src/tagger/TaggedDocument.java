package tagger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;

import analyse.Words;

public class TaggedDocument {
	
	private ArrayList<String> taggedDocContent = new ArrayList<String>();
	private static final String pathToLIA = "lia_tagg";
	
	public ArrayList<String> getTaggedDocContent() {
		return taggedDocContent;
	}	
	
	public TaggedDocument (String file, Map<String,Words> corpusWords){
		BufferedReader br;
		FileReader fr;
		String ligne;
		String filename;
		int i=0;
		try {
			filename="tagged/"+i+".tagged";
			i++;
			String[] cmd = {"/bin/sh", "-c"," export LIA_TAGG=\""+System.getProperty("user.dir") +"/lia_tagg\" " +
				"&& export LIA_TAGG_LANG=\"french\" && iconv -c -f UTF-8 -t ISO-8859-15//TRANSLIT "+file+" | "+pathToLIA+"/script/lia_clean | " +
				pathToLIA+"/script/lia_tagg+lemm | iconv -f ISO-8859-15 -t UTF-8 > "+filename};
			
			Process p =	Runtime.getRuntime().exec(cmd);
	        p.waitFor();
	        
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			while((ligne = br.readLine()) !=null)
				this.taggedDocContent.add(ligne);
			br.close();
			fr.close();
			
			File f =new File(filename);
			f.delete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
