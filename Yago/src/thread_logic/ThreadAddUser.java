package thread_logic;


import java.sql.ResultSet;
import java.sql.SQLException;

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
		
		ResultSet result = oparations.select("idUser", "Users", "userName = '" + userName + "'"); 
		
		try {
			//if no such a name than add it
			if(!result.next()){
			
			oparations.insert("Users", Integer.toString(userName.hashCode()) , "'" +userName + "'" , "'" +pass +  "'");
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
