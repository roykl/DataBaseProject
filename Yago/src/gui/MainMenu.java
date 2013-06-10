package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.RowLayout;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MainMenu extends Shell {
	private Text txtMovieTitle;
	private Text text;
	private Text txtPleaseEnterEach;
	private Text text_1;
	private Text text_2;
	private Text text_3;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			MainMenu shell = new MainMenu(display);
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
	 */
	public MainMenu(Display display) {
		super(display, SWT.SHELL_TRIM);
		setSize(1353, 678);
		setBackground(SWTResourceManager.getColor(128, 0, 0));
		setImage(SWTResourceManager.getImage(MainMenu.class, "/movies.png"));
		setLayout(new FormLayout());
		
		ExpandBar expandBar = new ExpandBar(this, SWT.V_SCROLL);
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
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(MainMenu.class, "/video screen.jpg"));
		lblNewLabel.setBounds(10, 10, 1024, 628);
		
		txtMovieTitle = new Text(this, SWT.BORDER);
		FormData fd_txtMovieTitle = new FormData();
		fd_txtMovieTitle.right = new FormAttachment(composite, -20);
		fd_txtMovieTitle.left = new FormAttachment(0, 7);
		txtMovieTitle.setLayoutData(fd_txtMovieTitle);
		
		Button btnSearch = new Button(this, SWT.NONE);
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
		fd_txtMovieTitle.top = new FormAttachment(0, 51);
		lblMovieTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		FormData fd_lblMovieTitle = new FormData();
		fd_lblMovieTitle.bottom = new FormAttachment(txtMovieTitle, -9);
		fd_lblMovieTitle.left = new FormAttachment(0, 10);
		lblMovieTitle.setLayoutData(fd_lblMovieTitle);
		lblMovieTitle.setText("Movie title");
		
		Label lblAdvancedSearch = new Label(this, SWT.NONE);
		fd_txtMovieTitle.bottom = new FormAttachment(lblAdvancedSearch, -33);
		fd_expandBar.top = new FormAttachment(0, 135);
		
		ExpandItem xpndtmDirectorName = new ExpandItem(expandBar, 0);
		xpndtmDirectorName.setText("Director");
		
		Composite composite_3 = new Composite(expandBar, SWT.NONE);
		xpndtmDirectorName.setControl(composite_3);
		composite_3.setLayout(new FormLayout());
		
		text = new Text(composite_3, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(0);
		fd_text.left = new FormAttachment(0, 10);
		fd_text.bottom = new FormAttachment(0, 41);
		fd_text.right = new FormAttachment(0, 247);
		text.setLayoutData(fd_text);
		xpndtmDirectorName.setHeight(54);
		
		ExpandItem xpndtmActors = new ExpandItem(expandBar, 0);
		xpndtmActors.setText("Actors");
		
		Composite composite_4 = new Composite(expandBar, SWT.NONE);
		xpndtmActors.setControl(composite_4);
		composite_4.setLayout(new FormLayout());
		
		txtPleaseEnterEach = new Text(composite_4, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		FormData fd_txtPleaseEnterEach = new FormData();
		fd_txtPleaseEnterEach.bottom = new FormAttachment(0, 78);
		fd_txtPleaseEnterEach.right = new FormAttachment(0, 247);
		fd_txtPleaseEnterEach.top = new FormAttachment(0);
		fd_txtPleaseEnterEach.left = new FormAttachment(0, 10);
		txtPleaseEnterEach.setLayoutData(fd_txtPleaseEnterEach);
		txtPleaseEnterEach.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				txtPleaseEnterEach.setText("");
			}
		});
		txtPleaseEnterEach.setText("Please enter each name in a separate line.");
		xpndtmActors.setHeight(89);
		
		ExpandItem xpndtmYear = new ExpandItem(expandBar, 0);
		xpndtmYear.setText("Years");
		
		Composite composite_5 = new Composite(expandBar, SWT.NONE);
		xpndtmYear.setControl(composite_5);
		composite_5.setLayout(null);
		
		text_1 = new Text(composite_5, SWT.BORDER);
		text_1.setBounds(48, 30, 99, 26);
		
		Label lblNewLabel_1 = new Label(composite_5, SWT.NONE);
		lblNewLabel_1.setBounds(47, 4, 86, 20);
		lblNewLabel_1.setText("From:");
		
		Label lblyyyy = new Label(composite_5, SWT.NONE);
		lblyyyy.setBounds(153, 33, 70, 20);
		lblyyyy.setText("(YYYY)");
		
		Label lblTo = new Label(composite_5, SWT.NONE);
		lblTo.setText("To:");
		lblTo.setBounds(48, 62, 86, 20);
		
		text_2 = new Text(composite_5, SWT.BORDER);
		text_2.setBounds(48, 88, 99, 26);
		
		Label label = new Label(composite_5, SWT.NONE);
		label.setText("(YYYY)");
		label.setBounds(153, 88, 70, 20);
		xpndtmYear.setHeight(140);
		
		ExpandItem xpndtmNewExpanditem_1 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem_1.setText("Language");
		
		Composite composite_2 = new Composite(expandBar, SWT.NONE);
		xpndtmNewExpanditem_1.setControl(composite_2);
		
		text_3 = new Text(composite_2, SWT.BORDER);
		text_3.setBounds(10, 0, 237, 41);
		xpndtmNewExpanditem_1.setHeight(54);
		lblAdvancedSearch.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		FormData fd_lblAdvancedSearch = new FormData();
		fd_lblAdvancedSearch.bottom = new FormAttachment(expandBar, -1);
		fd_lblAdvancedSearch.left = new FormAttachment(0, 10);
		lblAdvancedSearch.setLayoutData(fd_lblAdvancedSearch);
		lblAdvancedSearch.setText("Advanced search options");
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
