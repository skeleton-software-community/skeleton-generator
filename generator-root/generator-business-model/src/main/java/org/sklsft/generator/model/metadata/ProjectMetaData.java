package org.sklsft.generator.model.metadata;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="project")
@XmlType(name="project")
public class ProjectMetaData {

	/*
	 * properties
	 */
	@XmlTransient
	private PersistenceMode persistenceMode;
	@XmlTransient
	private String sourceFolder;
	@XmlTransient
    private String workspaceFolder;
	@XmlElement(required=true)
	private String domainName;
	@XmlElement(required=true)
    private String projectName;
	@XmlElement(required=true)
    private String skeleton;
	@XmlElement(required=true)
    private String databaseEngine;
	@XmlElement(required=true)
    private String databaseName;
	@XmlElement
    private String databaseDNS;
	@XmlElement
    private String databasePort;
	@XmlElement
    private String databaseUserName;
	@XmlElement
    private String databasePassword;
	@XmlElement(defaultValue="false")
    private boolean audited = false;
    
	@XmlElementWrapper(name="packages")
	@XmlElement(name="package")
    private List<PackageMetaData> packages;

    
    /*
     * getters and setters
     */
    public PersistenceMode getPersistenceMode() {
		return persistenceMode;
	}

	public void setPersistenceMode(PersistenceMode persistenceMode) {
		this.persistenceMode = persistenceMode;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public String getWorkspaceFolder() {
		return workspaceFolder;
	}

	public void setWorkspaceFolder(String workspaceFolder) {
		this.workspaceFolder = workspaceFolder;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSkeleton() {
		return skeleton;
	}

	public void setSkeleton(String skeleton) {
		this.skeleton = skeleton;
	}

	public String getDatabaseEngine() {
		return databaseEngine;
	}

	public void setDatabaseEngine(String databaseEngine) {
		this.databaseEngine = databaseEngine;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDatabaseDNS() {
		return databaseDNS;
	}

	public void setDatabaseDNS(String databaseDNS) {
		this.databaseDNS = databaseDNS;
	}

	public String getDatabasePort() {
		return databasePort;
	}

	public void setDatabasePort(String databasePort) {
		this.databasePort = databasePort;
	}

	public String getDatabaseUserName() {
		return databaseUserName;
	}

	public void setDatabaseUserName(String databaseUserName) {
		this.databaseUserName = databaseUserName;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public boolean getAudited() {
		return audited;
	}

	public void setAudited(boolean audited) {
		this.audited = audited;
	}

	public List<PackageMetaData> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageMetaData> packages) {
		this.packages = packages;
	}
}
