package org.sklsft.generator.skeletons.jsf.commands.controller;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DetailMode;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class JsfBaseListControllerFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public JsfBaseListControllerFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseListControllerPackageName.replace(".", File.separator), bean.baseListControllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.ArrayList;");
		javaImports.add("import javax.inject.Inject;");
		javaImports.add("import org.sklsft.commons.mvc.ajax.AjaxMethodTemplate;");
		javaImports.add("import org.sklsft.commons.mvc.annotations.AjaxMethod;");
		javaImports.add("import org.sklsft.commons.api.exception.rights.OperationDeniedException;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollForm;");
		
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".CommonController;");
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".BaseController;");
		
		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		javaImports.add("import " + this.bean.myPackage.listViewPackageName + "." + this.bean.listViewClassName + ";");
		javaImports.add("import " + this.bean.myPackage.filtersPackageName + "." + this.bean.basicViewBean.filterClassName + ";");
		javaImports.add("import " + this.bean.myPackage.sortingsPackageName + "." + this.bean.basicViewBean.sortingClassName + ";");
		javaImports.add("import " + this.bean.myPackage.basicViewsPackageName + "." + this.bean.basicViewBean.className + ";");
		javaImports.add("import " + this.bean.myPackage.fullViewsPackageName + "." + this.bean.fullViewBean.className + ";");
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseListControllerPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated base list controller class file");
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.baseListControllerClassName + " extends BaseController {");
		skipLine();

		writeLine("/*");
		writeLine(" * services injected by spring");
		writeLine(" */");
		writeLine("@Inject");
		writeLine("protected " + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ";");
		writeLine("@Inject");
		writeLine("protected CommonController commonController;");
		skipLine();

		writeLine("/*");
		writeLine(" * view");
		writeLine(" */");
		writeLine("@Inject");
		writeLine("protected " + this.bean.listViewClassName + " " + this.bean.listViewObjectName + ";");
		skipLine();

		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");
		writeLine("public " + this.bean.listViewClassName + " get" + this.bean.listViewClassName + "() {");
		writeLine("return " + this.bean.listViewObjectName + ";");
		writeLine("}");
		writeLine("public void set" + this.bean.listViewClassName + "(" + this.bean.listViewClassName + " " + this.bean.listViewObjectName + ") {");
		writeLine("this." + this.bean.listViewObjectName + " = " + this.bean.listViewObjectName + ";");
		writeLine("}");
		skipLine();


		createLoad();
		createCreateObject();
		createSaveObject();
		createEditObject();
		createUpdateObject();
		createDeleteObject();
		createDeleteObjectList();
		createResetFlters();

		writeLine("}");

	}
	

	private void createLoad() {
		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("public void load() {");
		writeLine("this.reset();");
		writeLine("}");
		skipLine();
		
		writeLine("/**");
		writeLine(" * refresh object list");
		writeLine(" */");
		writeLine("public void refresh() {");
		writeLine(bean.listViewObjectName + ".setScrollView(" + bean.serviceObjectName + ".scroll(" + bean.listViewObjectName + ".getScrollForm()));");
		writeLine(bean.listViewObjectName + ".getScrollForm().setPage(" + bean.listViewObjectName + ".getScrollView().getCurrentPage());");
		writeLine("}");
		skipLine();
	}


	private void createCreateObject() {
		writeLine("/**");
		writeLine(" * create object");
		writeLine(" */");
		writeLine("public void create" + this.bean.className + "() {");

		for (ViewProperty property : this.bean.formBean.properties) {
			if (property.comboBoxBean != null && property.visibility.isDetailVisible()) {
				writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
			}
		}

		writeLine("try {");
		writeLine("this." + this.bean.listViewObjectName + ".setSelected" + this.bean.className + "(this." + this.bean.serviceObjectName + ".create());");
		writeLine("} catch (OperationDeniedException e) {");
		writeLine("displayError(e.getMessage());");
		writeLine("}");

		writeLine("}");
		skipLine();

	}


	private void createSaveObject() {
		writeLine("/**");
		writeLine(" * save object");
		writeLine(" */");
		if (bean.detailMode.equals(DetailMode.MODAL)) {
			writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".save" + CHAR_34 + ")");
			writeLine("public void save() {");
			writeLine(this.bean.serviceObjectName + ".save(this." + this.bean.listViewObjectName + ".getSelected" + this.bean.className + "().getForm());");
			writeLine("this.refresh();");
			writeLine("}");
			skipLine();
		} else {
			writeLine("public void save() {");
			writeLine("executeAjaxMethod(" + CHAR_34 + bean.className + ".save" + CHAR_34 + ", new AjaxMethodTemplate() {");
			writeLine("@Override");
			writeLine("public Object execute() {");
			writeLine("return " + this.bean.serviceObjectName + ".save(" + this.bean.listViewObjectName + ".getSelected" + this.bean.className + "().getForm());");
			writeLine("}");
			writeLine("@Override");
			writeLine("public void redirectOnComplete(Object result) {");
			writeLine("redirect(" + CHAR_34 + "/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + bean.className + "Details.jsf?id=" + CHAR_34 + " + result);");
			writeLine("}");
			writeLine("});");
			writeLine("}");
		}
	}
	
	
	private void createEditObject() {		

		writeLine("/**");
		writeLine(" * edit object");
		writeLine(" */");
		writeLine("public void edit" + bean.className + "(Long id) {");
		
		for (ViewProperty property : bean.formBean.properties) {
			if (property.comboBoxBean != null && property.visibility.isDetailVisible()) {
				writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
			}
		}
		
		writeLine(bean.listViewObjectName + ".setSelected" + bean.className + "(" + this.bean.serviceObjectName + ".load(id));");
		writeLine("}");
		skipLine();
	}
	
	
	private void createUpdateObject() {
		writeLine("/**");
		writeLine(" * update object");
		writeLine(" */");
		writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".update" + CHAR_34 + ")");
		writeLine("public void update() {");
		writeLine(this.bean.serviceObjectName + ".update(this." + this.bean.listViewObjectName + ".getSelected" + this.bean.className + "().getId(), this." + this.bean.listViewObjectName + ".getSelected" + this.bean.className + "().getForm());");
		writeLine("this.refresh();");
		writeLine("}");
		skipLine();
	}
	
	
	private void createDeleteObject() {
		writeLine("/**");
		writeLine(" * delete object");
		writeLine(" */");
		writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".delete" + CHAR_34 + ")");
		writeLine("public void delete(Long id) {");
		writeLine(this.bean.serviceObjectName + ".delete(id);");
		writeLine("this.refresh();");
		writeLine("}");
		skipLine();
	}
	
	
	private void createDeleteObjectList() {
		writeLine("/**");
		writeLine(" * delete object list");
		writeLine(" */");
		writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".deleteList" + CHAR_34 + ")");
		writeLine("public void deleteList() {");
		writeLine("List<Long> ids = new ArrayList<>();");
		writeLine("for (" + bean.basicViewBean.className + " " + bean.objectName + ":" + bean.listViewObjectName + ".getScrollView().getElements()) {");
		writeLine("if (" + bean.objectName + ".getSelected()) {");
		writeLine("ids.add(" + bean.objectName + ".getId());");
		writeLine("}");
		writeLine("}");
		writeLine(this.bean.serviceObjectName + ".deleteList(ids);");
		writeLine("this.refresh();");
		writeLine("}");
		skipLine();
	}

	
	private void createResetFlters() {
		
		writeLine("/**");
		writeLine(" * reset filters and sortings");
		writeLine(" */");
		writeLine("public void reset() {");
		writeLine("this." + this.bean.listViewObjectName + ".setScrollForm(new ScrollForm<>());");
		writeLine("this." + this.bean.listViewObjectName + ".getScrollForm().setFilter(new " + bean.basicViewBean.filterClassName + "());");
		writeLine("this." + this.bean.listViewObjectName + ".getScrollForm().setSorting(new " + bean.basicViewBean.sortingClassName + "());");
		writeLine("refresh();");
		writeLine("}");
		skipLine();
	}
}
