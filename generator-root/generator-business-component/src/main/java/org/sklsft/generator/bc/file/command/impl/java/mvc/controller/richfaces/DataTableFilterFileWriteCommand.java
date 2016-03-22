package org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;

public class DataTableFilterFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public DataTableFilterFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.filterPackageName.replace(".", File.separator), bean.basicViewBean.filterClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.io.Serializable;");
	}

	@Override
	protected void writeContent() throws IOException {
		writeLine("package " + bean.myPackage.filterPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated filter class file");
		writeLine(" * <br/>basic representation of filter used along with jsf datatable");
		writeLine(" * <br/>write modifications between specific code marks");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.basicViewBean.filterClassName + " implements Serializable {");
		skipLine();

		writeLine("private static final long serialVersionUID = 1L;");
		skipLine();

		createProperties();
		createGettersAndSetters();
		writeNotOverridableContent();

		writeLine("}");

	}

	private void createProperties() {
		writeLine("/*");
		writeLine(" * properties");
		writeLine(" */");
		
		for (Property property:this.bean.basicViewBean.properties) {
			writeLine("private String " + property.name + ";");
		}
		skipLine();

	}

	private void createGettersAndSetters() {
		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");

		for (Property property:this.bean.basicViewBean.properties) {
			writeLine("public String get" + property.capName + "() {");
			writeLine("return this." + property.name + ";");
			writeLine("}");
			skipLine();

			writeLine("public void set" + property.capName + "(String " + property.name + ") {");
			writeLine("this." + property.name + " = " + property.name + ";");
			writeLine("}");
			skipLine();
		}
		skipLine();
	}
}
