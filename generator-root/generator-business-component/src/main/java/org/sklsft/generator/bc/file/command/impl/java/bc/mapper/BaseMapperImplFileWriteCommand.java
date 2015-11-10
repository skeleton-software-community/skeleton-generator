package org.sklsft.generator.bc.file.command.impl.java.bc.mapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;

public class BaseMapperImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	private Set<String> daoSet;

	/*
	 * constructor
	 */
	public BaseMapperImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component\\src\\main\\java\\"
				+ bean.myPackage.baseMapperImplPackageName.replace(".", "\\"), bean.baseMapperClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		this.daoSet = new HashSet<>();

		javaImports.add("import org.sklsft.commons.mapper.impl.BasicMapperImpl;");
		javaImports.add("import org.sklsft.commons.api.exception.repository.ObjectNotFoundException;");
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null) {
				boolean test = this.daoSet.add(property.referenceBean.daoObjectName);
				if (test) {
					javaImports.add("import " + property.referenceBean.myPackage.DAOInterfacePackageName + "." + property.referenceBean.daoInterfaceName + ";");
				}
			}
		}
	}
		

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseMapperImplPackageName + ";");
		skipLine();
		
		writeImports();
		skipLine();
		
		writeLine("/**");
		writeLine(" * auto generated base mapper class file");
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		write("public class " + this.bean.baseMapperClassName);	
		writeLine(" extends BasicMapperImpl<" + bean.viewClassName + ", " + bean.className + "> {");
		skipLine();
		
		writeLine("public " + this.bean.baseMapperClassName + "() {");
		writeLine("super(" + this.bean.viewClassName + ".class, " + this.bean.className + ".class);");
		writeLine("}");
		skipLine();

		writeLine("/*");
		writeLine(" * properties");
		writeLine(" */");
		
		this.daoSet = new HashSet<>();

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null) {
				boolean test = this.daoSet.add(property.referenceBean.daoClassName);
				if (test) {
					writeLine("@Autowired");
					writeLine("protected " + property.referenceBean.daoInterfaceName + " " + property.referenceBean.daoObjectName + ";");

				}
			}
		}

		skipLine();

		createMapping();

		writeLine("}");

	}

	private void createMapping() {
		writeLine("/**");
		writeLine(" * mapping view from object");
		writeLine(" */");
		writeLine("@Override");
		writeLine("public " + this.bean.viewClassName + " mapFrom(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ", " + this.bean.className + " "
				+ this.bean.objectName + ") {");
		
		writeLine(this.bean.viewObjectName + " = super.mapFrom(" + this.bean.viewObjectName + ", " + this.bean.objectName + ");");
		writeLine(this.bean.viewObjectName + ".setSelected(false);");

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null) {
				List<Property> findPropertyList = property.referenceBean.getFindProperties();
				if (property.nullable) {
					writeLine("if (" + this.bean.objectName + "." + property.getterName + "() != null) {");
					for (Property findProperty : findPropertyList) {
						writeLine(this.bean.viewObjectName + "." + property.setterName + findProperty.capName + "(" + this.bean.objectName + "." + property.fetchName + "." + findProperty.fetchName
								+ ");");
					}
					writeLine("}");

				} else {
					for (Property findProperty : findPropertyList) {
						writeLine(this.bean.viewObjectName + "." + property.setterName + findProperty.capName + "(" + this.bean.objectName + "." + property.getterName + "()." + findProperty.fetchName
								+ ");");
					}
				}
			}
		}

		writeLine("return " + this.bean.viewObjectName + ";");

		writeLine("}");
		skipLine();

		writeLine("/**");
		writeLine(" * mapping view to object");
		writeLine(" */");
		writeLine("@Override");
		writeLine("public " + this.bean.className + " mapTo(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ", " + this.bean.className + " "
				+ this.bean.objectName + ") {");
		
		writeLine(this.bean.objectName + " = super.mapTo(" + this.bean.viewObjectName + ", " + this.bean.objectName + ");");

		for (Property property : this.bean.properties) {
			if (property.referenceBean == null) {
				writeLine(this.bean.objectName + "." + property.setterName + "(" + this.bean.viewObjectName + "." + property.fetchName + ");");
			} else {
				List<Property> findPropertyList = property.referenceBean.getFindProperties();
				writeLine(this.bean.objectName + "." + property.setterName + "(" + property.referenceBean.daoObjectName + ".find" + property.referenceBean.className + "(");
				writeLine(this.bean.viewObjectName + "." + property.getterName + findPropertyList.get(0).capName + "()");
				for (int j = 1; j < findPropertyList.size(); j++) {
					writeLine("," + this.bean.viewObjectName + "." + property.getterName + findPropertyList.get(j).capName + "()");
				}

				writeLine("));");
			}
		}

		writeLine("return " + this.bean.objectName + ";");
		writeLine("}");
		skipLine();
	}
}
