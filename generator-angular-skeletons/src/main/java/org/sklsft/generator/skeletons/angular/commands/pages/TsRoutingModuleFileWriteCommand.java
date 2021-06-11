package org.sklsft.generator.skeletons.angular.commands.pages;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsRoutingModuleFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsRoutingModuleFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece, bean.urlPiece + "-routing.module");
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { NgModule } from '@angular/core';");
		imports.add("import { RouterModule, Routes } from '@angular/router';");
		imports.add("import { " + bean.className + "ListComponent } from './list/" + bean.urlPiece + "-list.component';");
		imports.add("import { " + bean.className + "DetailsComponent } from './details/" + bean.urlPiece + "-details.component';");
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
        writeLine( "{path: 'list', component: " + bean.className + "ListComponent },");
        writeLine( "{path: ':id', component: " + bean.className + "DetailsComponent }");
        writeLine("];");
        skipLine();
        
        writeLine("@NgModule({");
        writeLine("imports: [RouterModule.forChild(routes)],");
        writeLine("exports: [RouterModule]");
        writeLine("})");
        writeLine("export class " + bean.className + "RoutingModule { }");

	}
}
