package org.sklsft.generator.skeletons.rest.commands;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class RestClientFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	
	public RestClientFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.restClientArtefactName + File.separator + bean.myPackage.model.javaSourcesFolder + File.separator
				+ bean.myPackage.restClientPackageName.replace(".", File.separator), bean.restClientClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import " + this.bean.myPackage.baseRestClientPackageName + "." + this.bean.baseRestClientClassName + ";");
		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		javaImports.add("import org.springframework.stereotype.Service;");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.restClientPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated REST client class file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("@Service(\"" + bean.myPackage.model.project.projectName.toLowerCase() + this.bean.restClientClassName+"\")");
        writeLine("public class " + this.bean.restClientClassName + " extends " + this.bean.baseRestClientClassName + " implements " + this.bean.serviceInterfaceName + "{");
        skipLine();
        
        this.writeNotOverridableContent();

        writeLine("}");
		
	}

}
