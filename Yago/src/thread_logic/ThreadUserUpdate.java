
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
	String dbName;
	
	public ThreadUserUpdate(IdbOparations inOpp, String table, int firstKey,int secondKey, String coulmn, int newVal){
		
		//set table name
		utils.Configuration settings = new Configuration();
		dbName = settings.getDbName();
		
		oparations = inOpp;
		this.table = dbName + "." + table;
		this.columnName = coulmn;
		this.newVal = newVal;
		this.firstKey = firstKey;
		this.secondKey = secondKey;
		
	}
	
	
	

	//return true if work false if not
	private boolean yagoUpdate(){
		
		//UPDATE
		if(secondKey != 0 && newVal != 0){
			UPDATE();
			return true;
		}
		//INSERT
		else if(secondKey == 0 && newVal!=0){
			INSERT();
			return true;
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
	private void INSERT(){
		 if(table.equals(dbName+".Actor-Movie")){
			oparations.insert(table,Integer.toString(firstKey), Integer.toString(newVal));
		}else{//Genre-Movie
			oparations.insert(table,Integer.toString(firstKey), Integer.toString(newVal));
		}
	}
	
	//user can only insert actor or genre to movie
	private void DELETE(){
		if(table.equals(dbName + ".Actor-Movie")){
			oparations.delete(table,"idMovie = " + firstKey+ " idActor = " + newVal);
		}else{//Genre-Movie
			oparations.delete(table,"idMovie = " + firstKey+ " idGenre = " + newVal);
		}
	}

	private void UPDATE(){
		if(table.equals(dbName+ ".Movie")){
			oparations.update(table, columnName + " = " + newVal   , "idMovie" + "= " + firstKey);
		}
		else if(table.equals(dbName+".ActorMovie")){
			oparations.update(table, columnName +  " = "  + newVal   , "idMovie = " + firstKey + " AND " +" idActor = " + secondKey);
		}else{//Genre-Movie
			oparations.update(table, columnName +  " = " + newVal   , "idMovie = " + firstKey+ " AND " + " idGenre = " + secondKey);
		}
	}
	

//Update the 'Updates' table
private void userTableUpdate() {
	//if value already updated - UPDATE the updates table
	if(checkExist("tableName, columnName, firstKey, secondKey", dbName+".Updates" , "tableName = '" + table + "' AND  columnName = '" + columnName + "' AND  firstKey = " + firstKey + " AND secondKey = " + secondKey))
	{	
		oparations.update(dbName + ".Updates", "newVal = " + newVal , "tableName = '" + table + "' AND " + " columnName = '" +  columnName + "' AND " + "firstKey = " + firstKey + " AND secondKey = " + secondKey);
	}
	else
	{	//INSERT to updates table
//		if(table.equals(dbName + ".Movie") && secondKey.equals(""))
//			secondKey = "-1";
		
		oparations.insert("Updates", "'"+table+"'" ,"'"+columnName+"'" , Integer.toString(newVal),Integer.toString(firstKey), Integer.toString(newVal));
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
