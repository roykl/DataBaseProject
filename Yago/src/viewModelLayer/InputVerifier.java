package viewModelLayer;

public class InputVerifier {

	/** verify the correctness of the username */
	public static boolean verifyUsername(String username){

		if((username.length()) < 3 || (username.length() > 13) || (!username.matches("[a-zA-Z0-9]+")) )
			return false;
		return true;
	}

	/** verify the correctness of the password */
	public static boolean verifyPass(String pass){

		if((pass.length()) < 3 || (pass.length() > 13) )
			return false;
		return true;
	}
	
	/** verify the correctness of the user input when searching for a movie, director or actor */
	public static boolean verifyInput(String input){
		if(input.contains("\""))
			return false;
		else return true;
	}
	
	public static boolean verifyInjection(String input){
		if((input.toLowerCase().contains("select") && input.toLowerCase().contains("from"))
				|| (input.toLowerCase().contains("delete") && input.toLowerCase().contains("from"))
				|| (input.toLowerCase().contains("insert") && input.toLowerCase().contains("into"))
				|| (input.toLowerCase().contains("update") && input.toLowerCase().contains("set")))
			return false;
		else return true;
	}
	
	/** verify the correctness of the year */
	public static boolean validateYear(int from, int to){
		if (from > to || from > 2013 || (from < 1901 && to > 2013))
			return false;
		else
			return true;
	}

}
