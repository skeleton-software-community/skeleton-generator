package org.sklsft.generator.skeletons.angular.commands.pages.list;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
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
		
		imports.add("import { " + bean.basicViewBean.filterClassName + " } from '../models/" + bean.basicViewBean.filterClassName + "';");
		imports.add("import { " + bean.basicViewBean.sortingClassName + " } from '../models/" + bean.basicViewBean.sortingClassName + "';");
		
		imports.add("import { " + bean.listViewClassName + " } from '../views/" + bean.listViewClassName + "';");
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

        writeLine("view:" + bean.listViewClassName + " = new " + bean.listViewClassName + "();");
        writeLine("dataSource:MatTableDataSource<" + bean.basicViewBean.className + ">;");
        writeLine("@ViewChild(MatPaginator) paginator: MatPaginator;");
        writeLine("pageSizeOptions: number[] = [10, 20, 50, 100];");
        write("displayedColumns: string[] = [");
        for (ViewProperty property:bean.basicViewBean.properties) {	
        	write("'" + property.name + "',");
        }
        write("'Actions'");
        writeLine("];");
        skipLine();

        writeLine("constructor(private service:" + bean.restClientClassName + ") { }");

        writeLine("ngOnInit(): void {");
        writeLine("this.reset();");
        writeLine("}");
        
        writeLine("ngAfterViewInit(): void {");
        writeLine("this.paginator.page.subscribe(");
        writeLine("(event) => {");
        writeLine("this.view.scrollForm.page=event.pageIndex+1;");
        writeLine("this.view.scrollForm.elementsPerPage=event.pageSize;");
        writeLine("this.refresh();");
        writeLine("})");
        writeLine("}");
        
        writeLine("refresh(): void {");
        writeLine("this.service.scroll(this.view.scrollForm).subscribe((t) => {");
        writeLine("this.view.scrollView=t;");
        writeLine("this.dataSource = new MatTableDataSource(this.view.scrollView.elements);");
        writeLine("});");
        writeLine("}");
        
        writeLine("reset(): void {");
        writeLine("this.view = new " + bean.listViewClassName + "();");
        writeLine("this.view.scrollForm = new ScrollForm();");
        writeLine("this.view.scrollForm.filter = new " + bean.basicViewBean.filterClassName + "();");
        writeLine("this.view.scrollForm.sorting = new " + bean.basicViewBean.sortingClassName + "();");
        writeLine("this.view.scrollForm.page=1;");
        writeLine("this.view.scrollForm.elementsPerPage=10;");
        writeLine("this.refresh();");
        writeLine("}");

        writeNotOverridableContent();
        
        writeLine("}");

    }
}
