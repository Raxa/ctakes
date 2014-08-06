package org.raxa.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.raxa.main.GetInformation;


import com.google.gson.Gson;

@Path("/doctor")
public class doctorService {
	
	@GET
	@Path("/{parameter}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseMsg(@QueryParam("text") String text1) throws ClassNotFoundException, SQLException{
		List<HashMap<String,String>> terms = new ArrayList<HashMap<String, String>>();
		terms = GetInformation.getTerms(text1);		
		Gson gson = new Gson();
		String json = gson.toJson(terms);
		System.out.println(json);
		return Response.status(200)
				.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.entity(json).build();
		
	}

}
