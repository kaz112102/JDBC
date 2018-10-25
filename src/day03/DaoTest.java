package day03;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class DaoTest {
    
	Dao dao=new Dao();
	
	@Test
	public void testUpdate() throws Exception {
		String sql="insert into b(name) values(?)";
		dao.update(sql, "wangwu");
		
	}

	@Test
	public void testGet() throws Exception {
		String sql="select * from b where id=?";
		Book book=dao.get(Book.class, sql, "3");
		System.out.println(book);
	}

	@Test
	public void testGetForList() throws Exception {
		String sql="select * from b ";
		List<Book> book=dao.getForList(Book.class, sql);
		System.out.println(book);
	}

	@Test
	public void testGetForValue() throws Exception {
		String sql="select name from b where id=?";
		String name=dao.getForValue(sql, "3");
		
		System.err.println(name);
	}

}
