package cloud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class cloud {

	public static void main(String[] args) throws IOException{
		String filename="docTxt/test.txt";
		
		File file = new File(filename);
		FileReader in = new FileReader(file);
		BufferedReader buffer = new BufferedReader(in);
		
		String content="";
		String line;
		while((line=buffer.readLine())!=null){
			content+=line;
			
		}
		content=content.toLowerCase();
		content=content.replaceAll("([a-zA-Z](pour|qui|que|elle|il|ils|elles|de|des|ses|pas|à|en|aux|ça|ou|se|ainsi|tant|être|me|doit|tout|par|sous|sur|vers|avec|les|de|s\\'|leur|du|le|la|le|un|d'|l'|plus|comme|dans|et|des|ceci|cela|celui|sont|ce|cet|cette|y|www|à|ça|par|pour|en|vers|avec|sans|sous|être|est|si|son|etc|ce|se|lui|ont|d'une|.com|gouv)[a-zA-Z])", " ");
		String f="docTxt/filtered.txt";
		File file1 = new File(f);
		if(!file1.exists())
			file1.createNewFile();
		FileWriter in1 = new FileWriter(file1);
		in1.write(content);
		System.out.println("done");
		in1.close();
	}
	
	
}
