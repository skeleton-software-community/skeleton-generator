package org.sklsft.generator.skeletons.jsf.commands.controller;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

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
		javaImports.add("import org.sklsft.commons.api.model.SelectItem;");
		javaImports.add("import org.springframework.stereotype.Component;");
		javaImports.add("import javax.inject.Inject;");
		javaImports.add("import org.springframework.context.annotation.Scope;");
		javaImports.add("import org.springframework.web.context.WebApplicationContext;");
		
		javaImports.add("import " + project.model.mvcModelPackageName + ".CommonView;");

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (bean.selectable) {
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
		writeLine("@Inject");
		writeLine("private CommonView commonView;");
		skipLine();

		writeLine("/*");
		writeLine(" * the services used by the controller");
		writeLine(" */");

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.selectable) {
					writeLine("@Inject");
					writeLine("private " + bean.serviceInterfaceName + " " + bean.serviceObjectName + ";");
				}
			}
		}
		skipLine();


		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (bean.selectable) {
					if (bean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
						writeLine("/**");
						writeLine(" * load options for " + bean.className);
						writeLine(" */");
						writeLine("public void load" + bean.className + "Options() {");
						writeLine("List<SelectItem> options = this." + bean.serviceObjectName + ".getOptions();");
						writeLine("this.commonView.set" + bean.className + "Options(options);");
						writeLine("}");
						skipLine();
					} else {
						writeLine("/**");
						writeLine(" * search options for " + bean.className);
						writeLine(" */");
						writeLine("public List<String> search" + bean.className + "Options(String arg) {");
						writeLine("List<SelectItem> options = this." + bean.serviceObjectName + ".searchOptions(arg);");
						writeLine("List<String> result = new ArrayList<>(options.size());");
						writeLine("for (SelectItem option : options) {");
						writeLine("result.add(option.getKey());");
						writeLine("}");
						writeLine("return result;");
						writeLine("}");
						skipLine();
					}
				}
			}
		}


		this.writeNotOverridableContent();

		writeLine("}");
	}
}
