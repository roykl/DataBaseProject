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
//import youTube.YouTubeMedia;
//import youTube.YouTubeVideo;

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
	public HashMap<Integer, String> actorsList;
	public HashMap<Integer, String> genresList;
	public String youtubeUrl;
	public String posterUrl;

	public void addYoutubeUrl(String movieName) {
		/** get the trailer of the <movieName> */

		// create a service and a new youtube query
		YouTubeService service= new YouTubeService("");
		YouTubeQuery query = null;
		try {
			query = new YouTubeQuery(new URL("http://gdata.youtube.com/feeds/api/videos"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// get the video with max number of views (most viewed first)
		query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
		query.setMaxResults(1);

		// search for movie trailer and include restricted content in the search results
		query.setFullTextQuery(movieName + "Trailer");
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

}