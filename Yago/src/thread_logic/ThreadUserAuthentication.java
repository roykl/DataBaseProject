package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.IdbOparations;

public class ThreadUserAuthentication extends Thread {

	IdbOparations oparations ;
	String userName;
	String password;
	
	public  ThreadUserAuthentication(IdbOparations inOpp, String userName, String password){
		
		oparations = inOpp;
		this.password = password;
		this.userName = userName;
	}
	
	
private boolean UserAuthentication(){
	
	ResultSet result = oparations.select("idUser", "Users", "userName = '" + userName + "'" + " userPassword = '" + password +"'");
	
	try {
		return result.next();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;
}

public void run(){
		
		this.UserAuthentication();
	}
	
}
