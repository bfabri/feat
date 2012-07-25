package br.pucrio.inf.les.feat.ui.views;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ProjectVersionDialog extends TitleAreaDialog {

	private Text projectVersionText;
	private String projectVersion;
	
	public ProjectVersionDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	public void create() {
		super.create();
		setTitle("Project version");
		setMessage("Which version you want to generate?", IMessageProvider.INFORMATION);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		
		parent.setLayout(layout);
		
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		
		Label projectVersionLabel = new Label(parent, SWT.BORDER);
		projectVersionLabel.setText("Version:");
		
		projectVersionText = new Text(parent, SWT.NONE);
		projectVersionText.setLayoutData(gridData);
		
		return parent;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.CENTER;

		parent.setLayoutData(gridData);
		// Create Add button
		// Own method as we need to overview the SelectionAdapter
		createOkButton(parent, OK, "Add", true);
		// Add a SelectionListener

		// Create Cancel button
		Button cancelButton = 
				createButton(parent, CANCEL, "Cancel", false);
		// Add a SelectionListener
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setReturnCode(CANCEL);
				close();
			}
		});		
	}
	
	protected Button createOkButton(Composite parent, int id, String label, boolean defaultButton) {
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (isValidInput()) {
					okPressed();
				}
			}
		});
		
		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
		}
		
		setButtonLayoutData(button);
		return button;
	}
	
	private boolean isValidInput() {
		boolean valid = true;
		if (projectVersionText.getText().length() == 0) {
			setErrorMessage("Please maintain the project version.");
			valid = false;
		}
		return valid;
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}

	private void saveInput() {
		projectVersion = projectVersionText.getText();
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getProjectVersion() {
		return projectVersion;
	}
}
