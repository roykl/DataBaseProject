package db;

import java.sql.ResultSet;

public interface IdbOparations {

	//one row oparations
	
	public  int update(String statment);
	public  int delete(String statment);
	public  int insert(String statment);
	public  ResultSet select(String statment);
}
