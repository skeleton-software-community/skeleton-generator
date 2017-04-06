package org.sklsft.generator.skeletons.core.commands.bc.rightmanager;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class BaseRightsManagerImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public BaseRightsManagerImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseRightsManagerImplPackageName.replace(".", File.separator), bean.baseRightsManagerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
	
		javaImports.add("import org.sklsft.commons.api.exception.rights.AccessDeniedException;");
		javaImports.add("import org.sklsft.commons.api.exception.rights.OperationDeniedException;");

        javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
        javaImports.add("import org.sklsft.commons.model.patterns.RightsManager;");
        
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

		writeLine("package " + this.bean.myPackage.baseRightsManagerImplPackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();

        writeLine("/**");
        writeLine(" * auto generated base rights manager class file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");

        writeLine("public class " + this.bean.baseRightsManagerClassName + " implements RightsManager<" + this.bean.className + ", Long> {");
        skipLine();
        
        
        
        
        
        
        
        /***********************************************************/
        /***************************Access**************************/
        /***********************************************************/
        
        writeLine("/**");
        writeLine(" * can access all");
        writeLine(" */");
        writeLine("public boolean canAccess() {");
        writeLine("return true;");        
        writeLine("}");
        skipLine();
        writeLine("/**");
        writeLine(" * check can access all");
        writeLine(" */");
        writeLine("public void checkCanAccess() {");
        writeLine("if (!canAccess()) {");        
        writeLine("throw new AccessDeniedException(" + CHAR_34 + this.bean.className + ".accessDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();
        
        writeLine("/**");
        writeLine(" * can access");
        writeLine(" */");
        writeLine("public boolean canAccess(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("return true;");        
        writeLine("}");
        skipLine();
        writeLine("/**");
        writeLine(" * check can access");
        writeLine(" */");
        writeLine("public void checkCanAccess(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canAccess(" + this.bean.objectName + ")) {");        
        writeLine("throw new AccessDeniedException(" + CHAR_34 + this.bean.className + ".accessDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();
        

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
        	Bean currentBean = oneToOneComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * can access one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canAccess" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("return canAccess(" + currentBean.objectName + ".get" + currentBean.parentBean.className + "());");
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can access one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanAccess" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("if (!canAccess" + currentBean.className + "(" + currentBean.objectName + ")) {");        
            writeLine("throw new AccessDeniedException(" + CHAR_34 + currentBean.className + ".accessDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
        	Bean currentBean = oneToManyComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * can access one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canAccess" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("return canAccess(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can access one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanAccess" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canAccess" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new AccessDeniedException(" + CHAR_34 + currentBean.className + ".accessDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        /***********************************************************/
        
        
        
        /***********************************************************/
        /***************************Create**************************/
        /***********************************************************/
        writeLine("/**");
        writeLine(" * can create");
        writeLine(" */");
        writeLine("public boolean canCreate() {");
        writeLine("return true;");        
        writeLine("}");
        skipLine();
        writeLine("/**");
        writeLine(" * check can create");
        writeLine(" */");
        writeLine("public void checkCanCreate() {");
        writeLine("if (!canCreate()) {");        
        writeLine("throw new OperationDeniedException(" + CHAR_34 + this.bean.className + ".create.operationDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
        	Bean currentBean = oneToOneComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * can create one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canCreate" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("return canUpdate(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can create one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanCreate" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canCreate" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new OperationDeniedException(" + CHAR_34 + currentBean.className + ".create.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
        	Bean currentBean = oneToManyComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * can create one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canCreate" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("return canUpdate(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can create one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanCreate" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canCreate" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new OperationDeniedException(" + CHAR_34 + currentBean.className + ".create.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        /***********************************************************/
        
        
        
        
        
        
        
        
        writeLine("/**");
        writeLine(" * can save");
        writeLine(" */");
        writeLine("public boolean canSave(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("return true;");        
        writeLine("}");
        skipLine();
        writeLine("/**");
        writeLine(" * check can save");
        writeLine(" */");
        writeLine("public void checkCanSave(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canSave(" + this.bean.objectName + ")) {");        
        writeLine("throw new OperationDeniedException(" + CHAR_34 + this.bean.className + ".save.operationDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
        	Bean currentBean = oneToOneComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * can save one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canSave" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("return canUpdate(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can save one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanSave" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canSave" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new OperationDeniedException(" + CHAR_34 + currentBean.className + ".save.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
        	Bean currentBean = oneToManyComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * can save one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canSave" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("return canUpdate(" + this.bean.objectName + ");");
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can save one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanSave" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ") {");
            writeLine("if (!canSave" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new OperationDeniedException(" + CHAR_34 + currentBean.className + ".save.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * can update");
        writeLine(" */");
        writeLine("public boolean canUpdate(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("return true;");        
        writeLine("}");
        skipLine();
        writeLine("/**");
        writeLine(" * check can update");
        writeLine(" */");
        writeLine("public void checkCanUpdate(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canUpdate(" + this.bean.objectName + ")) {");        
        writeLine("throw new OperationDeniedException(" + CHAR_34 + this.bean.className + ".update.operationDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;
            writeLine("/**");
            writeLine(" * can update one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canUpdate" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("return canUpdate(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can update one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanUpdate" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canUpdate" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new OperationDeniedException(" + CHAR_34 + currentBean.className + ".update.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * can update one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canUpdate" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("return canUpdate(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can update one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanUpdate" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canUpdate" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new OperationDeniedException(" + CHAR_34 + currentBean.className + ".update.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }

        writeLine("/**");
        writeLine(" * can delete");
        writeLine(" */");
        writeLine("public boolean canDelete(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("return true;");
        writeLine("}");
        skipLine();
        writeLine("/**");
        writeLine(" * check can delete");
        writeLine(" */");
        writeLine("public void checkCanDelete(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canDelete(" + bean.objectName + ")) {");        
        writeLine("throw new OperationDeniedException(" + CHAR_34 + bean.className + ".delete.operationDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;
            writeLine("/**");
            writeLine(" * can delete one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canDelete" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("return canUpdate(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can delete one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanDelete" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canDelete" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new OperationDeniedException(" + CHAR_34 + currentBean.className + ".delete.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * can delete one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canDelete" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("return canUpdate(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can delete one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("public void checkCanDelete" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canDelete" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new OperationDeniedException(" + CHAR_34 + currentBean.className + ".delete.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        
        writeLine("}");
    }
}
