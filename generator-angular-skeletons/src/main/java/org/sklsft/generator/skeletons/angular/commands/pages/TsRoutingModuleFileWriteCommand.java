package org.sklsft.generator.skeletons.angular.commands.pages;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsRoutingModuleFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsRoutingModuleFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsComponentsPath + File.separator + bean.urlPiece, bean.urlPiece + "-routing.module");
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { NgModule } from '@angular/core';");
		imports.add("import { RouterModule, Routes } from '@angular/router';");
		imports.add("import { " + bean.className + "ListComponent } from './list/" + bean.urlPiece + "-list.component';");
		imports.add("import { " + bean.className + "DetailsComponent } from './details/" + bean.urlPiece + "-details.component';");
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			imports.add("import { " + oneToManyComponent.referenceBean.className + "ListComponent } from './" + oneToManyComponent.referenceBean.urlPiece + "/list/" + oneToManyComponent.referenceBean.urlPiece + "-list.component';");
		}
		for (OneToMany oneToMany:bean.oneToManyList) {
			imports.add("import { " + oneToMany.referenceBean.className + "ListComponent } from './" + oneToMany.referenceBean.urlPiece + "/list/" + oneToMany.referenceBean.urlPiece + "-list.component';");
		}
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList) {
			imports.add("import { " + oneToOneComponent.referenceBean.className + "DetailsComponent } from './" + oneToOneComponent.referenceBean.urlPiece + "/details/" + oneToOneComponent.referenceBean.urlPiece + "-details.component';");
		}
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated list routing module ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();
        
        writeLine("const routes: Routes = [");
        writeLine( "{path: 'list', component: " + bean.className + "ListComponent }");
        writeLine( ",{path: ':id', component: " + bean.className + "DetailsComponent }");
        for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
        	writeLine( ",{path: ':id/" + oneToManyComponent.referenceBean.urlPiece + "/list', component: " + oneToManyComponent.referenceBean.className + "ListComponent }");
		}
        for (OneToMany oneToMany:bean.oneToManyList) {
        	writeLine( ",{path: ':id/" + oneToMany.referenceBean.urlPiece + "/list', component: " + oneToMany.referenceBean.className + "ListComponent }");
		}
        for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList) {
        	writeLine( ",{path: ':id/" + oneToOneComponent.referenceBean.urlPiece + "', component: " + oneToOneComponent.referenceBean.className + "DetailsComponent }");
		}
        writeLine("];");
        skipLine();
        
        writeLine("@NgModule({");
        writeLine("imports: [RouterModule.forChild(routes)],");
        writeLine("exports: [RouterModule]");
        writeLine("})");
        writeLine("export class " + bean.className + "RoutingModule { }");

	}
}
