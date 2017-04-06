package org.sklsft.generator.skeletons.core.commands.api.interfaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class ServiceInterfaceFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	
	public ServiceInterfaceFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-api" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.serviceInterfacePackageName.replace(".", File.separator), bean.serviceInterfaceName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import " + this.bean.myPackage.baseServiceInterfacePackageName + "." + this.bean.baseServiceInterfaceName + ";");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.serviceInterfacePackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated service interface file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("public interface " + this.bean.serviceInterfaceName + " extends " + this.bean.baseServiceInterfaceName + " {");
        skipLine();
        
        this.writeNotOverridableContent();

        writeLine("}");
		
	}

}
