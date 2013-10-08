package org.skeleton.generator.bc.command.file.impl.java.controller.jsf;

import java.io.File;
import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.java.JavaFileWriteCommand;
import org.skeleton.generator.model.om.Bean;

public class BaseJsfControllerFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	
	public BaseJsfControllerFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\java\\"
				+ bean.myPackage.baseControllerPackageName.replace(".", "\\"), bean.baseControllerClassName);

		this.bean = bean;
	}
	
	
	@Override
	protected void fetchSpecificImports() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeContent() throws IOException {
		// TODO Auto-generated method stub

	}

}
