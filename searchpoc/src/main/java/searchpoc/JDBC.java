package searchpoc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {

	ResultSet rs = null;
	
	public  ResultSet getResultSet() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://192.168.33.101:3306/martjack", "capillary", "123");
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("select * from user_mapping limit 10;");
			//con.close();
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return rs;
	}

}
