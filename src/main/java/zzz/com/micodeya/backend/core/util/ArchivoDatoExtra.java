package zzz.com.micodeya.backend.core.util;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ArchivoDatoExtra {

	private String lastModified;
	private Integer size;
	private String type; //Mime
	private String name;
	private String uuidName;
	private String fecha;
	private String jsonString;
	
	public ArchivoDatoExtra(JSONObject jsonObject) throws JSONException {
		
		
		lastModified=jsonObject.get("lastModified").toString();
		size=jsonObject.getInt("size");
		type=jsonObject.getString("type");
		name=jsonObject.getString("name");
		uuidName=jsonObject.getString("uuidName");
		fecha=jsonObject.getString("fecha");

		jsonString=jsonObject.toString();
		
	}

	public static List<ArchivoDatoExtra> jsonArrayToList(JSONArray array) throws JSONException{

		List<ArchivoDatoExtra> retornoList= new LinkedList<ArchivoDatoExtra>();

		for(int i = 0; i < array.length(); i++) {
	        JSONObject json = array.getJSONObject(i);
	        retornoList.add(new ArchivoDatoExtra(json));
		}
	    

		return retornoList;

	}


	

	
}


