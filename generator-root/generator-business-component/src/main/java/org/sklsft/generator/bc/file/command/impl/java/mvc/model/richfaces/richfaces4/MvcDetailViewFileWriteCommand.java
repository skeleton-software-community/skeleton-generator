package org.sklsft.generator.bc.file.command.impl.java.mvc.model.richfaces.richfaces4;

import org.sklsft.generator.bc.file.command.impl.java.mvc.model.richfaces.AbstractMvcDetailViewFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;

public class MvcDetailViewFileWriteCommand extends AbstractMvcDetailViewFileWriteCommand {

	public MvcDetailViewFileWriteCommand(Bean bean) {
		super(bean);
	}

	@Override
	protected void writeViewScope() {
		writeLine("@Scope(" + CHAR_34 + "view" + CHAR_34 + ")");		
	}

	@Override
	protected void writeViewScopeImport() {
		
		//nothing to add
	}
}
