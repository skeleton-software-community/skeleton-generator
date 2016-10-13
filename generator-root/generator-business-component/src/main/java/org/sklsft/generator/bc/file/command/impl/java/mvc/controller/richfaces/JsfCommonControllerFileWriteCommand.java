package org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;

public class JsfCommonControllerFileWriteCommand extends JavaFileWriteCommand {

	private Project project;

	public JsfCommonControllerFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + project.model.controllerPackageName.replace(".", File.separator),
				"CommonController");

		this.project = project;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.util.List;");		
		javaImports.add("import java.util.ArrayList;");
		javaImports.add("import javax.faces.model.SelectItem;");
		javaImports.add("import org.springframework.stereotype.Component;");
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import org.springframework.context.annotation.Scope;");
		javaImports.add("import org.springframework.web.context.WebApplicationContext;");
		
		javaImports.add("import " + project.model.mvcModelPackageName + ".CommonView;");

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					javaImports.add("import " + bean.myPackage.serviceInterfacePackageName + "." + bean.serviceInterfaceName + ";");
				}
			}
		}

	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.project.model.controllerPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated common controller class file");
		writeLine(" * <br/>used for loading select items");
		writeLine(" * <br/>write modifications between specific code marks");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Component");
		writeLine("@Scope(value=WebApplicationContext.SCOPE_REQUEST)");
		writeLine("public class CommonController {");
        skipLine();        
        writeLine("/*");
		writeLine(" * the view handled by the controller");
		writeLine(" */");
		writeLine("@Autowired");
		writeLine("private CommonView commonView;");
		skipLine();

		writeLine("/*");
		writeLine(" * the services used by the controller");
		writeLine(" */");

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("@Autowired");
					writeLine("private " + bean.serviceInterfaceName + " " + bean.serviceObjectName + ";");
				}
			}
		}
		skipLine();


		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("/**");
					writeLine(" * load combobox items for " + bean.className);
					writeLine(" */");
					writeLine("public void load" + bean.className + "Options() {");
					writeLine("List<SelectItem> result = new ArrayList<>();");
					writeLine("result.add(new SelectItem(null," + CHAR_34 + CHAR_34 + "));");
					writeLine("List<" + bean.properties.get(1).beanDataType + "> options = this." + bean.serviceObjectName + ".getOptions();");
					writeLine("if (options != null){");
					writeLine("for (" + bean.properties.get(1).beanDataType + " option:options){");
					writeLine("result.add(new SelectItem(option));");
					writeLine("}");
					writeLine("}");
					writeLine("this.commonView.set" + bean.className + "Options(result);");
					writeLine("}");
					skipLine();
				}
			}
		}


		this.writeNotOverridableContent();

		writeLine("}");
	}
}
