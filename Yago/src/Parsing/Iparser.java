package Parsing;

import java.util.List;



public interface Iparser {

	public List<Movie> getMovie(String fileName);
	public List<Movie> getActor(String fileName);
	public List<Movie> getDirector(String fileName);

}
