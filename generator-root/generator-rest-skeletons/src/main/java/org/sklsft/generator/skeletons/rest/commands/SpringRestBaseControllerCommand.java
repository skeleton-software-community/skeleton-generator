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

public class SpringRestBaseControllerCommand extends JavaFileWriteCommand {

private Bean bean;
	
	public SpringRestBaseControllerCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-rest" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseRestControllerPackageName.replace(".", File.separator), bean.baseRestControllerClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.math.BigDecimal;");
		javaImports.add("import java.util.List;");
		javaImports.add("import javax.inject.Inject;");
		javaImports.add("import javax.validation.Valid;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollForm;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollView;");
		javaImports.add("import org.sklsft.commons.api.model.SelectItem;");
		javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
		javaImports.add("import " + this.bean.myPackage.basicViewsPackageName + "." + this.bean.basicViewBean.className + ";");
		javaImports.add("import " + this.bean.myPackage.fullViewsPackageName + "." + this.bean.fullViewBean.className + ";");
		javaImports.add("import " + this.bean.myPackage.formsPackageName + "." + this.bean.formBean.className + ";");
		javaImports.add("import " + bean.myPackage.filtersPackageName + "." + bean.basicViewBean.filterClassName + ";");
		javaImports.add("import " + bean.myPackage.sortingsPackageName + "." + bean.basicViewBean.sortingClassName + ";");
		
		for (OneToOneComponent OneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = OneToOneComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.formsPackageName + "." + currentBean.formBean.className + ";");
		}
		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + this.bean.myPackage.serviceInterfacePackageName + "." + this.bean.serviceInterfaceName + ";");
			javaImports.add("import " + currentBean.myPackage.basicViewsPackageName + "." + currentBean.basicViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.formsPackageName + "." + currentBean.formBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.filtersPackageName + "." + currentBean.basicViewBean.filterClassName + ";");
			javaImports.add("import " + currentBean.myPackage.sortingsPackageName + "." + currentBean.basicViewBean.sortingClassName + ";");
		}
		javaImports.add("import org.springframework.web.bind.annotation.PathVariable;");
		javaImports.add("import org.springframework.web.bind.annotation.RequestBody;");
		javaImports.add("import org.springframework.web.bind.annotation.RequestMapping;");
		javaImports.add("import org.springframework.web.bind.annotation.RequestMethod;");
		javaImports.add("import org.springframework.web.bind.annotation.RequestParam;");
		javaImports.add("import org.springframework.web.bind.annotation.ResponseBody;");

	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.baseRestControllerPackageName + ";");
		skipLine();
		
		writeImports();
		skipLine();
		
		writeLine("/**");
		writeLine(" * auto generated base rest controller file"); 
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.baseRestControllerClassName + " {");
		skipLine();
		writeLine("/*");
		writeLine(" * services injected by spring");
		writeLine(" */");
		writeLine("@Inject");
		writeLine("protected " + this.bean.serviceInterfaceName + " " + this.bean.serviceObjectName + ";");
		skipLine();
		
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
		createUpdateUniqueComponent();
		createUpdateOneToManyComponent();
		createDeleteObject();
		createDeleteOneToOneComponent();
		createDeleteOneToManyComponent();
		createDeleteObjectList();
		createDeleteOneToManyComponentList();

		writeLine("}");

	}

	private void createGetOptions() {
		
		if (bean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {			
			writeLine("/**");
			writeLine(" * get options");
			writeLine(" */");
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".GET_OPTIONS_URL}, method = RequestMethod.GET)");
			writeLine("public @ResponseBody List<SelectItem> getOptions() {");
			writeLine("return " + bean.serviceObjectName + ".getOptions();");
			writeLine("}");
			skipLine();
		}
		if (bean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {			
			writeLine("/**");
			writeLine(" * search options");
			writeLine(" */");
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".SEARCH_OPTIONS_URL }, method = RequestMethod.POST)");
			writeLine("public @ResponseBody List<SelectItem> searchOptions(@RequestBody String arg) {");
			writeLine("return " + bean.serviceObjectName + ".searchOptions(arg);");
			writeLine("}");
			skipLine();
		}
	}

	private void createLoadObjectList() {
		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".GET_LIST_URL}, method = RequestMethod.GET)");
		writeLine("public @ResponseBody List<" + this.bean.basicViewBean.className + "> loadList() {");
		writeLine("return " + bean.serviceObjectName + ".loadList();");
		writeLine("}");
		skipLine();

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
				writeLine("/**");
				writeLine(" * load object list from " + property.name);
				writeLine(" */");
				writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".GET_" + bean.table.originalName + "_LIST_FROM_" + property.referenceBean.table.originalName + "_URL}, method = RequestMethod.GET)");
				writeLine("public @ResponseBody List<" + this.bean.basicViewBean.className + "> loadListFrom" + property.capName + " (@PathVariable(\"" + property.name + "Id\") " + property.referenceBean.idType + " " + property.name + "Id) {");
				writeLine("return " + bean.serviceObjectName + ".loadListFrom" + property.capName + "(" + property.name + "Id);");
				writeLine("}");
			}
		}

	}
	
	
	private void createScroll() {
		writeLine("/**");
		writeLine(" * scroll object list");
		writeLine(" */");
		writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".SCROLL_URL}, method = RequestMethod.POST)");
		writeLine("public @ResponseBody ScrollView<" + this.bean.basicViewBean.className + "> scroll(@RequestBody ScrollForm<" + bean.basicViewBean.filterClassName + ", " + bean.basicViewBean.sortingClassName + "> form) {");
		writeLine("return " + bean.serviceObjectName + ".scroll(form);");
		writeLine("}");
		skipLine();
		
		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
				writeLine("/**");
				writeLine(" * scroll object list from " + property.name);
				writeLine(" */");
				writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".SCROLL_" + bean.table.originalName + "_FROM_" + property.referenceBean.table.originalName + "_URL}, method = RequestMethod.POST)");
				writeLine("public @ResponseBody ScrollView<" + this.bean.basicViewBean.className + "> scrollFrom" + property.capName + " (@PathVariable(\"" + property.name + "Id\") " + property.referenceBean.idType + " " + property.name + "Id, @RequestBody ScrollForm<" + bean.basicViewBean.filterClassName + ", " + bean.basicViewBean.sortingClassName + "> form) {");
				writeLine("return " + bean.serviceObjectName + ".scrollFrom" + property.capName + "(" + property.name + "Id, form);");
				writeLine("}");
			}
		}
	}
	

	private void createLoadObject() {
		writeLine("/**");
		writeLine(" * load object");
		writeLine(" */");
		writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".GET_URL}, method = RequestMethod.GET)");
		writeLine("public @ResponseBody " + this.bean.fullViewBean.className + " load(@PathVariable(\"id\") " + bean.idType + " id) {");
		writeLine("return " + bean.serviceObjectName + ".load(id);");
		writeLine("}");

	}
	
	
	private void createFindObject() {
		boolean start = true;
		writeLine("/**");
		writeLine(" * find object");
		writeLine(" */");
		writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".FIND_URL}, method = RequestMethod.GET)");
		write("public @ResponseBody " + this.bean.fullViewBean.className + " find(");
		for (ViewProperty property:bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write("@RequestParam(\"" + property.name + "\") " + property.beanDataType + " " + property.name);
		}
		start = true;
		writeLine(") {");
		write("return " + bean.serviceObjectName + ".find(");
		for (ViewProperty property:bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(property.name);
		}
		writeLine(");");
		writeLine("}");
		skipLine();
	}
	

	private void createLoadOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * load one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".GET_" + currentBean.table.originalName + "_URL}, method = RequestMethod.GET)");
			writeLine("public @ResponseBody " + currentBean.fullViewBean.className + " load" + currentBean.className + "(@PathVariable(\"id\") " + bean.idType + " id) {");
			writeLine("return " + bean.serviceObjectName + ".load" + currentBean.className + "(id);");
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
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".GET_" + currentBean.table.originalName + "_LIST_URL}, method = RequestMethod.GET)");
			writeLine("public @ResponseBody List<" + currentBean.basicViewBean.className + "> load" + currentBean.className + "List(@PathVariable(\"id\") " + currentBean.idType + " id) {");
			writeLine("return " + bean.serviceObjectName + ".load" + currentBean.className + "List(id);");
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
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".SCROLL_" + currentBean.table.originalName + "_URL}, method = RequestMethod.POST)");
			writeLine("public @ResponseBody ScrollView<" + currentBean.basicViewBean.className + "> scroll" + currentBean.className + " (@PathVariable(\"id\") " + bean.idType + " id, @RequestBody ScrollForm<" + currentBean.basicViewBean.filterClassName + ", " + currentBean.basicViewBean.sortingClassName + "> form) {");
			writeLine("return " + bean.serviceObjectName + ".scroll" + currentBean.className + "(id, form);");
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
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".GET_" + currentBean.table.originalName + "_URL}, method = RequestMethod.GET)");
			writeLine("public @ResponseBody " + currentBean.fullViewBean.className + " load" + currentBean.className + "(@PathVariable(\"id\") " + bean.idType + " id) {");
			writeLine("return " + bean.serviceObjectName + ".load" + currentBean.className + "(id);");
			writeLine("}");
			skipLine();
		}
	}

	private void createCreateObject() {
		writeLine("/**");
		writeLine(" * create object");
		writeLine(" */");
		writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".GET_NEW_URL}, method = RequestMethod.GET)");
		writeLine("public @ResponseBody " + this.bean.fullViewBean.className + " create() {");
		writeLine("return " + bean.serviceObjectName + ".create();");
		writeLine("}");
		skipLine();
	}

	private void createCreateOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * create one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".GET_NEW_" + currentBean.table.originalName + "_URL}, method = RequestMethod.GET)");
			writeLine("public @ResponseBody " +  currentBean.fullViewBean.className + " create" + currentBean.className + "(@PathVariable(\"id\") " + bean.idType + " id) {");
			writeLine("return " + bean.serviceObjectName + ".create" + currentBean.className + "(id);");
			writeLine("}");
			skipLine();
		}
	}

	private void createSaveObject() {
		writeLine("/**");
		writeLine(" * save object");		
		writeLine(" */");
		writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".SAVE_URL}, method = RequestMethod.POST)");
		writeLine("public @ResponseBody " + bean.idType + " save(@Valid @RequestBody " + this.bean.formBean.className + " form) {");
		writeLine("return " + bean.serviceObjectName + ".save(form);");
		writeLine("}");
		skipLine();
		
	}
	
	private void createSaveOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * save one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".SAVE_" + currentBean.table.originalName + "_URL}, method = RequestMethod.POST)");
			writeLine("public @ResponseBody void save" + currentBean.className + "(@PathVariable(\"id\") " + bean.idType + " id, @Valid @RequestBody " + currentBean.formBean.className + " form) {");
			writeLine(bean.serviceObjectName + ".save" + currentBean.className + "(id, form);");
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
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".SAVE_" + currentBean.table.originalName + "_URL}, method = RequestMethod.POST)");
			writeLine("public @ResponseBody void save" + currentBean.className + "(@PathVariable(\"id\") " + bean.idType + " id, @Valid @RequestBody " + currentBean.formBean.className + " form) {");
			writeLine(bean.serviceObjectName + ".save" + currentBean.className + "(id, form);");
			writeLine("}");
			skipLine();
		}
	}

	private void createUpdateObject() {
		writeLine("/**");		
		writeLine(" * update object");		
		writeLine(" */");
		writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".UPDATE_URL}, method = RequestMethod.PUT)");
		writeLine("public @ResponseBody void update(@PathVariable(\"id\") " + bean.idType + " id, @Valid @RequestBody " + this.bean.formBean.className + " form) {");
		writeLine(bean.serviceObjectName + ".update(id, form);");
		writeLine("}");
		skipLine();
	}

	private void createUpdateUniqueComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * update one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".UPDATE_" + currentBean.table.originalName + "_URL}, method = RequestMethod.PUT)");
			writeLine("public @ResponseBody void update" + currentBean.className + "(@PathVariable(\"id\") " + bean.idType + " id, @Valid @RequestBody " + currentBean.formBean.className + " form) {");
			writeLine(bean.serviceObjectName + ".update" + currentBean.className + "(id, form);");
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
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".UPDATE_" + currentBean.table.originalName + "_URL}, method = RequestMethod.PUT)");
			writeLine("public @ResponseBody void update" + currentBean.className + "(@PathVariable(\"id\") " + currentBean.idType + " id, @Valid @RequestBody " + currentBean.formBean.className + " form) {");
			writeLine(bean.serviceObjectName + ".update" + currentBean.className + "(id, form);");
			writeLine("}");
			skipLine();
		}
	}

	private void createDeleteObject() {
		writeLine("/**");		
		writeLine(" * delete object");		
		writeLine(" */");
		writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".DELETE_URL}, method = RequestMethod.DELETE)");
		writeLine("public @ResponseBody void delete(@PathVariable(\"id\") " + bean.idType + " id) {");
		writeLine(bean.serviceObjectName + ".delete(id);");
		writeLine("}");
		skipLine();
	}
	
	private void createDeleteOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");			
			writeLine(" * delete one to one component " + currentBean.objectName);			
			writeLine(" */");
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".DELETE_" + currentBean.table.originalName + "_URL}, method = RequestMethod.DELETE)");
			writeLine("public @ResponseBody void delete" + currentBean.className + "(@PathVariable(\"id\")" + bean.idType + " id) {");			
			writeLine(bean.serviceObjectName + ".delete" + currentBean.className + "(id);");
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
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".DELETE_" + currentBean.table.originalName + "_URL}, method = RequestMethod.DELETE)");
			writeLine("public @ResponseBody void delete" + currentBean.className + "(@PathVariable(\"id\")" + currentBean.idType + " id) {");			
			writeLine(bean.serviceObjectName + ".delete" + currentBean.className + "(id);");
			writeLine("}");
			skipLine();
		}
	}

	private void createDeleteObjectList() {
		writeLine("/**");		
		writeLine(" * delete object list");		
		writeLine(" */");
		writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".DELETE_LIST_URL}, method = RequestMethod.POST)");
		writeLine("public @ResponseBody void deleteList(@RequestBody List<" + bean.idType + "> idList) {");
		writeLine(bean.serviceObjectName + ".deleteList(idList);");
		writeLine("}");
		skipLine();
	}

	private void createDeleteOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("@RequestMapping(value = {" + bean.serviceInterfaceName + ".DELETE_" + currentBean.table.originalName + "_LIST_URL}, method = RequestMethod.POST)");
			writeLine("public @ResponseBody void delete" + currentBean.className + "List(@RequestBody List<" + currentBean.idType + "> idList) {");
			writeLine(bean.serviceObjectName + ".delete" + currentBean.className + "List(idList);");
			writeLine("}");
			skipLine();
		}
	}
}