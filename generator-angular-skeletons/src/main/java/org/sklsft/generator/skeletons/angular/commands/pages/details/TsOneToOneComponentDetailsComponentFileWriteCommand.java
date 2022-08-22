package org.sklsft.generator.skeletons.angular.commands.pages.details;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsOneToOneComponentDetailsComponentFileWriteCommand extends TsFileWriteCommand {

	private OneToOneComponent oneToOneComponent;
	private Bean parentBean;
	private Bean referenceBean;
	private Map<String, Bean> selectableBeans = new HashMap<>();
	/*
	 * constructor
	 */
	public TsOneToOneComponentDetailsComponentFileWriteCommand(OneToOneComponent oneToOneComponent) {
        
		super(oneToOneComponent.parentBean.myPackage.model.project.workspaceFolder + File.separator + oneToOneComponent.parentBean.myPackage.model.tsUiArtefactName + File.separator + oneToOneComponent.parentBean.myPackage.tsComponentsPath + File.separator + oneToOneComponent.parentBean.urlPiece + File.separator + oneToOneComponent.referenceBean.urlPiece + File.separator + "details", oneToOneComponent.referenceBean.urlPiece + "-details.component");
		
		this.oneToOneComponent = oneToOneComponent;
		this.parentBean = oneToOneComponent.parentBean;
		this.referenceBean = oneToOneComponent.referenceBean;
		
		for (ViewProperty property:referenceBean.formBean.properties) {
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
		imports.add("import { ActivatedRoute } from '@angular/router';");
		imports.add("import { NavLink } from 'src/app/core/models/nav-link';");
		imports.add("import { " + referenceBean.fullViewBean.className + " } from '" + referenceBean.myPackage.tsModelsSourcePath + "/views/full/" + referenceBean.fullViewBean.className + "';");
		imports.add("import { " + parentBean.restClientClassName + " } from '" + parentBean.myPackage.tsServicesSourcePath + "/" + parentBean.restClientClassName + "';");
		imports.add("import { FormBuilder, FormGroup, Validators } from '@angular/forms';");
		imports.add("import { NotificationService } from 'src/app/core/services/NotificationService';");
		for (ViewProperty property:this.referenceBean.formBean.properties) {
        	if (property.selectableBean!=null) {
        		imports.add("import { " + property.selectableBean.restClientClassName + " } from '" + property.selectableBean.myPackage.tsServicesSourcePath + "/" + property.selectableBean.restClientClassName + "';");
        	}
		}
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated one to one component details component ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();
        
        writeLine("@Component({");
        writeLine("selector: 'app-" + referenceBean.urlPiece + "-details',");
        writeLine("templateUrl: './" + referenceBean.urlPiece + "-details.component.html',");
        writeLine("styleUrls: ['./" + referenceBean.urlPiece + "-details.component.scss']");
        writeLine("})");
        writeLine("export class " + this.referenceBean.className + "DetailsComponent implements OnInit {");
        skipLine();

        writeLine("id:" + parentBean.idTsType + ";");
        writeLine("activePath:string;");
        writeLine("view: " + referenceBean.fullViewBean.className + " = new " + referenceBean.fullViewBean.className + "();");
        writeLine("form: FormGroup;");
        
        for (ViewProperty property:this.referenceBean.formBean.properties) {
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

        write("constructor(private service:" + parentBean.restClientClassName);
        for (Bean selectableBean:selectableBeans.values()) {
        	write(", private " + selectableBean.serviceObjectName + ":" + selectableBean.restClientClassName);
		}
        
        writeLine(", private route: ActivatedRoute, private formBuilder: FormBuilder, private notifications: NotificationService) {");
        
        if (parentBean.idTsType.equals(DataType.INTEGER.getTsType())) {
        	writeLine("this.id = parseInt(this.route.snapshot.paramMap.get('id'));");
        } else {
        	writeLine("this.id = this.route.snapshot.paramMap.get('id');");
        }
        writeLine("this.activePath = '/" + parentBean.urlPiece + "/' + this.id.toString() + '/" + referenceBean.urlPiece + "';");
        writeLine("}");
	
	    skipLine();
        boolean start = true;
        writeLine("ngOnInit(): void {");
        writeLine("this.form = this.formBuilder.group({");
        for (ViewProperty property:this.referenceBean.formBean.properties) {
        	if (start) {
        		start = false;
        	} else {
        		writeLine(",");
        	}
        	write(property.name + ":[null");
        	if (!property.nullable && !property.dataType.equals(DataType.BOOLEAN)) {
        		write(", Validators.required");
        	}
        	write("]");
        }
        writeLine("})");
        for (ViewProperty property:this.referenceBean.formBean.properties) {
        	if (property.selectableBean!=null && property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {
        		writeLine("this.form.controls['" + property.name + "'].valueChanges.subscribe(value=>{this.searchOptionsFor" + property.capName + "(value)});");
        	}
        }
        writeLine("this.load();");
        writeLine("}");
        
        writeLine("restoreForm(): void {");
        writeLine("this.form.patchValue({");
        start = true;
        for (ViewProperty property:this.referenceBean.formBean.properties) {
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
        for (ViewProperty property:this.referenceBean.formBean.properties) {
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
        
        writeLine("load(): void {");
        writeLine("this.service.load" + referenceBean.className + "(this.id).subscribe((t) => {this.view=t;this.restoreForm();});");
        for (ViewProperty property:this.referenceBean.formBean.properties) {
        	if (property.selectableBean!=null && property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
        		writeLine("this.loadOptionsFor" + property.capName + "();");
        	}
        }
        writeLine("}");
        
        skipLine();
        
        for (ViewProperty property:this.referenceBean.formBean.properties) {
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
        writeLine("this.service.save" + referenceBean.className + "(this.id, this.view.form).subscribe(success => {this.notifications.info(\"Operation completed\");this.load();}, error => {this.notifications.error(\"Operation failed\")});");
        writeLine("this.load();");
        writeLine("}");
        skipLine();
        
        writeLine("update(): void {");
        writeLine("this.applyForm();");
        writeLine("this.service.update" + referenceBean.className + "(this.id, this.view.form).subscribe(success => {this.notifications.info(\"Operation completed\");this.load();}, error => {this.notifications.error(\"Operation failed\")});");
        writeLine("}");
        skipLine();
        
        writeLine("saveOrUpdate(): void {");
        writeLine("if (this.view.id == null) {this.save()} else {this.update()}");
        writeLine("}");
        skipLine();
        
        writeLine("delete(): void {");
        writeLine("this.service.delete" + referenceBean.className + "(this.id).subscribe(success => {this.notifications.info(\"Operation completed\");this.load();}, error => {this.notifications.error(\"Operation failed\")});");
        writeLine("}");
        skipLine();

        writeNotOverridableContent();
        
        writeLine("}");

    }
}
