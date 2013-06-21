package ontology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import analyse.Comptage;
import analyse.MyDocument;
import analyse.Words;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;


/**
 * <p>
 * Execution wrapper for class hierarchy example
 * </p>
 */
public class MyOntology {

	public static String URI = "http://www.semanticweb.org/deslis/ontologies/2013/1/LRE-BloomActionValues#";
	public static HashMap<MyDocument,LinkedDocument> docs = new HashMap<MyDocument,LinkedDocument>();
	
    public static void main( String[] args ) {
    	try {
			Comptage.main(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	for (MyDocument doc : Comptage.getCorpus())
    		docs.put(doc,(new LinkedDocument(doc)));
    	Set<Words> verbes = Comptage.getCorpusWords();
        OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
        m.read ("owl/Bloom_LRE.owl");
        fillOntology(m,verbes);
        
  //      new ClassHierarchy().showHierarchy( System.out, m );
        File file= new File("myonto.xml");
        try {
			m.write(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        HTMLTagger.tagHTML(docs);
    } 

   public static void fillOntology(OntModel onto, Set<Words> verbes){
	   
   	   OntClass cl;
	   Iterator<OntClass> it = onto.listClasses();
	   Map<String,OntClass> classes = new HashMap<String,OntClass>();
	   while(it.hasNext()){
		   cl = it.next();
		   classes.put(cl.getLabel("fr"), cl);
	   }
	   
	   for(Words w : verbes){
		   if(w.isVerb()){
			   String lemm = w.getLemm().getLemm(); 
			   if(classes.containsKey(lemm)){
				   System.out.println("Verbe de l'ontologie trouvé : "+ w.getWord() +" pour "+ lemm);
				   Individual ind = classes.get(lemm).createIndividual(URI+w.getWord());
				   ind.setLabel(w.getWord(), "fr");
				   linkDocuments(w);
			   }
		   }
	   }
   }
	private static void linkDocuments(Words w) {
		for (MyDocument doc : w.getDocFrequency().keySet())
			docs.get(doc).addWord(w.getWord());
	}
}