package org.sklsft.generator.skeletons.angular.commands.pages.list;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.skeletons.commands.impl.typed.ScssFileWriteCommand;


public class ScssOneToManyListComponentFileWriteCommand extends ScssFileWriteCommand {

	private OneToMany oneToMany;
	private Bean referenceBean;
	
	/*
	 * constructor
	 */
	public ScssOneToManyListComponentFileWriteCommand(OneToMany oneToMany) {
        
		super(oneToMany.parentBean.myPackage.model.project.workspaceFolder + File.separator + oneToMany.parentBean.myPackage.model.tsUiArtefactName + File.separator + oneToMany.parentBean.myPackage.tsComponentsPath + File.separator + oneToMany.parentBean.urlPiece + File.separator + oneToMany.referenceBean.urlPiece + File.separator + "list", oneToMany.referenceBean.urlPiece + "-list.component");
		
		this.oneToMany = oneToMany;
		this.referenceBean = oneToMany.referenceBean;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        writeLine("/**");
        writeLine(" * auto generated one to many list component scss file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        
        writeNotOverridableContent();
        
	}
}
