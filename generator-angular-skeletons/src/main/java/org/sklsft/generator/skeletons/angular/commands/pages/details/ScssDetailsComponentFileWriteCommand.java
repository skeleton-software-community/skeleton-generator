package org.sklsft.generator.skeletons.angular.commands.pages.details;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.ScssFileWriteCommand;


public class ScssDetailsComponentFileWriteCommand extends ScssFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public ScssDetailsComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "details", bean.urlPiece + "-details.component");
		
		this.bean = bean;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        writeLine("/**");
        writeLine(" * auto generated details component scss file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        
        writeNotOverridableContent();
        
	}
}
