package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.IdbOparations;

public class ThreadCheckExist extends Thread {
	
	IdbOparations oparations ;
	String select;
	String from;
	String where;
	
	public ThreadCheckExist(IdbOparations oparations,String select, String from, String where){
		this.oparations = oparations;
		this.select = select;
		this.from = from;
		this.where = where;
	}
	
	private boolean checkExist(){
		
		ResultSet result = oparations.select(select ,from, where);
		
		try {
			return result.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	

public void run(){
		
		this.checkExist();
		
		
	}


}