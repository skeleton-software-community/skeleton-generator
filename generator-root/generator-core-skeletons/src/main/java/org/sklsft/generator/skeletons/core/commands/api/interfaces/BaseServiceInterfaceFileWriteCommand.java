package org.sklsft.generator.skeletons.core.commands.api.interfaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;

public class BaseServiceInterfaceFileWriteCommand extends JavaFileWriteCommand {

private Bean bean;
	
	public BaseServiceInterfaceFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-api" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseServiceInterfacePackageName.replace(".", File.separator), bean.baseServiceInterfaceName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.util.List;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollForm;");
		javaImports.add("import org.sklsft.commons.api.model.ScrollView;");
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
		
		for (OneToManyComponent uniqueComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.basicViewsPackageName + "." + currentBean.basicViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.fullViewsPackageName + "." + currentBean.fullViewBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.formsPackageName + "." + currentBean.formBean.className + ";");
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
		
		if (this.bean.hasComboBox) {
			
			Property comboProperty = this.bean.properties.get(0);
			
			writeLine("/**");
			writeLine(" * get options");
			writeLine(" */");
			writeLine("List<" + comboProperty.beanDataType + "> getOptions();");
			skipLine();
		
		}

		createLoadObjectList();
		createScroll();
		createLoadObject();
		createFindObject();
		createLoadOneToOneComponent();
		createLoadOneToManyComponentList();
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

	private void createLoadObjectList() {
		writeLine("/**");
		writeLine(" * load object list");
		writeLine(" */");
		writeLine("List<" + this.bean.basicViewBean.className + "> loadList();");
		skipLine();

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
				writeLine("/**");
				writeLine(" * load object list from " + property.name);
				writeLine(" */");
				writeLine("List<" + this.bean.basicViewBean.className + "> loadListFrom" + property.capName + " (Long " + property.name + "Id);");
				skipLine();
			}
		}

	}
	
	
	private void createScroll() {
		writeLine("/**");
		writeLine(" * scroll object list");
		writeLine(" */");
		writeLine("ScrollView<" + this.bean.basicViewBean.className + "> scroll(ScrollForm<" + bean.basicViewBean.filterClassName + ", " + bean.basicViewBean.sortingClassName + "> form);");
		skipLine();
	}
	

	private void createLoadObject() {
		writeLine("/**");
		writeLine(" * load object");
		writeLine(" */");
		writeLine(this.bean.fullViewBean.className + " load(Long id);");
		skipLine();

	}
	
	
	private void createFindObject() {
		boolean start = true;
		writeLine("/**");
		writeLine(" * find object");
		writeLine(" */");
		write(this.bean.fullViewBean.className + " find(");
		for (ViewProperty property:bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(property.beanDataType + " " + property.name);
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
			writeLine(currentBean.fullViewBean.className + " load" + currentBean.className + "(Long id);");
			skipLine();
		}
	}

	private void createLoadOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * load one to many component " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("List<" + currentBean.basicViewBean.className + "> load" + currentBean.className + "List(Long id);");
			skipLine();
		}
	}

	private void createLoadOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * load one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine(currentBean.fullViewBean.className + " load" + currentBean.className + "(Long " + currentBean.objectName + "Id,Long id);");			
			skipLine();
		}
	}

	private void createCreateObject() {
		writeLine("/**");
		writeLine(" * create object");
		writeLine(" */");
		writeLine(this.bean.fullViewBean.className + " create();");
		skipLine();
	}

	private void createCreateOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * create one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine(currentBean.fullViewBean.className + " create" + currentBean.className + "(Long id);");
			skipLine();
		}
	}

	private void createSaveObject() {
		writeLine("/**");
		writeLine(" * save object");		
		writeLine(" */");
		writeLine("Long save(" + this.bean.formBean.className + " " + this.bean.formBean.objectName + ");");
		skipLine();
		
		for (Property property:bean.properties) {
			if (property.referenceBean!=null) {
				if (property.relation.equals(RelationType.MANY_TO_ONE)) {
					
					Bean parentBean = property.referenceBean;
					
					writeLine("/**");
					writeLine(" * save object from parent " + parentBean.className);		
					writeLine(" */");
					writeLine("Long saveFrom" + parentBean.className + "(Long " + parentBean.objectName + "Id, " + this.bean.formBean.className + " " + this.bean.formBean.objectName + ");");
					skipLine();
				}
			}
		}
	}
	
	private void createSaveOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * save one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public void save" + currentBean.className + "(Long id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ");");
			skipLine();
		}
	}

	private void createSaveOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * save one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("void save" + currentBean.className + "(Long id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ");");
			skipLine();
		}
	}

	private void createUpdateObject() {
		writeLine("/**");		
		writeLine(" * update object");		
		writeLine(" */");
		writeLine("void update(Long id, " + this.bean.formBean.className + " " + this.bean.formBean.objectName + ");");
		skipLine();
	}

	private void createUpdateUniqueComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");
			writeLine(" * update one to one component " + currentBean.objectName);
			writeLine(" */");
			writeLine("void update" + currentBean.className + "(Long id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ");");
			skipLine();
		}
	}

	private void createUpdateOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * update one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("void update" + currentBean.className + "(Long id, " + currentBean.formBean.className + " " + currentBean.formBean.objectName + ");");
			skipLine();
		}
	}

	private void createDeleteObject() {
		writeLine("/**");		
		writeLine(" * delete object");		
		writeLine(" */");
		writeLine("void delete(Long id);");
		skipLine();
	}
	
	private void createDeleteOneToOneComponent() {
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("/**");			
			writeLine(" * delete one to one component " + currentBean.objectName);			
			writeLine(" */");
			writeLine("public void delete" + currentBean.className + "(Long id);");			
			skipLine();
		}
	}

	private void createDeleteOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");			
			writeLine(" * delete one to many component " + currentBean.objectName);			
			writeLine(" */");
			writeLine("void delete" + currentBean.className + "(Long id);");
			skipLine();
		}
	}

	private void createDeleteObjectList() {
		writeLine("/**");		
		writeLine(" * delete object list");		
		writeLine(" */");
		writeLine("void deleteList(List<Long> idList);");
		skipLine();
	}

	private void createDeleteOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.objectName + " list");
			writeLine(" */");
			writeLine("void delete" + currentBean.className + "List(List<Long> idList);");
			skipLine();
		}
	}
}
