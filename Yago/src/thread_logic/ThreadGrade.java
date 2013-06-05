package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Configuration;

import db.IdbOparations;

public class ThreadGrade extends Thread {

	IdbOparations oparations ;
	int IDuser;
	int IDmovie;
	int grade;
	String dbName;
	
	public ThreadGrade(IdbOparations inOpp, int inUser, int inMovie, int inGrade){
		
		oparations = inOpp;
		IDuser = inUser;
		IDmovie = inMovie;
		grade = inGrade;
		//set table name
		utils.Configuration settings = new Configuration();
		dbName = settings.getDbName();
	}
	
	//check if user already ranked the movie
	private synchronized boolean checkRank(){
		
		ResultSet result = oparations.select("idUser" ,dbName+".UsersMovies", "idUser = " + IDuser + " AND idMovie = " + IDmovie);
		
		try {
			boolean tmp =  result.next();
			return tmp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	//assumption: In MoviesGrades table movie is unique 
	private synchronized void updateAvarage(int IDuser,int IDmovie, int userGrade){
		
		
		int newGrade, newCount;
		int oldGrade = 0;
		int  oldCount =0;
		int oldUserGrade = 0;
		boolean movieRanked = false;
		boolean userRanked = false;
		//get old number of rankers
		ResultSet result1 = oparations.select("numberOfRankers", dbName+".MoviesGrades", "idMovie = "+ IDmovie);
		//get old grade
		ResultSet result2 = oparations.select("grade", dbName+".MoviesGrades", "idmovie = "+ IDmovie);
		
		//check if movie already ranked by some user
		try {
			//movie already ranked by some user
			if (result1.next() && result2.next()){ 
				oldCount = result1.getInt(1);
				oldGrade = result2.getInt(1);
				movieRanked = true;
				}
			//movie haven't ranked before
			else{ 
				oldCount = 0;
				oldGrade = 0;
				movieRanked = false;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 //check if user ranked the movie before
		
		if(!checkRank()){//user haven't ranked that movie before
			newCount = oldCount + 1;
			newGrade = ((oldCount*oldGrade) + userGrade)/newCount;
			userRanked = false;
			}
		
		
		else{ //user already ranked the movie
			
			//get old user grade 
			ResultSet result3 = oparations.select("rank", dbName+".UsersMovies", "idUser = "+ IDuser + " AND idMovie = "+ IDmovie);
			userRanked = true;
			try {
				if (result3.next())
					oldUserGrade = result3.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			newCount = oldCount;
			newGrade = ((oldCount*oldGrade) - oldUserGrade +userGrade)  /newCount;
			
		}
		
		if(!userRanked && !movieRanked){
			//insert to MoviesGrades
			oparations.insert(dbName+".MoviesGrades", Integer.toString(IDmovie), Integer.toString(newGrade), Integer.toString(newCount) );
			//insert to UsersMovies
			oparations.insert(dbName+".UsersMovies", Integer.toString(IDuser),Integer.toString(IDmovie),Integer.toString(grade));
		} else if(!userRanked && movieRanked){
			//insert to UsersMovies
			oparations.insert(dbName+".UsersMovies", Integer.toString(IDuser),Integer.toString(IDmovie),Integer.toString(grade));
			//update MoviesGrades
			oparations.update(dbName+".MoviesGrades", "grade = " + newGrade + " , numberOfRankers = "+ newCount, "idMovie = " + IDmovie);
		} else if(userRanked && movieRanked){
			oparations.update(dbName+".MoviesGrades", "grade = " + newGrade + " ,numberOfRankers = "+ newCount, "idMovie = " + IDmovie);
			//update UsersMovies
			oparations.update(dbName+".UsersMovies", "rank = " + newGrade, "idUser = " + IDuser + " AND idMovie" + IDmovie);
		}
	}
	
	
	private synchronized void grade(){
	
		updateAvarage(IDuser, IDmovie, grade);		
		
		
		
	}
	
	public synchronized void run(){
		
		this.grade();
		
	}
	
	
}
