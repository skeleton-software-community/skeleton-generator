package org.sklsft.generator.skeletons.core.commands.dao;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class DaoImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public DaoImplFileWriteCommand(Bean bean) {

		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-repository" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.DAOImplPackageName.replace(".", File.separator), bean.daoClassName);

		this.bean = bean;

	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import org.springframework.stereotype.Repository;");
		javaImports.add("import " + bean.myPackage.DAOInterfacePackageName + "." + this.bean.daoInterfaceName + ";");
        javaImports.add("import " + bean.myPackage.baseDAOImplPackageName + "." + this.bean.baseDaoClassName + ";");
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.DAOImplPackageName + ";");
        skipLine();

        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated dao class file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("@Repository");
        writeLine("public class " + this.bean.daoClassName + " extends " + this.bean.baseDaoClassName + " implements " + this.bean.daoInterfaceName + " {");
        skipLine();

        this.writeNotOverridableContent();

        writeLine("}");
	}
}

