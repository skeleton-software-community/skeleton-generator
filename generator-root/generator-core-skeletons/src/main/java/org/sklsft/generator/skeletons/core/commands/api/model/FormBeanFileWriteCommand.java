package org.sklsft.generator.skeletons.core.commands.api.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;


public class FormBeanFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public FormBeanFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-api" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + bean.myPackage.formsPackageName.replace(".",File.separator), bean.formBean.className);
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import java.util.Date;");
        javaImports.add("import java.io.Serializable;");
        javaImports.add("import javax.validation.constraints.NotNull;");
	}
	
	
	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + bean.myPackage.formsPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated form bean class file");
        writeLine(" * <br/>basic representation of what is going to be considered as model in MVC patterns");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("public class " + this.bean.formBean.className + " implements Serializable {");
        skipLine();

        writeLine("private static final long serialVersionUID = 1L;");
        skipLine();

        createProperties();
        createGettersAndSetters();
        writeNotOverridableContent();
        
        writeLine("}");

    }

    private void createProperties()
    {
        writeLine("/*");
        writeLine(" * properties");
        writeLine(" */");

        for (ViewProperty property:this.bean.formBean.properties) {
        	if (!property.nullable) {
        		writeLine("@NotNull");
        	}
        	writeLine("private " + property.beanDataType + " " + property.name + ";");
        }
        skipLine();

    }

    private void createGettersAndSetters()
    {
        writeLine("/*");
        writeLine(" * getters and setters");
        writeLine(" */");

        for (ViewProperty property:this.bean.formBean.properties) {
            writeLine("public " + property.beanDataType + " get" + property.capName + "() {");
            writeLine("return this." + property.name + ";");
            writeLine("}");
            
            writeLine("public void set" + property.capName + "(" + property.beanDataType + " " + property.name + ") {");
            writeLine("this." + property.name + " = " + property.name + ";");
            writeLine("}");
        }
        skipLine();
	}
}
