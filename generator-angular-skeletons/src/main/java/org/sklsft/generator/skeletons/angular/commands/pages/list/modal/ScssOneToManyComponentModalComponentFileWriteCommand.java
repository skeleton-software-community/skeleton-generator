package org.sklsft.generator.skeletons.angular.commands.pages.list.modal;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.skeletons.commands.impl.typed.ScssFileWriteCommand;


public class ScssOneToManyComponentModalComponentFileWriteCommand extends ScssFileWriteCommand {

	private OneToManyComponent oneToManyComponent;
	/*
	 * constructor
	 */
	public ScssOneToManyComponentModalComponentFileWriteCommand(OneToManyComponent oneToManyComponent) {
        
		super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToManyComponent.referenceBean.myPackage.model.tsUiArtefactName + File.separator + oneToManyComponent.parentBean.myPackage.model.tsSourcesFolder + File.separator + oneToManyComponent.parentBean.myPackage.tsFeaturePath + File.separator + oneToManyComponent.parentBean.urlPiece + File.separator + oneToManyComponent.referenceBean.urlPiece + File.separator + "list" + File.separator + "modal", oneToManyComponent.referenceBean.urlPiece + "-modal.component");
		
		this.oneToManyComponent = oneToManyComponent;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        writeLine("/**");
        writeLine(" * auto generated modal component scss file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        
        writeNotOverridableContent();
        
	}
}
