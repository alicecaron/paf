package reader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;


public class Menu {

	private static int choice;
	
	public static void main (String[] args) throws Exception{
		Scanner scanner = new Scanner(System.in);
		boolean go=true;
		while(true){
			System.out.println("\n*******************************\n1: Extract PDF files contents\n2: Extract HTML files contents\n3: Quit\n*******************************\n");
			choice=scanner.nextInt();
			System.out.println(choice);
			go= compute(choice);
		}
	}

	private static boolean compute(int choice) throws Exception {
		switch(choice){
		case 1:computePDF();
			return true;
		case 2:computeHTML();
			return true;
		case 3:System.exit(0);return false;
		default:
			System.out.println("Bad choice, try again!");
			return true;
		
		}
	}
	
	/*******************************************
	 * Traitement des fichiers HTML 
	 *******************************************/
	
	private static void computeHTML() throws Exception {
		Set<HTMLLink> HTMLLinks=getListUp("src/list.txt");
		makeHTMLFile(HTMLLinks,"docTxt/");
	}
	private static Set<HTMLLink> getListUp(String list) throws IOException {
		Set<HTMLLink> liste=new HashSet<HTMLLink>();
		Reader reader = new FileReader(list);
		BufferedReader buffer = new BufferedReader(reader);
		String line;
		while((line=buffer.readLine())!=null){
			System.out.println(line);
			liste.add(new HTMLLink(line));		
		}
		buffer.close();
		return liste;
	}
	private static void makeHTMLFile(Set<HTMLLink> hTMLLinks, String repository) throws Exception {
		for(HTMLLink htmlLink:hTMLLinks){
			String link=htmlLink.link;
			String filename=htmlLink.filename;
			
			MyHTMLReader textFromHTML = new MyHTMLReader(link);
			String HTMLText=textFromHTML.getText();
			String text=extractHTMLText(textFromHTML.getText(),link);
			
			//save html content
			if(!HTMLText.equals("")){
				String path = repository+"docSaved/"+filename+".html";
				System.out.println(path);
				File file = new File(path);
				file.createNewFile();
				Writer out = new FileWriter(path);
				BufferedWriter writer = new BufferedWriter(out);
				if(HTMLText.startsWith("null"))
					HTMLText=HTMLText.substring(4,HTMLText.length());
				writer.write(HTMLText);
				writer.close();
			}
			//save text brut
			if(text!=null){
				String path = repository+filename+".txt";
				System.out.println(path);
				File file = new File(path);
				file.createNewFile();
				Writer out = new FileWriter(path);
				BufferedWriter writer = new BufferedWriter(out);
				if(text.startsWith("null"))
					text=text.substring(4,text.length());
				writer.write(text);
				writer.close();
			}
		}		
	}

	private static String extractHTMLText(String text,String link) {
		if(!link.startsWith("http://www.education.gouv.fr/bo/")){
			String[] text2 = text.split("<h2");
			if(text2[2]!=null){
				String text3 = null;
				for(int i=2;i<text2.length;i++)
					text3+=text2[i];
				String[] text4 = text3.split("<div id=\"bookmarks\">");
				if(text4[0]!=null){
					String text5 = text4[0].replaceAll("<[^>]*>", "");
					text5=text5.split(">")[1];
					return StringEscapeUtils.unescapeHtml3(text5);
				}
			}
		}
		else
			System.out.println("Mauvais modèle de fichier HTML.");
		return null;
	}
	
	/*******************************************
	 * Traitement des fichiers PDF 
	 *******************************************/
	
	private static void computePDF() throws Exception {
		String repository="docTxt/";
		ArrayList<String> pathToPDFFiles = getRepositoryList(repository+"pdf/");
		makeTXTFile(pathToPDFFiles,repository);
	}
	
	public static ArrayList<String> getRepositoryList(String repository) {
		System.out.println(repository);
		File[] list=null;
		File repertoire =  new File(repository);
		if(repertoire.isDirectory())
			list = repertoire.listFiles();
		ArrayList<String> pathList = new ArrayList<String>();
		for(File file:list)
			pathList.add(file.getAbsolutePath());
		return pathList;
	}
	
	private static void makeTXTFile(ArrayList<String> fileList, String repository) throws Exception {
		MyPDFReader pdfContent=null;
		for(String link:fileList){
			if(link.endsWith(".pdf")){
				pdfContent = new MyPDFReader(link);
			String[] st = link.split("\\/");
			String filename=st[st.length-1];
			filename=repository+filename.subSequence(0, filename.length()-4)+".txt";
			System.out.println(filename);
			File file = new File(filename);
			file.createNewFile();
			Writer out = new FileWriter(filename);
			BufferedWriter writer = new BufferedWriter(out);
			String text=pdfContent.getText().toString();
			System.out.println(filename+" : "+text);
			writer.write(text);
			writer.close();
			}
		}		
	}	
}
