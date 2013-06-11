package gui;

public class InputVerifier {

	public static String verifiUsername(String username){
		String msg;
		if(username.length() < 3)
			msg = "Username must contain more than 3 chars";
		else if (username.length() > 13)
			msg = "Username must contain not more than 12 chars";
		else if (!username.matches("[a-zA-Z0-9]+"))
			msg = "Username must contain only letters or numbers";
		else
			msg = "OK";
		
		return msg;
	}
	
}
