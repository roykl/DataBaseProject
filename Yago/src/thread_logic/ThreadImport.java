package thread_logic;

import db.IdbOparations;


public class ThreadImport extends Thread {

	IdbOparations oparations ;
	
	
	public ThreadImport(IdbOparations inOpp){		
		oparations = inOpp;		
	}
	
	
	public void run(){
		oparations.importData();
	}
}
