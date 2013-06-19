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
	private static Map<String,Words> corpusWords=new HashMap<String,Words>();
	private static List<Lemm> corpusLemms = new ArrayList<Lemm>();
	private static Set<MyDocument> corpus=new HashSet<MyDocument>();
	private static int CORPUS_SIZE;
	
	public static void main(String[] args) throws IOException{
		String repository="test/";
		getRepositoryList(repository);
		for(MyDocument file:corpus)
			computeDocument(file.getFile());
		//Compute document differences
		for(MyDocument doc:corpus)
			doc.setDocumentWords(corpusWords);
		DocumentDifferences docDiff = new DocumentDifferences(corpus);
		
		displayStatistics();
		System.out.println("Nombre de documents dans le corpus: "+CORPUS_SIZE);
	}
	

	private static void displayStatistics() {
		Set<String> clef = corpusWords.keySet();
		Iterator<String> it = clef.iterator();
		while (it.hasNext()){
		   String key =  it.next();
		   Words word = corpusWords.get(key);
		   feedCorpusLemms(word);
		   System.out.println("====================="+key+" "+word.getType()+" "+word.getLemm());
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
		Set<String> clef = word.getDocFrequency().keySet();
		Iterator<String> it = clef.iterator();
		while (it.hasNext()){
		   String key =  it.next();
		   int documentFrequency = word.getDocFrequency().get(key).getDocumentFrequency();
		   System.out.println("**"+key+": "+documentFrequency+" times ");
		   try{System.out.print("("+word.getDoc().getClasse()+","+word.getDoc().getMatiere()+","+word.getDoc().getGroupe()+")");}
		   catch(Exception e){}
		   System.out.println("    TFIDF: "+word.getDocFrequency().get(key).computeTFIDF(CORPUS_SIZE,word.getDocFrequency().size()));
		}
		System.out.println("-----------------------------------------------------------");
	}

	private static void computeDocument(File file) throws IOException {
		ArrayList<String> content = getFileContent(file);
		String[] filename = file.getAbsolutePath().split("/");
		for(String wordInfos : content){
			String[] wordiz=wordInfos.split(" ");
			if(!wordiz[0].equals("") && !(wordiz[0].length()<2) && !(wordiz[1].equals("ZTRM")))
				computeWord(wordiz,filename[filename.length-1]);
		}
	}

	private static void computeWord(String[] wordiz,String documentPath) {
		Words lastWord;
		String wordStr = wordiz[0];
		if(!corpusWords.containsKey(wordStr)){
				lastWord = new Words(wordiz,documentPath,corpus);
				corpusWords.put(wordStr, lastWord);
		}
		corpusWords.get(wordStr).updateCorpusFrequency(documentPath);
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
