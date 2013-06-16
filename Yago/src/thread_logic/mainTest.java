package thread_logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBOparations;
import db.IdbOparations;
import db.JDBCConnectionPooling;

public class mainTest {

	public static void main(String[] args) throws SQLException{
		
		JDBCConnectionPooling pool = new JDBCConnectionPooling();
		IdbOparations oparations = new DBOparations(pool);
		
		//***  add user test  ***//
		
//		ThreadAddUser t1 = new ThreadAddUser(oparations , "aaa", "1234");
//		ThreadAddUser t2 = new ThreadAddUser(oparations , "bbb", "1234");
//		ThreadAddUser t3 = new ThreadAddUser(oparations , "ccc", "1234");
//		ThreadAddUser t4 = new ThreadAddUser(oparations , "ddd", "12345");
//		ThreadAddUser t5 = new ThreadAddUser(oparations , "eee", "bulfdwml");
//		t1.start();
//		t2.start();
//		t3.start();
//		t4.start();
//		t5.start();
		
		
		//***  exist test  ***//
		
//		ThreadCheckExist t1 = new ThreadCheckExist(oparations, "*", "Genre", "genreName = 'horrrrror'");
//		t1.start();
//		try {
//			t1.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		System.out.println( t1.getValue());
		
	
	
		//***  grade test  ***//
		
		ThreadGrade t1 = new ThreadGrade(oparations, "nir".hashCode(), -2143842341, 3);
		ThreadGrade t2 = new ThreadGrade(oparations, "roy".hashCode(), -2143842341, 7);
		ThreadGrade t3 = new ThreadGrade(oparations, "Omri".hashCode(), -2143842341, 8);
		ThreadGrade t4 = new ThreadGrade(oparations, "yoni".hashCode(), -2143842341, 5);
		ThreadGrade t5 = new ThreadGrade(oparations, "nir".hashCode(), -2143842341, 9);
		ThreadGrade t6 = new ThreadGrade(oparations, "yoni".hashCode(), -2106016793, 5);
		ThreadGrade t7 = new ThreadGrade(oparations, "nir".hashCode(), -2106016793, 9);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		
		//***  search test ***//
		
//		ThreadSearch t1 = new ThreadSearch(oparations, "*", "Users" ,"");
//		t1.start();
//		try {
//			t1.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		ResultSet r = t1.getResult();
//		r.next();
//		System.out.println(r.getString(2));
		

		//*** user authentication test ***//
		
//		ThreadUserAuthentication t1 = new ThreadUserAuthentication(oparations, "Yoni", "12345");
//		t1.start();
//		try {
//			t1.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		System.out.println(t1.getValue());
		
//		ThreadUserUpdate t1 = new ThreadUserUpdate(oparations, "Movie", -2143842341, 0, "year", 3000);
//		t1.start();
		
//		ThreadUserUpdate t2 = new ThreadUserUpdate(oparations, "GenreMovie", -2143842341, 0, "idGenre", -2120419507);
//		t2.start();
//		
	}
		
	
		
		
}
