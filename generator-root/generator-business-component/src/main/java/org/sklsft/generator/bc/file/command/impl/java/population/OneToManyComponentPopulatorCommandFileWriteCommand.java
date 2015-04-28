package org.sklsft.generator.bc.file.command.impl.java.population;

import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Property;

public class OneToManyComponentPopulatorCommandFileWriteCommand extends JavaFileWriteCommand {

	private OneToManyComponent oneToManyComponent;
	private Bean referenceBean;
    private Bean parentBean;

    public OneToManyComponentPopulatorCommandFileWriteCommand(OneToManyComponent oneToManyComponent){
        super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + "\\" + oneToManyComponent.referenceBean.myPackage.model.project.projectName + "-populator\\src\\main\\java\\" + oneToManyComponent.referenceBean.myPackage.commandPackageName.replace(".","\\"),
        		oneToManyComponent.referenceBean.className + "Command");
        
        		this.oneToManyComponent = oneToManyComponent;
        		referenceBean = oneToManyComponent.referenceBean;
        	    parentBean = oneToManyComponent.parentBean;
    }

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.Date;");
		
		javaImports.add("import org.sklsft.generator.repository.backup.command.interfaces.Command;");
		
		javaImports.add("import org.slf4j.Logger;");
		javaImports.add("import org.slf4j.LoggerFactory;");
		
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import org.springframework.stereotype.Component;");
		
        javaImports.add("import " + referenceBean.myPackage.ovPackageName + "." + referenceBean.viewClassName + ";");
        javaImports.add("import " + parentBean.myPackage.ovPackageName + "." + parentBean.viewClassName + ";");
        javaImports.add("import " + parentBean.myPackage.serviceInterfacePackageName + "." + parentBean.serviceInterfaceName + ";");
        
        javaImports.add("import " + referenceBean.myPackage.builderPackageName + "." + referenceBean.viewClassName + "Builder;");

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
        writeLine("public class " + oneToManyComponent.referenceBean.className + "Command implements Command {");
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
        writeLine("public void execute(List<Object[]> argsList) {");
        
        writeLine("for (Object[] args:argsList) {");
        writeLine("String message = " + CHAR_34 + "execute " + parentBean.serviceObjectName + ".save" + referenceBean.className + " - args : " + CHAR_34 + ";");
                
        writeLine("for (Object arg:args) {");
        writeLine("message += " + CHAR_34 + "[" + CHAR_34 + " + arg + " + CHAR_34 + "]" + CHAR_34 + ";");
        writeLine("}");
        writeLine("logger.info(message);");
        skipLine();
        
        writeLine("try {");
        
        writeLine(referenceBean.viewClassName + " " + referenceBean.viewObjectName + " = " + referenceBean.viewClassName + "Builder.build(args);");
        skipLine();
        
        List<Property> findPropertyList = parentBean.getFindProperties();
        write(parentBean.viewClassName + " " + parentBean.viewObjectName + " = " + parentBean.serviceObjectName + ".find" + parentBean.className + "((" + DataType.getJavaType(findPropertyList.get(0).dataType) + ")args[0]");
        for (int i=1;i<findPropertyList.size();i++)
        {
            write(", (" + DataType.getJavaType(findPropertyList.get(i).dataType) + ")args[" + i + "]");
        }
        writeLine(");");
        skipLine();
        
        writeLine("this." + parentBean.serviceObjectName + ".save" + referenceBean.className + "(" + referenceBean.viewObjectName + ", " + parentBean.viewObjectName + ".getId());");
        writeLine("} catch (Exception e) {");
        writeLine("logger.error(message + " + CHAR_34 + "failed : " + CHAR_34 + " + e.getClass().getSimpleName() + " + CHAR_34 + " - " + CHAR_34 + " + e.getMessage());");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        
	}
}
