package org.skeleton.generator.bc.command.file.impl.java.bc.statemanager;

import java.io.File;
import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.java.JavaFileWriteCommand;
import org.skeleton.generator.model.om.Bean;

public class StateManagerImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public StateManagerImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component\\src\\main\\java\\"
				+ bean.myPackage.stateManagerImplPackageName.replace(".", "\\"), bean.stateManagerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import org.springframework.stereotype.Component;");
		javaImports.add("import " + this.bean.myPackage.stateManagerInterfacePackageName + "." + this.bean.stateManagerInterfaceName + ";");
        javaImports.add("import " + this.bean.myPackage.baseStateManagerImplPackageName + "." + this.bean.baseStateManagerClassName + ";");
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.stateManagerImplPackageName + ";");
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
        writeLine("public class " + this.bean.stateManagerClassName + " extends " + this.bean.baseStateManagerClassName + " implements " + bean.stateManagerInterfaceName + " {");
        skipLine();
        
        this.writeNotOverridableContent();

        writeLine("}");
	}
}
