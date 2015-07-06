package org.sklsft.generator.bc.file.command.impl.java.controller.jsf;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.Visibility;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Property;
import org.sklsft.generator.model.om.UniqueComponent;

public class BaseJsfControllerFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public BaseJsfControllerFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\java\\"
				+ bean.myPackage.baseControllerPackageName.replace(".", "\\"), bean.baseControllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.ArrayList;");
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import javax.faces.event.ActionEvent;");
		javaImports.add("import org.sklsft.commons.mvc.annotations.AjaxMethod;");
		javaImports.add("import org.sklsft.commons.api.exception.repository.ObjectNotFoundException;");
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".CommonController;");
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".BaseController;");
		javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
		javaImports.add("import " + this.bean.myPackage.filterPackageName + "." + this.bean.filterClassName + ";");

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
			javaImports.add("import " + currentBean.myPackage.filterPackageName + "." + currentBean.filterClassName + ";");
		}

		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		javaImports.add("import org.sklsft.commons.api.exception.state.InvalidStateException;");
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseControllerPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated base controller class file");
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.baseControllerClassName + " extends BaseController {");
		skipLine();

		writeLine("/*");
		writeLine(" * properties injected by spring");
		writeLine(" */");
		writeLine("@Autowired");
		writeLine("protected " + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ";");
		writeLine("@Autowired");
		writeLine("protected CommonController commonController;");
		skipLine();

		writeLine("/*");
		writeLine(" * view");
		writeLine(" */");
		writeLine("protected List<" + this.bean.viewClassName + "> " + this.bean.objectName + "List;");
		writeLine("protected " + this.bean.filterClassName + " " + this.bean.filterObjectName + " = new " + this.bean.filterClassName + "();");
		writeLine("protected " + this.bean.viewClassName + " selected" + this.bean.className + ";");

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;
			writeLine("protected " + currentBean.viewClassName + " selected" + uniqueComponent.referenceBean.className + ";");
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("protected List<" + currentBean.viewClassName + "> " + currentBean.objectName + "List;");
			writeLine("protected " + currentBean.filterClassName + " " + currentBean.filterObjectName + " = new " + currentBean.filterClassName + "();");
			writeLine("protected " + currentBean.viewClassName + " selected" + currentBean.className + ";");
		}
		skipLine();

		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");
		writeLine("public List<" + this.bean.viewClassName + "> get" + this.bean.className + "List() {");
		writeLine("return " + this.bean.objectName + "List;");
		writeLine("}");
		skipLine();

		writeLine("public void set" + this.bean.className + "List(List<" + this.bean.viewClassName + "> " + this.bean.objectName + "List) {");
		writeLine("this." + this.bean.objectName + "List = " + this.bean.objectName + "List;");
		writeLine("}");
		skipLine();
		
		writeLine("public " + this.bean.filterClassName + " get" + this.bean.filterClassName + "() {");
		writeLine("return " + this.bean.filterObjectName + ";");
		writeLine("}");
		skipLine();

		writeLine("public void set" + this.bean.filterClassName + "(" + this.bean.filterClassName + " " + this.bean.filterObjectName + ") {");
		writeLine("this." + this.bean.filterObjectName + " = " + this.bean.filterObjectName + ";");
		writeLine("}");
		skipLine();

		writeLine("public " + this.bean.viewClassName + " getSelected" + this.bean.className + "() {");
		writeLine("return selected" + this.bean.className + ";");
		writeLine("}");
		skipLine();

		writeLine("public void setSelected" + this.bean.className + "(" + this.bean.viewClassName + " selected" + this.bean.className + ") {");
		writeLine("this.selected" + this.bean.className + " = selected" + this.bean.className + ";");
		writeLine("}");
		skipLine();

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;
			writeLine("public " + currentBean.viewClassName + " getSelected" + currentBean.className + "() {");
			writeLine("return selected" + currentBean.className + ";");
			writeLine("}");
			skipLine();

			writeLine("public void setSelected" + currentBean.className + "(" + currentBean.viewClassName + " selected" + currentBean.className + ") {");
			writeLine("this.selected" + currentBean.className + " = selected" + currentBean.className + ";");
			writeLine("}");
			skipLine();
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("public List<" + currentBean.viewClassName + "> get" + currentBean.className + "List() {");
			writeLine("return " + currentBean.objectName + "List;");
			writeLine("}");
			skipLine();

			writeLine("public void set" + currentBean.className + "List(List<" + currentBean.viewClassName + "> " + currentBean.objectName + "List) {");
			writeLine("this." + currentBean.objectName + "List = " + currentBean.objectName + "List;");
			writeLine("}");
			skipLine();
			
			writeLine("public " + currentBean.filterClassName + " get" + currentBean.filterClassName + "() {");
			writeLine("return " + currentBean.filterObjectName + ";");
			writeLine("}");
			skipLine();

			writeLine("public void set" + currentBean.filterClassName + "(" + currentBean.filterClassName + " " + currentBean.filterObjectName + ") {");
			writeLine("this." + currentBean.filterObjectName + " = " + currentBean.filterObjectName + ";");
			writeLine("}");
			skipLine();

			writeLine("public " + currentBean.viewClassName + " getSelected" + currentBean.className + "() {");
			writeLine("return selected" + currentBean.className + ";");
			writeLine("}");
			skipLine();

			writeLine("public void setSelected" + currentBean.className + "(" + currentBean.viewClassName + " selected" + currentBean.className + ") {");
			writeLine("this.selected" + currentBean.className + " = selected" + currentBean.className + ";");
			writeLine("}");
			skipLine();
		}

		createRefresh();
		createRefreshObject();
		createLoad();
		createDisplay();
		createDisplayObject();
		createCreateObject();
		createCreateOneToManyComponent();
		createSaveObject();
		createSaveOneToManyComponent();
		createEditObject();
		createEditOneToManyComponent();
		createUpdateObject();
		createUpdateUniqueComponent();
		createUpdateOneToManyComponent();
		createDeleteObject();
		createDeleteOneToManyComponent();
		createDeleteObjectList();
		createDeleteOneToManyComponentList();
		createListenSelectedObject();
		createListenSelectedOneToManyComponent();
		createListenSelectedObjectList();
		createListenSelectedOneToManyComponentList();
		createResetFlters();

		writeLine("}");

	}


	private void createRefresh() {
		writeLine("/**");
		writeLine(" * refresh object list");
		writeLine(" */");
		writeLine("public void refresh() {");
		writeLine("if (this.loadedFrom == null){");
		writeLine("try {");
		writeLine("this." + this.bean.objectName + "List = this." + this.bean.serviceObjectName + ".load" + this.bean.className + "List();");
		writeLine("} catch (Exception e) {");
		writeLine("logger.error(" + CHAR_34 + "display failure : " + CHAR_34 + " + e.getMessage(),e);");
		writeLine("this.displaySuccessfull = false;");
		writeLine("}");
		writeLine("return;");
		writeLine("}");

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && !property.relation.equals(RelationType.PROPERTY)) {
				writeLine("if (this.loadedFrom.equals(" + CHAR_34 + property.referenceBean.className + CHAR_34 + ")) {");
				writeLine("try {");
				writeLine("this." + this.bean.objectName + "List = this." + this.bean.serviceObjectName + ".load" + this.bean.className + "ListFrom" + property.capName
						+ "List(this.commonController.getSelected" + property.referenceBean.className + "IdList());");
				writeLine("} catch (Exception e) {");
				writeLine("logger.error(" + CHAR_34 + "display failure : " + CHAR_34 + " + e.getMessage(),e);");
				writeLine("this.displaySuccessfull = false;");
				writeLine("}");
				writeLine("return;");
				writeLine("}");
			}
		}

		writeLine("}");
		skipLine();
	}

	private void createRefreshObject() {
		writeLine("/**");
		writeLine(" * refresh object");
		writeLine(" */");
		writeLine("public void refresh" + this.bean.className + "() {");
		writeLine("try {");

		for (Property property : this.bean.getVisibleProperties()) {
			if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
				writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
			}
		}

		writeLine("this.selected" + this.bean.className + " = this." + this.bean.serviceObjectName + ".load" + this.bean.className + "(this.commonController.getSelected" + this.bean.className
				+ "Id());");

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("this." + currentBean.objectName + "List = this." + this.bean.serviceObjectName + ".load" + currentBean.className + "List(this.commonController.getSelected"
					+ this.bean.className + "Id());");
		}

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;

			for (Property property : currentBean.getVisibleProperties()) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
				}
			}

			writeLine("this.selected" + currentBean.className + " = this." + this.bean.serviceObjectName + ".load" + currentBean.className + "(this.commonController.getSelected" + this.bean.className
					+ "Id());");
		}

		writeLine("} catch (Exception e) {");
		writeLine("logger.error(" + CHAR_34 + "display failure : " + CHAR_34 + " + e.getMessage(),e);");
		writeLine("this.displaySuccessfull = false;");
		writeLine("}");
		writeLine("}");
		skipLine();
	}

	private void createLoad() {
		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("public String load() {");
		writeLine("this.commonController.setDefault();");
		writeLine("this.reset" + bean.filterClassName + "();");
		writeLine("this.setDefault();");
		writeLine("this.loadedFrom = null;");
		writeLine("this.refresh();");
		writeLine("return SUCCESS;");
		writeLine("}");
		skipLine();

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && !property.relation.equals(RelationType.PROPERTY)) {

				writeLine("/**");
				writeLine(" * load object list form list of " + property.referenceBean.objectName);
				writeLine(" */");
				writeLine("public String loadFrom" + property.referenceBean.className + "() {");
				writeLine("this.setDefault();");
				writeLine("if (this.commonController.getSelected" + property.referenceBean.className + "IdList() == null) {");
				writeLine("return FAILURE;");
				writeLine("}");
				writeLine("this.loadedFrom = " + CHAR_34 + property.referenceBean.className + CHAR_34 + ";");
				writeLine("this.refresh();");
				writeLine("return SUCCESS;");
				writeLine("}");
				skipLine();
			}
		}
	}

	private void createDisplay() {
		writeLine("/**");
		writeLine(" * display object list");
		writeLine(" */");
		writeLine("public String display() {");
		writeLine("this.setDefault();");
		writeLine("this.refresh();");
		writeLine("return SUCCESS;");
		writeLine("}");
		skipLine();

	}

	private void createDisplayObject() {
		writeLine("/**");
		writeLine(" * display object");
		writeLine(" */");
		writeLine("public void display" + this.bean.className + "() {");
		writeLine("this.setDefault();");
		writeLine("this.refresh" + this.bean.className + "();");
		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("this.reset" + currentBean.filterClassName + "();");
		}
		
		writeLine("}");
		skipLine();

	}

	private void createCreateObject() {
		writeLine("/**");
		writeLine(" * create object");
		writeLine(" */");
		writeLine("public String create" + this.bean.className + "() {");
		writeLine("this.setDefault();");
		writeLine("try {");

		for (Property property : this.bean.getVisibleProperties()) {
			if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
				writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
			}
		}

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;

			for (Property property : currentBean.getVisibleProperties()) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
				}
			}
		}

		writeLine("this.selected" + this.bean.className + " = this." + this.bean.serviceObjectName + ".create" + this.bean.className + "();");
		writeLine("} catch (Exception e) {");
		writeLine("logger.error(" + CHAR_34 + "display failure : " + CHAR_34 + " + e.getMessage(),e);");
		writeLine("this.displaySuccessfull = false;");
		writeLine("return SUCCESS;");
		writeLine("}");
		writeLine("this.creationTag = true;");
		writeLine("return SUCCESS;");
		writeLine("}");
		skipLine();

	}

	private void createCreateOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * create one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void create" + currentBean.className + "() {");
			writeLine("this.setDefault();");
			writeLine("try {");

			for (Property property : currentBean.getVisibleProperties()) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
				}
			}

			writeLine("this.selected" + currentBean.className + " = this." + this.bean.serviceObjectName + ".create" + currentBean.className + "();");
			writeLine("} catch (Exception e) {");
			writeLine("logger.error(" + CHAR_34 + "display failure : " + CHAR_34 + " + e.getMessage(),e);");
			writeLine("this.displaySuccessfull = false;");
			writeLine("}");
			writeLine("this.creationTag = true;");
			writeLine("}");
			skipLine();
		}
	}

	private void createSaveObject() {
		writeLine("/**");
		writeLine(" * save object");
		writeLine(" */");
		writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".save" + CHAR_34 + ")");
		writeLine("public void save" + this.bean.className + "() {");
		
		writeLine("this.commonController.setSelected" + this.bean.className + "Id(" + this.bean.serviceObjectName + ".save" + this.bean.className + "(this.selected" + this.bean.className + "));");
		writeLine("this.creationTag = false;");
		writeLine("this.refresh" + this.bean.className + "();");
		
		
		writeLine("}");
		skipLine();

	}

	private void createSaveOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * save one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".save" + CHAR_34 + ")");
			writeLine("public void save" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".save" + currentBean.className + "(this.selected" + currentBean.className + ",this.commonController.getSelected" + this.bean.className + "Id());");
			writeLine("this.creationTag = false;");
			writeLine("this.refresh" + this.bean.className + "();");
			writeLine("}");
			skipLine();
		}
	}

	private void createEditObject() {
		writeLine("/**");
		writeLine(" * edit object");
		writeLine(" */");
		writeLine("public String edit" + this.bean.className + "() {");
		writeLine("this.setDefault();");
		writeLine("this.refresh" + this.bean.className + "();");
		writeLine("return SUCCESS;");
		writeLine("}");
		skipLine();
	}

	private void createEditOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * edit one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void edit" + currentBean.className + "() {");
			writeLine("this.setDefault();");
			writeLine("try{");

			for (Property property : currentBean.getVisibleProperties()) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
				}
			}

			writeLine("this.selected" + this.bean.className + " = this." + this.bean.serviceObjectName + ".load" + this.bean.className + "(this.commonController.getSelected" + this.bean.className
					+ "Id());");
			writeLine("this.selected" + currentBean.className + " = this." + this.bean.serviceObjectName + ".load" + currentBean.className + "(this.commonController.getSelected"
					+ currentBean.className + "Id(), this.commonController.getSelected" + this.bean.className + "Id());");
			writeLine("} catch (Exception e) {");
			writeLine("logger.error(" + CHAR_34 + "display failure : " + CHAR_34 + " + e.getMessage(),e);");
			writeLine("this.displaySuccessfull = false;");
			writeLine("}");
			writeLine("}");
			skipLine();

		}
	}

	private void createUpdateObject() {
		writeLine("/**");
		writeLine(" * update object");
		writeLine(" */");
		writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".update" + CHAR_34 + ")");
		writeLine("public void update" + this.bean.className + "() {");
		
		writeLine(this.bean.serviceObjectName + ".update" + this.bean.className + "(this.selected" + this.bean.className + ");");
		writeLine("this.refresh" + this.bean.className + "();");
		
		writeLine("}");
		skipLine();

	}

	private void createUpdateUniqueComponent() {
		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;

			writeLine("/**");
			writeLine(" * update unique component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".update" + CHAR_34 + ")");
			writeLine("public void update" + currentBean.className + "() {");
			
			writeLine(this.bean.serviceObjectName + ".update" + currentBean.className + "(this.selected" + currentBean.className + ",this.commonController.getSelected" + this.bean.className
					+ "Id());");
			writeLine("this.refresh" + this.bean.className + "();");
			writeLine("}");
			skipLine();
		}
	}

	private void createUpdateOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * update one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".update" + CHAR_34 + ")");
			writeLine("public void update" + currentBean.className + "() {");
			writeLine("this.setDefault();");			
			writeLine(this.bean.serviceObjectName + ".update" + currentBean.className + "(this.selected" + currentBean.className + ",this.commonController.getSelected" + this.bean.className
					+ "Id());");
			writeLine("this.refresh" + this.bean.className + "();");
			writeLine("}");
			skipLine();
		}
	}

	private void createDeleteObject() {
		writeLine("/**");
		writeLine(" * delete object");
		writeLine(" */");
		writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".delete" + CHAR_34 + ")");
		writeLine("public void delete" + this.bean.className + "() {");
		
		writeLine(this.bean.serviceObjectName + ".delete" + this.bean.className + "(this.commonController.getSelected" + this.bean.className + "Id());");
		writeLine("this.refresh();");
		
		writeLine("}");
		skipLine();

	}

	private void createDeleteOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".delete" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".delete" + currentBean.className + "(this.commonController.getSelected" + currentBean.className + "Id(),this.commonController.getSelected"
					+ this.bean.className + "Id());");
			writeLine("this.refresh" + this.bean.className + "();");
			writeLine("}");
			skipLine();
		}
	}

	private void createDeleteObjectList() {
		writeLine("/**");
		writeLine(" * delete object list");
		writeLine(" */");
		writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".deleteList" + CHAR_34 + ")");
		writeLine("public void delete" + this.bean.className + "List() {");
		writeLine("if (this.commonController.getSelected" + this.bean.className + "IdList() == null) {");
		writeLine("return;");
		writeLine("}");
		
		writeLine(this.bean.serviceObjectName + ".delete" + this.bean.className + "List(this.commonController.getSelected" + this.bean.className + "IdList());");
		writeLine("this.refresh();");
		
		writeLine("}");
		skipLine();
	}

	private void createDeleteOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * delete one to many component list " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".deleteList" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "List() {");
			writeLine("if (this.commonController.getSelected" + currentBean.className + "IdList() == null) {");
			writeLine("return;");
			writeLine("}");
			
			writeLine(this.bean.serviceObjectName + ".delete" + currentBean.className + "List(this.commonController.getSelected" + currentBean.className + "IdList(),this.commonController.getSelected"
					+ this.bean.className + "Id());");
			writeLine("this.refresh" + this.bean.className + "();");
			
			writeLine("}");
			skipLine();
		}
	}

	private void createListenSelectedObject() {
		writeLine("/**");
		writeLine(" * listen selected object id");
		writeLine(" */");
		writeLine("public void listenSelected" + this.bean.className + "Id(ActionEvent event) {");
		writeLine("this.commonController.setSelected" + this.bean.className + "Id((Long) event.getComponent().getAttributes().get(" + CHAR_34 + "selected" + this.bean.className + "Id" + CHAR_34
				+ "));");
		writeLine("}");
		skipLine();
	}

	private void createListenSelectedOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * listen selected one to many component id " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void listenSelected" + currentBean.className + "Id(ActionEvent event) {");
			writeLine("this.commonController.setSelected" + currentBean.className + "Id((Long) event.getComponent().getAttributes().get(" + CHAR_34 + "selected" + currentBean.className + "Id"
					+ CHAR_34 + "));");
			writeLine("}");
			skipLine();
		}
	}

	private void createListenSelectedObjectList() {
		writeLine("/**");
		writeLine(" * listen selected object id list");
		writeLine(" */");
		writeLine("public void listenSelected" + this.bean.className + "IdList(ActionEvent event) {");
		writeLine("this.commonController.setSelected" + this.bean.className + "IdList(new ArrayList<Long>());");
		writeLine("if (" + this.bean.objectName + "List != null){");
		writeLine("for (" + this.bean.viewClassName + " " + this.bean.objectName + ":" + this.bean.objectName + "List){");
		writeLine("if (" + this.bean.objectName + ".getSelected()){");
		writeLine("this.commonController.getSelected" + this.bean.className + "IdList().add(" + this.bean.objectName + ".getId());");
		writeLine("}");
		writeLine("}");
		writeLine("}");
		writeLine("if (this.commonController.getSelected" + this.bean.className + "IdList().isEmpty()) {");
		writeLine("this.commonController.setSelected" + this.bean.className + "IdList(null);");
		writeLine("displayError(EMPTY_SELECTION);");
		writeLine("}");
		writeLine("}");
		skipLine();
	}

	private void createListenSelectedOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * listen selected one to many component id list " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void listenSelected" + currentBean.className + "IdList(ActionEvent event) {");
			writeLine("this.commonController.setSelected" + currentBean.className + "IdList(new ArrayList<Long>());");
			writeLine("if (" + currentBean.objectName + "List != null){");
			writeLine("for (" + currentBean.viewClassName + " " + currentBean.objectName + ":" + currentBean.objectName + "List){");
			writeLine("if (" + currentBean.objectName + ".getSelected()){");
			writeLine("this.commonController.getSelected" + currentBean.className + "IdList().add(" + currentBean.objectName + ".getId());");
			writeLine("}");
			writeLine("}");
			writeLine("}");
			writeLine("if (this.commonController.getSelected" + currentBean.className + "IdList().isEmpty()) {");
			writeLine("this.commonController.setSelected" + currentBean.className + "IdList(null);");
			writeLine("displayError(EMPTY_SELECTION);");
			writeLine("}");
			writeLine("}");
			skipLine();
		}
	}
	
	private void createResetFlters() {
		
		writeLine("/**");
		writeLine(" * reset object datatable filter");
		writeLine(" */");
		writeLine("public void reset" + bean.filterClassName + "() {");
		writeLine("this." + bean.filterObjectName + " = new " + bean.filterClassName + "();");
		writeLine("}");
		skipLine();
		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * reset one to many component " + bean.filterClassName + " datatable filter");
			writeLine(" */");
			writeLine("public void reset" + currentBean.filterClassName + "() {");
			writeLine("this." + currentBean.filterObjectName + " = new " + currentBean.filterClassName + "();");
			writeLine("}");
			skipLine();
		}
	}
}
