package analyse;

import java.io.File;
import java.lang.reflect.Array;
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
	private Double sumCarreeTFIDF;
	private ArrayList<Lemm> docLemms = new ArrayList<Lemm>();
	private Double sumCarreeOccurencesLemm;
	private Double sumCarreeTFIDFLemm;
	private int nbWords;
	
	
	public int getNbWords() {
		return nbWords;
	}
	public void setNbWords(int nbWords) {
		this.nbWords = nbWords;
	}
	public Double getSumCarreeTFIDF() {
		return sumCarreeTFIDF;
	}
	public void setSumCarreeTFIDF(Double sumCarreeTFIDF) {
		this.sumCarreeTFIDF = sumCarreeTFIDF;
	}
	public ArrayList<Lemm> getDocLemms() {
		return docLemms;
	}
	public void setDocLemms(ArrayList<Lemm> docLemms) {
		this.docLemms = docLemms;
	}
	public Double getSumCarreeOccurencesLemm() {
		return sumCarreeOccurencesLemm;
	}
	public void setSumCarreeOccurencesLemm(Double sumCarreeOccurencesLemm) {
		this.sumCarreeOccurencesLemm = sumCarreeOccurencesLemm;
	}
	public Double getSumCarreeTFIDFLemm() {
		return sumCarreeTFIDFLemm;
	}
	public void setSumCarreeTFIDFLemm(Double sumCarreeTFIDFLemm) {
		this.sumCarreeTFIDFLemm = sumCarreeTFIDFLemm;
	}
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
		this.sumCarreeTFIDF=0.0;
		this.sumCarreeOccurencesLemm=0.0;
		this.sumCarreeTFIDFLemm=0.0;
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
		catch(Exception e){
			this.groupe=this.matiere=this.classe=this.filename;
			System.err.println("Impossible de définir le groupe, la matière et la classe du document "+this.filename);return;}
		try{
			if(nameCompo[1]!=null)
				this.matiere=nameCompo[1];
		}
		catch(Exception e){
			this.matiere=this.classe=this.filename;
			System.err.println("Impossible de définir la classe et la matière du document "+this.filename);return;}
		try{
			if(nameCompo[2] != null){
				//intro, general
				this.classe=nameCompo[2];
			}
		}
		catch(Exception e){
			this.classe=this.filename;
			System.err.println("Impossible de définir la classe du document "+this.filename);return;}
	}
	
	public void computeSumPerWord(ArrayList<Words> commons) {
		for(Words word:commons){
			Double x =  (double)(word.getDocFrequency().get(this).getDocumentFrequency()/(double)(this.nbWords));
			this.sumCarreeOccurences+=x*x;
			float y = word.getDocFrequency().get(this).getTfidf()/(float)(this.nbWords);
			this.sumCarreeTFIDF+=y*y;
		}
	}
	public void computeSumPerLemm(ArrayList<Lemm> commons) {
		for(Lemm lemm:commons){
			Double x = (double)(lemm.getDocFrequency().get(this).getDocumentFrequency());
			this.sumCarreeOccurencesLemm+=x*x;
			float y = lemm.getDocFrequency().get(this).getTfidf();
			this.sumCarreeTFIDFLemm+=y*y;
		}
	}
	public void addLemm(Lemm lemm) {
		for(Lemm l:docLemms)
			if(l.getLemm().equals(lemm.getLemm()))
				return;
		this.docLemms.add(lemm);		
	}
	public void addWord(Words words) {
		this.nbWords++;
		for(Words w:docWords)
			if(w.getWord().equals(words.getWord()))
				return;
		this.docWords.add(words);
	}
}
