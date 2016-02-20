package org.raxa.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.raxa.CtakesService;
import org.raxa.NaturalLanguageGenerator;

import com.google.gson.Gson;



@Path("/ctakes")
public class ctakesService {

	@GET
	@Path("/{parameter}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response resposeMsg(@QueryParam("language") String language, @QueryParam("text") List<String> text) throws Exception{
		
		ArrayList <HashMap<String,String>> drugMap = new ArrayList<HashMap<String,String>>();
		
		for(String drugtext : text){
			System.out.println(drugtext);
			HashMap<String, String> drug = CtakesService.extract(drugtext);
			String langconvertedText = 
					NaturalLanguageGenerator.langTranslator(drug.get("naturalText"),language);
			drug.put("naturalText", langconvertedText);
			//query the database to get the other info of drug
			System.out.println("Natural Text Out "+langconvertedText);
			HashMap<String,String> info = InformationExtraction.getInfo(drug.get("drug"));
			drug.putAll(info);
			drugMap.add(drug);
		}
		//String output = drugsList.toString();
		Gson gson = new Gson();
		String json = gson.toJson(drugMap);
		System.out.println(json);
		return Response.status(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.entity(json).build();
	}
}
