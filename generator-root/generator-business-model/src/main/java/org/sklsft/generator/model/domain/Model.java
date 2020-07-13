package org.sklsft.generator.model.domain;

import java.util.List;

import org.sklsft.generator.exception.BeanNotFoundException;
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
	
	public String javaSourcesFolder;
	public String testJavaSourcesFolder;
	public String resourcesFolder;
	public String testResourcesFolder;
	public String webResourcesFolder;
	
	public String apiArtefactName;
	public String modelArtefactName;
	public String persistenceArtefactName;
	public String componentsArtefactName;
	public String servicesArtefactName;
	public String populatorArtefactName;
	public String testsArtefactName;	
	public String restArtefactName;
	public String restClientArtefactName;
	public String webappArtefactName;

	public List<Package> packages;	
	
	public String apiModelPackageName;
	public String serviceInterfacePackageName;
	
	public String modelPackageName;
	public String enversPackageName;
	
	public String persistencePackageName;
	public String daoInterfacePackageName;
	public String daoImplPackageName;
	
	public String componentsPackageName;
	public String mapperPackageName;
	public String stateManagerPackageName;
	public String rightsManagerPackageName;
	public String processorPackageName;
	
	public String servicesPackageName;
	public String restClientPackageName;
	
	public String restControllerPackageName;
	
	public String mvcPackageName;
	public String controllerPackageName;
	public String mvcModelPackageName;
	public String mvcFilterPackageName;
	
	public String commandPackageName;
	public String builderPackageName;
	public String executorPackageName;	
	public String junitPackageName;

	
	/*
	 * getters and setters
	 */
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getJavaSourcesFolder() {
		return javaSourcesFolder;
	}
	public void setJavaSourcesFolder(String javaSourcesFolder) {
		this.javaSourcesFolder = javaSourcesFolder;
	}
	public String getTestJavaSourcesFolder() {
		return testJavaSourcesFolder;
	}
	public void setTestJavaSourcesFolder(String testJavaSourcesFolder) {
		this.testJavaSourcesFolder = testJavaSourcesFolder;
	}
	public String getResourcesFolder() {
		return resourcesFolder;
	}
	public void setResourcesFolder(String resourcesFolder) {
		this.resourcesFolder = resourcesFolder;
	}
	public String getTestResourcesFolder() {
		return testResourcesFolder;
	}
	public void setTestResourcesFolder(String testResourcesFolder) {
		this.testResourcesFolder = testResourcesFolder;
	}
	public String getWebResourcesFolder() {
		return webResourcesFolder;
	}
	public void setWebResourcesFolder(String webResourcesFolder) {
		this.webResourcesFolder = webResourcesFolder;
	}
	public String getApiArtefactName() {
		return apiArtefactName;
	}
	public void setApiArtefactName(String apiArtefactName) {
		this.apiArtefactName = apiArtefactName;
	}
	public String getModelArtefactName() {
		return modelArtefactName;
	}
	public void setModelArtefactName(String modelArtefactName) {
		this.modelArtefactName = modelArtefactName;
	}
	public String getPersistenceArtefactName() {
		return persistenceArtefactName;
	}
	public void setPersistenceArtefactName(String persistenceArtefactName) {
		this.persistenceArtefactName = persistenceArtefactName;
	}
	public String getComponentsArtefactName() {
		return componentsArtefactName;
	}
	public void setComponentsArtefactName(String componentsArtefactName) {
		this.componentsArtefactName = componentsArtefactName;
	}
	public String getServicesArtefactName() {
		return servicesArtefactName;
	}
	public void setServicesArtefactName(String servicesArtefactName) {
		this.servicesArtefactName = servicesArtefactName;
	}
	public String getPopulatorArtefactName() {
		return populatorArtefactName;
	}
	public void setPopulatorArtefactName(String populatorArtefactName) {
		this.populatorArtefactName = populatorArtefactName;
	}
	public String getTestsArtefactName() {
		return testsArtefactName;
	}
	public void setTestsArtefactName(String testsArtefactName) {
		this.testsArtefactName = testsArtefactName;
	}
	public String getRestArtefactName() {
		return restArtefactName;
	}
	public void setRestArtefactName(String restArtefactName) {
		this.restArtefactName = restArtefactName;
	}
	public String getRestClientArtefactName() {
		return restClientArtefactName;
	}
	public void setRestClientArtefactName(String restClientArtefactName) {
		this.restClientArtefactName = restClientArtefactName;
	}
	public String getWebappArtefactName() {
		return webappArtefactName;
	}
	public void setWebappArtefactName(String webappArtefactName) {
		this.webappArtefactName = webappArtefactName;
	}
	public List<Package> getPackages() {
		return packages;
	}
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
	public String getApiModelPackageName() {
		return apiModelPackageName;
	}
	public void setApiModelPackageName(String apiModelPackageName) {
		this.apiModelPackageName = apiModelPackageName;
	}
	public String getServiceInterfacePackageName() {
		return serviceInterfacePackageName;
	}
	public void setServiceInterfacePackageName(String serviceInterfacePackageName) {
		this.serviceInterfacePackageName = serviceInterfacePackageName;
	}
	public String getModelPackageName() {
		return modelPackageName;
	}
	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}
	public String getEnversPackageName() {
		return enversPackageName;
	}
	public void setEnversPackageName(String enversPackageName) {
		this.enversPackageName = enversPackageName;
	}
	public String getPersistencePackageName() {
		return persistencePackageName;
	}
	public void setPersistencePackageName(String persistencePackageName) {
		this.persistencePackageName = persistencePackageName;
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
	public String getComponentsPackageName() {
		return componentsPackageName;
	}
	public void setComponentsPackageName(String componentsPackageName) {
		this.componentsPackageName = componentsPackageName;
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
	public String getRightsManagerPackageName() {
		return rightsManagerPackageName;
	}
	public void setRightsManagerPackageName(String rightsManagerPackageName) {
		this.rightsManagerPackageName = rightsManagerPackageName;
	}
	public String getProcessorPackageName() {
		return processorPackageName;
	}
	public void setProcessorPackageName(String processorPackageName) {
		this.processorPackageName = processorPackageName;
	}
	public String getServicesPackageName() {
		return servicesPackageName;
	}
	public void setServicesPackageName(String servicesPackageName) {
		this.servicesPackageName = servicesPackageName;
	}
	public String getRestClientPackageName() {
		return restClientPackageName;
	}
	public void setRestClientPackageName(String restClientPackageName) {
		this.restClientPackageName = restClientPackageName;
	}
	public String getRestControllerPackageName() {
		return restControllerPackageName;
	}
	public void setRestControllerPackageName(String restControllerPackageName) {
		this.restControllerPackageName = restControllerPackageName;
	}
	public String getMvcPackageName() {
		return mvcPackageName;
	}
	public void setMvcPackageName(String mvcPackageName) {
		this.mvcPackageName = mvcPackageName;
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
	public String getMvcFilterPackageName() {
		return mvcFilterPackageName;
	}
	public void setMvcFilterPackageName(String mvcFilterPackageName) {
		this.mvcFilterPackageName = mvcFilterPackageName;
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
	public String getJunitPackageName() {
		return junitPackageName;
	}
	public void setJunitPackageName(String junitPackageName) {
		this.junitPackageName = junitPackageName;
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
