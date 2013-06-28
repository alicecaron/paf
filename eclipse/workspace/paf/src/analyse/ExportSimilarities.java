package analyse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ExportSimilarities {

	private Map<String,ArrayList<CloseToDoc>> docSimil;
	private CSVSimilCreator csvSimilCreator;
	
	public ExportSimilarities(String filename){
		docSimil=new HashMap<String,ArrayList<CloseToDoc>>();
		this.csvSimilCreator = new CSVSimilCreator(filename);
	}
	
	public void addCloseDoc(String doc,String name,float distance){
		if(!docSimil.containsKey(doc))
			docSimil.put(doc, new ArrayList<CloseToDoc>());
		docSimil.get(doc).add(new CloseToDoc(name,distance));
	}
	public void display() throws IOException{		
		for(String doc:docSimil.keySet()){
			sort(doc);
			String line=doc+",";
			for(CloseToDoc close:docSimil.get(doc))
				line+=close.getName()+"*"+close.getDistance()+"%";
			csvSimilCreator.addLine(line);
			csvSimilCreator.makeCSVFile();
		}
	}

	private void sort(String doc) {
		ArrayList<CloseToDoc> list = docSimil.get(doc);
		Collections.sort(list, new Comparator<CloseToDoc>() {
			public int compare(CloseToDoc f1, CloseToDoc f2) {
				float res=f1.getDistance()-f2.getDistance();
				if(res>0)
					return -1;
				else
					return 1;
			}
		});	
	}
}
