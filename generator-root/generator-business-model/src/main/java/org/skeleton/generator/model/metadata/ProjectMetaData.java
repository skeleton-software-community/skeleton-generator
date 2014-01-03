package org.skeleton.generator.model.metadata;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.skeleton.generator.util.metadata.PersistenceMode;

@XmlRootElement
@XmlType
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
	
	private String domainName;
	
    private String projectName;
	
    private String skeleton;
	
    private String databaseEngine;
	
    private String databaseName;
	
    private String databaseDNS;
	
    private String databasePort;
	
    private String databaseUserName;
	
    private String databasePassword;
	
    private String audited;
    

    private List<PackageMetaData> packageMetaDataList;

    
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

	public String getAudited() {
		return audited;
	}

	public void setAudited(String audited) {
		this.audited = audited;
	}

	public List<PackageMetaData> getPackageMetaDataList() {
		return packageMetaDataList;
	}

	public void setPackageMetaDataList(List<PackageMetaData> packageMetaDataList) {
		this.packageMetaDataList = packageMetaDataList;
	}
}
