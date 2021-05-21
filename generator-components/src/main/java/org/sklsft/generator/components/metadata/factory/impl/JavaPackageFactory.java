package org.sklsft.generator.components.metadata.factory.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.sklsft.generator.components.metadata.factory.interfaces.BeanFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.PackageFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.ProjectFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.TableFactory;
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
	public List<Package> scanPackages(PackageMetaData packageMetaData, Model model, Package parent) {
		
		List<Package> result = new ArrayList<>();
		
		Package myPackage = setUpPackage(packageMetaData, model, parent);
		logger.trace("Package found : " + myPackage.name);
		
		if (packageMetaData.getTables() != null) {
			for (TableMetaData tableMetaData : packageMetaData.getTables()){
				
				Table table = tableFactory.scanTable(tableMetaData, myPackage);
				myPackage.tables.add(table);
				
				Bean bean = beanFactory.scanBean(tableMetaData, table);
				myPackage.beans.add(bean);
			}
		}
		
		result.add(myPackage);
		
		if (packageMetaData.getPackages() != null) {
			for (PackageMetaData child:packageMetaData.getPackages()) {
				result.addAll(scanPackages(child, model, myPackage));
			}
		}
		
		return result;
	}

	@Override
	public void fillPackage(PackageMetaData packageMetaData, Model model){
		if (packageMetaData.getTables() != null) {
			for (TableMetaData tableMetaData : packageMetaData.getTables()){
				logger.trace("Filling table " + tableMetaData.getName());
				Table table = tableFactory.fillTable(tableMetaData, model);
				logger.trace("Creating bean from table : " + table.name);
				beanFactory.fillBean(tableMetaData, table, model);
			}
		}
		if (packageMetaData.getPackages() != null) {
			for (PackageMetaData child:packageMetaData.getPackages()) {
				fillPackage(child, model);
			}
		}
	}

	private Package setUpPackage(PackageMetaData packageMetaData, Model model, Package parent) {
		Package myPackage = new Package();
		myPackage.model = model;
		myPackage.name = packageMetaData.getName().toLowerCase();
		if (parent != null) {
			myPackage.name = parent.name + "." + myPackage.name;
		}
		myPackage.urlPiece = myPackage.name.replace(".", "/");
		
		myPackage.omPackageName = model.modelPackageName + "." + myPackage.name;
		
		myPackage.fullViewsPackageName = model.apiModelPackageName + "." + myPackage.name + ".views.full";
		myPackage.basicViewsPackageName = model.apiModelPackageName + "." + myPackage.name + ".views.basic";
		myPackage.formsPackageName = model.apiModelPackageName + "." + myPackage.name + ".forms";
		myPackage.filtersPackageName = model.apiModelPackageName + "." + myPackage.name + ".filters";
		myPackage.sortingsPackageName = model.apiModelPackageName + "." + myPackage.name + ".sortings";
		
		myPackage.baseDAOImplPackageName = model.daoImplPackageName + "." + myPackage.name + ".base";
		myPackage.baseDAOInterfacePackageName = model.daoInterfacePackageName + "." + myPackage.name + ".base";
		myPackage.DAOImplPackageName = model.daoImplPackageName + "." + myPackage.name;
		myPackage.DAOInterfacePackageName = model.daoInterfacePackageName + "." + myPackage.name;
		
		myPackage.baseServiceInterfacePackageName = model.serviceInterfacePackageName + "." + myPackage.name + ".base";
		myPackage.serviceInterfacePackageName = model.serviceInterfacePackageName + "." + myPackage.name;
		myPackage.baseServiceImplPackageName = model.servicesPackageName + "." + myPackage.name + ".base";
		myPackage.serviceImplPackageName = model.servicesPackageName + "." + myPackage.name;
		myPackage.baseRestClientPackageName = model.restClientPackageName + "." + myPackage.name + ".base";
		myPackage.restClientPackageName = model.restClientPackageName + "." + myPackage.name;
		
		
		
		myPackage.baseFormMapperPackageName = model.mapperPackageName + "." + myPackage.name + ".forms.base";
		myPackage.formMapperPackageName = model.mapperPackageName + "." + myPackage.name + ".forms";
		myPackage.fullViewMapperPackageName = model.mapperPackageName + "." + myPackage.name + ".views.full";
		myPackage.baseBasicViewMapperPackageName = model.mapperPackageName + "." + myPackage.name + ".views.basic.base";
		myPackage.baseFullViewMapperPackageName = model.mapperPackageName + "." + myPackage.name + ".views.full.base";
		myPackage.basicViewMapperPackageName = model.mapperPackageName + "." + myPackage.name + ".views.basic";
		
		myPackage.baseStateManagerImplPackageName = model.stateManagerPackageName + "." + myPackage.name + ".base";
		myPackage.stateManagerImplPackageName = model.stateManagerPackageName + "." + myPackage.name;
		
		myPackage.baseRightsManagerImplPackageName = model.rightsManagerPackageName + "." + myPackage.name + ".base";
		myPackage.rightsManagerImplPackageName = model.rightsManagerPackageName + "." + myPackage.name;
		
		myPackage.baseProcessorImplPackageName = model.processorPackageName + "." + myPackage.name + ".base";
		myPackage.processorImplPackageName = model.processorPackageName + "." + myPackage.name;
		
		myPackage.baseRestControllerPackageName = model.restControllerPackageName + "." + myPackage.name + ".base";
		myPackage.restControllerPackageName = model.restControllerPackageName + "." + myPackage.name;
		
		myPackage.baseListControllerPackageName = model.controllerPackageName + "." + myPackage.name + ".list.base";
		myPackage.listControllerPackageName = model.controllerPackageName + "." + myPackage.name+ ".list";
		myPackage.baseDetailControllerPackageName = model.controllerPackageName + "." + myPackage.name + ".detail.base";
		myPackage.detailControllerPackageName = model.controllerPackageName + "." + myPackage.name + ".detail";
		
		myPackage.listViewPackageName = model.mvcModelPackageName + "." + myPackage.name + ".list";
		myPackage.detailViewPackageName = model.mvcModelPackageName + "." + myPackage.name + ".detail";
		
		myPackage.commandPackageName = model.commandPackageName + "." + myPackage.name;
		
		myPackage.tables = new ArrayList<Table>();
		myPackage.beans = new ArrayList<Bean>();
		
		return myPackage;
	}

}
