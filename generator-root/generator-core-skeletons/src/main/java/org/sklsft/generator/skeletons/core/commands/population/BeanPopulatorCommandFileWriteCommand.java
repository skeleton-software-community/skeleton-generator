package org.sklsft.generator.skeletons.core.commands.population;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

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
		
		javaImports.add("import javax.inject.Inject;");
		javaImports.add("import org.springframework.stereotype.Component;");
		
        javaImports.add("import " + this.bean.myPackage.formsPackageName + "." + this.bean.formBean.className + ";");
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
        
        writeLine("@Inject");
        writeLine("private " + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ";");
        skipLine();

        writeLine("@Override");
        writeLine("public void execute(BackupArguments arguments) {");
                
        writeLine("ObjectArrayToBeanMapper<" + bean.formBean.className + "> mapper;");
		
        writeLine("if (arguments.isArgumentsTyped()) {");
        writeLine("mapper = new ObjectArrayToBeanMapperImpl<" + bean.formBean.className + ">(" + bean.formBean.className + ".class);");
        writeLine("} else {");
        writeLine("mapper = new StringArrayToBeanMapperImpl<" + bean.formBean.className + ">(" + bean.formBean.className + ".class);");
        writeLine("}");
        
        writeLine("for (Object[] args : arguments.getArguments()) {");
        writeLine("String message = " + CHAR_34 + "execute " + bean.serviceObjectName + ".save - args : " + CHAR_34 + ";");
        writeLine("for (Object arg:args) {");
        writeLine("message += " + CHAR_34 + "[" + CHAR_34 + " + arg + " + CHAR_34 + "]" + CHAR_34 + ";");
        writeLine("}");
        writeLine("logger.info(message);");
        skipLine();
                
        writeLine("try {");
        writeLine(bean.formBean.className + " " + bean.formBean.objectName + " = mapper.mapFrom(new " + bean.formBean.className + "(), args);");
        skipLine();
        
        writeLine("this." + bean.serviceObjectName + ".save(" + this.bean.formBean.objectName + ");");
        writeLine("} catch (Exception e) {");
        writeLine("logger.error(message + " + CHAR_34 + "failed : " + CHAR_34 + " + e.getClass().getSimpleName() + " + CHAR_34 + " - " + CHAR_34 + " + e.getMessage(), e);");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        
	}
}
