package runnableLogic;

import db.IdbOparations;


public class Import implements Runnable {

	private IdbOparations oparations ;
	private int value; // returned value
	
	
	public Import(IdbOparations inOpp){		
		oparations = inOpp;		
	}
	
	
	
	
	public int getValue() {
		return value;
	}

	//public setV

	public void run(){
		value = oparations.importData();
		return;
	}
}
