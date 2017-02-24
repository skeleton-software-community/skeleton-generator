package org.sklsft.generator.bc.file.command.impl.java.bc.mapper;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;

public class FullViewMapperFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public FullViewMapperFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.fullViewMapperPackageName.replace(".", File.separator), bean.fullViewBean.mapperClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import org.springframework.stereotype.Component;");
        javaImports.add("import org.sklsft.commons.mapper.impl.FullViewMapper;");
        javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		javaImports.add("import " + this.bean.myPackage.formsPackageName + "." + this.bean.formBean.className + ";");
		javaImports.add("import " + this.bean.myPackage.fullViewsPackageName + "." + this.bean.fullViewBean.className + ";");
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.fullViewMapperPackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated mapper class file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();

        writeLine("@Component(" + CHAR_34 + bean.myPackage.model.project.projectName.toLowerCase() + this.bean.fullViewBean.mapperClassName + CHAR_34 + ")");
        writeLine("public class " + this.bean.fullViewBean.mapperClassName + " extends FullViewMapper<" + this.bean.fullViewBean.className + ", Long, " + this.bean.formBean.className + ", " + this.bean.className + "> {");
        skipLine();
        
        writeLine("public " + this.bean.fullViewBean.mapperClassName + "() {");
		writeLine("super(" + this.bean.fullViewBean.className + ".class, " + this.bean.className + ".class);");
		writeLine("}");
		skipLine();
        
        this.writeNotOverridableContent();

        writeLine("}");
	}
}
