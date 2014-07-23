package org.sklsft.generator.bc.command.file.impl.presentation.jsf.basic;

import org.sklsft.generator.bc.command.file.impl.presentation.jsf.common.CommonJsfOneToManyComponentListViewFileWriteCommand;
import org.sklsft.generator.model.om.OneToManyComponent;

public class JsfOneToManyComponentListViewFileWriteCommand extends CommonJsfOneToManyComponentListViewFileWriteCommand {
	
	public JsfOneToManyComponentListViewFileWriteCommand(OneToManyComponent oneToManyComponent) {
		super(oneToManyComponent, false);
	}
}