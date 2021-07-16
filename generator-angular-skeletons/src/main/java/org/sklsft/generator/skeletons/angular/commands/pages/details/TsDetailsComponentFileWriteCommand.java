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
		imports.add("import { FormBuilder, FormGroup, Validators } from '@angular/forms';");
		imports.add("import { NotificationService } from 'src/app/core/services/NotificationService';");
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
        writeLine("form: FormGroup;");
        skipLine();

        writeLine("constructor(private service:" + bean.restClientClassName + ", private route: ActivatedRoute, private formBuilder: FormBuilder, private notifications: NotificationService) {this.id = parseInt(this.route.snapshot.paramMap.get('id'));}");
        skipLine();
        boolean start = true;
        writeLine("ngOnInit(): void {");
        writeLine("this.form = this.formBuilder.group({");
        for (ViewProperty property:this.bean.formBean.properties) {
        	if (start) {
        		start = false;
        	} else {
        		writeLine(",");
        	}
        	write(property.name + ":[''");
        	if (!property.nullable) {
        		write(", Validators.required");
        	}
        	write("]");
        }
        writeLine("})");
        writeLine("this.load();");
        writeLine("}");
        
        writeLine("restoreForm(): void {");
        writeLine("this.form.patchValue({");
        start = true;
        for (ViewProperty property:this.bean.formBean.properties) {
        	if (start) {
        		start = false;
        	} else {
        		writeLine(",");
        	}
            write(property.name + ": this.view.form." + property.name);
        }
        skipLine();    
        writeLine("})");
        writeLine("}");
        skipLine();

        writeLine("applyForm(): void {");
        for (ViewProperty property:this.bean.formBean.properties) {
        	writeLine("this.view.form." + property.name + " = this.form.get('" + property.name + "').value;");
        }
        writeLine("}");
        skipLine();
        
        writeLine("load(): void {");
        writeLine("this.service.load(this.id).subscribe((t) => {this.view=t;this.restoreForm();});");
        writeLine("}");
        
        writeLine("update(): void {");
        writeLine("this.applyForm();");
        writeLine("this.service.update(this.id, this.view.form).subscribe(success => this.notifications.info(\"Operation completed\"), error => {this.notifications.error(\"Operation failed\")});");
        writeLine("this.load();");
        writeLine("}");

        writeNotOverridableContent();
        
        writeLine("}");

    }
}
