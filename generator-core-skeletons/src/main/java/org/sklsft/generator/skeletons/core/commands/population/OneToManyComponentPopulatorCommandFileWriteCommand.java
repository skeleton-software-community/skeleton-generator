package org.sklsft.generator.skeletons.core.commands.population;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class OneToManyComponentPopulatorCommandFileWriteCommand extends JavaFileWriteCommand {

	private OneToManyComponent oneToManyComponent;
	private Bean referenceBean;
    private Bean parentBean;

    public OneToManyComponentPopulatorCommandFileWriteCommand(OneToManyComponent oneToManyComponent){
        super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToManyComponent.referenceBean.myPackage.model.populatorArtefactName + File.separator + oneToManyComponent.referenceBean.myPackage.model.javaSourcesFolder + File.separator + oneToManyComponent.referenceBean.myPackage.commandPackageName.replace(".",File.separator),
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
		javaImports.add("import java.time.LocalDate;");
		javaImports.add("import java.math.BigDecimal;");
		
		javaImports.add("import org.sklsft.generator.persistence.backup.command.interfaces.BackupArgumentsCommand;");
		javaImports.add("import org.sklsft.generator.persistence.backup.reader.model.BackupArguments;");
		
		javaImports.add("import org.slf4j.Logger;");
		javaImports.add("import org.slf4j.LoggerFactory;");
		
		javaImports.add("import javax.inject.Inject;");
		javaImports.add("import org.springframework.stereotype.Component;");
		
        javaImports.add("import " + referenceBean.myPackage.fullViewsPackageName + "." + referenceBean.fullViewBean.className + ";");
        javaImports.add("import " + referenceBean.myPackage.formsPackageName + "." + referenceBean.formBean.className + ";");
        javaImports.add("import " + parentBean.myPackage.fullViewsPackageName + "." + parentBean.fullViewBean.className + ";");
        javaImports.add("import " + parentBean.myPackage.serviceInterfacePackageName + "." + parentBean.serviceInterfaceName + ";");
        
        javaImports.add("import org.sklsft.commons.mapper.impl.ObjectArrayToBeanMapperImpl;");
		javaImports.add("import org.sklsft.commons.mapper.impl.StringArrayToBeanMapperImpl;");
		javaImports.add("import org.sklsft.commons.mapper.interfaces.ObjectArrayToBeanMapper;");
		javaImports.add("import org.sklsft.commons.mapper.impl.StringToObjectConverter;");
		javaImports.add("import org.sklsft.commons.mapper.impl.DbObjectToObjectConverter;");
		
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
        writeLine("private static final Logger logger = LoggerFactory.getLogger(" + oneToManyComponent.referenceBean.className + "Command.class);");
        skipLine();
    
        writeLine("@Inject");
        writeLine("private " + oneToManyComponent.parentBean.serviceInterfaceName + " " + parentBean.serviceObjectName + ";");
        skipLine();
        
        writeLine("@Override");
        writeLine("public void execute(BackupArguments arguments) {");
        
        writeLine("ObjectArrayToBeanMapper<" + referenceBean.formBean.className + "> mapper;");
		
        writeLine("if (arguments.isArgumentsTyped()) {");
        writeLine("mapper = new ObjectArrayToBeanMapperImpl<" + referenceBean.formBean.className + ">(" + referenceBean.formBean.className + ".class);");
        writeLine("} else {");
        writeLine("mapper = new StringArrayToBeanMapperImpl<" + referenceBean.formBean.className + ">(" + referenceBean.formBean.className + ".class);");
        writeLine("}");
        
        writeLine("for (Object[] args:arguments.getArguments()) {");
        writeLine("String message = " + CHAR_34 + "execute " + parentBean.serviceObjectName + ".save - args : " + CHAR_34 + ";");
                
        writeLine("for (Object arg:args) {");
        writeLine("message += " + CHAR_34 + "[" + CHAR_34 + " + arg + " + CHAR_34 + "]" + CHAR_34 + ";");
        writeLine("}");
        writeLine("logger.info(message);");
        skipLine();
                
        writeLine("try {");
        
        List<ViewProperty> properties = parentBean.referenceViewProperties;
        
        writeLine(referenceBean.formBean.className + " " + referenceBean.formBean.objectName + " = mapper.mapFrom(new " + referenceBean.formBean.className + "(), Arrays.copyOfRange(args," + properties.size() + ",args.length));");
        skipLine();               
        
        int i = 0;
        for (ViewProperty property:properties) {
        	String type = property.javaType;        	
        	writeLine(type + " arg" + i + " = arguments.isArgumentsTyped()?(" + type + ")(DbObjectToObjectConverter.getObjectFromDbObject(args[" + i + "], " + type + ".class)):(" + type + ")(StringToObjectConverter.getObjectFromString((String)args[" + i + "], " + type + ".class));");
        	i++;
        }
        
        write(parentBean.fullViewBean.className + " " + parentBean.fullViewBean.objectName + " = " + parentBean.serviceObjectName + ".find(arg0");
        
        for (i=1;i<properties.size();i++) {
            write(", arg" + i);
        }
        writeLine(");");
        skipLine();
        
        writeLine("this." + parentBean.serviceObjectName + ".save" + referenceBean.className + "(" + parentBean.fullViewBean.objectName + ".getId(), " + referenceBean.formBean.objectName + ");");
        writeLine("} catch (Exception e) {");
        writeLine("logger.error(message + " + CHAR_34 + "failed : " + CHAR_34 + " + e.getClass().getSimpleName() + " + CHAR_34 + " - " + CHAR_34 + " + e.getMessage(), e);");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        
	}
}
