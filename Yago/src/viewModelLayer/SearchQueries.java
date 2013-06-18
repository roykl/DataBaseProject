package viewModelLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableItem;

/**
 * Create the search queries for the DB
 * after user press the search button
 * most of the search queries depends on 
 * the user input
 */
public class SearchQueries {

	public static final String MOVIE_SELECT = "DISTINCT Movie.idMovie, movieName, Movie.idLanguage, LanguageName, Movie.idDirector, directorName, year, wiki," +
			"duration, plot, grade, numberOfRankers";

	public static final String MOVIE_FROM = "Movie LEFT OUTER JOIN Language ON " +
			"Movie.idLanguage = Language.idLanguage\n LEFT OUTER JOIN  Director ON  "+
			"Movie.idDirector = Director.idDirector\n LEFT OUTER JOIN MoviesGrades ON "+
			"Movie.idMovie = MoviesGrades.idMovie";

	public static final String GENRES_SELECT ="GenreMovie.idMovie, GenreMovie.idGenre, Genre.genreName";

	public static final String GENRES_FROM ="Movie, GenreMovie,Genre";

	public static final String ACTORS_SELECT = "ActorMovie.idMovie, ActorMovie.idActor, Actor.actorName";

	public static final String ACTORS_FROM = "Movie, ActorMovie, Actor";

	public String whereMovie;
	public String whereGenre;
	public String whereActor;
	public String selectProp = "DISTINCT Movie.idMovie";
	public String fromProp = "Movie";
	public String whereProp = "";
	public boolean preformSearch = true;

	public SearchQueries(){		
	}
	
	/** create the where with the genres get fron the user input*/
	public ArrayList<String> createGenreWhere(TableItem[] genres){
		ArrayList<String> genreList = new ArrayList<String>();
		for (TableItem ti: genres){
			if(ti.getChecked())
				genreList.add(ti.getText());
		}
		System.out.println(genreList);
		return genreList;
	}

	/**create WHERE with the movie name */
	public boolean createWheres(String movieName){
		boolean enteredMoive = movieName.trim().isEmpty()? false : true;
		if (enteredMoive){ // if entered a movie, find that movie
			whereMovie = "Movie.movieName LIKE '%" + movieName +"%'";
			whereGenre = "Movie.movieName LIKE '%" + movieName +"%'" +
					" AND GenreMovie.idMovie = Movie.idMovie AND GenreMovie.idGenre = Genre.idGenre";
			whereActor =  "Movie.movieName LIKE '%" + movieName +"%'" +
					" AND ActorMovie.idMovie = Movie.idMovie AND ActorMovie.idActor = Actor.idactor";

			return true;
		}
		else return false;

	}

