package org.sklsft.generator.skeletons.jsf.commands.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

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
		javaImports.add("import org.sklsft.commons.api.model.ScrollForm;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollView;");
		javaImports.add("import org.springframework.stereotype.Component;");
        javaImports.add("import org.springframework.context.annotation.Scope;");
        javaImports.add("import org.springframework.web.context.WebApplicationContext;");
		
		javaImports.add("import " + this.bean.myPackage.basicViewsPackageName + "." + this.bean.basicViewBean.className + ";");
		javaImports.add("import " + this.bean.myPackage.filtersPackageName + "." + this.bean.basicViewBean.filterClassName + ";");
		javaImports.add("import " + this.bean.myPackage.sortingsPackageName + "." + this.bean.basicViewBean.sortingClassName + ";");
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
		writeLine("protected ScrollForm<" + bean.basicViewBean.filterClassName + ", " + bean.basicViewBean.sortingClassName + "> scrollForm = new ScrollForm<>();");
		writeLine("protected ScrollView<" + bean.basicViewBean.className + "> scrollView = new ScrollView<>();");
		writeLine("protected " + this.bean.fullViewBean.className + " selected" + this.bean.className + ";");
		skipLine();

		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");
		writeLine("public ScrollView<" + bean.basicViewBean.className + "> getScrollView() {");
		writeLine("return scrollView;");
		writeLine("}");
		writeLine("public void setScrollView(ScrollView<" + this.bean.basicViewBean.className + "> scrollView) {");
		writeLine("this.scrollView = scrollView;");
		writeLine("}");
		skipLine();
		
		writeLine("public ScrollForm<" + bean.basicViewBean.filterClassName + ", " + bean.basicViewBean.sortingClassName + "> getScrollForm() {");
		writeLine("return scrollForm;");
		writeLine("}");
		writeLine("public void setScrollForm(ScrollForm<" + bean.basicViewBean.filterClassName + ", " + bean.basicViewBean.sortingClassName + "> scrollForm) {");
		writeLine("this.scrollForm = scrollForm;");
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
