package gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import db.IdbOparations;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridData;

import thread_logic.MultiThreadSearch;
import thread_logic.ThreadSearch;
import viewModelLayer.MoviesResults;
import viewModelLayer.SearchQueries;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;


public class MainMenu extends Shell {
	private Text txtMovieTitle;
	private Text actorTxtBox3;
	private Text actorTxtBox2;
	private Text actorTxtBox1;
	private Table genreTable;
	private Text text;


	/**
	 * Create the shell.
	 * @param display
	 */ //
	public MainMenu(final Display display, final IdbOparations operations,final boolean isAdmin) {
		super(display, SWT.SHELL_TRIM);
		setSize(1353, 678);
		setBackground(SWTResourceManager.getColor(128, 0, 0));
		setImage(SWTResourceManager.getImage(MainMenu.class, "/movies.png"));
		setLayout(new FormLayout());

		final ExpandBar expandBar = new ExpandBar(this, SWT.V_SCROLL);
		expandBar.setSpacing(10);
		expandBar.setBackground(SWTResourceManager.getColor(128, 0, 0));
		FormData fd_expandBar = new FormData();
		fd_expandBar.left = new FormAttachment(0);
		expandBar.setLayoutData(fd_expandBar);

		Composite composite = new Composite(this, SWT.NONE);
		fd_expandBar.right = new FormAttachment(composite, -13);
		composite.setBackground(SWTResourceManager.getColor(0, 0, 0));
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.right = new FormAttachment(100, 5);
		fd_composite.bottom = new FormAttachment(0, 648);
		fd_composite.left = new FormAttachment(0, 292);
		composite.setLayoutData(fd_composite);

		List list = new List(composite, SWT.NONE);
		list.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		list.setItems(new String[] {});
		list.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		list.setBounds(21, 21, 997, 486);

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(MainMenu.class, "/video screen.jpg"));
		lblNewLabel.setBounds(10, 10, 1024, 628);

		txtMovieTitle = new Text(this, SWT.BORDER);
		txtMovieTitle.setToolTipText("Enter a movie title");
		txtMovieTitle.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		FormData fd_txtMovieTitle = new FormData();
		fd_txtMovieTitle.right = new FormAttachment(composite, -24);
		fd_txtMovieTitle.left = new FormAttachment(0, 10);
		txtMovieTitle.setLayoutData(fd_txtMovieTitle);

		Button btnSearch = new Button(this, SWT.NONE);
		fd_expandBar.bottom = new FormAttachment(btnSearch, -6);

		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setText("Genre");
		
