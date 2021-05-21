package org.sklsft.generator.skeletons.core.commands.population;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.CsvFileWriteCommand;

public class BeanPopulatorFileTemplateCommandFileWriteCommand extends CsvFileWriteCommand {

	private Bean bean;

    public BeanPopulatorFileTemplateCommandFileWriteCommand(Bean bean){
        super(bean.myPackage.model.project.workspaceFolder + File.separator + "data-model" + File.separator + "population" + File.separator + "templates" + File.separator + bean.table.myPackage.name.toUpperCase().replace(".", File.separator),
        		bean.table.originalName);
        
        		this.bean = bean;
    }

	

	@Override
	protected void writeContent() throws IOException {
		
		boolean start = true;
		for (ViewProperty property:bean.formBean.properties) {
			if (start) {
				start = false;
			} else {
				write(";");
			}
			write("\"" + property.rendering + "\"");
		}
	}
}
