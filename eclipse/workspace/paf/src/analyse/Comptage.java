package analyse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tagger.TaggedDocument;

public class Comptage {
	private static Map<String,Words> corpusWords=new HashMap<String,Words>();
	private static Set<File> corpus=new HashSet<File>();
	private static int CORPUS_SIZE;
	
	public static void main(String[] args) throws IOException{
		String repository="test/";
		getRepositoryList(repository);
		for(File file:corpus)
			computeDocument(file);
		displayStatistics();
		System.out.println(CORPUS_SIZE);
	}
	
	private static void displayStatistics() {
		Set<String> clef = corpusWords.keySet();
		Iterator<String> it = clef.iterator();
		while (it.hasNext()){
		   String key =  it.next();
		   Words word = corpusWords.get(key);
		   System.out.println("====================="+key+" "+word.getType()+" "+word.getLemm());
		   System.out.println("++ Corpus frequency: "+word.getCorpusFrequency());
		   System.out.println("++ Documents presence: "+word.getDocFrequency().size());
		   
		  // System.out.println(key+" |||| "+word.getCorpusFrequency()+" times/corpus |||| type : "+word.getType());
		   //System.out.println("Nb docs : "+word.getDocFrequency().size());
		   getDocPresenceOfTheWord(word);
		}
		
	}

	private static void getDocPresenceOfTheWord(Words word) {
		System.out.println("-------------------Statistics per document:");
		Set<String> clef = word.getDocFrequency().keySet();
		Iterator<String> it = clef.iterator();
		while (it.hasNext()){
		   String key =  it.next();
		   int documentFrequency = word.getDocFrequency().get(key).getDocumentFrequency();
		   System.out.println("**"+key+": "+documentFrequency+" times");
		   System.out.println("    TFIDF: "+word.getDocFrequency().get(key).computeTFIDF(CORPUS_SIZE,word.getDocFrequency().size()));

		}
		System.out.println("-------------------------------------------");
	}

	private static void computeDocument(File file) throws IOException {
		ArrayList<String> content = getFileContent(file);
		String[] filename = file.getAbsolutePath().split("/");
//		content=StringEscapeUtils.escapeJava(content);
//		content = content.replaceAll("([ \t\n\r\f.'():]|u2019|,)","%%%");
//		content=StringEscapeUtils.unescapeJava(content);
//		String[] wordsTab=content.split("%%%");

		for(String wordInfos : content){
			String[] wordiz=wordInfos.split(" ");
			if(!wordiz[0].equals("") && !(wordiz[0].length()<2)){
				//System.out.println(wordiz[0]);
				computeWord(wordiz,filename[filename.length-1]);
			}
		}		
	}

	private static void computeWord(String[] wordiz,String documentPath) {
//		String[] wordProp = str.split("_");
		Words lastWord;
//		String theWord=wordProp[0];
		String wordStr = wordiz[0];
		if(!corpusWords.containsKey(wordStr)){
			//if(wordProp.length==2){
			//lastWord = new Words(theWord,wordProp[1],documentPath);
				lastWord = new Words(wordiz,documentPath);
				corpusWords.put(wordStr, lastWord);
			//}
		}
		corpusWords.get(wordStr).updateCorpusFrequency(documentPath);
	
	}

	private static ArrayList<String> getFileContent(File file) throws IOException { 
		String documentPath=file.getAbsolutePath();
		TaggedDocument taggedDocument = new TaggedDocument(documentPath,corpusWords);
//		Reader reader = new FileReader(file);
//		BufferedReader buffer=new BufferedReader(reader);
//		String line;
//		String content=null;
//		while((line=buffer.readLine())!=null)
//			content+=" "+line;
		return taggedDocument.getTaggedDocContent();
	}

	public static void getRepositoryList(String repository) {
		File[] list=null;
		File repertoire =  new File(repository);
		if(repertoire.isDirectory())
			list = repertoire.listFiles();
		for(File file:list)
			corpus.add(file);
		CORPUS_SIZE=corpus.size();
	}
}
