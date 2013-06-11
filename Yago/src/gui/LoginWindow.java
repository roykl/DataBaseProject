package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.IdbOparations;

public class LoginWindow extends Shell {
	private Text text;
	private Text text_1;


	/**
	 * Create the shell.
	 * @param display
	 */
	public LoginWindow(final Display display, final IdbOparations oparations) {
		super(display, SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX);
		setMinimumSize(new Point(795, 600));
		setImage(SWTResourceManager.getImage(LoginWindow.class, "/movies.png"));
		setLayout(new FormLayout());
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(null);
		FormData fd_composite = new FormData();
		fd_composite.left = new FormAttachment(0);
		fd_composite.top = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		
		Button button_1 = new Button(composite, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				dispose();
				MainMenu MainMenuShell = new MainMenu(display,oparations);
				MainMenuShell.open();
				MainMenuShell.layout();
				while (!MainMenuShell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				
			}
		});
		button_1.setText("Login");
		button_1.setBounds(273, 473, 90, 30);
		
		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				dispose();
				SignUpForm signUpShell = new SignUpForm(display,oparations);
				signUpShell.open();
				signUpShell.layout();
				while (!signUpShell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				
				
			}
		});
		button.setText("Signup");
		button.setBounds(447, 473, 90, 30);
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				if(text_1.getText().equals("Username")){
					text_1.setText("");
				}
			}
			@Override
			public void mouseUp(MouseEvent arg0) {
				if(text.getText().isEmpty()){
					text.setText("Password");
				}
			}
		});
		text_1.setText("Username");
		text_1.setBounds(273, 364, 264, 30);
		
		text = new Text(composite, SWT.BORDER);
		text.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				if(text.getText().equals("Password")){
					text.setText("");
				}
			}
			@Override
			public void mouseUp(MouseEvent arg0) {
				if(text_1.getText().isEmpty()){
					text_1.setText("Username");
				}
			}
		});
		text.setText("Password");
		text.setBounds(273, 411, 264, 30);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(0, 0, 790, 568);
		lblNewLabel.setImage(SWTResourceManager.getImage(LoginWindow.class, "/redcarpet background 2.jpg"));
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MoveIt!");
		setSize(800, 573);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	
}
