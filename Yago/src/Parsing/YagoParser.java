package Parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class YagoParser {

	final static String YAGO_TYPES_FILE = "yagoSimpleTypes.ttl";
	final static String YAGO_FACTS_FILE = "yagoFacts.ttl";
	final static String ACTED_IN = "actedIn";
	final static String DIRECTED = "directed";
	
	private static List<Movie> moviesLst = new ArrayList<Movie>();
	private static List<Person> actorsLst = new ArrayList<Person>();
	private static List<Person> directorsLst = new ArrayList<Person>();

	//enum to represent the entities: Movie, Actor, Director
	public enum Entity{
		MOVIE("106613686"),
		ACTOR("109765278"),
		DIRECTOR("110088200");

		private String id;

		private Entity(String id){
			this.id = id;
		}

		public String getId(){
			return id;
		}
	}

	/**expect to get the file yagoSimpleTpyes.ttl and populate movieLst, actorLst, directorLst*/
	public static void parseYagoTypes(String path){
		//make sure the path is not null or empty and that it's the correct file
		if (path != null && !path.isEmpty() && new File(path).getName().compareTo(YAGO_TYPES_FILE)== 0){			
			try{
				// create a buffered reader for the current file
				BufferedReader br = new BufferedReader(new FileReader(path));
				String line;
				String[] strArr = new String[3]; //array with 3 cells to parse each line				
				while((line = br.readLine()) != null){ //read line	
					strArr = line.split("\\t"); //split the line by tabs - we know there are 2 tabs in each line				
					if(strArr.length >= 3 && strArr[2].contains(Entity.MOVIE.getId())){
						moviesLst.add(new Movie(strArr[0]));
						continue;
					}
					else if(strArr.length >= 3 && strArr[2].contains(Entity.ACTOR.getId())){
						actorsLst.add(new Person(strArr[0]));
						continue;
					}
					else if(strArr.length >= 3 && strArr[2].contains(Entity.DIRECTOR.getId())){
						directorsLst.add(new Person(strArr[0]));
						continue;
					}
				}
				br.close();
				return;
			} 
			catch(Exception ex){
				//to-do
			}			
		}
		System.out.println("Wrong Path. Please Provide a correct path");
	}

	/**expect to get the file yagoFacts.ttl and update the entities*/
	public static void parseYagoFacts(String path){
		//make sure the path is not null or empty and that it's the correct file
		if (path != null && !path.isEmpty() && new File(path).getName().compareTo(YAGO_TYPES_FILE)== 0){			
			try{
				// create a buffered reader for the current file
				BufferedReader br = new BufferedReader(new FileReader(path));
				String line;
				String[] strArr = new String[3]; //array with 3 cells to parse each line				
				while((line = br.readLine()) != null){ //read line	
					strArr = line.split("\\t"); //split the line by tabs - we know there are 2 tabs in each line				
					if(strArr.length >= 3 && strArr[1].contains(ACTED_IN){
						moviesLst.add(new Movie(strArr[0]));
						continue;
					}
					else if(strArr.length >= 3 && strArr[2].contains(Entity.ACTOR.getId())){
						actorsLst.add(new Person(strArr[0]));
						continue;
					}
					else if(strArr.length >= 3 && strArr[2].contains(Entity.DIRECTOR.getId())){
						directorsLst.add(new Person(strArr[0]));
						continue;
					}
				}
				br.close();
				return;
			} 
			catch(Exception ex){
				//to-do
			}	
			}
	}
	
	
	
	
	/* 1 approach */
	/**expect to get the file yagoSimpleTpyes.ttl and an entity, and pull all the instances of that entity*/
	public List<String> getEntityList(String path, Entity ent){
		//make sure the path is not null or empty and that it's the correct file
		if (path != null && !path.isEmpty() && new File(path).getName().compareTo(YAGO_TYPES_FILE)== 0){			
			try{
				// create a buffered reader of the current file
				BufferedReader br = new BufferedReader(new FileReader(path));
				String line;
				String[] strArr = new String[3];
				ArrayList<String> movieNames = new ArrayList<String>();
				while((line = br.readLine()) != null){ //read line 
					if(!line.contains(ent.getId()))
						continue;						
					strArr = line.split("\\t"); //split the line by tabs - we know there are 2 tabs in each line
					movieNames.add(strArr[0]); // add the movie name
				}
				br.close();
				return movieNames;
			}
			catch(Exception ex){
				//to-do
			}	
		}
		System.out.println("Wrong Path. Please Provide a correct path");
		return null;
	}


	/* 2 approach */
	/**expect to get the file yagoSimpleTpyes.ttl and return a list<movies>*/
	public List<Movie> getMoviesList(String path){
		if (path != null && !path.isEmpty() && new File(path).getName().compareTo(YAGO_TYPES_FILE)== 0){			
			try{
				// create a buffered reader of the current file
				BufferedReader br = new BufferedReader(new FileReader(path));
				String line;
				String[] strArr = new String[3];
				ArrayList<Movie> movieLst = new ArrayList<Movie>();
				while((line = br.readLine()) != null){ //read line 
					if(!line.contains(Entity.MOVIE.getId()))
						continue;						
					strArr = line.split("\\t"); //split the line by tabs - we know there are 2 tabs in each line
					movieLst.add(new Movie(strArr[0])); //create a movie object with that name and add it to the list
				}
				br.close();
				return movieLst;
			}
			catch(Exception ex){
				//todo
			}
		}
		System.out.println("Wrong Path. Please Provide a correct path");
		return null;
	}


	/* getters */

	public static List<Movie> getMoviesLst() {
		return moviesLst;
	}

	public static List<Person> getActorsLst() {
		return actorsLst;
	}

	public static List<Person> getDirectorsLst() {
		return directorsLst;
	}
}
