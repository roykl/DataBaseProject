package parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import utils.Configuration;



public class TestConsole {


public static void main(String[] args) throws IOException {
		  
//		readBigYagoFile("C:\\Users\\Roy\\YAGO\\Yago\\plot.list","C:\\Users\\Roy\\Desktop\\test\\plot.list",1550000);
//
//	        
//	        IMDBParser im = new IMDBParser();
//	        im.parseGenre(new HashMap<String,Movie>());
		
		
		
		HashMap<String,Movie> p = new HashMap<String, Movie>();
		Parser yp = new Parser();
		try {
			p = (HashMap<String,Movie>) getObjFromFile("C:\\Users\\Roy\\Desktop\\test\\object");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	System.out.println(yp.pullDuration("'5940.0'^^<s>"));
	//	yp.parse();
		
        writeObjectToFile(yp.getMoviesTable());
		
		
		System.out.println("Num Of Movies is: " + yp.getMoviesTable().size());
		Set<Integer> keys = new HashSet<Integer>();
		for( String id : yp.getMoviesTable().keySet())
		{
			System.out.println(id.hashCode() +"\n");
			keys.add(id.hashCode());
		}
		
		System.out.println("Num Of keys is: " + keys.size());

		
//		yp.parseYagoTypes("C:\\Users\\Roy\\Desktop\\test\\yagoSimpleTypes.ttl");		
//	    yp.parseYagoFacts("C:\\Users\\Roy\\Desktop\\test\\yagoFacts.ttl");		
//		yp.parseYagoLiteralFacts("C:\\Users\\Roy\\Desktop\\test\\yagoLiteralFacts.ttl");	
//		yp.parseYagoWikiInfo("C:\\Users\\Roy\\Dropbox\\DB Project\\Yago Tables\\yagoWikipediaInfo.ttl");
	    
		File file = new File("C:\\Users\\Roy\\Desktop\\test\\try.txt");
		try {
			FileWriter fw = new FileWriter(file);
			for(Movie m : yp.getMoviesTable().values())
			{
				fw.write(m.toString());
				fw.write(System.getProperty("line.separator"));
			//	fw.write("Actors: " + m.getActorsLst().toString());				
			//	fw.write(System.getProperty("line.separator"));
			//	if(m.getDirector() != null){
			//		fw.write("Director: " + m.getDirector().getName());
			//	    fw.write(System.getProperty("line.separator"));
			//	}
			//	fw.write("Created On: " + m.getDateCreated() + ", Duration: " + m.getDuration());
			//	fw.write(System.getProperty("line.separator"));
			//	fw.write("WikiURL: "+ m.getWikiURL());
			//	fw.write(System.getProperty("line.separator"));
			//	fw.write(System.getProperty("line.separator"));
			}
			fw.close();
		}
		catch(Exception ex){
			//
		}
			
	}

	public static void readBigYagoFile(String bigfilePath, String outpath, int numRows2read) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(bigfilePath));		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outpath));
		for (int i=0; i< numRows2read;i++){
			bw.write(br.readLine());
			bw.write(System.getProperty("line.separator"));
		}
		br.close();
		bw.close();
	}
	
	public static void writeObjectToFile(Object obj) throws IOException{
		FileOutputStream fout = new FileOutputStream("C:\\Users\\Roy\\Desktop\\test\\object");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(obj);
	}
	
	public static Object getObjFromFile(String path) throws ClassNotFoundException, IOException{
		FileInputStream fin = new FileInputStream(path);
		ObjectInputStream ios = new ObjectInputStream(fin);
		return ios.readObject();
	}

}