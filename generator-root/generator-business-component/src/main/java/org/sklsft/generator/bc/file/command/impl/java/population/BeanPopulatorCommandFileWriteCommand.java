package org.sklsft.generator.bc.file.command.impl.java.population;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;

public class BeanPopulatorCommandFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

    public BeanPopulatorCommandFileWriteCommand(Bean bean){
        super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-populator\\src\\main\\java\\" + bean.myPackage.commandPackageName.replace(".","\\"),
        		bean.className + "Command");
        
        		this.bean = bean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.List;");
		
		javaImports.add("import org.sklsft.generator.repository.backup.command.interfaces.Command;");
		
		javaImports.add("import org.slf4j.Logger;");
		javaImports.add("import org.slf4j.LoggerFactory;");		
		
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import org.springframework.stereotype.Component;");
		
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
        javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
        
        javaImports.add("import " + bean.myPackage.builderPackageName + "." + bean.viewClassName + "Builder;");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.commandPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated bean populator command class file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Component");
        writeLine("public class " + bean.className + "Command implements Command {");
        skipLine();
        
        writeLine("/*");
        writeLine(" * logger");
        writeLine(" */");
        writeLine("private static final Logger logger = LoggerFactory.getLogger(" + bean.serviceInterfaceName + ".class);");
        skipLine();
        
        writeLine("@Autowired");
        writeLine("private " + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ";");
        skipLine();

        writeLine("@Override");
        writeLine("public void execute(List<Object[]> argsList) {");
        
        writeLine("for (Object[] args:argsList) {");
        writeLine("String message = " + CHAR_34 + "execute " + bean.serviceObjectName + ".save" + bean.className + " - args : " + CHAR_34 + ";");
        writeLine("for (Object arg:args) {");
        writeLine("message += " + CHAR_34 + "[" + CHAR_34 + " + arg + " + CHAR_34 + "]" + CHAR_34 + ";");
        writeLine("}");
        writeLine("logger.info(message);");
        skipLine();
                
        writeLine("try {");
        writeLine(bean.viewClassName + " " + bean.viewObjectName + " = " + bean.viewClassName + "Builder.build(args);");
        skipLine();
        
        writeLine("this." + bean.serviceObjectName + ".save" + bean.className + "(" + this.bean.viewObjectName + ");");
        writeLine("} catch (Exception e) {");
        writeLine("logger.error(message + " + CHAR_34 + "failed : " + CHAR_34 + " + e.getClass().getSimpleName() + " + CHAR_34 + " - " + CHAR_34 + " + e.getMessage());");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        
	}
}
