package thread_logic;

import db.IdbOparations;


public class ThreadImport implements Runnable {

	private IdbOparations oparations ;
	private int value; // returned value
	
	
	public ThreadImport(IdbOparations inOpp){		
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
