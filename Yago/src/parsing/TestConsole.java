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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gdata.util.ServiceException;

import db.DBOparations;
import db.JDBCConnectionPooling;

import utils.Configuration;
import youTube.YouTubeManager;


//new branch
public class TestConsole {


public static void main(String[] args) throws IOException, SQLException {
	
	
	JDBCConnectionPooling pl = null;
	try {
	//	pl= new JDBCConnectionPooling("jdbc:mysql://localhost:3306/dbyago","root", "61088");
		pl = new JDBCConnectionPooling();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	DBOparations db= new DBOparations(pl);
	
	db.delete("DbMysql05.Movies", "idMovies =1");
	
	//db.insert("DbMysql05.Movies", "3" ,"16");
	//db.update("DbMysql05.Movies", "language = 1", "idMovies = 1");
	ResultSet rs = db.select("*", "DbMysql05.Movies", "");
	rs.next();
	System.out.println(rs.getString(1));
	System.out.println(rs.getString(2));
	rs.next();
	System.out.println(rs.getString(1));
	System.out.println(rs.getString(2));
	
	YouTubeManager yt = new YouTubeManager();
	try {
		yt.getTrailer("reservoir dogs");
	} catch (ServiceException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		  
//		readBigYagoFile("C:\\Users\\Roy\\YAGO\\Yago\\plot.list","C:\\Users\\Roy\\Desktop\\test\\plot.list",1550000);
//
//	        
//	        IMDBParser im = new IMDBParser();
//	        im.parseGenre(new HashMap<String,Movie>());
		
		
//		
//		HashMap<String,Movie> p = new HashMap<String, Movie>();
		Parser yp = new Parser();
		yp.parse();
		System.out.println(yp.getDirectorsTable().size());
		Set<Integer> s = new HashSet<Integer>();
		for(Movie l : yp.getMoviesTable().values()){
			if(l.getDirector() != null)
		    	s.add(l.getDirector().getId().hashCode());
		}
		System.out.println(s.size());
		

//		long start = System.currentTimeMillis();
//		yp.parse();
//		System.out.println((System.currentTimeMillis()-start)/1000F + " Seconds");
//		try {
//			long s = System.currentTimeMillis();
//			p = (HashMap<String,Movie>) getObjFromFile("F:\\Users\\Roy's Room\\Dropbox\\DB Project\\object");
//			System.out.println((System.currentTimeMillis()-s)/1000F + " Seconds");
//			System.out.println("Num of Movies is: " +p.size());
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		JDBCConnectionPooling pl = null;
//				try {
//				//	pl= new JDBCConnectionPooling("jdbc:mysql://localhost:3306/dbyago","root", "61088");
//					pl = new JDBCConnectionPooling();
//					
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//				DBOparations db= new DBOparations(pl);
//				db.importData();
			
		
	//	System.out.println(yp.pullDuration("'5940.0'^^<s>"));
	//	yp.parse();
		
   //     writeObjectToFile(yp.getMoviesTable());
		
		
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