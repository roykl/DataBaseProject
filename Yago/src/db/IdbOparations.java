package db;

import java.sql.ResultSet;

public interface IdbOparations {

	//one row oparations
		
	public ResultSet select(String select, String from, String where);
	public void importData();
	public int update(String tableNamr, String columnSet, String predicatesSet);
	public int delete(String tableNamr, String whereCol);
	public int insert(String table, String... values);
}
