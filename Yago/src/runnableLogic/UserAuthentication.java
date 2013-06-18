package runnableLogic;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Configuration;

import db.IdbOparations;

public class UserAuthentication implements Runnable {

	private IdbOparations oparations ;
	private String userName;
	private String password;
	private int value;

	private static final int USER_NOT_EXIST = 2;
	private static final int OK = 1;
	private static final int ERR = 0;

	public  UserAuthentication(IdbOparations inOpp, String userName, String password){

		oparations = inOpp;
		this.password = password;
		this.userName = userName;
	}


	private int UserAuthentication(){
		int retVal;
		ResultSet result = oparations.select("idUsers", "Users", "idUsers = " +Integer.toString(userName.hashCode()) + " AND hashPassword = "+ Integer.toString(password.hashCode()));

		try {
			retVal = result.next() ? OK: USER_NOT_EXIST;
			result.close();
			return retVal;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERR;
		}

	}

	public void run(){
		value = this.UserAuthentication();
		return;
	}

	public int getValue(){
		return value;
	}

}
