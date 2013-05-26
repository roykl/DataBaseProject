package parsing;

import java.util.HashMap;

public class Parser implements Iparser {

	private HashMap<String, Movie> moviesTable; // key = the movie id (String), value = movie object
	private HashMap<String, Person> actorsTable; // key = actor id (String), value = person object
	private HashMap<String, Person> directorsTable; // key = director id (String), value = person object

	
	// constructor
	public Parser(){
		this.moviesTable = new HashMap<String,Movie>();
		this.actorsTable = new HashMap<String,Person>();
		this.directorsTable = new HashMap<String,Person>();
	}

	@Override
	public void parse(){
		// parse yago file - get all the information from yago
		YagoParser yp = new YagoParser();
		yp.parse();
System.out.print("Finished Yago Parsing");		
		// parse IMDB files - get missing information from imdb (genre, plot, language)
    	IMDBParser imdbPars = new IMDBParser(yp.getMoviesTable()); 
    	
    	// update the movies/actors/directors tables
    	moviesTable = yp.getMoviesTable();
    	actorsTable = yp.getActorsTable();
    	directorsTable = yp.getDirectorsTable();
	}

	
	// getters and setters
	
	@Override
	public HashMap<String, Movie> getMoviesTable() {
		return moviesTable;
	}

	public void setMoviesTable(HashMap<String, Movie> moviesTable) {
		this.moviesTable = moviesTable;
	}

	@Override
	public HashMap<String, Person> getActorsTable() {
		return actorsTable;
	}

	public void setActorsTable(HashMap<String, Person> actorsTable) {
		this.actorsTable = actorsTable;
	}

	@Override
	public HashMap<String, Person> getDirectorsTable() {
		return directorsTable;
	}

	public void setDirectorsTable(HashMap<String, Person> directorsTable) {
		this.directorsTable = directorsTable;
	}

}
