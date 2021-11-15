package org.sklsft.generator.skeletons.core.commands.components.statemanager;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class BaseStateManagerImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public BaseStateManagerImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.componentsArtefactName + File.separator + bean.myPackage.model.javaSourcesFolder + File.separator
				+ bean.myPackage.baseStateManagerImplPackageName.replace(".", File.separator), bean.baseStateManagerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
	
		javaImports.add("import org.sklsft.commons.api.exception.state.InvalidStateException;");
        javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
        
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
        writeLine(" * can save");
        writeLine(" */");
        writeLine("public boolean canSave(" + this.bean.className + " " + this.bean.objectName + ") {");
        if (bean.createEnabled) {
        	writeLine("return true;");
        } else {
        	writeLine("return false;");
        }
        writeLine("}");
        skipLine();
        writeLine("/**");
        writeLine(" * check can save");
        writeLine(" */");
        writeLine("public void checkCanSave(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canSave(" + this.bean.objectName + ")) {");        
        writeLine("throw new InvalidStateException(" + CHAR_34 + this.bean.className + ".save.invalidState" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
        	Bean currentBean = oneToOneComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * can save one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canSave" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") {");
            if (currentBean.createEnabled) {
            	writeLine("return true;");
            } else {
            	writeLine("return false;");
            }
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can save one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanSave" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") {");
            writeLine("if (!canSave" + currentBean.className + "(" + currentBean.objectName + ", " + this.bean.objectName + ")) {");        
            writeLine("throw new InvalidStateException(" + CHAR_34 + currentBean.className + ".save.invalidState" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
        	Bean currentBean = oneToManyComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * can save one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canSave" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") {");
            if (currentBean.createEnabled) {
            	writeLine("return true;");
            } else {
            	writeLine("return false;");
            }
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can save one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanSave" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") {");
            writeLine("if (!canSave" + currentBean.className + "(" + currentBean.objectName + ", " + this.bean.objectName + ")) {");        
            writeLine("throw new InvalidStateException(" + CHAR_34 + currentBean.className + ".save.invalidState" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * can update");
        writeLine(" */");
        writeLine("public boolean canUpdate(" + this.bean.className + " " + this.bean.objectName + ") {");
        if (bean.updateEnabled) {
        	writeLine("return true;");
        } else {
        	writeLine("return false;");
        }
        writeLine("}");
        skipLine();
        writeLine("/**");
        writeLine(" * check can update");
        writeLine(" */");
        writeLine("public void checkCanUpdate(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canUpdate(" + this.bean.objectName + ")) {");        
        writeLine("throw new InvalidStateException(" + CHAR_34 + this.bean.className + ".update.invalidState" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;
            writeLine("/**");
            writeLine(" * can update one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canUpdate" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            if (currentBean.updateEnabled) {
            	writeLine("return true;");
            } else {
            	writeLine("return false;");
            }
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can update one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanUpdate" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("if (!canUpdate" + currentBean.className + "(" + currentBean.objectName + ")) {");        
            writeLine("throw new InvalidStateException(" + CHAR_34 + currentBean.className + ".update.invalidState" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * can update one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canUpdate" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            if (currentBean.updateEnabled) {
            	writeLine("return true;");
            } else {
            	writeLine("return false;");
            }
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can update one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanUpdate" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("if (!canUpdate" + currentBean.className + "(" + currentBean.objectName + ")) {");        
            writeLine("throw new InvalidStateException(" + CHAR_34 + currentBean.className + ".update.invalidState" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * can delete");
        writeLine(" */");
        writeLine("public boolean canDelete(" + this.bean.className + " " + this.bean.objectName + ") {");
        if (bean.deleteEnabled) {
        	writeLine("return true;");
        } else {
        	writeLine("return false;");
        }
        writeLine("}");
        skipLine();
        writeLine("/**");
        writeLine(" * check can delete");
        writeLine(" */");
        writeLine("public void checkCanDelete(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canDelete(" + bean.objectName + ")) {");        
        writeLine("throw new InvalidStateException(" + CHAR_34 + bean.className + ".delete.invalidState" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;
            writeLine("/**");
            writeLine(" * can delete one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canDelete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            if (currentBean.deleteEnabled) {
            	writeLine("return true;");
            } else {
            	writeLine("return false;");
            }
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can delete one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanDelete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("if (!canDelete" + currentBean.className + "(" + currentBean.objectName + ")) {");        
            writeLine("throw new InvalidStateException(" + CHAR_34 + currentBean.className + ".delete.invalidState" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * can delete one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canDelete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            if (currentBean.deleteEnabled) {
            	writeLine("return true;");
            } else {
            	writeLine("return false;");
            }
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can delete one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanDelete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("if (!canDelete" + currentBean.className + "(" + currentBean.objectName + ")) {");        
            writeLine("throw new InvalidStateException(" + CHAR_34 + currentBean.className + ".delete.invalidState" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        
        writeLine("}");
    }
}
