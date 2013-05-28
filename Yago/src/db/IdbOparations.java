package db;

import java.sql.ResultSet;

public interface IdbOparations {

	//one row oparations
		
	public ResultSet select(String select, String from, String where);
	public void importData();
	public int update(String tableNamr, String columnSet,
					String predicatesSet, String columnWhere, String predicateWhere);
	public int update(String tableNamr, String columnSet, String predicatesSet);
	public int delete(String tableNamr, String whereCol, String pred);
	public int insert(String table, String... values);
}
