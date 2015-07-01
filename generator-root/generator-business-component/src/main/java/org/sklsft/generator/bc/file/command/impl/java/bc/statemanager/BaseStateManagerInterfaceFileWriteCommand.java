package org.sklsft.generator.bc.file.command.impl.java.bc.statemanager;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToMany;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.OneToOne;
import org.sklsft.generator.model.om.UniqueComponent;

public class BaseStateManagerInterfaceFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public BaseStateManagerInterfaceFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component\\src\\main\\java\\"
				+ bean.myPackage.baseStateManagerInterfacePackageName.replace(".", "\\"), bean.baseStateManagerInterfaceName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

        javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");     
        javaImports.add("import org.sklsft.commons.api.exception.state.InvalidStateException;");
        
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

        for (OneToMany oneToMany : this.bean.oneToManyList)
        {
            Bean currentBean = oneToMany.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
        }

        for (OneToOne oneToOne : this.bean.oneToOneList)
        {
            Bean currentBean = oneToOne.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
        }
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseStateManagerInterfacePackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated base state manager interface file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");

        writeLine("public interface " + this.bean.baseStateManagerInterfaceName + " {");
        skipLine();
        
        writeLine("/**");
        writeLine(" * check before save");
        writeLine(" */");
        writeLine("void checkBeforeSave(" + this.bean.className + " " + this.bean.objectName + ") throws InvalidStateException;");
        skipLine();

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
        	Bean currentBean = oneToManyComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * check before save one to many compoennt " + currentBean.className);
            writeLine(" */");
            writeLine("void checkBeforeSave" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") throws InvalidStateException;");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * check before update");
        writeLine(" */");
        writeLine("void checkBeforeUpdate(" + this.bean.className + " " + this.bean.objectName + ", " + this.bean.viewClassName + " " + this.bean.viewObjectName + ") throws InvalidStateException;");
        skipLine();

        for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            writeLine("/**");
            writeLine(" * check before update unique component " + currentBean.className);
            writeLine(" */");
            writeLine("void checkBeforeUpdate" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.viewClassName + " " + currentBean.viewObjectName + ") throws InvalidStateException;");
            skipLine();
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * check before update one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("void checkBeforeUpdate" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.viewClassName + " " + currentBean.viewObjectName + ") throws InvalidStateException;");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * check before delete");
        writeLine(" */");
        writeLine("void checkBeforeDelete(" + this.bean.className + " " + this.bean.objectName + ") throws InvalidStateException;");
        skipLine();

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * check before delete one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("void checkBeforeDelete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") throws InvalidStateException;");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * set default");
        writeLine(" */");
        writeLine("void setDefault(" + this.bean.className + " " + this.bean.objectName + ");");
        skipLine();

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * set default on to many component " + currentBean.className);
            writeLine(" */");
            writeLine("void setDefault" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ");");
            skipLine();
        }        

        writeLine("}");

    }
}
