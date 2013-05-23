package parsing;

import java.util.HashMap;

public class Parser implements Iparser {

	private HashMap<String, Movie> moviesTable;
	private HashMap<String, Person> actorsTable;
	private HashMap<String, Person> directorsTable;

	public Parser(){
		this.moviesTable = new HashMap<String,Movie>();
		this.actorsTable = new HashMap<String,Person>();
		this.directorsTable = new HashMap<String,Person>();
	}

	@Override
	public void parse(){
		YagoParser yp = new YagoParser();
		yp.parse();
    	IMDBParser imdbPars = new IMDBParser(yp.getMoviesTable()); 
    	moviesTable = yp.getMoviesTable();
    	actorsTable = yp.getActorsTable();
    	directorsTable = yp.getDirectorsTable();
	}


	public HashMap<String, Movie> getMoviesTable() {
		return moviesTable;
	}

	public void setMoviesTable(HashMap<String, Movie> moviesTable) {
		this.moviesTable = moviesTable;
	}

	public HashMap<String, Person> getActorsTable() {
		return actorsTable;
	}

	public void setActorsTable(HashMap<String, Person> actorsTable) {
		this.actorsTable = actorsTable;
	}

	public HashMap<String, Person> getDirectorsTable() {
		return directorsTable;
	}

	public void setDirectorsTable(HashMap<String, Person> directorsTable) {
		this.directorsTable = directorsTable;
	}

}
