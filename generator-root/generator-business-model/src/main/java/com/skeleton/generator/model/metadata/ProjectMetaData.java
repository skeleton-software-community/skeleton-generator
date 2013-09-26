package com.skeleton.generator.model.metadata;

import java.util.List;

public class ProjectMetaData {

	/*
	 * properties
	 */
	private String domainName;
    private String projectName;
    private String sourceFolder;
    private String workspaceFolder;
    private String serverDNS;
    private String serverPort;
    private String wsUrl;
    private String skeleton;
    private String databaseEngine;
    private String databaseName;
    private String userName;
    private String password;
    
    private List<PackageMetaData> packageMetaDataList;

    /*
     * getters and setters
     */
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

	public String getServerDNS() {
		return serverDNS;
	}

	public void setServerDNS(String serverDNS) {
		this.serverDNS = serverDNS;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getWsUrl() {
		return wsUrl;
	}

	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<PackageMetaData> getPackageMetaDataList() {
		return packageMetaDataList;
	}

	public void setPackageMetaDataList(List<PackageMetaData> packageMetaDataList) {
		this.packageMetaDataList = packageMetaDataList;
	}   
}
