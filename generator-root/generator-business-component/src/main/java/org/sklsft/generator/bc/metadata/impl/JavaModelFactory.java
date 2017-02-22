package org.sklsft.generator.bc.metadata.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.sklsft.generator.bc.metadata.interfaces.BasicViewBeanFactory;
import org.sklsft.generator.bc.metadata.interfaces.FormBeanFactory;
import org.sklsft.generator.bc.metadata.interfaces.FullViewBeanFactory;
import org.sklsft.generator.bc.metadata.interfaces.ModelFactory;
import org.sklsft.generator.bc.metadata.interfaces.OptionBeanFactory;
import org.sklsft.generator.bc.metadata.interfaces.PackageFactory;
import org.sklsft.generator.bc.metadata.interfaces.ProjectFactory;
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
	
	@Resource(name="javaOptionBeanFactory")
	private OptionBeanFactory optionBeanFactory;
	
	
	
	
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

        model.controllerPackageName = project.domainName + "." + project.projectName + ".mvc.controller";
        model.mvcModelPackageName = project.domainName + "." + project.projectName + ".mvc.model";
        model.apiModelPackageName = project.domainName + "." + project.projectName + ".api.model";
        model.modelPackageName = project.domainName + "." + project.projectName + ".model";
        model.mvcFilterPackageName = project.domainName + "." + project.projectName + ".mvc.filter";
        model.commandPackageName = project.domainName + "." + project.projectName + ".populator.command";
        model.builderPackageName = project.domainName + "." + project.projectName + ".populator.builder";
        model.executorPackageName = project.domainName + "." + project.projectName + ".populator.executor";
        model.enversPackageName = project.domainName + "." + project.projectName + ".model.envers";
        model.mapperPackageName = project.domainName + "." + project.projectName + ".bc.mapper";
        model.stateManagerPackageName = project.domainName + "." + project.projectName + ".bc.statemanager";
        model.rightsManagerPackageName = project.domainName + "." + project.projectName + ".bc.rightsmanager";
        model.processorPackageName = project.domainName + "." + project.projectName + ".bc.processor";
        model.daoImplPackageName = project.domainName + "." + project.projectName + ".repository.dao.impl";
        model.daoInterfacePackageName = project.domainName + "." + project.projectName + ".repository.dao.interfaces";
        model.serviceImplPackageName = project.domainName + "." + project.projectName + ".bl.impl";
        model.serviceInterfacePackageName = project.domainName + "." + project.projectName + ".api.interfaces";
        model.junitPackageName = project.domainName + "." + project.projectName + ".junit";
        model.junitDataPackageName =  model.junitPackageName + ".data";
        
        model.packages = new ArrayList<Package>();
		return model;
	}


	private void fillPackages(ProjectMetaData projectMetaData, Model model) {
		for (PackageMetaData packageMetaData : projectMetaData.getPackages()){
			Package myPackage = packageFactory.fillPackage(packageMetaData, model);
			logger.info("Filling package : " + myPackage.name);
			
		}
	}


	private void scanPackages(ProjectMetaData projectMetaData, Model model) {
		for (PackageMetaData packageMetaData : projectMetaData.getPackages())
		{
			logger.info("Scanning package : " + packageMetaData.getName());
			Package myPackage = packageFactory.scanPackage(packageMetaData, model);
			model.packages.add(myPackage);
		}
	}
	
	
	private void buildViews(Model model) {
		for (Package pack:model.getPackages()) {
			for (Bean bean:pack.beans) {
				
				if (bean.detailMode == null) {
					if (bean.hasTabsInDetailView()) {
						bean.detailMode = DetailMode.PAGE;
					} else {
						bean.detailMode = DetailMode.MODAL;
					}
				}
				
				bean.basicViewBean = basicViewBeanFactory.getBasicViewBean(bean);
				bean.fullViewBean = fullViewBeanFactory.getFullViewBean(bean);
				bean.formBean = formBeanFactory.getFormBean(bean);
				
				for (OneToMany oneToMany:bean.oneToManyList) {
					oneToMany.basicViewBean = basicViewBeanFactory.getBasicViewBean(oneToMany);
					oneToMany.formBean = formBeanFactory.getFormBean(oneToMany);
				}
			}
		}
		
	}
}
