package thread_logic;


import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Configuration;

import db.IdbOparations;

public class ThreadAddUser extends Thread {
	
	private IdbOparations oparations ;
	private String userName;
	private String pass;
	private int value; // returned value
	private static final int OK = 1;
	private static final int ERR = 0;
	
	public ThreadAddUser(IdbOparations inOpp, String inUserName, String inPass){
		oparations = inOpp;
		userName = inUserName;
		pass = inPass;
		
	}
	
	//if user not already exist (add user name, pass, and user ID to Users table) return true
	//return false
	private int addUser(){
		ResultSet result = oparations.select("idUsers", "Users", "idUsers = '" + Integer.toString(userName.hashCode()) + "'"); 
		
		try {
			//if no such a name than add it
			if(!result.next()){
			
			oparations.insert("Users", Integer.toString(userName.hashCode()) , "'" +userName + "'" , "'" +pass +  "'", Integer.toString(pass.hashCode()));
			result.close();
			return OK;
			}
			
		} catch (SQLException e) {
			
			
			return ERR;
		}
		
		return ERR;
		
	}
	
	public int getValue() {
		return value;
	}

	//public setV

	public void run(){
		value = this.addUser(); 		
	}
	
}
