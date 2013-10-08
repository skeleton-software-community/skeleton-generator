package org.skeleton.generator.bc.command.file.impl.java.bc.mapper;

import java.io.File;
import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.java.JavaFileWriteCommand;
import org.skeleton.generator.model.om.Bean;

public class MapperInterfaceFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public MapperInterfaceFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component\\src\\main\\java\\"
				+ bean.myPackage.mapperInterfacePackageName.replace(".", "\\"), bean.mapperInterfaceName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import " + this.bean.myPackage.baseMapperInterfacePackageName + "." + this.bean.baseMapperInterfaceName + ";");
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.mapperInterfacePackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated mapper interface file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();

        writeLine("public interface " + this.bean.mapperInterfaceName + " extends " + this.bean.baseMapperInterfaceName + " {");
        skipLine();
        
        this.writeNotOverridableContent();

        writeLine("}");
	}
}
