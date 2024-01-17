package org.sklsft.generator.skeletons.core.commands.services;

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

public class BaseServiceImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	
	public BaseServiceImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.servicesArtefactName + File.separator + bean.myPackage.model.javaSourcesFolder + File.separator
				+ bean.myPackage.baseServiceImplPackageName.replace(".", File.separator), bean.baseServiceClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.time.LocalDate;");
		javaImports.add("import java.math.BigDecimal;");
		javaImports.add("import java.util.Collection;");
        javaImports.add("import java.util.List;");
        javaImports.add("import java.util.ArrayList;");
        javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
        javaImports.add("import org.springframework.transaction.annotation.Transactional;");
        javaImports.add("import org.sklsft.commons.api.model.ScrollForm;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollView;");
		javaImports.add("import org.sklsft.commons.api.model.SelectItem;");
        javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
        javaImports.add("import " + this.bean.myPackage.basicViewsPackageName + "." + this.bean.basicViewBean.className + ";");
        javaImports.add("import " + this.bean.myPackage.fullViewsPackageName + "." + this.bean.fullViewBean.className + ";");
        javaImports.add("import " + this.bean.myPackage.formsPackageName + "." + this.bean.formBean.className + ";");
        javaImports.add("import " + bean.myPackage.filtersPackageName + "." + bean.basicViewBean.filter.className + ";");
		javaImports.add("import " + bean.myPackage.sortingsPackageName + "." + bean.basicViewBean.sortingClassName + ";");
        javaImports.add("import " + this.bean.myPackage.DAOInterfacePackageName + "." + this.bean.daoInterfaceName + ";");
        
        for (Property property:bean.properties) {
        	if (property.referenceBean!=null) {
        		if (property.relation.equals(RelationType.MANY_TO_ONE)) {
        			
        			Bean parentBean = property.referenceBean;
        			
        			javaImports.add("import " + parentBean.myPackage.omPackageName + "." + parentBean.className + ";");
        			javaImports.add("import " + parentBean.myPackage.DAOInterfacePackageName + "." + parentBean.daoInterfaceName + ";");
        		}
        	}
        }
        
        javaImports.add("import " + this.bean.myPackage.basicViewMapperPackageName + "." + this.bean.basicViewBean.mapperClassName + ";");
        javaImports.add("import " + this.bean.myPackage.fullViewMapperPackageName + "." + this.bean.fullViewBean.mapperClassName + ";");
        javaImports.add("import " + this.bean.myPackage.formMapperPackageName + "." + this.bean.formBean.mapperClassName + ";");
        javaImports.add("import " + this.bean.myPackage.processorImplPackageName + "." + this.bean.processorClassName + ";");
        javaImports.add("import " + this.bean.myPackage.stateManagerImplPackageName + "." + this.bean.stateManagerClassName + ";");
        javaImports.add("import " + this.bean.myPackage.rightsManagerImplPackageName + "." + this.bean.rightsManagerClassName + ";");

        javaImports.add("import " + this.bean.myPackage.baseServiceInterfacePackageName + "." + this.bean.baseServiceInterfaceName + ";");
        

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.formsPackageName + "." + currentBean.formBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.fullViewMapperPackageName + "." + currentBean.fullViewBean.mapperClassName + ";");
            javaImports.add("import " + currentBean.myPackage.formMapperPackageName + "." + currentBean.formBean.mapperClassName + ";");
        }

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.basicViewsPackageName + "." + currentBean.basicViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.basicViewMapperPackageName + "." + currentBean.basicViewBean.mapperClassName + ";");
			javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.fullViewMapperPackageName + "." + currentBean.fullViewBean.mapperClassName + ";");
			javaImports.add("import " + currentBean.myPackage.formsPackageName + "." + currentBean.formBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.filtersPackageName + "." + currentBean.basicViewBean.filter.className + ";");
			javaImports.add("import " + currentBean.myPackage.sortingsPackageName + "." + currentBean.basicViewBean.sortingClassName + ";");
			javaImports.add("import " + currentBean.myPackage.formMapperPackageName + "." + currentBean.formBean.mapperClassName + ";");
		}
		
	}

	@Override
	protected void writeContent() throws IOException {
			
		writeLine("package " + this.bean.myPackage.baseServiceImplPackageName + ";");
		skipLine();
		
		writeImports();
		skipLine();
		
		writeLine("/**");
		writeLine(" * auto generated base service class file"); 
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.baseServiceClassName + " implements " + this.bean.baseServiceInterfaceName + " {");
		skipLine();
		
		writeLine("/*"); 
		writeLine(" * properties injected by spring");
		writeLine(" */");
		
		writeLine("@Autowired");
		writeLine("protected " + this.bean.daoInterfaceName + " " + this.bean.daoObjectName + ";");
		
		for (Property property:bean.properties) {
			if (property.referenceBean!=null) {
				if (property.relation.equals(RelationType.MANY_TO_ONE)) {
					
					Bean parentBean = property.referenceBean;
					writeLine("@Autowired");
					writeLine("protected " + parentBean.daoInterfaceName + " " + parentBean.daoObjectName + ";");
				}
			}
		}
		
		writeLine("@Autowired");
		writeLine("protected " + this.bean.fullViewBean.mapperClassName + " " + this.bean.fullViewBean.mapperObjectName + ";");
		writeLine("@Autowired");
		writeLine("protected " + this.bean.basicViewBean.mapperClassName + " " + this.bean.basicViewBean.mapperObjectName + ";");
		writeLine("@Autowired");
		writeLine("protected " + this.bean.formBean.mapperClassName + " " + this.bean.formBean.mapperObjectName + ";");
		
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
		    Bean currentBean = oneToOneComponent.referenceBean;
		    writeLine("@Autowired");
		    writeLine("protected " + currentBean.fullViewBean.mapperClassName + " " + currentBean.fullViewBean.mapperObjectName + ";");
		    writeLine("@Autowired");
		    writeLine("protected " + currentBean.formBean.mapperClassName + " " + currentBean.formBean.mapperObjectName + ";");
		}
		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
		    Bean currentBean = oneToManyComponent.referenceBean;
		    writeLine("@Autowired");
			writeLine("protected " + currentBean.fullViewBean.mapperClassName + " " + currentBean.fullViewBean.mapperObjectName + ";");
			writeLine("@Autowired");
			writeLine("protected " + currentBean.basicViewBean.mapperClassName + " " + currentBean.basicViewBean.mapperObjectName + ";");
			writeLine("@Autowired");
		    writeLine("protected " + currentBean.formBean.mapperClassName + " " + currentBean.formBean.mapperObjectName + ";");
		}
		
		writeLine("@Autowired");
		writeLine("protected " + this.bean.stateManagerClassName + " " + this.bean.stateManagerObjectName + ";");
		
		writeLine("@Autowired");
		writeLine("protected " + this.bean.rightsManagerClassName + " " + this.bean.rightsManagerObjectName + ";");
		
		writeLine("@Autowired");
		writeLine("protected " + this.bean.processorClassName + " " + this.bean.processorObjectName + ";");
		skipLine();
		
		
		
		if (this.bean.selectable) {
			createGetOptions();
		}
		createLoadObjectList();
		createScroll();
		createLoadObject();
		if (bean.cardinality>0) {
			createFindObject();
		}
		createLoadOneToOneComponent();
		createLoadOneToManyComponentList();
		createScrollOneToManyComponent();
		createLoadOneToManyComponent();
		createCreateObject();
		createCreateOneToManyComponent();
		createSaveObject();
		createSaveOneToOneComponent();
		createSaveOneToManyComponent();
		createUpdateObject();
		createUpdateOneTOneComponent();
		createUpdateOneToManyComponent();
		createDeleteObject();
		createDeleteOneToOneComponent();
		createDeleteOneToManyComponent();
		createDeleteObjectList();
		createDeleteOneToManyComponentList();
		
		writeLine("}");

    }
	
	private void createGetOptions() {
			
		Property targetProperty = bean.selectionBehavior.targetProperty;
		Property labelProperty = bean.selectionBehavior.labelProperty!=null?bean.selectionBehavior.labelProperty:bean.selectionBehavior.targetProperty;
		
		if (bean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
			
			writeLine("/**");
			writeLine(" * get options");
			writeLine(" */");
			writeLine("@Override");
			writeLine("@Transactional(readOnly=true)");
			writeLine("public List<SelectItem> getOptions() {");
			writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".loadList();");
			writeLine("List<SelectItem> result = new ArrayList<>(" + this.bean.objectName + "List.size());");
			writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
			writeLine("result.add(new SelectItem(" + this.bean.objectName + "." + targetProperty.getterName + "(), " + this.bean.objectName + "." + labelProperty.getterName + "()));");
			writeLine("}");
			writeLine("return result;");
			writeLine("}");
			skipLine();
		}
		if (bean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {			
			writeLine("/**");
			writeLine(" * search options");
			writeLine(" */");
			writeLine("@Override");
			writeLine("@Transactional(readOnly=true)");
			writeLine("public List<SelectItem> searchOptions(String arg) {");
			writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".search(arg);");
			writeLine("List<SelectItem> result = new ArrayList<>(" + this.bean.objectName + "List.size());");
			writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
			writeLine("result.add(new SelectItem(" + this.bean.objectName + "." + targetProperty.getterName + "(), " + this.bean.objectName + "." + labelProperty.getterName + "()));");
			writeLine("}");
			writeLine("return result;");
			writeLine("}");
			skipLine();
		}
	}

    private void createLoadObjectList() {
		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("@Override");
		writeLine("@Transactional(readOnly=true)");
		writeLine("public List<" + this.bean.basicViewBean.className + "> loadList() {");
		
        writeLine(this.bean.rightsManagerObjectName + ".checkCanAccess();");

		writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".loadListEagerly();");
		writeLine("List<" + this.bean.basicViewBean.className + "> result = new ArrayList<>(" + this.bean.objectName + "List.size());");
		writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
		writeLine("result.add(this." + bean.basicViewBean.mapperObjectName + ".mapFrom(new " + this.bean.basicViewBean.className + "()," + this.bean.objectName + "));");
		writeLine("}");
		writeLine("return result;");
		writeLine("}");
		skipLine();
		
		for (Property property : this.bean.properties) {
		    if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
		        writeLine("/**");
		        writeLine(" * load object list from " + property.name);
		        writeLine(" */");
		        writeLine("@Override");
		        writeLine("@Transactional(readOnly=true)");
		        writeLine("public List<" + this.bean.basicViewBean.className + "> loadListFrom" + property.capName + " (" + property.referenceBean.idType + " " + property.name + "Id) {");
		        writeLine(this.bean.rightsManagerObjectName + ".checkCanAccess();");
		        writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".loadListEagerlyFrom" + property.capName + "(" + property.name + "Id);");
		        writeLine("List<" + this.bean.basicViewBean.className + "> result = new ArrayList<>(" + this.bean.objectName + "List.size());");
		        writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
		        writeLine("result.add(this." + bean.basicViewBean.mapperObjectName + ".mapFrom(new " + this.bean.basicViewBean.className + "()," + this.bean.objectName + "));");
		        writeLine("}");
		        writeLine("return result;");
		        writeLine("}");
		        skipLine();
		    }
		}
    }
    
    
    private void createScroll() {

		writeLine("/**");
		writeLine(" * scroll object list");
		writeLine(" */");
		writeLine("@Override");
		writeLine("@Transactional(readOnly=true)");
		writeLine("public ScrollView<" + this.bean.basicViewBean.className + "> scroll(ScrollForm<" + bean.basicViewBean.filter.className + ", " + bean.basicViewBean.sortingClassName + "> form) {");
		writeLine(this.bean.rightsManagerObjectName + ".checkCanAccess();");
		writeLine("ScrollView<" + this.bean.basicViewBean.className + "> result = new ScrollView<>();");
		writeLine("result.setSize(" + bean.daoObjectName + ".count());");
		writeLine("Long count = " + bean.daoObjectName + ".count(form.getFilter());");
		writeLine("result.setCount(count);");
		writeLine("result.setNumberOfPages(count/form.getElementsPerPage() + ((count%form.getElementsPerPage()) > 0L?1L:0L));");
		writeLine("result.setCurrentPage(Math.max(1L, Math.min(form.getPage()!=null?form.getPage():1L, result.getNumberOfPages())));");
		writeLine("List<" + this.bean.className + "> list = " + bean.daoObjectName + ".scroll(form.getFilter(), form.getSorting(),(result.getCurrentPage()-1)*form.getElementsPerPage(), form.getElementsPerPage());");
		writeLine("List<" + this.bean.basicViewBean.className + "> elements = new ArrayList<>(list.size());");
		writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : list) {");
		writeLine("elements.add(this." + bean.basicViewBean.mapperObjectName + ".mapFrom(new " + this.bean.basicViewBean.className + "()," + this.bean.objectName + "));");
		writeLine("}");
		writeLine("result.setElements(elements);");
		writeLine("return result;");
		writeLine("}");
		skipLine();
		
		for (Property property : this.bean.properties) {
		    if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
		        writeLine("/**");
		        writeLine(" * scroll object list from " + property.name);
		        writeLine(" */");
		        writeLine("@Override");
				writeLine("@Transactional(readOnly=true)");
				writeLine("public ScrollView<" + this.bean.basicViewBean.className + "> scrollFrom" + property.capName + " (" + property.referenceBean.idType + " " + property.name + "Id, ScrollForm<" + bean.basicViewBean.filter.className + ", " + bean.basicViewBean.sortingClassName + "> form) {");
				writeLine(this.bean.rightsManagerObjectName + ".checkCanAccess();");
				writeLine("ScrollView<" + this.bean.basicViewBean.className + "> result = new ScrollView<>();");
				writeLine("result.setSize(" + bean.daoObjectName + ".countFrom" + property.capName + "(" + property.name + "Id));");
				writeLine("Long count = " + bean.daoObjectName + ".countFrom" + property.capName + "(" + property.name + "Id, form.getFilter());");
				writeLine("result.setCount(count);");
				writeLine("result.setNumberOfPages(count/form.getElementsPerPage() + ((count%form.getElementsPerPage()) > 0L?1L:0L));");
				writeLine("result.setCurrentPage(Math.max(1L, Math.min(form.getPage()!=null?form.getPage():1L, result.getNumberOfPages())));");
				writeLine("List<" + this.bean.className + "> list = " + bean.daoObjectName + ".scrollFrom" + property.capName + "(" + property.name + "Id, form.getFilter(), form.getSorting(),(result.getCurrentPage()-1)*form.getElementsPerPage(), form.getElementsPerPage());");
				writeLine("List<" + this.bean.basicViewBean.className + "> elements = new ArrayList<>(list.size());");
				writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : list) {");
				writeLine("elements.add(this." + bean.basicViewBean.mapperObjectName + ".mapFrom(new " + this.bean.basicViewBean.className + "()," + this.bean.objectName + "));");
				writeLine("}");
				writeLine("result.setElements(elements);");
				writeLine("return result;");
				writeLine("}");
				skipLine();
		    }
		}
    }
    

	private void createLoadObject() {
		writeLine("/**");
		writeLine(" * load object");
		writeLine(" */");
		writeLine("@Override");
		writeLine("@Transactional(readOnly=true)");
		writeLine("public " + this.bean.fullViewBean.className + " load(" + bean.idType + " id) {");
		writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");
        writeLine(this.bean.rightsManagerObjectName + ".checkCanAccess(" + this.bean.objectName + ");");
		writeLine("return this." + bean.fullViewBean.mapperObjectName + ".mapFrom(new " + this.bean.fullViewBean.className + "()," + this.bean.objectName + ");");
		writeLine("}");
		skipLine();

	}
    
    
    private void createFindObject() {
    	boolean start = true;
        writeLine("/**");
        writeLine(" * find object");
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(readOnly=true)");
        write("public " + this.bean.fullViewBean.className + " find(");
		for (ViewProperty property:bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(property.javaType + " " + property.name);
		}
		writeLine(") {");
        start = true;
		write(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".find(");
		for (ViewProperty property:bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(property.name);
        }
        writeLine(");");
        writeLine(this.bean.rightsManagerObjectName + ".checkCanAccess(" + this.bean.objectName + ");");
        writeLine("return this." + bean.fullViewBean.mapperObjectName + ".mapFrom(new " + this.bean.fullViewBean.className + "(), " + this.bean.objectName + ");");
        writeLine("}");
        skipLine();
    }
    

    private void createLoadOneToOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(readOnly=true)");
            writeLine("public " + currentBean.fullViewBean.className + " load" + currentBean.className + "(" + bean.idType + " id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanAccess" + currentBean.className + "(" + this.bean.objectName + ");");
            writeLine(currentBean.className + " " + currentBean.objectName + " = " + bean.objectName + ".get" + currentBean.className + "();");
            writeLine("if (" + currentBean.objectName + "==null) {");
            writeLine("return new " + currentBean.fullViewBean.className + "();");
            writeLine("} else {");
            writeLine("return this." + currentBean.fullViewBean.mapperObjectName + ".mapFrom(new " + currentBean.fullViewBean.className + "(), " + currentBean.objectName + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
    }

    private void createLoadOneToManyComponentList() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.objectName + " list");
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(readOnly=true)");
            writeLine("public List<" + currentBean.basicViewBean.className + "> load" + currentBean.className + "List(" + bean.idType + " id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanAccess" + currentBean.className + "(" + this.bean.objectName + ");");
            writeLine("List<" + currentBean.className + "> " + currentBean.objectName + "List = " + this.bean.daoObjectName + ".load" + currentBean.className + "List(id);");
            writeLine("List<" + currentBean.basicViewBean.className + "> result = new ArrayList<>(" + currentBean.objectName + "List.size());");
            writeLine("for (" + currentBean.className + " " + currentBean.objectName + ":" + currentBean.objectName + "List){");
            writeLine("result.add(this." + currentBean.basicViewBean.mapperObjectName + ".mapFrom(new " + currentBean.basicViewBean.className + "()," + currentBean.objectName + "));");
            writeLine("}");
            writeLine("return result;");
            writeLine("}");
            skipLine();
        }
    }
    
    private void createScrollOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * scroll one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@Override");
            writeLine("@Transactional(readOnly=true)");
			writeLine("public ScrollView<" + currentBean.basicViewBean.className + "> scroll" + currentBean.className + " (" + bean.idType + " " + bean.objectName + "Id, ScrollForm<" + currentBean.basicViewBean.filter.className + ", " + currentBean.basicViewBean.sortingClassName + "> form) {");
			writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load(" + bean.objectName + "Id);");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanAccess" + currentBean.className + "(" + this.bean.objectName + ");");
			writeLine("ScrollView<" + currentBean.basicViewBean.className + "> result = new ScrollView<>();");
			writeLine("result.setSize(" + bean.daoObjectName + ".count" + currentBean.className + "(" + bean.objectName + "Id));");
			writeLine("Long count = " + bean.daoObjectName + ".count" + currentBean.className + "(" + bean.objectName + "Id, form.getFilter());");
			writeLine("result.setCount(count);");
			writeLine("result.setNumberOfPages(count/form.getElementsPerPage() + ((count%form.getElementsPerPage()) > 0L?1L:0L));");
			writeLine("result.setCurrentPage(Math.max(1L, Math.min(form.getPage()!=null?form.getPage():1L, result.getNumberOfPages())));");
			writeLine("List<" + currentBean.className + "> list = " + bean.daoObjectName + ".scroll" + currentBean.className + "(" + bean.objectName + "Id, form.getFilter(), form.getSorting(),(result.getCurrentPage()-1)*form.getElementsPerPage(), form.getElementsPerPage());");
			writeLine("List<" + currentBean.basicViewBean.className + "> elements = new ArrayList<>(list.size());");
			writeLine("for (" + currentBean.className + " " + currentBean.objectName + " : list) {");
			writeLine("elements.add(this." + currentBean.basicViewBean.mapperObjectName + ".mapFrom(new " + currentBean.basicViewBean.className + "()," + currentBean.objectName + "));");
			writeLine("}");
			writeLine("result.setElements(elements);");
			writeLine("return result;");
			writeLine("}");
			skipLine();
		}
	}

    private void createLoadOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(readOnly=true)");
            writeLine("public " + currentBean.fullViewBean.className + " load" + currentBean.className + "(" + currentBean.idType + " id) {");            
            writeLine(currentBean.className + " " + currentBean.objectName + " = " + this.bean.daoObjectName + ".load" + currentBean.className + "(id);");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanAccess" + currentBean.className + "(" + currentBean.objectName + ".get" + oneToManyComponent.referenceProperty.capName + "());");
            writeLine("return this." + currentBean.fullViewBean.mapperObjectName + ".mapFrom(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createCreateObject() {
        writeLine("/**");
        writeLine(" * create object");
        writeLine(" */");
        writeLine("@Override");
        writeLine("public " + this.bean.fullViewBean.className + " create() {");
        writeLine(this.bean.rightsManagerObjectName + ".checkCanCreate();");
        writeLine("return new " + this.bean.fullViewBean.className + "();");
        writeLine("}");
        skipLine();
    }

    private void createCreateOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * create one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(readOnly=true)");
            writeLine("public " + currentBean.fullViewBean.className + " create" + currentBean.className + "(" + bean.idType + " id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanCreate" + currentBean.className + "(" + this.bean.objectName + ");");
            writeLine("return new " + currentBean.fullViewBean.className + "();");
            writeLine("}");
            skipLine();
        }
    }

    private void createSaveObject() {
        writeLine("/**");
        writeLine(" * save object");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(rollbackFor=Exception.class)");
        writeLine("public " + bean.idType + " save(" + this.bean.formBean.className + " " + this.bean.formBean.objectName + ") {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = this." + bean.formBean.mapperObjectName + ".mapTo(" + this.bean.formBean.objectName + ", new " + this.bean.className + "());");
        writeLine(this.bean.rightsManagerObjectName + ".checkCanSave(" + this.bean.objectName + ");");
        writeLine(this.bean.stateManagerObjectName + ".checkCanSave(" + this.bean.objectName + ");");
        writeLine("return " + this.bean.processorObjectName + ".save(" + this.bean.objectName + ");");
        writeLine("}");
        skipLine();
    }
    
    private void createSaveOneToOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * save one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void save" + currentBean.className + "(" + bean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ") {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = this." + currentBean.formBean.mapperObjectName + ".mapTo(" + currentBean.formBean.objectName + ", new " + currentBean.className + "());");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanSave" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine(this.bean.stateManagerObjectName + ".checkCanSave" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine(this.bean.processorObjectName + ".save" + currentBean.className + "(" + currentBean.objectName + ", " + this.bean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createSaveOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * save one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void save" + currentBean.className + "(" + bean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ") {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = this." + currentBean.formBean.mapperObjectName + ".mapTo(" + currentBean.formBean.objectName + ", new " + currentBean.className + "());");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanSave" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine(this.bean.stateManagerObjectName + ".checkCanSave" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine(this.bean.processorObjectName + ".save" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createUpdateObject() {
        writeLine("/**");        
        writeLine(" * update object");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(rollbackFor=Exception.class)");
        writeLine("public void update(" + bean.idType + " id, " + this.bean.formBean.className + " " + this.bean.formBean.objectName + ") {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load(id);");
        writeLine(this.bean.rightsManagerObjectName + ".checkCanUpdate(" + this.bean.objectName + ");");
        writeLine(this.bean.stateManagerObjectName + ".checkCanUpdate(" + this.bean.objectName + ");");
        writeLine(this.bean.objectName + " = this." + bean.formBean.mapperObjectName + ".mapTo(" + this.bean.formBean.objectName + ", " + this.bean.objectName + ");");
        writeLine(this.bean.processorObjectName + ".update" + "(" + bean.objectName + ");");
        writeLine("}");
        skipLine();
    }

    private void createUpdateOneTOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * update one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void update" + currentBean.className + "(" + bean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ") {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = " + bean.objectName + ".get" + currentBean.className + "();");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanUpdate" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine(this.bean.stateManagerObjectName + ".checkCanUpdate" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine(this.bean.objectName + ".set" + currentBean.className + "(this." + currentBean.formBean.mapperObjectName + ".mapTo(" + currentBean.formBean.objectName + ", " + currentBean.objectName + "));");
            writeLine(this.bean.processorObjectName + ".update" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createUpdateOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * update one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void update" + currentBean.className + "(" + currentBean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ") {");
            writeLine(currentBean.className + " " + currentBean.objectName + " = this." + this.bean.daoObjectName + ".load" + currentBean.className + "(id);");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanUpdate" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine(this.bean.stateManagerObjectName + ".checkCanUpdate" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine(currentBean.objectName + " = this." + currentBean.formBean.mapperObjectName + ".mapTo(" + currentBean.formBean.objectName + ", " + currentBean.objectName + ");");
            writeLine(this.bean.processorObjectName + ".update" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
            skipLine();
        }
    }

    private void createDeleteObject() {
        writeLine("/**");        
        writeLine(" * delete object");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(rollbackFor=Exception.class)");
        writeLine("public void delete(" + bean.idType + " id) {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");
        writeLine(this.bean.rightsManagerObjectName + ".checkCanDelete(" + this.bean.objectName + ");");
        writeLine(this.bean.stateManagerObjectName + ".checkCanDelete(" + this.bean.objectName + ");");
        writeLine(this.bean.processorObjectName + ".delete" + "(" + bean.objectName + ");");
        writeLine("}");
        skipLine();
    }

    private void createDeleteOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");            
            writeLine(" * delete one to many component " + currentBean.objectName);            
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void delete" + currentBean.className + "(" + currentBean.idType + " id) {");
            writeLine(currentBean.className + " " + currentBean.objectName + " = " + this.bean.daoObjectName + ".load" + currentBean.className + "(id);");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanDelete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine(this.bean.stateManagerObjectName + ".checkCanDelete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("this." + this.bean.processorObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }
    
    private void createDeleteOneToOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");            
            writeLine(" * delete one to one component " + currentBean.objectName);            
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void delete" + currentBean.className + "(" + bean.idType + " id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = " + bean.objectName + ".get" + currentBean.className + "();");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanDelete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine(this.bean.stateManagerObjectName + ".checkCanDelete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("this." + this.bean.processorObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createDeleteObjectList() {
        writeLine("/**");        
        writeLine(" * delete object list");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(rollbackFor=Exception.class)");
        writeLine("public void deleteList(List<" + bean.idType + "> idList) {");
        writeLine(this.bean.className + " " + this.bean.objectName + ";");
        writeLine("if (idList != null){");
        writeLine("for (" + bean.idType + " id:idList){");
        writeLine(this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");
        writeLine(this.bean.rightsManagerObjectName + ".checkCanDelete(" + this.bean.objectName + ");");
        writeLine(this.bean.stateManagerObjectName + ".checkCanDelete(" + this.bean.objectName + ");");
        writeLine(this.bean.processorObjectName + ".delete" + "(" + bean.objectName + ");");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        skipLine();
    }

    private void createDeleteOneToManyComponentList() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * delete one to many component " + currentBean.objectName + " list");
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void delete" + currentBean.className + "List(List<" + currentBean.idType + "> idList) {");
            writeLine(currentBean.className + " " + currentBean.objectName + ";");
            writeLine("if (idList != null){");
            writeLine("for (" + currentBean.idType + " i:idList){");
            writeLine(currentBean.objectName + " = " + this.bean.daoObjectName + ".load" + currentBean.className + "(i);");
            writeLine(this.bean.rightsManagerObjectName + ".checkCanDelete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine(this.bean.stateManagerObjectName + ".checkCanDelete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("this." + this.bean.processorObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
    }
}
