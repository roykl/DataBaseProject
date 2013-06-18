package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.browser.Browser;
import viewModelLayer.MovieInfo;

public class TrailerWindow extends Shell {

	/**
	 * Create the shell.
	 * @param display
	 */
	public TrailerWindow(Display display, final MovieInfo movie) {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(TrailerWindow.class, "/movies.png"));
		setMinimumSize(new Point(800, 600));
		setLayout(new FillLayout(SWT.HORIZONTAL));
		Browser trailerBrowser = new Browser(this, SWT.BORDER);
		trailerBrowser.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		trailerBrowser.setUrl(movie.youtubeUrl);
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
