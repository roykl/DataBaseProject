package parsing;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Movie{


	private String name;
	private String id;
	private String duration;
	private String dateCreated;
	private Set<String> genreList = new LinkedHashSet<String>();
	private String language; // need to do
	private Person director;
	private List<Person> actorsLst = new ArrayList<Person>();
	private String wikiURL;
	private String plot;
	private String youTubeURL; // need to do


	/* constructor */	

	public Movie(String id){
		this.id = id;
	}


	/** adding an actor to the actors List */
	public void addActor(Person actor) {
		if(actor != null)
			this.actorsLst.add(actor);
	}
	
	/** adding genre to the genreList */
	public void addGenre(String genre) {
		this.genreList.add(genre);
	}
	
	
	@Override
	public String toString() {
		return "id= " + getId() + ",   name=" + name + "\n" + "actors= " + actorsLst +"\n"
				+ "director= " + getDirector() +"\n" + "year= " + getDateCreated()
				+ ", duration= " + getDuration() + "\n"+ "wiki= " + getWikiURL() +"\n";
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

	public Set<String> getGenre() {
		return genreList;
	}

	public void setGenre(Set<String> genreList) {
		this.genreList = genreList;
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

	public String getYouTubeURL() {
		return youTubeURL;
	}

	public void setYouTubeURL(String youuTubeURL) {
		this.youTubeURL = youuTubeURL;
	}


	public String getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
	public String getPlot() {
		return plot;
	}

	
	public void setPlot(String plot) {
		this.plot = plot;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

}
