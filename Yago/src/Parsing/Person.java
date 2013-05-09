package Parsing;

public class Person {
	
	private String name;
	private String preferredMean;
	private String firstName;
	private String lastName;
	
	public Person(String name){
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return "First Name= "+ this.firstName +" , Last Name= " + this.lastName;
		
	}

	public String getPreferredMean() {
		return preferredMean;
	}

	public void setPreferredMean(String preferredMean) {
		this.preferredMean = preferredMean;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
