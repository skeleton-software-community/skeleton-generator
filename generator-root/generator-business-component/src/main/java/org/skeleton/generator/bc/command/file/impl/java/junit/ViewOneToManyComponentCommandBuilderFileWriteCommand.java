package org.skeleton.generator.bc.command.file.impl.java.junit;

import java.io.IOException;
import java.util.List;

import org.skeleton.generator.bc.command.file.impl.java.JavaFileWriteCommand;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToManyComponent;
import org.skeleton.generator.model.om.Property;
import org.skeleton.generator.util.metadata.DataType;

public class ViewOneToManyComponentCommandBuilderFileWriteCommand extends JavaFileWriteCommand {
	
	//private OneToManyComponent oneToManyComponent;
	private Bean referenceBean;
    private Bean parentBean;

    public ViewOneToManyComponentCommandBuilderFileWriteCommand(OneToManyComponent oneToManyComponent) {
        
        super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + "\\" + oneToManyComponent.referenceBean.myPackage.model.project.projectName + "-services\\src\\test\\java\\" + oneToManyComponent.referenceBean.myPackage.commandPackageName.replace(".", "\\"),
        oneToManyComponent.referenceBean.viewClassName + "CommandBuilder");
    
        //this.oneToManyComponent = oneToManyComponent;
        referenceBean = oneToManyComponent.referenceBean;
        parentBean = oneToManyComponent.parentBean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import " + referenceBean.myPackage.ovPackageName + "." + referenceBean.viewClassName + ";");
        javaImports.add("import " + referenceBean.myPackage.builderPackageName + "." + referenceBean.viewClassName + "Builder;");
        javaImports.add("import " + parentBean.myPackage.serviceInterfacePackageName + "." + parentBean.serviceInterfaceName + ";");
        javaImports.add("import " + referenceBean.myPackage.model.commandPackageName + ".Command;");
        javaImports.add("import " + referenceBean.myPackage.model.commandPackageName + ".CommandBuilder;");
        javaImports.add("import " + referenceBean.myPackage.model.testExceptionPackageName + ".BuildFailureException;");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + referenceBean.myPackage.commandPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("public class " + referenceBean.viewClassName + "CommandBuilder implements CommandBuilder {");
        skipLine();
        
        writeLine("private static final String SEPARATOR = " + (char)34 + "\\\\$" + (char)34 + ";");
        skipLine();

        writeLine("/*");
        writeLine(" * properties");
        writeLine(" */");
        writeLine("private " + parentBean.serviceInterfaceName + " " + parentBean.serviceObjectName + ";");
        skipLine();

        writeLine("/*");
        writeLine("* getters and setters");
        writeLine("*/");

        writeLine("public " + parentBean.serviceInterfaceName + " get" + parentBean.serviceInterfaceName + "() {");
        writeLine("return " + parentBean.serviceObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("public void set" + parentBean.serviceInterfaceName + "(" + parentBean.serviceInterfaceName + " " + parentBean.serviceObjectName + ") {");
        writeLine("this." + parentBean.serviceObjectName + " = " + parentBean.serviceObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("@Override");
        writeLine("public Command buildCommand(String line) throws BuildFailureException {");
        skipLine();

        writeLine(referenceBean.viewClassName + "Command command = new " + referenceBean.viewClassName + "Command();");
        writeLine("command.set" + parentBean.serviceInterfaceName + "(" + parentBean.serviceObjectName + ");");
        writeLine("command.set" + referenceBean.viewClassName + "(" + referenceBean.viewClassName + "Builder.build(line));");

        List<Property> findPropertyList = parentBean.getFindPropertyList();
        for (Property property : findPropertyList)
        {
            writeLine(property.beanDataType + " " + property.name + " = null;");
        }
        
        writeLine("String[] args = line.split(SEPARATOR, " + findPropertyList.size() + ");");
        skipLine();
        
        Integer argNumber = 0;
        for (Property property : findPropertyList)
        {
            writeLine("if (!args[" + argNumber.toString() + "].isEmpty()) {");
            writeLine(property.name + " = " + DataType.stringToBuildArg("args[" + argNumber + "]", property.dataType) + ";");
            writeLine("}");
            argNumber++;
        }
        

        writeLine("try {");
        write("command.set" + parentBean.viewClassName + "(" + parentBean.serviceObjectName + ".find" + parentBean.className + "(" + findPropertyList.get(0).name);
        for (int i=1;i<findPropertyList.size();i++)
        {
            write(", " + findPropertyList.get(i).name);
        }
        writeLine("));");
        writeLine("} catch (Exception e) {");
        writeLine("throw new BuildFailureException(" + (char)34 + "faild to find parent object" + (char)34 + ", e);");
        writeLine("}");
        writeLine("return command;");
        writeLine("}");
        writeLine("}");
		
	}
}
