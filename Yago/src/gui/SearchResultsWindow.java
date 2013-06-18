package gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import db.IdbOparations;

import viewModelLayer.MovieInfo;
import viewModelLayer.MoviesResults;

public class SearchResultsWindow extends Shell {

	/**
	 * Create the shell.
	 * @param display
	 */
	public SearchResultsWindow(final Display display, final IdbOparations operations,  final int idUser, final ArrayList<MovieInfo> moviesResult) {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(SearchResultsWindow.class, "/movies.png"));
		setMinimumSize(new Point(1024, 768));

		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(0, 0, 1006, 723);

		final List searchResultsList = new List(composite, SWT.V_SCROLL);
		searchResultsList.setItems(new String[] {});
		searchResultsList.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		searchResultsList.setBackground(SWTResourceManager.getColor(105, 105, 105));
		searchResultsList.setBounds(10, 10, 986, 475);

		Label backgroundLbl = new Label(composite, SWT.NONE);
		backgroundLbl.setImage(SWTResourceManager.getImage(SearchResultsWindow.class, "/video screen.jpg"));
		backgroundLbl.setBounds(0, 0, 1007, 723);

		//clear the list of previous results
		searchResultsList.removeAll();
		//no search results found
		if(moviesResult == null || moviesResult.isEmpty()){
			searchResultsList.add("Oops...Your search did not return any matches");
		}
		else{
			//add search results to the list
			for (MovieInfo movieInfo : moviesResult) {
				searchResultsList.add(movieInfo.movieName);
			}
			//one result opens movie details immediately
			if(moviesResult.size() == 1){
				//TODO - change idUser to meaningful
				MovieDetails detailsShell = new MovieDetails(display, operations, idUser,moviesResult.get(0));
				//dispose();
				detailsShell.open();
				detailsShell.layout();
				while (!detailsShell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		}

		searchResultsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				//TODO - change idUser to meaningful

				int selectionIndex = searchResultsList.getSelectionIndex();
				if(selectionIndex < 0)
					return;
				MovieDetails detailsShell = new MovieDetails(display, operations, idUser, 
						moviesResult.get(searchResultsList.getSelectionIndex()));

				System.out.println("************************"+moviesResult.get(searchResultsList.getSelectionIndex()).movieName+"********************************************");
				for (MovieInfo movieInfo : moviesResult) {
					System.out.println(movieInfo.movieName);
				}
				System.out.println("**************************************************************************************************************************");
				//dispose();
				detailsShell.open();
				detailsShell.layout();
				while (!detailsShell.isDisposed()) {
					if (!display.readAndDispatch()) {							
						display.sleep();
					}
				}

				System.out.println("Disposed");
			}
		});
		
		createContents();
	}

	
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MovIt!");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
