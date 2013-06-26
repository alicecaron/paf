package analyse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jsonCreator.JsonCreator;

import tagger.TaggedDocument;

public class Comptage {
	private static HashMap<String,Words> corpusWords = new HashMap<String,Words>();
	private static HashMap<String,Lemm> corpusLemms = new HashMap<String,Lemm>();
	private static Set<MyDocument> corpus = new HashSet<MyDocument>();
	private static int CORPUS_SIZE;
	private static DocumentDifferences docDiff;
	public static String regex="(^(ADVNE|ADVPAS|DET|COCO|CHIF|COSUB|PDEM|PIN|PPER|PPOB|PRE|YPFAI|YPFOR|VA|VE).*)";
	
	public static void main(String[] args) throws IOException {
		String repository = "test/";
		getRepositoryList(repository);
		
		/*****************************************************
		 * CorpusDoc et corpusLemms et corpusWord complétés
		 *****************************************************/
		for (MyDocument document : corpus)
			computeDocument(document);
		
		/*****************************************************
		 * Tri des mots et lemms pour enlever les inutiles
		 *****************************************************/
		sortUselessWords();
		sortUselessLemms();
		//forTheCloud();

		/*****************************************************
		 * Calculs des TFIDF des mots et des lemmes
		 *****************************************************/
		for (Words word : corpusWords.values())
			if(!word.getFiltered()) word.computeTFIDF(corpus);

		String cc="";
		for (Lemm lemm : corpusLemms.values()){
			if(!lemm.getFiltered()){ 
				lemm.computeTFIDF(corpus);
				//System.out.println(lemm.getLemm() +" "+lemm.getType());
				if(!lemm.getFiltered())
					for(int i=0;i<lemm.getCorpusFrequency();i++)
						if(!lemm.getLemm().toLowerCase().matches("(pas|ci|son|est|très|plus|pas|non|tel|zéro|bien|ne|sont|sera|soit)"))
							cc+=" "+lemm.getLemm();
			}
		}
		File file=new File("total.txt");
		FileWriter w = new FileWriter(file);
		w.write(cc);
		w.close();
			

		/*****************************************************
		 * Matrice des différences inter-documents
		 *****************************************************/
		docDiff = new DocumentDifferences(corpus);

		
//		/*****************************************************
//		 * Création du JSON pour la visualisation
//		 *****************************************************/
//		JsonCreator json=new JsonCreator(corpus);
//		//CSVCreator csv=new CSVCreator(corpusWords);
		
		/*****************************************************
		 * Affichage des statistiques
		 *****************************************************/
		// displayWordStatistics();
		// displayLemmStatistics();
		displayDocumentSimilarities();
		//displayFiltered();
		System.out.println("Nombre de documents dans le corpus: " + CORPUS_SIZE);	
	}

	/*private static void forTheCloud() throws IOException {
		String cc="";
		for(Lemm lemm:corpusLemms.values()){
			if(!lemm.getFiltered())
				for(int i=0;i<lemm.getCorpusFrequency();i++)
					cc+=" "+lemm.getLemm();
		}
		File file=new File("total.txt");
		FileWriter w = new FileWriter(file);
		w.write(cc);
		w.close();
	}*/

	//Tris
	private static void sortUselessWords() {
		for (Words word : corpusWords.values()) {
			if ( word.getType().matches(regex)
				|| (word.getType().equals("MOTINC")&&corpusWords.containsKey(word.getWord().toLowerCase())) ){
				word.setFiltered(true);
			//if (word.getType().matches("(^(X).*)")){
				//word.setFiltered(false);
					//System.out.println(word.getWord()+" "+word.getType());
			}
		}
	}
	private static void sortUselessLemms() {
		for (Lemm lemm : corpusLemms.values()) {
			if (lemm.getType().matches(regex)
				|| (lemm.getType().equals("MOTINC")&&corpusWords.containsKey(lemm.getLemm().toLowerCase()))){
				lemm.setFiltered(true);
				//System.out.println(lemm.getWord()+" "+lemm.getType()+" is now filtered");
			}
		}
	}

	//rendre le corpus accessible à l'ontologie
	public static Set<MyDocument> getCorpus() {
		return corpus;
	}
	public static Collection<Words> getCorpusWords() {
		return corpusWords.values();
	}

