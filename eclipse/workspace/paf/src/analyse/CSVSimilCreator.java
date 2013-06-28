package analyse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CSVSimilCreator {

	public String filename;
	private Set<String> lines=new HashSet<String>();
	
	public CSVSimilCreator(String filename){
		this.filename=filename;
	}

	public void addLine(String line){
		lines.add(line);
	}
	
	public void makeCSVFile() throws IOException{
		String CSVContent="document,proches";
		for(String line:lines)
			CSVContent+="\n"+line;
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
