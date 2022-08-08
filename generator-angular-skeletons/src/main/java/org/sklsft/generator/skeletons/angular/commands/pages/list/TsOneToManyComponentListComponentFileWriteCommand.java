package org.sklsft.generator.skeletons.angular.commands.pages.list;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.ui.FilterProperty;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsOneToManyComponentListComponentFileWriteCommand extends TsFileWriteCommand {

	private OneToManyComponent oneToManyComponent;
	private Bean referenceBean;
	private Bean parentBean;
	
	/*
	 * constructor
	 */
	public TsOneToManyComponentListComponentFileWriteCommand(OneToManyComponent oneToManyComponent) {
        
		super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToManyComponent.referenceBean.myPackage.model.tsUiArtefactName + File.separator + oneToManyComponent.referenceBean.myPackage.tsComponentsPath + File.separator + oneToManyComponent.parentBean.urlPiece + File.separator + oneToManyComponent.referenceBean.urlPiece + File.separator + "list", oneToManyComponent.referenceBean.urlPiece + "-list.component");
		
		this.oneToManyComponent = oneToManyComponent;
		this.referenceBean = oneToManyComponent.referenceBean;
		this.parentBean = oneToManyComponent.parentBean;
		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';");
		imports.add("import { MatTableDataSource } from '@angular/material/table';");
		imports.add("import { MatPaginator } from '@angular/material/paginator';");
		
		imports.add("import { " + parentBean.restClientClassName + " } from '../../services/" + parentBean.restClientClassName + "';");
		imports.add("import { ScrollForm } from \"src/app/core/models/ScrollForm\";");
		imports.add("import { ScrollView } from \"src/app/core/models/ScrollView\";");
		imports.add("import { SelectItem } from \"src/app/core/models/SelectItem\";");
		
		imports.add("import { " + oneToManyComponent.referenceBean.basicViewBean.className + " } from '" + oneToManyComponent.referenceBean.myPackage.tsModelsSourcePath + "/views/basic/" + oneToManyComponent.referenceBean.basicViewBean.className + "';");
		imports.add("import { " + oneToManyComponent.referenceBean.fullViewBean.className + " } from '" + oneToManyComponent.referenceBean.myPackage.tsModelsSourcePath + "/views/full/" + oneToManyComponent.referenceBean.fullViewBean.className + "';");
		imports.add("import { " + oneToManyComponent.referenceBean.formBean.className + " } from '" + oneToManyComponent.referenceBean.myPackage.tsModelsSourcePath + "/forms/" + oneToManyComponent.referenceBean.formBean.className + "';");
		imports.add("import { " + oneToManyComponent.referenceBean.basicViewBean.filter.className + " } from '" + oneToManyComponent.referenceBean.myPackage.tsModelsSourcePath + "/filters/" + oneToManyComponent.referenceBean.basicViewBean.filter.className + "';");
		imports.add("import { " + oneToManyComponent.referenceBean.basicViewBean.sortingClassName + " } from '" + oneToManyComponent.referenceBean.myPackage.tsModelsSourcePath + "/sortings/" + oneToManyComponent.referenceBean.basicViewBean.sortingClassName + "';");
		
		imports.add("import { FormBuilder, FormGroup, Validators } from '@angular/forms';");
		
		imports.add("import { MatSort } from '@angular/material/sort';");
		imports.add("import { MatDialog } from '@angular/material/dialog';");
		imports.add("import { StringUtils } from 'src/app/core/services/StringUtils';");
		imports.add("import { ActivatedRoute } from '@angular/router';");
		imports.add("import { NavLink } from 'src/app/core/models/nav-link';");
		imports.add("import { " + referenceBean.className + "ModalComponent } from './modal/" + referenceBean.urlPiece + "-modal.component';");
		imports.add("import { ConfirmationModalComponent } from 'src/app/core/components/confirmation-modal/confirmation-modal.component';");
		imports.add("import { NotificationService } from 'src/app/core/services/NotificationService';");
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated one to many component list component ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();
        
        writeLine("@Component({");
        writeLine("selector: 'app-" + referenceBean.urlPiece + "-list',");
        writeLine("templateUrl: './" + referenceBean.urlPiece + "-list.component.html',");
        writeLine("styleUrls: ['./" + referenceBean.urlPiece + "-list.component.scss']");
        writeLine("})");
        writeLine("export class " + this.referenceBean.className + "ListComponent implements OnInit, AfterViewInit {");
        skipLine();

        writeLine("id:" + oneToManyComponent.parentBean.idTsType + ";");
        writeLine("links:NavLink[];");
        writeLine("activePath:string;");
        writeLine("scrollForm: ScrollForm<" + referenceBean.basicViewBean.filter.className + ", " + referenceBean.basicViewBean.sortingClassName + "> = new ScrollForm();");
		writeLine("scrollView: ScrollView<" + referenceBean.basicViewBean.className + "> = new ScrollView();");
        writeLine("dataSource:MatTableDataSource<" + referenceBean.basicViewBean.className + ">;");
        writeLine("@ViewChild(MatPaginator) paginator: MatPaginator;");
        writeLine("@ViewChild(MatSort) sort: MatSort");
        writeLine("pageSizeOptions: number[] = [10, 20, 50, 100];");
        write("displayedColumns: string[] = [");
        for (ViewProperty property:referenceBean.basicViewBean.properties) {	
        	write("'" + property.name + "',");
        }
        write("'Actions'");
        writeLine("];");
        writeLine("filter: FormGroup;");
        skipLine();

        writeLine("constructor(private service:" + parentBean.restClientClassName + ", private route: ActivatedRoute, private formBuilder: FormBuilder, private dialog: MatDialog, private notifications: NotificationService) {");
        writeLine("this.id = parseInt(this.route.snapshot.paramMap.get('id'));");
        writeLine("this.activePath = '/" + oneToManyComponent.parentBean.urlPiece + "/' + this.id.toString() + '/" + oneToManyComponent.referenceBean.urlPiece + "/list';");
        write("this.links=[{text:'Details',path:'/" + oneToManyComponent.parentBean.urlPiece + "/' + this.id.toString()}");
        for (OneToManyComponent component:oneToManyComponent.parentBean.oneToManyComponentList) {
        	write(",{text:'" + component.referenceBean.listRendering + "',path:'/" + oneToManyComponent.parentBean.urlPiece + "/' + this.id.toString() + '/" + component.referenceBean.urlPiece + "/list'}");
        }
        writeLine("];");
        writeLine("}");

        writeLine("ngOnInit(): void {");
        writeLine("this.filter = this.formBuilder.group({");
        boolean start = true;
        for (FilterProperty property:referenceBean.basicViewBean.filter.properties) {
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
        writeLine("this.scrollForm.sorting = new " + referenceBean.basicViewBean.sortingClassName + "();");
        writeLine("switch (this.sort.active) {");
        for (ViewProperty property:this.referenceBean.basicViewBean.properties) {    
        	writeLine("case '" + property.name + "': this.scrollForm.sorting." + property.name + "OrderType = StringUtils.emptyToNull(this.sort.direction.toUpperCase());break;");
        }	      
        writeLine("}");
        writeLine("this.refresh();");
        writeLine("});");
        skipLine();
        
        for (FilterProperty property:this.referenceBean.basicViewBean.filter.properties) {
    		writeLine("this.filter.controls['" + property.name + "'].valueChanges.subscribe(value=>{");
    		writeLine("this.scrollForm.filter." + property.name + "=value;");
    		writeLine("this.refresh();");
    		writeLine("});");
        }
        writeLine("}");
        skipLine();
        
        writeLine("refresh(): void {");
        writeLine("this.service.scroll" + referenceBean.className + "(this.id, this.scrollForm).subscribe((t) => {");
        writeLine("this.scrollView=t;");
        writeLine("this.dataSource = new MatTableDataSource(this.scrollView.elements);");
        writeLine("});");
        writeLine("}");
        skipLine();
        
        writeLine("reset(): void {");
        writeLine("this.scrollForm = new ScrollForm();");
        writeLine("this.scrollForm.filter = new " + referenceBean.basicViewBean.filter.className + "();");
        writeLine("this.scrollForm.sorting = new " + referenceBean.basicViewBean.sortingClassName + "();");
        writeLine("this.scrollForm.page=1;");
        writeLine("this.scrollForm.elementsPerPage=10;");
        writeLine("this.filter.patchValue({");
        start = true;
        for (FilterProperty property:referenceBean.basicViewBean.filter.properties) {
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
        writeLine("this.service.create" + referenceBean.className + "(this.id).subscribe((t) => {");
        writeLine("let ref = this.dialog.open(" + referenceBean.className + "ModalComponent);");
        writeLine("ref.componentInstance.view = t;");
        writeLine("ref.componentInstance.parentId = this.id;");
        writeLine("ref.afterClosed().subscribe(result => {this.refresh();});");
        writeLine("});");
        writeLine("}");
        skipLine();
        
        writeLine("edit(id: " + referenceBean.idTsType + "): void {");
        writeLine("this.service.load" + referenceBean.className + "(id).subscribe((t) => {");
        writeLine("let ref = this.dialog.open(" + referenceBean.className + "ModalComponent);");
        writeLine("ref.componentInstance.view = t;");
        writeLine("ref.afterClosed().subscribe(result => {this.refresh();});");
        writeLine("});");
        writeLine("}");
        skipLine();
        
        writeLine("delete(id: " + referenceBean.idTsType + "): void {");
        writeLine("this.dialog.open(ConfirmationModalComponent).afterClosed().subscribe(result => {");
        writeLine("if (result) {");
        writeLine("this.service.delete" + referenceBean.className + "(id).subscribe(success => {this.notifications.info(\"Operation completed\");this.refresh()}, error => {this.notifications.error(\"Operation failed\")});");
        writeLine("}");
        writeLine("});");
        writeLine("}");
        skipLine();

        writeNotOverridableContent();
        
        writeLine("}");

    }
}
