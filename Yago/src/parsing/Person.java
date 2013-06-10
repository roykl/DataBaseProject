package parsing;

import java.io.Serializable;

/**
 * Person class to describe Actor or Director.
 * We need only the name of the person and the it's id as appear in yago 
 */
public class Person implements Serializable{
	
	private String name;
	private String id;
	
	public Person(String id){
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
