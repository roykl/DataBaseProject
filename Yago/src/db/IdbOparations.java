package db;

import java.sql.ResultSet;

/** Interface to link between the DB layer
 * and the logic(thread_logic) layer
 */
public interface IdbOparations {

	public ResultSet select(String select, String from, String where);
	public int importData();
	public int update(String tableNamr, String columnSet, String predicatesSet);
	public int delete(String tableNamr, String whereCol);
	public int insert(String table, String... values);
}
