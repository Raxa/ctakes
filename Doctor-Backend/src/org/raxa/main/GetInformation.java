package org.raxa.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetInformation {

	public static List<HashMap<String,String>> getTerms(String text) throws ClassNotFoundException, SQLException{
		List<HashMap<String, String>> Terms = new ArrayList<HashMap<String, String>>();
		System.out.println("Searching for"+text);
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/Gsoc", "root", "rpg0908");
		
		String query = "SELECT * FROM doctorModule WHERE Text LIKE ? AND Type = ? LIMIT 6";
		PreparedStatement prepStmt = (PreparedStatement) connection.prepareStatement(query);
		prepStmt.setString(1,"%"+text+"%");
		prepStmt.setString(2, "Drug");
		System.out.println(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next()){
			HashMap<String,String> map = new HashMap<String, String>();
			map.put("Text", rs.getString("Text"));
			map.put("Type","Drug");
			Terms.add(map);
		}
		
		prepStmt.setString(2, "Test");
		System.out.println(prepStmt);
		rs = prepStmt.executeQuery();
		while(rs.next()){
			HashMap<String,String> map = new HashMap<String, String>();
			map.put("Text", rs.getString("Text"));
			map.put("Type","Test");
			Terms.add(map);
		}
		
		return Terms;
	}
	
	
}
