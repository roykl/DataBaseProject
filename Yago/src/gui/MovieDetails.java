package gui;

//TODO: give constructor the user id and movie id
//TODO: make movie name lable  

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import thread_logic.ThreadGrade;
import thread_logic.ThreadSearch;
import thread_logic.ThreadUserUpdate;
import viewModelLayer.MovieInfo;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.IdbOparations;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Composite;

public class MovieDetails extends Shell {
	private MovieInfo movie;
	private Text txtMovieName;
	private Text trailerTxt;
	private Text txtDirector;
	private Text txtYear;
	private Text txtLanguage;
	private Text txtPlot;
	private Label lblMovitGrade;
	private Text txtRating;
	private Label lblActors;
	private Button btnRankIt;
	private int idMovie; // TODO
	private Label lblNumberOfRankers;
	private Text txtNumOfRankers;
	private Label lblWiki;
	private Text txtWikiurl;
	private int grade;
	private Text genresTxt;
	private Label lblUsers;
	private Composite composite;
	private Text txtRated;
	private Composite composite_1;
	private Composite composite_2;
	private Composite composite_3;
	private Composite composite_4;
	private Text txtDuration;
	private Label label;
	private Composite composite_6;

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public MovieDetails(final Display display, final IdbOparations operations,
			final int idUser, final MovieInfo movie) {
		super(display, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);
		setSize(1032, 992);
		setMinimumSize(new Point(1030, 998));
		setText("MovIt!");
		setImage(SWTResourceManager.getImage(MovieDetails.class, "/movies.png"));
		this.movie = movie;

		try{
			composite = new Composite(this, SWT.V_SCROLL);
			composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			composite.setBounds(0, 0, 1045, 965);

			composite_4 = new Composite(composite, SWT.NONE);
			composite_4.setBounds(10, 191, 343, 484);

			Browser posterBrowser = new Browser(composite_4, SWT.BORDER);
			posterBrowser.setBounds(0, 0, 343, 484);
			posterBrowser.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			

			composite_1 = new Composite(composite, SWT.BORDER);
			composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			composite_1.setBounds(606, 142, 410, 158);

			lblUsers = new Label(composite_1, SWT.NONE);
			lblUsers.setBounds(351, 26, 46, 38);
			lblUsers.setText("users");
			lblUsers.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblUsers.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblUsers.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			final Combo userRank = new Combo(composite_1, SWT.READ_ONLY);
			userRank.setBounds(150, 70, 88, 42);
			userRank.setItems(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});

			txtNumOfRankers = new Text(composite_1, SWT.BORDER | SWT.CENTER);
			txtNumOfRankers.setBounds(202, 23, 132, 26);
			txtNumOfRankers.setText("-");
			txtNumOfRankers.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			lblNumberOfRankers = new Label(composite_1, SWT.NONE);
			lblNumberOfRankers.setBounds(165, 26, 31, 38);
			lblNumberOfRankers.setText("by");
			lblNumberOfRankers.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblNumberOfRankers.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblNumberOfRankers.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			btnRankIt = new Button(composite_1, SWT.NONE);
			btnRankIt.setBounds(105, 118, 187, 28);
			//updating new user rating
			btnRankIt.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					grade = userRank.getSelectionIndex();
					updateGrade(display, operations, idUser);
					updateLabel(display, operations);
				}
			});
			btnRankIt.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			btnRankIt.setText("Rate");

			lblMovitGrade = new Label(composite_1, SWT.NONE);
			lblMovitGrade.setBounds(17, 73, 99, 25);
			lblMovitGrade.setText("Your rating");
			lblMovitGrade.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblMovitGrade.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblMovitGrade.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtRating = new Text(composite_1, SWT.BORDER | SWT.CENTER);
			txtRating.setBounds(76, 23, 73, 26);
			txtRating.setText("-");
			txtRating.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			txtRated = new Text(composite_1, SWT.NONE);
			txtRated.setBounds(17, 26, 53, 26);
			txtRated.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			txtRated.setText("Rated");

			composite_2 = new Composite(composite, SWT.BORDER);
			composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			composite_2.setBounds(606, 306, 410, 214);

			genresTxt = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
			genresTxt.setBounds(23, 41, 258, 28);

			List list = new List(composite_2, SWT.BORDER | SWT.V_SCROLL);
			list.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			list.setBounds(23, 105, 258, 92);



			Label lblGenre = new Label(composite_2, SWT.NONE);
			lblGenre.setBounds(23, 10, 52, 25);
			lblGenre.setText("Genre");
			lblGenre.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblGenre.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblGenre.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			lblActors = new Label(composite_2, SWT.NONE);
			lblActors.setBounds(23, 75, 60, 28);
			lblActors.setText("Actors");
			lblActors.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblActors.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblActors
			.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			Button btnNewButton = new Button(composite_2, SWT.NONE);
			btnNewButton.setBounds(296, 41, 100, 156);
			btnNewButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// call add remove form
					AddRemoveWindow shell = new AddRemoveWindow(display,operations, new MovieInfo());
					shell.open();
					shell.layout();
					while (!shell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
				}
			});
			btnNewButton.setText("Add/Remove");

			composite_3 = new Composite(composite, SWT.BORDER);
			composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			composite_3.setBounds(359, 142, 239, 378);

			Label lblLanguage = new Label(composite_3, SWT.NONE);
			lblLanguage.setBounds(10, 55, 79, 24);
			lblLanguage.setText("Language");
			lblLanguage.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblLanguage.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblLanguage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtYear = new Text(composite_3, SWT.BORDER);
			txtYear.setBounds(10, 22, 215, 27);
			txtYear.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.keyCode == SWT.CR) {

						update(operations, "Movie", movie.idMovie, -1, "year",
								txtYear.getText(),txtYear,display);

					}

				}
			});
			
			txtYear.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			Label lblYear = new Label(composite_3, SWT.NONE);
			lblYear.setBounds(10, 0, 64, 27);
			lblYear.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblYear.setText("Year");
			lblYear.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblYear.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtLanguage = new Text(composite_3, SWT.BORDER);
			txtLanguage.setBounds(10, 85, 215, 27);
			txtLanguage.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.keyCode == SWT.CR) {

						update(operations, "Movie", movie.idMovie, -1, "language",
								txtYear.getText(),txtLanguage,display);

					}

				}
			});
			
			txtLanguage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			Label lblNewLabel_2 = new Label(composite_3, SWT.NONE);
			lblNewLabel_2.setBounds(10, 117, 67, 29);
			lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			lblNewLabel_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblNewLabel_2.setText("Director");

			txtDirector = new Text(composite_3, SWT.BORDER);
			txtDirector.setBounds(10, 148, 215, 27);
			txtDirector.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.keyCode == SWT.CR) {

						update(operations, "Movie", movie.idMovie, -1, "idDirector",
								txtDirector.getText(),txtDirector,display);

					}

				}
			});
			
			txtDirector.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			txtDuration = new Text(composite_3, SWT.BORDER);
			txtDuration.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			txtDuration.setBounds(10, 215, 215, 28);
			

			label = new Label(composite_3, SWT.NONE);
			label.setText("Duration");
			label.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			label.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			label.setBounds(10, 181, 79, 23);

			lblWiki = new Label(composite_3, SWT.NONE);
			lblWiki.setBounds(10, 261, 76, 25);
			lblWiki.setText("Wikipedia");
			lblWiki.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblWiki.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblWiki.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtWikiurl = new Text(composite_3, SWT.BORDER | SWT.WRAP);
			txtWikiurl.setBounds(10, 292, 215, 72);
			txtWikiurl.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.keyCode == SWT.CR) {

						update(operations, "Movie", movie.idMovie, -1, "wiki",
								txtWikiurl.getText(),txtWikiurl,display);

					}

				}
			});
			txtWikiurl.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			Label backgroundLabl = new Label(composite, SWT.NONE);
			backgroundLabl.setBounds(0, 0, 1024, 136);
			backgroundLabl.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			backgroundLabl.setForeground(SWTResourceManager.getColor(255, 255, 255));
			backgroundLabl.setImage(SWTResourceManager.getImage(MovieDetails.class, "/movie_clapper.jpg"));
			backgroundLabl.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			composite_6 = new Composite(composite, SWT.BORDER);
			composite_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			composite_6.setBounds(362, 526, 654, 432);

			trailerTxt = new Text(composite_6, SWT.NONE);
			trailerTxt.setBounds(10, 146, 88, 20);
			trailerTxt.setText("Trailer");
			trailerTxt.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			trailerTxt.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			trailerTxt.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			Browser trailerBrowser = new Browser(composite_6, SWT.BORDER);
			trailerBrowser.setBounds(10, 172, 630, 246);
			trailerBrowser.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			txtPlot = new Text(composite_6, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL);
			txtPlot.setBounds(10, 36, 630, 92);
			
			txtPlot.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			
			Label lblPlot = new Label(composite_6, SWT.SHADOW_IN);
			lblPlot.setBounds(10, 10, 43, 27);
			lblPlot.setText("Plot");
			lblPlot.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblPlot.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblPlot.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtMovieName = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
			txtMovieName.setBounds(10, 142, 343, 43);
			txtMovieName.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
			txtMovieName.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			txtMovieName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			
			//trying to update all fields
			if(movie.plot != null)
				txtPlot.setText(movie.plot);
			if(movie.duration != null)
				txtDuration.setText(movie.duration);
			if(movie.directorName != null)
				txtDirector.setText(movie.directorName);
			if(movie.language != null)
				txtLanguage.setText(movie.language);
			if(movie.year != null)
				txtYear.setText(movie.year);
			if(movie.posterUrl != null)
				posterBrowser.setUrl(movie.posterUrl);
			if(movie.movieName != null)
				txtMovieName.setText(movie.movieName);
			if(movie.youtubeUrl != null)
				trailerBrowser.setUrl(movie.youtubeUrl);
			if(movie.wikiUrl != null)
				txtWikiurl.setText(movie.wikiUrl);
			if(movie.genresList != null){
				String genresToString = "";
				for (String genre : movie.genresList.values()) {
					genresToString+= genre+"-";
				}
				genresToString = genresToString.substring(0, genresToString.length()-1);
				genresTxt.setText(genresToString);
			}
			
			createContents();
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
	}

	private void update(IdbOparations operations, final String tableName,
			int idMovie, int key2, String column, final String newVal, final Text txtBox,final Display display) {
		display.syncExec(new ThreadUserUpdate(operations, tableName, idMovie, key2, column, newVal.hashCode()){
			@Override
			public void run(){	
				super.run();
				int returnVal= this.getValue();
				if (returnVal==OK){
					txtBox.setText(newVal);
					MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_INFORMATION);
					messageBox.setText("Your changes have been saved!");
					messageBox.setMessage("successfully Updated!");
					messageBox.open();


				}

				else if(returnVal == NOT_EXIST){
					MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_ERROR);
					messageBox.setText("Error");
					messageBox.setMessage("Value must exist in our " +tableName+ " data.");
					messageBox.open();

				}

			}
		});

	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
	}

	private void updateGrade(Display display, IdbOparations operations,
			int idUser) {

		display.syncExec(new ThreadGrade(operations, idUser, idMovie, grade) {
			@Override
			public void run() {
				super.run();
			}
		});
	}

	private void updateLabel(Display display, IdbOparations operations) {
		display.syncExec(new ThreadSearch(operations,
				"grade, numbersOfRankers", "MoviesGrades", "idMovie = "
						+ idMovie) {
			@Override
			public void run() {
				super.run();
				try {
					int numbersOfRankers = this.getResult().getInt("numbersOfRankers");
					int grade = this.getResult().getInt("grade");
					txtRating.setText(Integer.toString(grade));
					// TODO: add number of rankers label
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
