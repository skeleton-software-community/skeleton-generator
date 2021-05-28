package org.sklsft.generator.skeletons.rest.commands;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class BaseRestClientFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	
	public BaseRestClientFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.restClientArtefactName + File.separator + bean.myPackage.model.javaSourcesFolder + File.separator
				+ bean.myPackage.baseRestClientPackageName.replace(".", File.separator), bean.baseRestClientClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.time.LocalDate;");
		javaImports.add("import java.math.BigDecimal;");
		javaImports.add("import java.util.Collection;");
        javaImports.add("import java.util.List;");
        javaImports.add("import java.util.ArrayList;");
        javaImports.add("import java.util.Arrays;");
        javaImports.add("import java.util.HashMap;");
        javaImports.add("import java.util.Map;");
        javaImports.add("import javax.annotation.Resource;");
        javaImports.add("import javax.inject.Inject;");
        javaImports.add("import org.sklsft.commons.api.model.ScrollForm;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollView;");
		javaImports.add("import org.sklsft.commons.api.model.SelectItem;");
		javaImports.add("import org.sklsft.commons.rest.client.RestClient;");
		javaImports.add("import org.springframework.core.ParameterizedTypeReference;");

        javaImports.add("import " + this.bean.myPackage.basicViewsPackageName + "." + this.bean.basicViewBean.className + ";");
        javaImports.add("import " + this.bean.myPackage.fullViewsPackageName + "." + this.bean.fullViewBean.className + ";");
        javaImports.add("import " + this.bean.myPackage.formsPackageName + "." + this.bean.formBean.className + ";");
        javaImports.add("import " + bean.myPackage.filtersPackageName + "." + bean.basicViewBean.filterClassName + ";");
		javaImports.add("import " + bean.myPackage.sortingsPackageName + "." + bean.basicViewBean.sortingClassName + ";");
        
        javaImports.add("import " + this.bean.myPackage.baseServiceInterfacePackageName + "." + this.bean.baseServiceInterfaceName + ";");
        

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;
            
            javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.formsPackageName + "." + currentBean.formBean.className + ";");
            
        }

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			
			javaImports.add("import " + currentBean.myPackage.basicViewsPackageName + "." + currentBean.basicViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.formsPackageName + "." + currentBean.formBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.filtersPackageName + "." + currentBean.basicViewBean.filterClassName + ";");
			javaImports.add("import " + currentBean.myPackage.sortingsPackageName + "." + currentBean.basicViewBean.sortingClassName + ";");
			
		}
		
	}

	@Override
	protected void writeContent() throws IOException {
			
		writeLine("package " + this.bean.myPackage.baseRestClientPackageName + ";");
		skipLine();
		
		writeImports();
		skipLine();
		
		writeLine("/**");
		writeLine(" * auto generated base rest client class file"); 
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.baseRestClientClassName + " implements " + this.bean.baseServiceInterfaceName + " {");
		skipLine();
		
		writeLine("/*"); 
		writeLine(" * properties injected by spring");
		writeLine(" */");
		
		writeLine("@Resource(name=\"" + bean.myPackage.model.project.projectName + "RestClient\")");
		writeLine("protected RestClient restClient;");
		
		
		if (this.bean.selectable) {
			createGetOptions();
		}
		createLoadObjectList();
		createScroll();
		createLoadObject();
		if (bean.cardinality>0) {
			createFindObject();
		}
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
		createDeleteObjectList();
		createDeleteOneToManyComponentList();
		
		writeLine("}");

    }
	
	private void createGetOptions() {
			
		Property targetProperty = bean.selectionBehavior.targetProperty;
		Property labelProperty = bean.selectionBehavior.labelProperty!=null?bean.selectionBehavior.labelProperty:bean.selectionBehavior.targetProperty;
		
		if (bean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
			
			writeLine("/**");
			writeLine(" * get options");
			writeLine(" */");
			writeLine("@Override");
			writeLine("public List<SelectItem> getOptions() {");

			writeLine("return Arrays.asList(restClient.getForObject(GET_OPTIONS_URL, SelectItem[].class));");
			writeLine("}");
			skipLine();
		}
		if (bean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {			
			writeLine("/**");
			writeLine(" * search options");
			writeLine(" */");
			writeLine("@Override");
			writeLine("public List<SelectItem> searchOptions(String arg) {");
			
			writeLine("return Arrays.asList(restClient.postForObject(SEARCH_OPTIONS_URL, arg, SelectItem[].class));");
			writeLine("}");
			skipLine();
		}
	}

    private void createLoadObjectList() {
		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("@Override");
		writeLine("public List<" + this.bean.basicViewBean.className + "> loadList() {");
		
		writeLine("return Arrays.asList(restClient.getForObject(GET_LIST_URL, " + this.bean.basicViewBean.className + "[].class));");
		writeLine("}");
		skipLine();
		
		for (Property property : this.bean.properties) {
		    if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
		        writeLine("/**");
		        writeLine(" * load object list from " + property.name);
		        writeLine(" */");
		        writeLine("@Override");
		        writeLine("public List<" + this.bean.basicViewBean.className + "> loadListFrom" + property.capName + " (" + property.referenceBean.idType + " " + property.name + "Id) {");
		        writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
		        writeLine("vars.put(\"" + property.name + "Id\", " + property.name + "Id);");
		        writeLine("return Arrays.asList(restClient.getForObject(GET_" + bean.table.originalName + "_LIST_FROM_" + property.referenceBean.table.originalName + "_URL, " + this.bean.basicViewBean.className + "[].class, vars));");
		        writeLine("}");
		        skipLine();
		    }
		}
    }
    
    
    private void createScroll() {

		writeLine("/**");
		writeLine(" * scroll object list");
		writeLine(" */");
		writeLine("@Override");
		writeLine("public ScrollView<" + this.bean.basicViewBean.className + "> scroll(ScrollForm<" + bean.basicViewBean.filterClassName + ", " + bean.basicViewBean.sortingClassName + "> form) {");
		
		writeLine("return restClient.postForObject(SCROLL_URL, form, new ParameterizedTypeReference<ScrollView<" + this.bean.basicViewBean.className +">>(){});");
		writeLine("}");
		skipLine();
		
		for (Property property : this.bean.properties) {
		    if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
		        writeLine("/**");
		        writeLine(" * scroll object list from " + property.name);
		        writeLine(" */");
		        writeLine("@Override");
				writeLine("public ScrollView<" + this.bean.basicViewBean.className + "> scrollFrom" + property.capName + " (" + property.referenceBean.idType + " " + property.name + "Id, ScrollForm<" + bean.basicViewBean.filterClassName + ", " + bean.basicViewBean.sortingClassName + "> form) {");
				writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
		        writeLine("vars.put(\"" + property.name + "Id\", " + property.name + "Id);");
		        writeLine("return restClient.postForObject(SCROLL_" + bean.table.originalName + "_FROM_" + property.referenceBean.table.originalName + "_URL, form, new ParameterizedTypeReference<ScrollView<" + this.bean.basicViewBean.className +">>(){}, vars);");
				writeLine("}");
				skipLine();
		    }
		}
    }
    

	private void createLoadObject() {
		writeLine("/**");
		writeLine(" * load object");
		writeLine(" */");
		writeLine("@Override");
		writeLine("public " + this.bean.fullViewBean.className + " load(" + bean.idType + " id) {");
		
		writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
        writeLine("vars.put(\"id\", id);");
        writeLine("return restClient.getForObject(GET_URL, " + this.bean.fullViewBean.className + ".class, vars);");
		writeLine("}");
		skipLine();

	}
    
    
    private void createFindObject() {

    	boolean start = true;
        writeLine("/**");
        writeLine(" * find object");
        writeLine(" */");
        writeLine("@Override");
        write("public " + this.bean.fullViewBean.className + " find(");
		for (ViewProperty property:bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(property.javaType + " " + property.name);
		}
		writeLine(") {");
        writeLine("return null;");
        writeLine("}");
        skipLine();
    }
    

    private void createLoadOneToOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("public " + currentBean.fullViewBean.className + " load" + currentBean.className + "(" + bean.idType + " id) {");
            
            writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
            writeLine("vars.put(\"id\", id);");
            writeLine("return restClient.getForObject(GET_" + currentBean.table.originalName + "_URL, " + currentBean.fullViewBean.className + ".class, vars);");

            writeLine("}");
            skipLine();
        }
    }

    private void createLoadOneToManyComponentList() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.objectName + " list");
            writeLine(" */");
            writeLine("@Override");
            writeLine("public List<" + currentBean.basicViewBean.className + "> load" + currentBean.className + "List(" + bean.idType + " id) {");
            
            writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
	        writeLine("vars.put(\"id\", id);");
	        writeLine("return Arrays.asList(restClient.getForObject(GET_" + currentBean.table.originalName + "_LIST_URL, " + currentBean.basicViewBean.className + "[].class, vars));");
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
			writeLine("@Override");
			writeLine("public ScrollView<" + currentBean.basicViewBean.className + "> scroll" + currentBean.className + " (" + bean.idType + " id, ScrollForm<" + currentBean.basicViewBean.filterClassName + ", " + currentBean.basicViewBean.sortingClassName + "> form) {");
			
			writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
	        writeLine("vars.put(\"id\", id);");
	        writeLine("return restClient.postForObject(SCROLL_" + currentBean.table.originalName + "_URL, form, new ParameterizedTypeReference<ScrollView<" + currentBean.basicViewBean.className +">>(){}, vars);");
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
            writeLine("@Override");
            writeLine("public " + currentBean.fullViewBean.className + " load" + currentBean.className + "(" + currentBean.idType + " id) {");            
          
            writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
            writeLine("vars.put(\"id\", id);");
            writeLine("return restClient.getForObject(GET_" + currentBean.table.originalName + "_URL, " + currentBean.fullViewBean.className + ".class, vars);");
            writeLine("}");
            skipLine();
        }
    }

    private void createCreateObject() {
        writeLine("/**");
        writeLine(" * create object");
        writeLine(" */");
        writeLine("@Override");
        writeLine("public " + this.bean.fullViewBean.className + " create() {");
        
        writeLine("return restClient.getForObject(GET_NEW_URL, " + bean.fullViewBean.className + ".class);");
        writeLine("}");
        skipLine();
    }

    private void createCreateOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * create one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("public " + currentBean.fullViewBean.className + " create" + currentBean.className + "(" + bean.idType + " id) {");
            writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
            writeLine("vars.put(\"id\", id);");
            writeLine("return restClient.getForObject(GET_NEW_" + currentBean.table.originalName + "_URL, " + currentBean.fullViewBean.className + ".class, vars);");
            writeLine("}");
            skipLine();
        }
    }

    private void createSaveObject() {
        writeLine("/**");
        writeLine(" * save object");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("public " + bean.idType + " save(" + this.bean.formBean.className + " " + this.bean.formBean.objectName + ") {");
        
        writeLine("return restClient.postForObject(SAVE_URL, " + this.bean.formBean.objectName + ", " + bean.idType + ".class);");
        
        writeLine("}");
        skipLine();
    }
    
    private void createSaveOneToOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * save one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("public void save" + currentBean.className + "(" + bean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ") {");
            writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
            writeLine("vars.put(\"id\", id);");
            writeLine("restClient.postForObject(SAVE_" + currentBean.table.originalName + "_URL, " + currentBean.formBean.objectName + ", Void.class, vars);");
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
            writeLine("@Override");
            writeLine("public void save" + currentBean.className + "(" + bean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ") {");
            writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
            writeLine("vars.put(\"id\", id);");
            writeLine("restClient.postForObject(SAVE_" + currentBean.table.originalName + "_URL, " + currentBean.formBean.objectName + ", Void.class, vars);");
            writeLine("}");
            skipLine();
        }
    }

    private void createUpdateObject() {
    	writeLine("/**");        
    	writeLine(" * update object");        
    	writeLine(" */");
    	writeLine("@Override");
    	writeLine("public void update(" + bean.idType + " id, " + this.bean.formBean.className + " " + this.bean.formBean.objectName + ") {");
    	writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
        writeLine("vars.put(\"id\", id);");
        writeLine("restClient.put(UPDATE_URL, " + bean.formBean.objectName + ", vars);");
        writeLine("}");
        skipLine();
    }

    private void createUpdateOneTOneComponent() {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * update one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("public void update" + currentBean.className + "(" + bean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ") {");
            writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
            writeLine("vars.put(\"id\", id);");
            writeLine("restClient.put(UPDATE_" + currentBean.table.originalName + "_URL, " + currentBean.formBean.objectName + ", vars);");
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
            writeLine("@Override");
            writeLine("public void update" + currentBean.className + "(" + currentBean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ") {");
            writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
            writeLine("vars.put(\"id\", id);");
            writeLine("restClient.put(UPDATE_" + currentBean.table.originalName + "_URL, " + currentBean.formBean.objectName + ", vars);");
            writeLine("}");
            skipLine();
            skipLine();
        }
    }

    private void createDeleteObject() {
        writeLine("/**");        
        writeLine(" * delete object");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("public void delete(" + bean.idType + " id) {");
        writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
        writeLine("vars.put(\"id\", id);");
        writeLine("restClient.delete(DELETE_URL, vars);");
        writeLine("}");
        skipLine();
    }

    private void createDeleteOneToManyComponent() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");            
            writeLine(" * delete one to many component " + currentBean.objectName);            
            writeLine(" */");
            writeLine("@Override");
            writeLine("public void delete" + currentBean.className + "(" + currentBean.idType + " id) {");
            writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
            writeLine("vars.put(\"id\", id);");
            writeLine("restClient.delete(DELETE_" + currentBean.table.originalName + "_URL, vars);");
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
            writeLine("@Override");
            writeLine("public void delete" + currentBean.className + "(" + bean.idType + " id) {");
            writeLine("Map<String, Object> vars = new HashMap<String, Object>();");
            writeLine("vars.put(\"id\", id);");
            writeLine("restClient.delete(DELETE_" + currentBean.table.originalName + "_URL, vars);");
            writeLine("}");
            skipLine();
        }
    }

    private void createDeleteObjectList() {
        writeLine("/**");        
        writeLine(" * delete object list");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("public void deleteList(List<" + bean.idType + "> idList) {");
        
        writeLine("restClient.postForObject(DELETE_LIST_URL, idList, Void.class);");
        writeLine("}");
        skipLine();
    }

    private void createDeleteOneToManyComponentList() {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * delete one to many component " + currentBean.objectName + " list");
            writeLine(" */");
            writeLine("@Override");
            writeLine("public void delete" + currentBean.className + "List(List<" + currentBean.idType + "> idList) {");
            writeLine("restClient.postForObject(DELETE_" + currentBean.table.originalName + "_LIST_URL, idList, Void.class);");
            writeLine("}");
            skipLine();
        }
    }
}
