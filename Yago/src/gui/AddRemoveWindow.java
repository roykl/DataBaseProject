package gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import gui.SWTResourceManager;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import db.DBOparations;
import db.IdbOparations;
import db.JDBCConnectionPooling;

import thread_logic.ThreadSearch;
import thread_logic.ThreadUserUpdate;
import viewModelLayer.MovieInfo;

public class AddRemoveWindow extends Shell {
	private Text text;
	private MovieInfo movie;
	
	
	public static final int OK = 1;
	public static final int NOT_EXIST = 2;
	public static final int ERR = 0;		
	
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			
			//connection pool declaration
			JDBCConnectionPooling pool = null;
			Thread t1 = null;
			
			
				
				//connection pool initialization
				pool = new JDBCConnectionPooling();
				t1 = new Thread(pool);
				//t1.start();
				//IdbOparations initialization
				IdbOparations operations = new DBOparations(pool);
				MovieInfo mov = new MovieInfo();
				mov.idMovie = -2147374156;
			AddRemoveWindow shell = new AddRemoveWindow(display,operations,mov);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void checkSubclass() {
	    // Disable the check that prevents subclassing of SWT components
	}
	/**
	 * Create the shell.
	 * @param display
	 */
	public AddRemoveWindow(final Display display ,final IdbOparations operations ,final MovieInfo movie) {
		super(display, SWT.SHELL_TRIM | SWT.BORDER);
		this.movie = movie;
		
		setImage(SWTResourceManager.getImage(AddRemoveWindow.class, "/movies.png"));
		setMinimumSize(new Point(800, 600));
		setLayout(new FormLayout());

		Composite composite = new Composite(this, SWT.BORDER);
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(100, -35);
		composite.setLayoutData(fd_composite);

		final Button btnRadioButton = new Button(composite, SWT.RADIO);
		btnRadioButton.setSelection(true);
		btnRadioButton.setBounds(10, 10, 52, 20);
		btnRadioButton.setText("Add");

		Button btnRadioButton_1 = new Button(composite, SWT.RADIO);
		btnRadioButton_1.setBounds(111, 10, 78, 20);
		btnRadioButton_1.setText("Remove");

		Composite composite_1 = new Composite(this, SWT.BORDER);
		fd_composite.top = new FormAttachment(composite_1, 30);
		fd_composite.left = new FormAttachment(composite_1, 0, SWT.LEFT);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.right = new FormAttachment(100, -35);
		fd_composite_1.bottom = new FormAttachment(100, -439);
		composite_1.setLayoutData(fd_composite_1);

		Button btnGenre = new Button(composite_1, SWT.RADIO);
		btnGenre.setSelection(true);
		btnGenre.setText("Genre");
		btnGenre.setBounds(10, 10, 72, 20);

		final Button btnActor = new Button(composite_1, SWT.RADIO);
		btnActor.setText("Actor");
		btnActor.setBounds(111, 10, 71, 20);

		text = new Text(this, SWT.BORDER);
		fd_composite_1.left = new FormAttachment(text, 55);
		fd_composite.bottom = new FormAttachment(text, 0, SWT.BOTTOM);
		fd_composite_1.top = new FormAttachment(text, 0, SWT.TOP);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(0, 70);
		fd_text.right = new FormAttachment(100, -286);
		fd_text.left = new FormAttachment(0, 22);
		text.setLayoutData(fd_text);

		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			//commit pressed
			public void widgetSelected(SelectionEvent arg0) {
				if(text.getText().equals("")){
					MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
					messageBox.setText("Warning");
					messageBox.setMessage("Value must exists in actors repository.");
					messageBox.open();
				}
				
				else if( btnActor.getSelection()){		// actor - calc id actor
					 
					
					display.syncExec(new ThreadSearch(operations,"idActor","Actor","actorName = '" + text.getText()+ "'"){

						int newVal;
						@Override
						public void run(){
			
							ResultSet result;
							super.run();
							
							result = this.getResult();
							
								
							
							try {
//								if(!result.next()){ // check that actor is in the system
////									MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
////									messageBox.setText("Warning");
////									messageBox.setMessage("Value must exists in actors repository.");
////									messageBox.open();
////									
//								}
//								
								
								newVal = result.getInt("idActor");
								
								
							} catch (SQLException e) {}
							
							commit(btnRadioButton.getSelection(), btnActor.getSelection(), display, operations, newVal);

						}
					});	
					
				}else {	//  genre - hash code
					commit(btnRadioButton.getSelection(), btnActor.getSelection(), display, operations, text.getText().hashCode());  
					//System.out.println("genre hash "+text.getText().hashCode() );
				}
				
			}
				
		});
		fd_text.bottom = new FormAttachment(btnNewButton, -199);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.top = new FormAttachment(0, 391);
		fd_btnNewButton.bottom = new FormAttachment(100, -96);
		fd_btnNewButton.left = new FormAttachment(0, 318);
		fd_btnNewButton.right = new FormAttachment(100, -286);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("Apply");
		createContents();



	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MovIt!");
		setSize(635, 570);

	}

	// INSERT - key2 = 0 - only to ActorMovie, GenreMovie
	// DELETE - newVal = 0 - only to ActorMovie, GenreMovie

	private void commit(final boolean add,final boolean actor, final Display display , IdbOparations operations  ,int newValue){
//System.out.println("im in commit" );		
		int secondKey = add ? 0:newValue;
		String table = actor ? "ActorMovie" : "GenreMovie";
		int firstKey = movie.idMovie;
		String column = actor ? "idActor" : "idGenre";
		int newVal = add ? newValue : 0;
		final String message1 = add ? "Insertion to" : "Delete from";
		final String message2 = actor ? "actors":"genres";
		
		
		
		display.syncExec(new ThreadUserUpdate(operations,table,firstKey,secondKey,column,newVal){
			@Override
			public void run(){
				super.run();
				int returnVal = this.getValue();
				
				if(returnVal == OK){ 
				MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_INFORMATION);
				messageBox.setText(add ? "Insert":"Delete");
				messageBox.setMessage(message1 +" "+ message2  +" successfully commited.");
				messageBox.open();
				}
				else if(returnVal == ERR){
					MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
					messageBox.setText(add ? "Insert":"Delete");
					messageBox.setMessage("Value must exists in "+ message2 + " repository.");
					messageBox.open();
					
				}
				
			}
		});
	}
}
