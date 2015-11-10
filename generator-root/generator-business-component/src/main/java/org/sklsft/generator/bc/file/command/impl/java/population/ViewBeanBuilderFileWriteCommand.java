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
        		bean.viewClassName + "Builder");
        
        		this.bean = bean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Date;");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
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
        writeLine("public class " + bean.viewClassName + "Builder {");
        skipLine();
        
        writeLine("public static " + bean.viewClassName + " build(Object[] args) {");
        skipLine();
	    writeLine(bean.viewClassName + " " + bean.viewObjectName + " = new " + bean.viewClassName + "();");
	    skipLine();
                
        Integer argNumber = 0;
        for (Property property : bean.getVisibleProperties())
        {            
            writeLine(bean.viewObjectName + ".set" + property.capName + "((" + DataType.getJavaType(property.dataType) + ")args[" + argNumber + "]);");
            argNumber++;
        }
        
	    writeLine("return " + bean.viewObjectName + ";");
        
        writeLine("}");
        
        writeLine("}");
		
	}
}
