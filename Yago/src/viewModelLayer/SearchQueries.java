package viewModelLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableItem;

public class SearchQueries {

	public static final String MOVIE_SELECT = "movie.idMovie, movieName, movie.idLanguage, LanguageName, movie.idDirector, directorName, year, wiki," +
			"duration, plot, grade, numberOfRankers";

	public static final String MOVIE_FROM = "movie LEFT OUTER JOIN language ON " +
			"movie.idLanguage = language.idLanguage\n LEFT OUTER JOIN  director ON  "+
			"movie.idDirector = director.idDirector\n LEFT OUTER JOIN moviesgrades ON "+
			"movie.idMovie = moviesgrades.idMovie";

	public static final String GENRES_SELECT ="genremovie.idMovie, genremovie.idGenre, genre.genreName";

	public static final String GENRES_FROM ="movie, genremovie,genre";

	public static final String ACTORS_SELECT = "actormovie.idMovie, actormovie.idActor, actor.actorName";

	public static final String ACTORS_FROM = "movie, actormovie, actor";

	public String whereMovie;
	public String whereGenre;
	public String whereActor;
	public String selectProp = "DISTINCT movie.idMovie";
	public String fromProp = "movie";
	public String whereProp = "";
	public boolean preformSearch = true;

	public SearchQueries(){
		
	}
	
	public  ArrayList<String> createGenreWhere(TableItem[] genres){
		ArrayList<String> genreList = new ArrayList<String>();
		for (TableItem ti: genres){
			if(ti.getChecked())
				genreList.add(ti.getText());
		}
		System.out.println(genreList);
		return genreList;
	}


	public  boolean createWheres(String movieName){
		boolean enteredMoive = movieName.trim().isEmpty()? false : true;
		if (enteredMoive){ // if entered a movie, find that movie
			whereMovie = "movie.movieName = '" + movieName +"'";
			whereGenre = "movie.movieName = '" + movieName +"'" +
					" AND genremovie.idMovie = movie.idMovie AND genremovie.idGenre = genre.idGenre";
			whereActor =  "movie.movieName = '" + movieName +"'" +
					" AND actormovie.idMovie = movie.idMovie AND actormovie.idActor = actor.idactor";

			System.out.println("user entered movie name!!!!");
			return true;
		}
		else return false;

	}


