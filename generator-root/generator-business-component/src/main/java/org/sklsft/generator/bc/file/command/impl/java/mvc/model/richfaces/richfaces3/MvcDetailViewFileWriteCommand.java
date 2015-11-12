package org.sklsft.generator.bc.file.command.impl.java.mvc.model.richfaces.richfaces3;

import org.sklsft.generator.bc.file.command.impl.java.mvc.model.richfaces.CommonMvcDetailViewFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;

public class MvcDetailViewFileWriteCommand extends CommonMvcDetailViewFileWriteCommand {

	public MvcDetailViewFileWriteCommand(Bean bean) {
		super(bean);
	}

	@Override
	protected void writeViewScope() {
		writeLine("@KeepAlive(ajaxOnly=false)");
		writeLine("@Scope(value=WebApplicationContext.SCOPE_REQUEST)");
	}

	@Override
	protected void writeViewScopeImport() {
		
		javaImports.add("import org.ajax4jsf.model.KeepAlive;");
		
	}
}
