package org.sklsft.generator.skeletons.angular.commands.pages;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsAppRoutingModuleFileWriteCommand extends TsFileWriteCommand {

	private Project project;
	/*
	 * constructor
	 */
	public TsAppRoutingModuleFileWriteCommand(Project project) {
        
		super(project.workspaceFolder + File.separator + project.model.tsUiArtefactName + File.separator + "src" + File.separator + "app", "app-routing.module");
		this.project = project;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { NgModule } from '@angular/core';");
		imports.add("import { RouterModule, Routes } from '@angular/router';");
		imports.add("import { AuthGuard } from './core/services/AuthGuard';");
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated app routing module ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();
        
        writeLine("const routes: Routes = [");
        boolean start = true;
        for (Package myPackage : project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					if (start) {
						start = false;
					} else {
						write(",");
					}
					writeLine("{path:'" + bean.urlPiece + "', loadChildren:()=>import('" + bean.myPackage.tsComponentsSourcePath + "/" + bean.urlPiece + "/" + bean.urlPiece + ".module').then(m=>m." + bean.className + "Module), canActivate: [AuthGuard] }");
				}
			}
        }
        writeNotOverridableContent();
        writeLine("];");
        skipLine();
        
        writeLine("@NgModule({");
        writeLine("imports: [RouterModule.forRoot(routes)],");
        writeLine("exports: [RouterModule]");
        writeLine("})");
        writeLine("export class AppRoutingModule { }");

	}
}