	public void createWheresFromProperties(TableItem[] genreItems,
			String directorName, String actor1, String actor2, String actor3,
			int yearFrom, int yearTo, String language) {


		boolean isWhereEmpty = true;

		// create genres from and where
		
		System.out.println("about to get genre");
		ArrayList<String> genreList = this.createGenreWhere(genreItems);
		if(genreList.isEmpty() || genreList.size()>3){
			//TODO- we don't ignore the genres
		}
		else{
			if(genreList.size() == 1){
				fromProp +=", genremovie ";
				whereProp += "genremovie.idGenre = " + genreList.get(0).hashCode() +
							" AND genremovie.idMovie = movie.idMovie";
			}
			else if(genreList.size() == 2){
				fromProp +=", genremovie as A, genremovie as B ";
				whereProp += "A.idGenre= " + genreList.get(0).hashCode() + " AND B.idGenre= " +
						genreList.get(1).hashCode() + " AND A.idMovie = B.idMovie AND A.idMovie= movie.idMovie";
			}
			else if(genreList.size() == 3){
				fromProp +=", genremovie as A, genremovie as B , genremovie as C ";
				whereProp += "A.idGenre= " + genreList.get(0).hashCode() + " AND B.idGenre= " +						
						genreList.get(1).hashCode() + " AND C.idGenre= "+ genreList.get(2).hashCode()+
						" AND A.idMovie = B.idMovie AND B.idMovie = C.idMovie AND A.idMovie = C.idMovie  AND A.idMovie= movie.idMovie";
			}
			isWhereEmpty = false;
		}
		
		System.out.println("After genres:");
		System.out.println("FROM- " + fromProp);
		System.out.println("WHERE- " + whereProp);
		

		System.out.println("BEFOR DIRECTOR");
		// director
		if(!directorName.trim().isEmpty()){
			//user entered director name
			fromProp += ", director";
			if(!isWhereEmpty)
				whereProp += " AND ";
			whereProp += "director.directorName = '" + directorName + "' AND director.idDirector =" +
					" movie.idDirector";
			isWhereEmpty = false;
		}
		
		System.out.println("After DIRECTOR:");
		System.out.println("FROM- " + fromProp);
		System.out.println("WHERE- " + whereProp);
		

		System.out.println("BEFOR ACTORS:");
		// actors
		boolean actorExists = false;
		int count = 0;
		if(!actor1.trim().isEmpty() ||  !actor2.trim().isEmpty() || !actor3.trim().isEmpty()){
			fromProp += ", actormovie, actor";
			if(!isWhereEmpty)
				whereProp += " AND ";
			whereProp += "(";

			if(!actor1.trim().isEmpty())
				count++;
			if(!actor2.trim().isEmpty())
				count++;
			if(!actor3.trim().isEmpty())
				count++;

			isWhereEmpty = false;
			actorExists = true;
		}

		if(!actor1.trim().isEmpty()){
			whereProp += "actor.actorName= '" + actor1 + "'";
			if(count >1){
				count--;
				whereProp += " OR ";
			}
		}

		if(!actor2.trim().isEmpty()){
			whereProp += "actor.actorName= '" + actor2 + "'";	
			if(count >1){
				count--;
				whereProp += " OR ";
			}
		}
		if(!actor3.trim().isEmpty()){
			whereProp += "actor.actorName= '" + actor3 + "'";	
			if(count >1){
				count--;
				whereProp += " OR ";
			}
		}
		if(actorExists)
			whereProp +=  ") AND actormovie.idActor= actor.idActor " +
					" AND actormovie.idMovie = movie.idMovie ";


		
		System.out.println("After ACTORS:");
		System.out.println("FROM- " + fromProp);
		System.out.println("WHERE- " + whereProp);
		
		

		System.out.println("Before YEAR: " + yearFrom);
		// year
		if(InputVerifier.validateYear(yearFrom, yearTo)){
			if(!isWhereEmpty)
				whereProp += " AND ";
			whereProp += "movie.year >= "+ yearFrom + " AND movie.year <= " + yearTo;			
			isWhereEmpty = false;
		}

		System.out.println("AFTER YEAR:");
		System.out.println("FROM- " + fromProp);
		System.out.println("WHERE- " + whereProp);
		

		System.out.println("Before LANGUAGE:");
		
		// language
		if(language != null){
			if(!isWhereEmpty)
				whereProp += " AND ";
			fromProp += ", language";
			whereProp += " language.LanguageName = '" + language + "' AND language.idLanguage = movie.idLanguage";
			
			isWhereEmpty = false;
		}
		
		if(isWhereEmpty){
			//lets find movies with best grade
			fromProp += ", moviesgrades";
			whereProp += "movie.idMovie = moviesgrades.idMovie ORDER BY grade desc, year desc";			
		}
		else{
		  whereProp += " ORDER BY year desc LIMIT 0, 40 ";
		}
		
		System.out.println("AFTER LANGUAGE:");
		System.out.println("FROM- " + fromProp);
		System.out.println("WHERE- " + whereProp);


	}




	public void createFromMoviesIds(ResultSet moviesIds) {
		boolean isFirst = true;
		try {			
			System.out.println("I'm in createFromMoviesIds");
			while(moviesIds.next()){
				if(isFirst){				
					whereMovie = "movie.idMovie = " + moviesIds.getInt(1);
					whereGenre =  "(movie.idMovie = "  + moviesIds.getInt(1); 
					whereActor =  "(movie.idMovie = "  + moviesIds.getInt(1);
					isFirst = false;
				}
				else{
				whereMovie += " OR movie.idMovie = " + moviesIds.getInt(1);
				whereGenre +=  " OR movie.idMovie = "  + moviesIds.getInt(1);
				whereActor +=  " OR movie.idMovie = "  + moviesIds.getInt(1);
				}
			}
			whereGenre += ") AND genremovie.idMovie = movie.idMovie AND genremovie.idGenre = genre.idGenre ";
			whereActor += ") AND actormovie.idMovie = movie.idMovie AND actormovie.idActor = actor.idActor ";
			
			preformSearch = !isFirst;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				moviesIds.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}








}