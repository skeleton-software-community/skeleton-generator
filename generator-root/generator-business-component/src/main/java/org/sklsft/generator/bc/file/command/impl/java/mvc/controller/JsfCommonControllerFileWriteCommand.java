package org.sklsft.generator.bc.file.command.impl.java.mvc.controller;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;

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
		writeLine(" * <br/>used for select items");
		writeLine(" * <br/>write modifications between specific code marks");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Component");
		writeLine("@Scope(value=WebApplicationContext.SCOPE_SESSION)");
		writeLine("public class CommonController implements Serializable {");
        skipLine();

        writeLine("private static final long serialVersionUID = 1L;");
        skipLine();

		writeLine("/*");
		writeLine(" * properties injected by spring");
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


		writeLine("/*");
		writeLine(" * select items");
		writeLine(" */");
		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("private List<SelectItem>" + bean.objectName + bean.properties.get(1).capName + "List;");
				}
			}
		}
		skipLine();

		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("public List<SelectItem> get" + bean.className + bean.properties.get(1).capName + "List() {");
					writeLine("return " + bean.objectName + bean.properties.get(1).capName + "List;");
					writeLine("}");
					writeLine("public void set" + bean.className + bean.properties.get(1).capName + "List(List<SelectItem> " + bean.objectName + bean.properties.get(1).capName + "List) {");
					writeLine("this." + bean.objectName + bean.properties.get(1).capName + "List = " + bean.objectName + bean.properties.get(1).capName + "List;");
					writeLine("}");
					skipLine();
				}
			}
		}


		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("/**");
					writeLine(" * load combobox items for " + bean.className);
					writeLine(" */");
					writeLine("public void load" + bean.className + bean.properties.get(1).capName + "List() {");
					writeLine("this." + bean.objectName + bean.properties.get(1).capName + "List = new ArrayList<SelectItem>();");
					writeLine("this." + bean.objectName + bean.properties.get(1).capName + "List.add(new SelectItem(null," + (char) 34 + (char) 34 + "));");
					writeLine("List<" + bean.properties.get(1).beanDataType + "> " + bean.objectName + bean.properties.get(1).capName + "List = this." + bean.serviceObjectName + ".get"
							+ bean.className + bean.properties.get(1).capName + "List ();");
					writeLine("if (" + bean.objectName + bean.properties.get(1).capName + "List != null){");
					writeLine("for (" + bean.properties.get(1).beanDataType + " " + bean.objectName + bean.properties.get(1).capName + ":" + bean.objectName + bean.properties.get(1).capName
							+ "List){");
					writeLine("this." + bean.objectName + bean.properties.get(1).capName + "List.add(new SelectItem(" + bean.objectName + bean.properties.get(1).capName + "));");
					writeLine("}");
					writeLine("}");
					writeLine("}");
					skipLine();
				}
			}
		}


		this.writeNotOverridableContent();

		writeLine("}");
	}
}
