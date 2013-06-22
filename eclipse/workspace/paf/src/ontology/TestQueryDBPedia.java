package ontology;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;

public class TestQueryDBPedia {

	public static void main(String[] args) {
		String service = "http://dbpedia.org/sparql";
		
		
		String sparqlQueryString1= "PREFIX dbont: <http://dbpedia.org/ontology/> "+
	    	    "PREFIX dbp: <http://dbpedia.org/property/>"+
	    	        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"+
	    	    "   SELECT ?musician  ?place"+
	    	//    "   FROM<http://dbpedia.org/resource/Daphne_Oram>"+
	    	    "   WHERE {  "+
	    	    "       ?musician dbont:birthPlace ?place ."+
	    	    "        }";


	    	      Query query = QueryFactory.create(sparqlQueryString1);
	    	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

	    	      ResultSet results = qexec.execSelect();
	    	      System.out.println(results);
	    	      ResultSetFormatter.out(System.out, results, query);       
	    	      System.out.println("=======================================================================");
	    	     qexec.close() ;
		
		
		
		String query3 = "ASK { }";
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query3);
		try {
			if (qe.execAsk()) {
			    System.out.println(service + " is UP");

				//test query
				String queryString ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> SELECT ?label " +
				    "WHERE {" +
				     "<http://dbpedia.org/resource/Barcelone> <http://dbpedia.org/ontology/country> ?y ."+
				     "?y rdfs:label ?label ."+ 
				     "FILTER (LANG(?label) = 'en')"+
				    "}";
				
				Query query2 = QueryFactory.create(queryString);
				QueryEngineHTTP qexec1 = QueryExecutionFactory.createServiceRequest(service, query2);
				ResultSet results2 = qexec1.execSelect();
				for ( ; results2.hasNext() ; ) {
				    QuerySolution soln = results2.nextSolution() ;
				    System.out.println(soln);
				    System.out.println(soln.getLiteral("label"));
				}
			}
		} catch (QueryExceptionHTTP e) {
		    System.out.println(service + " is DOWN");
		    } finally {
		        qe.close();
		    }
	}
}