		genreTable = new Table(expandBar, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		genreTable.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		xpndtmNewExpanditem.setControl(genreTable);
		
		TableItem tableItem = new TableItem(genreTable, SWT.NONE);
		tableItem.setText("Horror");
		
		TableItem tableItem_1 = new TableItem(genreTable, SWT.NONE);
		tableItem_1.setText("Documentary");
		
		TableItem tableItem_2 = new TableItem(genreTable, SWT.NONE);
		tableItem_2.setText("Sci-Fi");
		
		TableItem tableItem_3 = new TableItem(genreTable, SWT.NONE);
		tableItem_3.setText("History");
		
		TableItem tableItem_4 = new TableItem(genreTable, SWT.NONE);
		tableItem_4.setText("Western");
		
		TableItem tableItem_5 = new TableItem(genreTable, SWT.NONE);
		tableItem_5.setText("Romance");
		
		TableItem tableItem_6 = new TableItem(genreTable, SWT.NONE);
		tableItem_6.setText("Musical");
		
		TableItem tableItem_7 = new TableItem(genreTable, SWT.NONE);
		tableItem_7.setText("Animation");
		
		TableItem tableItem_8 = new TableItem(genreTable, SWT.NONE);
		tableItem_8.setText("Mystery");
		
		TableItem tableItem_9 = new TableItem(genreTable, SWT.NONE);
		tableItem_9.setText("War");
		
		TableItem tableItem_10 = new TableItem(genreTable, SWT.NONE);
		tableItem_10.setText("News");
		
		TableItem tableItem_11 = new TableItem(genreTable, SWT.NONE);
		tableItem_11.setText("Crime");
		
		TableItem tableItem_12 = new TableItem(genreTable, SWT.NONE);
		tableItem_12.setText("Drama");
		
		TableItem tableItem_13 = new TableItem(genreTable, SWT.NONE);
		tableItem_13.setText("Music");
		
		TableItem tableItem_14 = new TableItem(genreTable, SWT.NONE);
		tableItem_14.setText("Short");
		
		TableItem tableItem_15 = new TableItem(genreTable, SWT.NONE);
		tableItem_15.setText("Sport");
		
		TableItem tableItem_16 = new TableItem(genreTable, SWT.NONE);
		tableItem_16.setText("Fantasy");
		
		TableItem tableItem_17 = new TableItem(genreTable, SWT.NONE);
		tableItem_17.setText("Reality-TV");
		
		TableItem tableItem_18 = new TableItem(genreTable, SWT.NONE);
		tableItem_18.setText("Film-Noir");
		
		TableItem tableItem_19 = new TableItem(genreTable, SWT.NONE);
		tableItem_19.setText("Adventure");
		
		TableItem tableItem_20 = new TableItem(genreTable, SWT.NONE);
		tableItem_20.setText("Thriller");
		
		TableItem tableItem_21 = new TableItem(genreTable, SWT.NONE);
		tableItem_21.setText("Action");
		
		TableItem tableItem_22 = new TableItem(genreTable, SWT.NONE);
		tableItem_22.setText("Comedy");
		
		TableItem tableItem_23 = new TableItem(genreTable, SWT.NONE);
		tableItem_23.setText("Biography");
		
		TableItem tableItem_24 = new TableItem(genreTable, SWT.NONE);
		tableItem_24.setText("Family");
		xpndtmNewExpanditem.setHeight(120);
		FormData fd_btnSearch = new FormData();
		fd_btnSearch.top = new FormAttachment(0, 473);
		fd_btnSearch.left = new FormAttachment(0, 51);
		fd_btnSearch.right = new FormAttachment(0, 223);
		btnSearch.setLayoutData(fd_btnSearch);
		btnSearch.setText("Search");

		Label lblMovieTitle = new Label(this, SWT.NONE);
		fd_txtMovieTitle.top = new FormAttachment(lblMovieTitle, 12);
		lblMovieTitle.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMovieTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		FormData fd_lblMovieTitle = new FormData();
		fd_lblMovieTitle.bottom = new FormAttachment(100, -591);
		fd_lblMovieTitle.left = new FormAttachment(0, 10);
		lblMovieTitle.setLayoutData(fd_lblMovieTitle);
		lblMovieTitle.setText("Movie title");

		Label lblAdvancedSearch = new Label(this, SWT.NONE);
		fd_txtMovieTitle.bottom = new FormAttachment(lblAdvancedSearch, -30);
		lblAdvancedSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		fd_expandBar.top = new FormAttachment(0, 135);

		ExpandItem xpndtmDirectorName = new ExpandItem(expandBar, 0);
		xpndtmDirectorName.setText("Director");

		Composite directorTxtBox = new Composite(expandBar, SWT.NONE);
		xpndtmDirectorName.setControl(directorTxtBox);
		directorTxtBox.setLayout(new FormLayout());
		
		text = new Text(directorTxtBox, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(0, 10);
		fd_text.right = new FormAttachment(100, -25);
		fd_text.left = new FormAttachment(0, 32);
		text.setLayoutData(fd_text);
		xpndtmDirectorName.setHeight(50);

		ExpandItem xpndtmActors = new ExpandItem(expandBar, 0);
		xpndtmActors.setText("Actors");

		Composite composite_4 = new Composite(expandBar, SWT.NONE);
		xpndtmActors.setControl(composite_4);
		composite_4.setLayout(new FormLayout());
		
		actorTxtBox1 = new Text(composite_4, SWT.BORDER);
		FormData fd_actorTxtBox1 = new FormData();
		actorTxtBox1.setLayoutData(fd_actorTxtBox1);
		
		actorTxtBox2 = new Text(composite_4, SWT.BORDER);
		fd_actorTxtBox1.bottom = new FormAttachment(100, -51);
		FormData fd_actorTxtBox2 = new FormData();
		fd_actorTxtBox2.right = new FormAttachment(actorTxtBox1, 0, SWT.RIGHT);
		fd_actorTxtBox2.left = new FormAttachment(actorTxtBox1, 0, SWT.LEFT);
		fd_actorTxtBox2.top = new FormAttachment(actorTxtBox1, 6);
		actorTxtBox2.setLayoutData(fd_actorTxtBox2);
		
		actorTxtBox3 = new Text(composite_4, SWT.BORDER);
		fd_actorTxtBox1.right = new FormAttachment(actorTxtBox3, 0, SWT.RIGHT);
		fd_actorTxtBox1.left = new FormAttachment(actorTxtBox3, 0, SWT.LEFT);
		FormData fd_actorTxtBox3 = new FormData();
		fd_actorTxtBox3.bottom = new FormAttachment(100, -83);
		fd_actorTxtBox3.left = new FormAttachment(0, 35);
		fd_actorTxtBox3.right = new FormAttachment(100, -26);
		actorTxtBox3.setLayoutData(fd_actorTxtBox3);
		xpndtmActors.setHeight(118);

		ExpandItem xpndtmYear = new ExpandItem(expandBar, 0);
		xpndtmYear.setText("Years");

		Composite composite_5 = new Composite(expandBar, SWT.NONE);
		xpndtmYear.setControl(composite_5);
		composite_5.setLayout(null);

		Label lblNewLabel_1 = new Label(composite_5, SWT.NONE);
		lblNewLabel_1.setBounds(47, 13, 48, 20);
		lblNewLabel_1.setText("From:");
		
		final Spinner yearFromSpinner = new Spinner(composite_5, SWT.BORDER);
		yearFromSpinner.setTextLimit(4);
		yearFromSpinner.setMaximum(2013);
		yearFromSpinner.setMinimum(1900);
		yearFromSpinner.setBounds(106, 13, 62, 22);

		Label lblTo = new Label(composite_5, SWT.NONE);
		lblTo.setText("To:");
		lblTo.setBounds(47, 60, 48, 20);
		
		final Spinner yearToSpinner = new Spinner(composite_5, SWT.BORDER);
		yearToSpinner.setTextLimit(4);
		yearToSpinner.setMaximum(2100);
		yearToSpinner.setMinimum(1900);
		yearToSpinner.setSelection(2103);
		yearToSpinner.setBounds(106, 57, 62, 22);
		xpndtmYear.setHeight(115);

		final ExpandItem xpndtmNewExpanditem_1 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem_1.setText("Language");
		
		final ScrolledComposite scrolledComposite_1 = new ScrolledComposite(expandBar, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		xpndtmNewExpanditem_1.setControl(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		final List languageList = new List(scrolledComposite_1, SWT.BORDER | SWT.V_SCROLL);
		languageList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.keyCode == SWT.CR)
					xpndtmNewExpanditem_1.setExpanded(false);
			}
		});
		languageList.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		languageList.setItems(new String[] {"English", "Hindi", "Spanish", "French", "Italian", "German", "Malayalam", "Telugu", "Japanese", "Bengali", "Danish", "Dutch", "Filipino", "Portuguese", "Russian", "Arabic", "Hebrew"});
		scrolledComposite_1.setContent(languageList);
		scrolledComposite_1.setMinSize(languageList.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		xpndtmNewExpanditem_1.setHeight(110);
		lblAdvancedSearch.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		FormData fd_lblAdvancedSearch = new FormData();
		fd_lblAdvancedSearch.bottom = new FormAttachment(expandBar, -1);
		fd_lblAdvancedSearch.left = new FormAttachment(0, 10);
		lblAdvancedSearch.setLayoutData(fd_lblAdvancedSearch);
		lblAdvancedSearch.setText("Advanced search options");
		
		if(isAdmin){
			Button btnImport = new Button(this, SWT.NONE);
			btnImport.addSelectionListener(new SelectionAdapter() {
				@Override
				//Import button pressed
				public void widgetSelected(SelectionEvent arg0) {
					massiveImport(display, operations);
				}
			});
			btnImport.setText("Import");
			FormData fd_btnImport = new FormData();
			fd_btnImport.left = new FormAttachment(btnSearch, 0, SWT.LEFT);
			fd_btnImport.top = new FormAttachment(btnSearch, 19);
			fd_btnImport.right = new FormAttachment(btnSearch, 0, SWT.RIGHT);
			btnImport.setLayoutData(fd_btnImport);
		}


			
		//Search button listener - TODO: complete extracting data from search parameters
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			//Search button pressed
			public void widgetSelected(SelectionEvent arg0) {
				//parameters for select - if you need you can send few of these threads
				final String selectMovie = SearchQueries.MOVIE_SELECT;
				final String fromMovie = SearchQueries.MOVIE_FROM;
			    String whereMovie= null;
				final String selectGenres = SearchQueries.GENRES_SELECT;
				final String fromGenres = SearchQueries.GENRES_FROM;
				String whereGenres = null;
				String selectActors = SearchQueries.ACTORS_SELECT;
				String fromActors = SearchQueries.ACTORS_FROM;
				String whereActors = null;
				// check if user entered movie name
				boolean enteredMoive = txtMovieTitle.getText().trim().isEmpty()? false : true;				
				if (enteredMoive){ // if entered a movie, find that movie
					whereMovie = "movie.movieName = '" + txtMovieTitle.getText() +"'";
					whereGenres = "movie.movieName = '" + txtMovieTitle.getText() +"'" +
								" AND genremovie.idMovie = movie.idMovie AND genremovie.idGenre = genre.idGenre";
					whereActors =  "movie.movieName = '" + txtMovieTitle.getText() +"'" +
							" AND actormovie.idMovie = movie.idMovie AND actormovie.idActor = actor.idactor";
				}
				else {// user didn't enter movieName so we use the advanced properties
					//genres
					SearchQueries.createGenreWhere(genreTable.getItems());
					//director
					String directorName = text.getText();
					//actors
					String actor1 = actorTxtBox3.getText();
					String actor2 = actorTxtBox1.getText();
					String actor3 = actorTxtBox2.getText();
					//year
					String fromYear = yearFromSpinner.getText();
					String toYear = yearToSpinner.getText();
					//language
					String language = null;
					if(languageList.getSelectionCount() > 0)
						language = languageList.getSelection()[0];
					System.out.println(language);
					System.out.println(toYear);
					System.out.println(actor3);

					
					//TODO - remove (for test purposes only)
					System.out.println((languageList.getSelection()).length);
					
					
				}
				display.syncExec(new MultiThreadSearch(operations, selectMovie, fromMovie, whereMovie,
							selectGenres, fromGenres, whereGenres, selectActors, fromActors, whereActors){
					@Override		
					public void run(){
						super.run();
						ResultSet resultMovie = this.getResultMovie();
						ResultSet resultGenre = this.getResultGenre();
						ResultSet resultActor = this.getResultActor();
						
						MoviesResults moviesRes = new MoviesResults();
						moviesRes.setResultsMoive(resultMovie);
						moviesRes.setResultsGenre(resultGenre);
						moviesRes.setResultsActors(resultActor);
						moviesRes.addYoutubeAndPoster();
						
						System.out.println(moviesRes.getMoviesResult().toString());
					}
				});
				
			}
		});
		
		createContents();
	}
	
	
	
	private void massiveImport(Display display1, IdbOparations operations1 ){
		display1.syncExec(new thread_logic.ThreadImport(operations1){
			@Override
			public void run(){
				super.run();
				
			}
		});	
		}
	

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MoveIt!");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
