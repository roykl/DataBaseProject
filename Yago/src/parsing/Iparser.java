package parsing;

import java.util.HashMap;

/**
 * an Interface to link between the DB layer
 * and the Parsing layer
 */
public interface Iparser {

	public void parse();
	
	public HashMap<String, Movie> getMoviesTable(); 
	public HashMap<String, Person> getActorsTable();
	public HashMap<String, Person> getDirectorsTable();

}
