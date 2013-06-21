package analyse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


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
			for(MyDocument doc2:otherDoc)
				computeDistance(currentDoc,doc2);
		}
	}
	
	private void computeDistance(MyDocument currentDoc, MyDocument doc2) {
		float distance=0;
		
		/*********************************************
		 * Similarités par 	MOTS 1:occurence 2:TFIDF
		 * 					LEMM 3:occurence 4:TFIDF
		 *********************************************/
		int comptageChoice=2;

		if(comptageChoice==1 || comptageChoice==2){
			ArrayList<Words> commons=new ArrayList<Words>();
			for(Words word:currentDoc.getDocWords())
				if(doc2.getDocWords().contains(word) && !word.getFiltered())
					commons.add(word);
				
			currentDoc.computeSumPerWord(commons);
			doc2.computeSumPerWord(commons);
			Double sumCarree1;
			Double sumCarree2;
			
			switch(comptageChoice){
			case 1:
				sumCarree1=currentDoc.getSumCarreeOccurences();
				sumCarree2=doc2.getSumCarreeOccurences();
				for(Words word1:commons){
					Double num;
					Double denom;
					num=(double)(word1.getDocFrequency().get(currentDoc).getDocumentFrequency()*word1.getDocFrequency().get(doc2).getDocumentFrequency());
					denom=Math.sqrt(sumCarree1*sumCarree2);
					distance+=num/denom;
				}		
				break;
			case 2:
				sumCarree1=currentDoc.getSumCarreeTFIDF();
				sumCarree2=doc2.getSumCarreeTFIDF();
				for(Words word1:commons){
					Double num;
					Double denom;
					num=(double)(word1.getDocFrequency().get(currentDoc).getTfidf()*word1.getDocFrequency().get(doc2).getTfidf());
					denom=Math.sqrt(sumCarree1*sumCarree2);
					distance+=num/denom;
				}
				break;
			}
		}
		else if(comptageChoice==3 || comptageChoice==4){
			ArrayList<Lemm> commons=new ArrayList<Lemm>();
			for(Lemm lemm:currentDoc.getDocLemms())
				if(doc2.getDocLemms().contains(lemm) && !lemm.getFiltered()){
					commons.add(lemm);
					System.out.println(lemm.getFiltered()+" "+lemm.getLemm()+" "+lemm.getType());
				}
			currentDoc.computeSumPerLemm(commons);
			doc2.computeSumPerLemm(commons);
			Double sumCarree1=currentDoc.getSumCarreeOccurencesLemm();
			Double sumCarree2=doc2.getSumCarreeOccurencesLemm();
			
			switch(comptageChoice){
			case 3:
				sumCarree1=currentDoc.getSumCarreeOccurencesLemm();
				sumCarree2=doc2.getSumCarreeOccurencesLemm();
				for(Lemm lemm:commons){
					Double num;
					Double denom;
					num=(double)(lemm.getDocFrequency().get(currentDoc).getDocumentFrequency()*lemm.getDocFrequency().get(doc2).getDocumentFrequency());
					denom=Math.sqrt(sumCarree1*sumCarree2);
					distance+=num/denom;
				}
				break;
			case 4:
				sumCarree1=currentDoc.getSumCarreeTFIDFLemm();
				sumCarree2=doc2.getSumCarreeTFIDFLemm();
				for(Lemm lemm:commons){
					Double num;
					Double denom;
					num=(double)(lemm.getDocFrequency().get(currentDoc).getTfidf()*lemm.getDocFrequency().get(doc2).getTfidf());
					denom=Math.sqrt(sumCarree1*sumCarree2);
					distance+=num/denom;
				}
				break;
			}
		}
		TripletDistance triplet = new TripletDistance(currentDoc,doc2,distance);
		differenceMatrix.add(triplet);
	}
}
