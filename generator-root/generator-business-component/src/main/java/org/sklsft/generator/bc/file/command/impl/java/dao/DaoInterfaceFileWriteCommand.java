package org.sklsft.generator.bc.file.command.impl.java.dao;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;

public class DaoInterfaceFileWriteCommand extends JavaFileWriteCommand {
	
	private Bean bean;

	/*
	 * constructor
	 */
	public DaoInterfaceFileWriteCommand(Bean bean) {

		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-repository\\src\\main\\java\\"
				+ bean.myPackage.DAOInterfacePackageName.replace(".", "\\"), bean.daoInterfaceName);

		this.bean = bean;

	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import " + bean.myPackage.baseDAOInterfacePackageName + "." + this.bean.baseDaoInterfaceName + ";");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + bean.myPackage.DAOInterfacePackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated dao interface file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("public interface " + bean.daoInterfaceName + " extends " + bean.baseDaoInterfaceName + " {");
        skipLine();

        this.writeNotOverridableContent();

        writeLine("}");
	}
}
