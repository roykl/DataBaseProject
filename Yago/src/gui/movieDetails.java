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
	/**
	 * Launch the application.
	 * @param args
	 */
	
	
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			movieDetails shell = new movieDetails(display);
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
		
		button10 = new Button(this, SWT.RADIO);
		button10.setText("10");
		button10.setBounds(447, 643, 27, 26);
		
		button9 = new Button(this, SWT.RADIO);
		button9.setText("9");
		button9.setBounds(414, 643, 27, 26);
		
		button8 = new Button(this, SWT.RADIO);
		button8.setText("8");
		button8.setBounds(381, 643, 27, 26);
		
		button7 = new Button(this, SWT.RADIO);
		button7.setText("7");
		button7.setBounds(348, 643, 27, 26);
		
		button6 = new Button(this, SWT.RADIO);
		button6.setText("6");
		button6.setBounds(315, 643, 27, 26);
		
		button5 = new Button(this, SWT.RADIO);
		button5.setText("5");
		button5.setBounds(281, 643, 27, 26);
		
		button4 = new Button(this, SWT.RADIO);
		button4.setText("4");
		button4.setBounds(248, 643, 27, 26);
		
		button3 = new Button(this, SWT.RADIO);
		button3.setText("3");
		button3.setBounds(215, 643, 27, 26);
		
		button2 = new Button(this, SWT.RADIO);
		button2.setBounds(182, 643, 27, 26);
		button2.setText("2");
		
		button1 = new Button(this, SWT.RADIO);
		button1.setBounds(149, 643, 27, 26);
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
		btnRankIt.setBounds(281, 612, 75, 25);
		btnRankIt.setText("Rank it!");
		
		lblYourRank = new Label(this, SWT.NONE);
		lblYourRank.setText("   Your Rank");
		lblYourRank.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblYourRank.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblYourRank.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblYourRank.setBounds(23, 643, 120, 25);
		
		txtGen = new Text(this, SWT.BORDER);
		if (movie.genresList.get(0)!=null)
		txtGen.setText(movie.genresList.get(0));
		txtGen.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtGen.setBounds(530, 598, 50, 24);
		
		txtActor_3 = new Text(this, SWT.BORDER);
		if (movie.actorsList.get(3) != null)
			txtActor_3.setText(movie.actorsList.get(3));
		 
		txtActor_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtActor_3.setBounds(640, 657, 112, 26);
		
		txtActor_2 = new Text(this, SWT.BORDER);
		if (movie.actorsList.get(2) != null)
			txtActor_2.setText(movie.actorsList.get(2));
		 
		txtActor_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtActor_2.setBounds(524, 657, 112, 26);
		
		txtActor = new Text(this, SWT.BORDER);
		if (movie.actorsList.get(0) != null)
		txtActor.setText(movie.actorsList.get(0));
		txtActor.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtActor.setBounds(524, 625, 112, 26);
		
		lblMovieitGrade = new Label(this, SWT.NONE);
		lblMovieitGrade.setText("Movie-it Rank");
		lblMovieitGrade.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblMovieitGrade.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblMovieitGrade.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblMovieitGrade.setBounds(23, 595, 120, 25);
		
		txtGenre_2 = new Text(this, SWT.BORDER);
		if (movie.genresList.get(3)!=null)
			txtGenre_2.setText(movie.genresList.get(3));
		 
		txtGenre_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtGenre_2.setBounds(702, 598, 50, 24);
		
		Label lblGenre = new Label(this, SWT.NONE);
		lblGenre.setText("   Genre");
		lblGenre.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblGenre.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblGenre.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblGenre.setBounds(437, 592, 81, 23);
		
		Label lblLanguage = new Label(this, SWT.NONE);
		lblLanguage.setText("Language");
		lblLanguage.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblLanguage.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblLanguage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblLanguage.setBounds(437, 561, 81, 26);
		
		Label lblDuration = new Label(this, SWT.NONE);
		lblDuration.setText(" Duration");
		lblDuration.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblDuration.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblDuration.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblDuration.setBounds(437, 529, 81, 26);
		
		txtYear = new Text(this, SWT.BORDER);
		txtYear.setText(movie.year);
		txtYear.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtYear.setBounds(557, 501, 195, 27);
		
		Label lblYear = new Label(this, SWT.NONE);
		lblYear.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		lblYear.setText("    Year");
		lblYear.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblYear.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblYear.setBounds(437, 498, 81, 26);
		
		txtLul_1 = new Text(this, SWT.BORDER);
		txtLul_1.setText(movie.directorName);
		txtLul_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtLul_1.setBounds(557, 471, 195, 27);
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblNewLabel_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel_2.setBounds(437, 471, 81, 26);
		lblNewLabel_2.setText(" Director");
		
		Browser browser_1 = new Browser(this, SWT.NONE);
		browser_1.setUrl(movie.posterUrl);
		browser_1.setBounds(23, 213, 345, 287);
		
		txtMovieName_1 = new Text(this, SWT.BORDER);
		txtMovieName_1.setText("     Trailer");
		txtMovieName_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		txtMovieName_1.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		txtMovieName_1.setBackground(SWTResourceManager.getColor(102, 102, 102));
		txtMovieName_1.setBounds(557, 170, 112, 27);
		
		Browser browser = new Browser(this, SWT.NONE);
		browser.setUrl(movie.youtubeUrl);
		browser.setBackground(SWTResourceManager.getColor(102, 102, 102));
		browser.setBounds(454, 202, 282, 260);
		
		txtMovieName = new Text(this, SWT.BORDER);
		txtMovieName.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.BOLD));
		txtMovieName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		txtMovieName.setBackground(SWTResourceManager.getColor(102, 102, 102));
		txtMovieName.setText(movie.movieName);
		txtMovieName.setBounds(23, 169, 238, 38);
		
		txtDuration = new Text(this, SWT.BORDER);
		txtDuration.setText(movie.duration);
		txtDuration.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtDuration.setBounds(557, 532, 195, 26);
		
		txtLanguage = new Text(this, SWT.BORDER);
		txtLanguage.setText(movie.language);
		txtLanguage.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtLanguage.setBounds(557, 564, 195, 27);
		
		Label lblPlot = new Label(this, SWT.NONE);
		lblPlot.setText(movie.plot);
		lblPlot.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblPlot.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblPlot.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblPlot.setBounds(23, 507, 44, 24);
		
		txtPlot = new Text(this, SWT.BORDER);
		txtPlot.setText("Plot");
		txtPlot.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtPlot.setBounds(73, 510, 295, 77);
		
		txtLul = new Text(this, SWT.BORDER);
		txtLul.setText(Double.toString(movie.grade));
		txtLul.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtLul.setBounds(159, 593, 50, 24);
		
		lblActors = new Label(this, SWT.NONE);
		lblActors.setText("   Actors");
		lblActors.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblActors.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblActors.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lblActors.setBounds(437, 621, 81, 23);
		
		txtActor_1 = new Text(this, SWT.BORDER);
		if (movie.actorsList.get(1) != null)
			txtActor_1.setText(movie.actorsList.get(1));

		txtActor_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtActor_1.setBounds(640, 625, 112, 26);
		
		txtGenre_1 = new Text(this, SWT.BORDER);
		if (movie.genresList.get(2)!=null)
			txtGenre_1.setText(movie.genresList.get(2));
		
		txtGenre_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtGenre_1.setBounds(646, 598, 50, 24);
		
		txtGenre = new Text(this, SWT.BORDER);
		if (movie.genresList.get(1)!=null)
			txtGenre.setText(movie.genresList.get(1));
		
		txtGenre.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		txtGenre.setBounds(586, 598, 50, 24);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText("BackGround");
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD));
		lblNewLabel.setImage(SWTResourceManager.getImage(movieDetails.class, "/movieDetails.jpg"));
		lblNewLabel.setBounds(10, -140, 790, 1054);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setSize(783, 798);

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
