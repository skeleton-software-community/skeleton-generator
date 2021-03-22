package org.sklsft.generator.skeletons.core.commands.population;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.CsvFileWriteCommand;

public class OneToOneComponentPopulatorFileTemplateCommandFileWriteCommand extends CsvFileWriteCommand {

	private Bean referenceBean;
    private Bean parentBean;

    public OneToOneComponentPopulatorFileTemplateCommandFileWriteCommand(OneToOneComponent OneToOneComponent){
    	super(OneToOneComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + "data-model" + File.separator + "population" + File.separator + "templates" + File.separator + OneToOneComponent.referenceBean.table.myPackage.name.toUpperCase().replace(".", File.separator),
    			OneToOneComponent.referenceBean.table.originalName);
    			referenceBean = OneToOneComponent.referenceBean;
    			parentBean = OneToOneComponent.parentBean;
    }


    @Override
	protected void writeContent() throws IOException {
		
    	boolean start = true;
    	for (ViewProperty property:parentBean.referenceViewProperties) {
			if (start) {
				start = false;
			} else {
				write(";");
			}
			write("\"" + property.rendering + "\"");
		}
		for (ViewProperty property:referenceBean.formBean.properties) {
			write(";");
			write("\"" + property.rendering + "\"");
		}
	}
}
