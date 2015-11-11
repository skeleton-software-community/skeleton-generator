package org.sklsft.generator.bc.file.command.impl.java.population;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.metadata.DataType;

public class ViewBeanBuilderFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

    public ViewBeanBuilderFileWriteCommand(Bean bean){
        super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-populator\\src\\main\\java\\" + bean.myPackage.builderPackageName.replace(".","\\"),
        		bean.fullViewBean.className + "Builder");
        
        		this.bean = bean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Date;");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.fullViewBean.className + ";");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.builderPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated view builder class file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
        writeLine("public class " + bean.fullViewBean.className + "Builder {");
        skipLine();
        
        writeLine("public static " + bean.fullViewBean.className + " build(Object[] args) {");
        skipLine();
	    writeLine(bean.fullViewBean.className + " " + bean.fullViewBean.objectName + " = new " + bean.fullViewBean.className + "();");
	    skipLine();
                
        Integer argNumber = 0;
        for (Property property : bean.fullViewBean.properties) {            
            writeLine(bean.fullViewBean.objectName + ".set" + property.capName + "((" + DataType.getJavaType(property.dataType) + ")args[" + argNumber + "]);");
            argNumber++;
        }
        
	    writeLine("return " + bean.fullViewBean.objectName + ";");
        
        writeLine("}");
        
        writeLine("}");
		
	}
}
