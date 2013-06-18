package viewModelLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class get many result sets from the DB
 * And with the methods in it creates moviesResult list
 * of MovieInfo.
 */
public class MoviesResults {

	ArrayList<MovieInfo> moviesResult; 
	
	/** constructor **/
	public MoviesResults(){
		this.moviesResult = new ArrayList<MovieInfo>();
	}

	/** add information about the movie (still no info from the many-to-many table) */
	public void setResultsMoive(ResultSet results){
		try {
			while(results.next()){				
				MovieInfo m = new MovieInfo();
				m.idMovie = results.getInt(1);
				m.movieName = results.getString(2);
				m.idLanguage = results.getInt(3);
				m.language = results.getString(4);
				m.idDirector = results.getInt(5);
				m.directorName = results.getString(6);
				m.year = results.getString(7);
				m.wikiUrl = results.getString(8);
				m.duration = results.getString(9);
				m.plot = results.getString(10);
				m.grade = results.getDouble(11);
				m.numRankers = results.getInt(12);
				m.actorsList = new HashMap<Integer, String>();
				m.genresList = new HashMap<Integer, String>();

				moviesResult.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				results.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	/** add information about the actors playing in that movie */
	public void setResultsActors(ResultSet results){
		try {
			while (results.next()){
				int idMovie = results.getInt(1);
				MovieInfo m = getMovieInfo(idMovie);
				if (m == null)
					return;
				m.actorsList.put(results.getInt(2), results.getString(3));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		finally{
			try {
				results.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** add information about the actors playing in that movie */
	private MovieInfo getMovieInfo(int idMovie) {
		for(MovieInfo m : moviesResult){
			if (m.idMovie == idMovie){
				return m;
			}
		}
		return null;
	}

	/** add information about the Genres of that movie */
	public void setResultsGenre(ResultSet results){
		try {
			while (results.next()){
				int idMovie = results.getInt(1);
				MovieInfo m = getMovieInfo(idMovie);
				if (m == null)
					return;
				m.genresList.put(results.getInt(2), results.getString(3));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		finally{
			try {
				results.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** add youtube and url poster */
	public void addYoutubeAndPoster(){
		for(MovieInfo m: moviesResult){
			m.addYoutubeUrl(m.movieName, m.year);
			m.addPosterUrl(m.movieName);
		}
	}

	/** getter */
	public ArrayList<MovieInfo> getMoviesResult(){
		return moviesResult;
	}

}


