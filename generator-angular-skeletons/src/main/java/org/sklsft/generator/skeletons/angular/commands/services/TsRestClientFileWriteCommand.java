package org.sklsft.generator.skeletons.angular.commands.services;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.TsFileWriteCommand;

public class TsRestClientFileWriteCommand extends TsFileWriteCommand {

	private Bean bean;
	
	public TsRestClientFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsServicesPath, bean.restClientClassName);
		
		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		imports.add("import { environment } from 'src/environments/environment';");
		imports.add("import { Injectable } from '@angular/core';");
		imports.add("import { HttpClient, HttpHeaders} from '@angular/common/http';");
		
		imports.add("import { ScrollForm } from 'src/app/core/models/ScrollForm';");
		imports.add("import { ScrollView } from 'src/app/core/models/ScrollView';");
		imports.add("import { SelectItem } from 'src/app/core/models/SelectItem';");
		
		imports.add("import { " + bean.basicViewBean.className + " } from '" + bean.myPackage.tsModelsSourcePath + "/views/basic/" + bean.basicViewBean.className + "';");
		imports.add("import { " + bean.fullViewBean.className + " } from '" + bean.myPackage.tsModelsSourcePath + "/views/full/" + bean.fullViewBean.className + "';");
		imports.add("import { " + bean.formBean.className + " } from '" + bean.myPackage.tsModelsSourcePath + "/forms/" + bean.formBean.className + "';");
		imports.add("import { " + bean.basicViewBean.filter.className + " } from '" + bean.myPackage.tsModelsSourcePath + "/filters/" + bean.basicViewBean.filter.className + "';");
		imports.add("import { " + bean.basicViewBean.sortingClassName + " } from '" + bean.myPackage.tsModelsSourcePath + "/sortings/" + bean.basicViewBean.sortingClassName + "';");
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			imports.add("import { " + oneToManyComponent.referenceBean.basicViewBean.className + " } from '" + oneToManyComponent.referenceBean.myPackage.tsModelsSourcePath + "/views/basic/" + oneToManyComponent.referenceBean.basicViewBean.className + "';");
			imports.add("import { " + oneToManyComponent.referenceBean.fullViewBean.className + " } from '" + oneToManyComponent.referenceBean.myPackage.tsModelsSourcePath + "/views/full/" + oneToManyComponent.referenceBean.fullViewBean.className + "';");
			imports.add("import { " + oneToManyComponent.referenceBean.formBean.className + " } from '" + oneToManyComponent.referenceBean.myPackage.tsModelsSourcePath + "/forms/" + oneToManyComponent.referenceBean.formBean.className + "';");
			imports.add("import { " + oneToManyComponent.referenceBean.basicViewBean.filter.className + " } from '" + oneToManyComponent.referenceBean.myPackage.tsModelsSourcePath + "/filters/" + oneToManyComponent.referenceBean.basicViewBean.filter.className + "';");
			imports.add("import { " + oneToManyComponent.referenceBean.basicViewBean.sortingClassName + " } from '" + oneToManyComponent.referenceBean.myPackage.tsModelsSourcePath + "/sortings/" + oneToManyComponent.referenceBean.basicViewBean.sortingClassName + "';");
		}
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList) {
			imports.add("import { " + oneToOneComponent.referenceBean.fullViewBean.className + " } from '" + oneToOneComponent.referenceBean.myPackage.tsModelsSourcePath + "/views/full/" + oneToOneComponent.referenceBean.fullViewBean.className + "';");
			imports.add("import { " + oneToOneComponent.referenceBean.formBean.className + " } from '" + oneToOneComponent.referenceBean.myPackage.tsModelsSourcePath + "/forms/" + oneToOneComponent.referenceBean.formBean.className + "';");
		}
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
//		if (bean.cardinality>0) {
//			createFindObject();
//		}
		createLoadOneToOneComponent();
		createLoadOneToManyComponentList();
		createScrollOneToManyComponent();
		createLoadOneToManyComponent();
		createCreateObject();
		createCreateOneToManyComponent();
		createSaveObject();
		createSaveOneToOneComponent();
		createSaveOneToManyComponent();
		createUpdateObject();
		createUpdateOneTOneComponent();
		createUpdateOneToManyComponent();
		createDeleteObject();
		createDeleteOneToOneComponent();
		createDeleteOneToManyComponent();
