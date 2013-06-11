package youTube;

import java.io.IOException;
import java.lang.reflect.Array;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MoviePoster {

	public String getMoviePoster(String movieName){
		 int i;
		 String posterUrl;
		 String[] splited = movieName.split("\\s+");
		 String urlAdress="http://www.omdbapi.com/?i=&t=";
		 Document doc=null;  
			
			for (i=0;i<Array.getLength(splited)-1;i++){
				urlAdress+=splited[i]+"+";
			}
			
			urlAdress+=splited[i];
			try {
				 doc = Jsoup.connect(urlAdress).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Elements links = doc.select("body");;
			 
			String str =links.get(0).toString();
			int j=str.indexOf("http");
			int m=str.indexOf(".jpg");
			posterUrl=str.substring(j, m+4);
		    return posterUrl;
	}
	
}
