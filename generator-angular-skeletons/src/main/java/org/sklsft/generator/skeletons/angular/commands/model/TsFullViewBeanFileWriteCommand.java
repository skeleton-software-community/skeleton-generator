package org.sklsft.generator.skeletons.angular.commands.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsFullViewBeanFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsFullViewBeanFileWriteCommand(Bean bean) {
        
super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsModelsPath + File.separator + "views" + File.separator + "full" , bean.fullViewBean.className);
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { " + bean.formBean.className + " } from '" + bean.myPackage.tsModelsSourcePath + "/forms/" + bean.formBean.className + "';");
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated full veiw bean ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        writeLine("export class " + this.bean.fullViewBean.className + " {");
        skipLine();

        createProperties();

        writeNotOverridableContent();
        
        writeLine("}");

    }

    private void createProperties() {
    	
        writeLine("id: " + bean.idTsType + ";");
        writeLine("form: " + bean.formBean.className + ";");
        writeLine("canUpdate: boolean;");
        skipLine();

    }
}
