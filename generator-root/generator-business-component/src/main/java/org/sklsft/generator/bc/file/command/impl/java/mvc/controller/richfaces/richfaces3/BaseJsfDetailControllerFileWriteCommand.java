package org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces.richfaces3;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces.AbstractBaseJsfDetailControllerFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;

public class BaseJsfDetailControllerFileWriteCommand extends AbstractBaseJsfDetailControllerFileWriteCommand {

	public BaseJsfDetailControllerFileWriteCommand(Bean bean) {
		super(bean);
	}
	

	protected void createInit() {
		
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
		
		writeLine("/**");
		writeLine(" * init");
		writeLine(" */");
		writeLine("@PostConstruct");
		writeLine("public void init() {");
		writeLine("String id = getParameter(" + CHAR_34 + "id" + CHAR_34 + ");");
		writeLine("if (id != null) {");
		writeLine(bean.detailViewObjectName + ".setSelected" + bean.className + "Id(Long.valueOf(id));");
		
		writeLine("load();");
		writeLine("}");
		writeLine("}");
	}
}
