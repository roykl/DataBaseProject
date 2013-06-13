package thread_logic;
import java.sql.ResultSet;
import db.IdbOparations;

public class ThreadSearch extends Thread {
	
	protected IdbOparations oparations ;
	protected String select;
	protected String from;
	protected String where;
	private ResultSet result;


	public ThreadSearch(IdbOparations inOpp,String select, String from, String where){		
		oparations = inOpp;
		this.select = select;
		this.from = from;
		this.where = where;
	}
	
	public ThreadSearch(){
	 
	}
	
	
	
	private ResultSet search(String select, String from, String where){		
		result = oparations.select(select, from, where);
		return result;		
	}
	
	
	public void run(){		
		this.search(select, from, where);
		
	}


	public ResultSet getResult() {
		return result;
	}

}
