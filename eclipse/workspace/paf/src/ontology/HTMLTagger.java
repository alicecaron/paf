package ontology;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import analyse.MyDocument;

public class HTMLTagger {

	public static void tagHTML(HashMap<MyDocument, LinkedDocument> docs) {
		for (LinkedDocument doc : docs.values()){
			InputStream file = null;
			try {
				file = new FileInputStream("html/"+doc.getHTMLfile());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(file));
			
		//	while(br.read()
			
			
		}
			
			
		
		
	}
	
	

}
