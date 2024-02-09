package org.sklsft.generator.skeletons.angular.commands.pages.menu;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;


public class TsMenuComponentFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	private Map<String, Bean> selectableBeans = new HashMap<>();
	/*
	 * constructor
	 */
	public TsMenuComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsComponentsPath + File.separator + bean.urlPiece + File.separator + "menu", bean.urlPiece + "-menu.component");
		
		this.bean = bean;
		
		for (ViewProperty property:this.bean.formBean.properties) {
        	if (property.selectableBean!=null) {
        		selectableBeans.put(property.selectableBean.className, property.selectableBean);
        	}
		}		
	}
	
	@Override
	protected void fetchSpecificImports() {
		imports.add("import { Component, Input, OnInit } from '@angular/core';");
		imports.add("import { NavLink } from 'src/app/templates/private/models/nav-link';");
	}
	
	
	@Override
	protected void writeContent() throws IOException {

        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated menu component ts file");
        writeLine(" * <br/>write modifications between specific code marks");
        writeLine(" * <br/>processed by skeleton-generator");
        writeLine(" */");
        skipLine();
        
        writeLine("@Component({");
        writeLine("selector: 'app-" + bean.urlPiece + "-menu',");
        writeLine("templateUrl: './" + bean.urlPiece + "-menu.component.html',");
        writeLine("styleUrls: ['./" + bean.urlPiece + "-menu.component.scss']");
        writeLine("})");
        writeLine("export class " + this.bean.className + "MenuComponent implements OnInit {");
        skipLine();

        writeLine("links:NavLink[];");
        writeLine("@Input() id:" + bean.idTsType + ";");        
        writeLine("@Input() activePath:string;");
	
	    skipLine();
        writeLine("ngOnInit(): void {");
        write("this.links=[{text:'Details',path:'/" + bean.urlPiece + "/' + this.id.toString()}");
	    for (OneToManyComponent component:bean.oneToManyComponentList) {
	    	write(",{text:'" + component.referenceBean.listRendering + "',path:'/" + bean.urlPiece + "/' + this.id.toString() + '/" + component.referenceBean.urlPiece + "/list'}");
	    }
	    for (OneToMany component:bean.oneToManyList) {
	    	write(",{text:'" + component.referenceBean.listRendering + "',path:'/" + bean.urlPiece + "/' + this.id.toString() + '/" + component.referenceBean.urlPiece + "/list'}");
	    }
	    for (OneToOneComponent component:bean.oneToOneComponentList) {
	    	write(",{text:'" + component.referenceBean.detailRendering + "',path:'/" + bean.urlPiece + "/' + this.id.toString() + '/" + component.referenceBean.urlPiece + "'}");
	    }
	    writeLine("];");
        writeLine("}");
        writeLine("}");

    }
}
