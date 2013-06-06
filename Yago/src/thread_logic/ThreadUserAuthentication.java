package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Configuration;

import db.IdbOparations;

public class ThreadUserAuthentication extends Thread {

	IdbOparations oparations ;
	String userName;
	String password;
	private boolean value;

	public  ThreadUserAuthentication(IdbOparations inOpp, String userName, String password){

		oparations = inOpp;
		this.password = password;
		this.userName = userName;
	}


	private boolean UserAuthentication(){
		Configuration settings = new Configuration();
		String dbName = settings.getDbName();
		ResultSet result = oparations.select("idUsers", dbName+".Users", "userName = '" + userName + "'" + " AND userPassword = '" + password +"'");

		try {
			return result.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void run(){
		value = this.UserAuthentication();
	}

	public boolean getValue(){
		return value;
	}

}
