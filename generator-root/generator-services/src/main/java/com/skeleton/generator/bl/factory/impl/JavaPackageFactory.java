package com.skeleton.generator.bl.factory.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skeleton.generator.bl.factory.interfaces.BeanFactory;
import com.skeleton.generator.bl.factory.interfaces.PackageFactory;
import com.skeleton.generator.bl.factory.interfaces.ProjectFactory;
import com.skeleton.generator.bl.factory.interfaces.TableFactory;
import com.skeleton.generator.model.metadata.PackageMetaData;
import com.skeleton.generator.model.metadata.TableMetaData;
import com.skeleton.generator.model.om.Bean;
import com.skeleton.generator.model.om.Model;
import com.skeleton.generator.model.om.Package;
import com.skeleton.generator.model.om.Table;


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
		model.packageList.add(myPackage);
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

        myPackage.baseStateManagerImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.basetransitionvalidator." + myPackage.name + ".impl";
        myPackage.baseStateManagerInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".bc.basetransitionvalidator." + myPackage.name + ".interfaces";
        myPackage.stateManagerImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.transitionvalidator." + myPackage.name + ".impl";
        myPackage.stateManagerInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".bc.transitionvalidator." + myPackage.name + ".interfaces";


        myPackage.facadeImplPackageName = model.project.domainName + "." + model.project.projectName + ".facade." + myPackage.name + ".impl";
        myPackage.facadeInterfacesPackageName = model.project.domainName + "." + model.project.projectName + ".facade." + myPackage.name + ".interfaces";

        myPackage.baseControllerPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.basecontroller." + myPackage.name + ".impl";
        myPackage.controllerPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.controller." + myPackage.name + ".impl";

        myPackage.builderPackageName = model.project.domainName + "." + model.project.projectName + ".bl.services." + myPackage.name + ".builder";
        myPackage.commandPackageName = model.project.domainName + "." + model.project.projectName + ".bl.services." + myPackage.name + ".command";

        myPackage.tableList = new ArrayList<Table>();
        myPackage.beanList = new ArrayList<Bean>();

        for (TableMetaData tableMetaData : packageMetaData.getTableMetaDataList())
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
