package org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.presentation.jsf.JsfXhtmlFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.common.CommonJsfDetailViewFileWriteCommand;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToManyComponent;
import org.skeleton.generator.model.om.Property;
import org.skeleton.generator.model.om.UniqueComponent;

public class JsfDetailViewFileWriteCommand extends CommonJsfDetailViewFileWriteCommand {
	
	public JsfDetailViewFileWriteCommand(Bean bean) {
		super(bean);
	}
}
