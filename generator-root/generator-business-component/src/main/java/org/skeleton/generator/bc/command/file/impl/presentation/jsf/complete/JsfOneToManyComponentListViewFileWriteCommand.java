package org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete;

import org.skeleton.generator.bc.command.file.impl.presentation.jsf.common.CommonJsfOneToManyComponentListViewFileWriteCommand;
import org.skeleton.generator.model.om.OneToManyComponent;

public class JsfOneToManyComponentListViewFileWriteCommand extends CommonJsfOneToManyComponentListViewFileWriteCommand {
	
	public JsfOneToManyComponentListViewFileWriteCommand(OneToManyComponent oneToManyComponent) {
		super(oneToManyComponent, true);
	}
}