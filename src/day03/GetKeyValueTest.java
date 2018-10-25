package day03;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import jdk.internal.org.objectweb.asm.tree.IincInsnNode;

import org.junit.Test;

import com.mysql.jdbc.Connection;

import day01.JdbcTools;

public class GetKeyValueTest {
    @Test
	public void getBlob() throws Exception{
    	 Connection conn=null;
		   PreparedStatement preparedStatement=null;
		   ResultSet resultSet=null;
		   String sql="select * from b where id=6";
		   try {
			   //1.得到结果集
			conn=JdbcTools.getConnection();
			preparedStatement=conn.prepareStatement(sql);
			ResultSet rs=preparedStatement.executeQuery();
			if (rs.next()) {
				int id=rs.getInt(1);
				String name=rs.getString(2);
				Blob blob=rs.getBlob(3);
				InputStream in=blob.getBinaryStream();
				OutputStream out=new FileOutputStream("2.jpg");
				byte[] b=new byte[1024];
				int len=0;
				while ((len=in.read(b))!=-1) {
					out.write(b,0,len);
				}
				out.close();
				in.close();		
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			JdbcTools.jdbcClose(resultSet, preparedStatement, conn);
		}
	}
	
	
	@Test
	public void test() throws Exception {
		   Connection conn=null;
		   PreparedStatement preparedStatement=null;
		   ResultSet resultSet=null;
		   String sql="insert into b (name,pic) values(?,?)";
		   try {
			   //1.得到结果集
			conn=JdbcTools.getConnection();
			preparedStatement=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, "meili");
			InputStream inputStream=new FileInputStream("1.jpg");
			preparedStatement.setBlob(2, inputStream);
			preparedStatement.executeUpdate();
			
			
			ResultSet rs=preparedStatement.getGeneratedKeys();
			System.out.println(rs);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			JdbcTools.jdbcClose(resultSet, preparedStatement, conn);
		}
	}

}
