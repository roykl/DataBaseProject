package Parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class YagoParser {

	//final String YAGO_TYPES = "yagoTypes.ttl";
	final String YAGO_TYPES = "yagoSimpleTypes.ttl";
	final String MOVIE_ID = "106613686";

	public YagoParser(){
		;
	}

	/**expect to get the file yagoTpyes.ttl and pull all the movies from it*/
	public List<String> getMovies(String path){
		//make sure the path is not null or empty and that it's the correct file
		if (path != null && !path.isEmpty() && new File(path).getName().compareTo(YAGO_TYPES)== 0){			
			try{
				// create a buffered reader of the current file
				BufferedReader br = new BufferedReader(new FileReader(path));
				String line;
				String[] strArr = new String[3];
				ArrayList<String> movieNames = new ArrayList<String>();
				while((line = br.readLine()) != null){ //read line 
					if(!line.contains(MOVIE_ID))
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


}
