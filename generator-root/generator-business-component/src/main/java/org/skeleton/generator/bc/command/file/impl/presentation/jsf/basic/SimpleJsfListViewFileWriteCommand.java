package org.skeleton.generator.bc.command.file.impl.presentation.jsf.basic;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.presentation.jsf.JsfXhtmlFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.common.CommonSimpleJsfListViewFileWriteCommand;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToMany;
import org.skeleton.generator.model.om.OneToOne;
import org.skeleton.generator.model.om.Property;
import org.skeleton.generator.util.metadata.RelationType;

public class SimpleJsfListViewFileWriteCommand extends CommonSimpleJsfListViewFileWriteCommand {

	public SimpleJsfListViewFileWriteCommand(Bean bean) {
		super(bean, false, false);
	}
}
