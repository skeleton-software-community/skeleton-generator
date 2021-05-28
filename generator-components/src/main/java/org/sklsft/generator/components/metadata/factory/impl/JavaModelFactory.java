package org.sklsft.generator.components.metadata.factory.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.sklsft.generator.components.metadata.factory.interfaces.BasicViewBeanFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.FormBeanFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.FullViewBeanFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.ModelFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.PackageFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.ProjectFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.ViewPropertiesFactory;
import org.sklsft.generator.model.domain.Model;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.metadata.DetailMode;
import org.sklsft.generator.model.metadata.PackageMetaData;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * 
 * @author Nicolas Thibault
 *
 */
@Component(value="javaModelFactory")
public class JavaModelFactory implements ModelFactory {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProjectFactory.class);
	
	/*
	 * properties injected by spring
	 */
	@Resource(name="javaPackageFactory")
	private PackageFactory packageFactory;
	
	@Resource(name="javaBasicViewBeanFactory")
	private BasicViewBeanFactory basicViewBeanFactory;
	
	@Resource(name="javaFullViewBeanFactory")
	private FullViewBeanFactory fullViewBeanFactory;
	
	@Resource(name="javaFormBeanFactory")
	private FormBeanFactory formBeanFactory;
	
	@Resource(name="javaViewPropertiesFactory")
	private ViewPropertiesFactory viewPropertiesFactory;
		
	
	
	public Model buildModel(ProjectMetaData projectMetaData, Project project)
    {
        Model model = setUpModel(project);
        
        if (projectMetaData.getPackages() != null) {
	        scanPackages(projectMetaData, model);
	        fillPackages(projectMetaData, model);
	        buildViews(model);
        }

        return model;
    }


	private Model setUpModel(Project project) {
		Model model = new Model();
        model.project = project;
        
        model.javaSourcesFolder = "src" + File.separator + "main" + File.separator + "java";
        model.tsSourcesFolder = "src" + File.separator + "app" + File.separator + "features";
        model.testJavaSourcesFolder = "src" + File.separator + "test" + File.separator + "java";
        model.resourcesFolder = "src" + File.separator + "main" + File.separator + "resources";
        model.testResourcesFolder = "src" + File.separator + "test" + File.separator + "resources";
        model.webResourcesFolder = "src" + File.separator + "main" + File.separator + "webapp";
        
        model.apiArtefactName = project.projectName + "-api";
        model.modelArtefactName = project.projectName + "-model";
        model.persistenceArtefactName = project.projectName + "-persistence";
        model.componentsArtefactName = project.projectName + "-components";
        model.servicesArtefactName = project.projectName + "-services";
        model.populatorArtefactName = project.projectName + "-populator";
        model.testsArtefactName = project.projectName + "-tests";
        model.restArtefactName = project.projectName + "-rest";
        model.restClientArtefactName = project.projectName + "-rest-client";
        model.webappArtefactName = project.projectName + "-webapp";
        model.tsUiArtefactName = project.projectName + "-ui";

        model.apiModelPackageName = project.domainName + "." + project.projectName + ".api.model";
        model.serviceInterfacePackageName = project.domainName + "." + project.projectName + ".api.interfaces";
       
        model.modelPackageName = project.domainName + "." + project.projectName + ".model";
        model.enversPackageName = project.domainName + "." + project.projectName + ".model.envers";
        
        model.persistencePackageName = project.domainName + "." + project.projectName + ".persistence";
        model.daoImplPackageName = model.persistencePackageName + ".impl";
        model.daoInterfacePackageName = model.persistencePackageName + ".interfaces";
        
        model.componentsPackageName = project.domainName + "." + project.projectName + ".components";
        model.mapperPackageName = model.componentsPackageName + ".mapper";
        model.stateManagerPackageName = model.componentsPackageName + ".statemanager";
        model.rightsManagerPackageName = model.componentsPackageName + ".rightsmanager";
        model.processorPackageName = model.componentsPackageName + ".processor";
       
        model.servicesPackageName = project.domainName + "." + project.projectName + ".services";
        
        model.restClientPackageName = project.domainName + "." + project.projectName + ".rest.client";
       
        model.restControllerPackageName = project.domainName + "." + project.projectName + ".rest.controller";
        
        model.mvcPackageName = project.domainName + "." + project.projectName + ".mvc";
        model.controllerPackageName = model.mvcPackageName + ".controller";
        model.mvcModelPackageName = model.mvcPackageName + ".model";
        model.mvcFilterPackageName = model.mvcPackageName + ".filter";
       
        model.commandPackageName = project.domainName + "." + project.projectName + ".populator.command";
        model.builderPackageName = project.domainName + "." + project.projectName + ".populator.builder";
        model.executorPackageName = project.domainName + "." + project.projectName + ".populator.executor";
        model.junitPackageName = project.domainName + "." + project.projectName + ".junit";
        
        model.packages = new ArrayList<Package>();
		
        return model;
	}


	private void scanPackages(ProjectMetaData projectMetaData, Model model) {
		logger.trace("Scanning packages");
		for (PackageMetaData packageMetaData : projectMetaData.getPackages()) {
			List<Package> myPackages = packageFactory.scanPackages(packageMetaData, model, null);
			model.packages.addAll(myPackages);
		}
	}
	
	
	private void fillPackages(ProjectMetaData projectMetaData, Model model) {
		for (PackageMetaData packageMetaData : projectMetaData.getPackages()) {
			packageFactory.fillPackage(packageMetaData, model);			
		}
	}
	
	
	private void buildViews(Model model) {
		for (Package pack:model.getPackages()) {
			for (Bean bean:pack.beans) {
				
				if (bean.detailMode == null) {
					boolean hasTabsInDetailView = hasTabsInDetailView(bean);
					if (hasTabsInDetailView) {
						bean.detailMode = DetailMode.PAGE;
					} else {
						bean.detailMode = DetailMode.MODAL;
					}
				}
				
				bean.basicViewBean = basicViewBeanFactory.getBasicViewBean(bean);
				bean.fullViewBean = fullViewBeanFactory.getFullViewBean(bean);
				bean.formBean = formBeanFactory.getFormBean(bean);
				bean.referenceViewProperties = viewPropertiesFactory.getReferenceProperties(bean);
				
				for (OneToMany oneToMany:bean.oneToManyList) {
					oneToMany.basicViewBean = basicViewBeanFactory.getBasicViewBean(oneToMany);
					oneToMany.formBean = formBeanFactory.getFormBean(oneToMany);
				}
			}
		}
	}
	
	/**
	 * determines whether the bean will have several tabs in the detail view (Mode Page)
	 */
	private boolean hasTabsInDetailView(Bean bean) {
		return bean.oneToManyComponentList.size() > 0 || bean.oneToManyList.size() > 0 || bean.oneToOneComponentList.size() > 0 || bean.oneToOneList.size() > 0;
	}
}
