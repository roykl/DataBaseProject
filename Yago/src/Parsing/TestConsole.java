package Parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;

import Parsing.YagoParser.Entity;

public class TestConsole {


	/**
	 * @param args
	 * @throws IOException 
	 */


	public static void main(String[] args) throws IOException {
		

	//	readBigYagoFile("C:\\Users\\Roy\\Dropbox\\DB Project\\Yago Tables\\yagoFacts.ttl","C:\\Users\\Roy\\Desktop\\test\\try.txt",1550000);
		YagoParser yp = new YagoParser();
		yp.parseYagoTypes("C:\\Users\\Roy\\Dropbox\\DB Project\\Yago Tables\\yagoSimpleTypes.ttl");
		
		System.out.println("Num Movies = " + yp.getMoviesTable().size());
		System.out.println("Num Movies = " + yp.getActorsTable().size());
		
	    yp.parseYagoFacts("C:\\Users\\Roy\\Dropbox\\DB Project\\Yago Tables\\yagoFacts.ttl");
		
		
		File file = new File("C:\\Users\\Roy\\Desktop\\test\\try.txt");
		try {
			FileWriter fw = new FileWriter(file);
			for(Movie m : yp.getMoviesTable().values())
			{
				fw.write("Movie: " +m.getName() + " Actors: " + m.getActorsLst().toString());				
				fw.write(System.getProperty("line.separator"));
			}
			fw.close();
		}
		catch(Exception ex){
			//
		}
	
/*		
		ArrayList<String> arr = new ArrayList<String>();
		YagoParser yp = new YagoParser();
		arr = (ArrayList<String>) yp.getEntityList("C:\\Users\\Roy\\Dropbox\\DB Project\\Yago Tables\\yagoSimpleTypes.ttl", Entity.ACTOR);
		File file = new File("C:\\Users\\Roy\\Desktop\\test\\try.txt");
		try {
			FileWriter fw = new FileWriter(file);
			for(String str : arr)
			{
				fw.write(str);
				fw.write(System.getProperty("line.separator"));
			}
			fw.close();
		}
		catch(Exception ex){
			//
		}*/
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

}