//		createDeleteObjectList();
//		createDeleteOneToManyComponentList();
		
		skipLine();
		writeNotOverridableContent();
		skipLine();
		
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
		        writeLine("return this.http.get<" + this.bean.basicViewBean.className + "[]>(environment.restApiUrl + '/" + property.referenceBean.urlPiece + "/' + " + property.name + "Id + '/" + bean.urlPiece + "/list');");
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
				writeLine("return this.http.post<ScrollView<" + this.bean.basicViewBean.className + ">>(environment.restApiUrl + '/" + property.referenceBean.urlPiece + "/' + " + property.name + "Id + '/" + bean.urlPiece + "/scroll', form, this.httpOptions);");
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
    
    
    private void createCreateObject() {
		writeLine("/**");
		writeLine(" * create object");
		writeLine(" */");
		writeLine("public create() {");	
        writeLine("return this.http.get<" + this.bean.fullViewBean.className + ">(environment.restApiUrl + '/" + bean.urlPiece + "/new');");
		writeLine("}");
		skipLine();

	}
    
    
    private void createSaveObject() {
        writeLine("/**");
        writeLine(" * save object");        
        writeLine(" */");
        writeLine("public save(form: " + this.bean.formBean.className + ") {");
        writeLine("return this.http.post<" + bean.idTsType + ">(environment.restApiUrl + '/" + bean.urlPiece + "', form, this.httpOptions);");
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
    
    
    private void createDeleteObject() {
        writeLine("/**");
        writeLine(" * delete object");        
        writeLine(" */");
        writeLine("public delete(id: " + bean.idTsType + ") {");
        writeLine("return this.http.delete(environment.restApiUrl + '/" + bean.urlPiece + "/' + id);");
        writeLine("}");
        skipLine();
    }
    
    
    private void createLoadOneToManyComponentList() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.objectName + " list");
            writeLine(" */");
            writeLine("public load" + currentBean.className + "List(id:" + bean.idTsType + ") {");
           
            writeLine("return this.http.get<" + currentBean.basicViewBean.className + "[]>(environment.restApiUrl + '/" + bean.urlPiece + "/' + id + '/" + currentBean.urlPiece + "/list', this.httpOptions);");
            writeLine("}");
            skipLine();
        }
    }
    
    private void createScrollOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * scroll one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public scroll" + currentBean.className + " (id: " + bean.idTsType + ", form: ScrollForm<" + currentBean.basicViewBean.filter.className + ", " + currentBean.basicViewBean.sortingClassName + ">) {");
			writeLine("return this.http.post<ScrollView<" + currentBean.basicViewBean.className + ">>(environment.restApiUrl + '/" + bean.urlPiece + "/' + id + '/" + currentBean.urlPiece + "/scroll', form, this.httpOptions);");
			writeLine("}");
			skipLine();
		}
	}
    
    private void createLoadOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("public load" + currentBean.className + "(id: " + bean.idTsType + ") {");            
            writeLine("return this.http.get<" + currentBean.fullViewBean.className + ">(environment.restApiUrl + '/" + currentBean.urlPiece + "/' + id, this.httpOptions);");
            writeLine("}");
            skipLine();
        }
    }
    
    private void createLoadOneToOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("public load" + currentBean.className + "(id: " + bean.idTsType + ") {");            
            writeLine("return this.http.get<" + currentBean.fullViewBean.className + ">(environment.restApiUrl + '/" + bean.urlPiece + "/' + id + '/" + currentBean.urlPiece + "', this.httpOptions);");
            writeLine("}");
            skipLine();
        }
    }


    private void createCreateOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * create one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("public create" + currentBean.className + "(id:" + bean.idTsType + ") {");
            writeLine("return this.http.get<" + currentBean.fullViewBean.className + ">(environment.restApiUrl + '/" + bean.urlPiece + "/' + id + '/" + currentBean.urlPiece + "/new', this.httpOptions);");
            writeLine("}");
            skipLine();
            skipLine();
        }
    }
    
    private void createSaveOneToOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * save one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("public save" + currentBean.className + "(id:" + bean.idTsType + ", form: " + currentBean.formBean.className + ") {");
            writeLine("return this.http.post<" + currentBean.idTsType + ">(environment.restApiUrl + '/" + bean.urlPiece + "/' + id + '/" + currentBean.urlPiece + "', form, this.httpOptions);");
            writeLine("}");
            skipLine();
        }
    }

    private void createSaveOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * save one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("public save" + currentBean.className + "(id:" + bean.idTsType + ", form: " + currentBean.formBean.className + ") {");
            writeLine("return this.http.post<" + currentBean.idTsType + ">(environment.restApiUrl + '/" + bean.urlPiece + "/' + id + '/" + currentBean.urlPiece + "', form, this.httpOptions);");
            writeLine("}");
            skipLine();
        }
    }

    private void createUpdateOneTOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * update one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("public update" + currentBean.className + "(id: " + bean.idTsType + ", form: " + currentBean.formBean.className + ") {");
            writeLine("return this.http.put(environment.restApiUrl + '/" + bean.urlPiece + "/' + id + '/" + currentBean.urlPiece + "', form, this.httpOptions);");
            writeLine("}");
            skipLine();
        }
    }

    private void createUpdateOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * update one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("public update" + currentBean.className + "(id: " + currentBean.idTsType + ", form: " + currentBean.formBean.className + ") {");
            writeLine("return this.http.put(environment.restApiUrl + '/" + currentBean.urlPiece + "/' + id, form, this.httpOptions);");
            writeLine("}");
            skipLine();
        }
    }

    private void createDeleteOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");            
            writeLine(" * delete one to many component " + currentBean.objectName);            
            writeLine(" */");
            writeLine("public delete" + currentBean.className + "(id: " + currentBean.idTsType + ") {");
            writeLine("return this.http.delete(environment.restApiUrl + '/" + currentBean.urlPiece + "/' + id);");
            writeLine("}");
            skipLine();
        }
    }
    
    private void createDeleteOneToOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");            
            writeLine(" * delete one to one component " + currentBean.objectName);            
            writeLine(" */");
            writeLine("public delete" + currentBean.className + "(id: " + bean.idTsType + ") {");
            writeLine("return this.http.delete(environment.restApiUrl + '/" + bean.urlPiece + "/' + id + '/" + currentBean.urlPiece + "');");
            writeLine("}");
            skipLine();
        }
    }
}
