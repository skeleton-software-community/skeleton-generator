package com.skeleton.generator.bl.factory.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skeleton.generator.bl.factory.interfaces.ModelFactory;
import com.skeleton.generator.bl.factory.interfaces.PackageFactory;
import com.skeleton.generator.bl.factory.interfaces.ProjectFactory;
import com.skeleton.generator.model.metadata.PackageMetaData;
import com.skeleton.generator.model.metadata.ProjectMetaData;
import com.skeleton.generator.model.om.Model;
import com.skeleton.generator.model.om.Package;
import com.skeleton.generator.model.om.Project;


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
        model.commandPackageName = project.domainName + "." + project.projectName + ".bl.services.command";

        model.packageList = new ArrayList<Package>();

        for (PackageMetaData packageMetaData : projectMetaData.getPackageMetaDataList())
        {
        	logger.info("adding package : " + packageMetaData.getName());
			Package myPackage = packageFactory.buildPackage(packageMetaData, model);
			logger.info("package : " + myPackage.name + " added");
        }

        return model;
    }
}
