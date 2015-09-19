package org.sklsft.generator.bc.file.command.impl.java.mvc.model.richfaces4;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;

public class MvcListViewFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	public MvcListViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\java\\"
				+ bean.myPackage.listViewPackageName.replace(".", "\\"), bean.listViewClassName);

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
		javaImports.add("import " + this.bean.myPackage.filterPackageName + "." + this.bean.filterClassName + ";");
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
		writeLine("protected List<" + this.bean.viewClassName + "> " + this.bean.objectName + "List;");
		writeLine("protected " + this.bean.filterClassName + " " + this.bean.filterObjectName + " = new " + this.bean.filterClassName + "();");
		writeLine("protected " + this.bean.viewClassName + " new" + this.bean.className + ";");
		skipLine();

		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");
		writeLine("public List<" + this.bean.viewClassName + "> get" + this.bean.className + "List() {");
		writeLine("return " + this.bean.objectName + "List;");
		writeLine("}");
		writeLine("public void set" + this.bean.className + "List(List<" + this.bean.viewClassName + "> " + this.bean.objectName + "List) {");
		writeLine("this." + this.bean.objectName + "List = " + this.bean.objectName + "List;");
		writeLine("}");
		skipLine();
		
		writeLine("public " + this.bean.filterClassName + " get" + this.bean.filterClassName + "() {");
		writeLine("return " + this.bean.filterObjectName + ";");
		writeLine("}");
		writeLine("public void set" + this.bean.filterClassName + "(" + this.bean.filterClassName + " " + this.bean.filterObjectName + ") {");
		writeLine("this." + this.bean.filterObjectName + " = " + this.bean.filterObjectName + ";");
		writeLine("}");
		skipLine();
		
		writeLine("public " + this.bean.viewClassName + " getNew" + this.bean.className + "() {");
		writeLine("return new" + this.bean.className + ";");
		writeLine("}");

		writeLine("public void setNew" + this.bean.className + "(" + this.bean.viewClassName + " new" + this.bean.className + ") {");
		writeLine("this.new" + this.bean.className + " = new" + this.bean.className + ";");
		writeLine("}");
		skipLine();

		writeNotOverridableContent();

		writeLine("}");

	}
}
