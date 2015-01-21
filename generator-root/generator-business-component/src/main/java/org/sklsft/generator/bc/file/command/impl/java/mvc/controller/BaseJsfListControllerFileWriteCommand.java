package org.sklsft.generator.bc.file.command.impl.java.mvc.controller;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.Visibility;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Property;
import org.sklsft.generator.model.om.UniqueComponent;

public class BaseJsfListControllerFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public BaseJsfListControllerFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\java\\"
				+ bean.myPackage.baseControllerPackageName.replace(".", "\\"), bean.baseListControllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import org.slf4j.Logger;");
		javaImports.add("import org.slf4j.LoggerFactory;");
		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.ArrayList;");
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import javax.faces.event.ActionEvent;");
		
		javaImports.add("import " + this.bean.myPackage.model.mvcAspectPackageName + ".AjaxMethod;");

		javaImports.add("import " + this.bean.myPackage.model.daoExceptionPackageName + ".ObjectNotFoundException;");
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".CommonController;");
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".BaseController;");
		
		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		javaImports.add("import " + this.bean.myPackage.model.stateExceptionPackageName + ".InvalidStateException;");
		javaImports.add("import " + this.bean.myPackage.listViewPackageName + "." + this.bean.listViewClassName + ";");
		javaImports.add("import " + this.bean.myPackage.filterPackageName + "." + this.bean.filterClassName + ";");
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseControllerPackageName + ";");
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
		writeLine("@Autowired");
		writeLine("protected " + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ";");
		writeLine("@Autowired");
		writeLine("protected CommonController commonController;");
		skipLine();

		writeLine("/*");
		writeLine(" * view");
		writeLine(" */");
		writeLine("@Autowired");
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
		createDeleteObject();
		createResetFlters();

		writeLine("}");

	}
	

	private void createLoad() {
		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("public void load() {");
		writeLine("this.reset" + bean.filterClassName + "();");
		writeLine("this.refresh();");
		writeLine("}");
		skipLine();
		
		writeLine("/**");
		writeLine(" * refresh object list");
		writeLine(" */");
		writeLine("public void refresh() {");
		writeLine("this." + this.bean.listViewObjectName + ".set" + this.bean.className + "List(this." + this.bean.serviceObjectName + ".load" + this.bean.className + "List());");
		writeLine("}");
		skipLine();
	}


	private void createCreateObject() {
		writeLine("/**");
		writeLine(" * create object");
		writeLine(" */");
		writeLine("public void create" + this.bean.className + "() {");

		for (Property property : this.bean.getVisibleProperties()) {
			if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
				writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
			}
		}

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;

			for (Property property : currentBean.getVisibleProperties()) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
				}
			}
		}

		writeLine("this." + this.bean.listViewObjectName + ".setNew" + this.bean.className + "(this." + this.bean.serviceObjectName + ".create" + this.bean.className + "());");
		writeLine("}");
		skipLine();

	}


	private void createSaveObject() {
		writeLine("/**");
		writeLine(" * save object");
		writeLine(" */");
		writeLine("@AjaxMethod(identifier=" + CHAR_34 + bean.className + ".save" + CHAR_34 + ")");
		writeLine("public void save" + this.bean.className + "() {");
		writeLine(this.bean.serviceObjectName + ".save" + this.bean.className + "(this." + this.bean.listViewObjectName + ".getNew" + this.bean.className + "());");
		writeLine("this.refresh();");
		writeLine("}");
		skipLine();

	}
	
	
	private void createDeleteObject() {
		writeLine("/**");
		writeLine(" * delete object");
		writeLine(" */");
		writeLine("@AjaxMethod(identifier=" + CHAR_34 + bean.className + ".delete" + CHAR_34 + ")");
		writeLine("public void delete" + this.bean.className + "(Long id) {");
		writeLine(this.bean.serviceObjectName + ".delete" + this.bean.className + "(id);");
		writeLine("this.refresh();");
		writeLine("}");
		skipLine();
	}

	
	private void createResetFlters() {
		
		writeLine("/**");
		writeLine(" * reset object datatable filter");
		writeLine(" */");
		writeLine("public void reset" + bean.filterClassName + "() {");
		writeLine("this." + this.bean.listViewObjectName + ".set" + bean.filterClassName + "(new " + bean.filterClassName + "());");
		writeLine("}");
		skipLine();
	}
}
