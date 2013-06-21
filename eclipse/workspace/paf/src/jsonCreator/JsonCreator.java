package jsonCreator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
		
		ArrayList<JSONPerDoc> jsonDocs= new ArrayList<JSONPerDoc>();
		
		for(MyDocument doc:corpus){
			ArrayList<Words> docWords = doc.getDocWords();
			String groupe = doc.getGroupe();
			String classe = doc.getClasse();
			String matiere = doc.getMatiere();
			
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
			
			JSONObject docClasse = new JSONObject();
			JSONArray docClasseArray = new JSONArray();
			docClasse.put("name", classe);
			docClasse.put("children", docClasseArray);
			docArray.add(docClasse);
			
			for(Words word:docWords){
				if(word.getType().equals("VINF")){
					JSONObject verbe = new JSONObject();
					verbe.put("name", word.getWord());
					docClasseArray.add(verbe);
				}
			}
			
			
			
			
			
			
		}
			
			
			
			
			
			
			
			
			
			
			/*JSONObject jsonOfDoc = new jsonOfDoc();
			jsonOfDoc.put("name", classe);
			JSONObject jsonObject= new JSONObject();
			for(Words word:docWords){
				if(word.getType().equals("VINF")){
					jsonObject.put("name", word.getWord());
					list.add(jsonObject);
				}
			}
					
			JSONObject jsonMatiere = new JSONObject();
			jsonMatiere.put("name", matiere);
			jsonMatiere.put("children", jsonOfDoc);
			jsonObjectList.add(jsonMatiere);
			
			jsonDocs.add(new JSONPerDoc(classe, docWords));
		}
		for(JSONPerDoc jsonPerDoc:jsonDocs){
			
		}*/
		
		/*
		JSONArray jsonObjectList2=new JSONArray();
		
		JSONObject j1 = new JSONObject();
		JSONObject j2 = new JSONObject();
		JSONObject j3 = new JSONObject();
		JSONObject j4 = new JSONObject();
		JSONObject j5 = new JSONObject();
		JSONObject j6 = new JSONObject();
		
		j1.put("name", "Mathématiques");
		j1.put("children", jsonObjectList2);
		j2.put("name", "Physique");
		j3.put("name", "Arts");
		j4.put("name", "6eme");
		j5.put("name", "5eme");
		j6.put("name", "Terminale");
		
		jsonObjectList.add(j1);
		jsonObjectList.add(j2);
		jsonObjectList.add(j3);
		jsonObjectList2.add(j4);
		jsonObjectList2.add(j5);
		jsonObjectList2.add(j6);
		*/
		
		try {
			FileWriter file = new FileWriter("json/test.json");
			file.write(json.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print(json);
		
	}
	
	public static void main(String[] args){
		JSONObject json = new JSONObject();
		json.put("name", "Programme Scolaire");
		
		JSONArray jsonObjectList=new JSONArray();
		JSONArray jsonObjectList2=new JSONArray();
		json.put("children",jsonObjectList);
		
		JSONObject j1 = new JSONObject();
		JSONObject j2 = new JSONObject();
		JSONObject j3 = new JSONObject();
		JSONObject j4 = new JSONObject();
		JSONObject j5 = new JSONObject();
		JSONObject j6 = new JSONObject();
		
		j1.put("name", "Mathématiques");
		j1.put("children", jsonObjectList2);
		j2.put("name", "Physique");
		j3.put("name", "Arts");
		j4.put("name", "6eme");
		j5.put("name", "5eme");
		j6.put("name", "Terminale");
		
		
		jsonObjectList.add(j1);
		jsonObjectList.add(j2);
		jsonObjectList.add(j3);
		jsonObjectList2.add(j4);
		jsonObjectList2.add(j5);
		jsonObjectList2.add(j6);
		
		try {
			FileWriter file = new FileWriter("json/test.json");
			file.write(json.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print(json);
	}
}