	/**if the user didn't search by movie name, we create the "WHERE" with
	 * the properties he entered
	 */
	public void createWheresFromProperties(TableItem[] genreItems,
			String directorName, String actor1, String actor2, String actor3,
			int yearFrom, int yearTo, String language) {


		boolean isWhereEmpty = true;

		// create genres from and where
		
//		System.out.println("about to get genre");
		ArrayList<String> genreList = this.createGenreWhere(genreItems);
		if(genreList.isEmpty() || genreList.size()>3){
			//TODO- we don't ignore the genres
		}
		else{
			if(genreList.size() == 1){
				fromProp +=", GenreMovie ";
				whereProp += "GenreMovie.idGenre = " + genreList.get(0).hashCode() +
							" AND GenreMovie.idMovie = Movie.idMovie";
			}
			else if(genreList.size() == 2){
				fromProp +=", GenreMovie as A, GenreMovie as B ";
				whereProp += "A.idGenre= " + genreList.get(0).hashCode() + " AND B.idGenre= " +
						genreList.get(1).hashCode() + " AND A.idMovie = B.idMovie AND A.idMovie= Movie.idMovie";
			}
			else if(genreList.size() == 3){
				fromProp +=", GenreMovie as A, GenreMovie as B , GenreMovie as C ";
				whereProp += "A.idGenre= " + genreList.get(0).hashCode() + " AND B.idGenre= " +						
						genreList.get(1).hashCode() + " AND C.idGenre= "+ genreList.get(2).hashCode()+
						" AND A.idMovie = B.idMovie AND B.idMovie = C.idMovie AND A.idMovie = C.idMovie  AND A.idMovie= Movie.idMovie";
			}
			isWhereEmpty = false;
		}
		
//		System.out.println("After genres:");
//		System.out.println("FROM- " + fromProp);
//		System.out.println("WHERE- " + whereProp);
		

//		System.out.println("BEFOR DIRECTOR");
		// director
		if(!directorName.trim().isEmpty()){
			//user entered director name
			fromProp += ", Director";
			if(!isWhereEmpty)
				whereProp += " AND ";
			whereProp += "Director.directorName = '" + directorName + "' AND Director.idDirector =" +
					" Movie.idDirector";
			isWhereEmpty = false;
		}
		
//		System.out.println("After DIRECTOR:");
//		System.out.println("FROM- " + fromProp);
//		System.out.println("WHERE- " + whereProp);
		

//		System.out.println("BEFOR ACTORS:");
		// actors
		boolean actorExists = false;
		int count = 0;
		if(!actor1.trim().isEmpty() ||  !actor2.trim().isEmpty() || !actor3.trim().isEmpty()){
			fromProp += ", ActorMovie, Actor";
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
			whereProp += "Actor.actorName= '" + actor1 + "'";
			if(count >1){
				count--;
				whereProp += " OR ";
			}
		}

		if(!actor2.trim().isEmpty()){
			whereProp += "Actor.actorName= '" + actor2 + "'";	
			if(count >1){
				count--;
				whereProp += " OR ";
			}
		}
		if(!actor3.trim().isEmpty()){
			whereProp += "Actor.actorName= '" + actor3 + "'";	
			if(count >1){
				count--;
				whereProp += " OR ";
			}
		}
		if(actorExists)
			whereProp +=  ") AND ActorMovie.idActor= Actor.idActor " +
					" AND ActorMovie.idMovie = Movie.idMovie ";


		
//		System.out.println("After ACTORS:");
//		System.out.println("FROM- " + fromProp);
//		System.out.println("WHERE- " + whereProp);
		
		

//		System.out.println("Before YEAR: " + yearFrom);
		// year
		if(InputVerifier.validateYear(yearFrom, yearTo)){
			if(!isWhereEmpty)
				whereProp += " AND ";
			whereProp += "Movie.year >= "+ yearFrom + " AND Movie.year <= " + yearTo;			
			isWhereEmpty = false;
		}

		System.out.println("AFTER YEAR:");
		System.out.println("FROM- " + fromProp);
		System.out.println("WHERE- " + whereProp);
		

	//	System.out.println("Before LANGUAGE:");
		
		// language
		if(language != null){
			if(!isWhereEmpty)
				whereProp += " AND ";
			fromProp += ", Language";
			whereProp += " Language.LanguageName = '" + language + "' AND Language.idLanguage = Movie.idLanguage";
			
			isWhereEmpty = false;
		}
		
		if(isWhereEmpty){
			//lets find movies with best grade
			fromProp += ", MoviesGrades";
			whereProp += "Movie.idMovie = MoviesGrades.idMovie ORDER BY grade desc, year desc";			
		}
		else{
		  whereProp += " ORDER BY year desc LIMIT 0, 40 ";
		}
	
//		System.out.println("AFTER LANGUAGE:");
//		System.out.println("FROM- " + fromProp);
//		System.out.println("WHERE- " + whereProp);


	}

	/**if many movies returned from db, we create the FROM here
	 * in order to get all the details for those movies */
	public void createFromMoviesIds(ResultSet moviesIds) {
		boolean isFirst = true;
		try {			
			System.out.println("I'm in createFromMoviesIds");
			while(moviesIds.next()){
				if(isFirst){				
					whereMovie = "Movie.idMovie = " + moviesIds.getInt(1);
					whereGenre =  "(Movie.idMovie = "  + moviesIds.getInt(1); 
					whereActor =  "(Movie.idMovie = "  + moviesIds.getInt(1);
					isFirst = false;
				}
				else{
				whereMovie += " OR Movie.idMovie = " + moviesIds.getInt(1);
				whereGenre +=  " OR Movie.idMovie = "  + moviesIds.getInt(1);
				whereActor +=  " OR Movie.idMovie = "  + moviesIds.getInt(1);
				}
			}
			whereGenre += ") AND GenreMovie.idMovie = Movie.idMovie AND GenreMovie.idGenre = Genre.idGenre ";
			whereActor += ") AND ActorMovie.idMovie = Movie.idMovie AND ActorMovie.idActor = Actor.idActor ";
			
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