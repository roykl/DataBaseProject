package viewModelLayer;

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


	public static void createGenreWhere(TableItem[] genres){
		ArrayList<String> genreList = new ArrayList<String>();
		for (TableItem ti: genres){
			if(ti.getChecked())
				genreList.add(ti.getText());
		}
		System.out.println(genreList);
	}


	public static void createLangaugeWhere(Control[] children) {
		for(Control b : children){
		//	if(b.)
		}
		
	}

}