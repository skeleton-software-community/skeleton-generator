package org.sklsft.generator.skeletons.angular.commands.services;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;

public class TsRestClientFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	
	public TsRestClientFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "services", bean.restClientClassName);
		
		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		imports.add("import { environment } from 'src/environments/environment';");
		imports.add("import { Injectable } from '@angular/core';");
		imports.add("import { HttpClient, HttpHeaders} from '@angular/common/http';");
		
		imports.add("import { ScrollForm } from \"src/app/core/models/ScrollForm\";");
		imports.add("import { ScrollView } from \"src/app/core/models/ScrollView\";");
		imports.add("import { SelectItem } from \"src/app/core/models/SelectItem\";");
		
		imports.add("import { " + bean.basicViewBean.className + " } from '../models/" + bean.basicViewBean.className + "';");
		imports.add("import { " + bean.fullViewBean.className + " } from '../models/" + bean.fullViewBean.className + "';");
		imports.add("import { " + bean.formBean.className + " } from '../models/" + bean.formBean.className + "';");
		
		imports.add("import { " + bean.basicViewBean.filter.className + " } from '../models/" + bean.basicViewBean.filter.className + "';");
		imports.add("import { " + bean.basicViewBean.sortingClassName + " } from '../models/" + bean.basicViewBean.sortingClassName + "';");
		
	}

	@Override
	protected void writeContent() throws IOException {
			
		
		writeImports();
		skipLine();
		
		writeLine("/**");
		writeLine(" * auto generated rest client ts file"); 
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@Injectable({providedIn:'root'})");
		writeLine("export class " + this.bean.restClientClassName + " {");
		skipLine();
		
		
		writeLine("private httpOptions = {headers:new HttpHeaders({'Content-Type':'application/json'})};");
		skipLine();
		
		writeLine("constructor(private http: HttpClient) { }");
		
		if (this.bean.selectable) {
			createGetOptions();
		}
		createLoadObjectList();
		createScroll();
		createLoadObject();
		if (bean.cardinality>0) {
			//createFindObject();
		}
//		createLoadOneToOneComponent();
//		createLoadOneToManyComponentList();
//		createScrollOneToManyComponent();
//		createLoadOneToManyComponent();
//		createCreateObject();
//		createCreateOneToManyComponent();
		createSaveObject();
//		createSaveOneToOneComponent();
//		createSaveOneToManyComponent();
		createUpdateObject();
//		createUpdateOneTOneComponent();
//		createUpdateOneToManyComponent();
//		createDeleteObject();
//		createDeleteOneToOneComponent();
//		createDeleteOneToManyComponent();
//		createDeleteObjectList();
//		createDeleteOneToManyComponentList();
		
		writeLine("}");

    }
	
	private void createGetOptions() {
		
		Property targetProperty = bean.selectionBehavior.targetProperty;
		Property labelProperty = bean.selectionBehavior.labelProperty!=null?bean.selectionBehavior.labelProperty:bean.selectionBehavior.targetProperty;
		
		if (bean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
			
			writeLine("/**");
			writeLine(" * get options");
			writeLine(" */");
			writeLine("public getOptions() {");
			writeLine("return this.http.get<SelectItem[]>(environment.restApiUrl + '/" + bean.urlPiece + "/options');");
			writeLine("}");
			skipLine();
		}
		if (bean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {			
			writeLine("/**");
			writeLine(" * get options");
			writeLine(" */");
			writeLine("public searchOptions(arg:string) {");
			writeLine("return this.http.post<SelectItem[]>(environment.restApiUrl + '/" + bean.urlPiece + "/options/search', arg, this.httpOptions);");
			writeLine("}");
			skipLine();
		}
	}		
		

    private void createLoadObjectList() {
		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("public loadList() {");
		writeLine("return this.http.get<" + this.bean.basicViewBean.className + "[]>(environment.restApiUrl + '/" + bean.urlPiece + "/list');");
		writeLine("}");
		skipLine();
		
		for (Property property : this.bean.properties) {
		    if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
		        writeLine("/**");
		        writeLine(" * load object list from " + property.name);
		        writeLine(" */");
		        writeLine("public loadListFrom" + property.capName + " (" + property.name + "Id: " + property.referenceBean.idTsType + ") {");
		        writeLine("return this.http.get<" + this.bean.basicViewBean.className + "[]>(environment.restApiUrl + '/" + bean.urlPiece + "/' + " + property.name + "Id + '/" + property.referenceBean.urlPiece + "/list');");
				writeLine("}");
		        skipLine();
		    }
		}
    }
    
    private void createScroll() {

		writeLine("/**");
		writeLine(" * scroll object list");
		writeLine(" */");
		writeLine("public scroll(form: ScrollForm<" + bean.basicViewBean.filter.className + ", " + bean.basicViewBean.sortingClassName + ">) {");
		writeLine("return this.http.post<ScrollView<" + this.bean.basicViewBean.className + ">>(environment.restApiUrl + '/" + bean.urlPiece + "/scroll', form, this.httpOptions);");
		writeLine("}");
		skipLine();
		
		for (Property property : this.bean.properties) {
		    if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
		        writeLine("/**");
		        writeLine(" * scroll object list from " + property.name);
		        writeLine(" */");
				writeLine("public scrollFrom" + property.capName + " (" + property.name + "Id: " + property.referenceBean.idTsType + ", form: ScrollForm<" + bean.basicViewBean.filter.className + ", " + bean.basicViewBean.sortingClassName + ">) {");
				writeLine("return this.http.post<ScrollView<" + this.bean.basicViewBean.className + ">>(environment.restApiUrl + '/" + bean.urlPiece + "/' + " + property.name + "Id + '/" + property.referenceBean.urlPiece + "/scroll', form, this.httpOptions);");
				writeLine("}");
				skipLine();
		    }
		}
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
    
    
    private void createSaveObject() {
        writeLine("/**");
        writeLine(" * save object");        
        writeLine(" */");
        writeLine("public save(form: " + this.bean.formBean.className + ") {");
        writeLine("return this.http.post<number>(environment.restApiUrl + '/" + bean.urlPiece + "', form, this.httpOptions);");
        writeLine("}");
        skipLine();
    }
    
    
    private void createUpdateObject() {
        writeLine("/**");
        writeLine(" * update object");        
        writeLine(" */");
        writeLine("public update(id: " + bean.idTsType + ", form: " + this.bean.formBean.className + ") {");
        writeLine("return this.http.put(environment.restApiUrl + '/" + bean.urlPiece + "/' + id, form, this.httpOptions);");
        writeLine("}");
        skipLine();
    }
    
}
