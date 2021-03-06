package analyse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CSVBloomCreator {

	public String filename;
	private Set<CSVLine> lemms=new HashSet<CSVLine>();

	public CSVBloomCreator(String filename){
		this.filename=filename;
	}

	public void addLine(Mots mot){
		float occ;
		if(mot instanceof Words){
			 Words word=((Words) mot);
			 for(CSVLine line:lemms){
				 if(line.getLemm().equals(word.getLemm().getLemm())){
					 occ=mot.getCorpusFrequency();
					 line.setOccurence(occ);
					 return;
				 }
			 }
			 occ=mot.getCorpusFrequency();
			 lemms.add(new CSVLine(word.getLemm().getLemm(),occ ));
		 }	 
		 else if(mot instanceof Lemm){
			 Lemm lemm=((Lemm) mot);
			 for(CSVLine line:lemms){
				 if(line.getLemm().equals(lemm.getLemm())){
					 occ=mot.getCorpusFrequency();
					 line.setOccurence(occ);
					 return;
				 }
			 }
			 occ=mot.getCorpusFrequency();
			 lemms.add(new CSVLine(lemm.getLemm(),occ ));
		 }
	}
	public void makeCSVFile() throws IOException{
		String CSVContent="mot,occurence";
	
		for(CSVLine line:lemms)
			if(line.getOccurence()>4)
				CSVContent+="\n"+line.getLemm()+","+line.getOccurence();
		
		File file = new File(filename);
		if(!file.exists()){
			file.createNewFile();
		}
		FileWriter out = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(out);
		out.write(CSVContent);
		bw.close();
		out.close();
	}
}
