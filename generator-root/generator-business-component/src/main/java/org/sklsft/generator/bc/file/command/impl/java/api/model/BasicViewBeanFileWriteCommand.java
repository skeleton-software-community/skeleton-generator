package org.sklsft.generator.bc.file.command.impl.java.api.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;


public class BasicViewBeanFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public BasicViewBeanFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-api" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + bean.myPackage.basicViewsPackageName.replace(".",File.separator), bean.basicViewBean.className);
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import java.util.Date;");
        javaImports.add("import java.io.Serializable;");
	}
	
	
	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + bean.myPackage.basicViewsPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated view bean class file");
        writeLine(" * <br/>basic representation of what is going to be considered as model in MVC patterns");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("public class " + this.bean.basicViewBean.className + " implements Serializable {");
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
        writeLine("private Long id;");
        writeLine("private boolean selected;");

        for (Property property:this.bean.basicViewBean.properties) {
            writeLine("private " + property.beanDataType + " " + property.name + ";");
        }
        skipLine();

    }

    private void createGettersAndSetters()
    {
        writeLine("/*");
        writeLine(" * getters and setters");
        writeLine(" */");
        writeLine("public Long getId() {");
        writeLine("return this.id;");
        writeLine("}");
        skipLine();
        
        writeLine("public void setId(Long id) {");
        writeLine("this.id = id;");
        writeLine("}");
        skipLine();

        writeLine("public boolean getSelected() {");
        writeLine("return this.selected;");
        writeLine("}");
        skipLine();
        
        writeLine("public void setSelected(boolean selected) {");
        writeLine("this.selected = selected;");
        writeLine("}");
        skipLine();

        for (Property property:this.bean.basicViewBean.properties) {
            writeLine("public " + property.beanDataType + " get" + property.capName + "() {");
            writeLine("return this." + property.name + ";");
            writeLine("}");
            skipLine();
            
            writeLine("public void set" + property.capName + "(" + property.beanDataType + " " + property.name + ") {");
            writeLine("this." + property.name + " = " + property.name + ";");
            writeLine("}");
            skipLine();
        }
        skipLine();
	}
}
