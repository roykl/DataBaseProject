package runnableLogic;
import java.sql.ResultSet;

import db.IdbOparations;


public class MultiThreadSearch extends Search {

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
	


	@Override
	public void run() {
		Search t11 = new Search(oparations, selectM, fromM, whereM);
		Search t22 = new Search(oparations, selectG, fromG, whereG);
		Search t33 = new Search(oparations, selectA, fromA, whereA);
		
		Thread t1 = new Thread(t11);
		Thread t2 = new Thread(t22);
		Thread t3 = new Thread(t33);
		
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMovie = t11.getResult();
		
		t2.start();
		try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultGenre = t22.getResult();
		
		t3.start();
		try {
			t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultActor = t33.getResult();
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
