package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.IdbOparations;

public class ThreadUserUpdate extends Thread {

	IdbOparations oparations ;
	String table;
	int firstKey; //idMovie
	int secondKey;//idActor|idGenre
	String newVal;
	String column;
	
	
	public ThreadUserUpdate(IdbOparations inOpp, String table, int firstKey,int secondKey, String coulmn, String newVal){
		
		oparations = inOpp;
		this.table = table;
		this.column = coulmn;
		this.newVal = newVal;
		this.firstKey = firstKey;
		this.secondKey = secondKey;
	}
	
	
	

//update Yago tables
private void yagoUpdate(){
	if(table == "Movie"){
		oparations.update(table, column + " = '" + newVal + "'"  , "idMovie" + "= '" + firstKey + "'");
	}
	else if(table == "Actor-Movie"){
		oparations.update(table, column +  " = '"  + newVal + "'"  , "idMovie" + " = '" + firstKey + "' "+ "idActor" + " = '" + secondKey + "'" );
	}else{//Genre-Movie
		oparations.update(table, column +  " = '"  + newVal + "'"  , "idMovie" + " = '" + firstKey + "' "+ "idGenre" + " = '" + secondKey+ "'" );
	}
	
	
}
	

private void userTableUpdate() {
	//if value already updated - UPDATE
	if(checkExist("Updates", "tableName column firstKey secondKey", "tableName = " + table + " column = " + column + " firstKey = " + firstKey + " secondKey = " + secondKey))
	{	
		oparations.update("Updates", "newVal = " + newVal + "'", "table = " + table + "'" + " column = " +  column + "'" + "firstKey = " + firstKey + "'" + "secondKey = " + secondKey+ "'");
	}
	else
	{	//INSERT
		oparations.insert("Updates", table ,column , newVal, Integer.toString(firstKey),Integer.toString(secondKey));
	}
		
	
	
}

private void userUpdate() {
	yagoUpdate();
	userTableUpdate();
	
	
}


	
private boolean checkExist(String select, String from, String where ){
	
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
		
		this.userUpdate( );
		
		
	}





	
}
