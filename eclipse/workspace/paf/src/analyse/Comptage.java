package analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

public class Comptage {
	private Set<Words> corpusWords=new HashSet<Words>();
	private static Set<File> corpus=new HashSet<File>();
//	private Map<String,File> corpusFileNames=new HashMap<String,File>();

	
	public static void main(String[] args) throws IOException{
		String repository="test/";
		getRepositoryList(repository);
		for(File file:corpus)
			computeDocument(file);
	}
	
	private static void computeDocument(File file) throws IOException {
		String content = getFileContent(file);
		String[] wordsTab = content.split("([ \t\n\r\f.\\'])");
		for(String word:wordsTab)
			System.out.println(word);
	}

	private static String getFileContent(File file) throws IOException {
		Reader reader = new FileReader(file);
		BufferedReader buffer=new BufferedReader(reader);
		String line;
		String content=null;
		while((line=buffer.readLine())!=null)
			content+=line;
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
