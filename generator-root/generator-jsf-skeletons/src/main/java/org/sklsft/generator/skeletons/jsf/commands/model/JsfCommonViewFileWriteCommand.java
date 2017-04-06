package org.sklsft.generator.skeletons.jsf.commands.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class JsfCommonViewFileWriteCommand extends JavaFileWriteCommand {

	private Project project;

	public JsfCommonViewFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + project.model.mvcModelPackageName.replace(".", File.separator),
				"CommonView");

		this.project = project;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.io.Serializable;");
		javaImports.add("import java.util.List;");		
		javaImports.add("import java.util.ArrayList;");
		javaImports.add("import javax.faces.model.SelectItem;");
		javaImports.add("import org.springframework.stereotype.Component;");
		javaImports.add("import javax.inject.Inject;");
		javaImports.add("import org.springframework.context.annotation.Scope;");
		javaImports.add("import org.springframework.web.context.WebApplicationContext;");
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.project.model.mvcModelPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated common view class file");
		writeLine(" * <br/>used for select items");
		writeLine(" * <br/>write modifications between specific code marks");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Component");
		writeLine("@Scope(value=WebApplicationContext.SCOPE_SESSION)");
		writeLine("public class CommonView implements Serializable {");
        skipLine();

        writeLine("private static final long serialVersionUID = 1L;");
        skipLine();

		writeLine("/*");
		writeLine(" * properties");
		writeLine(" */");
		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("private List<SelectItem>" + bean.objectName + "Options;");
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
					writeLine("public List<SelectItem> get" + bean.className + "Options() {");
					writeLine("return " + bean.objectName + "Options;");
					writeLine("}");
					writeLine("public void set" + bean.className + "Options(List<SelectItem> " + bean.objectName + "Options) {");
					writeLine("this." + bean.objectName + "Options = " + bean.objectName + "Options;");
					writeLine("}");
					skipLine();
				}
			}
		}

		this.writeNotOverridableContent();

		writeLine("}");
	}
}
