package org.sklsft.generator.skeletons.core.commands.components.mapper;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class BaseFullViewMapperFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public BaseFullViewMapperFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.componentsArtefactName + File.separator + bean.myPackage.model.javaSourcesFolder + File.separator
				+ bean.myPackage.baseFullViewMapperPackageName.replace(".", File.separator), bean.fullViewBean.baseMapperClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import org.sklsft.commons.mapper.impl.FullViewMapper;");

        javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		javaImports.add("import " + this.bean.myPackage.formsPackageName + "." + this.bean.formBean.className + ";");
		javaImports.add("import " + this.bean.myPackage.fullViewsPackageName + "." + this.bean.fullViewBean.className + ";");
		if (this.bean.isComponent) {
			javaImports.add("import " + this.bean.parentBean.myPackage.rightsManagerImplPackageName + "." + this.bean.parentBean.rightsManagerClassName + ";");
			javaImports.add("import " + this.bean.parentBean.myPackage.stateManagerImplPackageName + "." + this.bean.parentBean.stateManagerClassName + ";");
		} 
		else {
			javaImports.add("import " + this.bean.myPackage.rightsManagerImplPackageName + "." + this.bean.rightsManagerClassName + ";");
			javaImports.add("import " + this.bean.myPackage.stateManagerImplPackageName + "." + this.bean.stateManagerClassName + ";");
		}
	}
		

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseFullViewMapperPackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated mapper class file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();

        writeLine("public class " + this.bean.fullViewBean.baseMapperClassName + " extends FullViewMapper<" + this.bean.fullViewBean.className + ", " + bean.idType + ", " + this.bean.formBean.className + ", " + this.bean.className + "> {");
        skipLine();
        
        if (this.bean.isComponent) {
			writeLine("@Autowired");
			writeLine("protected " + this.bean.parentBean.rightsManagerClassName + " " + this.bean.parentBean.rightsManagerObjectName + ";");
			
			writeLine("@Autowired");
			writeLine("protected " + this.bean.parentBean.stateManagerClassName + " " + this.bean.parentBean.stateManagerObjectName + ";");
		} 
		else {
			writeLine("@Autowired");
			writeLine("protected " + this.bean.rightsManagerClassName + " " + this.bean.rightsManagerObjectName + ";");
			
			writeLine("@Autowired");
			writeLine("protected " + this.bean.stateManagerClassName + " " + this.bean.stateManagerObjectName + ";");
		}
        skipLine();

        
        writeLine("public " + this.bean.fullViewBean.baseMapperClassName + "() {");
		writeLine("super(" + this.bean.fullViewBean.className + ".class, " + this.bean.className + ".class);");
		writeLine("}");
		skipLine();
		
		writeLine("@Override");
		writeLine("public " + this.bean.fullViewBean.className + " mapFrom(" + this.bean.fullViewBean.className + " " + this.bean.fullViewBean.objectName + ", " 
				 + this.bean.className + " " + this.bean.objectName + ") {");
		writeLine(this.bean.fullViewBean.objectName + " = " + "super.mapFrom(" + this.bean.fullViewBean.objectName + ", " + this.bean.objectName + ");");
		
		if (this.bean.isComponent) {
			writeLine(this.bean.fullViewBean.objectName + ".setCanUpdate(" 
					+ this.bean.parentBean.rightsManagerObjectName +".canUpdate" + this.bean.className + "(" + this.bean.objectName + ") && "
					+ this.bean.parentBean.stateManagerObjectName +".canUpdate" + this.bean.className + "(" + this.bean.objectName + "));");
			
			writeLine(this.bean.fullViewBean.objectName + ".setCanDelete(" 
					+ this.bean.parentBean.rightsManagerObjectName + ".canDelete" + bean.className + "(" + this.bean.objectName + ")" 
					+ " && "
					+ this.bean.parentBean.stateManagerObjectName + ".canDelete" + bean.className + "(" + this.bean.objectName + "));");
		} 
		else {
			writeLine(this.bean.fullViewBean.objectName + ".setCanUpdate(" 
					+ this.bean.rightsManagerObjectName +".canUpdate(" + this.bean.objectName + ") && "
					+ this.bean.stateManagerObjectName +".canUpdate(" + this.bean.objectName + "));");
			
			writeLine(this.bean.fullViewBean.objectName + ".setCanDelete(" 
					+ this.bean.rightsManagerObjectName + ".canDelete(" + this.bean.objectName + ")" 
					+ " && "
					+ this.bean.stateManagerObjectName + ".canDelete(" + this.bean.objectName + "));");
		}		
		
		writeLine("return " + this.bean.fullViewBean.objectName + ";");
		writeLine("}");
		skipLine();
		
        writeLine("}");

	}


	

}
