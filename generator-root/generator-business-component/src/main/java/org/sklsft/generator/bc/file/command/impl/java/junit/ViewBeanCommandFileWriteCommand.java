package org.sklsft.generator.bc.file.command.impl.java.junit;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;

public class ViewBeanCommandFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

    public ViewBeanCommandFileWriteCommand(Bean bean){
        super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-services\\src\\test\\java\\" + bean.myPackage.commandPackageName.replace(".","\\"),
        		bean.viewClassName + "Command");
        
        		this.bean = bean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import org.springframework.dao.DataAccessException;");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
        javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
        javaImports.add("import " + this.bean.myPackage.model.commandPackageName + ".Command;");
        javaImports.add("import org.sklsft.commons.api.exception.state.InvalidStateException;");
        javaImports.add("import org.sklsft.commons.api.exception.repository.ObjectNotFoundException;");
        javaImports.add("import " + this.bean.myPackage.model.testExceptionPackageName + ".CommandFailureException;");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.commandPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated view command class file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
        writeLine("public class " + bean.viewClassName + "Command implements Command {");
        skipLine();
    
        writeLine("/*");
        writeLine(" * properties");
        writeLine(" */");
        writeLine("private " + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ";");
        writeLine("private " + this.bean.viewClassName + " " + this.bean.viewObjectName + ";");
        skipLine();
        
        writeLine("/*");
        writeLine(" * getters and setters");
        writeLine(" */");
        writeLine("public " + this.bean.serviceInterfaceName + " get" + this.bean.serviceClassName + "() {");
        writeLine("return " + this.bean.serviceObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("public void set" + this.bean.serviceInterfaceName + "(" + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ") {");
        writeLine("this." + this.bean.serviceObjectName + " = " + this.bean.serviceObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("public " + this.bean.viewClassName + " get" + this.bean.viewClassName + "() {");
        writeLine("return " + this.bean.viewObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("public void set" + this.bean.viewClassName + "(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ") {");
        writeLine("this." + this.bean.viewObjectName + " = " + this.bean.viewObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("@Override");
        writeLine("public void execute() throws CommandFailureException {");
        skipLine();

        writeLine("try {");
        writeLine("this." + bean.serviceObjectName + ".save" + bean.className + "(this." + this.bean.viewObjectName + ");");
        writeLine("} catch (Exception e) {");
        writeLine("throw new CommandFailureException(" + (char)34 + "failed to execute command" + (char)34 + ", e);");
        writeLine("}");
        writeLine("}");
        skipLine(); 
        writeLine("}");
        
	}
}
