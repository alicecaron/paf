package analyse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DocumentDifferences {

	private Set<TripletDistance> differenceMatrix;
		
	public DocumentDifferences(Set<MyDocument> corpus) {
		ArrayList<MyDocument> otherDoc = new ArrayList<MyDocument>();
		for(MyDocument adoc:corpus)
			otherDoc.add(adoc);
		for(MyDocument currentDoc:corpus){
			for(MyDocument doc2:otherDoc){
				computeDistance(currentDoc,doc2);
			}
			otherDoc.remove(currentDoc);
		}
	}

	private void computeDistance(MyDocument currentDoc, MyDocument doc2) {
		//Map<String,Float> documentWords1=currentDoc.getDocumentWords();
		//Map<String,Float> documentWords2=doc2.getDocumentWords();
		ArrayList<Words> documentWords1=currentDoc.getDocWords();
		Double sumCarree1=currentDoc.getSumCarreeOccurences();
		Double sumCarree2=doc2.getSumCarreeOccurences();
		ArrayList<Words> documentWords2=doc2.getDocWords();
		float distance=0;
		for(Words word1:documentWords1){
			int index;
			Double num;
			Double denom;
			if((index=documentWords2.indexOf(word1))!=-1){
				num=(double) (word1.getDocFrequency().get(currentDoc.getFilename()).getDocumentFrequency()*word1.getDocFrequency().get(doc2.getFilename()).getDocumentFrequency());
				denom=Math.sqrt(sumCarree1*sumCarree1);
				distance+=num/denom;
			}
				
		}
			
		TripletDistance triplet = new TripletDistance(currentDoc,doc2,distance);
		
	}

}
