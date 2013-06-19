package analyse;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class MyDocument {

	private File file;
	private String filename;
	private String classe;
	private String groupe;
	private String matiere;
	private ArrayList<Words> docWords = new ArrayList<Words>();
	private Double sumCarreeOccurences;

	
	public ArrayList<Words> getDocWords() {
		return docWords;
	}
	public void setDocWords(ArrayList<Words> docWords) {
		this.docWords = docWords;
	}
	public Double getSumCarreeOccurences() {
		return sumCarreeOccurences;
	}
	public void setSumCarreeOccurences(Double sumCarreeOccurences) {
		this.sumCarreeOccurences = sumCarreeOccurences;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getGroupe() {
		return groupe;
	}
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}
	public String getMatiere() {
		return matiere;
	}
	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}
	
	public MyDocument(File file){
		this.file=file;
		String[] f=file.getAbsolutePath().split("/");
		this.filename=f[f.length-1];
		this.sumCarreeOccurences=0.0;
		setWordAppartenance(this.filename);
	}
	private void setWordAppartenance(String documentPath){
		documentPath=documentPath.replace(".txt", "");
		String[] nameCompo = documentPath.split("_");
		this.classe=null;
		this.matiere=null;
		this.groupe=null;
		try{
			if(nameCompo[0]!=null)
				//en 0:tout college lycee
				this.groupe=nameCompo[0];
		}
		catch(Exception e){System.err.println("Impossible de définir le groupe, la matière et la classe du document "+this.filename);return;}
		try{
			if(nameCompo[1]!=null)
				this.classe=nameCompo[1];
		}
		catch(Exception e){System.err.println("Impossible de définir la classe et la matière du document "+this.filename);return;}
		try{
			if(nameCompo[2] != null){
				//intro, general
				this.matiere=nameCompo[2];
			}
		}
		catch(Exception e){System.err.println("Impossible de définir la matière du document "+this.filename);return;}
	}
	
	public void setDocumentWords(Set<Words> corpusWords){
		for(Words word:corpusWords){
			if(word.getDocFrequency().containsKey(this))
				this.docWords.add(word);
		}
		computeSumOccurencePerWord();
	}
	private void computeSumOccurencePerWord() {
		for(Words word:this.docWords){
			int x = word.getDocFrequency().get(this).getDocumentFrequency();
			this.sumCarreeOccurences+=x*x;
		}
	}
}
