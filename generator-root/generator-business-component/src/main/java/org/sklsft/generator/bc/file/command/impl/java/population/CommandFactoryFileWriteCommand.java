package org.sklsft.generator.bc.file.command.impl.java.population;

import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.Property;

public class CommandFactoryFileWriteCommand extends JavaFileWriteCommand {
	
	private Project project;
	
	public CommandFactoryFileWriteCommand(Project project) {
		super(project.workspaceFolder + "\\" + project.projectName + "-populator\\src\\main\\java\\" + project.model.commandPackageName.replace(".", "\\"),
				"CommandFactory");
		this.project = project;
	}

	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import org.springframework.stereotype.Component;");

        for (Package myPackage : this.project.model.packages)
        {
            for (Bean bean : myPackage.beans)
            {
                if (!bean.isComponent)
                {
                	javaImports.add("import " + myPackage.commandPackageName + "." + bean.viewClassName + "Command;");
                	javaImports.add("import " + myPackage.builderPackageName + "." + bean.viewClassName + "Builder;");
                	javaImports.add("import " + myPackage.serviceInterfacePackageName + "." + bean.serviceInterfaceName + ";");
                	

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        Bean referenceBean = oneToManyComponent.referenceBean;

                        javaImports.add("import " + referenceBean.myPackage.commandPackageName + "." + referenceBean.viewClassName + "Command;");
                        javaImports.add("import " + referenceBean.myPackage.builderPackageName + "." + referenceBean.viewClassName + "Builder;");
                        
                    }
                }
            }
        }
        javaImports.add("import " + this.project.model.populationExceptionPackageName + ".BuildFailureException;");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + project.model.commandPackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        
        writeLine("/**");
        writeLine(" * auto generated command builder factory class file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Component");
        writeLine("public class CommandFactory {");
        skipLine();

        for (Package myPackage : this.project.model.packages)
        {
            for (Bean bean : myPackage.beans)
            {
                if (!bean.isComponent)
                {
                    writeLine("@Autowired");
                    writeLine("private " + bean.serviceInterfaceName + " " + bean.serviceObjectName + ";");
                    
                }
            }
        }

        skipLine();

        writeLine("/**");
        writeLine(" * create the appropriate command depending on what class is used for population.");
        writeLine(" * @param line");
        writeLine(" * @return a command, if it is implemented.");
        writeLine(" * @throws BuildFailureException");
        writeLine(" */");
        writeLine("public Command createCommand(Class<?> clazz, String line) throws BuildFailureException {");
        skipLine();

        for (Package myPackage : this.project.model.packages)
        {
            for (Bean bean : myPackage.beans)
            {
                if (!bean.isComponent)
                {
                    writeLine("if (clazz.equals(" + bean.className + ".class)) {");
                    writeLine(bean.viewClassName + "Command command = new " + bean.viewClassName + "Command();");
                    writeLine("command.set" + bean.serviceInterfaceName + "(this." + bean.serviceObjectName + ");");
                    writeLine("command.set" + bean.viewClassName + "(" + bean.viewClassName + "Builder.build(line));");
                    writeLine("return command;");
                    writeLine("}");

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        Bean referenceBean = oneToManyComponent.referenceBean;
                        Bean parentBean = oneToManyComponent.parentBean;

                        writeLine("if (clazz.equals(" + referenceBean.className + ".class)) {");
                        writeLine(referenceBean.viewClassName + "Command command = new " + referenceBean.viewClassName + "Command();");
                        writeLine("command.set" + bean.serviceInterfaceName + "(this." + parentBean.serviceObjectName + ");");
                        writeLine("command.set" + referenceBean.viewClassName + "(" + referenceBean.viewClassName + "Builder.build(line));");

                        List<Property> findPropertyList = parentBean.getFindProperties();
                        for (Property property : findPropertyList)
                        {
                            writeLine(property.beanDataType + " " + property.name + " = null;");
                        }
                        
                        Integer splitSize = parentBean.getFindProperties().size() + referenceBean.getVisibleProperties().size();
                        
                        writeLine("String[] args = line.split(SEPARATOR, " + splitSize + ");");
                        skipLine();
                        
                        Integer argNumber = 0;
                        for (Property property : findPropertyList)
                        {
                            writeLine("if (!args[" + argNumber.toString() + "].isEmpty()) {");
                            writeLine(property.name + " = " + DataType.stringToBuildArg("args[" + argNumber + "]", property.dataType) + ";");
                            writeLine("}");
                            argNumber++;
                        }
                        
                        skipLine();
                        write("command.set" + parentBean.viewClassName + "(" + parentBean.serviceObjectName + ".find" + parentBean.className + "(" + findPropertyList.get(0).name);
                        for (int i=1;i<findPropertyList.size();i++)
                        {
                            write(", " + findPropertyList.get(i).name);
                        }
                        writeLine("));");
                        writeLine("return command;");
                        writeLine("} catch (Exception e) {");
                        writeLine("throw new BuildFailureException(" + (char)34 + "faild to find parent object" + (char)34 + ", e);");
                        writeLine("}");
                        writeLine("}");
                        writeLine("}");
                        
                    }
                }
            }
        }
        writeLine("throw new BuildFailureException(" + (char)34 + "unimplemented command" + (char)34 + ");");
        writeLine("}");
        writeLine("}");
	}
}
