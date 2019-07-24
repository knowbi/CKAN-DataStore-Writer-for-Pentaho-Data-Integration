package org.ckan;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

import org.ckan.ckanMeta;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.step.BaseStepDialog;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;

public class ckanDialog extends BaseStepDialog implements StepDialogInterface
{
	private ckanMeta input;
	
	private Group		ckanConnection;
	private FormData	fdckanConnection;
	
	private Label		wlDomain;
	private TextVar wDomain;
	private FormData	fdlDomain, fdDomain;
	
	private Label		wlApiKey;
	private TextVar		wApiKey;
	private FormData	fdlApiKey, fdApiKey;
	
	private Group		ResourceDetails;
	private FormData	fdResourceDetails;
	
	private Label		wlPackageId;
	private TextVar		wPackageId;
	private FormData	fdlPackageId, fdPackageId;
	
	private Label		wlTitle;
	private TextVar		wTitle;
	private FormData	fdlTitle, fdTitle;

	private Label		wlDescription;
	private TextVar		wDescription;
	private FormData	fdlDescription, fdDescription;
	
	private Label		wlResourceId;
	private TextVar		wResourceId;
	private FormData	fdlResourceId, fdResourceId;
	
	private Group		AdvancedSettings;
	private FormData	fdAdvancedSettings;

	private Label		wlBatchSize;
	private TextVar		wBatchSize;
	private FormData	fdlBatchSize, fdBatchSize;
	
	private Label		wlPrimaryKey;
	private TextVar		wPrimaryKey;
	private FormData	fdlPrimaryKey, fdPrimaryKey;
	
	private Group 		proxySettings; 
	private FormData	fdProxySettings; 
	
	private Label 		wlProxyHost; 
	private TextVar		wProxyHost;
	private FormData 	fdlProxyHost, fdProxyHost; 
	
	private Label 		wlProxyPort; 
	private TextVar		wProxyPort;
	private FormData 	fdlProxyPort, fdProxyPort; 
	
	private Label 		wlProxyUser; 
	private TextVar		wProxyUser;
	private FormData 	fdlProxyUser, fdProxyUser; 
	
	private Label 		wlProxyPass; 
	private TextVar		wProxyPass;
	private FormData 	fdlProxyPass, fdProxyPass; 
	
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
	

	public ckanDialog(Shell parent, Object in, TransMeta transMeta, String sname)
	{
		super(parent, (BaseStepMeta)in, transMeta, sname);
		input=(ckanMeta)in;
	}

