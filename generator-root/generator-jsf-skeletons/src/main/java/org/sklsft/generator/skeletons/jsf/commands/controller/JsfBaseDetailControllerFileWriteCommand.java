package org.sklsft.generator.skeletons.jsf.commands.controller;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DetailMode;
import org.sklsft.generator.model.metadata.Visibility;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class JsfBaseDetailControllerFileWriteCommand extends JavaFileWriteCommand {

	protected Bean bean;

	public JsfBaseDetailControllerFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseDetailControllerPackageName.replace(".", File.separator), bean.baseDetailControllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.ArrayList;");
		javaImports.add("import javax.annotation.PostConstruct;");
		javaImports.add("import javax.inject.Inject;");
		javaImports.add("import org.sklsft.commons.mvc.ajax.AjaxMethodTemplate;");
		javaImports.add("import org.sklsft.commons.mvc.annotations.AjaxMethod;");
		javaImports.add("import org.sklsft.commons.api.exception.rights.OperationDeniedException;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollForm;");
		
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".CommonController;");
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".BaseController;");

		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		
		javaImports.add("import " + this.bean.myPackage.detailViewPackageName + "." + this.bean.detailViewClassName + ";");
	
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.filtersPackageName + "." + currentBean.basicViewBean.filterClassName + ";");
			javaImports.add("import " + currentBean.myPackage.basicViewsPackageName + "." + currentBean.basicViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
		}
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			javaImports.add("import " + currentBean.myPackage.filtersPackageName + "." + currentBean.basicViewBean.filterClassName + ";");
			javaImports.add("import " + currentBean.myPackage.sortingsPackageName + "." + currentBean.basicViewBean.sortingClassName + ";");
			javaImports.add("import " + currentBean.myPackage.basicViewsPackageName + "." + currentBean.basicViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.serviceInterfacePackageName + "." + currentBean.serviceInterfaceName + ";");
		}
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseDetailControllerPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated base detail controller class file");
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.baseDetailControllerClassName + " extends BaseController {");
		skipLine();

		writeLine("/*");
		writeLine(" * services injected by spring");
		writeLine(" */");
		writeLine("@Inject");
		writeLine("protected " + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ";");
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			
			writeLine("@Inject");
			writeLine("protected " + currentBean.serviceInterfaceName + " " + currentBean.serviceObjectName + ";");
		}
		
		writeLine("@Inject");
		writeLine("protected CommonController commonController;");
		skipLine();

		writeLine("/*");
		writeLine(" * view");
		writeLine(" */");
		writeLine("@Inject");
		writeLine("protected " + this.bean.detailViewClassName + " " + this.bean.detailViewObjectName + ";");
		skipLine();
		
		
		createLoadObject();
		createLoadOneToOneComponent();
		createLoadOneToManyComponent();
		createLoadOneToMany();		
		
		createSaveOneToOneComponent();
		createCreateOneToManyComponent();
		createCreateOneToMany();
		createSaveOneToManyComponent();
		createSaveOneToMany();
		
		createUpdateObject();
		createEditOneToMany();
		createUpdateOneToMany();
		createEditOneToManyComponent();
		createUpdateOneToOneComponent();
		createUpdateOneToManyComponent();
		
		createDeleteOneToOneComponent();
		createDeleteOneToManyComponent();		
		createDeleteOneToMany();
		createDeleteOneToManyComponentList();
		createDeleteOneToManyList();
		
		createResetFlters();

		writeLine("}");

	}
	

	private void createLoadObject() {
		
		writeLine("/**");
		writeLine(" * load object");
		writeLine(" */");
		writeLine("public void load() {");
	
		for (ViewProperty property : this.bean.formBean.properties) {
			if (property.comboBoxBean != null && property.visibility.isDetailVisible()) {
				writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
			}
		}
		
		writeLine(this.bean.detailViewObjectName + ".setSelected" + this.bean.className + "(this." + this.bean.serviceObjectName + ".load(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId()));");

		writeLine("}");
		skipLine();
		skipLine();	
	}
	
	
	private void createLoadOneToOneComponent(){
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * load one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void load" + currentBean.className + "() {");
			
			for (ViewProperty property : currentBean.formBean.properties) {
				if (property.comboBoxBean != null && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
				}
			}

			writeLine(this.bean.detailViewObjectName + ".setSelected" + currentBean.className + "(this." + this.bean.serviceObjectName + ".load" + currentBean.className + "(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId()));");

			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createLoadOneToManyComponent(){
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * load one to many component " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("public void load" + currentBean.className + "List() {");

			writeLine("this.reset" + currentBean.basicViewBean.filterClassName + "();");
			
			writeLine(this.bean.detailViewObjectName + ".set" + currentBean.className + "List(this." + this.bean.serviceObjectName + ".load" + currentBean.className + "List(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId()));");
			
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createLoadOneToMany(){
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			
			writeLine("/**");
			writeLine(" * load one to many " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("public void load" + currentBean.className + "List() {");

			writeLine("this.reset" + currentBean.className + "List();");
			
			writeLine("}");
			skipLine();
			
			writeLine("/**");
			writeLine(" * refresh one to many " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("public void refresh" + currentBean.className + "List() {");
			writeLine(bean.detailViewObjectName + ".set" + currentBean.className + "ScrollView(" + currentBean.serviceObjectName + ".scrollFrom" + bean.className + "(" + bean.detailViewObjectName + ".getSelected" + bean.className + "().getId(), " + bean.detailViewObjectName + ".get" + currentBean.className + "ScrollForm()));");
			writeLine(bean.detailViewObjectName + ".get" + currentBean.className + "ScrollForm().setPage(" + bean.detailViewObjectName + ".get" + currentBean.className + "ScrollView().getCurrentPage());");
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createUpdateObject() {
		writeLine("/**");
		writeLine(" * update object");
		writeLine(" */");
		writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".update" + CHAR_34 + ")");
		writeLine("public void update() {");
		writeLine(this.bean.serviceObjectName + ".update(" + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId(), " + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getForm());");
		writeLine("load();");
		writeLine("}");
		skipLine();
	}
	
	
	private void createUpdateOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * update one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".update" + CHAR_34 + ")");
			writeLine("public void update" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".update" + currentBean.className + "(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId(), " + bean.detailViewObjectName + ".getSelected" + currentBean.className + "().getForm());");
			writeLine("load" + currentBean.className + "();");
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createSaveOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * save one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".save" + CHAR_34 + ")");
			writeLine("public void save" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".save" + currentBean.className + "(this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId(), " + bean.detailViewObjectName + ".getSelected" + currentBean.className + "().getForm());");
			writeLine("load" + currentBean.className + "();");
			writeLine("}");
			skipLine();
		}
	}


	private void createCreateOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * create one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void create" + currentBean.className + "() {");

			for (ViewProperty property : currentBean.formBean.properties) {
				if (property.comboBoxBean != null && property.visibility.isDetailVisible()) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
				}
			}
			writeLine("try {");
			writeLine(bean.detailViewObjectName + ".setSelected" + currentBean.className + "(this." + this.bean.serviceObjectName + ".create" + currentBean.className + "(this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId()));");
			writeLine("} catch (OperationDeniedException e) {");
			writeLine("displayError(e.getMessage());");
			writeLine("}");
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createCreateOneToMany() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;

			writeLine("/**");
			writeLine(" * create one to many " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void create" + currentBean.className + "() {");

			for (ViewProperty property : oneToMany.formBean.properties) {
				if (property.comboBoxBean != null && property.visibility.isDetailVisible()) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
				}
			}

			writeLine("try {");
			writeLine(bean.detailViewObjectName + ".setSelected" + currentBean.className + "(this." + currentBean.serviceObjectName + ".create());");
			writeLine("} catch (OperationDeniedException e) {");
			writeLine("displayError(e.getMessage());");
			writeLine("}");
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
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".save" + CHAR_34 + ")");
			writeLine("public void save" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".save" + currentBean.className + "(this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId(), " + bean.detailViewObjectName + ".getSelected" + currentBean.className + "().getForm());");
			writeLine("load" + currentBean.className + "List();");
			writeLine("}");
			skipLine();			
		}
	}
	
	
	private void createSaveOneToMany() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;

			writeLine("/**");
			writeLine(" * save one to many " + currentBean.objectName);
			writeLine(" */");
			if (currentBean.detailMode.equals(DetailMode.MODAL)) {
				writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".save" + CHAR_34 + ")");
				writeLine("public void save" + currentBean.className + "() {");
				writeLine(currentBean.serviceObjectName + ".saveFrom" + bean.className + "(this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId(), " + bean.detailViewObjectName + ".getSelected" + currentBean.className + "().getForm());");
				writeLine("refresh" + currentBean.className + "List();");
				writeLine("}");
				skipLine();
			} else {
				writeLine("public void save" + currentBean.className + "() {");
				writeLine("executeAjaxMethod(" + CHAR_34 + currentBean.className + ".save" + CHAR_34 + ", new AjaxMethodTemplate() {");
				writeLine("@Override");
				writeLine("public Object execute() {");
				writeLine("return " + currentBean.serviceObjectName + ".saveFrom" + bean.className + "( " + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId(), " + bean.detailViewObjectName + ".getSelected" + currentBean.className + "().getForm());");
				writeLine("}");
				writeLine("@Override");
				writeLine("public void redirectOnComplete(Object result) {");
				writeLine("redirect(" + CHAR_34 + "/sections/" + currentBean.myPackage.name + "/" + currentBean.className.toLowerCase() + "/" + currentBean.className + "Details.jsf?id=" + CHAR_34 + " + result);");
				writeLine("}");
				writeLine("});");
				writeLine("}");
			}
		}
	}
	
	
	private void createEditOneToMany() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;

			writeLine("/**");
			writeLine(" * edit one to many " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void edit" + currentBean.className + "(Long id) {");
			
			for (ViewProperty property : oneToMany.formBean.properties) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
				}
			}
			
			writeLine(bean.detailViewObjectName + ".setSelected" + currentBean.className + "(" + currentBean.serviceObjectName + ".load(id));");
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createUpdateOneToMany() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;

			writeLine("/**");
			writeLine(" * update one to many " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".update" + CHAR_34 + ")");
			writeLine("public void update" + currentBean.className + "() {");
			writeLine(currentBean.serviceObjectName + ".update(" + bean.detailViewObjectName + ".getSelected" + currentBean.className + "().getId(), " + bean.detailViewObjectName + ".getSelected" + currentBean.className + "().getForm());");
			writeLine("refresh" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}

	
	private void createEditOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * edit one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void edit" + currentBean.className + "(Long id) {");
			
			for (ViewProperty property : currentBean.formBean.properties) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
				}
			}
			
			writeLine(bean.detailViewObjectName + ".setSelected" + currentBean.className + "(" + this.bean.serviceObjectName + ".load" + currentBean.className + "(id, this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId()));");
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
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".update" + CHAR_34 + ")");
			writeLine("public void update" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".update" + currentBean.className + "(" + bean.detailViewObjectName + ".getSelected" + currentBean.className + "().getId(), " + bean.detailViewObjectName + ".getSelected" + currentBean.className + "().getForm());");
			writeLine("load" + currentBean.className + "List();");
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
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".delete" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".delete" + currentBean.className + "(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId());");
			writeLine("load" + currentBean.className + "();");
			writeLine("}");
			skipLine();
		}
	}


	private void createDeleteOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".delete" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "(Long id) {");
			writeLine(this.bean.serviceObjectName + ".delete" + currentBean.className + "(id);");
			writeLine("load" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}
	
	private void createDeleteOneToMany() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;

			writeLine("/**");
			writeLine(" * delete one to many " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".delete" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "(Long id) {");
			writeLine(currentBean.serviceObjectName + ".delete(id);");
			writeLine("refresh" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createDeleteOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
		
			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".deleteList" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "List() {");
			writeLine("List<Long> ids = new ArrayList<>();");
			writeLine("for (" + currentBean.basicViewBean.className + " " + currentBean.objectName + ":" + bean.detailViewObjectName + ".get" + currentBean.className + "List()) {");
			writeLine("if (" + currentBean.objectName + ".getSelected()) {");
			writeLine("ids.add(" + currentBean.objectName + ".getId());");
			writeLine("}");
			writeLine("}");
			writeLine(this.bean.serviceObjectName + ".delete" + currentBean.className + "List(ids);");
			writeLine("load" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}
	
	private void createDeleteOneToManyList() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
		
			writeLine("/**");
			writeLine(" * delete one to many " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".deleteList" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "List() {");
			writeLine("List<Long> ids = new ArrayList<>();");
			writeLine("for (" + currentBean.basicViewBean.className + " " + currentBean.objectName + ":" + bean.detailViewObjectName + ".get" + currentBean.className + "ScrollView().getElements()) {");
			writeLine("if (" + currentBean.objectName + ".getSelected()) {");
			writeLine("ids.add(" + currentBean.objectName + ".getId());");
			writeLine("}");
			writeLine("}");
			writeLine(currentBean.serviceObjectName + ".deleteList(ids);");
			writeLine("refresh" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}

	
	private void createResetFlters() {
		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * reset one to many component " + currentBean.basicViewBean.filterClassName + " datatable filter");
			writeLine(" */");
			writeLine("public void reset" + currentBean.basicViewBean.filterClassName + "() {");
			writeLine(this.bean.detailViewObjectName + ".set" + currentBean.basicViewBean.filterClassName + "(new " + currentBean.basicViewBean.filterClassName + "());");
			writeLine("}");
			skipLine();
		}
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			
			writeLine("/**");
			writeLine(" * reset one to many " + currentBean.basicViewBean.filterClassName + " datatable filter and sorting");
			writeLine(" */");
			writeLine("public void reset" + currentBean.className + "List() {");
			writeLine("this." + this.bean.detailViewObjectName + ".set" + currentBean.className + "ScrollForm(new ScrollForm<>());");
			writeLine("this." + this.bean.detailViewObjectName + ".get" + currentBean.className + "ScrollForm().setFilter(new " + currentBean.basicViewBean.filterClassName + "());");
			writeLine("this." + this.bean.detailViewObjectName + ".get" + currentBean.className + "ScrollForm().setSorting(new " + currentBean.basicViewBean.sortingClassName + "());");
			writeLine("refresh" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}
}
