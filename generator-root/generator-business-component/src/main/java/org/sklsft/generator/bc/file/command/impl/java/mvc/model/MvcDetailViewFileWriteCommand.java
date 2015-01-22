package org.sklsft.generator.bc.file.command.impl.java.mvc.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToMany;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.UniqueComponent;

public class MvcDetailViewFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public MvcDetailViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\java\\"
				+ bean.myPackage.detailViewPackageName.replace(".", "\\"), bean.detailViewClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		javaImports.add("import java.io.Serializable;");
		javaImports.add("import java.util.List;");
		javaImports.add("import org.springframework.stereotype.Component;");
        javaImports.add("import org.springframework.context.annotation.Scope;");
        javaImports.add("import org.springframework.web.context.WebApplicationContext;");
		
		javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
		
		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
			javaImports.add("import " + currentBean.myPackage.filterPackageName + "." + currentBean.filterClassName + ";");
		}
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
			javaImports.add("import " + currentBean.myPackage.filterPackageName + "." + currentBean.filterClassName + ";");
		}
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.detailViewPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated detail view class file");
		writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Component");
        writeLine("@Scope(value = " + CHAR_34 + "view" + CHAR_34 + ")");
		writeLine("public class " + this.bean.detailViewClassName + " implements Serializable {");
        skipLine();

        writeLine("private static final long serialVersionUID = 1L;");
        skipLine();

		writeLine("/*");
		writeLine(" * properties");
		writeLine(" */");
		writeLine("private Long selected" + this.bean.className + "Id;");
		skipLine();
		writeLine("private " + this.bean.viewClassName + " selected" + this.bean.className + ";");
		skipLine();
		
		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;
			writeLine("private " + currentBean.viewClassName + " selected" + uniqueComponent.referenceBean.className + ";");
			skipLine();
		}
		
		

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("private List<" + currentBean.viewClassName + "> " + currentBean.objectName + "List;");
			writeLine("private " + currentBean.filterClassName + " " + currentBean.filterObjectName + " = new " + currentBean.filterClassName + "();");
			writeLine("private " + currentBean.viewClassName + " new" + currentBean.className + ";");
			writeLine("private " + currentBean.viewClassName + " selected" + currentBean.className + ";");
			skipLine();
		}
		
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			writeLine("private List<" + currentBean.viewClassName + "> " + currentBean.objectName + "List;");
			writeLine("private " + currentBean.filterClassName + " " + currentBean.filterObjectName + " = new " + currentBean.filterClassName + "();");
			writeLine("private " + currentBean.viewClassName + " new" + currentBean.className + ";");
			skipLine();
		}
		

		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");
		writeLine("public Long getSelected" + this.bean.className + "Id() {");
		writeLine("return selected" + this.bean.className + "Id;");
		writeLine("}");
		writeLine("public void setSelected" + this.bean.className + "Id(Long selected" + this.bean.className + "Id) {");
		writeLine("this.selected" + this.bean.className + "Id = selected" + this.bean.className + "Id;");
		writeLine("}");
		skipLine();
		
		writeLine("public " + this.bean.viewClassName + " getSelected" + this.bean.className + "() {");
		writeLine("return selected" + this.bean.className + ";");
		writeLine("}");
		writeLine("public void setSelected" + this.bean.className + "(" + this.bean.viewClassName + " selected" + this.bean.className + ") {");
		writeLine("this.selected" + this.bean.className + " = selected" + this.bean.className + ";");
		writeLine("}");
		skipLine();

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;
			writeLine("public " + currentBean.viewClassName + " getSelected" + currentBean.className + "() {");
			writeLine("return selected" + currentBean.className + ";");
			writeLine("}");

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

			writeLine("public void set" + currentBean.className + "List(List<" + currentBean.viewClassName + "> " + currentBean.objectName + "List) {");
			writeLine("this." + currentBean.objectName + "List = " + currentBean.objectName + "List;");
			writeLine("}");
			skipLine();
			
			writeLine("public " + currentBean.filterClassName + " get" + currentBean.filterClassName + "() {");
			writeLine("return " + currentBean.filterObjectName + ";");
			writeLine("}");

			writeLine("public void set" + currentBean.filterClassName + "(" + currentBean.filterClassName + " " + currentBean.filterObjectName + ") {");
			writeLine("this." + currentBean.filterObjectName + " = " + currentBean.filterObjectName + ";");
			writeLine("}");
			skipLine();

			writeLine("public " + currentBean.viewClassName + " getNew" + currentBean.className + "() {");
			writeLine("return new" + currentBean.className + ";");
			writeLine("}");

			writeLine("public void setNew" + currentBean.className + "(" + currentBean.viewClassName + " new" + currentBean.className + ") {");
			writeLine("this.new" + currentBean.className + " = new" + currentBean.className + ";");
			writeLine("}");
			skipLine();
			
			writeLine("public " + currentBean.viewClassName + " getSelected" + currentBean.className + "() {");
			writeLine("return selected" + currentBean.className + ";");
			writeLine("}");

			writeLine("public void setSelected" + currentBean.className + "(" + currentBean.viewClassName + " selected" + currentBean.className + ") {");
			writeLine("this.selected" + currentBean.className + " = selected" + currentBean.className + ";");
			writeLine("}");
			skipLine();
		}
		
		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			writeLine("public List<" + currentBean.viewClassName + "> get" + currentBean.className + "List() {");
			writeLine("return " + currentBean.objectName + "List;");
			writeLine("}");

			writeLine("public void set" + currentBean.className + "List(List<" + currentBean.viewClassName + "> " + currentBean.objectName + "List) {");
			writeLine("this." + currentBean.objectName + "List = " + currentBean.objectName + "List;");
			writeLine("}");
			skipLine();
			
			writeLine("public " + currentBean.filterClassName + " get" + currentBean.filterClassName + "() {");
			writeLine("return " + currentBean.filterObjectName + ";");
			writeLine("}");

			writeLine("public void set" + currentBean.filterClassName + "(" + currentBean.filterClassName + " " + currentBean.filterObjectName + ") {");
			writeLine("this." + currentBean.filterObjectName + " = " + currentBean.filterObjectName + ";");
			writeLine("}");
			skipLine();

			writeLine("public " + currentBean.viewClassName + " getNew" + currentBean.className + "() {");
			writeLine("return new" + currentBean.className + ";");
			writeLine("}");

			writeLine("public void setNew" + currentBean.className + "(" + currentBean.viewClassName + " new" + currentBean.className + ") {");
			writeLine("this.new" + currentBean.className + " = new" + currentBean.className + ";");
			writeLine("}");
			skipLine();
			
		}

		writeNotOverridableContent();

		writeLine("}");

	}
}
