package org.sklsft.generator.skeletons.angular.commands.pages.details;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsDetailsComponentFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsDetailsComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "details", bean.urlPiece + "-details.component");
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { Component, OnInit, ViewChild } from '@angular/core';");
		imports.add("import { ActivatedRoute } from '@angular/router';");
		imports.add("import { " + bean.fullViewBean.className + " } from '../models/" + bean.fullViewBean.className + "';");
		imports.add("import { " + bean.restClientClassName + " } from '../services/" + bean.restClientClassName + "';");
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated details component ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();
        
        writeLine("@Component({");
        writeLine("selector: 'app-" + bean.urlPiece + "-details',");
        writeLine("templateUrl: './" + bean.urlPiece + "-details.component.html',");
        writeLine("styleUrls: ['./" + bean.urlPiece + "-details.component.scss']");
        writeLine("})");
        writeLine("export class " + this.bean.className + "DetailsComponent implements OnInit {");
        skipLine();

        writeLine("id:" + bean.idTsType + ";");
        writeLine("view: " + bean.fullViewBean.className + ";");
        skipLine();

        writeLine("constructor(private service:" + bean.restClientClassName + ", private route: ActivatedRoute) {this.id = parseInt(this.route.snapshot.paramMap.get('id'));}");
        skipLine();
        
        writeLine("ngOnInit(): void {");
        writeLine("this.load();");
        writeLine("}");
        
        writeLine("load(): void {");
        writeLine("this.service.load(this.id).subscribe((t) => {");
        writeLine("this.view=t;");
        writeLine("});");
        writeLine("}");
        
        writeLine("update(): void {");
        writeLine("this.service.update(this.id, this.view.form).subscribe();");
        writeLine("this.load();");
        writeLine("}");

        writeNotOverridableContent();
        
        writeLine("}");

    }
}
