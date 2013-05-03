package Parsing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Parsing.YagoParser.Entity;

public class TestConsole {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> arr = new ArrayList<String>();
		YagoParser yp = new YagoParser();
		arr = (ArrayList<String>) yp.getEntityList("C:\\Users\\Roy\\Dropbox\\DB Project\\Yago Tables\\yagoSimpleTypes.ttl", Entity.DIRECTOR);
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
		}
	}
}
