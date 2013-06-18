package runnableLogic;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.IdbOparations;
// UPDATE - all fields are full 
// INSERT - key2 = 0 - only to ActorMovie, GenreMovie
// DELETE - newVal = 0 - only to ActorMovie, GenreMovie


public class UserUpdate implements Runnable {

	IdbOparations oparations ;
	String table;
	int firstKey; //idMovie
	int secondKey;//idActor|idGenre
	int newVal;
	String columnName;
	int value;
	int opp; 

	public static final int OK = 1;
	public static final int NOT_EXIST = 2;
	public static final int ERR = 0;		
	public static final int INSERT = 3;	
	public static final int UPDATE = 4;	
	public static final int DELETE = 5;	
	// Constructor
	public UserUpdate(IdbOparations inOpp, String table, int firstKey,int secondKey, String coulmn, int newVal){

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
		if(table.equals("Movie")|| (secondKey != 0 && newVal != 0)){
			opp = UPDATE;
			return UPDATE();
		}
		//INSERT
		else if(secondKey == 0 && newVal!=0){
			opp = INSERT;
			return INSERT();
		}
		//DELETE
		else if(secondKey !=0 && newVal == 0){
			opp = DELETE;
			return DELETE();
		}
		//ERROR
		else{
			return ERR;
		}
	}


	//user can only insert actor or genre to movie 
	private int INSERT(){
		if(table.equals("ActorMovie")){
			return oparations.insert(table,Integer.toString(firstKey), Integer.toString(newVal));
		}else{//Genre-Movie
			return oparations.insert(table,Integer.toString(firstKey), Integer.toString(newVal));
		}
	}

	//user can only insert actor or genre to movie
	private int DELETE(){
		System.out.println("im in DELETE in user update");
		if(table.equals("ActorMovie")){
			return oparations.delete(table,"idMovie = " + firstKey+ " AND "+" idActor = " + secondKey);
		}else if (table.equals("GenreMovie")){//Genre-Movie
			return oparations.delete(table,"idMovie = " + firstKey+ " AND "+" idGenre = " + secondKey);
		}
		return ERR;
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

		retVal += oparations.delete("Updates", "tableName = '" + table + "' AND  columnName = '" + columnName + "' AND  firstKey = " + firstKey + " AND secondKey = " + secondKey);
		retVal += oparations.insert("Updates", "'"+table+"'" ,"'"+columnName+"'" , Integer.toString(newVal),Integer.toString(firstKey), Integer.toString(secondKey));
		return (retVal == 2) ? OK : ERR ; 
	}


	private int userUpdate() {
		int retVal = 0;
		setOpp();
		int tmpValidUpdate = validUpdate(); ///check valid update


		if (tmpValidUpdate == OK){
			retVal += yagoUpdate();
			retVal += userTableUpdate();
			return (retVal == 2) ? OK : ERR;
		}
		else{
			return tmpValidUpdate;
		}
	}

	private void setOpp(){
		//UPDATE
		if(table.equals("Movie")||(secondKey != 0 && newVal != 0)){
			opp = UPDATE;

		}
		//INSERT
		else if(secondKey == 0 && newVal!=0){
			opp = INSERT;

		}
		//DELETE
		else if(secondKey !=0 && newVal == 0){
			opp = DELETE;

		}


	}
	//check if update is valid - the newVal has to be exist in the system
	private int validUpdate(){
		System.out.println("im in valid update column name: " + columnName + opp);
		if( opp == UPDATE){ //check if exists in the system

			if(columnName.equals("idLanguage")) 
				return checkExist("idLanguage", "Language" ,"idLanguage = " + newVal);
			else if(columnName.equals("idDirector"))
				return checkExist("idDirector", "Director" ,"idDirector = " + newVal);
			else if(columnName.equals("idActor"))
				return checkExist("idActor", "Actor" ,"idActor = " + newVal);
			else if(columnName.equals("idGenre"))	{
				//				System.out.println(checkExist("idGenre", "Genre" ,"idGenre = " + newVal));
				return checkExist("idGenre", "Genre" ,"idGenre = " + newVal);

			}
			else
				return OK;
		}//check if exists  
		else if(opp == DELETE){

			if(columnName.equals("idActor"))
				return checkExist("idActor", "ActorMovie" ,"idMovie = " + firstKey+ " AND "+" idActor = " + secondKey);
			else if(columnName.equals("idGenre"))	{
				return checkExist("idGenre", "GenreMovie" ,"idMovie = " + firstKey+ " AND "+" idGenre = " + secondKey);
			}
		}
			else if(opp == INSERT){
				int retVal = 0;
				if(columnName.equals("idActor")){
					retVal += checkExist("idActor", "Actor" ,"idActor = " + newVal);
					retVal += 1 - checkExist("idActor", "ActorMovie" ,"idMovie = " + firstKey+ " AND "+" idActor = " + newVal);
				}
				else if(columnName.equals("idGenre"))	{
					retVal += checkExist("idGenre", "Genre" ,"idGenre = " + newVal);
					retVal += 1 - checkExist("idGenre", "GenreMovie" ,"idMovie = " + firstKey+ " AND "+" idGenre = " + newVal);
				}
				
				return (retVal == 2) ? OK:ERR ;

			}


			return ERR; 






		

	}
	private int checkExist(String select, String from, String where ){
		int retVal;
		ResultSet result = oparations.select(select ,from, where);

		try {

			retVal = result.next() ? OK : ERR ;
			result.close();
			return retVal;
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
		return;
	}






}
