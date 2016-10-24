package org.sklsft.generator.bc.file.command.impl.java.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.metadata.RelationType;

public class BaseDaoInterfaceFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public BaseDaoInterfaceFileWriteCommand(Bean bean) {

		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-repository" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseDAOInterfacePackageName.replace(".", File.separator), bean.baseDaoInterfaceName);

		this.bean = bean;

	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.Date;");
		javaImports.add("import org.sklsft.commons.model.patterns.BaseDao;");
		javaImports.add("import " + bean.myPackage.omPackageName + "." + bean.className + ";");
		
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
		}
		
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
		}
	}

	@Override
	protected void writeContent() throws IOException {
		
		writeLine("package " + bean.myPackage.baseDAOInterfacePackageName + ";");
        
        
        writeImports();
        
        writeLine("/**");
        writeLine(" * auto generated base dao interface file");
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
        writeLine("public interface " + this.bean.baseDaoInterfaceName + " extends BaseDao<" + this.bean.className + ", Long> {");
        skipLine();

        createLoadObjectList();
        createLoadOneToManyComponent();
        createExistsObject();
        createFindObject();
        createSaveComponent();
        createDeleteComponent();

        writeLine("}");
	}
	
	private void createLoadObjectList()
    {       

        for (Property property : this.bean.properties)
        {
            if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE))
            {

                writeLine("/**");
                writeLine(" * load object list from " + property.referenceBean.objectName); 
                writeLine(" */");
                writeLine("List<" + this.bean.className + "> loadListFrom" + property.capName + " (Long " + property.name + "Id);");
                skipLine();

                writeLine("/**");
                writeLine(" * load object list eagerly from list of " + property.referenceBean.objectName);
                writeLine(" */");
                writeLine("List<" + this.bean.className + "> loadListEagerlyFrom" + property.capName + " (Long " + property.name + "Id);");
                skipLine();

            }
        }
    }

    
    private void createLoadOneToManyComponent() {		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.className);
            writeLine(" */");
			writeLine("public " + currentBean.className + " load" + currentBean.className + "(Long id);");
			skipLine();
        }
	}

    private void createExistsObject() {
        List<Property> findPropertyList = this.bean.getReferenceProperties();

        writeLine("/**");
        writeLine(" * exists object");
        writeLine(" */");
        write("boolean exists(" + findPropertyList.get(0).beanDataType + " " + findPropertyList.get(0).name);
        for (int i = 1; i < findPropertyList.size(); i++)
        {
            write("," + findPropertyList.get(i).beanDataType + " " + findPropertyList.get(i).name);
        }
        writeLine(");");
        skipLine();
    }
    
    private void createFindObject() {
        List<Property> findPropertyList = this.bean.getReferenceProperties();

        writeLine("/**");
        writeLine(" * find object");  
        writeLine(" */");
        write(this.bean.className + " find(" + findPropertyList.get(0).beanDataType + " " + findPropertyList.get(0).name);
        for (int i=1;i<findPropertyList.size();i++)
        {
            write("," + findPropertyList.get(i).beanDataType + " " + findPropertyList.get(i).name);
        }
        writeLine(");");
        skipLine();
    }

    private void createSaveComponent() {
        for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList){
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("/**");
			writeLine(" * save one to many component " + currentBean.className);
			writeLine(" */");
			writeLine("public void save" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.className + " " + currentBean.objectName + ");");
			skipLine();
		}
        
        for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList){
			Bean currentBean = oneToOneComponent.referenceBean;
			writeLine("/**");
			writeLine(" * save one to one component " + currentBean.className);
			writeLine(" */");
			writeLine("public void save" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.className + " " + currentBean.objectName + ");");
			skipLine();
		}
    }


    private void createDeleteComponent() {           
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList){
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.className);
			writeLine(" */");
			writeLine("public void delete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ");");
			skipLine();
		}
		
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList){
			Bean currentBean = oneToOneComponent.referenceBean;
			writeLine("/**");
			writeLine(" * delete one to one component " + currentBean.className);
			writeLine(" */");
			writeLine("public void delete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ");");
			skipLine();
		}
	}
}
