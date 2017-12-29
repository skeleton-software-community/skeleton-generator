package org.sklsft.generator.skeletons.core.commands.api.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class OrderingFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public OrderingFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-api" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.sortingsPackageName.replace(".", File.separator), bean.basicViewBean.sortingClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.math.BigDecimal;");
		javaImports.add("import java.io.Serializable;");
		javaImports.add("import org.sklsft.commons.api.model.OrderType;");
	}

	@Override
	protected void writeContent() throws IOException {
		writeLine("package " + bean.myPackage.sortingsPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated ordering class file");
		writeLine(" * <br/>basic representation of filter used along with jsf datatable");
		writeLine(" * <br/>write modifications between specific code marks");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.basicViewBean.sortingClassName + " implements Serializable {");
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
		
		for (ViewProperty property:this.bean.basicViewBean.properties) {
			writeLine("private OrderType " + property.name + "OrderType;");
		}
		skipLine();

	}

	private void createGettersAndSetters() {
		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");

		for (ViewProperty property:this.bean.basicViewBean.properties) {
			writeLine("public OrderType get" + property.capName + "OrderType() {");
			writeLine("return this." + property.name + "OrderType;");
			writeLine("}");
			skipLine();

			writeLine("public void set" + property.capName + "OrderType(OrderType " + property.name + "OrderType) {");
			writeLine("this." + property.name + "OrderType = " + property.name + "OrderType;");
			writeLine("}");
			skipLine();
		}
		skipLine();
	}
}
