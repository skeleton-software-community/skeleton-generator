package org.sklsft.generator.bc.metadata.impl;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.inject.Inject;

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
	@Inject
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

		myPackage.omPackageName = model.modelPackageName + "." + myPackage.name;
		myPackage.ovPackageName = model.apiModelPackageName + "." + myPackage.name;

		myPackage.baseDAOImplPackageName = model.daoImplPackageName + "." + myPackage.name + ".base";
		myPackage.baseDAOInterfacePackageName = model.daoInterfacePackageName + "." + myPackage.name + ".base";
		myPackage.DAOImplPackageName = model.daoImplPackageName + "." + myPackage.name;
		myPackage.DAOInterfacePackageName = model.daoInterfacePackageName + "." + myPackage.name;

		myPackage.baseServiceImplPackageName = model.serviceImplPackageName + "." + myPackage.name + ".base";
		myPackage.baseServiceInterfacePackageName = model.serviceInterfacePackageName + "." + myPackage.name + ".base";
		myPackage.serviceImplPackageName = model.serviceImplPackageName + "." + myPackage.name;
		myPackage.serviceInterfacePackageName = model.serviceInterfacePackageName + "." + myPackage.name;

		myPackage.baseMapperImplPackageName = model.mapperPackageName + "." + myPackage.name + ".base";
		myPackage.mapperImplPackageName = model.mapperPackageName + "." + myPackage.name;

		myPackage.baseStateManagerImplPackageName = model.stateManagerPackageName + "." + myPackage.name + ".base";
		myPackage.stateManagerImplPackageName = model.stateManagerPackageName + "." + myPackage.name;
		
		myPackage.baseProcessorImplPackageName = model.processorPackageName + "." + myPackage.name + ".base";
		myPackage.processorImplPackageName = model.processorPackageName + "." + myPackage.name;

		myPackage.baseControllerPackageName = model.controllerPackageName + "." + myPackage.name + ".base";
		myPackage.controllerPackageName = model.controllerPackageName + "." + myPackage.name;
		myPackage.listViewPackageName = model.mvcModelPackageName + "." + myPackage.name;
		myPackage.detailViewPackageName = model.mvcModelPackageName + "." + myPackage.name;

		myPackage.filterPackageName = model.filterPackageName + "." + myPackage.name;

		myPackage.builderPackageName = model.builderPackageName + "." + myPackage.name;
		myPackage.commandPackageName = model.commandPackageName + "." + myPackage.name;

		myPackage.tables = new ArrayList<Table>();
		myPackage.beans = new ArrayList<Bean>();
		return myPackage;
	}

}
