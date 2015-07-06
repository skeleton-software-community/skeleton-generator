package org.sklsft.generator.bc.file.command.impl.java.controller.jsf;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.Visibility;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Property;

public class BaseSimpleJsfControllerFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public BaseSimpleJsfControllerFileWriteCommand(Bean bean) {
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
		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		javaImports.add("import org.sklsft.commons.api.exception.state.InvalidStateException;");
		javaImports.add("import " + this.bean.myPackage.filterPackageName + "." + this.bean.filterClassName + ";");
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

		createRefresh();
		createRefreshObject();
		createLoad();
		createDisplay();
		createDisplayObject();
		createCreateObject();
		createSaveObject();
		createEditObject();
		createUpdateObject();
		createDeleteObject();
		createDeleteObjectList();
		createListenSelectedObject();
		createListenSelectedObjectList();
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
		writeLine("logger.error(" + (char) 34 + "display failure : " + (char) 34 + " + e.getMessage(),e);");
		writeLine("this.displaySuccessfull = false;");
		writeLine("}");
		writeLine("return;");
		writeLine("}");

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && !property.relation.equals(RelationType.PROPERTY)) {
				writeLine("if (this.loadedFrom.equals(" + (char) 34 + property.referenceBean.className + (char) 34 + ")) {");
				writeLine("try {");
				writeLine("this." + this.bean.objectName + "List = this." + this.bean.serviceObjectName + ".load" + this.bean.className + "ListFrom" + property.capName
						+ "List(this.commonController.getSelected" + property.referenceBean.className + "IdList());");
				writeLine("} catch (Exception e) {");
				writeLine("logger.error(" + (char) 34 + "display failure : " + (char) 34 + " + e.getMessage(),e);");
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
		writeLine("} catch (Exception e) {");
		writeLine("logger.error(" + (char) 34 + "display failure : " + (char) 34 + " + e.getMessage(),e);");
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
				writeLine("this.loadedFrom = " + (char) 34 + property.referenceBean.className + (char) 34 + ";");
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
		writeLine("}");
		skipLine();
	}

	private void createCreateObject() {
		writeLine("/**");
		writeLine(" * create object");
		writeLine(" */");
		writeLine("public void create" + this.bean.className + "() {");
		writeLine("this.setDefault();");
		writeLine("try {");

		for (Property property : this.bean.getVisibleProperties()) {
			if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
				writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
			}
		}

		writeLine("this.selected" + this.bean.className + " = this." + this.bean.serviceObjectName + ".create" + this.bean.className + "();");
		writeLine("} catch (Exception e) {");
		writeLine("logger.error(" + (char) 34 + "display failure : " + (char) 34 + " + e.getMessage(),e);");
		writeLine("this.displaySuccessfull = false;");
		writeLine("}");
		writeLine("this.creationTag = true;");
		writeLine("}");
		skipLine();
	}

	private void createSaveObject() {
		writeLine("/**");
		writeLine(" * save object");
		writeLine(" */");
		writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".save" + CHAR_34 + ")");
		writeLine("public void save" + this.bean.className + "() {");
		
		writeLine("this.commonController.setSelected" + this.bean.className + "Id(" + this.bean.serviceObjectName + ".save" + this.bean.className + "(this.selected" + this.bean.className + "));");
		writeLine("this.creationTag = false;");
		writeLine("this.refresh();");
		
		writeLine("}");
		skipLine();

	}

	private void createEditObject() {
		writeLine("/**");
		writeLine(" * edit object");
		writeLine(" */");
		writeLine("public void edit" + this.bean.className + "() {");
		writeLine("this.setDefault();");
		writeLine("this.refresh" + this.bean.className + "();");
		writeLine("}");
		skipLine();
	}

	private void createUpdateObject() {
		writeLine("/**");
		writeLine(" * update object");
		writeLine(" */");
		writeLine("@AjaxMethod(" + CHAR_34 + bean.className + ".update" + CHAR_34 + ")");
		writeLine("public void update" + this.bean.className + "() {");
		
		writeLine(this.bean.serviceObjectName + ".update" + this.bean.className + "(this.selected" + this.bean.className + ");");
		writeLine("this.refresh();");
		
		writeLine("}");
		skipLine();

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
	

	private void createListenSelectedObject() {
		writeLine("/**");
		writeLine(" * listen selected object id");
		writeLine(" */");
		writeLine("public void listenSelected" + this.bean.className + "Id(ActionEvent event) {");
		writeLine("this.commonController.setSelected" + this.bean.className + "Id((Long) event.getComponent().getAttributes().get(" + (char) 34 + "selected" + this.bean.className + "Id" + (char) 34
				+ "));");
		writeLine("}");
		skipLine();
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
	
	private void createResetFlters() {
		
		writeLine("/**");
		writeLine(" * reset object datatable filter");
		writeLine(" */");
		writeLine("public void reset" + bean.filterClassName + "() {");
		writeLine("this." + bean.filterObjectName + " = new " + bean.filterClassName + "();");
		writeLine("}");
		skipLine();
	}
}