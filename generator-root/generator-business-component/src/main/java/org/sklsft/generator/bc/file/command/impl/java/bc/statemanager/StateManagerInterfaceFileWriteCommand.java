package org.sklsft.generator.bc.file.command.impl.java.bc.statemanager;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;

public class StateManagerInterfaceFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public StateManagerInterfaceFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component\\src\\main\\java\\"
				+ bean.myPackage.stateManagerInterfacePackageName.replace(".", "\\"), bean.stateManagerInterfaceName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import " + this.bean.myPackage.baseStateManagerInterfacePackageName + "." + this.bean.baseStateManagerInterfaceName + ";");
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.stateManagerInterfacePackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated state manager interface file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();

        writeLine("public interface " + this.bean.stateManagerInterfaceName + " extends " + this.bean.baseStateManagerInterfaceName + " {");
        skipLine();
        
        this.writeNotOverridableContent();

        writeLine("}");
	}
}
