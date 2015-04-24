package org.sklsft.generator.bc.file.command.impl.java.population;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Property;

public class ViewBeanBuilderFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

    public ViewBeanBuilderFileWriteCommand(Bean bean){
        super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-populator\\src\\main\\java\\" + bean.myPackage.builderPackageName.replace(".","\\"),
        		bean.viewClassName + "Builder");
        
        		this.bean = bean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.text.SimpleDateFormat;");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
        javaImports.add("import " + this.bean.myPackage.model.populationExceptionPackageName + ".BuildFailureException;");
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
    
        writeLine("private static final String SEPARATOR = " + (char)34 + "\\\\$" + (char)34 + ";");
        skipLine();
        
        writeLine("public static " + bean.viewClassName + " build(String line) throws BuildFailureException {");
        skipLine();
	    writeLine(bean.viewClassName + " " + bean.viewObjectName + " = new " + bean.viewClassName + "();");
	    skipLine();
        writeLine("try {");
        writeLine("String[] args = line.split(SEPARATOR, " + bean.getVisibleProperties().size() + ");");
        skipLine();
        
        Integer argNumber = 0;
        for (Property property : bean.getVisibleProperties())
        {
            writeLine("if (!args[" + argNumber.toString() + "].isEmpty()) {");
            writeLine(bean.viewObjectName + ".set" + property.capName + "(" + DataType.stringToBuildArg("args[" + argNumber + "]",property.dataType) + ");");
            writeLine("}");
            skipLine();
            argNumber++;
        }

        writeLine("} catch (Exception e) {");
        
		writeLine("throw new BuildFailureException (" + (char)34 + "failed to build object" + (char)34 + ",e);");
        
        writeLine("}");
        
	    writeLine("return " + bean.viewObjectName + ";");
        
        writeLine("}");
        
        writeLine("}");
		
	}
}
