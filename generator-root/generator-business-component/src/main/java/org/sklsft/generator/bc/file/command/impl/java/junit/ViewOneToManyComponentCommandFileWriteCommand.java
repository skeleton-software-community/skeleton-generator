package org.sklsft.generator.bc.file.command.impl.java.junit;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;

public class ViewOneToManyComponentCommandFileWriteCommand extends JavaFileWriteCommand {

	private OneToManyComponent oneToManyComponent;
	private Bean referenceBean;
    private Bean parentBean;

    public ViewOneToManyComponentCommandFileWriteCommand(OneToManyComponent oneToManyComponent){
        super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + "\\" + oneToManyComponent.referenceBean.myPackage.model.project.projectName + "-services\\src\\test\\java\\" + oneToManyComponent.referenceBean.myPackage.commandPackageName.replace(".","\\"),
        		oneToManyComponent.referenceBean.viewClassName + "Command");
        
        		this.oneToManyComponent = oneToManyComponent;
        		referenceBean = oneToManyComponent.referenceBean;
        	    parentBean = oneToManyComponent.parentBean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import org.springframework.dao.DataAccessException;");
        javaImports.add("import " + referenceBean.myPackage.ovPackageName + "." + referenceBean.viewClassName + ";");
        javaImports.add("import " + parentBean.myPackage.ovPackageName + "." + parentBean.viewClassName + ";");
        javaImports.add("import " + parentBean.myPackage.serviceInterfacePackageName + "." + parentBean.serviceInterfaceName + ";");
        javaImports.add("import " + referenceBean.myPackage.model.commandPackageName + ".Command;");
        javaImports.add("import " + referenceBean.myPackage.model.stateExceptionPackageName + ".InvalidStateException;");
        javaImports.add("import " + referenceBean.myPackage.model.daoExceptionPackageName + ".ObjectNotFoundException;");
        javaImports.add("import " + referenceBean.myPackage.model.testExceptionPackageName + ".CommandFailureException;");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + oneToManyComponent.referenceBean.myPackage.commandPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated view command class file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
        writeLine("public class " + oneToManyComponent.referenceBean.viewClassName + "Command implements Command {");
        skipLine();
    
        writeLine("/*");
        writeLine(" * properties");
        writeLine(" */");
        writeLine("private " + oneToManyComponent.parentBean.serviceInterfaceName + " " + parentBean.serviceObjectName + ";");
        writeLine("private " + referenceBean.viewClassName + " " + referenceBean.viewObjectName + ";");
        writeLine("private " + parentBean.viewClassName + " " + parentBean.viewObjectName + ";");
        skipLine();
        
        writeLine("/*");
        writeLine(" * getters and setters");
        writeLine(" */");
        writeLine("public " + parentBean.serviceInterfaceName + " get" + parentBean.serviceClassName + "() {");
        writeLine("return " + parentBean.serviceObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("public void set" + parentBean.serviceInterfaceName + "(" + parentBean.serviceInterfaceName + " " + parentBean.serviceObjectName + ") {");
        writeLine("this." + parentBean.serviceObjectName + " = " + parentBean.serviceObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("public " + referenceBean.viewClassName + " get" + referenceBean.viewClassName + "() {");
        writeLine("return " + referenceBean.viewObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("public void set" + referenceBean.viewClassName + "(" + referenceBean.viewClassName + " " + referenceBean.viewObjectName + ") {");
        writeLine("this." + referenceBean.viewObjectName + " = " + referenceBean.viewObjectName + ";");
        writeLine("}");
        skipLine();
        
        writeLine("public " + parentBean.viewClassName + " get" + parentBean.viewClassName + "() {");
        writeLine("return " + parentBean.viewObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("public void set" + parentBean.viewClassName + "(" + parentBean.viewClassName + " " + parentBean.viewObjectName + ") {");
        writeLine("this." + parentBean.viewObjectName + " = " + parentBean.viewObjectName + ";");
        writeLine("}");
        skipLine();

        writeLine("@Override");
        writeLine("public void execute() throws CommandFailureException {");
        skipLine();

        writeLine("try {");
        writeLine("this." + parentBean.serviceObjectName + ".save" + referenceBean.className + "(this." + referenceBean.viewObjectName + ", " + parentBean.viewObjectName + ".getId());");
        writeLine("} catch (Exception e) {");
        writeLine("throw new CommandFailureException(" + (char)34 + "failed to execute command" + (char)34 + ", e);");
        writeLine("}");
        writeLine("}");
        skipLine(); 
        writeLine("}");
        
	}
}
