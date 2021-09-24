package org.sklsft.generator.skeletons.core.commands.components.rightmanager;

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
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.componentsArtefactName + File.separator + bean.myPackage.model.javaSourcesFolder + File.separator
				+ bean.myPackage.baseRightsManagerImplPackageName.replace(".", File.separator), bean.baseRightsManagerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
	
		javaImports.add("import org.sklsft.commons.api.exception.rights.AccessDeniedException;");

        javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
        
        javaImports.add("import org.springframework.security.access.prepost.PreAuthorize;");
        
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
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

        writeLine("public class " + this.bean.baseRightsManagerClassName + " {");
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
        if (bean.accessRoles!=null && bean.accessRoles.readRole!=null) {
        	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.readRole + "')\")");
        }
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
        if (bean.accessRoles!=null && bean.accessRoles.readRole!=null) {
        	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.readRole + "')\")");
        }
        writeLine("public void checkCanAccess(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canAccess(" + this.bean.objectName + ")) {");        
        writeLine("throw new AccessDeniedException(" + CHAR_34 + this.bean.className + ".accessDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();
        

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
        	Bean currentBean = oneToOneComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * can access one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canAccess" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("return canAccess(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can access one to one component " + currentBean.className);
            writeLine(" */");
            if (bean.accessRoles!=null && bean.accessRoles.readRole!=null) {
            	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.readRole + "')\")");
            }
            writeLine("public void checkCanAccess" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canAccess" + currentBean.className + "(" + bean.objectName + ")) {");        
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
            if (bean.accessRoles!=null && bean.accessRoles.readRole!=null) {
            	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.readRole + "')\")");
            }
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
        if (bean.accessRoles!=null && bean.accessRoles.createRole!=null) {
        	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.createRole + "')\")");
        }
        writeLine("public void checkCanCreate() {");
        writeLine("if (!canCreate()) {");        
        writeLine("throw new AccessDeniedException(" + CHAR_34 + this.bean.className + ".create.operationDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
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
            if (bean.accessRoles!=null && bean.accessRoles.createRole!=null) {
            	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.createRole + "')\")");
            }
            writeLine("public void checkCanCreate" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canCreate" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new AccessDeniedException(" + CHAR_34 + currentBean.className + ".create.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
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
            if (bean.accessRoles!=null && bean.accessRoles.createRole!=null) {
            	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.createRole + "')\")");
            }
            writeLine("public void checkCanCreate" + currentBean.className + "(" + bean.className + " " + bean.objectName + ") {");
            writeLine("if (!canCreate" + currentBean.className + "(" + bean.objectName + ")) {");        
            writeLine("throw new AccessDeniedException(" + CHAR_34 + currentBean.className + ".create.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        /***********************************************************/
        
        
        
        
        /***********************************************************/
        /***************************Save****************************/
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
        if (bean.accessRoles!=null && bean.accessRoles.saveRole!=null) {
        	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.saveRole + "')\")");
        }
        writeLine("public void checkCanSave(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canSave(" + this.bean.objectName + ")) {");        
        writeLine("throw new AccessDeniedException(" + CHAR_34 + this.bean.className + ".save.operationDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
        	Bean currentBean = oneToOneComponent.referenceBean;
        	writeLine("/**");
            writeLine(" * can save one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canSave" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") {");
            writeLine("return canUpdate(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can save one to one component " + currentBean.className);
            writeLine(" */");
            if (bean.accessRoles!=null && bean.accessRoles.saveRole!=null) {
            	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.saveRole + "')\")");
            }
            writeLine("public void checkCanSave" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") {");
            writeLine("if (!canSave" + currentBean.className + "(" + currentBean.objectName + ", " + this.bean.objectName + ")) {");        
            writeLine("throw new AccessDeniedException(" + CHAR_34 + currentBean.className + ".save.operationDenied" + CHAR_34 + ");");
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
            writeLine("return canUpdate(" + bean.objectName + ");");
            writeLine("}");
            skipLine();
        	writeLine("/**");
            writeLine(" * check can save one to many component " + currentBean.className);
            writeLine(" */");
            if (bean.accessRoles!=null && bean.accessRoles.saveRole!=null) {
            	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.saveRole + "')\")");
            }
            writeLine("public void checkCanSave" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + "," + this.bean.className + " " + this.bean.objectName + ") {");
            writeLine("if (!canSave" + currentBean.className + "(" + currentBean.objectName + ", " + this.bean.objectName + ")) {");        
            writeLine("throw new AccessDeniedException(" + CHAR_34 + currentBean.className + ".save.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        /***********************************************************/
        
        
        /***********************************************************/
        /***************************Update**************************/
        /***********************************************************/        
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
        if (bean.accessRoles!=null && bean.accessRoles.updateRole!=null) {
        	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.updateRole + "')\")");
        }
        writeLine("public void checkCanUpdate(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canUpdate(" + this.bean.objectName + ")) {");        
        writeLine("throw new AccessDeniedException(" + CHAR_34 + this.bean.className + ".update.operationDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;
            writeLine("/**");
            writeLine(" * can update one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canUpdate" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("return canUpdate(" + currentBean.objectName + "." + oneToOneComponent.referenceProperty.getterName + "());");
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can update one to one component " + currentBean.className);
            writeLine(" */");
            if (bean.accessRoles!=null && bean.accessRoles.updateRole!=null) {
            	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.updateRole + "')\")");
            }
            writeLine("public void checkCanUpdate" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("if (!canUpdate" + currentBean.className + "(" + currentBean.objectName + ")) {");        
            writeLine("throw new AccessDeniedException(" + CHAR_34 + currentBean.className + ".update.operationDenied" + CHAR_34 + ");");
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
            writeLine("return canUpdate(" + currentBean.objectName + "." + oneToManyComponent.referenceProperty.getterName + "());");
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can update one to many component " + currentBean.className);
            writeLine(" */");
            if (bean.accessRoles!=null && bean.accessRoles.updateRole!=null) {
            	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.updateRole + "')\")");
            }
            writeLine("public void checkCanUpdate" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("if (!canUpdate" + currentBean.className + "(" + currentBean.objectName + ")) {");        
            writeLine("throw new AccessDeniedException(" + CHAR_34 + currentBean.className + ".update.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        /***********************************************************/
        
        
        /***********************************************************/
        /***************************Delete**************************/
        /***********************************************************/

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
        if (bean.accessRoles!=null && bean.accessRoles.deleteRole!=null) {
        	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.deleteRole + "')\")");
        }
        writeLine("public void checkCanDelete(" + this.bean.className + " " + this.bean.objectName + ") {");
        writeLine("if (!canDelete(" + bean.objectName + ")) {");        
        writeLine("throw new AccessDeniedException(" + CHAR_34 + bean.className + ".delete.operationDenied" + CHAR_34 + ");");
        writeLine("}");
        writeLine("}");
        skipLine();

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
        	Bean currentBean = oneToOneComponent.referenceBean;
            writeLine("/**");
            writeLine(" * can delete one to one component " + currentBean.className);
            writeLine(" */");
            writeLine("public boolean canDelete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("return canUpdate(" + currentBean.objectName + "." + oneToOneComponent.referenceProperty.getterName + "());");
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can delete one to one component " + currentBean.className);
            writeLine(" */");
            if (bean.accessRoles!=null && bean.accessRoles.deleteRole!=null) {
            	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.deleteRole + "')\")");
            }
            writeLine("public void checkCanDelete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("if (!canDelete" + currentBean.className + "(" + currentBean.objectName + ")) {");        
            writeLine("throw new AccessDeniedException(" + CHAR_34 + currentBean.className + ".delete.operationDenied" + CHAR_34 + ");");
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
            writeLine("return canUpdate(" + currentBean.objectName + "." + oneToManyComponent.referenceProperty.getterName + "());");
            writeLine("}");
            skipLine();
            writeLine("/**");
            writeLine(" * check can delete one to many component " + currentBean.className);
            writeLine(" */");
            if (bean.accessRoles!=null && bean.accessRoles.deleteRole!=null) {
            	writeLine("@PreAuthorize(\"hasRole('" + bean.accessRoles.deleteRole + "')\")");
            }
            writeLine("public void checkCanDelete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
            writeLine("if (!canDelete" + currentBean.className + "(" + currentBean.objectName + ")) {");        
            writeLine("throw new AccessDeniedException(" + CHAR_34 + currentBean.className + ".delete.operationDenied" + CHAR_34 + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
        
        writeLine("}");
    }
}
