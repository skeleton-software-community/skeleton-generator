package org.sklsft.generator.bc.file.command.impl.java.population;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.metadata.DataType;

public class OneToManyComponentPopulatorCommandFileWriteCommand extends JavaFileWriteCommand {

	private OneToManyComponent oneToManyComponent;
	private Bean referenceBean;
    private Bean parentBean;

    public OneToManyComponentPopulatorCommandFileWriteCommand(OneToManyComponent oneToManyComponent){
        super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToManyComponent.referenceBean.myPackage.model.project.projectName + "-populator" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + oneToManyComponent.referenceBean.myPackage.commandPackageName.replace(".",File.separator),
        		oneToManyComponent.referenceBean.className + "Command");
        
        		this.oneToManyComponent = oneToManyComponent;
        		referenceBean = oneToManyComponent.referenceBean;
        	    parentBean = oneToManyComponent.parentBean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Arrays;");
		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.Date;");
		
		javaImports.add("import org.sklsft.generator.repository.backup.command.interfaces.BackupArgumentsCommand;");
		javaImports.add("import org.sklsft.generator.repository.backup.reader.model.BackupArguments;");
		
		javaImports.add("import org.slf4j.Logger;");
		javaImports.add("import org.slf4j.LoggerFactory;");
		
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import org.springframework.stereotype.Component;");
		
        javaImports.add("import " + referenceBean.myPackage.ovPackageName + "." + referenceBean.fullViewBean.className + ";");
        javaImports.add("import " + parentBean.myPackage.ovPackageName + "." + parentBean.fullViewBean.className + ";");
        javaImports.add("import " + parentBean.myPackage.serviceInterfacePackageName + "." + parentBean.serviceInterfaceName + ";");
        
        javaImports.add("import org.sklsft.commons.mapper.impl.ObjectArrayToBeanMapperImpl;");
		javaImports.add("import org.sklsft.commons.mapper.impl.StringArrayToBeanMapperImpl;");
		javaImports.add("import org.sklsft.commons.mapper.interfaces.ObjectArrayToBeanMapper;");
		javaImports.add("import org.sklsft.commons.mapper.impl.StringToObjectConverter;");
		
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
		writeLine("@Component");
        writeLine("public class " + oneToManyComponent.referenceBean.className + "Command implements BackupArgumentsCommand {");
        skipLine();
        
        writeLine("/*");
        writeLine(" * logger");
        writeLine(" */");
        writeLine("private static final Logger logger = LoggerFactory.getLogger(" + parentBean.serviceInterfaceName + ".class);");
        skipLine();
    
        writeLine("@Autowired");
        writeLine("private " + oneToManyComponent.parentBean.serviceInterfaceName + " " + parentBean.serviceObjectName + ";");
        skipLine();
        
        writeLine("@Override");
        writeLine("public void execute(BackupArguments arguments) {");
        
        writeLine("ObjectArrayToBeanMapper<" + referenceBean.fullViewBean.className + "> mapper;");
		
        writeLine("if (arguments.isArgumentsTyped()) {");
        writeLine("mapper = new ObjectArrayToBeanMapperImpl<" + referenceBean.fullViewBean.className + ">(" + referenceBean.fullViewBean.className + ".class);");
        writeLine("} else {");
        writeLine("mapper = new StringArrayToBeanMapperImpl<" + referenceBean.fullViewBean.className + ">(" + referenceBean.fullViewBean.className + ".class);");
        writeLine("}");
        
        writeLine("for (Object[] args:arguments.getArguments()) {");
        writeLine("String message = " + CHAR_34 + "execute " + parentBean.serviceObjectName + ".save - args : " + CHAR_34 + ";");
                
        writeLine("for (Object arg:args) {");
        writeLine("message += " + CHAR_34 + "[" + CHAR_34 + " + arg + " + CHAR_34 + "]" + CHAR_34 + ";");
        writeLine("}");
        writeLine("logger.info(message);");
        skipLine();
        
        List<Property> findPropertyList = parentBean.getReferenceProperties();
                
        writeLine("try {");
        
        writeLine(referenceBean.fullViewBean.className + " " + referenceBean.fullViewBean.objectName + " = mapper.mapFrom(new " + referenceBean.fullViewBean.className + "(), Arrays.copyOfRange(args," + findPropertyList.size() + ",args.length),1);");
        skipLine();               
        
        for (int i=0;i<findPropertyList.size();i++)
        {
        	String type = DataType.getJavaType(findPropertyList.get(i).dataType);
        	
        	writeLine(type + " arg" + i + " = arguments.isArgumentsTyped()?(" + type + ")args[" + i + "]:(" + type + ")(StringToObjectConverter.getObjectFromString((String)args[" + i + "], " + type + ".class));");
        }
        
        write(parentBean.fullViewBean.className + " " + parentBean.fullViewBean.objectName + " = " + parentBean.serviceObjectName + ".find(arg0");
        for (int i=1;i<findPropertyList.size();i++)
        {
            write(", arg" + i);
        }
        writeLine(");");
        skipLine();
        
        writeLine("this." + parentBean.serviceObjectName + ".save" + referenceBean.className + "(" + referenceBean.fullViewBean.objectName + ", " + parentBean.fullViewBean.objectName + ".getId());");
        writeLine("} catch (Exception e) {");
        writeLine("logger.error(message + " + CHAR_34 + "failed : " + CHAR_34 + " + e.getClass().getSimpleName() + " + CHAR_34 + " - " + CHAR_34 + " + e.getMessage(), e);");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        
	}
}
