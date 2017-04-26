package org.sklsft.generator.skeletons.core.commands.bc.mapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class BaseBasicViewMapperFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	private Set<String> daoSet;

	/*
	 * constructor
	 */
	public BaseBasicViewMapperFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseBasicViewMapperPackageName.replace(".", File.separator), bean.basicViewBean.baseMapperClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		this.daoSet = new HashSet<>();

		javaImports.add("import org.sklsft.commons.mapper.impl.BasicMapperImpl;");
		javaImports.add("import org.sklsft.commons.api.exception.repository.ObjectNotFoundException;");
		javaImports.add("import javax.inject.Inject;");
		javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		javaImports.add("import " + this.bean.myPackage.basicViewsPackageName + "." + this.bean.basicViewBean.className + ";");
		if (this.bean.isComponent) {
			javaImports.add("import " + this.bean.parentBean.myPackage.rightsManagerImplPackageName + "." + this.bean.parentBean.rightsManagerClassName + ";");
			javaImports.add("import " + this.bean.parentBean.myPackage.stateManagerImplPackageName + "." + this.bean.parentBean.stateManagerClassName + ";");
		} 
		else {
			javaImports.add("import " + this.bean.myPackage.rightsManagerImplPackageName + "." + this.bean.rightsManagerClassName + ";");
			javaImports.add("import " + this.bean.myPackage.stateManagerImplPackageName + "." + this.bean.stateManagerClassName + ";");
		}


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

		writeLine("package " + this.bean.myPackage.baseBasicViewMapperPackageName + ";");
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
		
		if (this.bean.isComponent) {
			writeLine("@Inject");
			writeLine("protected " + this.bean.parentBean.rightsManagerClassName + " " + this.bean.parentBean.rightsManagerObjectName + ";");
			
			writeLine("@Inject");
			writeLine("protected " + this.bean.parentBean.stateManagerClassName + " " + this.bean.parentBean.stateManagerObjectName + ";");
		} 
		else {
			writeLine("@Inject");
			writeLine("protected " + this.bean.rightsManagerClassName + " " + this.bean.rightsManagerObjectName + ";");
			
			writeLine("@Inject");
			writeLine("protected " + this.bean.stateManagerClassName + " " + this.bean.stateManagerObjectName + ";");
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
		writeLine("public " + this.bean.basicViewBean.className + " mapFrom(" + this.bean.basicViewBean.className + " " + this.bean.basicViewBean.objectName +
				", " + this.bean.className + " " + this.bean.objectName + ") {");
		
		writeLine(this.bean.basicViewBean.objectName + " = super.mapFrom(" + this.bean.basicViewBean.objectName + ", "
				+ this.bean.objectName + ");");
		writeLine(this.bean.basicViewBean.objectName + ".setSelected(false);");
		if (this.bean.isComponent) {
			writeLine(this.bean.basicViewBean.objectName + ".setCanDelete(" 
					+ this.bean.parentBean.rightsManagerObjectName + ".canDelete(" + this.bean.objectName + ".get" + this.bean.parentBean.className+ "())" 
					+ " && "
					+ this.bean.parentBean.stateManagerObjectName + ".canDelete(" + this.bean.objectName + ".get" + this.bean.parentBean.className + "())" 										
					+ ");");
		} 
		else {
			writeLine(this.bean.basicViewBean.objectName + ".setCanDelete(" 
					+ this.bean.rightsManagerObjectName + ".canDelete(" + this.bean.objectName + ")" 
					+ " && "
					+ this.bean.stateManagerObjectName + ".canDelete(" + this.bean.objectName + ")" 										
					+ ");");
		}
		


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
		
		List<ViewProperty> referencePropertyList = property.referenceBean.referenceViewProperties;
		if (property.nullable) {
			writeLine("if (" + this.bean.objectName + "." + property.getterName + "() != null) {");
			for (ViewProperty viewProperty : referencePropertyList) {
				writeLine(this.bean.basicViewBean.objectName + "." + property.setterName + viewProperty.capName + "(" + this.bean.objectName + "." + property.getterName + "()." + viewProperty.mappingPath
						+ ");");
			}
			writeLine("}");

		} else {
			for (ViewProperty ViewProperty : referencePropertyList) {
				writeLine(this.bean.basicViewBean.objectName + "." + property.setterName + ViewProperty.capName + "(" + this.bean.objectName + "." + property.getterName + "()." + ViewProperty.mappingPath
						+ ");");
			}
		}
	}
	
	private void writeMapEmbeddedToView(Property property) {
		
		Bean embeddedBean = property.referenceBean;
		
		for (Property embeddedProperty : embeddedBean.properties) {	
			if (embeddedProperty.visibility.isListVisible()) {
				if (embeddedProperty.referenceBean != null) {
					List<ViewProperty> referencePropertyList = embeddedProperty.referenceBean.referenceViewProperties;
					if (embeddedProperty.nullable) {
						writeLine("if (" + this.bean.objectName + "." + property.getterName + "()." + embeddedProperty.getterName + "() != null) {");
						for (ViewProperty viewProperty : referencePropertyList) {
							writeLine(this.bean.basicViewBean.objectName + "." + embeddedProperty.setterName + viewProperty.capName + "(" + this.bean.objectName + "." + property.getterName + "()." + embeddedProperty.getterName + "()." + viewProperty.mappingPath + ");");
						}
						writeLine("}");
	
					} else {
						for (ViewProperty viewProperty : referencePropertyList) {
							writeLine(this.bean.basicViewBean.objectName + "." + embeddedProperty.setterName + viewProperty.capName + "(" + this.bean.objectName + "." + property.getterName + "()." + embeddedProperty.getterName + "()." + viewProperty.mappingPath + ");");
						}
					}
				} else {
					writeLine(this.bean.basicViewBean.objectName + "." + embeddedProperty.setterName + "(" + this.bean.objectName + "." + property.getterName + "()." + embeddedProperty.getterName + "());");
				}
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
		boolean start = true;
		write(this.bean.objectName + "." + property.setterName + "(" + property.referenceBean.daoObjectName + ".find(");
		for (ViewProperty refProperty:property.referenceBean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(this.bean.basicViewBean.objectName + "." + property.getterName + refProperty.capName + "()");
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
		
		for (Property embeddedProperty : embeddedBean.properties) {
			if (embeddedProperty.visibility.isListVisible()) {
				if (embeddedProperty.referenceBean != null) {
					
					List<ViewProperty> referencePropertyList = embeddedProperty.referenceBean.referenceViewProperties;
					write(embeddedBean.objectName + "." + embeddedProperty.setterName + "(" + embeddedProperty.referenceBean.daoObjectName + ".find(");
					boolean start = true;
					for (ViewProperty refProperty : referencePropertyList) {
						if (start) start = false; else write(", ");
						write(this.bean.basicViewBean.objectName + "." + embeddedProperty.getterName + refProperty.capName + "()");
					}
					writeLine("));");
					
				} else {
					writeLine(embeddedBean.objectName + "." + embeddedProperty.setterName + "(" + bean.basicViewBean.objectName + "." + embeddedProperty.getterName + "());");
				}
			}
		}
	}
}
