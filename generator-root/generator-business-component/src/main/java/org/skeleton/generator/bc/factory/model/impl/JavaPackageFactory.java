package org.skeleton.generator.bc.factory.model.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.skeleton.generator.bc.factory.model.interfaces.BeanFactory;
import org.skeleton.generator.bc.factory.model.interfaces.PackageFactory;
import org.skeleton.generator.bc.factory.model.interfaces.ProjectFactory;
import org.skeleton.generator.bc.factory.model.interfaces.TableFactory;
import org.skeleton.generator.model.metadata.PackageMetaData;
import org.skeleton.generator.model.metadata.TableMetaData;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.Model;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component(value="javaPackageFactory")
public class JavaPackageFactory implements PackageFactory {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProjectFactory.class);
	
	/*
	 * properties
	 */
	@Autowired
	TableFactory tableFactory;
	@Resource(name="javaBeanFactory")
	BeanFactory beanFactory;
	
	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.bl.factory.interfaces.PackageFactory#buildPackage(com.skeleton.generator.model.metadata.PackageMetaData, com.skeleton.generator.model.om.Model)
	 */
	public Package buildPackage(PackageMetaData packageMetaData, Model model) {
		Package myPackage = new Package();
		model.packages.add(myPackage);
        myPackage.model = model;
        myPackage.name = packageMetaData.getName().toLowerCase();

        myPackage.omPackageName = model.project.domainName + "." + model.project.projectName + ".model." + myPackage.name + ".om";
        myPackage.ovPackageName = model.project.domainName + "." + model.project.projectName + ".model." + myPackage.name + ".ov";

        myPackage.statusPackageName = model.project.domainName + "." + model.project.projectName + ".model." + myPackage.name + ".status";
        
        myPackage.baseDAOImplPackageName = model.project.domainName + "." + model.project.projectName + ".repository.basedao." + myPackage.name + ".impl";
        myPackage.baseDAOInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".repository.basedao." + myPackage.name + ".interfaces";
        myPackage.DAOImplPackageName = model.project.domainName + "." + model.project.projectName + ".repository.dao." + myPackage.name + ".impl";
        myPackage.DAOInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".repository.dao." + myPackage.name + ".interfaces";

        myPackage.baseServiceImplPackageName = model.project.domainName + "." + model.project.projectName + ".bl.baseservices." + myPackage.name + ".impl";
        myPackage.baseServiceInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".bl.baseservices." + myPackage.name + ".interfaces";
        myPackage.serviceImplPackageName = model.project.domainName + "." + model.project.projectName + ".bl.services." + myPackage.name + ".impl";
        myPackage.serviceInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".bl.services." + myPackage.name + ".interfaces";

        myPackage.baseMapperImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.basemapper." + myPackage.name + ".impl";
        myPackage.baseMapperInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".bc.basemapper." + myPackage.name + ".interfaces";
        myPackage.mapperImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.mapper." + myPackage.name + ".impl";
        myPackage.mapperInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".bc.mapper." + myPackage.name + ".interfaces";

        myPackage.baseStateManagerImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.basestatemanager." + myPackage.name + ".impl";
        myPackage.baseStateManagerInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".bc.basestatemanager." + myPackage.name + ".interfaces";
        myPackage.stateManagerImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.statemanager." + myPackage.name + ".impl";
        myPackage.stateManagerInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".bc.statemanager." + myPackage.name + ".interfaces";


        myPackage.facadeImplPackageName = model.project.domainName + "." + model.project.projectName + ".facade." + myPackage.name + ".impl";
        myPackage.facadeInterfacesPackageName = model.project.domainName + "." + model.project.projectName + ".facade." + myPackage.name + ".interfaces";

        myPackage.baseControllerPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.basecontroller." + myPackage.name + ".impl";
        myPackage.controllerPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.controller." + myPackage.name + ".impl";
        
        myPackage.filterPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.filter." + myPackage.name + ".impl";

        myPackage.builderPackageName = model.project.domainName + "." + model.project.projectName + ".junit.data." + myPackage.name + ".builder";
        myPackage.commandPackageName = model.project.domainName + "." + model.project.projectName + ".junit.data." + myPackage.name + ".command";

        myPackage.tables = new ArrayList<Table>();
        myPackage.beans = new ArrayList<Bean>();

        for (TableMetaData tableMetaData : packageMetaData.getTables())
        {
        	logger.info("adding table : " + tableMetaData.getName());
            Table table = tableFactory.buildTable(tableMetaData, myPackage);
            logger.info("table : " + table.name + " added");

            logger.info("adding bean from table : " + table.name);
			Bean bean = beanFactory.buildBean(tableMetaData, table);
			logger.info("bean : " + bean.className + " added");
        }
        
        
        return myPackage;
	}

}
