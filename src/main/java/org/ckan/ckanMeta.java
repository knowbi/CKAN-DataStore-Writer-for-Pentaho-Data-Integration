package org.ckan;

import java.util.List;
import java.util.Map;

import org.ckan.ckan;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.w3c.dom.Node;


@Step(
  id = "CKANWriter",
  image = "ckan_logo.png",
  i18nPackageName = "org.ckan",
  name = "CKANWriter.step.name",
  description = "CKANWriter.step.description",
  categoryDescription = "CKANWriter.step.category"
)
public class ckanMeta extends BaseStepMeta implements StepMetaInterface {
	private String Domain;
	private String ApiKey;
	private String PackageId;
	private String ResourceTitle;
	private String ResourceDescription;
	private String ResourceId;
	private String BatchSize;
	private String PrimaryKey;
	private String proxyHost, proxyPort, proxyUser, proxyPass; 

	public ckanMeta() {
		super(); // allocate BaseStepInfo
	}

	/**
	 * @return Returns the value.
	 */
	public String getDomain() {
		return Domain;
	}

	public String getApiKey() {
		return ApiKey;
	}

	public String getPackageId() {
		return PackageId;
	}

	public String getResourceTitle() {
		return ResourceTitle;
	}

	public String getResourceDescription() {
		return ResourceDescription;
	}

	public String getResourceId() {
		return ResourceId;
	}

	public String getBatchSize() {
		return BatchSize;
	}
	
	public String getPrimaryKey() {
		return PrimaryKey;
	}
	
	public String getProxyHost() {
		return proxyHost;
	}
	
	public String getProxyPort() {
		return proxyPort; 
	}
	
	public String getProxyUser() {
		return proxyUser; 
	}
	
	public String getProxyPass() {
		return proxyPass; 
	}
	
	/**
	 * @param value	The value to set.
	 */	
	public void setDomain(String Domain) {
		this.Domain = Domain;
	}

	public void setApiKey(String ApiKey) {
		this.ApiKey = ApiKey;
	}

	public void setPackageId(String PackageId) {
		this.PackageId = PackageId;
	}

	public void setResourceTitle(String ResourceTitle) {
		this.ResourceTitle = ResourceTitle;
	}

	public void setResourceDescription(String ResourceDescription) {
		this.ResourceDescription = ResourceDescription;
	}

	public void setResourceId(String ResourceId) {
		this.ResourceId = ResourceId;
	}
	
	public void setBatchSize(String BatchSize) {
		this.BatchSize = BatchSize;
	}
	
