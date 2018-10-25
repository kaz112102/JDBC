package day01;

import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.Connection;

public class JdbcTools {
	public static Connection getConnection() throws Exception{
		InputStream in=JdbcTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties=new Properties();
		properties.load(in);
		String driverclass=properties.getProperty("driver");
		String jdbcurl=properties.getProperty("jdbcurl");
		String user=properties.getProperty("user");
		String password=properties.getProperty("password");
		Class.forName(driverclass);
		return (Connection) DriverManager.getConnection(jdbcurl, user, password);
	}
	
	public static void jdbcClose(ResultSet resultSet,Statement statement,Connection conn) throws Exception{
		if (resultSet!=null) {
		     try {
		    	 resultSet.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		 }
		if (statement!=null) {
	     try {
			statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	     }
	     if (conn!=null) {
	    	 try {
	 			conn.close();
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 			e.printStackTrace();
	 		}
		}
		
	  }
	
	
}
