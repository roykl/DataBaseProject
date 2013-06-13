package viewModelLayer;

public class SearchQueries {
	
	public static final String MOVIE_SELECT = "movie.idMovie, movieName, movie.idLanguage, LanguageName, movie.idDirector, directorName, year, wiki," +
			"duration, plot, grade, numberOfRankers";
	
	public static final String MOVIE_FROM = "movie LEFT OUTER JOIN language ON " +
			"movie.idLanguage = language.idLanguage\n LEFT OUTER JOIN  director ON  "+
			"movie.idDirector = director.idDirector\n LEFT OUTER JOIN moviesgrades ON "+
			"movie.idMovie = moviesgrades.idMovie";
}