	//Remplissage des documents, mots et lemmes du corpus
	private static void computeDocument(MyDocument document) throws IOException {
		ArrayList<String> content = getFileContent(document.getFile());

		for (String wordInfos : content) {
			String[] wordiz = wordInfos.split(" ");
			if (!wordiz[0].equals("") && !(wordiz[0].length() < 2) && !(wordiz[1].equals("ZTRM")))
				computeWord(wordiz, document);
		}
	}
	private static ArrayList<String> getFileContent(File file) throws IOException {
		String documentPath = file.getAbsolutePath();
		TaggedDocument taggedDocument = new TaggedDocument(documentPath);
		return taggedDocument.getTaggedDocContent();
	}
	private static void computeWord(String[] wordiz, MyDocument document) {
		String wordStr = wordiz[0];
		boolean found = false;
		Words foundWord = null;
		Lemm foundLemm;
		for (Words word : corpusWords.values()) {
			if (word.getWord().equals(wordStr) && word.getType().equals(wordiz[1])) {
				found = true;
				foundWord = word;
				word.updateCorpusFrequency(document);
				document.addWord(foundWord);
				break;
			}
		}
		if (!found) {
			foundWord = new Words(wordiz, document);
			corpusWords.put(wordStr,foundWord);
		}
		for (Lemm lemm : corpusLemms.values()) {
			if (lemm.getLemm().equals(wordiz[2])) {
				foundWord.setLemm(lemm);
				lemm.updateCorpusFrequency(document);
				return;
			}
		}
	//	if(document.getClasse().equals("3")) System.out.println("NOUVEAU LEMME : "+wordiz[2]);
		foundLemm = new Lemm(wordiz, document);
		corpusLemms.put(wordiz[2],foundLemm);
		foundWord.setLemm(foundLemm);
	}

	//recherche des documents du corpus
	public static void getRepositoryList(String repository) {
		File[] list = null;
		File repertoire = new File(repository);
		if (repertoire.isDirectory())
			list = repertoire.listFiles();
		for (File file : list)
			corpus.add(new MyDocument(file));
		CORPUS_SIZE = corpus.size();
	}

	/**************************************
	 * Display Word and Lemm statistics
	 **************************************/
	private static void displayWordStatistics() {
		for (Words word : corpusWords.values()) {
			System.out.println("=====================" + word.getWord() + " "
					+ word.getType() + " " + word.getLemm());
			System.out.println("++ Corpus frequency: "
					+ word.getCorpusFrequency());
			System.out.println("++ Documents presence: "
					+ word.getDocFrequency().size());
			getDocPresence(word);
		}
	}
	private static void displayLemmStatistics() {
		for (Lemm lemm : corpusLemms.values()) {
			System.out.println("=====================" + lemm.getLemm() + " "
					+ lemm.getType());
			System.out.println("++ Corpus frequency: "
					+ lemm.getCorpusFrequency());
			System.out.println("++ Documents presence: "
					+ lemm.getDocFrequency().size());
			getDocPresence(lemm);
		}
	}
	private static void getDocPresence(Mots mot) {
		System.out
				.println("---------------------------------Statistics per document:");
		Set<MyDocument> clef = mot.getDocFrequency().keySet();
		Iterator<MyDocument> it = clef.iterator();
		while (it.hasNext()) {
			MyDocument key = it.next();
			int documentFrequency = mot.getDocFrequency().get(key)
					.getDocumentFrequency();
			System.out.println("**" + key.getFilename() + ": "
					+ documentFrequency + " times ");
			try {
				System.out.print("(" + key.getClasse() + "," + key.getMatiere()
						+ "," + key.getGroupe() + ")");
			} catch (Exception e) {
			}
			System.out.println("    TFIDF: "
					+ mot.getDocFrequency().get(key).getTfidf());// .getDocFrequency().get(key).computeTFIDF(CORPUS_SIZE,word.getDocFrequency().size()));
		}
		System.out
				.println("-----------------------------------------------------------");
	}
	//fonctions d'affichage des stats
	private static void displayDocumentSimilarities() throws IOException {
		exportSimilarities exportSimilarities=new exportSimilarities("similarites.csv");
		
		for (MyDocument doc : corpus) {
			// String filename=doc.getFilename();
			String closeDocuments = doc.getMatiere() + " proche de: ";
			Set<TripletDistance> diff = docDiff.getDifferenceMatrix();
			Map<String, Float> distancesToSort = new HashMap<String, Float>();
			
			for (TripletDistance triplet : diff) {
				if (triplet.getDoc1().getFilename().equals(doc.getFilename())) {
					distancesToSort.put(triplet.getDoc2().getFilename(),triplet.getDistance());
					//closeDocuments += triplet.getDoc2().getMatiere() + " "+ triplet.getDistance() + ", ";
					exportSimilarities.addCloseDoc(doc.getFilename(), triplet.getDoc2().getFilename(), triplet.getDistance());
				} else if (triplet.getDoc2().getFilename().equals(doc.getFilename())) {
					distancesToSort.put(triplet.getDoc1().getFilename(),
					triplet.getDistance());
					//closeDocuments += triplet.getDoc1().getMatiere() + " "+ triplet.getDistance() + ", ";
					exportSimilarities.addCloseDoc(doc.getFilename(), triplet.getDoc1().getFilename(), triplet.getDistance());
				}
			}
			//System.out.println(closeDocuments);
		}
		exportSimilarities.display();
	}
	private static void displayFiltered() {
		for (Words word : corpusWords.values()) {
			if(!word.getFiltered())
				System.out.println(word.getWord());
		}
	}

}
