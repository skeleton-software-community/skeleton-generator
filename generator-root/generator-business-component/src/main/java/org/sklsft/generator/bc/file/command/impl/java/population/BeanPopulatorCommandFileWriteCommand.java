package org.sklsft.generator.bc.file.command.impl.java.population;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;

public class BeanPopulatorCommandFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

    public BeanPopulatorCommandFileWriteCommand(Bean bean){
        super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-populator" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + bean.myPackage.commandPackageName.replace(".",File.separator),
        		bean.className + "Command");
        
        		this.bean = bean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.List;");
		
		javaImports.add("import org.sklsft.generator.repository.backup.command.interfaces.BackupArgumentsCommand;");
		javaImports.add("import org.sklsft.generator.repository.backup.reader.model.BackupArguments;");
		
		javaImports.add("import org.slf4j.Logger;");
		javaImports.add("import org.slf4j.LoggerFactory;");		
		
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import org.springframework.stereotype.Component;");
		
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.fullViewBean.className + ";");
        javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
        
        javaImports.add("import org.sklsft.commons.mapper.impl.ObjectArrayToBeanMapperImpl;");
		javaImports.add("import org.sklsft.commons.mapper.impl.StringArrayToBeanMapperImpl;");
		javaImports.add("import org.sklsft.commons.mapper.interfaces.ObjectArrayToBeanMapper;");
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
        writeLine("public class " + bean.className + "Command implements BackupArgumentsCommand {");
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
        writeLine("public void execute(BackupArguments arguments) {");
                
        writeLine("ObjectArrayToBeanMapper<" + bean.fullViewBean.className + "> mapper;");
		
        writeLine("if (arguments.isArgumentsTyped()) {");
        writeLine("mapper = new ObjectArrayToBeanMapperImpl<" + bean.fullViewBean.className + ">(" + bean.fullViewBean.className + ".class);");
        writeLine("} else {");
        writeLine("mapper = new StringArrayToBeanMapperImpl<" + bean.fullViewBean.className + ">(" + bean.fullViewBean.className + ".class);");
        writeLine("}");
        
        writeLine("for (Object[] args : arguments.getArguments()) {");
        writeLine("String message = " + CHAR_34 + "execute " + bean.serviceObjectName + ".save - args : " + CHAR_34 + ";");
        writeLine("for (Object arg:args) {");
        writeLine("message += " + CHAR_34 + "[" + CHAR_34 + " + arg + " + CHAR_34 + "]" + CHAR_34 + ";");
        writeLine("}");
        writeLine("logger.info(message);");
        skipLine();
                
        writeLine("try {");
        writeLine(bean.fullViewBean.className + " " + bean.fullViewBean.objectName + " = mapper.mapFrom(new " + bean.fullViewBean.className + "(), args, 1);");
        skipLine();
        
        writeLine("this." + bean.serviceObjectName + ".save(" + this.bean.fullViewBean.objectName + ");");
        writeLine("} catch (Exception e) {");
        writeLine("logger.error(message + " + CHAR_34 + "failed : " + CHAR_34 + " + e.getClass().getSimpleName() + " + CHAR_34 + " - " + CHAR_34 + " + e.getMessage(), e);");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        
	}
}
