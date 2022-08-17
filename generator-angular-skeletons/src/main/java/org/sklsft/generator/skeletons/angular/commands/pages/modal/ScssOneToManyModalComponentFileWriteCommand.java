package org.sklsft.generator.skeletons.angular.commands.pages.modal;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.skeletons.commands.impl.typed.ScssFileWriteCommand;


public class ScssOneToManyModalComponentFileWriteCommand extends ScssFileWriteCommand {

	private OneToMany oneToMany;
	/*
	 * constructor
	 */
	public ScssOneToManyModalComponentFileWriteCommand(OneToMany oneToMany) {
        
		super(oneToMany.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToMany.parentBean.myPackage.model.tsUiArtefactName + File.separator + oneToMany.parentBean.myPackage.tsComponentsPath + File.separator + oneToMany.parentBean.urlPiece + File.separator + oneToMany.referenceBean.urlPiece + File.separator + "modal", oneToMany.referenceBean.urlPiece + "-modal.component");
		
		this.oneToMany = oneToMany;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        writeLine("/**");
        writeLine(" * auto generated one to many modal component scss file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        
        writeNotOverridableContent();
        
	}
}
