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
	private static Set<Lemm> corpusLemms = new HashSet<Lemm>();
	private static Set<MyDocument> corpus=new HashSet<MyDocument>();
	private static int CORPUS_SIZE;
	private static DocumentDifferences docDiff;
	
	public static void main(String[] args) throws IOException{
		String repository="test/";
		getRepositoryList(repository);
		for(MyDocument document:corpus)
			computeDocument(document);
		
		/*****************************************************
		 * CorpusDoc et corpusLemms et corpusWord complétés
		 *****************************************************/
		
		//tfidfs des mots par document
		for(Words word:corpusWords)
			//System.out.println("Corpus word: "+word+" "+word.getWord()+" "+word.getType());
			word.computeTFIDF(corpus);
		for(Lemm lemm:corpusLemms)
			lemm.computeTFIDF(corpus);
		
		//Document differences
//		for(MyDocument doc:corpus){
//			doc.computeSumPerWord();
//			doc.computeSumPerLemm();
//		}
		docDiff = new DocumentDifferences(corpus);
		
		//displayWordStatistics();
		//displayLemmStatistics();
		displayDocumentSimilarities();
		System.out.println("Nombre de documents dans le corpus: "+CORPUS_SIZE);
	}

	private static void displayDocumentSimilarities() {
		for(MyDocument doc:corpus){
			//String filename=doc.getFilename();
			String closeDocuments="";
			Set<TripletDistance> diff = docDiff.getDifferenceMatrix();
			Map<String,Float> distancesToSort = new HashMap<String, Float>();
			
			for(TripletDistance triplet:diff){
				//System.out.println("Diff: "+triplet.getDistance());
				if(triplet.getDoc1().getFilename().equals(doc.getFilename())){
					distancesToSort.put(triplet.getDoc2().getFilename(),triplet.getDistance());
					closeDocuments+=triplet.getDoc2().getMatiere()+" "+triplet.getDistance()+", ";
				}
				else if(triplet.getDoc2().getFilename().equals(doc.getFilename())){
					distancesToSort.put(triplet.getDoc1().getFilename(),triplet.getDistance());
					closeDocuments+=triplet.getDoc1().getMatiere()+" "+triplet.getDistance()+", ";
				}
			}			
			System.out.println(doc.getMatiere()+" proche de: "+closeDocuments);
		}
	}

	private static void computeDocument(MyDocument document) throws IOException {
		ArrayList<String> content = getFileContent(document.getFile());
		for(String wordInfos : content){
			String[] wordiz=wordInfos.split(" ");
			if(!wordiz[0].equals("") && !(wordiz[0].length()<2) && !(wordiz[1].equals("ZTRM")))
				computeWord(wordiz,document);
		}
	}

	private static void computeWord(String[] wordiz,MyDocument document) {
		String wordStr=wordiz[0];
		boolean found = false;
		Words foundWord=null;
		Lemm foundLemm;
		for(Words word:corpusWords){
			if(word.getWord().equals(wordStr) && word.getType().equals(wordiz[1])){
				found = true;
				foundWord=word;
				word.updateCorpusFrequency(document);
				document.addWord(foundWord);
				break;
			}
		}
		if(!found) {
			foundWord = new Words(wordiz,document);
			corpusWords.add(foundWord);
		}
		
		for(Lemm lemm:corpusLemms){
			if(lemm.getLemm().equals(wordiz[2])){
				foundWord.setLemm(lemm);
				lemm.updateCorpusFrequency(document);
				document.addLemm(lemm);
				return;
			}
		}
		foundLemm = new Lemm(wordiz,document);
		corpusLemms.add(foundLemm);
		foundWord.setLemm(foundLemm);
		
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
	

	/**************************************
	 * Display Word and Lemm statistics
	 **************************************/
	
	
	private static void displayWordStatistics() {
		for(Words word:corpusWords){
		   System.out.println("====================="+word.getWord()+" "+word.getType()+" "+word.getLemm());
		   System.out.println("++ Corpus frequency: "+word.getCorpusFrequency());
		   System.out.println("++ Documents presence: "+word.getDocFrequency().size());
		   getDocPresence(word);
		}
	}
	private static void displayLemmStatistics() {
		for(Lemm lemm:corpusLemms){
			   System.out.println("====================="+lemm.getLemm()+" "+lemm.getType());
			   System.out.println("++ Corpus frequency: "+lemm.getCorpusFrequency());
			   System.out.println("++ Documents presence: "+lemm.getDocFrequency().size());
			   getDocPresence(lemm);
			}		
	}
	private static void getDocPresence(Mots mot) {
		System.out.println("---------------------------------Statistics per document:");
		Set<MyDocument> clef = mot.getDocFrequency().keySet();
		Iterator<MyDocument> it = clef.iterator();
		while (it.hasNext()){
			MyDocument key =  it.next();
		   int documentFrequency = mot.getDocFrequency().get(key).getDocumentFrequency();
		   System.out.println("**"+key.getFilename()+": "+documentFrequency+" times ");
		   try{System.out.print("("+key.getClasse()+","+key.getMatiere()+","+key.getGroupe()+")");}
		   catch(Exception e){}
		   System.out.println("    TFIDF: "+mot.getDocFrequency().get(key).getTfidf());//.getDocFrequency().get(key).computeTFIDF(CORPUS_SIZE,word.getDocFrequency().size()));
		}
		System.out.println("-----------------------------------------------------------");
	}

}
