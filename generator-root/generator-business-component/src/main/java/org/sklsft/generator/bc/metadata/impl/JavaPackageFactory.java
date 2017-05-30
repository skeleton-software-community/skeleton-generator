package org.sklsft.generator.bc.metadata.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.sklsft.generator.bc.metadata.interfaces.BeanFactory;
import org.sklsft.generator.bc.metadata.interfaces.PackageFactory;
import org.sklsft.generator.bc.metadata.interfaces.ProjectFactory;
import org.sklsft.generator.bc.metadata.interfaces.TableFactory;
import org.sklsft.generator.model.metadata.PackageMetaData;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Model;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Table;
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
	private TableFactory tableFactory;
	@Resource(name="javaBeanFactory")
	private BeanFactory beanFactory;

	@Override
	public Package scanPackage(PackageMetaData packageMetaData, Model model){
		Package myPackage = setUpPackage(packageMetaData, model);

		for (TableMetaData tableMetaData : packageMetaData.getTables()){
			logger.info("Scanning table " + tableMetaData.getName());
			
			Table table = tableFactory.scanTable(tableMetaData, myPackage);
			myPackage.tables.add(table);
			
			Bean bean = beanFactory.scanBean(tableMetaData, table);
			myPackage.beans.add(bean);
		}

		return myPackage;
	}

	@Override
	public Package fillPackage(PackageMetaData packageMetaData, Model model){
		Package pack = model.findPackage(packageMetaData.getName());
		
		for (TableMetaData tableMetaData : packageMetaData.getTables()){
			logger.info("Filling table " + tableMetaData.getName());
			Table table = tableFactory.fillTable(tableMetaData, pack);
			logger.info("adding bean from table : " + table.name);
			Bean bean = beanFactory.fillBean(table, model);
			logger.info("bean : " + bean.className + " added");
		}
		return pack;
	}

	private Package setUpPackage(PackageMetaData packageMetaData, Model model) {
		Package myPackage = new Package();
		myPackage.model = model;
		myPackage.name = packageMetaData.getName().toLowerCase();

		myPackage.omPackageName = model.project.domainName + "." + model.project.projectName + ".model." + myPackage.name;
		myPackage.ovPackageName = model.project.domainName + "." + model.project.projectName + ".api.model." + myPackage.name;

		myPackage.statusPackageName = model.project.domainName + "." + model.project.projectName + ".model." + myPackage.name + ".status";

		
		myPackage.DAOImplPackageName = model.project.domainName + "." + model.project.projectName + ".repository.dao.impl." + myPackage.name;
		myPackage.DAOInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".repository.dao.interfaces." + myPackage.name;
		myPackage.baseDAOImplPackageName = myPackage.DAOImplPackageName + ".base";
		myPackage.baseDAOInterfacePackageName = myPackage.DAOInterfacePackageName + ".base";

		myPackage.baseServiceImplPackageName = model.project.domainName + "." + model.project.projectName + ".bl.services." + myPackage.name + ".base";
		myPackage.baseServiceInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".api.interfaces." + myPackage.name + ".base";
		myPackage.serviceImplPackageName = model.project.domainName + "." + model.project.projectName + ".bl.services." + myPackage.name;
		myPackage.serviceInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".api.interfaces." + myPackage.name;

		myPackage.mapperPackageName = model.project.domainName + "." + model.project.projectName + ".bc.mapper." + myPackage.name;
		myPackage.baseMapperPackageName = myPackage.mapperPackageName + ".base";	
		
		myPackage.stateManagerPackageName = model.project.domainName + "." + model.project.projectName + ".bc.statemanager." + myPackage.name;
		myPackage.baseStateManagerPackageName = myPackage.stateManagerPackageName + ".base";
		
		myPackage.baseProcessorImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.processor." + myPackage.name + ".base";
		myPackage.processorImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.processor." + myPackage.name;


		myPackage.facadeImplPackageName = model.project.domainName + "." + model.project.projectName + ".facade." + myPackage.name + ".impl";
		myPackage.facadeInterfacesPackageName = model.project.domainName + "." + model.project.projectName + ".facade." + myPackage.name + ".interfaces";

		myPackage.baseControllerPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.controller." + myPackage.name + ".base";
		myPackage.controllerPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.controller." + myPackage.name;

		myPackage.filterPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.filter." + myPackage.name;

		myPackage.builderPackageName = model.project.domainName + "." + model.project.projectName + ".junit.data." + myPackage.name + ".builder";
		myPackage.commandPackageName = model.project.domainName + "." + model.project.projectName + ".junit.data." + myPackage.name + ".command";

		myPackage.tables = new ArrayList<Table>();
		myPackage.beans = new ArrayList<Bean>();
		return myPackage;
	}



	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.bl.factory.interfaces.PackageFactory#buildPackage(com.skeleton.generator.model.metadata.PackageMetaData, com.skeleton.generator.model.om.Model)
	 */
	//	public Package buildPackage(PackageMetaData packageMetaData, Model model) {
	//
	//
	//        for (TableMetaData tableMetaData : packageMetaData.getTables())
	//        {
	//        	logger.info("adding table : " + tableMetaData.getName());
	//            Table table = tableFactory.buildTable(tableMetaData, myPackage);
	//            logger.info("table : " + table.name + " added");
	//
	//            logger.info("adding bean from table : " + table.name);
	//			Bean bean = beanFactory.buildBean(tableMetaData, table);
	//			logger.info("bean : " + bean.className + " added");
	//        }
	//        
	//        
	//	}

}
