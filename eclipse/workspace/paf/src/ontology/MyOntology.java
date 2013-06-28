package ontology;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jsonCreator.JsonCreator;

import analyse.CSVBloomCreator;
import analyse.Comptage;
import analyse.MyDocument;
import analyse.Words;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class MyOntology {

	public static String URI = "http://www.semanticweb.org/deslis/ontologies/2013/1/LRE-BloomActionValues#";
	public static HashMap<MyDocument,LinkedDocument> docs = new HashMap<MyDocument,LinkedDocument>();
	public static Set<MyDocument> corpus= new HashSet<MyDocument>();
	public static JsonCreator jsonCreator;
	
    public static void main( String[] args ) throws IOException {
		Comptage comptage =new Comptage();
		comptage.main(null);
		Set<MyDocument> corpus = comptage.getCorpus();
    	for (MyDocument doc : corpus)
    		docs.put(doc,(new LinkedDocument(doc)));
    	
    	Collection<Words> verbes = comptage.getCorpusWords();
        OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
        m.read ("owl/Bloom_LRE.owl");
        fillOntology(m,verbes);
        
        HTMLTagger htmlTagger = new HTMLTagger();
        htmlTagger.tagHTML(docs);
    } 

   public static void fillOntology(OntModel onto, Collection<Words> mots) throws IOException{
   	   OntClass cl;
   	   Iterator<OntClass> it = onto.listClasses();
	   Map<String,OntClass> classes = new HashMap<String,OntClass>();
	   while(it.hasNext()){
		   cl = it.next();
		   classes.put(cl.getLabel("fr"), cl);
	   }
	   
	   CSVBloomCreator csvCreator = new CSVBloomCreator("csv/data.csv");
	   jsonCreator=new JsonCreator(corpus);
	   for(Words w : mots){
		  if (w.isVerb()) {
			   String lemm = w.getLemm().getLemm(); 
			   if (classes.containsKey(lemm)){
				   csvCreator.addLine(w);
				   //System.out.println("Verbe de l'ontologie trouvé : "+ w.getWord() +" pour "+ lemm);
				   Individual ind = classes.get(lemm).createIndividual(URI+w.getWord());
				   ind.setLabel(w.getWord(), "fr");
				   
				   linkDocuments(w,1);
			   }
			   else linkDocuments(w,2);
		  }
		  else if(w.getType().startsWith("X")){
			  linkDocuments(w,3); }
		 linkDocuments(w,4);
	   }
	   jsonCreator.makeJson();
	   csvCreator.makeCSVFile();
   }


	private static void linkDocuments(Words w,int n) {
		for (MyDocument doc : w.getDocFrequency().keySet()){
			switch(n){
			case 1:docs.get(doc).addWordToEnhance(w.getWord().toLowerCase());
				   jsonCreator.addWordInDoc(w,doc);
				break;
			case 2:docs.get(doc).addWordToSuggest(w.getWord().toLowerCase());
				break;
			case 3:docs.get(doc).addMotPropre(w.getWord().toLowerCase());
				break;
			}
			Float f=w.getLemm().getDocFrequency().get(doc).getTfidf();
			//System.out.println(f);
			if(f>5)
				docs.get(doc).addHighTfidf(w.getWord().toLowerCase(),f);
		}
	}
}