package org.sklsft.generator.model.domain;

import java.util.List;

import org.sklsft.generator.exception.BeanNotFoundException;
import org.sklsft.generator.exception.PackageNotFoundException;
import org.sklsft.generator.exception.TableNotFoundException;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.database.Table;


/**
 * Representation of a model associated to a project<br/>
 * Properties are willingly public because of their intensive use in file write commands<br/>
 * getters are necessary for apache Velocity
 * @author Nicolas Thibault
 *
 */
public class Model {

	/*
	 * properties
	 */
	public Project project;
	
	public List<Package> packages;

    public String controllerPackageName;
    public String mvcModelPackageName;
    public String apiModelPackageName;
    public String modelPackageName;
    public String commandPackageName;
    public String builderPackageName;
    public String executorPackageName;
    public String filterPackageName;
    public String enversPackageName;    
	public String daoInterfacePackageName;
	public String daoImplPackageName;
	public String mapperPackageName;
	public String stateManagerPackageName;
	public String processorPackageName;
	public String serviceImplPackageName;
	public String serviceInterfacePackageName;

	/*
     * getters and setters
     */
    public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public List<Package> getPackages() {
		return packages;
	}
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}	
	public String getControllerPackageName() {
		return controllerPackageName;
	}
	public void setControllerPackageName(String controllerPackageName) {
		this.controllerPackageName = controllerPackageName;
	}
	public String getMvcModelPackageName() {
		return mvcModelPackageName;
	}
	public void setMvcModelPackageName(String mvcModelPackageName) {
		this.mvcModelPackageName = mvcModelPackageName;
	}
	public String getApiModelPackageName() {
		return apiModelPackageName;
	}
	public void setApiModelPackageName(String apiModelPackageName) {
		this.apiModelPackageName = apiModelPackageName;
	}
	public String getModelPackageName() {
		return modelPackageName;
	}
	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}
	public String getCommandPackageName() {
		return commandPackageName;
	}
	public void setCommandPackageName(String commandPackageName) {
		this.commandPackageName = commandPackageName;
	}
	public String getBuilderPackageName() {
		return builderPackageName;
	}
	public void setBuilderPackageName(String builderPackageName) {
		this.builderPackageName = builderPackageName;
	}
	public String getExecutorPackageName() {
		return executorPackageName;
	}
	public void setExecutorPackageName(String executorPackageName) {
		this.executorPackageName = executorPackageName;
	}
	public String getFilterPackageName() {
		return filterPackageName;
	}
	public void setFilterPackageName(String filterPackageName) {
		this.filterPackageName = filterPackageName;
	}
	public String getEnversPackageName() {
		return enversPackageName;
	}
	public void setEnversPackageName(String enversPackageName) {
		this.enversPackageName = enversPackageName;
	}
	public String getDaoInterfacePackageName() {
		return daoInterfacePackageName;
	}
	public void setDaoInterfacePackageName(String daoInterfacePackageName) {
		this.daoInterfacePackageName = daoInterfacePackageName;
	}
	public String getDaoImplPackageName() {
		return daoImplPackageName;
	}
	public void setDaoImplPackageName(String daoImplPackageName) {
		this.daoImplPackageName = daoImplPackageName;
	}
	public String getMapperPackageName() {
		return mapperPackageName;
	}
	public void setMapperPackageName(String mapperPackageName) {
		this.mapperPackageName = mapperPackageName;
	}
	public String getStateManagerPackageName() {
		return stateManagerPackageName;
	}
	public void setStateManagerPackageName(String stateManagerPackageName) {
		this.stateManagerPackageName = stateManagerPackageName;
	}
	public String getProcessorPackageName() {
		return processorPackageName;
	}
	public void setProcessorPackageName(String processorPackageName) {
		this.processorPackageName = processorPackageName;
	}
	public String getServiceImplPackageName() {
		return serviceImplPackageName;
	}
	public void setServiceImplPackageName(String serviceImplPackageName) {
		this.serviceImplPackageName = serviceImplPackageName;
	}
	public String getServiceInterfacePackageName() {
		return serviceInterfacePackageName;
	}
	public void setServiceInterfacePackageName(String serviceInterfacePackageName) {
		this.serviceInterfacePackageName = serviceInterfacePackageName;
	}
	
	
	
	/**
     * run through all the tables until it is found
     * @param tableName
     * @return
     * @throws TableNotFoundException if no table with the corresponding name is found
     */
	public Table findTable(String tableName) {
		
		if (tableName == null) {
			return null;
		}
		if (tableName.isEmpty()) {
			return null;
		}
		for (Package myPackage : this.packages) {
			for (Table table : myPackage.tables) {
				if (table.originalName.equals(tableName)) {
					return table;
				}
			}
		}
		throw new TableNotFoundException("Invalid table reference : "
				+ tableName);
	}

	public Package findPackage(String packageName){
		String lowerCaseName = packageName.toLowerCase();
		for(Package packg : this.packages){
			if(packg.name.equals(lowerCaseName)) return packg;
		}
		throw new PackageNotFoundException(packageName);
	}

	/**
	 * run through all the beans until it is found
	 * @param tableOriginalName the original table name
	 * @return
	 * @throws BeanNotFoundException if no bean which associated table has the corresponding name
	 */
	public Bean findBean(String tableOriginalName) {
		for (Package myPackage : this.packages) {
			for (Bean bean : myPackage.beans) {
				if (bean.table.originalName.equals(tableOriginalName)) {
					return bean;
				}
			}
		}
		throw new BeanNotFoundException("invalid table reference : " + tableOriginalName);
	}
}
