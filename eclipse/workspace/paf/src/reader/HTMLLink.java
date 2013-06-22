package reader;

public class HTMLLink {
	public String link;
	public String filename;
	
	public HTMLLink(String str){
		String[] data=str.split(" ");
		this.link=data[0];
		this.filename=data[1];
	}
	
}
