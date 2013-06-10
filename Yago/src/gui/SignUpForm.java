package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SignUpForm extends Shell {
	private Text userNameText;
	private Text passwordText;
	private Text repeatPasswordText;
	private boolean cancelPressed = false;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			SignUpForm shell = new SignUpForm(display);
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
	public SignUpForm(Display display) {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(SignUpForm.class, "/movies.png"));
		setLayout(null);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(0, 0, 782, 555);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancelPressed = true;
			}
		});
		btnNewButton.setBounds(569, 515, 90, 30);
		btnNewButton.setText("Cancel");
		
		Button btnSign = new Button(composite, SWT.NONE);
		btnSign.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnSign.setBounds(682, 515, 90, 30);
		btnSign.setText("Finish");
		
		repeatPasswordText = new Text(composite, SWT.BORDER);
		repeatPasswordText.setBounds(269, 345, 309, 26);
		
		passwordText = new Text(composite, SWT.BORDER);
		passwordText.setBounds(269, 296, 309, 26);
		
		userNameText = new Text(composite, SWT.BORDER);
		userNameText.setBounds(269, 249, 309, 26);
		
		Label signupBackground = new Label(composite, SWT.NONE);
		signupBackground.setImage(SWTResourceManager.getImage(SignUpForm.class, "/red-fabric-background-800x600-captioned.jpg"));
		signupBackground.setBounds(0, 0, 782, 555);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MoveIt! - Create a new account");
		setSize(800, 600);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public boolean returnToSignin(){
		return cancelPressed;
	}
}
