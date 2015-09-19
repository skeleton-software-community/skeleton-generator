package org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces3;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;

public class JsfListControllerFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public JsfListControllerFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\java\\"
				+ bean.myPackage.controllerPackageName.replace(".", "\\"), bean.listControllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import org.springframework.stereotype.Component;");
        javaImports.add("import org.springframework.context.annotation.Scope;");
        javaImports.add("import org.springframework.web.context.WebApplicationContext;");
        javaImports.add("import " + this.bean.myPackage.baseControllerPackageName + "." + this.bean.baseListControllerClassName + ";");
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.controllerPackageName + ";");
		skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated list controller class file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("@Component");
        writeLine("@Scope(value=WebApplicationContext.SCOPE_REQUEST)");
        writeLine("public class " + this.bean.listControllerClassName + " extends  " + this.bean.baseListControllerClassName + " {");
        skipLine();

        writeNotOverridableContent();

        writeLine("}");
		
	}
}
