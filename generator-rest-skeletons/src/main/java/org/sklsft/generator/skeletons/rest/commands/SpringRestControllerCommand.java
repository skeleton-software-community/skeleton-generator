package org.sklsft.generator.skeletons.rest.commands;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class SpringRestControllerCommand extends JavaFileWriteCommand {

private Bean bean;
	
	public SpringRestControllerCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.restArtefactName + File.separator + bean.myPackage.model.javaSourcesFolder + File.separator
				+ bean.myPackage.restControllerPackageName.replace(".", File.separator), bean.restControllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import org.springframework.stereotype.Controller;");
		javaImports.add("import " + this.bean.myPackage.baseRestControllerPackageName + "." + this.bean.baseRestControllerClassName + ";");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.restControllerPackageName + ";");
		skipLine();
		
		writeImports();
		skipLine();
		
		writeLine("/**");
		writeLine(" * auto generated rest controller file"); 
		writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
		writeLine("@Controller");
		writeLine("public class " + this.bean.restControllerClassName + " extends  " + this.bean.baseRestControllerClassName + " {");
		skipLine();
		
		writeNotOverridableContent();

		writeLine("}");
	}
}