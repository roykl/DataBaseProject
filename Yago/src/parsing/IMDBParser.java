package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class IMDBParser {

	public final String GENRE_FILE = "genres.list";
	public final String GENRE_LIST = "8: THE GENRES LIST";

	
	public void parseGenre(HashMap<String,Movie> moviesTable){
		//get the current directory
		String currentDir = System.getProperty("user.dir");
		//set the file path
		String filePath = currentDir+"\\"+GENRE_FILE;
		try{
			BufferedReader br = new BufferedReader(new FileReader(filePath));					
			String[] strArr;
			@SuppressWarnings("unused")
			String line , movieName, genre;
			//read each line
			while((line = br.readLine()) != null){
				//if we got the the place in the file with the genres list
				if (line.contains(GENRE_LIST) && br.readLine().contains("======")){
					while((line = br.readLine()) != null){
						//split by tab and get the movie name and genre
						strArr = line.split("\\t");
						movieName = strArr[0];
						genre =strArr[strArr.length-1];
					}
					break;
				}
			}
			br.close();
		}
		catch(Exception ex){
			
		}
	}

	public void parsePlot(HashMap<String,Movie> moviesTable){
		//implement
	}
}
