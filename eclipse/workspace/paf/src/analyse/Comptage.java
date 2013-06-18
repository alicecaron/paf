package analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;

public class Comptage {
	private static Map<String,Words> corpusWords=new HashMap<String,Words>();
	private static Set<File> corpus=new HashSet<File>();
//	private Map<String,File> corpusFileNames=new HashMap<String,File>();

	
	public static void main(String[] args) throws IOException{
		String repository="test/";
		getRepositoryList(repository);
		for(File file:corpus)
			computeDocument(file);
		displayStatistics();
	}
	
	private static void displayStatistics() {
		Set<String> clef = corpusWords.keySet();
		Iterator<String> it = clef.iterator();
		while (it.hasNext()){
		   String key =  it.next();
		   Words word = corpusWords.get(key);
		   System.out.println(key+" apparaît "+word.getCorpusFrequency()+" fois dans ce corpus de textes.");
		}
		
	}

	private static void computeDocument(File file) throws IOException {
		String content = getFileContent(file);
		//System.out.println(content);
		content=StringEscapeUtils.escapeJava(content);
		System.out.println(content);
		content = content.replaceAll("([ \t\n\r\f.'():]|u2019|,)","%%%");
		System.out.println("here "+content);
		content=StringEscapeUtils.unescapeJava(content);
		String[] wordsTab=content.split("%%%");
		
		for(String word:wordsTab){
			if(!word.equals("") && !(word.length()<2)){
				System.out.println(word);
				computeWord(word,file.getAbsolutePath());
			}
		}
	}

	private static void computeWord(String str,String documentPath) {
		String[] wordProp = str.split("_");
		Words lastWord;
		String theWord=wordProp[0];
		if(!corpusWords.containsKey(theWord)){
			if(wordProp.length==2)
				lastWord = new Words(theWord,wordProp[1],documentPath);	
		}
		else{
			corpusWords.get(theWord).updateCorpusFrequency();
			corpusWords.get(theWord).updateDocumentFrequency(documentPath);
		}	
	}

	private static String getFileContent(File file) throws IOException {
		Reader reader = new FileReader(file);
		BufferedReader buffer=new BufferedReader(reader);
		String line;
		String content=null;
		while((line=buffer.readLine())!=null)
			content+=" "+line;
		return content;
	}

	public static void getRepositoryList(String repository) {
		File[] list=null;
		File repertoire =  new File(repository);
		if(repertoire.isDirectory())
			list = repertoire.listFiles();
		for(File file:list)
			//this.corpusFileNames.put(file.getAbsolutePath(), file);
			corpus.add(file);		
	}
}
