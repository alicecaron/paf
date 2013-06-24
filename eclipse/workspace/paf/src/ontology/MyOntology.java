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
	
    public static void main( String[] args ) throws IOException {
		Comptage comptage =new Comptage();
		comptage.main(null);
    	for (MyDocument doc : comptage.getCorpus())
    		docs.put(doc,(new LinkedDocument(doc)));
    	
    	Set<Words> verbes = comptage.getCorpusWords();
        OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
        m.read ("owl/Bloom_LRE.owl");
        fillOntology(m,verbes);
        
  //      new ClassHierarchy().showHierarchy( System.out, m );
        File file= new File("myonto.xml");
/*        try {
			m.write(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
        HTMLTagger htmlTagger = new HTMLTagger();
        htmlTagger.tagHTML(docs);
    } 

   public static void fillOntology(OntModel onto, Set<Words> mots){
   	   OntClass cl;
	   Iterator<OntClass> it = onto.listClasses();
	   Map<String,OntClass> classes = new HashMap<String,OntClass>();
	   while(it.hasNext()){
		   cl = it.next();
		   classes.put(cl.getLabel("fr"), cl);
	   }
	   for(Words w : mots){
		  if (w.isVerb()) {
			   String lemm = w.getLemm().getLemm(); 
			   if (classes.containsKey(lemm)){
				   System.out.println("Verbe de l'ontologie trouvé : "+ w.getWord() +" pour "+ lemm);
				   Individual ind = classes.get(lemm).createIndividual(URI+w.getWord());
				   ind.setLabel(w.getWord(), "fr");
				   linkDocuments(w,1);
			   }
			   else linkDocuments(w,2);
		  }
		  else if(w.getType().startsWith("X")){
			  linkDocuments(w,3);
			 
			  
		  }
	   }
   }


	private static void linkDocuments(Words w,int n) {
		for (MyDocument doc : w.getDocFrequency().keySet())
			switch(n){
			case 1:docs.get(doc).addWordToEnhance(w.getWord().toLowerCase());
				break;
			case 2:docs.get(doc).addWordToSuggest(w.getWord().toLowerCase());
				break;
			case 3:docs.get(doc).addMotPropre(w.getWord().toLowerCase());
				break;
			
			}
	}
}