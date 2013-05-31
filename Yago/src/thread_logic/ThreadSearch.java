package thread_logic;
import java.sql.ResultSet;
import db.IdbOparations;

public class ThreadSearch extends Thread {
	
	IdbOparations oparations ;
	String select, from, where;
	
	public ThreadSearch(IdbOparations inOpp,String select, String from, String where){
		
		oparations = inOpp;
		this.select = select;
		this.from = from;
		this.where = where;
	}
	
	
	
	private ResultSet search(String select, String from, String where){
		
		ResultSet result = oparations.select(select, from, where);
		return result;
		
	}
	
	
	public void run(){
		
		this.search(select, from, where);
	}
	
}
