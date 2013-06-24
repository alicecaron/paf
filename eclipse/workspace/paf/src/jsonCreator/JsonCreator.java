package jsonCreator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import analyse.MyDocument;
import analyse.Words;

public class JsonCreator {
	private ArrayList<JSONObject> matiereList;
	public JsonCreator(Set<MyDocument> corpus){
		this.matiereList=new ArrayList<JSONObject>();
		JSONObject json = new JSONObject();
		JSONArray jsonObjectList=new JSONArray();
		json.put("name", "Programme Scolaire");		
		json.put("children",jsonObjectList);
			
		for(MyDocument doc:corpus){
			ArrayList<Words> docWords = doc.getDocWords();
			String matiere;
			String classe;
			if((matiere=doc.getMatiere())==null)
				matiere="Matiere inconnue";
			if((classe=doc.getClasse())==null)
				classe="Classe inconnue";
			
			JSONObject docObject;
			JSONArray docArray = null;
						
			for(JSONObject j :this.matiereList){
				if(j.get("name").equals(matiere)){
					docArray = (JSONArray) j.get("children");
					break;
				}
			}
			if(docArray==null){
				docArray=new JSONArray();
				docObject = new JSONObject();
				this.matiereList.add(docObject);
				docObject.put("name", matiere);
				docObject.put("children", docArray);
				jsonObjectList.add(docObject);
			}
			
			JSONArray docClasseArray = new JSONArray();
			for(Words word:docWords){
				if(word.getType().equals("VINF")){
					JSONObject verbe = new JSONObject();
					verbe.put("name", word.getWord());
					verbe.put("tfidf",word.getDocFrequency().get(doc).getTfidf());
					verbe.put("corpusFreq", word.getCorpusFrequency());
					verbe.put("nbDocApparition", word.getDocFrequency().size());
					verbe.put("nbApparition", word.getDocFrequency().get(doc).getDocumentFrequency());

					String otherDocs="";
					Set<MyDocument> clef = word.getDocFrequency().keySet();
					Iterator<MyDocument> it = clef.iterator();
					while (it.hasNext()) {
						MyDocument key = it.next();
						otherDocs+="<br><span class=\"other\">"+key.getMatiere()+" ("+key.getClasse()+")</span>";
					}
					verbe.put("otherDocs",otherDocs);
					docClasseArray.add(verbe);
				}
			}
			if(docClasseArray!=null){
				JSONObject docClasse = new JSONObject();	
				docClasse.put("name", classe);
				docClasse.put("children", docClasseArray);
				docArray.add(docClasse);
			}
		}		
		try {
			FileWriter file = new FileWriter("json/test.json");
			file.write(json.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("the Json file has been generated.");	
	}
}
