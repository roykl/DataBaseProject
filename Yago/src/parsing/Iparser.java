package parsing;

import java.util.HashMap;


public interface Iparser {

	public void parse();
	public HashMap<String, Movie> getMoviesTable(); 
	public HashMap<String, Person> getActorsTable();
	public HashMap<String, Person> getDirectorsTable();

}
