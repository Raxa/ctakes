package org.raxa.rest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.raxa.CtakesService;
import org.raxa.Drug;

import com.google.gson.Gson;



@Path("/ctakes")
public class ctakesService {

	@GET
	@Path("/{parameter}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response resposeMsg(@QueryParam("text") String text) throws AnalysisEngineProcessException, ResourceInitializationException, InvalidXMLException, MalformedURLException, URISyntaxException{
		
		System.out.println(text);
		ArrayList<Drug> drugsList = CtakesService.extract(text);
		//String output = drugsList.toString();
		Gson gson = new Gson();
		String json = gson.toJson(drugsList);
		//System.out.println(output);
		return Response.status(200).entity(json).build();
	}
}
