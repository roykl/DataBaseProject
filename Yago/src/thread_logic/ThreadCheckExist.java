package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Configuration;

import db.IdbOparations;

public class ThreadCheckExist extends Thread {

	private IdbOparations oparations ;
	private String select;
	private String from;
	private String where;
	private boolean value;

	public ThreadCheckExist(IdbOparations oparations,String select, String from, String where){
		this.oparations = oparations;
		this.select = select;
		this.from = from;
		this.where = where;
	}

	//  from - don't need to give full table name

	/** check if exists in the db */
	private boolean checkExist(){
		ResultSet result = oparations.select(select , from, where);		
		try {	
			return result.next();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean getValue() {
		return value;
	}

	//public setV

	public void run(){
		value = this.checkExist(); 		
	}


}
