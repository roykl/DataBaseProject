package thread_logic;
import java.sql.ResultSet;

import db.IdbOparations;


public class MultiThreadSearch extends Thread{

	public MultiThreadSearch(IdbOparations oparations, String selectM,
			String fromM, String whereM, String selectG, String fromG,
			String whereG, String selectA, String fromA, String whereA) {
		this.oparations = oparations;
		this.selectM = selectM;
		this.fromM = fromM;
		this.whereM = whereM;
		this.selectG = selectG;
		this.fromG = fromG;
		this.whereG = whereG;
		this.selectA = selectA;
		this.fromA = fromA;
		this.whereA = whereA;
	}

	IdbOparations oparations;
	String selectM;
	String fromM;
	String whereM;
	String selectG;
	String fromG;
	String whereG;
	String selectA;
	String fromA;
	String whereA;
	private ResultSet resultMovie;
	private ResultSet resultGenre;
	private ResultSet resultActor;
	

	public void run() {
		ThreadSearch t1 = new ThreadSearch(oparations, selectM, fromM, whereM);
		ThreadSearch t2 = new ThreadSearch(oparations, selectG, fromG, whereG);
		ThreadSearch t3 = new ThreadSearch(oparations, selectA, fromA, whereA);
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMovie = t1.getResult();
		
		t2.start();
		try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultGenre = t2.getResult();
		
		t3.start();
		try {
			t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultActor = t3.getResult();
		
		return;
	}

	
	public ResultSet getResultMovie() {
		return resultMovie;
	}


	public ResultSet getResultGenre() {
		return resultGenre;
	}


	public ResultSet getResultActor() {
		return resultActor;
	}

		
		
	
	

}
