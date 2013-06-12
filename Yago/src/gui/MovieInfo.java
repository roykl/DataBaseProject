package gui;

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
import youTube.YouTubeMedia;
import youTube.YouTubeVideo;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<VideoEntry> videos = videoFeed.getEntries();
		try {
		this.youtubeUrl = convertVideos(videos);
		} catch( Exception ex){
			this. youtubeUrl = null;
		}
		
	}

	private String convertVideos(List<VideoEntry> videos) {

		List<YouTubeVideo> youtubeVideosList = new LinkedList<YouTubeVideo>();
		List<YouTubeMedia> medias = new LinkedList<YouTubeMedia>();

		for (VideoEntry videoEntry : videos) {

			YouTubeVideo ytv = new YouTubeVideo();
			YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

			// set webPlayerUrl
			String webPlayerUrl = mediaGroup.getPlayer().getUrl();
			ytv.setWebPlayerUrl(webPlayerUrl);

			// set embeddedWebPlayerUrl
			String query = "?v=";
			int index = webPlayerUrl.indexOf(query);
			String embeddedWebPlayerUrl = webPlayerUrl.substring(index+query.length());
			embeddedWebPlayerUrl = "http://www.youtube.com/v/" + embeddedWebPlayerUrl;
			ytv.setEmbeddedWebPlayerUrl(embeddedWebPlayerUrl);

			//set medias				
			for (YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
				medias.add(new YouTubeMedia(mediaContent.getUrl(), mediaContent.getType()));
			}
		}
		return medias.get(0).getLocation();
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
