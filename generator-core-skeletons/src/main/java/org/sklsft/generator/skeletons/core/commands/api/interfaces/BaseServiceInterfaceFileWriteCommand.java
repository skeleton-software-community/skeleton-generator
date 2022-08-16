package org.sklsft.generator.skeletons.core.commands.api.interfaces;

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

public class BaseServiceInterfaceFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	
	public BaseServiceInterfaceFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.apiArtefactName + File.separator + bean.myPackage.model.javaSourcesFolder + File.separator
				+ bean.myPackage.baseServiceInterfacePackageName.replace(".", File.separator), bean.baseServiceInterfaceName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.time.LocalDate;");
		javaImports.add("import java.math.BigDecimal;");
		javaImports.add("import java.util.List;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollForm;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollView;");
		javaImports.add("import org.sklsft.commons.api.model.SelectItem;");
		javaImports.add("import " + this.bean.myPackage.basicViewsPackageName + "." + this.bean.basicViewBean.className + ";");
		javaImports.add("import " + this.bean.myPackage.fullViewsPackageName + "." + this.bean.fullViewBean.className + ";");
		javaImports.add("import " + this.bean.myPackage.formsPackageName + "." + this.bean.formBean.className + ";");
		javaImports.add("import " + bean.myPackage.filtersPackageName + "." + bean.basicViewBean.filter.className + ";");
		javaImports.add("import " + bean.myPackage.sortingsPackageName + "." + bean.basicViewBean.sortingClassName + ";");
		
		for (OneToOneComponent OneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = OneToOneComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.formsPackageName + "." + currentBean.formBean.className + ";");
		}
		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.basicViewsPackageName + "." + currentBean.basicViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.formsPackageName + "." + currentBean.formBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.filtersPackageName + "." + currentBean.basicViewBean.filter.className + ";");
			javaImports.add("import " + currentBean.myPackage.sortingsPackageName + "." + currentBean.basicViewBean.sortingClassName + ";");
		}

	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + this.bean.myPackage.baseServiceInterfacePackageName + ";");
		skipLine();
		
		writeImports();
		skipLine();
		
		writeLine("/**");
		writeLine(" * auto generated base service interface file"); 
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public interface " + this.bean.baseServiceInterfaceName + " {");
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
		createUpdateOneToOneComponent();
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
			writeLine("List<SelectItem> getOptions();");
			writeLine("public static final String GET_OPTIONS_URL = \"/" + bean.urlPiece + "/options\";");
			skipLine();
		}
		if (bean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {			
			writeLine("/**");
			writeLine(" * search options");
			writeLine(" */");
			writeLine("List<SelectItem> searchOptions(String arg);");
			writeLine("public static final String SEARCH_OPTIONS_URL = \"/" + bean.urlPiece + "/options/search\";");
			skipLine();
		}
	}

	private void createLoadObjectList() {
		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("List<" + this.bean.basicViewBean.className + "> loadList();");
		writeLine("public static final String GET_LIST_URL = \"/" + bean.urlPiece + "/list\";");
		skipLine();

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
				writeLine("/**");
				writeLine(" * load object list from " + property.name);
				writeLine(" */");
				writeLine("List<" + this.bean.basicViewBean.className + "> loadListFrom" + property.capName + " (" + property.referenceBean.idType + " " + property.name + "Id);");
				writeLine("public static final String GET_LIST_FROM_" + property.referenceBean.table.originalName + "_URL = \"/" + property.referenceBean.urlPiece + "/{" + property.name + "Id}/" + bean.urlPiece + "/list\";");
				skipLine();
			}
		}
	}
	
	
	private void createScroll() {
		writeLine("/**");
		writeLine(" * scroll object list");
		writeLine(" */");
		writeLine("ScrollView<" + this.bean.basicViewBean.className + "> scroll(ScrollForm<" + bean.basicViewBean.filter.className + ", " + bean.basicViewBean.sortingClassName + "> form);");
		writeLine("public static final String SCROLL_URL = \"/" + bean.urlPiece + "/scroll\";");
		skipLine();
		
		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
				writeLine("/**");
				writeLine(" * scroll object list from " + property.name);
				writeLine(" */");
				writeLine("ScrollView<" + this.bean.basicViewBean.className + "> scrollFrom" + property.capName + " (" + property.referenceBean.idType + " " + property.name + "Id, ScrollForm<" + bean.basicViewBean.filter.className + ", " + bean.basicViewBean.sortingClassName + "> form);");
				writeLine("public static final String SCROLL_FROM_" + property.referenceBean.table.originalName + "_URL = \"/" + property.referenceBean.urlPiece + "/{" + property.name + "Id}/" + bean.urlPiece + "/scroll\";");
				skipLine();
			}
		}
	}
	

	private void createLoadObject() {
		writeLine("/**");
		writeLine(" * load object");
		writeLine(" */");
		writeLine(this.bean.fullViewBean.className + " load(" + bean.idType + " id);");
		writeLine("public static final String GET_URL = \"/" + bean.urlPiece + "/{id}\";");
		skipLine();

	}
	
	
	private void createFindObject() {
		boolean start = true;
		writeLine("/**");
		writeLine(" * find object");
		writeLine(" */");
		writeLine("public static final String FIND_URL = \"/" + bean.urlPiece + "/find\";");
		write(this.bean.fullViewBean.className + " find(");
		for (ViewProperty property:bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(property.javaType + " " + property.name);
		}
		writeLine(");");
		skipLine();
	}
	

	private void createLoadOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * load one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine(currentBean.fullViewBean.className + " load" + currentBean.className + "(" + bean.idType + " id);");
			writeLine("public static final String GET_" + currentBean.table.originalName + "_URL = \"/" + bean.urlPiece + "/{id}/" + currentBean.urlPiece + "\";");
			skipLine();
		}
	}

	private void createLoadOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * load one to many component " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("List<" + currentBean.basicViewBean.className + "> load" + currentBean.className + "List(" + bean.idType + " id);");
			writeLine("public static final String GET_" + currentBean.table.originalName + "_LIST_URL = \"/" + bean.urlPiece + "/{id}/" + currentBean.urlPiece + "/list\";");
			skipLine();
		}
	}
	
	private void createScrollOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			
			writeLine("/**");
			writeLine(" * scroll one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("ScrollView<" + currentBean.basicViewBean.className + "> scroll" + currentBean.className + " (" + bean.idType + " " + bean.objectName + "Id, ScrollForm<" + currentBean.basicViewBean.filter.className + ", " + currentBean.basicViewBean.sortingClassName + "> form);");
			writeLine("public static final String SCROLL_" + currentBean.table.originalName + "_URL = \"/" + bean.urlPiece + "/{id}/" + currentBean.urlPiece + "/scroll\";");
			skipLine();
		}
	}

	private void createLoadOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * load one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine(currentBean.fullViewBean.className + " load" + currentBean.className + "(" + currentBean.idType + " id);");			
			writeLine("public static final String GET_" + currentBean.table.originalName + "_URL = \"/" + currentBean.urlPiece + "/{id}\";");
			skipLine();
		}
	}

	private void createCreateObject() {
		writeLine("/**");
		writeLine(" * create object");
		writeLine(" */");
		writeLine(this.bean.fullViewBean.className + " create();");
		writeLine("public static final String GET_NEW_URL = \"/" + bean.urlPiece + "/new\";");
		skipLine();
	}

	private void createCreateOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * create one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine(currentBean.fullViewBean.className + " create" + currentBean.className + "(" + bean.idType + " id);");
			writeLine("public static final String GET_NEW_" + currentBean.table.originalName + "_URL = \"/" + bean.urlPiece + "/{id}/" + currentBean.urlPiece + "/new\";");
			skipLine();
		}
	}

	private void createSaveObject() {
		writeLine("/**");
		writeLine(" * save object");
		writeLine(" */");
		writeLine(bean.idType + " save(" + this.bean.formBean.className + " " + this.bean.formBean.objectName + ");");
		writeLine("public static final String SAVE_URL = \"/" + bean.urlPiece + "\";");
		skipLine();
	}
	
	private void createSaveOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * save one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void save" + currentBean.className + "(" + bean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ");");
			writeLine("public static final String SAVE_" + currentBean.table.originalName + "_URL = \"/" + bean.urlPiece + "/{id}/" + currentBean.urlPiece + "\";");
			skipLine();
		}
	}

	private void createSaveOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * save one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("void save" + currentBean.className + "(" + bean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ");");
			writeLine("public static final String SAVE_" + currentBean.table.originalName + "_URL = \"/" + bean.urlPiece + "/{id}/" + currentBean.urlPiece + "\";");
			skipLine();
		}
	}

	private void createUpdateObject() {
		writeLine("/**");		
		writeLine(" * update object");		
		writeLine(" */");
		writeLine("void update(" + bean.idType + " id, " + this.bean.formBean.className + " " + this.bean.formBean.objectName + ");");
		writeLine("public static final String UPDATE_URL = \"/" + bean.urlPiece + "/{id}\";");
		skipLine();
	}

	private void createUpdateOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * update one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("void update" + currentBean.className + "(" + bean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ");");
			writeLine("public static final String UPDATE_" + currentBean.table.originalName + "_URL = \"/" + bean.urlPiece + "/{id}/" + currentBean.urlPiece + "\";");
			skipLine();
		}
	}

	private void createUpdateOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * update one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("void update" + currentBean.className + "(" + currentBean.idType + " id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ");");
			writeLine("public static final String UPDATE_" + currentBean.table.originalName + "_URL = \"/" + currentBean.urlPiece + "/{id}\";");
			skipLine();
		}
	}

	private void createDeleteObject() {
		writeLine("/**");		
		writeLine(" * delete object");		
		writeLine(" */");
		writeLine("void delete(" + bean.idType + " id);");
		writeLine("public static final String DELETE_URL = \"/" + bean.urlPiece + "/{id}\";");
		skipLine();
	}
	
	private void createDeleteOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");			
			writeLine(" * delete one to one component " + currentBean.objectName);			
			writeLine(" */");
			writeLine("public void delete" + currentBean.className + "(" + bean.idType + " id);");
			writeLine("public static final String DELETE_" + currentBean.table.originalName + "_URL = \"/" + bean.urlPiece + "/{id}/" + currentBean.urlPiece + "\";");
			skipLine();
		}
	}

	private void createDeleteOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");			
			writeLine(" * delete one to many component " + currentBean.objectName);			
			writeLine(" */");
			writeLine("void delete" + currentBean.className + "(" + currentBean.idType + " id);");
			writeLine("public static final String DELETE_" + currentBean.table.originalName + "_URL = \"/" + currentBean.urlPiece + "/{id}\";");
			skipLine();
		}
	}

	private void createDeleteObjectList() {
		writeLine("/**");		
		writeLine(" * delete object list");		
		writeLine(" */");
		writeLine("void deleteList(List<" + bean.idType + "> idList);");
		writeLine("public static final String DELETE_LIST_URL = \"/" + bean.urlPiece + "/delete\";");
		skipLine();
	}

	private void createDeleteOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("void delete" + currentBean.className + "List(List<" + currentBean.idType + "> idList);");
			writeLine("public static final String DELETE_" + currentBean.table.originalName + "_LIST_URL = \"/" + currentBean.urlPiece + "/delete\";");
			skipLine();
		}
	}
}
