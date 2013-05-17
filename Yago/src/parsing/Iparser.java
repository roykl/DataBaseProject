package parsing;

import java.util.HashMap;




public interface Iparser {

	public HashMap<String, Movie> getMovie(String filePath); 
	public HashMap<String, Person> getActor(String filePath);
	public HashMap<String, Person> getDirector(String filePath);

}
