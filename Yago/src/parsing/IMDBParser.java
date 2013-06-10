package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import utils.Configuration;


/**
 * Parse the imdb files:
 * genres.list, plot.list, language.list
 * and populate the moviesTable, actorsTable and directorsTable.
 * 
 * Is called after parsing yago files.
 **/
public class IMDBParser {
	/*Class constants*/
	private final String GENRE_FILE = "genres.list";
	private final String GENRE_LIST = "8: THE GENRES LIST";
	private final String PLOT_FILE = "plot.list";
	private final String PLOT_LIST = "PLOT SUMMARIES LIST";
	private final String LANGUAGE_FILE = "language.list";
	private final String LANGUAGE_LIST = "LANGUAGE LIST";
	/*Current user directory*/
	private String currentDir;
	/*Setting from configuration file*/
	private Configuration settings; 
	/*Internal helper movie table */
	private HashMap<String, ArrayList<Movie>> IMDBMoviesTable;


	/**
	 * Default Constructor. get moviesTable and add genre, language and plot
	 **/
	public IMDBParser(HashMap<String,Movie> moviesTable){
		currentDir = System.getProperty("user.dir");
		settings = new Configuration();
		IMDBMoviesTable = new HashMap<String, ArrayList<Movie>>();
		//Building the IMDBMoviesTable
		for (Movie m : moviesTable.values()) {
			//if the year for this movie is known
			if(m.getDateCreated() != null){
				String[] newNameArr = {m.getName()+" ("+m.getDateCreated()+")", 
						m.getName()+" ("+m.getDateCreated()+"/I)",
						m.getName()+" ("+m.getDateCreated()+"/II)",
						m.getName()+" ("+m.getDateCreated()+"/III)"};
				for (int i = 0; i < newNameArr.length; i++) {
					if(IMDBMoviesTable.get(newNameArr[i]) == null){
						IMDBMoviesTable.put(newNameArr[i], new ArrayList<Movie>());
					}
					IMDBMoviesTable.get(newNameArr[i]).add(m);
				}
			}
		}
		parseGenre();
		parsePlot();
		parseLanguage();
	}


	/**
	 * Parses the IMDB genres file and updates the movie table accordingly.
	 **/
	private void parseGenre(){		
		String filePath;

		// set the file path- if exist in the configuration= take it from it   
		if (settings.getImdbGenres() == null || settings.getImdbGenres().length() < 3)
			filePath = currentDir + "\\" + GENRE_FILE;
		else
			// take it from the current directory
			filePath = settings.getImdbGenres();

		try{
			BufferedReader br = new BufferedReader(new FileReader(filePath));	
			boolean listStartReached = false;
			String[] strArr;
			ArrayList<Movie> movieList;
			String line , movieName, genre;
			genre = "";
			movieName = "";
			//read each line
			while((line = br.readLine()) != null){
				//if we got the the place in the file where the genres list starts
				if (!listStartReached && line.contains(GENRE_LIST) && br.readLine().contains("=")){
					listStartReached = true;
					continue;
				}
				//if this line does not contain a valid movie title
				if(!line.contains(" (")){
					continue;
				}
				//split by tab and get the movie name and genre
				strArr = line.split("\\t");
				movieName = strArr[0];
				genre = strArr[strArr.length-1];
				//adding genre to movie table
				if((movieList = IMDBMoviesTable.get(movieName)) != null){
					for (Movie movie : movieList) {
						movie.addGenre(genre);
					}
				}
			}
			br.close();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}


	/**
	 * Parses the IMDB plot file and updates the movie table accordingly.
	 **/
	private void parsePlot(){
		//set the file path
		String filePath = currentDir+"\\"+PLOT_FILE;
		try{
			BufferedReader br = new BufferedReader(new FileReader(filePath));	
			boolean listStartReached = false;
			ArrayList<Movie> movieList;
			String line , movieName, plot, comments;
			movieName = "";
			//read each line
			while((line = br.readLine()) != null){
				//if we got the the place in the file where the plot list starts
				if (!listStartReached && line.contains(PLOT_LIST) && br.readLine().contains("=")){
					listStartReached = true;
					continue;
				}
				//if valid movie plot starting line
				if(line.contains("MV:")){
					plot = "";
					//parsing movie name from line
					movieName = line.replace("MV: ", "");
					movieName = movieName.replaceAll("\"", "");
					movieName = movieName.replaceAll("$#*! ", "");
					comments = movieName.substring(movieName.indexOf(")")+1);
					movieName = movieName.replace(comments, "");

					line = br.readLine();
					//concatenating the plot lines
					while((line = br.readLine()).contains("PL:")){
						plot+= line;
					}
					//adding plot to movie table
					if((movieList = IMDBMoviesTable.get(movieName)) != null){
						plot = plot.replaceAll("PL:", "");
						for (Movie movie : movieList) {
							movie.setPlot(plot);
						}
					}
				}
			}
			br.close();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}


	/**
	 * Parses the IMDB language file and updates the movie table accordingly.
	 **/
	private void parseLanguage(){
		//set the file path
		String filePath = currentDir+"\\"+LANGUAGE_FILE;
		try{
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			boolean listStartReached = false;
			String[] strArr;
			ArrayList<Movie> movieList;
			String line , movieName, language, comments;
			movieName = "";
			//read each line
			while((line = br.readLine()) != null){
				//if we got the the place in the file where the language list starts
				if (!listStartReached && line.contains(LANGUAGE_LIST) && br.readLine().contains("=")){
					listStartReached = true;
					continue;
				}
				//if this line does not contain a valid movie title
				if(!line.contains(" (")){
					continue;
				}

				//split by tab and get the movie name and genre
				strArr = line.split("\\t");
				movieName = strArr[0];
				language = strArr[strArr.length-1];

				//parsing movie name from line
				movieName = movieName.replaceAll("\"", "");
				movieName = movieName.replaceAll("$#*! ", "");
				comments = movieName.substring(movieName.indexOf(")")+1);
				movieName = movieName.replace(comments, "");
				//adding language to movie table where none exists
				if((movieList = IMDBMoviesTable.get(movieName)) != null){
					for (Movie movie : movieList) {
						if(movie.getLanguage() == null){
							movie.setLanguage(language);
						}
					}
				}
			}
			br.close();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}
