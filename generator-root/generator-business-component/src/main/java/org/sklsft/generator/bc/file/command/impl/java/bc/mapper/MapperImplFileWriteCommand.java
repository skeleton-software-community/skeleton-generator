package org.sklsft.generator.bc.file.command.impl.java.bc.mapper;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;

public class MapperImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public MapperImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component\\src\\main\\java\\"
				+ bean.myPackage.mapperImplPackageName.replace(".", "\\"), bean.mapperClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import org.springframework.stereotype.Component;");
        javaImports.add("import " + this.bean.myPackage.baseMapperImplPackageName + "." + this.bean.baseMapperClassName + ";");
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.mapperImplPackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated mapper class file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();

        writeLine("@Component");
        writeLine("public class " + this.bean.mapperClassName + " extends " + this.bean.baseMapperClassName + " {");
        skipLine();
        
        this.writeNotOverridableContent();

        writeLine("}");
	}
}
