package org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;

public class JsfCommonControllerFileWriteCommand extends JavaFileWriteCommand {

	private Project project;

	public JsfCommonControllerFileWriteCommand(Project project) {
		super(project.workspaceFolder + "\\" + project.projectName + "-webapp\\src\\main\\java\\" + project.model.controllerPackageName.replace(".", "\\"),
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
					writeLine("List<SelectItem> options = new ArrayList<SelectItem>();");
					writeLine("options.add(new SelectItem(null," + (char) 34 + (char) 34 + "));");
					writeLine("List<" + bean.properties.get(1).beanDataType + "> " + bean.objectName + bean.properties.get(1).capName + "List = this." + bean.serviceObjectName + ".get"
							+ bean.className + bean.properties.get(1).capName + "List ();");
					writeLine("if (" + bean.objectName + bean.properties.get(1).capName + "List != null){");
					writeLine("for (" + bean.properties.get(1).beanDataType + " " + bean.objectName + bean.properties.get(1).capName + ":" + bean.objectName + bean.properties.get(1).capName
							+ "List){");
					writeLine("options.add(new SelectItem(" + bean.objectName + bean.properties.get(1).capName + "));");
					writeLine("}");
					writeLine("}");
					writeLine("this.commonView.set" + bean.className + "Options(options);");
					writeLine("}");
					skipLine();
				}
			}
		}


		this.writeNotOverridableContent();

		writeLine("}");
	}
}