	public String open()
	{
		Shell parent = getParent();
		Display display = parent.getDisplay();

		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
		props.setLook( shell );
		setShellImage(shell, input);

		ModifyListener lsMod = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				input.setChanged();
			}
		};
		changed = input.hasChanged();

		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;

		shell.setLayout(formLayout);
		shell.setText(Messages.getString("ckanDialog.Shell.Title")); //$NON-NLS-1$

		int middle = props.getMiddlePct()-10;
		int margin = Const.MARGIN;

		// Stepname line
		wlStepname=new Label(shell, SWT.RIGHT);
		wlStepname.setText(Messages.getString("ckanDialog.StepName.Label")); //$NON-NLS-1$
		props.setLook( wlStepname );
		fdlStepname=new FormData();
		fdlStepname.left = new FormAttachment(0, 0);
		fdlStepname.right= new FormAttachment(middle, -margin);
		fdlStepname.top  = new FormAttachment(0, margin);
		wlStepname.setLayoutData(fdlStepname);
		
		wStepname=new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wStepname.setText(stepname);
		props.setLook( wStepname );
		wStepname.addModifyListener(lsMod);
		fdStepname=new FormData();
		fdStepname.left = new FormAttachment(middle, 0);
		fdStepname.right= new FormAttachment(100, 0);
		fdStepname.top  = new FormAttachment(0, margin);
		wStepname.setLayoutData(fdStepname);
		
		
		// CKAN Connection Group
		ckanConnection = new Group(shell, SWT.NONE);
		ckanConnection.setText("CKAN Connection");
		fdckanConnection=new FormData();
		fdckanConnection.left = new FormAttachment(0, 0);
		fdckanConnection.right= new FormAttachment(100, 0);
		fdckanConnection.top  = new FormAttachment(wStepname, margin+20);
		ckanConnection.setLayoutData(fdckanConnection);
		
		formLayout = new FormLayout ();
		formLayout.marginWidth  = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;
		ckanConnection.setLayout(formLayout);
		
		// Domain line
		wlDomain=new Label(ckanConnection, SWT.RIGHT);
		wlDomain.setText(Messages.getString("ckanDialog.Domain.Label")); //$NON-NLS-1$
		fdlDomain=new FormData();
		fdlDomain.left = new FormAttachment(0, 0);
		fdlDomain.right= new FormAttachment(middle, -margin);
		fdlDomain.top  = new FormAttachment(0, margin);
		wlDomain.setLayoutData(fdlDomain);
		
		wDomain=new TextVar(transMeta, ckanConnection, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wDomain.addModifyListener(lsMod);
		fdDomain=new FormData();
		fdDomain.left = new FormAttachment(middle, 0);
		fdDomain.right= new FormAttachment(100, 0);
		fdDomain.top  = new FormAttachment(0, margin);
		wDomain.setLayoutData(fdDomain);

		// ApiKey line
		wlApiKey=new Label(ckanConnection, SWT.RIGHT);
		wlApiKey.setText(Messages.getString("ckanDialog.ApiKey.Label")); //$NON-NLS-1$
		fdlApiKey=new FormData();
		fdlApiKey.left = new FormAttachment(0, 0);
		fdlApiKey.right= new FormAttachment(middle, -margin);
		fdlApiKey.top  = new FormAttachment(wDomain, margin);
		wlApiKey.setLayoutData(fdlApiKey);
		
		wApiKey=new TextVar(transMeta, ckanConnection, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wApiKey.addModifyListener(lsMod);
		fdApiKey=new FormData();
		fdApiKey.left = new FormAttachment(middle, 0);
		fdApiKey.right= new FormAttachment(100, 0);
		fdApiKey.top  = new FormAttachment(wDomain, margin);
		wApiKey.setLayoutData(fdApiKey);
		
		
		// Resource Details Group
		ResourceDetails = new Group(shell, SWT.NONE);
		ResourceDetails.setText("Resource Details");
		fdResourceDetails=new FormData();
		fdResourceDetails.left = new FormAttachment(0, 0);
		fdResourceDetails.right= new FormAttachment(100, 0);
		fdResourceDetails.top  = new FormAttachment(ckanConnection, margin+20);
		ResourceDetails.setLayoutData(fdResourceDetails);
		
		formLayout = new FormLayout ();
		formLayout.marginWidth  = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;
		ResourceDetails.setLayout(formLayout);

		// PackageId line
		wlPackageId=new Label(ResourceDetails, SWT.RIGHT);
		wlPackageId.setText(Messages.getString("ckanDialog.PackageId.Label")); //$NON-NLS-1$
		fdlPackageId=new FormData();
		fdlPackageId.left = new FormAttachment(0, 0);
		fdlPackageId.right= new FormAttachment(middle, -margin);
		fdlPackageId.top  = new FormAttachment(0, margin);
		wlPackageId.setLayoutData(fdlPackageId);
		
		wPackageId=new TextVar(transMeta, ResourceDetails, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wPackageId.addModifyListener(lsMod);
		fdPackageId=new FormData();
		fdPackageId.left = new FormAttachment(middle, 0);
		fdPackageId.right= new FormAttachment(100, 0);
		fdPackageId.top  = new FormAttachment(0, margin);
		wPackageId.setLayoutData(fdPackageId);
		
		// Title line
		wlTitle=new Label(ResourceDetails, SWT.RIGHT);
		wlTitle.setText(Messages.getString("ckanDialog.Title.Label")); //$NON-NLS-1$
		fdlTitle=new FormData();
		fdlTitle.left = new FormAttachment(0, 0);
		fdlTitle.right= new FormAttachment(middle, -margin);
		fdlTitle.top  = new FormAttachment(wPackageId, margin);
		wlTitle.setLayoutData(fdlTitle);
		
		wTitle=new TextVar(transMeta, ResourceDetails, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wTitle.addModifyListener(lsMod);
		fdTitle=new FormData();
		fdTitle.left = new FormAttachment(middle, 0);
		fdTitle.right= new FormAttachment(100, 0);
		fdTitle.top  = new FormAttachment(wPackageId, margin);
		wTitle.setLayoutData(fdTitle);
		
		// Description line
		wlDescription=new Label(ResourceDetails, SWT.RIGHT);
		wlDescription.setText(Messages.getString("ckanDialog.Description.Label")); //$NON-NLS-1$
		fdlDescription=new FormData();
		fdlDescription.left = new FormAttachment(0, 0);
		fdlDescription.right= new FormAttachment(middle, -margin);
		fdlDescription.top  = new FormAttachment(wTitle, margin);
		wlDescription.setLayoutData(fdlDescription);
		
		wDescription=new TextVar(transMeta, ResourceDetails, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wDescription.addModifyListener(lsMod);
		fdDescription=new FormData();
		fdDescription.left = new FormAttachment(middle, 0);
		fdDescription.right= new FormAttachment(100, 0);
		fdDescription.top  = new FormAttachment(wTitle, margin);
		wDescription.setLayoutData(fdDescription);
		
		// ResourceId line
		wlResourceId=new Label(ResourceDetails, SWT.RIGHT);
		wlResourceId.setText(Messages.getString("ckanDialog.ResourceId.Label")); //$NON-NLS-1$
		fdlResourceId=new FormData();
		fdlResourceId.left = new FormAttachment(0, 0);
		fdlResourceId.right= new FormAttachment(middle, -margin);
		fdlResourceId.top  = new FormAttachment(wDescription, margin);
		wlResourceId.setLayoutData(fdlResourceId);
		
		wResourceId=new TextVar(transMeta, ResourceDetails, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wResourceId.addModifyListener(lsMod);
		fdResourceId=new FormData();
		fdResourceId.left = new FormAttachment(middle, 0);
		fdResourceId.right= new FormAttachment(100, 0);
		fdResourceId.top  = new FormAttachment(wDescription, margin);
		wResourceId.setLayoutData(fdResourceId);
		
		
		// Advanced Settings Group
		AdvancedSettings = new Group(shell, SWT.NONE);
		AdvancedSettings.setText("Advanced Settings");
		fdAdvancedSettings=new FormData();
		fdAdvancedSettings.left = new FormAttachment(0, 0);
		fdAdvancedSettings.right= new FormAttachment(100, 0);
		fdAdvancedSettings.top  = new FormAttachment(ResourceDetails, margin+20);
		AdvancedSettings.setLayoutData(fdAdvancedSettings);
		
		formLayout = new FormLayout ();
		formLayout.marginWidth  = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;
		AdvancedSettings.setLayout(formLayout);

		// BatchSize line
		wlBatchSize=new Label(AdvancedSettings, SWT.RIGHT);
		wlBatchSize.setText(Messages.getString("ckanDialog.BatchSize.Label")); //$NON-NLS-1$
		fdlBatchSize=new FormData();
		fdlBatchSize.left = new FormAttachment(0, 0);
		fdlBatchSize.right= new FormAttachment(middle, -margin);
		fdlBatchSize.top  = new FormAttachment(0, margin);
		wlBatchSize.setLayoutData(fdlBatchSize);
		
		wBatchSize=new TextVar(transMeta, AdvancedSettings, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wBatchSize.addModifyListener(lsMod);
		fdBatchSize=new FormData();
		fdBatchSize.left = new FormAttachment(middle, 0);
		fdBatchSize.right= new FormAttachment(100, 0);
		fdBatchSize.top  = new FormAttachment(0, margin);
		wBatchSize.setLayoutData(fdBatchSize);

		// PrimaryKey line
		wlPrimaryKey=new Label(AdvancedSettings, SWT.RIGHT);
		wlPrimaryKey.setText(Messages.getString("ckanDialog.PrimaryKey.Label")); //$NON-NLS-1$
		fdlPrimaryKey=new FormData();
		fdlPrimaryKey.left = new FormAttachment(0, 0);
		fdlPrimaryKey.right= new FormAttachment(middle, -margin);
		fdlPrimaryKey.top  = new FormAttachment(wBatchSize, margin);
		wlPrimaryKey.setLayoutData(fdlPrimaryKey);
		
		wPrimaryKey=new TextVar(transMeta, AdvancedSettings, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wPrimaryKey.addModifyListener(lsMod);
		fdPrimaryKey=new FormData();
		fdPrimaryKey.left = new FormAttachment(middle, 0);
		fdPrimaryKey.right= new FormAttachment(100, 0);
		fdPrimaryKey.top  = new FormAttachment(wBatchSize, margin);
		wPrimaryKey.setLayoutData(fdPrimaryKey);
		
		
		// Proxy Settings Group 
		proxySettings = new Group(shell, SWT.NONE);
		proxySettings.setText("Proxy Settings");
		fdProxySettings=new FormData();
		fdProxySettings.left = new FormAttachment(0, 0);
		fdProxySettings.right= new FormAttachment(100, 0);
		fdProxySettings.top  = new FormAttachment(AdvancedSettings, margin+20);
		proxySettings.setLayoutData(fdProxySettings);
		
		formLayout = new FormLayout ();
		formLayout.marginWidth  = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;
		proxySettings.setLayout(formLayout);
		
		// Proxy Host
		wlProxyHost = new Label(proxySettings, SWT.RIGHT);
		wlProxyHost.setText("Proxy Host");
		fdlProxyHost = new FormData();
		fdlProxyHost.left = new FormAttachment(0, 0);
		fdlProxyHost.right = new FormAttachment(middle, -margin);
		fdlProxyHost.top = new FormAttachment(0, margin);
		wlProxyHost.setLayoutData(fdlProxyHost);
		
		wProxyHost = new TextVar(transMeta, proxySettings, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wProxyHost.addModifyListener(lsMod);
		fdProxyHost=new FormData();
		fdProxyHost.left = new FormAttachment(middle, 0);
		fdProxyHost.right= new FormAttachment(100, 0);
		fdProxyHost.top  = new FormAttachment(0, margin);
		wProxyHost.setLayoutData(fdProxyHost);
		
		// Proxy port
		wlProxyPort = new Label(proxySettings, SWT.RIGHT);
		wlProxyPort.setText("Proxy Port");
		fdlProxyPort = new FormData();
		fdlProxyPort.left = new FormAttachment(0,0);
		fdlProxyPort.right = new FormAttachment(middle, -margin);
		fdlProxyPort.top = new FormAttachment(wProxyHost, margin);
		wlProxyPort.setLayoutData(fdlProxyPort);
		
		wProxyPort = new TextVar(transMeta, proxySettings, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wProxyPort.addModifyListener(lsMod);
		fdProxyPort = new FormData();
		fdProxyPort.left = new FormAttachment(middle, 0);
		fdProxyPort.right = new FormAttachment(100, 0);
		fdProxyPort.top = new FormAttachment(wProxyHost, margin); 
		wProxyPort.setLayoutData(fdProxyPort);
		
		// Proxy User 
		wlProxyUser = new Label(proxySettings, SWT.RIGHT);
		wlProxyUser.setText("Proxy User");
		fdlProxyUser = new FormData();
		fdlProxyUser.left = new FormAttachment(0,0);
		fdlProxyUser.right = new FormAttachment(middle, -margin);
		fdlProxyUser.top = new FormAttachment(wProxyPort, margin);
		wlProxyUser.setLayoutData(fdlProxyUser);
		
		wProxyUser = new TextVar(transMeta, proxySettings, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wProxyUser.addModifyListener(lsMod);
		fdProxyUser = new FormData();
		fdProxyUser.left = new FormAttachment(middle,0);
		fdProxyUser.right = new FormAttachment(100, 0);
		fdProxyUser.top = new FormAttachment(wProxyPort, margin);
		wProxyUser.setLayoutData(fdProxyUser);
		
		// Proxy Pass 
		wlProxyPass = new Label(proxySettings, SWT.RIGHT);
		wlProxyPass.setText("Proxy Pass");
		fdlProxyPass = new FormData();
		fdlProxyPass.left = new FormAttachment(0,0);
		fdlProxyPass.right = new FormAttachment(middle, -margin);
		fdlProxyPass.top = new FormAttachment(wProxyUser, margin);
		wlProxyPass.setLayoutData(fdlProxyPass);
		
		wProxyPass = new TextVar(transMeta, proxySettings, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wProxyPass.addModifyListener(lsMod);
		fdProxyPass = new FormData(); 
		fdProxyPass.left = new FormAttachment(middle,0);
		fdProxyPass.right = new FormAttachment(100, 0);
		fdProxyPass.top = new FormAttachment(wProxyUser, margin);
		wProxyPass.setLayoutData(fdProxyPass);
		
		
		
		
		
		// Some buttons
		wOK=new Button(shell, SWT.PUSH);
		wOK.setText(Messages.getString("System.Button.OK")); //$NON-NLS-1$
		wCancel=new Button(shell, SWT.PUSH);
		wCancel.setText(Messages.getString("System.Button.Cancel")); //$NON-NLS-1$

		BaseStepDialog.positionBottomButtons(shell, new Button[] {wOK, wCancel}, margin, proxySettings);
		
		// Add listeners
		lsCancel = new Listener() {
			public void handleEvent(Event e) { cancel(); }
		};
		lsOK = new Listener() {
			public void handleEvent(Event e) { ok(); }
		};
		
		wCancel.addListener(SWT.Selection, lsCancel);
		wOK.addListener(SWT.Selection, lsOK);

		// Default action listeners
		lsDef = new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) { ok(); }
		};
		
		wStepname.addSelectionListener(lsDef);
		wDomain.addSelectionListener(lsDef);
		wApiKey.addSelectionListener(lsDef);
		wPackageId.addSelectionListener(lsDef);
		wTitle.addSelectionListener(lsDef);
		wDescription.addSelectionListener(lsDef);
		wResourceId.addSelectionListener(lsDef);
		wBatchSize.addSelectionListener(lsDef);
		wPrimaryKey.addSelectionListener(lsDef);
		wProxyHost.addSelectionListener(lsDef);
		wProxyPort.addSelectionListener(lsDef);
		wProxyUser.addSelectionListener(lsDef);
		wProxyPass.addSelectionListener(lsDef);

		
		// Detect X or ALT-F4 or something that kills this window...
		shell.addShellListener(new ShellAdapter() { public void shellClosed(ShellEvent e) { cancel(); } } );

		// Set the shell size, based upon previous time...
		setSize();
		
		// Populate and open dialog
		getData();
		input.setChanged(changed);
	
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		return stepname;
	}
	
	// Read data from input (TextFileInputInfo)
	public void getData()
	{
		wStepname.selectAll();
		
		if (input.getDomain() != null) {
			wDomain.setText(input.getDomain());	
		}
		
		if (input.getApiKey() != null) {
			wApiKey.setText(input.getApiKey());	
		}
		
		if (input.getPackageId() != null) {
			wPackageId.setText(input.getPackageId());	
		}
		
		if (input.getResourceTitle() != null) {
			wTitle.setText(input.getResourceTitle());	
		}
		
		if (input.getResourceDescription() != null) {
			wDescription.setText(input.getResourceDescription());	
		}
		else {
			wDescription.setText("");
		}
		
		if (input.getResourceId() != null) {
			wResourceId.setText(input.getResourceId());	
		}
		
		if (input.getBatchSize() != null) {
			wBatchSize.setText(input.getBatchSize());	
		}
		
		if (input.getPrimaryKey() != null) {
			wPrimaryKey.setText(input.getPrimaryKey());	
		}
		
		if(input.getProxyHost() != null) {
			wProxyHost.setText(input.getProxyHost());
		}
		
		if(input.getProxyPort() != null) {
			wProxyPort.setText(input.getProxyPort());
		}
		if(input.getProxyUser() != null) {
			wProxyUser.setText(input.getProxyUser());
		}
		if(input.getProxyPass() != null) {
			wProxyPass.setText(input.getProxyPass());
		}
	}
	
	private void cancel()
	{
		stepname=null;
		input.setChanged(changed);
		dispose();
	}
	
	private void ok()
	{
		stepname = wStepname.getText(); // return value
		
		String domain = wDomain.getText().trim();
//		Removed. Interferes with use of parameters.
//		if (!domain.startsWith("http://") && !domain.startsWith("https://")) {
//			domain = "http://" + domain;
//		}
		while (domain.endsWith("/")) {
			domain = domain.substring(0,domain.length()-1);
		}
		
		String apiKey = wApiKey.getText().trim();
		
		String packageId = wPackageId.getText().trim();
		if (packageId.length() != 0 && !packageId.startsWith("${")) {
			packageId = toSlug(packageId);
		}
		
		String title = wTitle.getText().trim();
		String description = wDescription.getText().trim();
		String resourceId = wResourceId.getText().trim();
		
		String batchSize = wBatchSize.getText().trim();
		try {
			int validSize = Integer.parseInt(batchSize);
			if ( !(validSize > 0) ) {
				batchSize = "5000";
			}
		} catch (NumberFormatException e) {
			batchSize = "5000";
		}
		
		String primaryKey = wPrimaryKey.getText().trim();
		
		String proxyHost = wProxyHost.getText().trim();
		String proxyPort = wProxyPort.getText().trim();
		String proxyUser = wProxyUser.getText().trim();
		String proxyPass = wProxyPass.getText().trim();
	
		input.setDomain(domain);
		input.setApiKey(apiKey);
		input.setPackageId(packageId);
		input.setResourceTitle(title);
		input.setResourceDescription(description);
		input.setResourceId(resourceId);
		input.setBatchSize(batchSize);
		input.setPrimaryKey(primaryKey);
		input.setProxyHost(proxyHost);
		input.setProxyPort(proxyPort);
		input.setProxyUser(proxyUser);
		input.setProxyPass(proxyPass);
		
		dispose();
	}
	
	public String toSlug(String input) {  
		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Form.NFD);  
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH).replaceAll("-{2,}","-").replaceAll("^-|-$","");
	}
}
