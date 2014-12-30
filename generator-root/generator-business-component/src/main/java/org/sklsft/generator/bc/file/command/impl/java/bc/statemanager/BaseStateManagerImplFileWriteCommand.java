package org.sklsft.generator.bc.file.command.impl.java.bc.statemanager;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.UniqueComponent;

public class BaseStateManagerImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public BaseStateManagerImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component\\src\\main\\java\\"
				+ bean.myPackage.baseStateManagerImplPackageName.replace(".", "\\"), bean.baseStateManagerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

        javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
        javaImports.add("import " + this.bean.myPackage.model.stateExceptionPackageName + ".InvalidStateException;");

        for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
        }		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseStateManagerImplPackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated base state manager class file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");

        writeLine("public class " + this.bean.baseStateManagerClassName + " {");
        skipLine();
        
        writeLine("/**");
        writeLine(" * check before save");
        writeLine(" */");
        writeLine("public void checkBeforeSave(" + this.bean.className + " " + this.bean.objectName + ") throws InvalidStateException {");
        writeLine("}");
        skipLine();

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
        	Bean currentBean = oneToManyComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * check before save one to many compoennt " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkBeforeSave" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") throws InvalidStateException {");
            writeLine("}");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * check before update");
        writeLine(" */");
        writeLine("public void checkBeforeUpdate(" + this.bean.className + " " + this.bean.objectName + ", " + this.bean.viewClassName + " " + this.bean.viewObjectName + ") throws InvalidStateException {");
        writeLine("}");
        skipLine();

        for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            writeLine("/**");
            writeLine(" * check before update unique component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkBeforeUpdate" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.viewClassName + " " + currentBean.viewObjectName + ") throws InvalidStateException {");
            writeLine("}");
            skipLine();
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * check before update one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkBeforeUpdate" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.viewClassName + " " + currentBean.viewObjectName + ") throws InvalidStateException {");
            writeLine("}");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * check before delete");
        writeLine(" */");
        writeLine("public void checkBeforeDelete(" + this.bean.className + " " + this.bean.objectName + ") throws InvalidStateException {");
        writeLine("}");
        skipLine();

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * check before delete one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkBeforeDelete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") throws InvalidStateException {");
            writeLine("}");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * set default");
        writeLine(" */");
        writeLine("public void setDefault(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("}");
        skipLine();

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * set default on to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void setDefault" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("}");
            skipLine();
        }        

        writeLine("}");

    }
}
