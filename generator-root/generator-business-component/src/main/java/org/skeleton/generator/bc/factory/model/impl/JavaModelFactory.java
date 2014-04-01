package org.skeleton.generator.bc.factory.model.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.skeleton.generator.bc.factory.model.interfaces.ModelFactory;
import org.skeleton.generator.bc.factory.model.interfaces.PackageFactory;
import org.skeleton.generator.bc.factory.model.interfaces.ProjectFactory;
import org.skeleton.generator.model.metadata.PackageMetaData;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.om.Model;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
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
	PackageFactory packageFactory;
	
	
	public Model buildModel(ProjectMetaData projectMetaData, Project project)
    {
        Model model = new Model();
        model.project = project;

        model.serviceExceptionPackageName = project.domainName + "." + project.projectName + ".exception.services";
        model.daoExceptionPackageName = project.domainName + "." + project.projectName + ".exception.repository";
        model.testExceptionPackageName = project.domainName + "." + project.projectName + ".exception.test";
        model.controllerPackageName = project.domainName + "." + project.projectName + ".mvc.controller.impl";
        model.commandPackageName = project.domainName + "." + project.projectName + ".junit.data.command";

        model.packages = new ArrayList<Package>();

        if (projectMetaData.getPackages() != null) {
	        for (PackageMetaData packageMetaData : projectMetaData.getPackages())
	        {
	        	logger.info("adding package : " + packageMetaData.getName());
				Package myPackage = packageFactory.buildPackage(packageMetaData, model);
				logger.info("package : " + myPackage.name + " added");
	        }
        }

        return model;
    }
}
