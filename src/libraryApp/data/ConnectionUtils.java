/**
 * 
 */
package libraryApp.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtils  {


	public ConnectionUtils() {
		super();
	}


	public static Connection getConnetion() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarynew", "root", "Odaa@430");
			conn.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

}