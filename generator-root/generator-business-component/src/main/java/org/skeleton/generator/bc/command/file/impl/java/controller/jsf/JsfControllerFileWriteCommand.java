package org.skeleton.generator.bc.command.file.impl.java.controller.jsf;

import java.io.File;
import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.java.JavaFileWriteCommand;
import org.skeleton.generator.model.om.Bean;

public class JsfControllerFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public JsfControllerFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\java\\"
				+ bean.myPackage.controllerPackageName.replace(".", "\\"), bean.controllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import org.slf4j.Logger;");
		javaImports.add("import org.slf4j.LoggerFactory;");
		javaImports.add("import org.springframework.stereotype.Component;");
        javaImports.add("import org.springframework.context.annotation.Scope;");
        javaImports.add("import org.springframework.web.context.WebApplicationContext;");
        javaImports.add("import " + this.bean.myPackage.baseControllerPackageName + "." + this.bean.baseControllerClassName + ";");
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.controllerPackageName + ";");
		skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated controller class file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("@Component");
        writeLine("@Scope(value=WebApplicationContext.SCOPE_SESSION)");
        writeLine("public class " + this.bean.controllerClassName + " extends  " + this.bean.baseControllerClassName + " {");
        skipLine();
        
        writeLine("/*");
		writeLine(" * logger");
		writeLine(" */");
		writeLine("private static final Logger logger = LoggerFactory.getLogger(" + this.bean.controllerClassName + ".class);");
		skipLine();

        writeNotOverridableContent();

        writeLine("}");
		
	}
}
