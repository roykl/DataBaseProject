package runnableLogic;
import java.sql.ResultSet;
import db.IdbOparations;

public class Search implements Runnable {
	
	protected IdbOparations oparations ;
	protected String select;
	protected String from;
	protected String where;
	private ResultSet result;


	public Search(IdbOparations inOpp,String select, String from, String where){		
		oparations = inOpp;
		this.select = select;
		this.from = from;
		this.where = where;
	}
	
	public Search(){
	 
	}
	
	
	
	private ResultSet search(String select, String from, String where){		
		result = oparations.select(select, from, where);
		return result;		
	}
	
	
	public void run(){		
		this.search(select, from, where);
		return;
		
	}


	public ResultSet getResult() {
		return result;
	}

}
