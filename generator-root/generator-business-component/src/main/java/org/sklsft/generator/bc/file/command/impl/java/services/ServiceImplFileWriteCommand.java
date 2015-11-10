package org.sklsft.generator.bc.file.command.impl.java.services;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;

public class ServiceImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	
	public ServiceImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-services\\src\\main\\java\\"
				+ bean.myPackage.serviceImplPackageName.replace(".", "\\"), bean.serviceClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import " + this.bean.myPackage.baseServiceImplPackageName + "." + this.bean.baseServiceClassName + ";");
		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		javaImports.add("import org.springframework.stereotype.Service;");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.serviceImplPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated service class file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("@Service");
        writeLine("public class " + this.bean.serviceClassName + " extends " + this.bean.baseServiceClassName + " implements " + this.bean.serviceInterfaceName + "{");
        skipLine();
        
        this.writeNotOverridableContent();

        writeLine("}");
		
	}

}
