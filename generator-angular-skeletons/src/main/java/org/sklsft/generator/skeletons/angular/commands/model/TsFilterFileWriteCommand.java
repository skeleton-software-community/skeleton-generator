package org.sklsft.generator.skeletons.angular.commands.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsFilterFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsFilterFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "models", bean.basicViewBean.filterClassName);
		
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
        writeLine("export class " + this.bean.basicViewBean.filterClassName + " {");
        skipLine();

        createProperties();

        writeNotOverridableContent();
        
        writeLine("}");

    }

    private void createProperties() {
        
        for (ViewProperty property:this.bean.basicViewBean.properties) {
        	if (property.dataType.isLimitable()) {
        		writeLine(property.name + "MinValue: " + property.tsType + ";");
        		writeLine(property.name + "MaxValue: " + property.tsType + ";");
			} else {
				writeLine(property.name + ": " + property.tsType + ";");
			}
        }
        skipLine();

    }
}
