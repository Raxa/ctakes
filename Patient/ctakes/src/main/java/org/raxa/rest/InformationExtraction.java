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
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager
		    .getConnection("jdbc:mysql://localhost:3306/Gsoc", "root", "r");
		String query = "SELECT * FROM patientModule WHERE DrugName=?";
        PreparedStatement prepStmt = (PreparedStatement) connection.prepareStatement(query);
        prepStmt.setString(1,drugName);
        System.out.println(prepStmt);
        ResultSet rs = prepStmt.executeQuery();
        System.out.println(rs.first());
        String indication = rs.getString(3);
        String contraIndication = rs.getString(4);
        String caution = rs.getString(5);
        String precautions = rs.getString(6);
        
        System.out.println("ID hit "+rs.getInt(1));
        
        HashMap<String,String> info = new HashMap<String, String>();
        info.put("indication", indication);
		info.put("contraindication", contraIndication);
		info.put("caution", caution);
		info.put("sideeffects", precautions);
		
		return info;
	}

}
