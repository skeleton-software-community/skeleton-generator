package org.sklsft.generator.skeletons.angular.commands.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.FilterProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsFilterFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsFilterFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "models", bean.basicViewBean.filter.className);
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		//
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated filter ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("export class " + this.bean.basicViewBean.filter.className + " {");
        skipLine();

        createProperties();

        writeNotOverridableContent();
        
        writeLine("}");

    }

    private void createProperties() {
        
        for (FilterProperty property:this.bean.basicViewBean.filter.properties) {
			writeLine(property.name + ": " + property.tsType + ";");
        }
        skipLine();

    }
}
