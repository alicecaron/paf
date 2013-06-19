package analyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.tdb.store.Hash;

public class DocumentDifferences {

	private Set<TripletDistance> differenceMatrix;
	
	
	public Set<TripletDistance> getDifferenceMatrix() {
		return differenceMatrix;
	}

	public void setDifferenceMatrix(Set<TripletDistance> differenceMatrix) {
		this.differenceMatrix = differenceMatrix;
	}

	public DocumentDifferences(Set<MyDocument> corpus) {
		this.differenceMatrix=new HashSet<TripletDistance>();
		ArrayList<MyDocument> otherDoc = new ArrayList<MyDocument>();
		for(MyDocument adoc:corpus)
			otherDoc.add(adoc);
		for(MyDocument currentDoc:corpus){
			otherDoc.remove(currentDoc);
			for(MyDocument doc2:otherDoc){
				computeDistance(currentDoc,doc2);
			}
		}
	}

	private void computeDistance(MyDocument currentDoc, MyDocument doc2) {
		ArrayList<Words> documentWords1=currentDoc.getDocWords();
		Double sumCarree1=currentDoc.getSumCarreeOccurences();
		Double sumCarree2=doc2.getSumCarreeOccurences();
		ArrayList<Words> documentWords2=doc2.getDocWords();
		float distance=0;
		for(Words word1:documentWords1){
			int index;
			Double num;
			Double denom;
			System.out.println(word1+" "+word1.getWord()+" text1: "+word1.getDocFrequency().get("text1.txt")+" texte2: "+word1.getDocFrequency().get("text2.txt"));
			if((index=documentWords2.indexOf(word1))!=-1){
				num=(double) (word1.getDocFrequency().get(currentDoc).getDocumentFrequency()*word1.getDocFrequency().get(doc2).getDocumentFrequency());
				denom=Math.sqrt(sumCarree1*sumCarree2);
				distance+=num/denom;
			}
		}
		TripletDistance triplet = new TripletDistance(currentDoc,doc2,distance);
		differenceMatrix.add(triplet);
	}

}
