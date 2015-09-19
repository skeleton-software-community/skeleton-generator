package org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces4;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.metadata.Visibility;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToMany;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Property;
import org.sklsft.generator.model.om.UniqueComponent;

public class BaseJsfDetailControllerFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public BaseJsfDetailControllerFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\java\\"
				+ bean.myPackage.baseControllerPackageName.replace(".", "\\"), bean.baseDetailControllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.ArrayList;");
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import javax.annotation.PostConstruct;");
		javaImports.add("import org.sklsft.commons.mvc.annotations.AjaxMethod;");

		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".CommonController;");
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".BaseController;");

		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		
		javaImports.add("import " + this.bean.myPackage.detailViewPackageName + "." + this.bean.detailViewClassName + ";");
	
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.filterPackageName + "." + currentBean.filterClassName + ";");
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
		}
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			javaImports.add("import " + currentBean.myPackage.filterPackageName + "." + currentBean.filterClassName + ";");
			javaImports.add("import " + currentBean.myPackage.serviceInterfacePackageName + "." + currentBean.serviceInterfaceName + ";");
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
		}
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseControllerPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated base detail controller class file");
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.baseDetailControllerClassName + " extends BaseController {");
		skipLine();

		writeLine("/*");
		writeLine(" * services injected by spring");
		writeLine(" */");
		writeLine("@Autowired");
		writeLine("protected " + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ";");
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			
			writeLine("@Autowired");
			writeLine("protected " + currentBean.serviceInterfaceName + " " + currentBean.serviceObjectName + ";");
		}
		
		writeLine("@Autowired");
		writeLine("protected CommonController commonController;");
		skipLine();

		writeLine("/*");
		writeLine(" * view");
		writeLine(" */");
		writeLine("@Autowired");
		writeLine("protected " + this.bean.detailViewClassName + " " + this.bean.detailViewObjectName + ";");
		skipLine();
		
		

		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");
		writeLine("public " + this.bean.detailViewClassName + " get" + this.bean.detailViewClassName + "() {");
		writeLine("return " + this.bean.detailViewObjectName + ";");
		writeLine("}");
		writeLine("public void set" + this.bean.detailViewClassName + "(" + this.bean.detailViewClassName + " " + this.bean.detailViewObjectName + ") {");
		writeLine("this." + this.bean.detailViewObjectName + " = " + this.bean.detailViewObjectName + ";");
		writeLine("}");
		skipLine();

		
		createLoadObject();
		createLoadUniqueComponent();
		createLoadOneToManyComponent();
		createLoadOneToMany();
		createUpdateObject();
		createCreateOneToManyComponent();
		createCreateOneToMany();
		createSaveOneToManyComponent();
		createSaveOneToMany();
		createEditOneToManyComponent();
		createUpdateOneToManyComponent();
		createDeleteOneToManyComponent();
		createDeleteOneToMany();
		createDeleteOneToManyComponentList();
		createDeleteOneToManyList();
		createUpdateUniqueComponent();
		
		createResetFlters();

		writeLine("}");

	}


	private void createLoadObject() {
		
		writeLine("/**");
		writeLine(" * init");
		writeLine(" */");
		writeLine("@PostConstruct");
		writeLine("public void init() {");
		writeLine("String id = getParameter(" + CHAR_34 + "id" + CHAR_34 + ");");
		writeLine("if (id != null) {");
		writeLine(bean.detailViewObjectName + ".setSelected" + bean.className + "Id(Long.valueOf(id));");
		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("this.reset" + currentBean.filterClassName + "();");
		}
		
		writeLine("load();");
		writeLine("}");
		writeLine("}");
		
		writeLine("/**");
		writeLine(" * load object");
		writeLine(" */");
		writeLine("public void load() {");
		
		for (Property property : this.bean.getVisibleProperties()) {
			if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
				writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
			}
		}
		
		writeLine(this.bean.detailViewObjectName + ".setSelected" + this.bean.className + "(this." + this.bean.serviceObjectName + ".load" + this.bean.className + "(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "Id()));");

		writeLine("}");
		skipLine();
	}
	
	
	private void createLoadUniqueComponent(){
		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * load unique component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void load" + currentBean.className + "() {");
			
			for (Property property : currentBean.getVisibleProperties()) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
				}
			}

			writeLine(this.bean.detailViewObjectName + ".setSelected" + currentBean.className + "(this." + this.bean.serviceObjectName + ".load" + currentBean.className + "(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "Id()));");

			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createLoadOneToManyComponent(){
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * load one to many component " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("public void load" + currentBean.className + "List() {");

			writeLine(this.bean.detailViewObjectName + ".set" + currentBean.className + "List(this." + this.bean.serviceObjectName + ".load" + currentBean.className + "List(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "Id()));");
			
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createLoadOneToMany(){
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			
			writeLine("/**");
			writeLine(" * load one to many " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("public void load" + currentBean.className + "List() {");

			writeLine(this.bean.detailViewObjectName + ".set" + currentBean.className + "List(this." + currentBean.serviceObjectName + ".load" + currentBean.className + "ListFrom" + oneToMany.referenceProperty.capName + "(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "Id()));");
			
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
		writeLine(this.bean.serviceObjectName + ".update" + this.bean.className + "(" + bean.detailViewObjectName + ".getSelected" + this.bean.className + "());");
		writeLine("load();");
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
			writeLine(this.bean.serviceObjectName + ".update" + currentBean.className + "(" + bean.detailViewObjectName + ".getSelected" + currentBean.className + "(), this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "Id());");
			writeLine("load" + currentBean.className + "();");
			writeLine("}");
			skipLine();
		}
	}


	private void createCreateOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * create one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void create" + currentBean.className + "() {");

			for (Property property : currentBean.getVisibleProperties()) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
				}
			}

			writeLine(bean.detailViewObjectName + ".setNew" + currentBean.className + "(this." + this.bean.serviceObjectName + ".create" + currentBean.className + "());");
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createCreateOneToMany() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;

			writeLine("/**");
			writeLine(" * create one to many " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void create" + currentBean.className + "() {");

			for (Property property : oneToMany.getVisibleProperties()) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + property.comboBoxBean.properties.get(1).capName + "List();");
				}
			}

			writeLine(bean.detailViewObjectName + ".setNew" + currentBean.className + "(this." + currentBean.serviceObjectName + ".create" + currentBean.className + "());");
			writeLine("}");
			skipLine();
		}
	}


	private void createSaveOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * save one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".save" + CHAR_34 + ")");
			writeLine("public void save" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".save" + currentBean.className + "(" + bean.detailViewObjectName + ".getNew" + currentBean.className + "(), this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "Id());");
			writeLine("load" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createSaveOneToMany() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;

			writeLine("/**");
			writeLine(" * save one to many " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".save" + CHAR_34 + ")");
			writeLine("public void save" + currentBean.className + "() {");
			writeLine(currentBean.serviceObjectName + ".save" + currentBean.className + "From" + bean.className + "(" + bean.detailViewObjectName + ".getNew" + currentBean.className + "(), this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "Id());");
			writeLine("load" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}

	
	private void createEditOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * edit one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void edit" + currentBean.className + "(Long id) {");
			writeLine(bean.detailViewObjectName + ".setSelected" + currentBean.className + "(" + this.bean.serviceObjectName + ".load" + currentBean.className + "(id, this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "Id()));");
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
			writeLine(this.bean.serviceObjectName + ".update" + currentBean.className + "(" + bean.detailViewObjectName + ".getSelected" + currentBean.className + "());");
			writeLine("load" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}


	private void createDeleteOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".delete" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "(Long id) {");
			writeLine(this.bean.serviceObjectName + ".delete" + currentBean.className + "(id);");
			writeLine("load" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}
	
	private void createDeleteOneToMany() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;

			writeLine("/**");
			writeLine(" * delete one to many " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".delete" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "(Long id) {");
			writeLine(currentBean.serviceObjectName + ".delete" + currentBean.className + "(id);");
			writeLine("load" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createDeleteOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
		
			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".deleteList" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "List() {");
			writeLine("List<Long> ids = new ArrayList<>();");
			writeLine("for (" + currentBean.viewClassName + " " + currentBean.viewObjectName + ":" + bean.objectName + "DetailView.get" + currentBean.className + "List()) {");
			writeLine("if (" + currentBean.viewObjectName + ".getSelected()) {");
			writeLine("ids.add(" + currentBean.viewObjectName + ".getId());");
			writeLine("}");
			writeLine("}");
			writeLine(this.bean.serviceObjectName + ".delete" + currentBean.className + "List(ids);");
			writeLine("load" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}
	
	private void createDeleteOneToManyList() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
		
			writeLine("/**");
			writeLine(" * delete one to many " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".deleteList" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "List() {");
			writeLine("List<Long> ids = new ArrayList<>();");
			writeLine("for (" + currentBean.viewClassName + " " + currentBean.viewObjectName + ":" + bean.objectName + "DetailView.get" + currentBean.className + "List()) {");
			writeLine("if (" + currentBean.viewObjectName + ".getSelected()) {");
			writeLine("ids.add(" + currentBean.viewObjectName + ".getId());");
			writeLine("}");
			writeLine("}");
			writeLine(currentBean.serviceObjectName + ".delete" + currentBean.className + "List(ids);");
			writeLine("load" + currentBean.className + "List();");
			writeLine("}");
			skipLine();
		}
	}

	
	private void createResetFlters() {
		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * reset one to many component " + currentBean.filterClassName + " datatable filter");
			writeLine(" */");
			writeLine("public void reset" + currentBean.filterClassName + "() {");
			writeLine(this.bean.detailViewObjectName + ".set" + currentBean.filterClassName + "(new " + currentBean.filterClassName + "());");
			writeLine("}");
			skipLine();
		}
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			
			writeLine("/**");
			writeLine(" * reset one to many " + currentBean.filterClassName + " datatable filter");
			writeLine(" */");
			writeLine("public void reset" + currentBean.filterClassName + "() {");
			writeLine(this.bean.detailViewObjectName + ".set" + currentBean.filterClassName + "(new " + currentBean.filterClassName + "());");
			writeLine("}");
			skipLine();
		}
	}
}
