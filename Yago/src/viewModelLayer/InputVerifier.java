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
		if(!input.matches("[a-zA-Z0-9]+"))
			return false;
		else return true;
	}
	
	public static boolean validateYear(String from, String to){
		int fromYear = Integer.parseInt(from);
		int toYear = Integer.parseInt(to);
		if (fromYear > toYear || fromYear > 2013 || (fromYear < 1901 && toYear > 2013))
			return false;
		else
			return true;
	}

}
