package db;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;

public class DBOparations implements IdbOparations {


	Connection conn;
		
	//One row operations
		
	public DBOparations(Connection connParam){
		conn = connParam;
	}
	
	@Override
	public synchronized int update(String tableNamr, String column, String predicates) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public synchronized int delete(String tableNamr, String whereStatment) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public synchronized int insert(String statment) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public synchronized ResultSet select(String select, String from, String where) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

		@Override
		public synchronized void importData(String path) {
			// TODO Auto-generated method stub

		}

		

	
	

}
