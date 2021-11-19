package org.sklsft.generator.skeletons.angular.commands.pages.list;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.FilterProperty;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsListComponentFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public TsListComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "list", bean.urlPiece + "-list.component");
		
		this.bean = bean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';");
		imports.add("import { MatTableDataSource } from '@angular/material/table';");
		imports.add("import { MatPaginator } from '@angular/material/paginator';");
		
		imports.add("import { " + bean.restClientClassName + " } from '../services/" + bean.restClientClassName + "';");
		imports.add("import { ScrollForm } from \"src/app/core/models/ScrollForm\";");
		imports.add("import { ScrollView } from \"src/app/core/models/ScrollView\";");
		imports.add("import { SelectItem } from \"src/app/core/models/SelectItem\";");
		
		imports.add("import { " + bean.basicViewBean.className + " } from '../models/" + bean.basicViewBean.className + "';");
		imports.add("import { " + bean.fullViewBean.className + " } from '../models/" + bean.fullViewBean.className + "';");
		imports.add("import { " + bean.formBean.className + " } from '../models/" + bean.formBean.className + "';");
		
		imports.add("import { " + bean.basicViewBean.filter.className + " } from '../models/" + bean.basicViewBean.filter.className + "';");
		imports.add("import { " + bean.basicViewBean.sortingClassName + " } from '../models/" + bean.basicViewBean.sortingClassName + "';");
		
		imports.add("import { FormBuilder, FormGroup, Validators } from '@angular/forms';");
		
		imports.add("import { MatSort } from '@angular/material/sort';");
		imports.add("import { MatDialog } from '@angular/material/dialog';");
		imports.add("import { StringUtils } from 'src/app/core/services/StringUtils';");
		imports.add("import { " + bean.className + "ModalComponent } from './modal/" + bean.urlPiece + "-modal.component';");
		imports.add("import { ConfirmationModalComponent } from 'src/app/core/components/confirmation-modal/confirmation-modal.component';");
		imports.add("import { NotificationService } from 'src/app/core/services/NotificationService';");
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated list component ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();
        
        writeLine("@Component({");
        writeLine("selector: 'app-" + bean.urlPiece + "-list',");
        writeLine("templateUrl: './" + bean.urlPiece + "-list.component.html',");
        writeLine("styleUrls: ['./" + bean.urlPiece + "-list.component.scss']");
        writeLine("})");
        writeLine("export class " + this.bean.className + "ListComponent implements OnInit, AfterViewInit {");
        skipLine();

        writeLine("scrollForm: ScrollForm<" + bean.basicViewBean.filter.className + ", " + bean.basicViewBean.sortingClassName + "> = new ScrollForm();");
		writeLine("scrollView: ScrollView<" + bean.basicViewBean.className + "> = new ScrollView();");
        writeLine("dataSource:MatTableDataSource<" + bean.basicViewBean.className + ">;");
        writeLine("@ViewChild(MatPaginator) paginator: MatPaginator;");
        writeLine("@ViewChild(MatSort) sort: MatSort");
        writeLine("pageSizeOptions: number[] = [10, 20, 50, 100];");
        write("displayedColumns: string[] = [");
        for (ViewProperty property:bean.basicViewBean.properties) {	
        	write("'" + property.name + "',");
        }
        write("'Actions'");
        writeLine("];");
        writeLine("filter: FormGroup;");
        skipLine();

        writeLine("constructor(private service:" + bean.restClientClassName + ", private formBuilder: FormBuilder, private dialog: MatDialog, private notifications: NotificationService) { }");

        writeLine("ngOnInit(): void {");
        writeLine("this.filter = this.formBuilder.group({");
        boolean start = true;
        for (FilterProperty property:this.bean.basicViewBean.filter.properties) {
        	if (start) {
        		start = false;
        	} else {
        		writeLine(",");
        	}
        	write(property.name + ":[null]");
        }
        writeLine("})");
        writeLine("this.reset();");
        writeLine("}");
        skipLine();
        
        writeLine("ngAfterViewInit(): void {");
        
        writeLine("this.paginator.page.subscribe(");
        writeLine("(event) => {");
        writeLine("this.scrollForm.page=event.pageIndex+1;");
        writeLine("this.scrollForm.elementsPerPage=event.pageSize;");
        writeLine("this.refresh();");        
        writeLine("});");
        skipLine();
        
        writeLine("this.sort.sortChange.subscribe(");
        writeLine("(event) => {");
        writeLine("this.scrollForm.sorting = new " + bean.basicViewBean.sortingClassName + "();");
        writeLine("switch (this.sort.active) {");
        for (ViewProperty property:this.bean.basicViewBean.properties) {    
        	writeLine("case '" + property.name + "': this.scrollForm.sorting." + property.name + "OrderType = StringUtils.emptyToNull(this.sort.direction.toUpperCase());break;");
        }	      
        writeLine("}");
        writeLine("this.refresh();");
        writeLine("});");
        skipLine();
        
        for (FilterProperty property:this.bean.basicViewBean.filter.properties) {
    		writeLine("this.filter.controls['" + property.name + "'].valueChanges.subscribe(value=>{");
    		writeLine("this.scrollForm.filter." + property.name + "=value;");
    		writeLine("this.refresh();");
    		writeLine("});");
        }
        writeLine("}");
        skipLine();
        
        writeLine("refresh(): void {");
        writeLine("this.service.scroll(this.scrollForm).subscribe((t) => {");
        writeLine("this.scrollView=t;");
        writeLine("this.dataSource = new MatTableDataSource(this.scrollView.elements);");
        writeLine("});");
        writeLine("}");
        skipLine();
        
        writeLine("reset(): void {");
        writeLine("this.scrollForm = new ScrollForm();");
        writeLine("this.scrollForm.filter = new " + bean.basicViewBean.filter.className + "();");
        writeLine("this.scrollForm.sorting = new " + bean.basicViewBean.sortingClassName + "();");
        writeLine("this.scrollForm.page=1;");
        writeLine("this.scrollForm.elementsPerPage=10;");
        writeLine("this.filter.patchValue({");
        start = true;
        for (FilterProperty property:this.bean.basicViewBean.filter.properties) {
        	if (start) {
        		start = false;
        	} else {
        		writeLine(",");
        	}
            write(property.name + ": [null]");
        }
        skipLine();    
        writeLine("})");
        writeLine("this.refresh();");
        writeLine("}");
        skipLine();
        
        writeLine("create(): void {");
        writeLine("this.service.create().subscribe((t) => {");
        writeLine("let ref = this.dialog.open(" + bean.className + "ModalComponent);");
        writeLine("ref.componentInstance.view = t;");
        writeLine("ref.afterClosed().subscribe(result => {this.refresh();});");
        writeLine("});");
        writeLine("}");
        skipLine();
        
        writeLine("edit(id: " + bean.idTsType + "): void {");
        writeLine("this.service.load(id).subscribe((t) => {");
        writeLine("let ref = this.dialog.open(" + bean.className + "ModalComponent);");
        writeLine("ref.componentInstance.view = t;");
        writeLine("ref.afterClosed().subscribe(result => {this.refresh();});");
        writeLine("});");
        writeLine("}");
        skipLine();
        
        writeLine("delete(id: " + bean.idTsType + "): void {");
        writeLine("this.dialog.open(ConfirmationModalComponent).afterClosed().subscribe(result => {");
        writeLine("if (result) {");
        writeLine("this.service.delete(id).subscribe(success => {this.notifications.info(\"Operation completed\");this.refresh()}, error => {this.notifications.error(\"Operation failed\")});");
        writeLine("}");
        writeLine("});");
        writeLine("}");
        skipLine();

        writeNotOverridableContent();
        
        writeLine("}");

    }
}
