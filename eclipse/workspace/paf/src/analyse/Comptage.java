package analyse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tagger.TaggedDocument;

public class Comptage {
	private static Set<Words> corpusWords=new HashSet<Words>();
	private static List<Lemm> corpusLemms = new ArrayList<Lemm>();
	private static Set<MyDocument> corpus=new HashSet<MyDocument>();
	private static int CORPUS_SIZE;
	private static DocumentDifferences docDiff;
	
	public static void main(String[] args) throws IOException{
		String repository="test/";
		getRepositoryList(repository);
		for(MyDocument document:corpus)
			computeDocument(document);
		//Compute document differences
		for(MyDocument doc:corpus)
			doc.setDocumentWords(corpusWords);
		docDiff = new DocumentDifferences(corpus);
		
		//displayStatistics();
		displayDocumentSimilarities();
		System.out.println("Nombre de documents dans le corpus: "+CORPUS_SIZE);
	}
	

	private static void displayDocumentSimilarities() {
		for(MyDocument doc:corpus){
			String filename=doc.getFilename();
			String closeDocuments="";
			Set<TripletDistance> diff = docDiff.getDifferenceMatrix();
			Map<String,Float> distancesToSort = new HashMap<String, Float>();
			for(TripletDistance triplet:diff){
				if(triplet.getDoc1().getFilename().equals(doc.getFilename())){
					distancesToSort.put(triplet.getDoc2().getFilename(),triplet.getDistance());
					closeDocuments+=triplet.getDoc2().getFilename()+" "+triplet.getDistance()+", ";
				}
				else if(triplet.getDoc2().getFilename().equals(doc.getFilename())){
					distancesToSort.put(triplet.getDoc1().getFilename(),triplet.getDistance());
					closeDocuments+=triplet.getDoc1().getFilename()+" "+triplet.getDistance()+", ";
				}
			}
			
			System.out.println(filename+" proche de: "+closeDocuments);
		}
	}


	private static void displayStatistics() {
		for(Words word:corpusWords){
		   feedCorpusLemms(word);
		   System.out.println("====================="+word.getWord()+" "+word.getType()+" "+word.getLemm());
		   System.out.println("++ Corpus frequency: "+word.getCorpusFrequency());
		   System.out.println("++ Documents presence: "+word.getDocFrequency().size());
		   getDocPresenceOfTheWord(word);
		}
		displayLemms();
	}

	private static void displayLemms() {
		System.out.println("========================================================");
		Collections.sort(corpusLemms,new Comparator<Lemm>(){
			public int compare(Lemm l1,Lemm l2){
				return l1.getLemmFrequency()-l2.getLemmFrequency();
			}
		});
		for(Lemm lemm:corpusLemms)
			System.out.println(lemm.getLemm()+" "+lemm.getLemmFrequency());
			System.out.println("========================================================");
			System.out.println("Nombre de mots différents du corpus: "+corpusWords.size());
			System.out.println("Nombre de lemmes différents du corpus: "+corpusLemms.size());
	}

	private static void feedCorpusLemms(Words word) {
		Lemm currentLemm = new Lemm(word.getLemm(),word.getCorpusFrequency());
		boolean in=false;
		for(int i=0;i<corpusLemms.size();i++){
			if(corpusLemms.get(i).getLemm().equals(word.getLemm())){
				corpusLemms.get(i).updateFrequency(word.getCorpusFrequency());
				in=true;
				break;
			}
		}
		if(!in)corpusLemms.add(currentLemm);
	}

	private static void getDocPresenceOfTheWord(Words word) {
		System.out.println("---------------------------------Statistics per document:");
		Set<MyDocument> clef = word.getDocFrequency().keySet();
		Iterator<MyDocument> it = clef.iterator();
		while (it.hasNext()){
			MyDocument key =  it.next();
		   int documentFrequency = word.getDocFrequency().get(key).getDocumentFrequency();
		   System.out.println("**"+key+": "+documentFrequency+" times ");
		   try{System.out.print("("+key.getClasse()+","+key.getMatiere()+","+key.getGroupe()+")");}
		   catch(Exception e){}
		   System.out.println("    TFIDF: "+word.getDocFrequency().get(key).computeTFIDF(CORPUS_SIZE,word.getDocFrequency().size()));
		}
		System.out.println("-----------------------------------------------------------");
	}

	private static void computeDocument(MyDocument document) throws IOException {
		ArrayList<String> content = getFileContent(document.getFile());
		//String[] filename = file.getAbsolutePath().split("/");
		for(String wordInfos : content){
			String[] wordiz=wordInfos.split(" ");
			if(!wordiz[0].equals("") && !(wordiz[0].length()<2) && !(wordiz[1].equals("ZTRM")))
				computeWord(wordiz,document);
		}
	}

	private static void computeWord(String[] wordiz,MyDocument document) {
		String wordStr=wordiz[0];
		for(Words word:corpusWords){
			if(word.getWord().equals(wordStr)){
				word.updateCorpusFrequency(document);
				return;
			}
		}
		corpusWords.add(new Words(wordiz,document));
	}

	private static ArrayList<String> getFileContent(File file) throws IOException { 
		String documentPath=file.getAbsolutePath();
		TaggedDocument taggedDocument = new TaggedDocument(documentPath,corpusWords);
		return taggedDocument.getTaggedDocContent();
	}

	public static void getRepositoryList(String repository) {
		File[] list=null;
		File repertoire =  new File(repository);
		if(repertoire.isDirectory())
			list = repertoire.listFiles();
		for(File file:list)
			corpus.add(new MyDocument(file));
		CORPUS_SIZE=corpus.size();
	}
}
