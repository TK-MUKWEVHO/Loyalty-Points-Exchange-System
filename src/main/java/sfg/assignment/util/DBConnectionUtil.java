package sfg.assignment.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionUtil {

	//database props
	private static final String URL="jdbc:mysql://localhost:3306/loyaltypoints_system";
	private static final String DRIVER="com.mysql.cj.jdbc.Driver";
	private static final String USERNAME="root";
	private static final String PASSWORD="SFG117V";
	
	private static Connection connection=null;
	
	public static Connection openConnection() {
		
		if(connection !=null) {
			return connection;
		}
		else {
			try {
				Class.forName(DRIVER);
				connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return connection;
		}
	}
}
