package gui;


//TODO: give constructor the user id and movie id
//TODO: make movie name lable  

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import thread_logic.ThreadGrade;
import thread_logic.ThreadSearch;
import viewModelLayer.MovieInfo;
 
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.IdbOparations;


public class movieDetails extends Shell {
	private MovieInfo movie;
	private Text txtMovieName;
	private Text txtMovieName_1;
	private Text txtLul_1;
	private Text txtYear;
	private Text txtDuration;
	private Text txtLanguage;
	private Text txtGenre_2;
	private Text txtPlot;
	private Label lblMovieitGrade;
	private Text txtLul;
	private Label lblActors;
	private Text txtActor;
	private Text txtActor_1;
	private Text txtActor_2;
	private Text txtActor_3;
	private Text txtGenre_1;
	private Text txtGenre;
	private Text txtGen;
	private Label lblYourRank;
	private Button btnRankIt;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	private Button button9;
	private Button button10;
	private int idMovie; // TODO
	private Label lblNumberOfRankers;
	private Text txtRankers;
	private Label lblWiki;
	private Text txtWikiurl;
	/**
	 * Launch the application.
	 * @param args
	 */
	
	
	
	
	
	

	/**
	 * Create the shell.
	 * @param display
	 * @wbp.parser.constructor
	 */
	public movieDetails(final Display display, final IdbOparations operations,final int idUser, MovieInfo movie) {
		super(display, SWT.SHELL_TRIM);
		setText("MovieDetailes");
		setImage(SWTResourceManager.getImage(movieDetails.class, "/movieDetails.jpg"));
		
		this.movie = movie;
		
		txtWikiurl = new Text(this, SWT.BORDER);
		txtWikiurl.setText(movie.wikiUrl);
		txtWikiurl.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtWikiurl.setBounds(749, 429, 225, 27);
		
		lblWiki = new Label(this, SWT.BORDER);
		lblWiki.setText("   WIKI");
		lblWiki.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		lblWiki.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		lblWiki.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblWiki.setBounds(613, 431, 88, 25);
		
		txtRankers = new Text(this, SWT.BORDER);
		txtRankers.setText(Integer.toString(movie.numRankers));
		txtRankers.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtRankers.setBounds(204, 625, 54, 24);
		
		lblNumberOfRankers = new Label(this, SWT.BORDER);
		lblNumberOfRankers.setText("Number Of Rankers");
		lblNumberOfRankers.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblNumberOfRankers.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblNumberOfRankers.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblNumberOfRankers.setBounds(23, 625, 172, 25);
		
		button10 = new Button(this, SWT.BORDER | SWT.RADIO);
		button10.setText("10");
		button10.setBounds(480, 658, 37, 26);
		
		button9 = new Button(this, SWT.BORDER | SWT.RADIO);
		button9.setText("9");
		button9.setBounds(447, 658, 27, 26);
		
		button8 = new Button(this, SWT.BORDER | SWT.RADIO);
		button8.setText("8");
		button8.setBounds(414, 658, 27, 26);
		
		button7 = new Button(this, SWT.BORDER | SWT.RADIO);
		button7.setText("7");
		button7.setBounds(381, 658, 27, 26);
		
		button6 = new Button(this, SWT.BORDER | SWT.RADIO);
		button6.setText("6");
		button6.setBounds(348, 658, 27, 26);
		
		button5 = new Button(this, SWT.BORDER | SWT.RADIO);
		button5.setText("5");
		button5.setBounds(314, 658, 27, 26);
		
		button4 = new Button(this, SWT.BORDER | SWT.RADIO);
		button4.setText("4");
		button4.setBounds(281, 658, 27, 26);
		
		button3 = new Button(this, SWT.BORDER | SWT.RADIO);
		button3.setText("3");
		button3.setBounds(248, 658, 27, 26);
		
		button2 = new Button(this, SWT.BORDER | SWT.RADIO);
		button2.setBounds(215, 658, 27, 26);
		button2.setText("2");
		
		button1 = new Button(this, SWT.BORDER | SWT.RADIO);
		button1.setBounds(182, 658, 27, 26);
		button1.setText("1");
		
		btnRankIt = new Button(this, SWT.NONE);
		btnRankIt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateGrade(display,operations,idUser);
				updateLabel(display, operations);
			}
		});
		btnRankIt.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		btnRankIt.setBounds(530, 657, 75, 25);
		btnRankIt.setText("Rank it!");
		
		lblYourRank = new Label(this, SWT.BORDER);
		lblYourRank.setText("   Your Rank");
		lblYourRank.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblYourRank.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblYourRank.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblYourRank.setBounds(23, 659, 143, 25);
		
		txtGen = new Text(this, SWT.BORDER);
		if (movie.genresList.get(0)!=null)
		txtGen.setText(movie.genresList.get(0));
		txtGen.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtGen.setBounds(748, 593, 226, 24);
		
		txtActor_3 = new Text(this, SWT.BORDER);
		if (movie.actorsList.get(3) != null)
			txtActor_3.setText(movie.actorsList.get(3));
		 
		txtActor_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtActor_3.setBounds(748, 659, 112, 26);
		
		txtActor_2 = new Text(this, SWT.BORDER);
		if (movie.actorsList.get(2) != null)
			txtActor_2.setText(movie.actorsList.get(2));
		 
		txtActor_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtActor_2.setBounds(866, 659, 108, 25);
		
		txtActor = new Text(this, SWT.BORDER);
		if (movie.actorsList.get(0) != null)
		txtActor.setText(movie.actorsList.get(0));
		txtActor.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtActor.setBounds(748, 629, 112, 26);
		
		lblMovieitGrade = new Label(this, SWT.BORDER);
		lblMovieitGrade.setText("Movie-it Rank");
		lblMovieitGrade.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblMovieitGrade.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblMovieitGrade.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblMovieitGrade.setBounds(23, 578, 172, 25);
		
		txtGenre_2 = new Text(this, SWT.BORDER);
		if (movie.genresList.get(3)!=null)
			txtGenre_2.setText(movie.genresList.get(3));
		 
		txtGenre_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtGenre_2.setBounds(918, 593, 56, 24);
		
		Label lblGenre = new Label(this, SWT.BORDER);
		lblGenre.setText("   Genre");
		lblGenre.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblGenre.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblGenre.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblGenre.setBounds(613, 594, 88, 25);
		
		Label lblLanguage = new Label(this, SWT.BORDER);
		lblLanguage.setText("Language");
		lblLanguage.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblLanguage.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblLanguage.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblLanguage.setBounds(613, 560, 88, 27);
		
		Label lblDuration = new Label(this, SWT.BORDER);
		lblDuration.setText(" Duration");
		lblDuration.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblDuration.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblDuration.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblDuration.setBounds(613, 528, 88, 26);
		
		txtYear = new Text(this, SWT.BORDER);
		txtYear.setText(movie.year);
		txtYear.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtYear.setBounds(749, 495, 225, 27);
		
		Label lblYear = new Label(this, SWT.BORDER);
		lblYear.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		lblYear.setText("    Year");
		lblYear.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		lblYear.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblYear.setBounds(613, 495, 88, 27);
		
		txtLul_1 = new Text(this, SWT.BORDER);
		txtLul_1.setText(movie.directorName);
		txtLul_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtLul_1.setBounds(749, 462, 225, 27);
		
		Label lblNewLabel_2 = new Label(this, SWT.BORDER);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblNewLabel_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		lblNewLabel_2.setBounds(613, 462, 88, 25);
		lblNewLabel_2.setText(" Director");
		
		Browser browser_1 = new Browser(this, SWT.NONE);
		browser_1.setUrl(movie.posterUrl);
		browser_1.setBounds(23, 79, 321, 410);
		
		txtMovieName_1 = new Text(this, SWT.BORDER);
		txtMovieName_1.setText("     Trailer");
		txtMovieName_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		txtMovieName_1.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		txtMovieName_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtMovieName_1.setBounds(640, 28, 112, 27);
		
		Browser browser = new Browser(this, SWT.BORDER);
		browser.setUrl(movie.youtubeUrl);
		browser.setBackground(SWTResourceManager.getColor(102, 102, 102));
		browser.setBounds(552, 61, 469, 361);
		
		txtMovieName = new Text(this, SWT.BORDER);
		txtMovieName.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.BOLD));
		txtMovieName.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		txtMovieName.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtMovieName.setText(movie.movieName);
		txtMovieName.setBounds(23, 28, 238, 38);
		
		txtDuration = new Text(this, SWT.BORDER);
		txtDuration.setText(movie.duration);
		txtDuration.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtDuration.setBounds(749, 528, 225, 26);
		
		txtLanguage = new Text(this, SWT.BORDER);
		txtLanguage.setText(movie.language);
		txtLanguage.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtLanguage.setBounds(749, 560, 225, 27);
		
		Label lblPlot = new Label(this, SWT.BORDER | SWT.SHADOW_IN);
		lblPlot.setText("  Plot");
		lblPlot.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		lblPlot.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblPlot.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblPlot.setBounds(23, 495, 67, 32);
		
		txtPlot = new Text(this, SWT.BORDER);
		txtPlot.setText(movie.plot);
		txtPlot.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtPlot.setBounds(110, 495, 295, 77);
		
		txtLul = new Text(this, SWT.BORDER);
		txtLul.setText(Double.toString(movie.grade));
		txtLul.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtLul.setBounds(201, 578, 54, 24);
		
		lblActors = new Label(this, SWT.BORDER);
		lblActors.setText("   Actors");
		lblActors.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblActors.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblActors.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblActors.setBounds(613, 628, 88, 23);
		
		txtActor_1 = new Text(this, SWT.BORDER);
		if (movie.actorsList.get(1) != null)
			txtActor_1.setText(movie.actorsList.get(1));

		txtActor_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtActor_1.setBounds(866, 629, 108, 26);
		
		txtGenre_1 = new Text(this, SWT.BORDER);
		if (movie.genresList.get(2)!=null)
			txtGenre_1.setText(movie.genresList.get(2));
		
		txtGenre_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtGenre_1.setBounds(862, 593, 112, 24);
		
		txtGenre = new Text(this, SWT.BORDER);
		if (movie.genresList.get(1)!=null)
			txtGenre.setText(movie.genresList.get(1));
		
		txtGenre.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		txtGenre.setBounds(806, 593, 168, 24);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(movieDetails.class, "/movie-theaters-and-venues.jpg"));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(10, -36, 1095, 809);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setSize(1064, 825);

	}

	
	private int getGrade(){
		if(button1.getSelection())
			return 1;
		else if(button2.getSelection())
			return 2;
		else if(button3.getSelection())
			return 3;
		else if(button4.getSelection())
			return 4;
		else if(button5.getSelection())
			return 5;
		else if(button6.getSelection())
			return 6;
		else if(button7.getSelection())
			return 7;
		else if(button8.getSelection())
			return 8;
		else if(button9.getSelection())
			return 9;
		else if(button10.getSelection())
			return 10;
		return 0;
	}
	
	private void updateGrade(Display display, IdbOparations operations, int idUser){
		
		display.syncExec(new ThreadGrade(operations, idUser, idMovie,  getGrade()){
			@Override
			public void run(){
				super.run();
				}
			
			
		});
	}
	
	private void updateLabel(Display display, IdbOparations operations){
		display.syncExec(new ThreadSearch(operations, "grade, numbersOfRankers", "MoviesGrades", "idMovie = " + idMovie){
			@Override
			public void run(){
				super.run();
				try {
					int numbersOfRankers = this.getResult().getInt("numbersOfRankers");
					int grade = this.getResult().getInt("grade");
					txtLul.setText(Integer.toString(grade));
					//TODO: add number of rankers label
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
