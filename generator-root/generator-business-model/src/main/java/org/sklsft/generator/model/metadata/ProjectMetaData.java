package org.sklsft.generator.model.metadata;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.sklsft.generator.model.metadata.datasources.DataSourceMetaData;



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
	@XmlElement(defaultValue="false")
    private boolean audited = false;
	@XmlElement(required=false)
	public String tablesTableSpace;
	@XmlElement(required=false)
	public String indexesTableSpace;
	
	@XmlTransient
	private DataSourceMetaData DataSource = new DataSourceMetaData();
    
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
	public boolean getAudited() {
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
		return DataSource;
	}
	public void setDataSource(DataSourceMetaData dataSource) {
		DataSource = dataSource;
	}
	public List<PackageMetaData> getPackages() {
		return packages;
	}
	public void setPackages(List<PackageMetaData> packages) {
		this.packages = packages;
	}
	
	
	
	/**
	 * recursive method to get all packages including children packages
	 */
	@XmlTransient
	public List<PackageMetaData> getAllPackages() {
		List<PackageMetaData> result = new ArrayList<>();
		
		if (packages != null) {
			for (PackageMetaData pack : packages) {
				result.addAll(getPackages(pack));
			}
		}
		return result;
	}
	
	private List<PackageMetaData> getPackages(PackageMetaData pack) {
		List<PackageMetaData> result = new ArrayList<>();
		result.add(pack);
		if (pack.getPackages()!=null) {
			for (PackageMetaData child:pack.getPackages()) {
				result.addAll(getPackages(child));
			}
		}
		return result;
	}
}
