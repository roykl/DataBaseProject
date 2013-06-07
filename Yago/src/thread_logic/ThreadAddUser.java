package thread_logic;


import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Configuration;

import db.IdbOparations;

public class ThreadAddUser extends Thread {
	
	IdbOparations oparations ;
	String userName;
	String pass;
	
	
	public ThreadAddUser(IdbOparations inOpp, String inUserName, String inPass){
		oparations = inOpp;
		userName = inUserName;
		pass = inPass;
		
	}
	
	//if user not already exist (add user name, pass, and user ID to Users table) return true
	//return false
	private boolean addUser(){
		ResultSet result = oparations.select("idUsers", "Users", "idUsers = '" + Integer.toString(userName.hashCode()) + "'"); 
		
		try {
			//if no such a name than add it
			if(!result.next()){
			
			oparations.insert("Users", Integer.toString(userName.hashCode()) , "'" +userName + "'" , "'" +pass +  "'", Integer.toString(pass.hashCode()));
			return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return false;
		
	}
	
	public void run(){
		
		this.addUser();
	}
	
}
