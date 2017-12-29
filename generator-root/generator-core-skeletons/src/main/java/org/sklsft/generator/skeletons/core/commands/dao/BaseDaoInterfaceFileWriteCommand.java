package org.sklsft.generator.skeletons.core.commands.dao;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class BaseDaoInterfaceFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public BaseDaoInterfaceFileWriteCommand(Bean bean) {

		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-repository" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseDAOInterfacePackageName.replace(".", File.separator), bean.baseDaoInterfaceName);

		this.bean = bean;

	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.math.BigDecimal;");
		javaImports.add("import org.sklsft.commons.model.patterns.BaseDao;");
		javaImports.add("import " + bean.myPackage.omPackageName + "." + bean.className + ";");
		javaImports.add("import " + bean.myPackage.filtersPackageName + "." + bean.basicViewBean.filterClassName + ";");
		javaImports.add("import " + bean.myPackage.sortingsPackageName + "." + bean.basicViewBean.sortingClassName + ";");
		
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.filtersPackageName + "." + currentBean.basicViewBean.filterClassName + ";");
			javaImports.add("import " + currentBean.myPackage.sortingsPackageName + "." + currentBean.basicViewBean.sortingClassName + ";");
		}
		
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
		}
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + bean.myPackage.baseDAOInterfacePackageName + ";");
		
		
		writeImports();
		
		writeLine("/**");
		writeLine(" * auto generated base dao interface file");
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public interface " + this.bean.baseDaoInterfaceName + " extends BaseDao<" + this.bean.className + ", Long> {");
		skipLine();

		createLoadObjectList();
		createCount();
		createScroll();
		createLoadOneToManyComponentList();
		createCountOneToManyComponent();		
		createScrollOneToManyComponent();
		createLoadOneToManyComponent();
		
		if (bean.cardinality>0) {
			createExistsObject();
			createFindObject();
		}
		if (bean.selectable) {
			if (bean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {
				createSearch();
			}
		}
		createSaveComponent();
		createDeleteComponent();

		writeLine("}");
	}
	
	private void createLoadObjectList() {		

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {

				writeLine("/**");
				writeLine(" * load object list from " + property.referenceBean.objectName); 
				writeLine(" */");
				writeLine("List<" + this.bean.className + "> loadListFrom" + property.capName + "(Long " + property.name + "Id);");
				skipLine();
				
				writeLine("/**");
				writeLine(" * load object list eagerly from " + property.referenceBean.objectName);
				writeLine(" */");
				writeLine("List<" + this.bean.className + "> loadListEagerlyFrom" + property.capName + "(Long " + property.name + "Id);");
				skipLine();

			}
		}
	}
	
	
	private void createCount() {
		writeLine("/**");
		writeLine(" * count filtered object list");
		writeLine(" */");
		writeLine("Long count(" + bean.basicViewBean.filterClassName + " filter);");
		skipLine();
		
		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {

				writeLine("/**");
				writeLine(" * count object list from " + property.referenceBean.objectName); 
				writeLine(" */");
				writeLine("Long countFrom" + property.capName + "(Long " + property.name + "Id);");
				skipLine();
				
				writeLine("/**");
				writeLine(" * count filtered object list from " + property.referenceBean.objectName); 
				writeLine(" */");
				writeLine("Long countFrom" + property.capName + "(Long " + property.name + "Id, " + bean.basicViewBean.filterClassName + " filter);");
				skipLine();
			}
		}
	}
	
	private void createScroll() {
		writeLine("/**");
		writeLine(" * scroll filtered object list");
		writeLine(" */");
		writeLine("List<" + this.bean.className + "> scroll(" + bean.basicViewBean.filterClassName + " filter, " + bean.basicViewBean.sortingClassName + " sorting, Long firstResult, Long maxResults);");
		skipLine();
		
		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {

				writeLine("/**");
				writeLine(" * scroll filtered object from " + property.referenceBean.objectName); 
				writeLine(" */");
				writeLine("List<" + this.bean.className + "> scrollFrom" + property.capName + "(Long " + property.name + "Id, " + bean.basicViewBean.filterClassName + " filter, " + bean.basicViewBean.sortingClassName + " sorting, Long firstResult, Long maxResults);");
				skipLine();
			}
		}
	}
	
	
	private void createLoadOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.className + " list");
            writeLine(" */");
			writeLine("List<" + currentBean.className + "> load" + currentBean.className + "List(Long " + bean.objectName + "Id);");
			skipLine();
        }
	}
	
	
	private void createCountOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * count one to many component " + currentBean.className); 
			writeLine(" */");
			writeLine("Long count" + currentBean.className + "(Long " + bean.objectName + "Id);");
			skipLine();
			
			writeLine("/**");
			writeLine(" * count filtered one to many component " + currentBean.className);
			writeLine(" */");
			writeLine("Long count" + currentBean.className + "(Long " + bean.objectName + "Id, " + currentBean.basicViewBean.filterClassName + " filter);");
			skipLine();
		}
	}
	
	
	private void createScrollOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * scroll filtered one to many component " + currentBean.className); 
			writeLine(" */");
			writeLine("List<" + currentBean.className + "> scroll" + currentBean.className + "(Long " + bean.objectName + "Id, " + currentBean.basicViewBean.filterClassName + " filter, " + currentBean.basicViewBean.sortingClassName + " sorting, Long firstResult, Long maxResults);");
			skipLine();
		}
	}

	
	private void createLoadOneToManyComponent() {		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("/**");
			writeLine(" * load one to many component " + currentBean.className);
			writeLine(" */");
			writeLine(currentBean.className + " load" + currentBean.className + "(Long id);");
			skipLine();
		}
	}

	private void createExistsObject() {
		boolean start = true;
		writeLine("/**");
		writeLine(" * exists object");
		writeLine(" */");
		write("boolean exists(");
		for (ViewProperty property:bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(property.beanDataType + " " + property.name);
		}
		writeLine(");");
		skipLine();
	}
	
	private void createFindObject() {
		boolean start = true;
		writeLine("/**");
		writeLine(" * find object or null");
		writeLine(" */");
		write(this.bean.className + " findOrNull(");
		for (ViewProperty property:bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(property.beanDataType + " " + property.name);
		}
		writeLine(");");
		skipLine();
		
		start = true;
		writeLine("/**");
		writeLine(" * find object");  
		writeLine(" */");
		write(this.bean.className + " find(");
		for (ViewProperty property:bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(property.beanDataType + " " + property.name);
		}
		writeLine(");");
		skipLine();
	}
	
	
	private void createSearch() {
		writeLine("/**");
		writeLine(" * search");
		writeLine(" */");
		writeLine("List<" + this.bean.className + "> search(String arg);");
		skipLine();
	}

	private void createSaveComponent() {
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList){
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("/**");
			writeLine(" * save one to many component " + currentBean.className);
			writeLine(" */");
			writeLine("void save" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.className + " " + currentBean.objectName + ");");
			skipLine();
		}
		
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList){
			Bean currentBean = oneToOneComponent.referenceBean;
			writeLine("/**");
			writeLine(" * save one to one component " + currentBean.className);
			writeLine(" */");
			writeLine("void save" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.className + " " + currentBean.objectName + ");");
			skipLine();
		}
	}


	private void createDeleteComponent() {
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList){
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.className);
			writeLine(" */");
			writeLine("void delete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ");");
			skipLine();
		}
		
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList){
			Bean currentBean = oneToOneComponent.referenceBean;
			writeLine("/**");
			writeLine(" * delete one to one component " + currentBean.className);
			writeLine(" */");
			writeLine("void delete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ");");
			skipLine();
		}
	}
}
