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

public class MainMenu extends Shell {
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
		setImage(SWTResourceManager.getImage(MainMenu.class, "/movies.png"));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(0, 0, 1006, 723);
		
		ExpandBar expandBar = new ExpandBar(composite, SWT.NONE);
		expandBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		expandBar.setBounds(113, 217, 155, 135);
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setText("Select genre");
		
		Button btnCheckButton = new Button(expandBar, SWT.CHECK);
		xpndtmNewExpanditem.setControl(btnCheckButton);
		btnCheckButton.setText("Check Button");
		xpndtmNewExpanditem.setHeight(xpndtmNewExpanditem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		
		DateTime dateTime_1 = new DateTime(composite, SWT.BORDER);
		dateTime_1.setBounds(403, 156, 102, 28);
		
		DateTime dateTime = new DateTime(composite, SWT.BORDER);
		dateTime.setBounds(212, 156, 112, 28);
		
		Button btnThriller = new Button(composite, SWT.BORDER | SWT.CHECK | SWT.CENTER);
		btnThriller.setText("Thriller");
		btnThriller.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnThriller.setBounds(687, 83, 90, 30);
		
		Button btnMusical = new Button(composite, SWT.BORDER | SWT.CHECK | SWT.CENTER);
		btnMusical.setText("Romance");
		btnMusical.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnMusical.setBounds(578, 83, 90, 30);
		
		Button btnHorror = new Button(composite, SWT.BORDER | SWT.CHECK | SWT.CENTER);
		btnHorror.setText("Horror");
		btnHorror.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnHorror.setBounds(476, 83, 80, 30);
		
		Button btnDrama = new Button(composite, SWT.BORDER | SWT.CHECK | SWT.CENTER);
		btnDrama.setText("Drama");
		btnDrama.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnDrama.setBounds(361, 83, 93, 30);
		
		Button btnComedy = new Button(composite, SWT.BORDER | SWT.CHECK | SWT.CENTER);
		btnComedy.setText("Comedy");
		btnComedy.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnComedy.setBounds(243, 83, 92, 30);
		
		Button btnRadioButton = new Button(composite, SWT.BORDER | SWT.CHECK | SWT.CENTER);
		btnRadioButton.setText("Action");
		btnRadioButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnRadioButton.setBounds(130, 83, 90, 30);
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(130, 31, 647, 30);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setBounds(793, 31, 90, 30);
		btnNewButton.setText("Search");
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(MainMenu.class, "/video screen.jpg"));
		lblNewLabel.setBounds(0, 0, 1006, 722);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MoveIt!");
		setSize(1024, 768);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
