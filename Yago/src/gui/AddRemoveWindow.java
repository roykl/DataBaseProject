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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

public class AddRemoveWindow extends Shell {
	private Text editorText;
	private MovieInfo movie;


	public static final int OK = 1;
	public static final int NOT_EXIST = 2;
	public static final int ERR = 0;		
	private Text txtPleaseEnterValue;


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
		super(display, SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX);
		this.movie = movie;

		setImage(SWTResourceManager.getImage(AddRemoveWindow.class, "/movies.png"));
		setMinimumSize(new Point(800, 600));
		setLayout(new FormLayout());

		Composite composite_2 = new Composite(this, SWT.NONE);
		composite_2.setLayout(null);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.top = new FormAttachment(0);
		fd_composite_2.left = new FormAttachment(0);
		composite_2.setLayoutData(fd_composite_2);

		txtPleaseEnterValue = new Text(composite_2, SWT.READ_ONLY);
		txtPleaseEnterValue.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		txtPleaseEnterValue.setText("Please enter value to add or remove");
		txtPleaseEnterValue.setBounds(147, 157, 285, 26);

		editorText = new Text(composite_2, SWT.BORDER | SWT.WRAP);
		editorText.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		editorText.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		editorText.setBounds(147, 198, 483, 46);

		Composite genre_actor = new Composite(composite_2, SWT.BORDER);
		genre_actor.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		genre_actor.setBounds(147, 281, 196, 46);

		Button btnGenre = new Button(genre_actor, SWT.RADIO);
		btnGenre.setGrayed(true);
		btnGenre.setSelection(true);
		btnGenre.setText("Genre");
		btnGenre.setBounds(10, 10, 72, 20);

		final Button btnActor = new Button(genre_actor, SWT.RADIO);
		btnActor.setText("Actor");
		btnActor.setBounds(111, 10, 71, 20);

		Button applyButton = new Button(composite_2, SWT.NONE);
		applyButton.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		applyButton.setBounds(500, 281, 130, 153);

		applyButton.setText("Apply");

		Composite add_remove = new Composite(composite_2, SWT.BORDER);
		add_remove.setBounds(147, 382, 196, 46);

		final Button btnRadioButton = new Button(add_remove, SWT.RADIO);
		btnRadioButton.setSelection(true);
		btnRadioButton.setBounds(10, 10, 52, 20);
		btnRadioButton.setText("Add");

		Button btnRadioButton_1 = new Button(add_remove, SWT.RADIO);
		btnRadioButton_1.setBounds(111, 10, 78, 20);
		btnRadioButton_1.setText("Remove");

		Label label = new Label(composite_2, SWT.SHADOW_IN);
		label.setEnabled(false);
		label.setImage(SWTResourceManager.getImage(AddRemoveWindow.class, "/film strip.png"));
		label.setBounds(0, 0, 800, 579);

		applyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			//commit pressed
			public void widgetSelected(SelectionEvent arg0) {
				if(editorText.getText().equals("")){
					MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
					messageBox.setText("Warning");
					messageBox.setMessage("Value must exists in actors repository.");
					messageBox.open();
				}

				else if( btnActor.getSelection()){		// actor - calc id actor


					display.syncExec(new ThreadSearch(operations,"idActor","Actor","actorName = '" + editorText.getText()+ "'"){

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
					commit(btnRadioButton.getSelection(), btnActor.getSelection(), display, operations, editorText.getText().hashCode());  
					//System.out.println("genre hash "+text.getText().hashCode() );
				}

			}

		});

		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MovIt!");
		setSize(806, 612);

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
					//add\remove value to\from directly list
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
