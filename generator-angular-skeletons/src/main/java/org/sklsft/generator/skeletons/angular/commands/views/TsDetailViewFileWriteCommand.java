package org.sklsft.generator.skeletons.angular.commands.views;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;

public class TsDetailViewFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;

	public TsDetailViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "views", bean.detailViewClassName);


		this.bean = bean;
	}
	

	@Override
	protected void fetchSpecificImports() {
		imports.add("import { " + bean.fullViewBean.className + " } from '../models/" + bean.fullViewBean.className + "';");
	}

	@Override
	protected void writeContent() throws IOException {

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated detail view ts file");
		writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("export class " + this.bean.detailViewClassName + " {");
        skipLine();
		
        writeLine("selected" + this.bean.className + ": " + this.bean.fullViewBean.className + ";");
        skipLine();
        
		writeNotOverridableContent();

		writeLine("}");

	}
}
