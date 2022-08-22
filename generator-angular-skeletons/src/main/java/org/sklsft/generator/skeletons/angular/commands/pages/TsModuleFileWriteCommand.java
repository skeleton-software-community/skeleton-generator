package org.sklsft.generator.skeletons.angular.commands.pages;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsModuleFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsModuleFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsComponentsPath + File.separator + bean.urlPiece, bean.urlPiece + ".module");
		
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
		imports.add("import { " + bean.className + "MenuComponent } from './menu/" + bean.urlPiece + "-menu.component';");
		imports.add("import { " + bean.className + "ModalComponent } from './modal/" + bean.urlPiece + "-modal.component';");
		
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			imports.add("import { " + oneToManyComponent.referenceBean.className + "ListComponent } from './" + oneToManyComponent.referenceBean.urlPiece + "/list/" + oneToManyComponent.referenceBean.urlPiece + "-list.component';");
			imports.add("import { " + oneToManyComponent.referenceBean.className + "ModalComponent } from './" + oneToManyComponent.referenceBean.urlPiece + "/modal/" + oneToManyComponent.referenceBean.urlPiece + "-modal.component';");
		}
		
		for (OneToMany oneToMany:bean.oneToManyList) {
			imports.add("import { " + oneToMany.referenceBean.className + "ListComponent } from './" + oneToMany.referenceBean.urlPiece + "/list/" + oneToMany.referenceBean.urlPiece + "-list.component';");
			imports.add("import { " + oneToMany.referenceBean.className + "ModalComponent } from './" + oneToMany.referenceBean.urlPiece + "/modal/" + oneToMany.referenceBean.urlPiece + "-modal.component';");
		}
		
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList) {
			imports.add("import { " + oneToOneComponent.referenceBean.className + "DetailsComponent } from './" + oneToOneComponent.referenceBean.urlPiece + "/details/" + oneToOneComponent.referenceBean.urlPiece + "-details.component';");
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
        write("declarations: [" + bean.className + "ListComponent," + bean.className + "DetailsComponent, " + bean.className + "ModalComponent, " + bean.className + "MenuComponent");
        for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			write(", " + oneToManyComponent.referenceBean.className + "ListComponent, " + oneToManyComponent.referenceBean.className + "ModalComponent");
		}
        for (OneToMany oneToMany:bean.oneToManyList) {
			write(", " + oneToMany.referenceBean.className + "ListComponent, " + oneToMany.referenceBean.className + "ModalComponent");
		}
        for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList) {
			write(", " + oneToOneComponent.referenceBean.className + "DetailsComponent");
		}
        writeLine("],");
        
        writeLine("imports: [CommonModule, SharedModule, " + bean.className + "RoutingModule]");
        writeLine("})");
        writeLine("export class " + bean.className + "Module { }");

	}
}