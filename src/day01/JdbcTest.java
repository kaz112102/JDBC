package day01;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javafx.css.PseudoClass;

import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.sun.org.apache.regexp.internal.recompile;

public class JdbcTest {

	@Test
	public void test() throws SQLException {
		Driver driver=new com.mysql.jdbc.Driver();
		String url="jdbc:mysql://localhost:3306/test";
		Properties info=new Properties();
		info.put("user", "root");
		info.put("password", "root");
		Connection connection=(Connection) driver.connect(url, info);
		System.out.println(connection);
	}
	
	public Connection getConnection() throws Exception{
		InputStream in=getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties=new Properties();
		properties.load(in);
		System.out.println(in);
		String driverclass=properties.getProperty("driver");
		String jdbcurl=properties.getProperty("jdbcurl");
		String user=properties.getProperty("user");
		String password=properties.getProperty("password");
		
		Driver driver=(Driver) Class.forName(driverclass).newInstance();
		Properties info=new Properties();
		info.put("user", user);
		info.put("password", password);
		Connection connection=(Connection) driver.connect(jdbcurl, info);
		return connection;
	}
	@Test
	public void TestgetConnection() throws Exception{
		//System.out.println(getConnection());
		String sql="delete from b where id=2";
		update(sql);
		
	}
    @Test
	public void testDriverManager() throws Exception{
		String driverclass="com.mysql.jdbc.Driver";
		String url="jdbc:mysql:///test";
		
		String user="root";
		String password="root";
		
		
		Class.forName(driverclass);
		
		Connection connection=(Connection) DriverManager.getConnection(url, user, password);
		
		System.out.println(connection);
	}
    @Test
    public void dothings() throws Exception{
		//1.获取数据库连接
    	String sql="insert into b(name) values ('lisi')";
    	Connection conn=null;
    	Statement statement=conn.createStatement();
    	statement.executeUpdate(sql);
        statement.close();
    	//2。关闭资源
    	conn.close();
	}   
    public void update(String sql) throws Exception{
		//1.获取数据库连接
    	System.out.println(sql);
    	Connection conn=null;
    	Statement statement=null;
    	try {
    		conn=JdbcTools.getConnection();
    		statement=conn.createStatement();
    		statement.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
		   JdbcTools.jdbcClose(null,statement, conn);	
		 }
	 } 
    
    
    
     
     
    
    
    
    
	
	
}
