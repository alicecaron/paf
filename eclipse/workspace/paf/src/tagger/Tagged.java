package tagger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Tagged {
	
	private ArrayList<String> mots = new ArrayList<String>();
	private static final String pathToLIA = "/home/adrien/PAF/lia_tagg";
	
	public Tagged (String file){
		BufferedReader br;
		FileReader fr;
		String ligne;
		
		try {
		String[] cmd = {"/bin/sh",	"-c",
				"iconv -c -f UTF-8 -t ISO-8859-15 "+file+" | "+pathToLIA+"/script/lia_clean | " +
				pathToLIA+"/script/lia_tagg+lemm | iconv -f ISO-8859-15 -t UTF-8 > "+file+".tagged"};
		Process p =	Runtime.getRuntime().exec(cmd);

        p.waitFor();
			
			fr = new FileReader(file+".tagged");
			br = new BufferedReader(fr);
			while((ligne = br.readLine()) !=null)
				mots.add(ligne);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
		

}
