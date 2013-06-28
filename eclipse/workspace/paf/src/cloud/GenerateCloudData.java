package cloud;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import analyse.Lemm;

public class GenerateCloudData {

	public GenerateCloudData(HashMap<String,Lemm> corpusLemms) throws IOException{
		String cc="";
		for(Lemm lemm:corpusLemms.values()){
			if(!lemm.getFiltered())
				for(int i=0;i<lemm.getCorpusFrequency();i++)
					cc+=" "+lemm.getLemm();
		}
		File file=new File("cloudTxt/total.txt");
		FileWriter w = new FileWriter(file);
		w.write(cc);
		w.close();		
	}	
}
