package org.sklsft.generator.skeletons.angular.commands.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsFormBeanFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsFormBeanFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsModelsPath + File.separator + "forms", bean.formBean.className);
		
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
        writeLine(" * auto generated form bean ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("export class " + this.bean.formBean.className + " {");
        skipLine();

        createProperties();

        writeNotOverridableContent();
        
        writeLine("}");

    }

    private void createProperties() {
    	for (ViewProperty property:this.bean.formBean.properties) {
            writeLine(property.name + ": " + property.tsType + ";");
        }
        skipLine();

    }
}
