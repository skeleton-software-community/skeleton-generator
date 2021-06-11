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
		imports.add("import { Component, OnInit, ViewChild } from '@angular/core';");
		imports.add("import { MatTableDataSource } from '@angular/material/table';");
		imports.add("import { " + bean.basicViewBean.className + " } from '../models/" + bean.basicViewBean.className + "';");
		imports.add("import { " + bean.baseRestClientClassName + " } from '../services/" + bean.baseRestClientClassName + "';");
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
        writeLine("export class " + this.bean.className + "ListComponent implements OnInit {");
        skipLine();

        writeLine("list:" + bean.basicViewBean.className + "[];");
        writeLine("dataSource:MatTableDataSource<" + bean.basicViewBean.className + ">;");
        write("displayedColumns: string[] = [");
        
        for (ViewProperty property:bean.basicViewBean.properties) {
        	
        	write("'" + property.name + "',");
        }
        write("'Actions'");
        writeLine("];");
        skipLine();

        writeLine("constructor(private service:" + bean.baseRestClientClassName + ") { }");

        writeLine("ngOnInit(): void {");
        writeLine("this.service.loadList().subscribe((t) => {");
        writeLine("this.list=t;");
        writeLine("this.dataSource = new MatTableDataSource(this.list);");
        writeLine("});");
        writeLine("}");

        writeNotOverridableContent();
        
        writeLine("}");

    }
}
