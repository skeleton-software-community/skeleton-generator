package org.sklsft.generator.bc.file.command.impl.java.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;


public class ViewBeanFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public ViewBeanFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-api\\src\\main\\java\\" + bean.myPackage.ovPackageName.replace(".","\\"), bean.viewClassName);
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import java.util.Date;");
        javaImports.add("import java.io.Serializable;");
	}
	
	
	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + bean.myPackage.ovPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated view bean class file");
        writeLine(" * <br/>basic representation of what is going to be considered as model in MVC patterns");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("public class " + this.bean.viewClassName + " implements Serializable {");
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

        List<Property> visiblePropertyList = this.bean.getVisibleProperties();

        for (int i=0;i<visiblePropertyList.size();i++)
        {
            writeLine("private " + visiblePropertyList.get(i).beanDataType + " " + visiblePropertyList.get(i).name + ";");
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

        List<Property> visiblePropertyList = this.bean.getVisibleProperties();

        for (int i=0;i<visiblePropertyList.size();i++)
        {
            writeLine("public " + visiblePropertyList.get(i).beanDataType + " get" + visiblePropertyList.get(i).capName + "() {");
            writeLine("return this." + visiblePropertyList.get(i).name + ";");
            writeLine("}");
            skipLine();
            
            writeLine("public void set" + visiblePropertyList.get(i).capName + "(" + visiblePropertyList.get(i).beanDataType + " " + visiblePropertyList.get(i).name + ") {");
            writeLine("this." + visiblePropertyList.get(i).name + " = " + visiblePropertyList.get(i).name + ";");
            writeLine("}");
            skipLine();
        }
        skipLine();
	}
}
