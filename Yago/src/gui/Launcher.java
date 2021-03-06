package gui;

import java.awt.Window;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import db.DBOparations;
import db.IdbOparations;
import db.JDBCConnectionPooling;

public class Launcher {

	//main loop
	public static void main(String[] args) {
		start();
	}



	private static void start(){
		Display display = Display.getDefault();
		//connection pool declaration
		JDBCConnectionPooling pool = null;
		Thread t1 = null;

		try {

			//connection pool initialization
			pool = new JDBCConnectionPooling();
			t1 = new Thread(pool);
			//t1.start();
			//IdbOparations initialization
			IdbOparations oparations = new DBOparations(pool);
			//open login window
			LoginWindow loginShell = new LoginWindow(display,oparations);
			loginShell.open();
			loginShell.layout();
			while (!loginShell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}


		} catch ( Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();

			Shell shell = new Shell(display);
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);
			messageBox.setText("Error");
			messageBox.setMessage("Connection Error: Check Internet OR SSL connection.");
			messageBox.open();
			shell.dispose();

			//restart


		}
		finally{
			Queue<Thread> allThreads = new ConcurrentLinkedQueue<Thread>();
			for (Thread t; (t = allThreads.poll()) != null; )
				// t.join();
				System.out.println(t.isAlive());
			pool.stop();
			System.out.println(t1.isAlive());
			display.dispose();

		}

	}
}