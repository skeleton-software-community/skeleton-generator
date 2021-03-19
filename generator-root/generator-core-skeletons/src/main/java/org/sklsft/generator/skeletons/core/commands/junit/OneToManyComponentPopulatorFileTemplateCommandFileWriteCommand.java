package org.sklsft.generator.skeletons.core.commands.junit;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.skeletons.commands.impl.typed.CsvFileWriteCommand;

public class OneToManyComponentPopulatorFileTemplateCommandFileWriteCommand extends CsvFileWriteCommand {

	private OneToManyComponent oneToManyComponent;
	private Bean referenceBean;
    private Bean parentBean;

    public OneToManyComponentPopulatorFileTemplateCommandFileWriteCommand(OneToManyComponent oneToManyComponent){
    	super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToManyComponent.referenceBean.myPackage.model.testsArtefactName + File.separator + oneToManyComponent.referenceBean.myPackage.model.testResourcesFolder + File.separator + "junit" + File.separator + "data" + File.separator + "templates" + File.separator + oneToManyComponent.referenceBean.table.myPackage.name.toUpperCase().replace(".", File.separator),
    			oneToManyComponent.referenceBean.table.originalName);
        
        		this.oneToManyComponent = oneToManyComponent;
        		referenceBean = oneToManyComponent.referenceBean;
        	    parentBean = oneToManyComponent.parentBean;
    }

	

	@Override
	protected void writeContent() throws IOException {
		
		
        
	}
}
