package org.sklsft.generator.skeletons.jsf.commands.controller;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class JsfDetailControllerFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public JsfDetailControllerFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.detailControllerPackageName.replace(".", File.separator), bean.detailControllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import org.springframework.stereotype.Component;");
        javaImports.add("import org.springframework.context.annotation.Scope;");
        javaImports.add("import org.springframework.web.context.WebApplicationContext;");
        javaImports.add("import " + this.bean.myPackage.baseDetailControllerPackageName + "." + this.bean.baseDetailControllerClassName + ";");
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.detailControllerPackageName + ";");
		skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated detail controller class file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("@Component");
        writeLine("@Scope(value=WebApplicationContext.SCOPE_REQUEST)");
        writeLine("public class " + this.bean.detailControllerClassName + " extends  " + this.bean.baseDetailControllerClassName + " {");
        skipLine();

        writeNotOverridableContent();

        writeLine("}");
		
	}
}
