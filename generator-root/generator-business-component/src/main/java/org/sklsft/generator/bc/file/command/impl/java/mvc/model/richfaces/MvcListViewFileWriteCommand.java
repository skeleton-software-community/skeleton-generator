package org.sklsft.generator.bc.file.command.impl.java.mvc.model.richfaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;

public class MvcListViewFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public MvcListViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.listViewPackageName.replace(".", File.separator), bean.listViewClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.io.Serializable;");
		javaImports.add("import java.util.List;");
		javaImports.add("import org.springframework.stereotype.Component;");
        javaImports.add("import org.springframework.context.annotation.Scope;");
        javaImports.add("import org.springframework.web.context.WebApplicationContext;");
		
		javaImports.add("import " + this.bean.myPackage.basicViewsPackageName + "." + this.bean.basicViewBean.className + ";");
		javaImports.add("import " + this.bean.myPackage.filtersPackageName + "." + this.bean.basicViewBean.filterClassName + ";");
		javaImports.add("import " + this.bean.myPackage.fullViewsPackageName + "." + this.bean.fullViewBean.className + ";");
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.listViewPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated list view class file");
		writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Component");
        writeLine("@Scope(value=WebApplicationContext.SCOPE_SESSION)");
		writeLine("public class " + this.bean.listViewClassName + " implements Serializable {");
        skipLine();

        writeLine("private static final long serialVersionUID = 1L;");
        skipLine();

		writeLine("/*");
		writeLine(" * properties");
		writeLine(" */");
		writeLine("protected List<" + this.bean.basicViewBean.className + "> " + this.bean.objectName + "List;");
		writeLine("protected " + this.bean.basicViewBean.filterClassName + " " + this.bean.basicViewBean.filterObjectName + " = new " + this.bean.basicViewBean.filterClassName + "();");
		writeLine("protected " + this.bean.fullViewBean.className + " selected" + this.bean.className + ";");
		skipLine();

		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");
		writeLine("public List<" + this.bean.basicViewBean.className + "> get" + this.bean.className + "List() {");
		writeLine("return " + this.bean.objectName + "List;");
		writeLine("}");
		writeLine("public void set" + this.bean.className + "List(List<" + this.bean.basicViewBean.className + "> " + this.bean.objectName + "List) {");
		writeLine("this." + this.bean.objectName + "List = " + this.bean.objectName + "List;");
		writeLine("}");
		skipLine();
		
		writeLine("public " + this.bean.basicViewBean.filterClassName + " get" + this.bean.basicViewBean.filterClassName + "() {");
		writeLine("return " + this.bean.basicViewBean.filterObjectName + ";");
		writeLine("}");
		writeLine("public void set" + this.bean.basicViewBean.filterClassName + "(" + this.bean.basicViewBean.filterClassName + " " + this.bean.basicViewBean.filterObjectName + ") {");
		writeLine("this." + this.bean.basicViewBean.filterObjectName + " = " + this.bean.basicViewBean.filterObjectName + ";");
		writeLine("}");
		skipLine();
		
		writeLine("public " + this.bean.fullViewBean.className + " getSelected" + this.bean.className + "() {");
		writeLine("return selected" + this.bean.className + ";");
		writeLine("}");

		writeLine("public void setSelected" + this.bean.className + "(" + this.bean.fullViewBean.className + " selected" + this.bean.className + ") {");
		writeLine("this.selected" + this.bean.className + " = selected" + this.bean.className + ";");
		writeLine("}");
		skipLine();

		writeNotOverridableContent();

		writeLine("}");

	}
}
