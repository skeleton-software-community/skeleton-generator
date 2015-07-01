package org.sklsft.generator.bc.file.command.impl.java.controller.jsf;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;

public class JsfCommonControllerFileWriteCommand extends JavaFileWriteCommand {

	private Project project;

	public JsfCommonControllerFileWriteCommand(Project project) {
		super(project.workspaceFolder + "\\" + project.projectName + "-webapp\\src\\main\\java\\" + project.model.controllerPackageName.replace(".", "\\"),
				"CommonController");

		this.project = project;
	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.ArrayList;");
		javaImports.add("import javax.faces.model.SelectItem;");
		javaImports.add("import org.springframework.stereotype.Component;");
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import org.springframework.context.annotation.Scope;");
		javaImports.add("import org.springframework.web.context.WebApplicationContext;");

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					javaImports.add("import " + bean.myPackage.serviceInterfacePackageName + "." + bean.serviceInterfaceName + ";");
				}
			}
		}

	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.project.model.controllerPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated common controller class file");
		writeLine(" * <br/>used for session navigation context");
		writeLine(" * <br/>write modifications between specific code marks");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Component");
		writeLine("@Scope(value=WebApplicationContext.SCOPE_SESSION)");
		writeLine("public class CommonController {");
		skipLine();

		writeLine("/*");
		writeLine(" * properties injected by spring");
		writeLine(" */");

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("@Autowired");
					writeLine("private " + bean.serviceInterfaceName + " " + bean.serviceObjectName + ";");
				}
			}
		}
		skipLine();

		writeLine("/*");
		writeLine(" * view navigation");
		writeLine(" */");
		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					writeLine("private Long selected" + bean.className + "Id;");
					writeLine("private List<Long> selected" + bean.className + "IdList;");

					for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList) {
						Bean currentBean = oneToManyComponent.referenceBean;
						writeLine("private Long selected" + currentBean.className + "Id;");

						writeLine("private List<Long> selected" + currentBean.className + "IdList;");

					}
				}
			}
		}
		skipLine();

		writeLine("/*");
		writeLine(" * select items");
		writeLine(" */");
		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("private List<SelectItem>" + bean.objectName + bean.properties.get(1).capName + "List;");
				}
			}
		}
		skipLine();

		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					writeLine("public Long getSelected" + bean.className + "Id() {");
					writeLine("return selected" + bean.className + "Id;");
					writeLine("}");
					skipLine();

					writeLine("public void setSelected" + bean.className + "Id(Long selected" + bean.className + "Id) {");
					writeLine("this.selected" + bean.className + "Id = selected" + bean.className + "Id;");
					writeLine("}");
					skipLine();

					writeLine("public List<Long> getSelected" + bean.className + "IdList() {");
					writeLine("return selected" + bean.className + "IdList;");
					writeLine("}");
					skipLine();

					writeLine("public void setSelected" + bean.className + "IdList(List<Long> selected" + bean.className + "IdList) {");
					writeLine("this.selected" + bean.className + "IdList = selected" + bean.className + "IdList;");
					writeLine("}");
					skipLine();

					for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList) {
						Bean currentBean = oneToManyComponent.referenceBean;
						writeLine("public Long getSelected" + currentBean.className + "Id() {");
						writeLine("return selected" + currentBean.className + "Id;");
						writeLine("}");
						skipLine();

						writeLine("public void setSelected" + currentBean.className + "Id(Long selected" + currentBean.className + "Id) {");
						writeLine("this.selected" + currentBean.className + "Id = selected" + currentBean.className + "Id;");
						writeLine("}");
						skipLine();

						writeLine("public List<Long> getSelected" + currentBean.className + "IdList() {");
						writeLine("return selected" + currentBean.className + "IdList;");
						writeLine("}");
						skipLine();

						writeLine("public void setSelected" + currentBean.className + "IdList(List<Long> selected" + currentBean.className + "IdList) {");
						writeLine("this.selected" + currentBean.className + "IdList = selected" + currentBean.className + "IdList;");
						writeLine("}");
						skipLine();
					}
				}
			}
		}

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("public List<SelectItem> get" + bean.className + bean.properties.get(1).capName + "List() {");
					writeLine("return " + bean.objectName + bean.properties.get(1).capName + "List;");
					writeLine("}");
					skipLine();

					writeLine("public void set" + bean.className + bean.properties.get(1).capName + "List(List<SelectItem> " + bean.objectName + bean.properties.get(1).capName + "List) {");
					writeLine("this." + bean.objectName + bean.properties.get(1).capName + "List = " + bean.objectName + bean.properties.get(1).capName + "List;");
					writeLine("}");
					skipLine();
				}
			}
		}

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("public " + bean.serviceInterfaceName + " get" + bean.serviceInterfaceName + "() {");
					writeLine("return " + bean.serviceObjectName + ";");
					writeLine("}");
					skipLine();

					writeLine("public void set" + bean.serviceInterfaceName + "(" + bean.serviceInterfaceName + " " + bean.serviceObjectName + ") {");
					writeLine("this." + bean.serviceObjectName + " = " + bean.serviceObjectName + ";");
					writeLine("}");
					skipLine();
				}
			}
		}


		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent && bean.hasComboBox) {
					writeLine("/**");
					writeLine(" * load combobox items for " + bean.className);
					writeLine(" */");
					writeLine("public void load" + bean.className + bean.properties.get(1).capName + "List() {");
					writeLine("this." + bean.objectName + bean.properties.get(1).capName + "List = new ArrayList<SelectItem>();");
					writeLine("this." + bean.objectName + bean.properties.get(1).capName + "List.add(new SelectItem(null," + (char) 34 + (char) 34 + "));");
					writeLine("List<" + bean.properties.get(1).beanDataType + "> " + bean.objectName + bean.properties.get(1).capName + "List = this." + bean.serviceObjectName + ".get"
							+ bean.className + bean.properties.get(1).capName + "List ();");
					writeLine("if (" + bean.objectName + bean.properties.get(1).capName + "List != null){");
					writeLine("for (" + bean.properties.get(1).beanDataType + " " + bean.objectName + bean.properties.get(1).capName + ":" + bean.objectName + bean.properties.get(1).capName
							+ "List){");
					writeLine("this." + bean.objectName + bean.properties.get(1).capName + "List.add(new SelectItem(" + bean.objectName + bean.properties.get(1).capName + "));");
					writeLine("}");
					writeLine("}");
					writeLine("}");
					skipLine();
				}
			}
		}

		writeLine("/**");
		writeLine(" * set Default");
		writeLine(" */");
		writeLine("public void setDefault(){");

		for (Package myPackage : this.project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					writeLine("this.selected" + bean.className + "Id = null;");
					writeLine("this.selected" + bean.className + "IdList = null;");

					for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList) {
						Bean currentBean = oneToManyComponent.referenceBean;
						writeLine("this.selected" + currentBean.className + "Id = null;");
						writeLine("this.selected" + currentBean.className + "IdList = null;");

					}
				}
			}
		}

		writeLine("}");
		skipLine();

		this.writeNotOverridableContent();

		writeLine("}");
	}
}
