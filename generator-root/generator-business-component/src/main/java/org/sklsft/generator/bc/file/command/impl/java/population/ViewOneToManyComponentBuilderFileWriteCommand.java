package org.sklsft.generator.bc.file.command.impl.java.population;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Property;

public class ViewOneToManyComponentBuilderFileWriteCommand extends JavaFileWriteCommand {

	private OneToManyComponent oneToManyComponent;
	private Bean referenceBean;
    private Bean parentBean;

    public ViewOneToManyComponentBuilderFileWriteCommand(OneToManyComponent oneToManyComponent){
        super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + "\\" + oneToManyComponent.referenceBean.myPackage.model.project.projectName + "-populator\\src\\main\\java\\" + oneToManyComponent.referenceBean.myPackage.builderPackageName.replace(".","\\"),
        		oneToManyComponent.referenceBean.viewClassName + "Builder");
        
        this.oneToManyComponent = oneToManyComponent;
        referenceBean = oneToManyComponent.referenceBean;
	    parentBean = oneToManyComponent.parentBean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.text.SimpleDateFormat;");
        javaImports.add("import " + oneToManyComponent.referenceBean.myPackage.ovPackageName + "." + oneToManyComponent.referenceBean.viewClassName + ";");
        javaImports.add("import " + oneToManyComponent.referenceBean.myPackage.model.populationExceptionPackageName + ".BuildFailureException;");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + referenceBean.myPackage.builderPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated view builder class file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
        writeLine("public class " + referenceBean.viewClassName + "Builder {");
        skipLine();
    
        writeLine("private static final String SEPARATOR = " + (char)34 + "\\\\$" + (char)34 + ";");
        skipLine();
        
        Integer splitSize = parentBean.getFindProperties().size() + referenceBean.getVisibleProperties().size();
        
        writeLine("public static " + referenceBean.viewClassName + " build(String line) throws BuildFailureException {");
        skipLine();
	    writeLine(referenceBean.viewClassName + " " + referenceBean.viewObjectName + " = new " + referenceBean.viewClassName + "();");
	    skipLine();
        writeLine("try {");
        writeLine("String[] args = line.split(SEPARATOR, " + splitSize + ");");
        skipLine();
        
        Integer argNumber = parentBean.getFindProperties().size();
        for (Property property : referenceBean.getVisibleProperties())
        {
            writeLine("if (!args[" + argNumber.toString() + "].isEmpty()) {");
            writeLine(referenceBean.viewObjectName + ".set" + property.capName + "(" + DataType.stringToBuildArg("args[" + argNumber + "]",property.dataType) + ");");
            writeLine("}");
            skipLine();
            argNumber++;
        }

        writeLine("} catch (Exception e) {");
		writeLine("throw new BuildFailureException (" + (char)34 + "failed to build object" + (char)34 + ",e);");
        writeLine("}");
	    writeLine("return " + referenceBean.viewObjectName + ";");
        writeLine("}");
        writeLine("}");
		
	}
}
