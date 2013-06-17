package reader;

import java.net.*;
import java.io.*;

public class HTMLReader {
	
	private String url;
	private String text;
		
    public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public HTMLReader(String url) throws Exception {
    	this.url=url;
        URL oracle = new URL(url);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        	setText(getText()+inputLine);
        in.close();
    }
}