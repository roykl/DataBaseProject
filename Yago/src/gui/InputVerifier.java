package gui;

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

}
