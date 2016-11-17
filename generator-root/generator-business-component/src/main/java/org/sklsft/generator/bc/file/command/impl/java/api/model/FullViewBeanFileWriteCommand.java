package org.sklsft.generator.bc.file.command.impl.java.api.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;


public class FullViewBeanFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public FullViewBeanFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-api" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + bean.myPackage.fullViewsPackageName.replace(".",File.separator), bean.fullViewBean.className);
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.io.Serializable;");
		javaImports.add("import javax.validation.constraints.NotNull;");
		javaImports.add("import org.sklsft.commons.api.model.FullView;");
		javaImports.add("import " + this.bean.myPackage.formsPackageName + "." + this.bean.formBean.className + ";");
	}
	
	
	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + bean.myPackage.fullViewsPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated view bean class file");
		writeLine(" * <br/>basic representation of what is going to be considered as model in MVC patterns");
		writeLine(" * <br/>write modifications between specific code marks");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.fullViewBean.className + " extends FullView<Long, " + bean.formBean.className + "> {");
		skipLine();

		writeLine("private static final long serialVersionUID = 1L;");
		skipLine();
		
		writeNotOverridableContent();
		
		writeLine("}");

    }
}
