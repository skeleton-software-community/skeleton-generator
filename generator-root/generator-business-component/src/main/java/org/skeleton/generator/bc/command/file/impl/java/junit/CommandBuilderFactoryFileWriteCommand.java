package org.skeleton.generator.bc.command.file.impl.java.junit;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.java.JavaFileWriteCommand;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToManyComponent;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;

public class CommandBuilderFactoryFileWriteCommand extends JavaFileWriteCommand {
	
	private Project project;
	
	public CommandBuilderFactoryFileWriteCommand(Project project) {
		super(project.workspaceFolder + "\\" + project.projectName + "-services\\src\\test\\java\\" + project.model.commandPackageName.replace(".", "\\"),
				"CommandBuilderFactory");
		this.project = project;
	}

	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");

        for (Package myPackage : this.project.model.packages)
        {
            for (Bean bean : myPackage.beans)
            {
                if (!bean.isComponent)
                {
                	javaImports.add("import " + myPackage.commandPackageName + "." + bean.viewClassName + "CommandBuilder;");
                	javaImports.add("import " + myPackage.serviceInterfacePackageName + "." + bean.serviceInterfaceName + ";");
                	javaImports.add("import " + myPackage.omPackageName + "." + bean.className + ";");

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        Bean referenceBean = oneToManyComponent.referenceBean;

                        javaImports.add("import " + referenceBean.myPackage.commandPackageName + "." + referenceBean.viewClassName + "CommandBuilder;");
                        javaImports.add("import " + referenceBean.myPackage.omPackageName + "." + referenceBean.className + ";");
                    }
                }
            }
        }
        javaImports.add("import " + this.project.model.testExceptionPackageName + ".BuildFailureException;");
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
        writeLine("public class CommandBuilderFactory {");
        skipLine();

        writeLine("/*");
        writeLine(" * properties");
        writeLine(" */");

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
        writeLine(" * create the appropriate command builder depending on what class is tested.");
        writeLine(" * @param line");
        writeLine(" * @return a commandBuilder, if it is implemented.");
        writeLine(" * @throws BuildFailureException");
        writeLine(" */");
        writeLine("public CommandBuilder createCommandBuilder(String line, Class<?> clazz) throws BuildFailureException {");
        skipLine();

        for (Package myPackage : this.project.model.packages)
        {
            for (Bean bean : myPackage.beans)
            {
                if (!bean.isComponent)
                {
                    writeLine("if (clazz.equals(" + bean.className + ".class)) {");
                    writeLine(bean.viewClassName + "CommandBuilder commandBuilder = new " + bean.viewClassName + "CommandBuilder();");
                    writeLine("commandBuilder.set" + bean.serviceInterfaceName + "(this." + bean.serviceObjectName + ");");
                    writeLine("return commandBuilder;");
                    writeLine("}");

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        Bean referenceBean = oneToManyComponent.referenceBean;
                        Bean parentBean = oneToManyComponent.parentBean;

                        writeLine("if (clazz.equals(" + referenceBean.className + ".class)) {");
                        writeLine(referenceBean.viewClassName + "CommandBuilder commandBuilder = new " + referenceBean.viewClassName + "CommandBuilder();");
                        writeLine("commandBuilder.set" + bean.serviceInterfaceName + "(this." + parentBean.serviceObjectName + ");");
                        writeLine("return commandBuilder;");
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
