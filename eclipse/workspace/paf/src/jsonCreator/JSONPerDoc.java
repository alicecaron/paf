package jsonCreator;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import analyse.Words;

public class JSONPerDoc {

	private JSONObject classe;
	private JSONArray list;

	public JSONPerDoc(String classe,ArrayList<Words> docWords){
		this.classe.put("name", classe);

		JSONObject jsonObject= new JSONObject();
		for(Words word:docWords){
			if(word.getType().equals("VINF")){
				jsonObject.put("name", word.getWord());
				list.add(jsonObject);
			}
		}
	}
}
