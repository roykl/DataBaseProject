package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class AddRemoveWindow extends Shell {
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			AddRemoveWindow shell = new AddRemoveWindow(display);
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
	public AddRemoveWindow(Display display) {
		super(display, SWT.SHELL_TRIM | SWT.BORDER);
		setImage(SWTResourceManager.getImage(AddRemoveWindow.class, "/movies.png"));
		setMinimumSize(new Point(800, 600));
		setLayout(new FormLayout());
		
		Composite composite = new Composite(this, SWT.BORDER);
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(100, -35);
		composite.setLayoutData(fd_composite);
		
		Button btnRadioButton = new Button(composite, SWT.RADIO);
		btnRadioButton.setBounds(10, 10, 52, 20);
		btnRadioButton.setText("Add");
		
		Button btnRadioButton_1 = new Button(composite, SWT.RADIO);
		btnRadioButton_1.setBounds(111, 10, 78, 20);
		btnRadioButton_1.setText("Remove");
		
		Composite composite_1 = new Composite(this, SWT.BORDER);
		fd_composite.top = new FormAttachment(composite_1, 30);
		fd_composite.left = new FormAttachment(composite_1, 0, SWT.LEFT);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.right = new FormAttachment(100, -35);
		fd_composite_1.bottom = new FormAttachment(100, -439);
		composite_1.setLayoutData(fd_composite_1);
		
		Button btnGenre = new Button(composite_1, SWT.RADIO);
		btnGenre.setText("Genre");
		btnGenre.setBounds(10, 10, 72, 20);
		
		Button btnActor = new Button(composite_1, SWT.RADIO);
		btnActor.setText("Actor");
		btnActor.setBounds(111, 10, 71, 20);
		
		text = new Text(this, SWT.BORDER);
		fd_composite_1.left = new FormAttachment(text, 55);
		fd_composite.bottom = new FormAttachment(text, 0, SWT.BOTTOM);
		fd_composite_1.top = new FormAttachment(text, 0, SWT.TOP);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(0, 70);
		fd_text.right = new FormAttachment(100, -286);
		fd_text.left = new FormAttachment(0, 22);
		text.setLayoutData(fd_text);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		fd_text.bottom = new FormAttachment(btnNewButton, -199);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.top = new FormAttachment(0, 391);
		fd_btnNewButton.bottom = new FormAttachment(100, -96);
		fd_btnNewButton.left = new FormAttachment(0, 318);
		fd_btnNewButton.right = new FormAttachment(100, -286);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("Commit");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MovIt!");
		setSize(635, 570);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
