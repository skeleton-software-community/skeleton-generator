package org.sklsft.generator.bc.file.command.impl.java.bc.statemanager;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;

public class StateManagerImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public StateManagerImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component\\src\\main\\java\\"
				+ bean.myPackage.stateManagerPackageName.replace(".", "\\"), bean.stateManagerName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import org.springframework.stereotype.Component;");
        javaImports.add("import " + this.bean.myPackage.baseStateManagerPackageName + "." + this.bean.baseStateManagerName + ";");
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.stateManagerPackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated state manager class file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();

        writeLine("@Component");
        writeLine("public class " + this.bean.stateManagerName + " extends " + this.bean.baseStateManagerName + " {");
        skipLine();
        
        this.writeNotOverridableContent();

        writeLine("}");
	}
}
