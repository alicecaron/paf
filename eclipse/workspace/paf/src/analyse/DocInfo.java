package analyse;

public class DocInfo {
	private String filename;
	private String matiere;
	private String classe;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public DocInfo(String filename){
		this.filename=filename;
		
	}
}
