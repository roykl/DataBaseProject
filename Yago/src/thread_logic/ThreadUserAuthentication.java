package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Configuration;

import db.IdbOparations;

public class ThreadUserAuthentication extends Thread {

	private IdbOparations oparations ;
	private String userName;
	private String password;
	private int value;
	
	private static final int USER_NOT_EXIST = 2;
	private static final int OK = 1;
	private static final int ERR = 0;
	
	public  ThreadUserAuthentication(IdbOparations inOpp, String userName, String password){

		oparations = inOpp;
		this.password = password;
		this.userName = userName;
	}


	private int UserAuthentication(){
		ResultSet result = oparations.select("idUsers", "Users", "idUsers = " +Integer.toString(userName.hashCode()) + " AND hashPassword = "+ Integer.toString(password.hashCode()));

		try {
			return result.next() ? OK: USER_NOT_EXIST;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERR;
		}
	
	}

	public void run(){
		value = this.UserAuthentication();
	}

	public int getValue(){
		return value;
	}

}
