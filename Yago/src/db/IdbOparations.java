package db;

import java.sql.ResultSet;

public interface IdbOparations {

	//one row oparations
	
	public  int update(String tableNamr, String column ,String predicates );
	public  int delete(String tableNamr, String whereStatment /*WHERE some_column=some_value;*/);
	public  int insert(String statment);
	public  ResultSet select(String select, String from, String where);
}
