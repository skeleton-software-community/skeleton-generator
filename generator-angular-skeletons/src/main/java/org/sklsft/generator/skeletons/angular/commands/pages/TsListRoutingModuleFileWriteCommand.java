package org.sklsft.generator.skeletons.angular.commands.pages;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsListRoutingModuleFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsListRoutingModuleFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "list", bean.urlPiece + "-list-routing.module");
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { NgModule } from '@angular/core';");
		imports.add("import { RouterModule, Routes } from '@angular/router';");
		imports.add("import { " + bean.className + "ListComponent } from './" + bean.urlPiece + "-list.component';");
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
        
        writeLine("const routes: Routes = [{ path: 'list', component: " + bean.className + "ListComponent }];");
        skipLine();
        
        writeLine("@NgModule({");
        writeLine("imports: [RouterModule.forChild(routes)],");
        writeLine("exports: [RouterModule]");
        writeLine("})");
        writeLine("export class " + bean.className + "ListRoutingModule { }");

	}
}
