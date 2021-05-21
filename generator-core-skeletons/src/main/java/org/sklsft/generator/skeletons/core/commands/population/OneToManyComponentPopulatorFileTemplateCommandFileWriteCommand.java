package org.sklsft.generator.skeletons.core.commands.population;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.CsvFileWriteCommand;

public class OneToManyComponentPopulatorFileTemplateCommandFileWriteCommand extends CsvFileWriteCommand {

	private Bean referenceBean;
    private Bean parentBean;

    public OneToManyComponentPopulatorFileTemplateCommandFileWriteCommand(OneToManyComponent oneToManyComponent){
    	super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + "data-model" + File.separator + "population" + File.separator + "templates" + File.separator + oneToManyComponent.referenceBean.table.myPackage.name.toUpperCase().replace(".", File.separator),
    			oneToManyComponent.referenceBean.table.originalName);
    			referenceBean = oneToManyComponent.referenceBean;
    			parentBean = oneToManyComponent.parentBean;
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
