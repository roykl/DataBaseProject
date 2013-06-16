package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
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

import thread_logic.ThreadUserAuthentication;
import viewModelLayer.InputVerifier;

import db.IdbOparations;

public class LoginWindow extends Shell {
	private Text text;
	private Text text_1;

	private static final int USER_NOT_EXIST = 2;
	private static final int OK = 1;
	private static final int ERR = 0;

	/**
	 * Create the shell.
	 * @param display
	 */
	public LoginWindow(final Display display, final IdbOparations oparations) {
		super(display, SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.MAX);
		setSize(606, 763);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setMinimumSize(new Point(600, 750));
		setImage(SWTResourceManager.getImage(LoginWindow.class, "/movies.png"));
		setLayout(new FormLayout());

		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(204, 51, 51));
		composite.setLayout(null);
		FormData fd_composite = new FormData();
		fd_composite.left = new FormAttachment(0);
		fd_composite.top = new FormAttachment(0);
		composite.setLayoutData(fd_composite);

		Button button_1 = new Button(composite, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if(!InputVerifier.verifyUsername(text_1.getText())){ // illegal user name
					MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
					messageBox.setText("Illegal Username");
					messageBox.setMessage("Username must contain 3 - 12 chars\n Only letters or numbers allowed.");
					messageBox.open();
				}else if(!InputVerifier.verifyPass(text.getText())){ // invalid password
					MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
					messageBox.setText("Illegal Password");
					messageBox.setMessage("Password must contain 3 - 12 chars.");
					messageBox.open();
				}
				//if ADMINISTRATOR 
				else if(text_1.getText().equals("admin") && text.getText().equals("admin")){
					dispose();
					MainMenu MainMenuShell = new MainMenu(display,oparations,true);
					MainMenuShell.open();
					MainMenuShell.layout();
					while (!MainMenuShell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
				}
				else{ //not admin
					
					// check authentication
					display.syncExec(new ThreadUserAuthentication(oparations, text_1.getText() , text.getText()){
						@Override
						public void run(){
							super.run();
							int result = this.getValue();


							if(result == OK){
								// user authentication OK
								System.out.println("im in result ok");
								dispose();
								MainMenu MainMenuShell = new MainMenu(display,oparations,false);
								MainMenuShell.open();
								MainMenuShell.layout();
								while (!MainMenuShell.isDisposed()) {
									if (!display.readAndDispatch()) {
										display.sleep();
									}
								}
							}
							else if (result == USER_NOT_EXIST) //  user authentication NOT OK
							{
								
								MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
								messageBox.setText("User doesn't exist");
								messageBox.setMessage("User doesn't exist: please sign up first.");
								messageBox.open();
							}

						}


					});

				}		
			}	
		});

		button_1.setText("Login");
		button_1.setBounds(157, 327, 90, 30);

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
		button.setBounds(331, 327, 90, 30);

		text_1 = new Text(composite, SWT.BORDER);
		text_1.setBounds(157, 201, 264, 30);

		text = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		text.setBounds(157, 264, 264, 30);

		Label loginBackground = new Label(composite, SWT.NONE);
		loginBackground.setBounds(0, 0, 600, 811);
		loginBackground.setImage(SWTResourceManager.getImage(LoginWindow.class, "/Welcome to movit.png"));
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MovIt!");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
