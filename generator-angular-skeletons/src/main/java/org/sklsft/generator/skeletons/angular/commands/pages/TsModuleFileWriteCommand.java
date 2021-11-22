package org.sklsft.generator.skeletons.angular.commands.pages;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsModuleFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsModuleFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece, bean.urlPiece + ".module");
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { NgModule } from '@angular/core';");
		imports.add("import { CommonModule } from '@angular/common';");
		imports.add("import { SharedModule } from 'src/app/shared/shared.module';");
		
		imports.add("import { " + bean.className + "RoutingModule } from './" + bean.urlPiece + "-routing.module';");
		imports.add("import { " + bean.className + "ListComponent } from './list/" + bean.urlPiece + "-list.component';");
		imports.add("import { " + bean.className + "DetailsComponent } from './details/" + bean.urlPiece + "-details.component';");
		imports.add("import { " + bean.className + "ModalComponent } from './list/modal/" + bean.urlPiece + "-modal.component';");
		
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			imports.add("import { " + oneToManyComponent.referenceBean.className + "ListComponent } from './" + oneToManyComponent.referenceBean.urlPiece + "/list/" + oneToManyComponent.referenceBean.urlPiece + "-list.component';");
			imports.add("import { " + oneToManyComponent.referenceBean.className + "ModalComponent } from './" + oneToManyComponent.referenceBean.urlPiece + "/list/modal/" + oneToManyComponent.referenceBean.urlPiece + "-modal.component';");
		}
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated list module ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();
        
        writeLine("@NgModule({");
        write("declarations: [" + bean.className + "ListComponent," + bean.className + "DetailsComponent, " + bean.className + "ModalComponent");
        for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			write(", " + oneToManyComponent.referenceBean.className + "ListComponent, " + oneToManyComponent.referenceBean.className + "ModalComponent");
		}
        writeLine("],");
        
        writeLine("imports: [CommonModule, SharedModule, " + bean.className + "RoutingModule]");
        writeLine("})");
        writeLine("export class " + bean.className + "Module { }");

	}
}