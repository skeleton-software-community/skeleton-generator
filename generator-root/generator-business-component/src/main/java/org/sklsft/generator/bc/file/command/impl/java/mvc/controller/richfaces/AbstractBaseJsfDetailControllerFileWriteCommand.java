package org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.metadata.DetailMode;
import org.sklsft.generator.model.metadata.Visibility;

public abstract class AbstractBaseJsfDetailControllerFileWriteCommand extends JavaFileWriteCommand {

	protected Bean bean;

	public AbstractBaseJsfDetailControllerFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\java\\"
				+ bean.myPackage.baseControllerPackageName.replace(".", "\\"), bean.baseDetailControllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.ArrayList;");
		javaImports.add("import javax.annotation.PostConstruct;");
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import org.sklsft.commons.mvc.ajax.AjaxMethodTemplate;");
		javaImports.add("import org.sklsft.commons.mvc.annotations.AjaxMethod;");

		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".CommonController;");
		javaImports.add("import " + this.bean.myPackage.model.controllerPackageName + ".BaseController;");

		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		
		javaImports.add("import " + this.bean.myPackage.detailViewPackageName + "." + this.bean.detailViewClassName + ";");
	
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.filterPackageName + "." + currentBean.basicViewBean.filterClassName + ";");
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.basicViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.fullViewBean.className + ";");
		}
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			javaImports.add("import " + currentBean.myPackage.filterPackageName + "." + currentBean.basicViewBean.filterClassName + ";");			
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.basicViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.fullViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.serviceInterfacePackageName + "." + currentBean.serviceInterfaceName + ";");
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
		
		
		createInit();
		createLoadObject();
		createLoadOneToOneComponent();
		createLoadOneToManyComponent();
		createLoadOneToMany();		
		
		createSaveOneToOneComponent();
		createCreateOneToManyComponent();
		createCreateOneToMany();
		createSaveOneToManyComponent();
		createSaveOneToMany();
		
		createUpdateObject();
		createEditOneToMany();
		createUpdateOneToMany();
		createEditOneToManyComponent();
		createUpdateOneToOneComponent();
		createUpdateOneToManyComponent();
		
		createDeleteOneToOneComponent();
		createDeleteOneToManyComponent();		
		createDeleteOneToMany();
		createDeleteOneToManyComponentList();
		createDeleteOneToManyList();
		
		createResetFlters();

		writeLine("}");

	}


	protected abstract void createInit();
	

	private void createLoadObject() {
		
		writeLine("/**");
		writeLine(" * load object");
		writeLine(" */");
		writeLine("public void load() {");
	
		for (Property property : this.bean.fullViewBean.properties) {
			if (property.comboBoxBean != null) {
				writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
			}
		}
		
		writeLine(this.bean.detailViewObjectName + ".setSelected" + this.bean.className + "(this." + this.bean.serviceObjectName + ".load" + this.bean.className + "(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId()));");

		writeLine("}");
		skipLine();
		skipLine();		
	}
	
	
	private void createLoadOneToOneComponent(){
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * load one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void load" + currentBean.className + "() {");
			
			for (Property property : currentBean.fullViewBean.properties) {
				if (property.comboBoxBean != null && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
				}
			}

			writeLine(this.bean.detailViewObjectName + ".setSelected" + currentBean.className + "(this." + this.bean.serviceObjectName + ".load" + currentBean.className + "(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId()));");

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

			writeLine("this.reset" + currentBean.basicViewBean.filterClassName + "();");
			
			writeLine(this.bean.detailViewObjectName + ".set" + currentBean.className + "List(this." + this.bean.serviceObjectName + ".load" + currentBean.className + "List(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId()));");
			
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

			writeLine("this.reset" + currentBean.basicViewBean.filterClassName + "();");
			
			writeLine(this.bean.detailViewObjectName + ".set" + currentBean.className + "List(this." + currentBean.serviceObjectName + ".load" + currentBean.className + "ListFrom" + oneToMany.referenceProperty.capName + "(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId()));");
			
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
	
	
	private void createUpdateOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * update one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".update" + CHAR_34 + ")");
			writeLine("public void update" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".update" + currentBean.className + "(" + bean.detailViewObjectName + ".getSelected" + currentBean.className + "(), this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId());");
			writeLine("load" + currentBean.className + "();");
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createSaveOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * save one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".save" + CHAR_34 + ")");
			writeLine("public void save" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".save" + currentBean.className + "(" + bean.detailViewObjectName + ".getSelected" + currentBean.className + "(), this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId());");
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

			for (Property property : currentBean.fullViewBean.properties) {
				if (property.comboBoxBean != null && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
				}
			}

			writeLine(bean.detailViewObjectName + ".setSelected" + currentBean.className + "(this." + this.bean.serviceObjectName + ".create" + currentBean.className + "());");
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

			for (Property property : oneToMany.fullViewBean.properties) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
				}
			}

			writeLine(bean.detailViewObjectName + ".setSelected" + currentBean.className + "(this." + currentBean.serviceObjectName + ".create" + currentBean.className + "());");
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
			writeLine(this.bean.serviceObjectName + ".save" + currentBean.className + "(" + bean.detailViewObjectName + ".getSelected" + currentBean.className + "(), this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId());");
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
			if (currentBean.detailMode.equals(DetailMode.MODAL)) {
				writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".save" + CHAR_34 + ")");
				writeLine("public void save" + currentBean.className + "() {");
				writeLine(currentBean.serviceObjectName + ".save" + currentBean.className + "From" + bean.className + "(" + bean.detailViewObjectName + ".getSelected" + currentBean.className + "(), this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId());");
				writeLine("load" + currentBean.className + "List();");
				writeLine("}");
				skipLine();
			} else {
				writeLine("public void save" + currentBean.className + "() {");
				writeLine("executeAjaxMethod(" + CHAR_34 + currentBean.className + ".save" + CHAR_34 + ", new AjaxMethodTemplate() {");
				writeLine("@Override");
				writeLine("public Object execute() {");
				writeLine("return " + currentBean.serviceObjectName + ".save" + currentBean.className + "From" + bean.className + "(" + bean.detailViewObjectName + ".getSelected" + currentBean.className + "(), " + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId());");
				writeLine("}");
				writeLine("@Override");
				writeLine("public void redirectOnComplete(Object result) {");
				writeLine("redirect(" + CHAR_34 + "/sections/" + currentBean.myPackage.name + "/" + currentBean.className.toLowerCase() + "/" + currentBean.className + "Details.jsf?id=" + CHAR_34 + " + result);");
				writeLine("}");
				writeLine("});");
				writeLine("}");
			}
		}
	}
	
	
	private void createEditOneToMany() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;

			writeLine("/**");
			writeLine(" * edit one to many " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void edit" + currentBean.className + "(Long id) {");
			
			for (Property property : oneToMany.fullViewBean.properties) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
				}
			}
			
			writeLine(bean.detailViewObjectName + ".setSelected" + currentBean.className + "(" + currentBean.serviceObjectName + ".load" + currentBean.className + "(id));");
			writeLine("}");
			skipLine();
		}
	}
	
	
	private void createUpdateOneToMany() {
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;

			writeLine("/**");
			writeLine(" * update one to many " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".update" + CHAR_34 + ")");
			writeLine("public void update" + currentBean.className + "() {");
			writeLine(currentBean.serviceObjectName + ".update" + currentBean.className + "(" + bean.detailViewObjectName + ".getSelected" + currentBean.className + "());");
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
			
			for (Property property : currentBean.fullViewBean.properties) {
				if (property.comboBoxBean != null && !property.visibility.equals(Visibility.NOT_VISIBLE) && property.editable) {
					writeLine("this.commonController.load" + property.comboBoxBean.className + "Options();");
				}
			}
			
			writeLine(bean.detailViewObjectName + ".setSelected" + currentBean.className + "(" + this.bean.serviceObjectName + ".load" + currentBean.className + "(id, this." + bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId()));");
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
	
	
	private void createDeleteOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * delete one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@AjaxMethod(" + CHAR_34 + currentBean.className + ".delete" + CHAR_34 + ")");
			writeLine("public void delete" + currentBean.className + "() {");
			writeLine(this.bean.serviceObjectName + ".delete" + currentBean.className + "(this." + this.bean.detailViewObjectName + ".getSelected" + this.bean.className + "().getId());");
			writeLine("load" + currentBean.className + "();");
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
			writeLine("for (" + currentBean.basicViewBean.className + " " + currentBean.objectName + ":" + bean.detailViewObjectName + ".get" + currentBean.className + "List()) {");
			writeLine("if (" + currentBean.objectName + ".getSelected()) {");
			writeLine("ids.add(" + currentBean.objectName + ".getId());");
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
			writeLine("for (" + currentBean.basicViewBean.className + " " + currentBean.objectName + ":" + bean.detailViewObjectName + ".get" + currentBean.className + "List()) {");
			writeLine("if (" + currentBean.objectName + ".getSelected()) {");
			writeLine("ids.add(" + currentBean.objectName + ".getId());");
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
			writeLine(" * reset one to many component " + currentBean.basicViewBean.filterClassName + " datatable filter");
			writeLine(" */");
			writeLine("public void reset" + currentBean.basicViewBean.filterClassName + "() {");
			writeLine(this.bean.detailViewObjectName + ".set" + currentBean.basicViewBean.filterClassName + "(new " + currentBean.basicViewBean.filterClassName + "());");
			writeLine("}");
			skipLine();
		}
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			
			writeLine("/**");
			writeLine(" * reset one to many " + currentBean.basicViewBean.filterClassName + " datatable filter");
			writeLine(" */");
			writeLine("public void reset" + currentBean.basicViewBean.filterClassName + "() {");
			writeLine(this.bean.detailViewObjectName + ".set" + currentBean.basicViewBean.filterClassName + "(new " + currentBean.basicViewBean.filterClassName + "());");
			writeLine("}");
			skipLine();
		}
	}
}
