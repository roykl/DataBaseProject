package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.IdbOparations;

public class ThreadUserUpdate extends Thread {

	IdbOparations oparations ;
	String table;
	String firstKey; //idMovie
	String secondKey;//idActor|idGenre
	String newVal;
	String column;
	
	
	public ThreadUserUpdate(IdbOparations inOpp, String table, String firstKey,String secondKey, String coulmn, String newVal){
		
		oparations = inOpp;
		this.table = table;
		this.column = coulmn;
		this.newVal = newVal;
		this.firstKey = firstKey;
		this.secondKey = secondKey;
	}
	
	
	

	//return true if work false if not
	private boolean yagoUpdate(){
		
		//UPDATE
		if(!secondKey.equals("") && !newVal.equals("")){
			UPDATE();
			return true;
		}
		//INSERT
		else if(secondKey.equals("") && !newVal.equals("")){
			INSERT();
			return true;
		}
		//DELETE
		else if(!secondKey.equals("") && newVal.equals("")){
			DELETE();
			return true;
		}
		//ERROR
		else{
			return false;
		}
	}
	
	//user can only insert actor or genre to movie 
	private void INSERT(){
		 if(table == "Actor-Movie"){
			oparations.insert("ActorMovie",firstKey, newVal);
		}else{//Genre-Movie
			oparations.insert("GenreMovie",firstKey, newVal);
		}
	}
	
	//user can only insert actor or genre to movie
	private void DELETE(){
		if(table == "Actor-Movie"){
			oparations.delete("ActorMovie","idMovie = '" + firstKey+ "' idActor = '" + newVal + "'");
		}else{//Genre-Movie
			oparations.delete("GenreMovie","idMovie = '" + firstKey+ "' idGenre = '" + newVal + "'");
		}
	}

	private void UPDATE(){
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
	if(checkExist("Updates", "tableName column firstKey secondKey", "tableName = '" + table + "' column = '" + column + "' firstKey = '" + firstKey + "' secondKey = '" + secondKey+"'"))
	{	
		oparations.update("Updates", "newVal = '" + newVal + "'", "table = '" + table + "'" + " column = '" +  column + "'" + "firstKey = '" + firstKey + "'" + "secondKey = '" + secondKey+ "'");
	}
	else
	{	//INSERT
		oparations.insert("Updates", table ,column , newVal, firstKey,secondKey);
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
