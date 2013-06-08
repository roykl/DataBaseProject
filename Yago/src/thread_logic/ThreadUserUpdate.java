package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	int value;
	
	public static final int OK = 1;
	public static final int NOT_EXIST = 2;
	public static final int ERR = 0;		
	
	// Constructor
	public ThreadUserUpdate(IdbOparations inOpp, String table, int firstKey,int secondKey, String coulmn, int newVal){
		oparations = inOpp;
		this.table =  table;
		this.columnName = coulmn;
		this.newVal = newVal;
		this.firstKey = firstKey;
		this.secondKey = secondKey;
		
	}
	
	
	///////////////////////////////////  Yago update  ////////////////////////////////////////

	//return true if work false if not
	private int yagoUpdate(){
		
		//UPDATE
		if(secondKey != 0 && newVal != 0){
			return UPDATE();
		}
		//INSERT
		else if(secondKey == 0 && newVal!=0){
			return INSERT();
		}
		//DELETE
		else if(secondKey !=0 && newVal == 0){
			return DELETE();
		}
		//ERROR
		else{
			return ERR;
		}
	}
	
	
	//user can only insert actor or genre to movie 
	private int INSERT(){
		 if(table.equals("Actor-Movie")){
			return oparations.insert(table,Integer.toString(firstKey), Integer.toString(newVal));
		}else{//Genre-Movie
			return oparations.insert(table,Integer.toString(firstKey), Integer.toString(newVal));
		}
	}
	
	//user can only insert actor or genre to movie
	private int DELETE(){
		if(table.equals("Actor-Movie")){
			return oparations.delete(table,"idMovie = " + firstKey+ " idActor = " + newVal);
		}else{//Genre-Movie
			return oparations.delete(table,"idMovie = " + firstKey+ " idGenre = " + newVal);
		}
	}

	private int UPDATE(){
		if(table.equals("Movie")){
			return oparations.update(table, columnName + " = " + newVal   , "idMovie" + "= " + firstKey);
		}
		else if(table.equals("ActorMovie")){
			return oparations.update(table, columnName +  " = "  + newVal   , "idMovie = " + firstKey + " AND " +" idActor = " + secondKey);
		}else{//Genre-Movie
			return oparations.update(table, columnName +  " = " + newVal   , "idMovie = " + firstKey+ " AND " + " idGenre = " + secondKey);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	



//Update the 'Updates' table
private int userTableUpdate() {
	int retVal = 0;
	
	retVal =+ oparations.delete("Updates", "tableName = '" + table + "' AND  columnName = '" + columnName + "' AND  firstKey = " + firstKey + " AND secondKey = " + secondKey);
	retVal =+ oparations.insert("Updates", "'"+table+"'" ,"'"+columnName+"'" , Integer.toString(newVal),Integer.toString(firstKey), Integer.toString(secondKey));
	return (retVal == 2) ? OK : ERR ; 
}


private int userUpdate() {
	int retVal = 0;
	int tmpValidUpdate = validUpdate(); ///check valid update
	
	
	if (tmpValidUpdate == OK){
		retVal =+ yagoUpdate();
		retVal =+ userTableUpdate();
	return (retVal == 2) ? OK : ERR;
	}
	else{
		return tmpValidUpdate;
	}
}

//check if update is valid - the newVal has to be exist in the system
private int validUpdate(){
	
	switch (columnName) {
		
	case "idLanguage" :
		return checkExist("idLanguage", "Language" ,"idLanguage = " + newVal);

	case "idDirector" :
		return checkExist("idDirector", "Director" ,"idDirector = " + newVal);
	
	case "idActor" :
		return checkExist("idActor", "Actor" ,"idActor = " + newVal);
		
	case "idGenre" :
		return checkExist("idGenre", "Genre" ,"idGenre = " + newVal);
	
	default :
		return OK;
		
	}
	
}


private int checkExist(String select, String from, String where ){
	
	ResultSet result = oparations.select(select ,from, where);
	
	try {
		return result.next() ? OK : NOT_EXIST ;
	} catch (SQLException e) {
		e.printStackTrace();
		return ERR;
	}
}


public int getValue() {
	return value;
}

//public setV

public void run(){
	value = userUpdate();
}





	
}
