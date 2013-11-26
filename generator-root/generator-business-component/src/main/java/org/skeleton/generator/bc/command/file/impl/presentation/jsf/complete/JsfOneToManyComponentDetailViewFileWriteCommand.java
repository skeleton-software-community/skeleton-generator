package org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.presentation.jsf.JsfXhtmlFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.common.CommonJsfOneToManyComponentDetailViewFileWriteCommand;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToManyComponent;
import org.skeleton.generator.model.om.Property;

public class JsfOneToManyComponentDetailViewFileWriteCommand extends CommonJsfOneToManyComponentDetailViewFileWriteCommand {
	public JsfOneToManyComponentDetailViewFileWriteCommand(OneToManyComponent oneToManyComponent) {
		super(oneToManyComponent);
	}
}
