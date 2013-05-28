package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.IdbOparations;

public class ThreadGrade extends Thread {

	IdbOparations oparations ;
	int IDuser;
	int IDmovie;
	int grade;
	
	
	public ThreadGrade(IdbOparations inOpp, int inUser, int inMovie, int inGrade){
		
		oparations = inOpp;
		IDuser = inUser;
		IDmovie = inMovie;
		grade = inGrade;
	}
	
	//check if user already ranked the movie
	private boolean checkRank(){
		
		ResultSet result = oparations.select("idUser" ,"UsersMovies", "idUser = " + IDuser + " idMovie = " + IDmovie);
		
		try {
			return result.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	//assumption: In MoviesGrades table movie is unique 
	private void updateAvarage(int IDuser,int IDmovie, int userGrade){
		
		
		int newGrade, newCount;
		int oldGrade = 0;
		int  oldCount =0;
		int oldUserGrade = 0;
		
		//get old number of rankers
		ResultSet result1 = oparations.select("numberOfRankers", "MoviesGrades", "idMovie = "+ IDmovie);
		//get old grade
		ResultSet result2 = oparations.select("grade", "MoviesGrades", "idmovie = "+ IDmovie);
		//get old user grade 
		ResultSet result3 = oparations.select("rank", "UserMovies", "idUser = "+ IDuser + " idMovie = "+ IDmovie);
		
		try {
			oldCount = result1.getInt(1);
			oldGrade = result2.getInt(1);
			oldUserGrade = result3.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!checkRank()){//not ranked yet
		newCount = oldCount++;
		newGrade = ((oldCount*oldGrade) + userGrade)/newCount;
		}
		
		else{//if ranked
			newCount = oldCount;
			newGrade = ((oldCount*oldGrade) - oldUserGrade +userGrade)  /newCount;
		}
		
		oparations.insert("MoviesGrades", Integer.toString(IDmovie), Integer.toString(newGrade), Integer.toString(newCount) );
		
		
	}
	
	
	private void grade(){
		//if not ranked yet
		if(!checkRank()){
			//update user-movie table
			oparations.insert("UsersMovies", Integer.toString(IDuser), Integer.toString(IDmovie), Integer.toString(grade));
		}
		
		//update movie-grade
		updateAvarage(IDuser, IDmovie, grade);		
		
		
		
	}
	
	public void run(){
		
		this.grade();
		
	}
	
	
}
