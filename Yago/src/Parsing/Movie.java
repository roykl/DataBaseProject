package Parsing;

import java.util.List;

public class Movie{
	
	private String name;
	private int duration;
	private String genre;
	private String language;
	private String director;
	private List<Person> actorsLst;
	private String wikiURL;
	private String youuTubeURL;
	
	/* constructor */
	/*roy*/
	 public Movie(String name){
		 this.name = name;
	 }
	 
	
	/* getters & setters */
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}

	public List<Person> getActorsLst() {
		return actorsLst;
	}

	public void setActorsLst(List<Person> actorsLst) {
		this.actorsLst = actorsLst;
	}

	public String getWikiURL() {
		return wikiURL;
	}

	public void setWikiURL(String wikiURL) {
		this.wikiURL = wikiURL;
	}

	public String getYouuTubeURL() {
		return youuTubeURL;
	}


	public void setYouuTubeURL(String youuTubeURL) {
		this.youuTubeURL = youuTubeURL;
	}
	
}
