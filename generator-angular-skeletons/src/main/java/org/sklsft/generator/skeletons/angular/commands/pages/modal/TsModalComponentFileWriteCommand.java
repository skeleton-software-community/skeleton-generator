package org.sklsft.generator.skeletons.angular.commands.pages.modal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsModalComponentFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	private Map<String, Bean> selectableBeans = new HashMap<>();
	/*
	 * constructor
	 */
	public TsModalComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsComponentsPath + File.separator + bean.urlPiece + File.separator + "modal", bean.urlPiece + "-modal.component");
		
		this.bean = bean;
		
		for (ViewProperty property:this.bean.formBean.properties) {
        	if (property.selectableBean!=null) {
        		selectableBeans.put(property.selectableBean.className, property.selectableBean);
        	}
		}		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { Component, OnInit, ViewChild } from '@angular/core';");
		imports.add("import { SelectItem } from 'src/app/core/models/SelectItem';");
		imports.add("import { Observable } from 'rxjs';");
		imports.add("import { StringUtils } from 'src/app/core/services/StringUtils';");
		imports.add("import { " + bean.fullViewBean.className + " } from '" + bean.myPackage.tsModelsSourcePath + "/views/full/" + bean.fullViewBean.className + "';");
		imports.add("import { " + bean.restClientClassName + " } from '" + bean.myPackage.tsServicesSourcePath + "/" + bean.restClientClassName + "';");
		imports.add("import { FormBuilder, FormGroup, Validators } from '@angular/forms';");
		imports.add("import { MatDialogRef } from '@angular/material/dialog';");
		imports.add("import { NotificationService } from 'src/app/core/services/NotificationService';");
		for (ViewProperty property:this.bean.formBean.properties) {
        	if (property.selectableBean!=null) {
        		imports.add("import { " + property.selectableBean.restClientClassName + " } from '" + property.selectableBean.myPackage.tsServicesSourcePath + "/" + property.selectableBean.restClientClassName + "';");
        	}
		}
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated modal component ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();
        
        writeLine("@Component({");
        writeLine("selector: 'app-" + bean.urlPiece + "-modal',");
        writeLine("templateUrl: './" + bean.urlPiece + "-modal.component.html',");
        writeLine("styleUrls: ['./" + bean.urlPiece + "-modal.component.scss']");
        writeLine("})");
        writeLine("export class " + this.bean.className + "ModalComponent implements OnInit {");
        skipLine();

        writeLine("view: " + bean.fullViewBean.className + ";");
        writeLine("form: FormGroup;");
        
        for (ViewProperty property:this.bean.formBean.properties) {
        	if (property.selectableBean!=null) {
        		if (property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {
        			writeLine(property.name + "Options: Observable<SelectItem[]>;");
        		}
        		if (property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
        			writeLine(property.name + "Options: SelectItem[];");
        		}
        	}
        }
        
        skipLine();

        write("constructor(private service:" + bean.restClientClassName);
        for (Bean selectableBean:selectableBeans.values()) {
        	
        	write(", private " + selectableBean.serviceObjectName + ":" + selectableBean.restClientClassName);

		}
        
        writeLine(", private formBuilder: FormBuilder, private notifications: NotificationService, private dialogRef: MatDialogRef<" + bean.className + "ModalComponent>) {}");
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
        	write(property.name + ":[{value:null");
        	if (!property.editable) {
        		write(", disabled:true");
        	}
        	write("}");
        	if (!property.nullable) {
        		write(", Validators.required");
        	}
        	write("]");
        }
        writeLine("});");
        for (ViewProperty property:this.bean.formBean.properties) {
        	if (property.selectableBean!=null) {
        		if (property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {
        			writeLine("this.form.controls['" + property.name + "'].valueChanges.subscribe(value=>{this.searchOptionsFor" + property.capName + "(value)});");
        		}
        		if (property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
        			writeLine("this.loadOptionsFor" + property.capName + "();");
        		}
        	}
        }
        writeLine("this.restoreForm();");
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
        	switch (property.dataType) {
        	case BOOLEAN:
        		if (property.nullable) {
        			writeLine("this.view.form." + property.name + " = StringUtils.stringToNullableBoolean(this.form.get('" + property.name + "').value);");
        		} else {
        			writeLine("this.view.form." + property.name + " = StringUtils.stringToStrictBoolean(this.form.get('" + property.name + "').value);");
        		}
        		break;
        	default:
        		writeLine("this.view.form." + property.name + " = StringUtils.emptyToNull(this.form.get('" + property.name + "').value);");
    	}
        }
        writeLine("}");
        skipLine();
        
        
        for (ViewProperty property:this.bean.formBean.properties) {
        	if (property.selectableBean!=null) {
        		if (property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {
        			writeLine("searchOptionsFor" + property.capName + "(value:string) {");
        			writeLine("if (value) {");
        			writeLine("this." + property.name + "Options = this." + property.selectableBean.serviceObjectName + ".searchOptions(value);");
        			writeLine("}");
        			writeLine("}");
        			skipLine();
        		}
        		if (property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
        			writeLine("loadOptionsFor" + property.capName + "() {");
        			writeLine("this." + property.selectableBean.serviceObjectName + ".getOptions().subscribe((t) => {this." + property.name + "Options=t;});");
        			writeLine("}");
        			skipLine();
        		}
        	}
        }
        
        writeLine("save(): void {");
        writeLine("this.applyForm();");
        writeLine("this.service.save(this.view.form).subscribe(success => {this.notifications.info(\"Operation completed\");this.dialogRef.close();}, error => {this.notifications.error(\"Operation failed\")});");
        writeLine("}");
        skipLine();
        
        writeLine("update(): void {");
        writeLine("this.applyForm();");
        writeLine("this.service.update(this.view.id, this.view.form).subscribe(success => {this.notifications.info(\"Operation completed\");this.dialogRef.close();}, error => {this.notifications.error(\"Operation failed\")});");
        writeLine("}");
        skipLine();
        
        writeLine("saveOrUpdate(): void {");
        writeLine("if (this.view.id == null) {this.save()} else {this.update()}");
        writeLine("}");
        skipLine();

        writeNotOverridableContent();
        
        writeLine("}");

    }
}
