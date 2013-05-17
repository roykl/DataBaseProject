package DB;

import java.sql.ResultSet;

public interface IdbOparations {

	
	public  int update(String statment);
	public  int delete(String statment);
	public  int insert();
	public  ResultSet select();
}
