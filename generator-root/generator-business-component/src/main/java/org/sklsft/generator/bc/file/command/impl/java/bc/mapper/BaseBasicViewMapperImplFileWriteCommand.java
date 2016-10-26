package org.sklsft.generator.bc.file.command.impl.java.bc.mapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;

public class BaseBasicViewMapperImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	private Set<String> daoSet;

	/*
	 * constructor
	 */
	public BaseBasicViewMapperImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseMapperImplPackageName.replace(".", File.separator), bean.basicViewBean.baseMapperClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		this.daoSet = new HashSet<>();

		javaImports.add("import org.sklsft.commons.mapper.impl.BasicMapperImpl;");
		javaImports.add("import org.sklsft.commons.api.exception.repository.ObjectNotFoundException;");
		javaImports.add("import javax.inject.Inject;");
		javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.basicViewBean.className + ";");

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.visibility.isListVisible()) {
				if (!property.embedded) {
					boolean test = this.daoSet.add(property.referenceBean.daoObjectName);
					if (test) {
						javaImports.add("import " + property.referenceBean.myPackage.DAOInterfacePackageName + "." + property.referenceBean.daoInterfaceName + ";");
					}
				} else {
					javaImports.add("import " + property.referenceBean.myPackage.omPackageName + "." + property.referenceBean.className + ";");
					
					for (Property embeddedProperty:property.referenceBean.properties) {
						if (embeddedProperty.referenceBean != null && embeddedProperty.visibility.isListVisible()) {
							boolean test = this.daoSet.add(embeddedProperty.referenceBean.daoObjectName);
							if (test) {
								javaImports.add("import " + embeddedProperty.referenceBean.myPackage.DAOInterfacePackageName + "." + embeddedProperty.referenceBean.daoInterfaceName + ";");
							}
						}
					}
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
		write("public class " + this.bean.basicViewBean.baseMapperClassName);	
		writeLine(" extends BasicMapperImpl<" + bean.basicViewBean.className + ", " + bean.className + "> {");
		skipLine();
		
		writeLine("public " + this.bean.basicViewBean.baseMapperClassName + "() {");
		writeLine("super(" + this.bean.basicViewBean.className + ".class, " + this.bean.className + ".class);");
		writeLine("}");
		skipLine();

		writeLine("/*");
		writeLine(" * properties");
		writeLine(" */");
		
		this.daoSet = new HashSet<>();

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.visibility.isListVisible()) {
				if (!property.embedded) {
					boolean test = this.daoSet.add(property.referenceBean.daoObjectName);
					if (test) {
						writeLine("@Inject");
						writeLine("protected " + property.referenceBean.daoInterfaceName + " " + property.referenceBean.daoObjectName + ";");
					}
				} else {
					for (Property embeddedProperty:property.referenceBean.properties) {
						if (embeddedProperty.referenceBean != null && embeddedProperty.visibility.isListVisible()) {
							boolean test = this.daoSet.add(embeddedProperty.referenceBean.daoObjectName);
							if (test) {
								writeLine("@Inject");
								writeLine("protected " + embeddedProperty.referenceBean.daoInterfaceName + " " + embeddedProperty.referenceBean.daoObjectName + ";");
							}
						}
					}
				}
			}
		}

		skipLine();

		createMappingFrom();
		createMappingTo();

		writeLine("}");

	}

	private void createMappingFrom() {
		writeLine("/**");
		writeLine(" * mapping view from object");
		writeLine(" */");
		writeLine("@Override");
		writeLine("public " + this.bean.basicViewBean.className + " mapFrom(" + this.bean.basicViewBean.className + " " + this.bean.basicViewBean.objectName + ", " + this.bean.className + " "
				+ this.bean.objectName + ") {");
		
		writeLine(this.bean.basicViewBean.objectName + " = super.mapFrom(" + this.bean.basicViewBean.objectName + ", " + this.bean.objectName + ");");
		writeLine(this.bean.basicViewBean.objectName + ".setSelected(false);");

		for (Property property : this.bean.properties) {			
			if (property.referenceBean != null) {
				if (property.visibility.isListVisible()) {
					if (property.embedded) {
						writeMapEmbeddedToView(property);
					} else {
						writeMapReferenceToView(property);
					}					
				}
			}
		}

		writeLine("return " + this.bean.basicViewBean.objectName + ";");

		writeLine("}");
		skipLine();
	}
	
	private void writeMapReferenceToView(Property property) {
		
		List<Property> referencePropertyList = property.referenceBean.getReferenceProperties();
		if (property.nullable) {
			writeLine("if (" + this.bean.objectName + "." + property.getterName + "() != null) {");
			for (Property referenceProperty : referencePropertyList) {
				writeLine(this.bean.basicViewBean.objectName + "." + property.setterName + referenceProperty.capName + "(" + this.bean.objectName + "." + property.fetchName + "." + referenceProperty.fetchName
						+ ");");
			}
			writeLine("}");

		} else {
			for (Property findProperty : referencePropertyList) {
				writeLine(this.bean.basicViewBean.objectName + "." + property.setterName + findProperty.capName + "(" + this.bean.objectName + "." + property.getterName + "()." + findProperty.fetchName
						+ ");");
			}
		}
	}
	
	private void writeMapEmbeddedToView(Property property) {
		
		Bean embeddedBean = property.referenceBean;
		
		for (int i = 1; i < embeddedBean.properties.size();i++) {
			Property embeddedProperty = embeddedBean.properties.get(i);			
			if (embeddedProperty.referenceBean != null) {
				List<Property> referencePropertyList = embeddedProperty.referenceBean.getReferenceProperties();
				if (embeddedProperty.nullable) {
					writeLine("if (" + this.bean.objectName + "." + property.getterName + "()." + embeddedProperty.getterName + "() != null) {");
					for (Property referenceProperty : referencePropertyList) {
						writeLine(this.bean.basicViewBean.objectName + "." + embeddedProperty.setterName + referenceProperty.capName + "(" + this.bean.objectName + "." + property.fetchName + "." + embeddedProperty.fetchName + "." + referenceProperty.fetchName
								+ ");");
					}
					writeLine("}");

				} else {
					for (Property referenceProperty : referencePropertyList) {
						writeLine(this.bean.basicViewBean.objectName + "." + embeddedProperty.setterName + referenceProperty.capName + "(" + this.bean.objectName + "." + property.fetchName + "." + embeddedProperty.fetchName + "." + referenceProperty.fetchName
								+ ");");
					}
				}
			} else {
				writeLine(this.bean.basicViewBean.objectName + "." + embeddedProperty.setterName + "(" + this.bean.objectName + "." + property.fetchName + "." + embeddedProperty.fetchName + ");");
			}
		}
	}
	
	private void createMappingTo() {

		writeLine("/**");
		writeLine(" * mapping view to object");
		writeLine(" */");
		writeLine("@Override");
		writeLine("public " + this.bean.className + " mapTo(" + this.bean.basicViewBean.className + " " + this.bean.basicViewBean.objectName + ", " + this.bean.className + " "
				+ this.bean.objectName + ") {");
		
		writeLine(this.bean.objectName + " = super.mapTo(" + this.bean.basicViewBean.objectName + ", " + this.bean.objectName + ");");

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.visibility.isListVisible()) {
				if (property.embedded) {
					writeMapEmbeddedToObject(property);
				} else {
					writeMapReferenceToObject(property);
				}
			}
		}

		writeLine("return " + this.bean.objectName + ";");
		writeLine("}");
		skipLine();
	}

	

	private void writeMapReferenceToObject(Property property) {
		List<Property> referencePropertyList = property.referenceBean.getReferenceProperties();
		writeLine(this.bean.objectName + "." + property.setterName + "(" + property.referenceBean.daoObjectName + ".find(");
		writeLine(this.bean.basicViewBean.objectName + "." + property.getterName + referencePropertyList.get(0).capName + "()");
		for (int j = 1; j < referencePropertyList.size(); j++) {
			writeLine("," + this.bean.basicViewBean.objectName + "." + property.getterName + referencePropertyList.get(j).capName + "()");
		}
		writeLine("));");
	}

	private void writeMapEmbeddedToObject(Property property) {
		
		Bean embeddedBean = property.referenceBean;
		
		writeLine(embeddedBean.className + " " + embeddedBean.objectName + " = " + bean.objectName + "." + property.getterName + "();");
		writeLine("if (" + embeddedBean.objectName + " == null) {");
		writeLine(embeddedBean.objectName + " = new " + embeddedBean.className + "();");
		writeLine(bean.objectName + "." + property.setterName + "(" + embeddedBean.objectName + ");");
		writeLine("}");
		
		for (int i = 1; i < embeddedBean.properties.size(); i++) {
			Property embeddedProperty = embeddedBean.properties.get(i);
			if (embeddedProperty.visibility.isListVisible()) {
				if (embeddedProperty.referenceBean != null) {
					
					List<Property> referencePropertyList = embeddedProperty.referenceBean.getReferenceProperties();
					writeLine(embeddedBean.objectName + "." + embeddedProperty.setterName + "(" + embeddedProperty.referenceBean.daoObjectName + ".find(");
					writeLine(this.bean.basicViewBean.objectName + "." + embeddedProperty.getterName + referencePropertyList.get(0).capName + "()");
					for (int j = 1; j < referencePropertyList.size(); j++) {
						writeLine("," + this.bean.basicViewBean.objectName + "." + embeddedProperty.getterName + referencePropertyList.get(j).capName + "()");
					}
					writeLine("));");
					
				} else {
					writeLine(embeddedBean.objectName + "." + embeddedProperty.setterName + "(" + bean.basicViewBean.objectName + "." + embeddedProperty.getterName + "());");
				}
			}
		}
	}
}
