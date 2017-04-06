package org.sklsft.generator.skeletons.jsf.commands.model;

import org.sklsft.generator.model.domain.business.Bean;

public class MvcDetailViewFileWriteCommand extends AbstractMvcDetailViewFileWriteCommand {

	public MvcDetailViewFileWriteCommand(Bean bean) {
		super(bean);
	}

	@Override
	protected void writeViewScope() {
		writeLine("@Scope(ViewScope.NAME)");		
	}

	@Override
	protected void writeViewScopeImport() {
		
		javaImports.add("import org.sklsft.commons.mvc.scopes.ViewScope;");
	}
}
