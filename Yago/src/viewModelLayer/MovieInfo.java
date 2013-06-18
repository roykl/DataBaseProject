package viewModelLayer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaContent;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.util.ServiceException;

import parsing.Person;

/**
 * Create an object with all the data we need about
 * a movie. We get the information from the data base
 * and then we show all details in the gui
 */
public class MovieInfo {

	public int idMovie;
	public String movieName;
	public int idLanguage;
	public String language;
	public int idDirector;
	public String directorName;
	public String year;
	public String wikiUrl;
	public String duration;
	public String plot;
	public double grade;
	public int numRankers;
	public HashMap<Integer, String> actorsList; //
	public HashMap<Integer, String> genresList; //
	public String youtubeUrl;
	public String posterUrl;

	
	/** add the youTube URL of that movie (find it via youtube api- searching <movieName> + <year> + <"trailer"> **/
	public void addYoutubeUrl(String movieName, String year) {
		/** get the trailer of the <movieName> */

		// create a service and a new youtube query
		YouTubeService service= new YouTubeService("");
		YouTubeQuery query = null;
		try {
			query = new YouTubeQuery(new URL("http://gdata.youtube.com/feeds/api/videos"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// return only ine result (the first movie in the youtube list
		query.setMaxResults(1);

		// search for movie trailer and include restricted content in the search results
		if (year != null)
			query.setFullTextQuery(movieName +" " +year  + " Trailer");
		else
			query.setFullTextQuery(movieName + " Trailer");
		query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);

		//execute the query and get the video feed
		VideoFeed videoFeed = null;
		try {
			videoFeed = service.query(query, VideoFeed.class);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {		
			e.printStackTrace();
		}

		try {
			List<VideoEntry> videos = videoFeed.getEntries();
			
			YouTubeMediaGroup mediaGroup = videos.get(0).getMediaGroup();

			// set webPlayerUrl
			String webPlayerUrl = mediaGroup.getPlayer().getUrl();

			// set embeddedWebPlayerUrl
			String query1 = "?v=";
			int index = webPlayerUrl.indexOf(query1);
			String embeddedWebPlayerUrl = webPlayerUrl.substring(index+query1.length());
			embeddedWebPlayerUrl = "http://www.youtube.com/v/" + embeddedWebPlayerUrl;
			
			this.youtubeUrl = embeddedWebPlayerUrl;
		} catch( Exception ex){
			this. youtubeUrl = null;
		}		
	}

    /** get the poster url of the movie **/
	public void addPosterUrl(String movieName) {
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
			Elements links = doc.select("body");
			String str =links.get(0).toString();
			int j=str.indexOf("http");
			int m=str.indexOf(".jpg");
			posterUrl=str.substring(j, m+4);
			this.posterUrl= posterUrl;
		} catch (Exception e) {
			this.posterUrl= null;
		}

	}
	
	
	@Override
	public String toString() {
		return "MovieInfo [idMovie=" + idMovie + ", movieName=" + movieName
				+ ", idLanguage=" + idLanguage + ", language=" + language
				+ ", idDirector=" + idDirector + ", directorName="
				+ directorName + ", year=" + year + ", wikiUrl=" + wikiUrl
				+ ", duration=" + duration + ", plot=" + plot + ", grade="
				+ grade + ", numRankers=" + numRankers + ", actorsList="
				+ actorsList + ", genresList=" + genresList + ", youtubeUrl="
				+ youtubeUrl + ", posterUrl=" + posterUrl + "]";
	}

}