	public void setPrimaryKey(String PrimaryKey) {
		this.PrimaryKey = PrimaryKey;
	}
	
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	
	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}
	
	public void setProxyPass(String proxyPass) {
		this.proxyPass = proxyPass;
	}
	
	
	// Set sensible defaults for a new step
	public void setDefault() {
		Domain = "";
		ApiKey = "";
		PackageId = "";
		ResourceTitle = "Untitled";
		ResourceDescription = "";
		ResourceId = "";
		BatchSize = "5000";
		PrimaryKey = "";
		proxyHost = "";
		proxyPort = "";
		proxyUser = "";
		proxyPass = "";
	}

	public void getFields(RowMetaInterface r, String origin, RowMetaInterface[] info, StepMeta nextStep, VariableSpace space) {
		// Nothing here
	}

	public Object clone() {
		Object retval = super.clone();

		return retval;
	}

	public String getXML() throws KettleValueException {
		StringBuffer retval = new StringBuffer(255);

		retval.append("    ").append(XMLHandler.addTagValue("domain", Domain));
		retval.append("    ").append(XMLHandler.addTagValue("api_key", ApiKey));
		retval.append("    ").append(XMLHandler.addTagValue("package_id", PackageId));
		retval.append("    ").append(XMLHandler.addTagValue("resource_title", ResourceTitle));
		retval.append("    ").append(XMLHandler.addTagValue("resource_description", ResourceDescription));
		retval.append("    ").append(XMLHandler.addTagValue("resource_id", ResourceId));
		retval.append("    ").append(XMLHandler.addTagValue("batch_size", BatchSize));
		retval.append("    ").append(XMLHandler.addTagValue("primary_key", PrimaryKey));
		retval.append("    ").append(XMLHandler.addTagValue("proxy_host", proxyHost));
		retval.append("    ").append(XMLHandler.addTagValue("proxy_port", proxyPort));
		retval.append("    ").append(XMLHandler.addTagValue("proxy_user", proxyUser));
		retval.append("    ").append(XMLHandler.addTagValue("proxy_pass", proxyPass));

		return retval.toString();
	}

	public void loadXML(Node stepnode, List<DatabaseMeta> databases, Map<String, Counter> counters) throws KettleXMLException {
		try {
			Domain = XMLHandler.getTagValue(stepnode, "domain");
			ApiKey = XMLHandler.getTagValue(stepnode, "api_key");
			PackageId = XMLHandler.getTagValue(stepnode, "package_id");
			ResourceTitle = XMLHandler.getTagValue(stepnode, "resource_title");
			ResourceDescription = XMLHandler.getTagValue(stepnode, "resource_description");
			ResourceId = XMLHandler.getTagValue(stepnode, "resource_id");
			BatchSize = XMLHandler.getTagValue(stepnode, "batch_size");
			PrimaryKey = XMLHandler.getTagValue(stepnode, "primary_key");
			proxyHost = XMLHandler.getTagValue(stepnode, "proxy_host");
			proxyPort = XMLHandler.getTagValue(stepnode, "proxy_port");
			proxyUser = XMLHandler.getTagValue(stepnode, "proxy_user");
			proxyPass = XMLHandler.getTagValue(stepnode, "proxy_pass");
		} catch (Exception e) {
			throw new KettleXMLException("Template Plugin Unable to read step info from XML node", e);
		}
	}

	public void readRep(Repository rep, ObjectId id_step, List<DatabaseMeta> databases, Map<String, Counter> counters) throws KettleException {
		try {
			Domain = rep.getStepAttributeString(id_step, "domain");
			ApiKey = rep.getStepAttributeString(id_step, "apikey");
			PackageId = rep.getStepAttributeString(id_step, "package_id");
			ResourceTitle = rep.getStepAttributeString(id_step, "resource_title");
			ResourceDescription = rep.getStepAttributeString(id_step, "resource_description");
			ResourceId = rep.getStepAttributeString(id_step, "resource_id");
			BatchSize = rep.getStepAttributeString(id_step, "batch_size");
			PrimaryKey = rep.getStepAttributeString(id_step, "primary_key");
			proxyHost = rep.getStepAttributeString(id_step, "proxy_host");
			proxyPort = rep.getStepAttributeString(id_step, "proxy_port");
			proxyUser = rep.getStepAttributeString(id_step, "proxy_user");
			proxyPass = rep.getStepAttributeString(id_step, "proxy_pass");
		} catch (Exception e) {
			throw new KettleException("Unexpected error reading step with id_step=" + id_step + " from the repository", e);
		}
	}

	public void saveRep(Repository rep, ObjectId id_transformation, ObjectId id_step) throws KettleException {
		try {
			rep.saveStepAttribute(id_transformation, id_step, "domain", Domain);
			rep.saveStepAttribute(id_transformation, id_step, "apikey", ApiKey);
			rep.saveStepAttribute(id_transformation, id_step, "package_id", PackageId);
			rep.saveStepAttribute(id_transformation, id_step, "resource_title", ResourceTitle);
			rep.saveStepAttribute(id_transformation, id_step, "resource_description", ResourceDescription);
			rep.saveStepAttribute(id_transformation, id_step, "resource_id", ResourceId);
			rep.saveStepAttribute(id_transformation, id_step, "batch_size", BatchSize);
			rep.saveStepAttribute(id_transformation, id_step, "primary_key", PrimaryKey);
			rep.saveStepAttribute(id_transformation, id_step, "proxy_host", proxyHost);
			rep.saveStepAttribute(id_transformation, id_step, "proxy_port", proxyPort);
			rep.saveStepAttribute(id_transformation, id_step, "proxy_user", proxyUser);
			rep.saveStepAttribute(id_transformation, id_step, "proxy_pass", proxyPass);
		} catch (Exception e) {
			throw new KettleException("Unable to save step information to the repository, id_step=" + id_step, e);
		}
	}

	public void check(List<CheckResultInterface> remarks, TransMeta transmeta, StepMeta stepMeta, RowMetaInterface prev, String input[], String output[], RowMetaInterface info) {
		CheckResult cr;
		if (prev == null || prev.size() == 0) {
			cr = new CheckResult(CheckResult.TYPE_RESULT_WARNING, "Not receiving any fields from previous steps!", stepMeta);
			remarks.add(cr);
		} else {
			cr = new CheckResult(CheckResult.TYPE_RESULT_OK, "Step is connected to previous one, receiving " + prev.size() + " fields", stepMeta);
			remarks.add(cr);
		}

		// See if we have input streams leading to this step!
		if (input.length > 0) {
			cr = new CheckResult(CheckResult.TYPE_RESULT_OK, "Step is receiving info from other steps.", stepMeta);
			remarks.add(cr);
		} else {
			cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, "No input received from other steps!", stepMeta);
			remarks.add(cr);
		}
	}

	public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name) {
		return new ckanDialog(shell, meta, transMeta, name);
	}

	public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta, Trans disp) {
		return new ckan(stepMeta, stepDataInterface, cnr, transMeta, disp);
	}

	public StepDataInterface getStepData() {
		return new ckanData();
	}
}
