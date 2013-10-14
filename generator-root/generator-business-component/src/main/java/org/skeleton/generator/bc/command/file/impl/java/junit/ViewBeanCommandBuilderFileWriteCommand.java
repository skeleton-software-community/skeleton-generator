package org.skeleton.generator.bc.command.file.impl.java.junit;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.java.JavaFileWriteCommand;
import org.skeleton.generator.model.om.Bean;

public class ViewBeanCommandBuilderFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

    public ViewBeanCommandBuilderFileWriteCommand(Bean bean){
        super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-services\\src\\test\\java\\" + bean.myPackage.commandPackageName.replace(".","\\"),
        		bean.viewClassName + "CommandBuilder");
        
        		this.bean = bean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
        javaImports.add("import " + this.bean.myPackage.builderPackageName + "." + this.bean.viewClassName + "Builder;");
        javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
        javaImports.add("import " + this.bean.myPackage.model.commandPackageName + ".Command;");
        javaImports.add("import " + this.bean.myPackage.model.commandPackageName + ".CommandBuilder;");
        javaImports.add("import " + this.bean.myPackage.model.testExceptionPackageName + ".BuildFailureException;");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.commandPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated view command builder class file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
        writeLine("public class " + bean.viewClassName + "CommandBuilder implements CommandBuilder {");
        skipLine();
    
        writeLine("/*");
        writeLine(" * properties");
        writeLine(" */");
        writeLine("private " + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ";");
        skipLine();

        writeLine("/*");
        writeLine(" * getters and setters");
        writeLine(" */");
        writeLine("public " + this.bean.serviceInterfaceName + " get" + this.bean.serviceInterfaceName + "() {");
        writeLine("return " + this.bean.serviceObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("public void set" + this.bean.serviceInterfaceName + "(" + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ") {");
        writeLine("this." + this.bean.serviceObjectName + " = " + this.bean.serviceObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("@Override");
        writeLine("public Command buildCommand(String line) throws BuildFailureException {");
        skipLine();

        writeLine(this.bean.viewClassName + "Command command = new " + this.bean.viewClassName + "Command();");
        writeLine("command.set" + this.bean.serviceInterfaceName + "(" + this.bean.serviceObjectName + ");");
        writeLine("command.set" + this.bean.viewClassName + "(" + this.bean.viewClassName + "Builder.build(line));");
        writeLine("return command;");
        writeLine("}");
        writeLine("}");
		
	}
}
