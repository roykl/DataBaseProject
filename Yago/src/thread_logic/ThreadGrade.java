package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Configuration;

import db.IdbOparations;

public class ThreadGrade extends Thread {

	IdbOparations oparations ;
	private int IDuser;
	private int IDmovie;
	private int grade;
	private int value; // returned value
	private static final int OK = 1;
	private static final int ERR = 0;
	
	public ThreadGrade(IdbOparations inOpp, int inUser, int inMovie, int inGrade){	
		oparations = inOpp;
		IDuser = inUser;
		IDmovie = inMovie;
		grade = inGrade;
	}

	//check if user already ranked the movie
	private  boolean checkRank(){
		boolean retVal;
		ResultSet result = oparations.select("idUser" , "UsersMovies", "idUser = " + IDuser + " AND idMovie = " + IDmovie);		
		try {
			retVal = result.next();
			result.close();
			return retVal;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	//assumption: In MoviesGrades table movie is unique 
	private  int updateAvarage(){
		
		int retVal = 0;
		double newGrade;
		int newCount;
		double oldGrade = 0;
		int  oldCount =0;
		int oldUserGrade = 0;
		boolean movieRanked = false;
		boolean userRanked = false;

		//get old number of rankers
		ResultSet result1 = oparations.select("numberOfRankers", "MoviesGrades", "idMovie = "+ IDmovie);
		//get old grade of the movie
		ResultSet result2 = oparations.select("grade", "MoviesGrades", "idmovie = "+ IDmovie);

		//check if movie already ranked by some user
		try {
			//movie already ranked by some user
			if (result1.next() && result2.next()){ 
				oldCount = result1.getInt(1);
				oldGrade = result2.getDouble(1);
				movieRanked = true;
			}
			//movie hasn't been ranked before
			else{ 
				oldCount = 0;
				oldGrade = 0;
				movieRanked = false;
			}
			
			result1.close();
			result2.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERR;
		}

		//check if user ranked the movie before

		if(!checkRank()){ //user hasn't ranked that movie before
			newCount = oldCount + 1;
			newGrade = ((oldCount*oldGrade) + grade)/newCount;
			userRanked = false;
		}
		else{ //user already ranked the movie			
			//get old user grade 
			ResultSet result3 = oparations.select("rank", "UsersMovies", "idUser = "+ IDuser + " AND idMovie = "+ IDmovie);
			userRanked = true;
			try {
				if (result3.next())
					oldUserGrade = result3.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ERR;
			}

			newCount = oldCount;
			newGrade = ((oldCount*oldGrade) - oldUserGrade +grade)  /newCount;

		}
		
		//if (retVal == 2) -> (function succeed) else (error)
		if(!userRanked && !movieRanked){
			//insert to MoviesGrades
			retVal += oparations.insert("MoviesGrades", Integer.toString(IDmovie), Double.toString(newGrade), Integer.toString(newCount) );
			//insert to UsersMovies
			retVal += oparations.insert("UsersMovies", Integer.toString(IDuser),Integer.toString(IDmovie),Integer.toString(grade));
		} else if(!userRanked && movieRanked){
			//insert to UsersMovies
			retVal += oparations.insert("UsersMovies", Integer.toString(IDuser),Integer.toString(IDmovie),Integer.toString(grade));
			//update MoviesGrades
			retVal += oparations.update("MoviesGrades", "grade = " + newGrade + " , numberOfRankers = "+ newCount, "idMovie = " + IDmovie);
		} else if(userRanked && movieRanked){
			retVal += oparations.update("MoviesGrades", "grade = " + newGrade + " ,numberOfRankers = "+ newCount, "idMovie = " + IDmovie);
			//update UsersMovies
			retVal += oparations.update("UsersMovies", "rank = " + grade, "idUser = " + IDuser + " AND idMovie" + IDmovie);
		}
		if(retVal == 2)
			return OK;
		
		return ERR;
	}


	private  int grade(){
		return updateAvarage();		
	}

	public int getValue() {
		return value;
	}

	//public setV

	public void run(){
		value = this.grade(); 	
		return;
	}

}
