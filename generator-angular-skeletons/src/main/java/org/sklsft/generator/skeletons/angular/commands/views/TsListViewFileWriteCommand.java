package org.sklsft.generator.skeletons.angular.commands.views;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;

public class TsListViewFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;

	public TsListViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "views", bean.listViewClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		imports.add("import { ScrollForm } from \"src/app/core/models/ScrollForm\";");
		imports.add("import { ScrollView } from \"src/app/core/models/ScrollView\";");
		
		imports.add("import { " + bean.fullViewBean.className + " } from '../models/" + bean.fullViewBean.className + "';");
		imports.add("import { " + bean.basicViewBean.className + " } from '../models/" + bean.basicViewBean.className + "';");
		
		imports.add("import { " + bean.basicViewBean.filterClassName + " } from '../models/" + bean.basicViewBean.filterClassName + "';");
		imports.add("import { " + bean.basicViewBean.sortingClassName + " } from '../models/" + bean.basicViewBean.sortingClassName + "';");
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated list view ts file");
		writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("export class " + this.bean.listViewClassName + " {");
        skipLine();

		writeLine("scrollForm: ScrollForm<" + bean.basicViewBean.filterClassName + ", " + bean.basicViewBean.sortingClassName + ">;");
		writeLine("scrollView: ScrollView<" + bean.basicViewBean.className + ">;");
		writeLine("selected" + this.bean.className + ": " + this.bean.fullViewBean.className + ";");
		skipLine();

		writeNotOverridableContent();

		writeLine("}");

	}
}
