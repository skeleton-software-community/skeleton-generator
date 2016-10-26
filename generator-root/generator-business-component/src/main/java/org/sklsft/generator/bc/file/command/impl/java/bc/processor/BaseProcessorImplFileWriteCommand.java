package org.sklsft.generator.bc.file.command.impl.java.bc.processor;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;

public class BaseProcessorImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public BaseProcessorImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseProcessorImplPackageName.replace(".", File.separator), bean.baseProcessorClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import javax.inject.Inject;");
		javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
        javaImports.add("import " + this.bean.myPackage.DAOInterfacePackageName + "." + this.bean.daoInterfaceName + ";");
        javaImports.add("import org.sklsft.commons.model.patterns.Processor;");      
        
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
        }		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseProcessorImplPackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated base processor class file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");

        writeLine("public class " + this.bean.baseProcessorClassName + " implements Processor<" + this.bean.className + ", Long> {");
        skipLine();

        writeLine("/*"); 
        writeLine(" * properties injected by spring");
        writeLine(" */");
        
        writeLine("@Inject");
        writeLine("protected " + this.bean.daoInterfaceName + " " + this.bean.daoObjectName + ";");
        skipLine();
        
        writeLine("/**");
        writeLine(" * process save");
        writeLine(" */");
        writeLine("public Long save(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("return " + this.bean.daoObjectName + ".save(" + this.bean.objectName + ");");
        writeLine("}");
        skipLine();
        
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;
            writeLine("/**");
            writeLine(" * process save one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void save" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") {");
            writeLine(this.bean.daoObjectName + ".save" + currentBean.className + "(" + this.bean.objectName + ", " + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
        	Bean currentBean = oneToManyComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * process save one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void save" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") {");
            writeLine(this.bean.daoObjectName + ".save" + currentBean.className + "(" + this.bean.objectName + ", " + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }
        

        writeLine("/**");
        writeLine(" * process update");
        writeLine(" */");
        writeLine("public void update(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("// Empty by default. Can be overridden");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;
            writeLine("/**");
            writeLine(" * process update one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void update" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("// Empty by default. Can be overridden");
            writeLine("}");
            skipLine();
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * process update one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void update" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("// Empty by default. Can be overridden");
            writeLine("}");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * process delete");
        writeLine(" */");
        writeLine("public void delete(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine(this.bean.daoObjectName + ".delete(" + this.bean.objectName + ");");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;
            writeLine("/**");
            writeLine(" * process delete one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void delete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine(this.bean.daoObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }
        
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * process delete one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void delete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine(this.bean.daoObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }

        writeLine("}");

    }
}
