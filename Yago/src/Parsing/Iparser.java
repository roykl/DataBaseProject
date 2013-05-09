package Parsing;

import java.util.List;



public interface Iparser {

	public List<Movie> getMovie(String filePath); 
	public List<Person> getActor(String filePath);
	public List<Person> getDirector(String filePath);

}
