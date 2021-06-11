package org.sklsft.generator.skeletons.angular.commands.services;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;

public class TsBaseRestClientFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	
	public TsBaseRestClientFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "services", bean.baseRestClientClassName);
		
		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		imports.add("import { environment } from 'src/environments/environment';");
		imports.add("import { Injectable } from '@angular/core';");
		imports.add("import { HttpClient, HttpHeaders} from '@angular/common/http';");
		imports.add("import { " + bean.basicViewBean.className + " } from '../models/" + bean.basicViewBean.className + "';");
		imports.add("import { " + bean.fullViewBean.className + " } from '../models/" + bean.fullViewBean.className + "';");
		
	}

	@Override
	protected void writeContent() throws IOException {
			
		
		writeImports();
		skipLine();
		
		writeLine("/**");
		writeLine(" * auto generated base rest client class file"); 
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Injectable({providedIn:'root'})");
		writeLine("export class " + this.bean.baseRestClientClassName + " {");
		skipLine();
		
		
		writeLine("private httpOptions = {headers:new HttpHeaders({'Content-Type':'application/json'})};");
		skipLine();
		
		writeLine("constructor(private http: HttpClient) { }");
		
		createLoadObjectList();
		createLoadObject();
		
		writeLine("}");

    }
	
			
		

    private void createLoadObjectList() {
		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("public loadList() {");
		writeLine("return this.http.get<" + this.bean.basicViewBean.className + "[]>(environment.restApiUrl + '/" + bean.urlPiece + "/list');");
		writeLine("}");
		skipLine();
    }
    
    private void createLoadObject() {
		writeLine("/**");
		writeLine(" * load object");
		writeLine(" */");
		writeLine("public load(id:" + bean.idTsType + ") {");	
        writeLine("return this.http.get<" + this.bean.fullViewBean.className + ">(environment.restApiUrl + '/" + bean.urlPiece + "/' + id);");
		writeLine("}");
		skipLine();

	}
    
}
