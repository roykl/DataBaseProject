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

public class MainMenu extends Shell {
	private Text txtMovieTitle;
	private Text txtDirector;
	private Text txtActorsseperatedBy;
	private Text text;

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
		setSize(1342, 685);
		setBackground(SWTResourceManager.getColor(128, 0, 0));
		setImage(SWTResourceManager.getImage(MainMenu.class, "/movies.png"));
		setLayout(new FormLayout());
		
		ExpandBar expandBar = new ExpandBar(this, SWT.V_SCROLL);
		expandBar.setSpacing(8);
		expandBar.setBackground(SWTResourceManager.getColor(128, 0, 0));
		FormData fd_expandBar = new FormData();
		fd_expandBar.left = new FormAttachment(0, 10);
		fd_expandBar.right = new FormAttachment(0, 279);
		expandBar.setLayoutData(fd_expandBar);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(0, 0, 0));
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.right = new FormAttachment(100, 5);
		fd_composite.bottom = new FormAttachment(0, 649);
		fd_composite.left = new FormAttachment(0, 292);
		composite.setLayoutData(fd_composite);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(MainMenu.class, "/video screen.jpg"));
		lblNewLabel.setBounds(10, 0, 1027, 628);
		
		txtMovieTitle = new Text(this, SWT.BORDER);
		txtMovieTitle.setText("Movie title");
		FormData fd_txtMovieTitle = new FormData();
		fd_txtMovieTitle.top = new FormAttachment(0, 40);
		fd_txtMovieTitle.right = new FormAttachment(composite, -6);
		fd_txtMovieTitle.left = new FormAttachment(0, 10);
		txtMovieTitle.setLayoutData(fd_txtMovieTitle);
		
		Button btnSearch = new Button(this, SWT.NONE);
		fd_expandBar.bottom = new FormAttachment(100, -109);
		fd_txtMovieTitle.bottom = new FormAttachment(100, -570);
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setText("Choose genres");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(expandBar, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		xpndtmNewExpanditem.setControl(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		xpndtmNewExpanditem.setHeight(xpndtmNewExpanditem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		
		Composite composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		
		Button button = new Button(composite_1, SWT.CHECK);
		button.setBounds(0, 0, 111, 20);
		button.setText("Check Button");
		
		Button button_1 = new Button(composite_1, SWT.CHECK);
		button_1.setBounds(0, 0, 111, 20);
		button_1.setText("Check Button");
		
		Button button_2 = new Button(composite_1, SWT.CHECK);
		button_2.setBounds(0, 0, 111, 20);
		button_2.setText("Check Button");
		
		Button button_3 = new Button(composite_1, SWT.CHECK);
		button_3.setBounds(0, 0, 111, 20);
		button_3.setText("Check Button");
		
		Button button_4 = new Button(composite_1, SWT.CHECK);
		button_4.setBounds(0, 0, 111, 20);
		button_4.setText("Check Button");
		scrolledComposite.setContent(composite_1);
		scrolledComposite.setMinSize(composite_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		ExpandItem xpndtmNewExpanditem_1 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem_1.setText("Choose language");
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(expandBar, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		xpndtmNewExpanditem_1.setControl(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		xpndtmNewExpanditem_1.setHeight(xpndtmNewExpanditem_1.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		
		Composite composite_2 = new Composite(scrolledComposite_1, SWT.NONE);
		composite_2.setLayout(new GridLayout(1, false));
		
		Button btnCheckButton = new Button(composite_2, SWT.CHECK);
		btnCheckButton.setBounds(0, 0, 111, 20);
		btnCheckButton.setText("Check Button");
		
		Button btnCheckButton_1 = new Button(composite_2, SWT.CHECK);
		btnCheckButton_1.setBounds(0, 0, 111, 20);
		btnCheckButton_1.setText("Check Button");
		
		Button btnCheckButton_2 = new Button(composite_2, SWT.CHECK);
		btnCheckButton_2.setBounds(0, 0, 111, 20);
		btnCheckButton_2.setText("Check Button");
		
		Button btnCheckButton_3 = new Button(composite_2, SWT.CHECK);
		btnCheckButton_3.setBounds(0, 0, 111, 20);
		btnCheckButton_3.setText("Check Button");
		scrolledComposite_1.setContent(composite_2);
		scrolledComposite_1.setMinSize(composite_2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		FormData fd_btnSearch = new FormData();
		fd_btnSearch.top = new FormAttachment(expandBar, 6);
		fd_btnSearch.left = new FormAttachment(0, 50);
		fd_btnSearch.right = new FormAttachment(0, 222);
		btnSearch.setLayoutData(fd_btnSearch);
		btnSearch.setText("Search");
		
		txtDirector = new Text(this, SWT.BORDER);
		txtDirector.setText("Director");
		FormData fd_txtDirector = new FormData();
		fd_txtDirector.left = new FormAttachment(0, 10);
		fd_txtDirector.right = new FormAttachment(composite, -6);
		fd_txtDirector.bottom = new FormAttachment(txtMovieTitle, 49, SWT.BOTTOM);
		fd_txtDirector.top = new FormAttachment(txtMovieTitle, 19);
		txtDirector.setLayoutData(fd_txtDirector);
		
		txtActorsseperatedBy = new Text(this, SWT.BORDER);
		txtActorsseperatedBy.setText("Actor names (seperated by commas)");
		FormData fd_txtActorsseperatedBy = new FormData();
		fd_txtActorsseperatedBy.left = new FormAttachment(0, 10);
		fd_txtActorsseperatedBy.right = new FormAttachment(composite, -6);
		fd_txtActorsseperatedBy.bottom = new FormAttachment(txtDirector, 50, SWT.BOTTOM);
		fd_txtActorsseperatedBy.top = new FormAttachment(txtDirector, 20);
		txtActorsseperatedBy.setLayoutData(fd_txtActorsseperatedBy);
		
		text = new Text(this, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(0, 10);
		fd_text.right = new FormAttachment(composite, -6);
		fd_text.bottom = new FormAttachment(txtActorsseperatedBy, 54, SWT.BOTTOM);
		fd_text.top = new FormAttachment(txtActorsseperatedBy, 24);
		text.setLayoutData(fd_text);
		
		DateTime dateTime = new DateTime(this, SWT.BORDER);
		fd_expandBar.top = new FormAttachment(dateTime, 44);
		FormData fd_dateTime = new FormData();
		fd_dateTime.bottom = new FormAttachment(100, -337);
		fd_dateTime.left = new FormAttachment(0, 10);
		dateTime.setLayoutData(fd_dateTime);
		
		DateTime dateTime_1 = new DateTime(this, SWT.BORDER);
		FormData fd_dateTime_1 = new FormData();
		fd_dateTime_1.right = new FormAttachment(composite, -13);
		fd_dateTime_1.bottom = new FormAttachment(dateTime, 0, SWT.BOTTOM);
		dateTime_1.setLayoutData(fd_dateTime_1);
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
