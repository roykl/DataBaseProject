package gui;

import java.sql.ResultSet;
import java.sql.SQLException;

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

import viewModelLayer.MoviesResults;
import viewModelLayer.SearchQueries;

public class MainMenu extends Shell {
	private Text txtMovieTitle;
	private Text text_4;
	private Text text_5;
	private Text text_6;
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
		list.setBounds(21, 20, 997, 481);

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(MainMenu.class, "/video screen.jpg"));
		lblNewLabel.setBounds(10, 10, 1024, 628);

		txtMovieTitle = new Text(this, SWT.BORDER);
		FormData fd_txtMovieTitle = new FormData();
		fd_txtMovieTitle.right = new FormAttachment(composite, -20);
		fd_txtMovieTitle.left = new FormAttachment(0, 7);
		txtMovieTitle.setLayoutData(fd_txtMovieTitle);

		Button btnSearch = new Button(this, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			//Search button pressed
			public void widgetSelected(SelectionEvent arg0) {
				//parameters for select - if you need you can send few of these threads
				String select = SearchQueries.MOVIE_SELECT;
				String from = SearchQueries.MOVIE_FROM;
				String where= null;
				// check if user enered movie name
				boolean eneredMoive = txtMovieTitle.getText().trim().isEmpty()? false : true;				
				if (eneredMoive) // if entered a movie, find that movie
					where = "movie.movieName = '" + txtMovieTitle.getText() +"'";
				else // user didn't enter movieName so we use the advanced properties
				    where ="";
				display.syncExec(new thread_logic.ThreadSearch(operations,select,from,where){
					@Override
					public void run(){
						super.run();
						ResultSet result = this.getResult();
						MoviesResults moviesRes = new MoviesResults();
						moviesRes.setResultsMoive(result);
						System.out.println(moviesRes.getMoviesResult().toString());
						
						//  TODO: ResultSet => MovieInfo =>  table
					}
					


				});	
			}
		});
		fd_expandBar.bottom = new FormAttachment(btnSearch, -6);

		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setText("Genre");

		ScrolledComposite scrolledComposite = new ScrolledComposite(expandBar, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		xpndtmNewExpanditem.setControl(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		xpndtmNewExpanditem.setHeight(120);

		Composite composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));

		Button btnHorror = new Button(composite_1, SWT.CHECK);
		btnHorror.setText("Horror");

		Button btnDocumentary = new Button(composite_1, SWT.CHECK);
		btnDocumentary.setText("Documentary");

		Button btnScifi = new Button(composite_1, SWT.CHECK);
		btnScifi.setText("Sci-Fi");

		Button btnHistory = new Button(composite_1, SWT.CHECK);
		btnHistory.setText("History");

		Button btnWestern = new Button(composite_1, SWT.CHECK);
		btnWestern.setText("Western");

		Button btnCheckButton_4 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_4.setText("Romance");

		Button btnCheckButton_5 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_5.setText("Musical");

		Button btnCheckButton_6 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_6.setText("Animation");

		Button btnCheckButton_7 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_7.setText("Mystery");

		Button btnCheckButton_8 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_8.setText("War");

		Button btnCheckButton_9 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_9.setText("News");

		Button btnCheckButton_10 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_10.setText("Crime");

		Button btnCheckButton_11 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_11.setText("Drama");

		Button btnCheckButton_12 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_12.setText("Music");

		Button btnCheckButton_13 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_13.setText("Short");

		Button btnCheckButton_14 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_14.setText("Sport");

		Button btnCheckButton_15 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_15.setText("Fantasy");

		Button btnCheckButton_16 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_16.setText("Reality-TV");

		Button btnCheckButton_17 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_17.setText("Film-Noir");

		Button btnCheckButton_18 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_18.setText("Adventure");

		Button btnCheckButton_19 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_19.setText("Thriller");

		Button btnCheckButton_20 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_20.setText("Action");

		Button btnCheckButton_21 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_21.setText("Comedy");

		Button btnCheckButton_22 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_22.setText("Biography");

		Button btnCheckButton_23 = new Button(composite_1, SWT.CHECK);
		btnCheckButton_23.setText("Family");
		scrolledComposite.setContent(composite_1);
		scrolledComposite.setMinSize(composite_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		FormData fd_btnSearch = new FormData();
		fd_btnSearch.top = new FormAttachment(0, 473);
		fd_btnSearch.left = new FormAttachment(0, 51);
		fd_btnSearch.right = new FormAttachment(0, 223);
		btnSearch.setLayoutData(fd_btnSearch);
		btnSearch.setText("Search");

		Label lblMovieTitle = new Label(this, SWT.NONE);
		lblMovieTitle.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		fd_txtMovieTitle.top = new FormAttachment(0, 51);
		lblMovieTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		FormData fd_lblMovieTitle = new FormData();
		fd_lblMovieTitle.bottom = new FormAttachment(txtMovieTitle, -9);
		fd_lblMovieTitle.left = new FormAttachment(0, 10);
		lblMovieTitle.setLayoutData(fd_lblMovieTitle);
		lblMovieTitle.setText("Movie title");

		Label lblAdvancedSearch = new Label(this, SWT.NONE);
		lblAdvancedSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		fd_txtMovieTitle.bottom = new FormAttachment(lblAdvancedSearch, -43);
		fd_expandBar.top = new FormAttachment(0, 135);

		ExpandItem xpndtmDirectorName = new ExpandItem(expandBar, 0);
		xpndtmDirectorName.setText("Director");

		Composite composite_3 = new Composite(expandBar, SWT.NONE);
		xpndtmDirectorName.setControl(composite_3);
		composite_3.setLayout(new FormLayout());
		
		text = new Text(composite_3, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(0, 162);
		fd_text.bottom = new FormAttachment(100, -8);
		fd_text.left = new FormAttachment(0, 10);
		text.setLayoutData(fd_text);
		xpndtmDirectorName.setHeight(40);

		ExpandItem xpndtmActors = new ExpandItem(expandBar, 0);
		xpndtmActors.setText("Actors");

		Composite composite_4 = new Composite(expandBar, SWT.NONE);
		xpndtmActors.setControl(composite_4);
		composite_4.setLayout(new FormLayout());
		
		text_6 = new Text(composite_4, SWT.BORDER);
		FormData fd_text_6 = new FormData();
		fd_text_6.right = new FormAttachment(100, -94);
		fd_text_6.left = new FormAttachment(0, 10);
		text_6.setLayoutData(fd_text_6);
		
		text_5 = new Text(composite_4, SWT.BORDER);
		fd_text_6.bottom = new FormAttachment(text_5, -6);
		FormData fd_text_5 = new FormData();
		fd_text_5.right = new FormAttachment(text_6, 0, SWT.RIGHT);
		fd_text_5.top = new FormAttachment(0, 57);
		fd_text_5.left = new FormAttachment(0, 10);
		text_5.setLayoutData(fd_text_5);
		
		text_4 = new Text(composite_4, SWT.BORDER);
		FormData fd_text_4 = new FormData();
		fd_text_4.bottom = new FormAttachment(text_6, -6);
		fd_text_4.left = new FormAttachment(text_6, 0, SWT.LEFT);
		fd_text_4.right = new FormAttachment(100, -94);
		text_4.setLayoutData(fd_text_4);
		xpndtmActors.setHeight(89);

		ExpandItem xpndtmYear = new ExpandItem(expandBar, 0);
		xpndtmYear.setText("Years");

		Composite composite_5 = new Composite(expandBar, SWT.NONE);
		xpndtmYear.setControl(composite_5);
		composite_5.setLayout(null);

		Label lblNewLabel_1 = new Label(composite_5, SWT.NONE);
		lblNewLabel_1.setBounds(47, 4, 86, 20);
		lblNewLabel_1.setText("From:");
		
		Spinner spinner = new Spinner(composite_5, SWT.BORDER);
		spinner.setTextLimit(4);
		spinner.setMaximum(2013);
		spinner.setMinimum(1900);
		spinner.setBounds(47, 30, 62, 22);

		Label lblTo = new Label(composite_5, SWT.NONE);
		lblTo.setText("To:");
		lblTo.setBounds(48, 62, 86, 20);
		
		Spinner spinner_1 = new Spinner(composite_5, SWT.BORDER);
		spinner_1.setTextLimit(4);
		spinner_1.setMaximum(2013);
		spinner_1.setMinimum(1900);
		spinner_1.setSelection(2103);
		spinner_1.setBounds(47, 88, 62, 22);
		xpndtmYear.setHeight(140);

		ExpandItem xpndtmNewExpanditem_1 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem_1.setText("Language");
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(expandBar, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		xpndtmNewExpanditem_1.setControl(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		Composite composite_2 = new Composite(scrolledComposite_1, SWT.NONE);
		composite_2.setLayout(new GridLayout(1, false));
		
		Button btnRadioButton_1 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_1.setText("English");
		
		Button btnRadioButton_2 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_2.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		btnRadioButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnRadioButton_2.setText("Hindi");
		
		Button btnRadioButton_3 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_3.setText("Spanish");
		
		Button btnRadioButton_4 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_4.setText("French");
		
		Button btnRadioButton_5 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_5.setText("Italian");
		
		Button btnRadioButton_6 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_6.setText("German");
		
		Button btnRadioButton_7 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_7.setText("Malayalam");
		
		Button btnRadioButton_8 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_8.setText("Telugu");
		
		Button btnRadioButton_9 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_9.setText("Japanese");
		
		Button btnRadioButton_10 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_10.setText("Bengali");
		
		Button btnRadioButton_11 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_11.setText("Danish");
		
		Button btnRadioButton_12 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_12.setText("Dutch");
		
		Button btnRadioButton = new Button(composite_2, SWT.RADIO);
		btnRadioButton.setText("Filipino");
		
		Button btnRadioButton_13 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_13.setBounds(0, 0, 90, 16);
		btnRadioButton_13.setText("Portuguese");
		
		Button btnRadioButton_14 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_14.setBounds(0, 0, 90, 16);
		btnRadioButton_14.setText("Russian");
		
		Button btnRadioButton_15 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_15.setBounds(0, 0, 90, 16);
		btnRadioButton_15.setText("Arabic");
		
		Button btnRadioButton_16 = new Button(composite_2, SWT.RADIO);
		btnRadioButton_16.setBounds(0, 0, 90, 16);
		btnRadioButton_16.setText("Hebrew");
		scrolledComposite_1.setContent(composite_2);
		scrolledComposite_1.setMinSize(composite_2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		xpndtmNewExpanditem_1.setHeight(120);
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
					//TODO
				}
			});
			btnImport.setText("Import");
			FormData fd_btnImport = new FormData();
			fd_btnImport.left = new FormAttachment(btnSearch, 0, SWT.LEFT);
			fd_btnImport.top = new FormAttachment(btnSearch, 19);
			fd_btnImport.right = new FormAttachment(btnSearch, 0, SWT.RIGHT);
			btnImport.setLayoutData(fd_btnImport);
		}
		createContents();
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
