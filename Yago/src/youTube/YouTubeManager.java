package youTube;

import com.google.gdata.client.*;
import com.google.gdata.client.youtube.*;
import com.google.gdata.data.*;
import com.google.gdata.data.geo.impl.*;
import com.google.gdata.data.media.*;
import com.google.gdata.data.media.mediarss.*;
import com.google.gdata.data.youtube.*;
//import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;
import com.google.gdata.client.youtube.YouTubeService;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.io.IOException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;



public class YouTubeManager {

	private static final String YOUTUBE_URL = "http://gdata.youtube.com/feeds/api/videos";
	private static final String YOUTUBE_EMBEDDED_URL = "http://www.youtube.com/v/";

	/** get the trailer of the <movieName> */
	public void getTrailer(String movieName) throws IOException, ServiceException{

		// create a service and a new youtube query
		YouTubeService service= new YouTubeService("");
		YouTubeQuery query = new YouTubeQuery(new URL("http://gdata.youtube.com/feeds/api/videos"));

		// get the video with max number of views (most viewed first)
		query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
		query.setMaxResults(1);

		// search for movie trailer and include restricted content in the search results
		query.setFullTextQuery(movieName + "Trailer");
		query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);

		//execute the query and get the video feed
		VideoFeed videoFeed = service.query(query, VideoFeed.class);
		List<VideoEntry> videos = videoFeed.getEntries();
		convertVideos(videos);
	}


	private List<YouTubeVideo> convertVideos(List<VideoEntry> videos) {
		
		List<YouTubeVideo> youtubeVideosList = new LinkedList<YouTubeVideo>();

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
			embeddedWebPlayerUrl = YOUTUBE_EMBEDDED_URL + embeddedWebPlayerUrl;
			ytv.setEmbeddedWebPlayerUrl(embeddedWebPlayerUrl);
			
			// set Thumbnails
			List<String> thumbnails = new LinkedList<String>();
			for (MediaThumbnail mediaThumbnail : mediaGroup.getThumbnails()) {
				thumbnails.add(mediaThumbnail.getUrl());
			}  
			ytv.setThumbnails(thumbnails);

			//set medias
			List<YouTubeMedia> medias = new LinkedList<YouTubeMedia>();
			for (YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
				medias.add(new YouTubeMedia(mediaContent.getUrl(), mediaContent.getType()));
			}
			ytv.setMedias(medias);
			
			//add the youTube video to the list
			youtubeVideosList.add(ytv);
		}
		return youtubeVideosList;
	}


}
