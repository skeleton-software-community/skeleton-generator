package org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.metadata.DetailMode;
import org.sklsft.generator.model.metadata.Visibility;

public abstract class AbstractBaseJsfListControllerFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public AbstractBaseJsfListControllerFileWriteCommand(Bean bean) {
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
		
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".CommonController;");
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".BaseController;");
		
		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		javaImports.add("import " + this.bean.myPackage.listViewPackageName + "." + this.bean.listViewClassName + ";");
		javaImports.add("import " + this.bean.myPackage.filtersPackageName + "." + this.bean.basicViewBean.filterClassName + ";");
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
		writeLine("this.reset" + bean.basicViewBean.filterClassName + "();");
		writeLine("this.refresh();");
		writeLine("}");
		skipLine();
		
		writeLine("/**");
		writeLine(" * refresh object list");
		writeLine(" */");
		writeLine("public void refresh() {");
		writeLine("this." + this.bean.listViewObjectName + ".set" + this.bean.className + "List(this." + this.bean.serviceObjectName + ".loadList());");
		writeLine("}");
		skipLine();
	}


	private void createCreateObject() {
		writeLine("/**");
		writeLine(" * create object");
		writeLine(" */");
		writeLine("public void create" + this.bean.className + "() {");

		for (Property property : this.bean.fullViewBean.properties) {
			if (property.comboBoxBean != null && property.editable) {
				writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
			}
		}

		writeLine("this." + this.bean.listViewObjectName + ".setSelected" + this.bean.className + "(this." + this.bean.serviceObjectName + ".create());");
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
			writeLine(this.bean.serviceObjectName + ".save(this." + this.bean.listViewObjectName + ".getSelected" + this.bean.className + "());");
			writeLine("this.refresh();");
			writeLine("}");
			skipLine();
		} else {
			writeLine("public void save() {");
			writeLine("executeAjaxMethod(" + CHAR_34 + bean.className + ".save" + CHAR_34 + ", new AjaxMethodTemplate() {");
			writeLine("@Override");
			writeLine("public Object execute() {");
			writeLine("return " + this.bean.serviceObjectName + ".save(" + this.bean.listViewObjectName + ".getSelected" + this.bean.className + "());");
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
		
		for (Property property : bean.fullViewBean.properties) {
			if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
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
		writeLine(this.bean.serviceObjectName + ".update(this." + this.bean.listViewObjectName + ".getSelected" + this.bean.className + "());");
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
		writeLine("for (" + bean.basicViewBean.className + " " + bean.objectName + ":" + bean.objectName + "ListView.get" + bean.className + "List()) {");
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
		writeLine(" * reset object datatable filter");
		writeLine(" */");
		writeLine("public void reset" + bean.basicViewBean.filterClassName + "() {");
		writeLine("this." + this.bean.listViewObjectName + ".set" + bean.basicViewBean.filterClassName + "(new " + bean.basicViewBean.filterClassName + "());");
		writeLine("}");
		skipLine();
	}
}
