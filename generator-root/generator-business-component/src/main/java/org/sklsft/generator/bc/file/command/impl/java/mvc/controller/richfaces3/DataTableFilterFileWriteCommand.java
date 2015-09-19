package org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces3;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Property;

public class DataTableFilterFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public DataTableFilterFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\java\\"
				+ bean.myPackage.filterPackageName.replace(".", "\\"), bean.filterClassName);

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
		writeLine("public class " + this.bean.filterClassName + " implements Serializable {");
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
		
		List<Property> visiblePropertyList = this.bean.getVisibleProperties();

		for (int i = 0; i < visiblePropertyList.size(); i++) {
			writeLine("private String " + visiblePropertyList.get(i).name + ";");
		}
		skipLine();

	}

	private void createGettersAndSetters() {
		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");

		List<Property> visiblePropertyList = this.bean.getVisibleProperties();

		for (int i = 0; i < visiblePropertyList.size(); i++) {
			writeLine("public String get" + visiblePropertyList.get(i).capName + "() {");
			writeLine("return this." + visiblePropertyList.get(i).name + ";");
			writeLine("}");
			skipLine();

			writeLine("public void set" + visiblePropertyList.get(i).capName + "(String " + visiblePropertyList.get(i).name + ") {");
			writeLine("this." + visiblePropertyList.get(i).name + " = " + visiblePropertyList.get(i).name + ";");
			writeLine("}");
			skipLine();
		}
		skipLine();
	}
}
