package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import utils.Configuration;

/*************************************************************************
 * TODO - Issues:
 *  1. Decide genre list format.
 *  2. Handling title name duplication of different movies.
 *  3. Notice changes made to Movie.java !! (genreList is now a Set).
 *  4. Notice some movies are without genre (empty set).  
 *  5. Need the change the way we parse the movie titles as i did below.
 ************************************************************************/
public class IMDBParser {
	/*Class Constants*/
	public final String GENRE_FILE = "genres.list";
	public final String GENRE_LIST = "8: THE GENRES LIST";
	public final String PLOT_FILE = "plot.list";
	public final String PLOT_LIST = "PLOT SUMMARIES LIST";
	public final String LANGUAGE_FILE = "language.list";
	/*Current user directory*/
	private String currentDir;
	private Configuration settings = new Configuration(); 

	/**
	 * Default Constructor
	 **/
	public IMDBParser(){
		currentDir = System.getProperty("user.dir");
	}

	/**
	 * Parses the IMDB genres file and updates the movie table accordingly 
	 * @param movies - the movie table.
	 **/
	public void parseGenre(HashMap<String,Movie> movies){
		//set the file path
		String filePath;
		if (settings.getImdbGenres().isEmpty() || settings.getImdbGenres() == null)
			 filePath = currentDir + "\\" + GENRE_FILE;
		else
			 filePath = settings.getImdbGenres();
		try{
			BufferedReader br = new BufferedReader(new FileReader(filePath));	
			boolean listStartReached = false;
			String[] strArr;
			Movie m;
			String line , movieName, genre;
			genre = "";
			movieName = "";
			//read each line
			while((line = br.readLine()) != null){
				//if we got the the place in the file where the genres list starts
				if (!listStartReached && line.contains(GENRE_LIST) && br.readLine().contains("======")){
					listStartReached = true;
					continue;
				}
				//if this line does not contain a valid movie title
				if(!line.contains(" (")){
					continue;
				}
				//split by tab and get the movie name and genre
				strArr = line.split("\\t");
				movieName = strArr[0].substring(0, strArr[0].indexOf(" ("));
				genre = strArr[strArr.length-1];
				if((m = movies.get(movieName)) != null){
					m.addGenre(genre);
				}
			}
			br.close();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}


	public void parsePlot(HashMap<String,Movie> movies){
		//set the file path
		String filePath = currentDir+"\\"+PLOT_FILE;
		try{
			BufferedReader br = new BufferedReader(new FileReader(filePath));	
			boolean listStartReached = false;
			String[] strArr;
			Movie m;
			String line , movieName, plot;
			plot = "";
			movieName = "";
			//read each line
			while((line = br.readLine()) != null){
				//if we got the the place in the file where the plot list starts
				if (!listStartReached && line.contains(PLOT_LIST) && br.readLine().contains("===================")){
					listStartReached = true;
					continue;
				}
				if(line.contains("MV:")){
					if(line.contains("\"")){
						movieName = line.substring(line.indexOf("\"")+1, line.lastIndexOf("\""));
					}
					else{
						movieName = line.substring(line.indexOf(":")+1);
					}
					System.out.println(movieName);
				}
			}
			br.close();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}




//package parsing;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.HashMap;
//
//public class IMDBParser {
//
//	public final String GENRE_FILE = "genres.list";
//	public final String GENRE_LIST = "8: THE GENRES LIST";
//
//	
//	public void parseGenre(HashMap<String,Movie> moviesTable){
//		//get the current directory
//		String currentDir = System.getProperty("user.dir");
//		//set the file path
//		String filePath = currentDir+"\\"+GENRE_FILE;
//		try{
//			BufferedReader br = new BufferedReader(new FileReader(filePath));					
//			String[] strArr;
//			@SuppressWarnings("unused")
//			String line , movieName, genre;
//			//read each line
//			while((line = br.readLine()) != null){
//				//if we got the the place in the file with the genres list
//				if (line.contains(GENRE_LIST) && br.readLine().contains("======")){
//					while((line = br.readLine()) != null){
//						//split by tab and get the movie name and genre
//						strArr = line.split("\\t");
//						movieName = strArr[0];
//						genre =strArr[strArr.length-1];
//					}
//					break;
//				}
//			}
//			br.close();
//		}
//		catch(Exception ex){
//			
//		}
//	}
//
//	public void parsePlot(HashMap<String,Movie> moviesTable){
//		//implement
//	}
//}
