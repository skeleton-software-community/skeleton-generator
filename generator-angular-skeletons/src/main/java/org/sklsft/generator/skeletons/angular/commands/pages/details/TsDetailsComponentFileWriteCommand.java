package org.sklsft.generator.skeletons.angular.commands.pages.details;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsDetailsComponentFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	private Map<String, Bean> selectableBeans = new HashMap<>();
	/*
	 * constructor
	 */
	public TsDetailsComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "details", bean.urlPiece + "-details.component");
		
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
		imports.add("import { ActivatedRoute } from '@angular/router';");
		imports.add("import { NavLink } from 'src/app/core/models/nav-link';");
		imports.add("import { " + bean.fullViewBean.className + " } from '../models/" + bean.fullViewBean.className + "';");
		imports.add("import { " + bean.restClientClassName + " } from '../services/" + bean.restClientClassName + "';");
		imports.add("import { FormBuilder, FormGroup, Validators } from '@angular/forms';");
		imports.add("import { NotificationService } from 'src/app/core/services/NotificationService';");
		for (ViewProperty property:this.bean.formBean.properties) {
        	if (property.selectableBean!=null) {
        		imports.add("import { " + property.selectableBean.restClientClassName + " } from 'src/app/features/generated/" + property.selectableBean.myPackage.name.replace(".","/") + "/" + property.selectableBean.urlPiece + "/" + "services" + "/" + property.selectableBean.restClientClassName + "';");
        	}
		}
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
        writeLine("links:NavLink[];");
        writeLine("activePath:string;");
        writeLine("view: " + bean.fullViewBean.className + " = new " + bean.fullViewBean.className + "();");
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
        
        writeLine(", private route: ActivatedRoute, private formBuilder: FormBuilder, private notifications: NotificationService) {");
        
        writeLine("this.id = parseInt(this.route.snapshot.paramMap.get('id'));");
		writeLine("this.activePath = '/" + bean.urlPiece + "/' + this.id.toString();");
	    write("this.links=[{text:'Details',path:'/" + bean.urlPiece + "/' + this.id.toString()}");
	    for (OneToManyComponent component:bean.oneToManyComponentList) {
	    	write(",{text:'" + component.referenceBean.listRendering + "',path:'/" + bean.urlPiece + "/' + this.id.toString() + '/" + component.referenceBean.urlPiece + "/list'}");
	    }
	    writeLine("];");
	    writeLine("}");
	
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
        	write(property.name + ":[null");
        	if (!property.nullable) {
        		write(", Validators.required");
        	}
        	write("]");
        }
        writeLine("})");
        for (ViewProperty property:this.bean.formBean.properties) {
        	if (property.selectableBean!=null && property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {
        		writeLine("this.form.controls['" + property.name + "'].valueChanges.subscribe(value=>{this.searchOptionsFor" + property.capName + "(value)});");
        	}
        }
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
        	writeLine("this.view.form." + property.name + " = StringUtils.emptyToNull(this.form.get('" + property.name + "').value);");
        }
        writeLine("}");
        skipLine();
        
        writeLine("load(): void {");
        writeLine("this.service.load(this.id).subscribe((t) => {this.view=t;this.restoreForm();});");
        for (ViewProperty property:this.bean.formBean.properties) {
        	if (property.selectableBean!=null && property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
        		writeLine("this.loadOptionsFor" + property.capName + "();");
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
        
        writeLine("update(): void {");
        writeLine("this.applyForm();");
        writeLine("this.service.update(this.id, this.view.form).subscribe(success => this.notifications.info(\"Operation completed\"), error => {this.notifications.error(\"Operation failed\")});");
        writeLine("this.load();");
        writeLine("}");

        writeNotOverridableContent();
        
        writeLine("}");

    }
}
