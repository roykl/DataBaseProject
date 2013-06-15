package viewModelLayer;

public class InputVerifier {

	public static boolean verifyUsername(String username){

		if((username.length()) < 3 || (username.length() > 13) || (!username.matches("[a-zA-Z0-9]+")) )
			return false;
		return true;
	}

	public static boolean verifyPass(String pass){

		if((pass.length()) < 3 || (pass.length() > 13) )
			return false;
		return true;
	}
	
	public static boolean verifyInput(String input){
		if(input.contains("'") || input.contains("\""))
			return false;
		else return true;
	}
	
	public static boolean validateYear(int from, int to){
		if (from > to || from > 2013 || (from < 1901 && to > 2013))
			return false;
		else
			return true;
	}

}
