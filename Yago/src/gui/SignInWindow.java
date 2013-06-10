package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class SignInWindow {

	protected Shell shlMovit;
	private Text txtUsername;
	private Text txtPassword;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SignInWindow window = new SignInWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlMovit.open();
		shlMovit.layout();
		while (!shlMovit.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlMovit = new Shell();
		shlMovit.setImage(SWTResourceManager.getImage(SignInWindow.class, "/movies.png"));
		shlMovit.setMinimumSize(new Point(800, 600));
		shlMovit.setSize(818, 645);
		shlMovit.setText("MovIt!");
		shlMovit.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shlMovit, SWT.NONE);
		composite.setLayout(null);
		
		txtPassword = new Text(composite, SWT.BORDER);
		txtPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				txtPassword.setText("");
			}
		});
		txtPassword.setText("Password");
		txtPassword.setBounds(268, 418, 264, 30);
		
		txtUsername = new Text(composite, SWT.BORDER);
		txtUsername.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				txtUsername.setText("");
			}
		});
		txtUsername.addSelectionListener(new SelectionAdapter() {
		});
		txtUsername.setText("Username");
		txtUsername.setBounds(268, 371, 264, 30);
		
		Button btnSignup = new Button(composite, SWT.NONE);
		btnSignup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					shlMovit.dispose();
					Display display = Display.getDefault();
					SignUpForm shell = new SignUpForm(display);
					shell.open();
					shell.layout();
					while (!shell.isDisposed()) {
						if (shell.returnToSignin()){
							shell.dispose();
							open();
						}
						else if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSignup.setBounds(442, 468, 90, 30);
		btnSignup.setText("Signup");
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		btnNewButton.setBounds(268, 468, 90, 30);
		btnNewButton.setText("Login");
		
		Label signInBackground = new Label(composite, SWT.NONE);
		signInBackground.setBounds(0, 0, 800, 600);
		signInBackground.setAlignment(SWT.CENTER);
		signInBackground.setImage(SWTResourceManager.getImage(SignInWindow.class, "/redcarpet background 2.jpg"));

	}
}
