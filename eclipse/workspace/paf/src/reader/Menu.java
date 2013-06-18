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
import java.util.Scanner;

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

	private static void computeHTML() throws Exception {
		ArrayList<String> HTMLLinks=getList("src/list.txt");
		makeTXTFile(HTMLLinks,"txt/");
	}

	private static void computePDF() throws Exception {
		String repositoryCollege="pdfSources/college/";
		ArrayList<String> pathListCollege = getRepositoryList(repositoryCollege);
		makeTXTFile(pathListCollege,repositoryCollege);
		String repositoryLycee="pdfSources/lycee/";
		ArrayList<String> pathListLycee = getRepositoryList(repositoryLycee);
		makeTXTFile(pathListLycee,repositoryLycee);
	}
	
	public static ArrayList<String> getRepositoryList(String repository) {
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
		if(choice==1){
			MyPDFReader pdfContent=null;
			for(String link:fileList){
				if(link.endsWith(".pdf")){
				pdfContent = new MyPDFReader(link);
				String[] st = link.split("\\/");
				String filename=st[st.length-1];
				filename=repository+filename.subSequence(0, filename.length()-4)+".txt";
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
		
		else  if(choice==2){
			int i=1;
			for(String link : fileList){
				String filename=link.replaceAll(".html", "");
				filename=filename.replaceAll(".htm", "");
				filename=filename.replaceAll("http:\\/\\/", "");
				filename=filename.replaceAll("\\/", "_");
				filename+=".txt";
				System.out.println(filename);
				MyHTMLReader textFromHTML = new MyHTMLReader(link);
				String text=extractHTMLText(textFromHTML.getText(),link);
				if(text!=null){
					String path = repository+filename;
					System.out.println(path);
					File file = new File(path);
					file.createNewFile();
					Writer out = new FileWriter(path);
					BufferedWriter writer = new BufferedWriter(out);
					System.out.println(filename+" : "+text);
					System.out.println(text.length());
					writer.write(text);
					writer.close();
				}
				i++;
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
		else{
			System.out.println("Mauvais modèle.");
			//    <a href="#top">
//			String[] text2 = text.split("<span class=\"TIT\">");
//			if(text2[1]!=null){
//				String text3 = null;
//				for(int i=1;i<text2.length;i++)
//					text3+=text2[i];
//				String[] text4 = text3.split("<a href=\"#top\">");
//				if(text4[0]!=null){
//					String text5 = text4[0].replaceAll("<[^>]*>", "");
//					//text5=text5.split(">")[1];
//					return text5;
//				}
//			}
//			
			
		}
		return null;
	}

	private static ArrayList<String> getList(String list) throws IOException {
		ArrayList<String> liste=new ArrayList<String>();
		Reader reader = new FileReader(list);
		BufferedReader buffer = new BufferedReader(reader);
		String line;
		while((line=buffer.readLine())!=null){
			System.out.println(line);
			liste.add(line);		
		}
		buffer.close();
		return liste;
	}
}
