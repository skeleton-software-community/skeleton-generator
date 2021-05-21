package org.sklsft.generator.model.domain;

import org.sklsft.generator.model.metadata.datasources.DataSourceMetaData;


/**
 * Full representation of a project<br/>
 * Properties are willingly public because of their intensive use in file write commands<br/>
 * getters are necessary for apache Velocity
 * @author Nicolas Thibault
 *
 */
public class Project {

	/*
	 * properties
	 */
	public String sourceFolder;
	public String workspaceFolder;
	public String domainName;
	public String projectName;
	public String skeleton;
	public String databaseEngine;
	public boolean audited;
	public String tablesTableSpace;
	public String indexesTableSpace;
	
	public DataSourceMetaData dataSource;
    
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
	public boolean isAudited() {
		return audited;
	}
	public void setAudited(boolean audited) {
		this.audited = audited;
	}
	public String getTablesTableSpace() {
		return tablesTableSpace;
	}
	public void setTablesTableSpace(String tablesTableSpace) {
		this.tablesTableSpace = tablesTableSpace;
	}
	public String getIndexesTableSpace() {
		return indexesTableSpace;
	}
	public void setIndexesTableSpace(String indexesTableSpace) {
		this.indexesTableSpace = indexesTableSpace;
	}
	public DataSourceMetaData getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSourceMetaData dataSource) {
		this.dataSource = dataSource;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
}
