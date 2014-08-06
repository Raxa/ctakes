package org.raxa.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class InformationExtraction {

	public static HashMap<String, String> getInfo(String drugName) throws ClassNotFoundException, SQLException {
		System.out.println("Getting information for "+drugName);

		HashMap<String,String> info = new HashMap<String, String>();
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/Gsoc", "root", "rpg0908");
		
		if(drugName.equals("")){
			System.out.println("No drug to search for");
			info.put("indication", "No information present");
			info.put("contraindication", "No information present");
			info.put("caution", "No information present");
			info.put("sideeffects", "No information present");
			return info;
			
		}
		
		String query = "SELECT * FROM patientModule WHERE DrugName=?";

		PreparedStatement prepStmt = (PreparedStatement) connection.prepareStatement(query);
		prepStmt.setString(1,drugName);
		System.out.println(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		
		if(rs.first()==false){
			//print
			System.out.println("Drug not a generic drug");
			System.out.println("Searching drug from generic map");
			
			//get generic name from drugGeneric map
			String queryForGeneric = "Select * FROM drugGenericMap where DrugName=?";
			prepStmt = (PreparedStatement) connection.prepareStatement(queryForGeneric);
			prepStmt.setString(1,drugName.toUpperCase());
			System.out.println(prepStmt);
			rs = prepStmt.executeQuery();

			//check if present
			if(rs.first()==true){
				//generic present in database
				//search for information
				System.out.println("Generic Drug Name present");
				String genericName = rs.getString(3);
				prepStmt = (PreparedStatement) connection.prepareStatement(query);
				prepStmt.setString(1,genericName);
				System.out.println(prepStmt);
				ResultSet rs2 = prepStmt.executeQuery();
				
				if (rs2.first()==true){
					System.out.println("Information about generic drug present");
					rs = rs2;
				}
				//info not present
				else {
					System.out.println("Information about generic drug not present");
					info.put("indication", "No information present");
					info.put("contraindication", "No information present");
					info.put("caution", "No information present");
					info.put("sideeffects", "No information present");
					return info;
				}
					
			}
			else {
				System.out.println("Generic Name of the drug not present");
				info.put("indication", "No information present");
				info.put("contraindication", "No information present");
				info.put("caution", "No information present");
				info.put("sideeffects", "No information present");
				return info;

			}
			

		}
		
			String indication = rs.getString(3);
			String contraIndication = rs.getString(4);
			String caution = rs.getString(5);
			String precautions = rs.getString(6);

			System.out.println("ID hit "+rs.getInt(1));

			info.put("indication", indication);
			info.put("contraindication", contraIndication);
			info.put("caution", caution);
			info.put("sideeffects", precautions);

			return info;
		


	}

}
