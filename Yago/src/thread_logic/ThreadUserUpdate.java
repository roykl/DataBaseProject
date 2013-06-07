
//TODO: add DbMysql05
package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Configuration;

import db.IdbOparations;
// UPDATE - all fields are full 
// INSERT - key2 = 0 - only to ActorMovie, GenreMovie
// DELETE - newVal = 0 - only to ActorMovie, GenreMovie


public class ThreadUserUpdate extends Thread {

	IdbOparations oparations ;
	String table;
	int firstKey; //idMovie
	int secondKey;//idActor|idGenre
	int newVal;
	String columnName;
	
	public ThreadUserUpdate(IdbOparations inOpp, String table, int firstKey,int secondKey, String coulmn, int newVal){
		
		oparations = inOpp;
		this.table = table;
		this.columnName = coulmn;
		this.newVal = newVal;
		this.firstKey = firstKey;
		this.secondKey = secondKey;
		
	}
	
	//return true if work false if not
	private boolean yagoUpdate(){
		
		//UPDATE
		if(secondKey != 0 && newVal != 0 || table.equals("Movie")){
			UPDATE();
			return true;
		}
		//INSERT
		else if(secondKey == 0 && newVal!=0){
			return INSERT();
		}
		//DELETE
		else if(secondKey !=0 && newVal == 0){
			DELETE();
			return true;
		}
		//ERROR
		else{
			return false;
		}
	}
	
	//user can only insert actor or genre to movie 
	private boolean INSERT(){
		 if(table.equals("ActorMovie") && !checkExist("*", table , "idMovie = " + firstKey + " AND idActor = " + newVal)){
			oparations.insert(table,Integer.toString(firstKey), Integer.toString(newVal));
			return true;
		}else if(table.equals("GenreMovie") && !checkExist("*", table , "idMovie = " + firstKey + " AND idGenre = " + newVal)){//Genre-Movie
			oparations.insert(table,Integer.toString(firstKey), Integer.toString(newVal));
			return true;
		}
		 return false;
	}
	
	//user can only insert actor or genre to movie
	private void DELETE(){
		if(table.equals("ActorMovie")){
			oparations.delete(table,"idMovie = " + firstKey+ " idActor = " + newVal);
		}else{//Genre-Movie
			oparations.delete(table,"idMovie = " + firstKey+ " idGenre = " + newVal);
		}
	}

	private void UPDATE(){
		if(table.equals("Movie")){
			oparations.update(table, columnName + " = " + newVal   , "idMovie" + "= " + firstKey);
		}
		else if(table.equals("ActorMovie")){
			oparations.update(table, columnName +  " = "  + newVal   , "idMovie = " + firstKey + " AND " +" idActor = " + secondKey);
		}else{//Genre-Movie
			oparations.update(table, columnName +  " = " + newVal   , "idMovie = " + firstKey+ " AND " + " idGenre = " + secondKey);
		}
	}
	

//Update the 'Updates' table
private void userTableUpdate() {
	//if value already updated - UPDATE the updates table
	if(table.equals("Movie") || secondKey !=0) // if we want to do update- put newVal instead of secondKey
	if(checkExist("tableName, columnName, firstKey, secondKey", "Updates" , "tableName = '" + table + "' AND  columnName = '" + columnName + "' AND  firstKey = " + firstKey + " AND secondKey = " + secondKey))
	{	
		oparations.update("Updates", "newVal = " + newVal , "tableName = '" + table + "' AND " + " columnName = '" +  columnName + "' AND " + "firstKey = " + firstKey + " AND secondKey = " + secondKey);
	}
	else
	{	//INSERT to updates table
//		if(table.equals(dbName + ".Movie") && secondKey.equals(""))
//			secondKey = "-1";
		
		oparations.insert("Updates", "'"+table+"'" ,"'"+columnName+"'" , Integer.toString(newVal),Integer.toString(firstKey), Integer.toString(secondKey));
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
