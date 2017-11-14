package org.sklsft.generator.model.domain;

import java.io.File;


/**
 * Full representation of a project<br/>
 * Properties are willingly public because of their intensive use in file write commands<br/>
 * getters are necessary for apache Velocity
 * @author Nicolas Thibault
 *
 */
public class Project {
	
	public static final String BUILD_SCRIPT_FOLDER = "SQL" + File.separator + "BUILD";

	/*
	 * properties
	 */
    public String sourceFolder;
    public String workspaceFolder;
	public String domainName;
    public String projectName;
    public String skeleton;
    
    public String databaseEngine;
    public String databaseName;
    public String databaseDNS;
    public String databasePort;
    public String databaseUserName;
    public String databasePassword;
    
    public boolean audited;
    
    public Model model;

    
    /*
     * getters and setters
     */
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

	public boolean isAudited() {
		return audited;
	}

	public void setAudited(boolean audited) {
		this.audited = audited;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
}
