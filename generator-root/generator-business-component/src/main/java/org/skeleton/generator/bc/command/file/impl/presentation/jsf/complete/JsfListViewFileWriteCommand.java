package org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.presentation.jsf.JsfXhtmlFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.common.CommonJsfListViewFileWriteCommand;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToMany;
import org.skeleton.generator.model.om.OneToOne;
import org.skeleton.generator.model.om.Property;
import org.skeleton.generator.util.metadata.RelationType;

public class JsfListViewFileWriteCommand extends CommonJsfListViewFileWriteCommand {
	public JsfListViewFileWriteCommand(Bean bean) {
		super(bean,true,true);
	}
}
