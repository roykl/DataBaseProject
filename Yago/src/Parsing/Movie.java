package Parsing;

import java.util.ArrayList;
import java.util.List;

public class Movie{

	private String name;
	private String duration;
	private String dateCreated;
	private String genre;
	private String language;
	private Person director;
	private List<Person> actorsLst = new ArrayList<Person>();
	private String wikiURL;
	private String youuTubeURL;


	/* constructor */	

	public Movie(String name){
		this.name = name;
	}


	/**adding an actor to the actors List*/
	public void addActor(Person actor) {
		if(actor != null)
			this.actorsLst.add(actor);
	}

	/* getters & setters */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
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

	public Person getDirector() {
		return director;
	}

	public void setDirector(Person director) {
		this.director = director;
	}

	public List<Person> getActorsLst() {
		return actorsLst;
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


	public String getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

}
