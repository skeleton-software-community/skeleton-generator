package org.sklsft.generator.bc.metadata.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.sklsft.generator.bc.metadata.interfaces.BeanFactory;
import org.sklsft.generator.bc.metadata.interfaces.PackageFactory;
import org.sklsft.generator.bc.metadata.interfaces.ProjectFactory;
import org.sklsft.generator.bc.metadata.interfaces.TableFactory;
import org.sklsft.generator.model.domain.Model;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.PackageMetaData;
import org.sklsft.generator.model.metadata.TableMetaData;
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

		myPackage.baseDAOImplPackageName = model.project.domainName + "." + model.project.projectName + ".repository.dao.impl." + myPackage.name + ".base";
		myPackage.baseDAOInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".repository.dao.interfaces." + myPackage.name + ".base";
		myPackage.DAOImplPackageName = model.project.domainName + "." + model.project.projectName + ".repository.dao.impl." + myPackage.name;
		myPackage.DAOInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".repository.dao.interfaces." + myPackage.name;

		myPackage.baseServiceImplPackageName = model.project.domainName + "." + model.project.projectName + ".bl.impl." + myPackage.name + ".base";
		myPackage.baseServiceInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".api.interfaces." + myPackage.name + ".base";
		myPackage.serviceImplPackageName = model.project.domainName + "." + model.project.projectName + ".bl.impl." + myPackage.name;
		myPackage.serviceInterfacePackageName = model.project.domainName + "." + model.project.projectName + ".api.interfaces." + myPackage.name;

		myPackage.baseMapperImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.mapper." + myPackage.name + ".base";
		myPackage.mapperImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.mapper." + myPackage.name;

		myPackage.baseStateManagerImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.statemanager." + myPackage.name + ".base";
		myPackage.stateManagerImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.statemanager." + myPackage.name;
		
		myPackage.baseProcessorImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.processor." + myPackage.name + ".base";
		myPackage.processorImplPackageName = model.project.domainName + "." + model.project.projectName + ".bc.processor." + myPackage.name;

		myPackage.baseControllerPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.controller." + myPackage.name + ".base";
		myPackage.controllerPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.controller." + myPackage.name;
		myPackage.listViewPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.model." + myPackage.name;
		myPackage.detailViewPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.model." + myPackage.name;

		myPackage.filterPackageName = model.project.domainName + "." + model.project.projectName + ".mvc.filter." + myPackage.name;

		myPackage.builderPackageName = model.project.domainName + "." + model.project.projectName + ".populator.builder." + myPackage.name;
		myPackage.commandPackageName = model.project.domainName + "." + model.project.projectName + ".populator.command." + myPackage.name;

		myPackage.tables = new ArrayList<Table>();
		myPackage.beans = new ArrayList<Bean>();
		return myPackage;
	}

}
