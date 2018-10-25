package day03;


import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import com.mysql.jdbc.Connection;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.regexp.internal.recompile;

import day01.JdbcTools;
public class Dao {
   //更新
   public void  update(String sql,Object ... args) throws Exception {
	Connection conn=null;
	PreparedStatement preparedStatement=null;
	
	try {
	 	conn=JdbcTools.getConnection();
	    preparedStatement=conn.prepareStatement(sql);	
	    for (int i = 0; i < args.length; i++) {
			preparedStatement.setObject(i+1, args[i]);
		}
	    preparedStatement.executeUpdate();
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}finally{
		JdbcTools.jdbcClose(null, preparedStatement, conn);
	}
	   
	   
   }
   //查询一条记录，返回适应的对象
   public  <T> T get(Class<T>clazz,String sql,Object ... args) throws Exception {
     List<T> result=getForList(clazz, sql, args);
     if (result.size()>0) {
	  return result.get(0);
	}
     return null;
   }
   
   
   //查询多条记录，返回适应的对象
   public  <T> List<T> getForList(Class<T>clazz,String sql,Object ... args) throws Exception {
	   List<T> lists=new ArrayList<T>();
	   Connection conn=null;
	   PreparedStatement preparedStatement=null;
	   ResultSet resultSet=null;
	   try {
		   //1.得到结果集
		conn=JdbcTools.getConnection();
		preparedStatement=conn.prepareStatement(sql);
		for (int i = 0; i < args.length; i++) {
			preparedStatement.setObject(i+1, args[i]);
		}
		resultSet=preparedStatement.executeQuery();
		//2.处理结果集 得到map的list 其中一个map对象就是一条记录
		List<Map<String, Object>> values = handleResultToMapList(resultSet);
		//3.
		lists=transferMapListToBeanList(clazz, values);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}finally{
		JdbcTools.jdbcClose(resultSet, preparedStatement, conn);
	}
	return lists;
   }

	public <T> List<T> transferMapListToBeanList(Class<T> clazz,
			List<Map<String, Object>> values) throws InstantiationException,
			IllegalAccessException, InvocationTargetException {
		List<T> result = new ArrayList<T>();
		T bean = null;
		if (values.size() > 0) {
			for (Map<String, Object> m : values) {
				bean = clazz.newInstance();
				for (Map.Entry<String, Object> entry : m.entrySet()) {
					String propertyName = entry.getKey();
					Object value = entry.getValue();
					BeanUtils.setProperty(bean, propertyName, value);
				}
				result.add(bean);
			}
		}
		return result;
	}
  private List<Map<String, Object>> handleResultToMapList(ResultSet resultSet)
		throws SQLException {
	List<Map<String, Object>> values=new ArrayList<Map<String,Object>>();
	List<String> labels=getColumnLabels(resultSet);
	Map<String,Object> map=null;
	while (resultSet.next()) {
		map=new HashMap<String,Object>();
		for (String label:labels) {
			Object columnValue=resultSet.getObject(label);
			map.put(label, columnValue);
		}
		values.add(map);
	}
	return values;
}
    private List<String> getColumnLabels(ResultSet rs) throws SQLException {
    	List<String> list=new ArrayList<String>();
    	ResultSetMetaData resultSetMetaData=rs.getMetaData();
    	for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
			list.add(resultSetMetaData.getColumnLabel(i+1));
		}
    	return list;
	}
   
   //返回某条记录的某一个字段的值或者是一个统计的值
   public <E> E getForValue(String sql,Object ... args) throws Exception{
	   Connection conn=null;
	   PreparedStatement preparedStatement=null;
	   ResultSet resultSet=null;
	   try {
		   //1.得到结果集
		conn=JdbcTools.getConnection();
		preparedStatement=conn.prepareStatement(sql);
		for (int i = 0; i < args.length; i++) {
			preparedStatement.setObject(i+1, args[i]);
		}
		resultSet=preparedStatement.executeQuery();
		if (resultSet.next()) {
			return (E) resultSet.getObject(1);
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}finally{
		JdbcTools.jdbcClose(resultSet, preparedStatement, conn);
	}
	   return null;
   }
   
   
	
}
