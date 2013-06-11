package gui;

import java.awt.Window;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import db.DBOparations;
import db.IdbOparations;
import db.JDBCConnectionPooling;

public class MainLoop {

	//main loop
	public static void main(String[] args) {
		Display display = Display.getDefault();
		JDBCConnectionPooling pool;
		
		try {
			pool = new JDBCConnectionPooling();
			IdbOparations oparations = new DBOparations(pool);
			SignUpForm singup = new SignUpForm(display,oparations);
			singup.open();
			singup.layout();
			while (!singup.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR");
			
					
		
		
			Shell shell = new Shell(display);
			MessageBox messageBox =  new MessageBox(shell, SWT.ICON_WARNING);
			messageBox.setText("Error");
			messageBox.setMessage("Connection Error: Check Internet OR SSL connection.");
			messageBox.open();
			shell.dispose();
		}
		
		
		
	}
	
}
