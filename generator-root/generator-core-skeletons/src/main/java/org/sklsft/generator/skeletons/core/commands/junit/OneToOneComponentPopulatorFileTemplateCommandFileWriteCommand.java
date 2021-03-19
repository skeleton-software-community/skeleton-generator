package org.sklsft.generator.skeletons.core.commands.junit;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.skeletons.commands.impl.typed.CsvFileWriteCommand;

public class OneToOneComponentPopulatorFileTemplateCommandFileWriteCommand extends CsvFileWriteCommand {

	private OneToOneComponent oneToOneComponent;
	private Bean referenceBean;
    private Bean parentBean;

    public OneToOneComponentPopulatorFileTemplateCommandFileWriteCommand(OneToOneComponent oneToOneComponent){
    	super(oneToOneComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToOneComponent.referenceBean.myPackage.model.testsArtefactName + File.separator + oneToOneComponent.referenceBean.myPackage.model.testResourcesFolder + File.separator + "junit" + File.separator + "data" + File.separator + "templates" + File.separator + oneToOneComponent.referenceBean.table.myPackage.name.toUpperCase().replace(".", File.separator),
    			oneToOneComponent.referenceBean.table.originalName);
        
        		this.oneToOneComponent = oneToOneComponent;
        		referenceBean = oneToOneComponent.referenceBean;
        	    parentBean = oneToOneComponent.parentBean;
    }


	@Override
	protected void writeContent() throws IOException {
		
		
	}
}
