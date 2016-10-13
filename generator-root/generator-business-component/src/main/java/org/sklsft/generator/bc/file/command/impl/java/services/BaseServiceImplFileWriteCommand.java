package org.sklsft.generator.bc.file.command.impl.java.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.metadata.RelationType;

public class BaseServiceImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	
	public BaseServiceImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-services" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseServiceImplPackageName.replace(".", File.separator), bean.baseServiceClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.util.Collection;");
        javaImports.add("import java.util.List;");
        javaImports.add("import java.util.ArrayList;");
        javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
        javaImports.add("import org.springframework.transaction.annotation.Transactional;");
        javaImports.add("import org.sklsft.commons.api.exception.repository.ObjectNotFoundException;");
        javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.basicViewBean.className + ";");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.fullViewBean.className + ";");
        javaImports.add("import " + this.bean.myPackage.DAOInterfacePackageName + "." + this.bean.daoInterfaceName + ";");
        
        for (Property property:bean.properties) {
        	if (property.referenceBean!=null) {
        		if (property.relation.equals(RelationType.MANY_TO_ONE)) {
        			
        			Bean parentBean = property.referenceBean;
        			
        			javaImports.add("import " + parentBean.myPackage.omPackageName + "." + parentBean.className + ";");
        			javaImports.add("import " + parentBean.myPackage.DAOInterfacePackageName + "." + parentBean.daoInterfaceName + ";");
        		}
        	}
        }
        
        javaImports.add("import " + this.bean.myPackage.mapperImplPackageName + "." + this.bean.basicViewBean.mapperClassName + ";");
        javaImports.add("import " + this.bean.myPackage.mapperImplPackageName + "." + this.bean.fullViewBean.mapperClassName + ";");
        javaImports.add("import " + this.bean.myPackage.processorImplPackageName + "." + this.bean.processorClassName + ";");
        javaImports.add("import " + this.bean.myPackage.stateManagerImplPackageName + "." + this.bean.stateManagerClassName + ";");
        
        javaImports.add("import " + this.bean.myPackage.baseServiceInterfacePackageName + "." + this.bean.baseServiceInterfaceName + ";");
        

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.fullViewBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.mapperImplPackageName + "." + currentBean.fullViewBean.mapperClassName + ";");
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.basicViewBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.mapperImplPackageName + "." + currentBean.basicViewBean.mapperClassName + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.fullViewBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.mapperImplPackageName + "." + currentBean.fullViewBean.mapperClassName + ";");
        }
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseServiceImplPackageName + ";");
        skipLine();
        
        writeImports();
        skipLine();
        
        writeLine("/**");
        writeLine(" * auto generated base service class file"); 
        writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
        writeLine("public class " + this.bean.baseServiceClassName + " implements " + this.bean.baseServiceInterfaceName + " {");
        skipLine();

        writeLine("/*"); 
        writeLine(" * properties injected by spring");
        writeLine(" */");

        writeLine("@Autowired");
        writeLine("protected " + this.bean.daoInterfaceName + " " + this.bean.daoObjectName + ";");
        
        for (Property property:bean.properties) {
        	if (property.referenceBean!=null) {
        		if (property.relation.equals(RelationType.MANY_TO_ONE)) {
        			
        			Bean parentBean = property.referenceBean;
        			writeLine("@Autowired");
        	        writeLine("protected " + parentBean.daoInterfaceName + " " + parentBean.daoObjectName + ";");
        		}
        	}
        }
        
        writeLine("@Autowired");
        writeLine("protected " + this.bean.fullViewBean.mapperClassName + " " + this.bean.fullViewBean.mapperObjectName + ";");
        writeLine("@Autowired");
        writeLine("protected " + this.bean.basicViewBean.mapperClassName + " " + this.bean.basicViewBean.mapperObjectName + ";");
        
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;
            writeLine("@Autowired");
            writeLine("protected " + currentBean.fullViewBean.mapperClassName + " " + currentBean.fullViewBean.mapperObjectName + ";");
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("@Autowired");
            writeLine("protected " + currentBean.fullViewBean.mapperClassName + " " + currentBean.fullViewBean.mapperObjectName + ";");
            writeLine("@Autowired");
            writeLine("protected " + currentBean.basicViewBean.mapperClassName + " " + currentBean.basicViewBean.mapperObjectName + ";");
        }
        
        writeLine("@Autowired");
        writeLine("protected " + this.bean.stateManagerClassName + " " + this.bean.stateManagerObjectName + ";");
        skipLine();
        
        writeLine("@Autowired");
        writeLine("protected " + this.bean.processorClassName + " " + this.bean.processorObjectName + ";");
        skipLine();


        if (this.bean.hasComboBox)
        {
            writeLine("/**");
            writeLine(" * get options");
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(readOnly=true)");
            writeLine("public List<" + this.bean.properties.get(1).beanDataType + "> getOptions() {");
            writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".loadList();");
            writeLine("List<" + this.bean.properties.get(1).beanDataType + "> " + this.bean.objectName + this.bean.properties.get(1).capName + "List = new ArrayList<>(" + this.bean.objectName + "List.size());");
            writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
            writeLine(this.bean.objectName + this.bean.properties.get(1).capName + "List.add(" + this.bean.objectName + ".get" + this.bean.properties.get(1).capName + "());");
            writeLine("}");
            writeLine("return " + this.bean.objectName + this.bean.properties.get(1).capName + "List;");
            writeLine("}");
            skipLine();

        }

        createLoadObjectList();
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
        createUpdateOneTOneComponent();
        createUpdateOneToManyComponent();
        createDeleteObject();
        createDeleteOneToOneComponent();
        createDeleteOneToManyComponent();
        createDeleteObjectList();
        createDeleteOneToManyComponentList();

        writeLine("}");

    }

    private void createLoadObjectList()
    {
        writeLine("/**");
        writeLine(" * load object list");
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(readOnly=true)");
        writeLine("public List<" + this.bean.basicViewBean.className + "> loadList() {");
        writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".loadListEagerly();");
        writeLine("List<" + this.bean.basicViewBean.className + "> result = new ArrayList<>(" + this.bean.objectName + "List.size());");
        writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
        writeLine("result.add(this." + bean.basicViewBean.mapperObjectName + ".mapFrom(new " + this.bean.basicViewBean.className + "()," + this.bean.objectName + "));");
        writeLine("}");
        writeLine("return result;");
        writeLine("}");
        skipLine();

        for (Property property : this.bean.properties)
        {
            if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE))
            {
                writeLine("/**");
                writeLine(" * load object list from " + property.name);
                writeLine(" */");
                writeLine("@Override");
                writeLine("@Transactional(readOnly=true)");
                writeLine("public List<" + this.bean.basicViewBean.className + "> loadListFrom" + property.capName + " (Long " + property.name + "Id) {");
                writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".loadListEagerlyFrom" + property.capName + " (" + property.name + "Id);");
                writeLine("List<" + this.bean.basicViewBean.className + "> result = new ArrayList<>(" + this.bean.objectName + "List.size());");
                writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
                writeLine("result.add(this." + bean.basicViewBean.mapperObjectName + ".mapFrom(new " + this.bean.basicViewBean.className + "()," + this.bean.objectName + "));");
                writeLine("}");
                writeLine("return result;");
                writeLine("}");
                skipLine();
            }
        }

    }

    private void createLoadObject()
    {
        writeLine("/**");
        writeLine(" * load object");
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(readOnly=true)");
        writeLine("public " + this.bean.fullViewBean.className + " load(Long id) {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");
        writeLine("return this." + bean.fullViewBean.mapperObjectName + ".mapFrom(new " + this.bean.fullViewBean.className + "()," + this.bean.objectName + ");");
        writeLine("}");
        skipLine();

    }
    
    
    private void createFindObject()
    {
        List<Property> findPropertyList = this.bean.getReferenceProperties();

        writeLine("/**");
        writeLine(" * find object");
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(readOnly=true)");
        write("public " + this.bean.fullViewBean.className + " find(" + findPropertyList.get(0).beanDataType + " " + findPropertyList.get(0).name);
        for (int i=1;i<findPropertyList.size();i++)
        {
            write("," + findPropertyList.get(i).beanDataType + " " + findPropertyList.get(i).name);
        }
        writeLine(") {");
        write(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".find(" + findPropertyList.get(0).name);
        for (int i=1;i<findPropertyList.size();i++)
        {
            write(", " + findPropertyList.get(i).name);
        }
        writeLine(");");
        writeLine("return this." + bean.fullViewBean.mapperObjectName + ".mapFrom(new " + this.bean.fullViewBean.className + "(), " + this.bean.objectName + ");");
        writeLine("}");
        skipLine();
    }
    

    private void createLoadOneToOneComponent()
    {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(readOnly=true)");
            writeLine("public " + currentBean.fullViewBean.className + " load" + currentBean.className + "(Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = " + bean.objectName + ".get" + currentBean.className + "();");
            writeLine("if (" + currentBean.objectName + "==null) {");
            writeLine("return new " + currentBean.fullViewBean.className + "();");
            writeLine("} else {");
            writeLine("return this." + currentBean.fullViewBean.mapperObjectName + ".mapFrom(new " + currentBean.fullViewBean.className + "(), " + currentBean.objectName + ");");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
    }

    private void createLoadOneToManyComponentList()
    {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.objectName + " list");
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(readOnly=true)");
            writeLine("public List<" + currentBean.basicViewBean.className + "> load" + currentBean.className + "List(Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");
            writeLine("List<" + currentBean.basicViewBean.className + "> result = new ArrayList<>(" + this.bean.objectName + ".get" + currentBean.className + "Collection().size());");
            writeLine("for (" + currentBean.className + " " + currentBean.objectName + ":" + this.bean.objectName + ".get" + currentBean.className + "Collection()){");
            writeLine("result.add(this." + currentBean.basicViewBean.mapperObjectName + ".mapFrom(new " + currentBean.basicViewBean.className + "()," + currentBean.objectName + "));");
            writeLine("}");
            writeLine("return result;");
            writeLine("}");
            skipLine();
        }
    }

    private void createLoadOneToManyComponent()
    {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(readOnly=true)");
            writeLine("public " + currentBean.fullViewBean.className + " load" + currentBean.className + "(Long " + currentBean.objectName + "Id,Long id) {");            
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");            
            writeLine("for (" + currentBean.className + " collection" + currentBean.className + " : " + this.bean.objectName + ".get" + currentBean.className + "Collection()){");
            writeLine("if (collection" + currentBean.className + ".getId().equals(" + currentBean.objectName + "Id)){");
            writeLine("return this." + currentBean.fullViewBean.mapperObjectName + ".mapFrom(new " + currentBean.fullViewBean.className + "(),collection" + currentBean.className + ");");
            writeLine("}");
            writeLine("}");
            writeLine("throw new ObjectNotFoundException(" + CHAR_34 + "Invalid one to many component id" + CHAR_34 + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createCreateObject()
    {
        writeLine("/**");
        writeLine(" * create object");
        writeLine(" */");
        writeLine("@Override");
        writeLine("public " + this.bean.fullViewBean.className + " create() {");
        writeLine("return new " + this.bean.fullViewBean.className + "();");
        writeLine("}");
        skipLine();
    }

    private void createCreateOneToManyComponent()
    {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * create one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("public " + currentBean.fullViewBean.className + " create" + currentBean.className + "() {");
            writeLine("return new " + currentBean.fullViewBean.className + "();");
            writeLine("}");
            skipLine();
        }
    }

    private void createSaveObject()
    {
        writeLine("/**");
        writeLine(" * save object");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(rollbackFor=Exception.class)");
        writeLine("public Long save(" + this.bean.fullViewBean.className + " " + this.bean.fullViewBean.objectName + ") {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = this." + bean.fullViewBean.mapperObjectName + ".mapTo(" + this.bean.fullViewBean.objectName + ", new " + this.bean.className + "());");
        writeLine(this.bean.stateManagerObjectName + ".checkCanSave(" + this.bean.objectName + ");");
        writeLine("return " + this.bean.processorObjectName + ".save(" + this.bean.objectName + ");");
        writeLine("}");
        skipLine();
        
        
        for (Property property:bean.properties) {
        	if (property.referenceBean!=null) {
        		if (property.relation.equals(RelationType.MANY_TO_ONE)) {
        			
        			Bean parentBean = property.referenceBean;
        			
        			writeLine("/**");
        	        writeLine(" * save object from parent " + parentBean.className);        
        	        writeLine(" */");
        	        writeLine("@Override");
        	        writeLine("@Transactional(rollbackFor=Exception.class)");
        	        writeLine("public Long saveFrom" + parentBean.className + "(" + this.bean.fullViewBean.className + " " + this.bean.fullViewBean.objectName + ", Long " + parentBean.objectName + "Id) {");
        	        writeLine(this.bean.className + " " + this.bean.objectName + " = this." + bean.fullViewBean.mapperObjectName + ".mapTo(" + this.bean.fullViewBean.objectName + ", new " + this.bean.className + "());");
        	        writeLine(parentBean.className + " " + parentBean.objectName + " = this." + parentBean.daoObjectName + ".load(" + parentBean.objectName + "Id);");
        	        writeLine(this.bean.objectName + "." + property.setterName + "(" + parentBean.objectName + ");");
        	        writeLine(this.bean.stateManagerObjectName + ".checkCanSave(" + this.bean.objectName + ");");
        	        writeLine("return " + this.bean.processorObjectName + ".save(" + this.bean.objectName + ");");
        	        writeLine("}");
        	        skipLine();
        		}
        	}
        }
    }
    
    private void createSaveOneToOneComponent()
    {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * save one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void save" + currentBean.className + "(" + currentBean.fullViewBean.className + " " + currentBean.fullViewBean.objectName + ", Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = this." + currentBean.fullViewBean.mapperObjectName + ".mapTo(" + currentBean.fullViewBean.objectName + ", new " + currentBean.className + "());");
            writeLine(this.bean.stateManagerObjectName + ".checkCanSave" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine(this.bean.processorObjectName + ".save" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createSaveOneToManyComponent()
    {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * save one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void save" + currentBean.className + "(" + currentBean.fullViewBean.className + " " + currentBean.fullViewBean.objectName + ", Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = this." + currentBean.fullViewBean.mapperObjectName + ".mapTo(" + currentBean.fullViewBean.objectName + ", new " + currentBean.className + "());");
            writeLine(this.bean.stateManagerObjectName + ".checkCanSave" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine(this.bean.processorObjectName + ".save" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createUpdateObject()
    {
        writeLine("/**");        
        writeLine(" * update object");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(rollbackFor=Exception.class)");
        writeLine("public void update(" + this.bean.fullViewBean.className + " " + this.bean.fullViewBean.objectName + ") {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load(" + this.bean.fullViewBean.objectName + ".getId());");
        writeLine(this.bean.stateManagerObjectName + ".checkCanUpdate(" + this.bean.objectName + ");");
        writeLine(this.bean.objectName + " = this." + bean.fullViewBean.mapperObjectName + ".mapTo(" + this.bean.fullViewBean.objectName + ", " + this.bean.objectName + ");");
        writeLine(this.bean.processorObjectName + ".update" + "(" + bean.objectName + ");");
        writeLine("}");
        skipLine();
    }

    private void createUpdateOneTOneComponent()
    {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");
            writeLine(" * update one to one component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void update" + currentBean.className + "(" + currentBean.fullViewBean.className + " " + currentBean.fullViewBean.objectName + ", Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = " + bean.objectName + ".get" + currentBean.className + "();");
            writeLine(this.bean.stateManagerObjectName + ".checkCanUpdate" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine(this.bean.objectName + ".set" + currentBean.className + "(this." + currentBean.fullViewBean.mapperObjectName + ".mapTo(" + currentBean.fullViewBean.objectName + ", " + currentBean.objectName + "));");
            writeLine(this.bean.processorObjectName + ".update" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createUpdateOneToManyComponent()
    {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * update one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void update" + currentBean.className + "(" + currentBean.fullViewBean.className + " " + currentBean.fullViewBean.objectName + ") {");
            writeLine(currentBean.className + " " + currentBean.objectName + " = this." + this.bean.daoObjectName + ".load" + currentBean.className + "(" + currentBean.fullViewBean.objectName + ".getId());");
            writeLine(this.bean.stateManagerObjectName + ".checkCanUpdate" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine(currentBean.objectName + " = this." + currentBean.fullViewBean.mapperObjectName + ".mapTo(" + currentBean.fullViewBean.objectName + ", " + currentBean.objectName + ");");
            writeLine(this.bean.processorObjectName + ".update" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
            skipLine();
        }
    }

    private void createDeleteObject()
    {
        writeLine("/**");        
        writeLine(" * delete object");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(rollbackFor=Exception.class)");
        writeLine("public void delete(Long id) {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");
        writeLine(this.bean.stateManagerObjectName + ".checkCanDelete(" + this.bean.objectName + ");");
        writeLine(this.bean.processorObjectName + ".delete" + "(" + bean.objectName + ");");
        writeLine("}");
        skipLine();
    }

    private void createDeleteOneToManyComponent()
    {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");            
            writeLine(" * delete one to many component " + currentBean.objectName);            
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void delete" + currentBean.className + "(Long id) {");
            writeLine(currentBean.className + " " + currentBean.objectName + " = " + this.bean.daoObjectName + ".load" + currentBean.className + "(id);");
            writeLine(this.bean.stateManagerObjectName + ".checkCanDelete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("this." + this.bean.processorObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }
    
    private void createDeleteOneToOneComponent()
    {
        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
            Bean currentBean = oneToOneComponent.referenceBean;

            writeLine("/**");            
            writeLine(" * delete one to one component " + currentBean.objectName);            
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void delete" + currentBean.className + "(Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = " + bean.objectName + ".get" + currentBean.className + "();");
            writeLine(this.bean.stateManagerObjectName + ".checkCanDelete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("this." + this.bean.processorObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createDeleteObjectList()
    {
        writeLine("/**");        
        writeLine(" * delete object list");        
        writeLine(" */");
        writeLine("@Override");
        writeLine("@Transactional(rollbackFor=Exception.class)");
        writeLine("public void deleteList(List<Long> idList) {");
        writeLine(this.bean.className + " " + this.bean.objectName + ";");
        writeLine("if (idList != null){");
        writeLine("for (Long id:idList){");
        writeLine(this.bean.objectName + " = " + this.bean.daoObjectName + ".load(id);");
        writeLine(this.bean.stateManagerObjectName + ".checkCanDelete(" + this.bean.objectName + ");");
        writeLine(this.bean.processorObjectName + ".delete" + "(" + bean.objectName + ");");
        writeLine("}");
        writeLine("}");
        writeLine("}");
        skipLine();
    }

    private void createDeleteOneToManyComponentList()
    {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * delete one to many component " + currentBean.objectName + " list");
            writeLine(" */");
            writeLine("@Override");
            writeLine("@Transactional(rollbackFor=Exception.class)");
            writeLine("public void delete" + currentBean.className + "List(List<Long> idList) {");
            writeLine(currentBean.className + " " + currentBean.objectName + ";");
            writeLine("if (idList != null){");
            writeLine("for (Long i:idList){");
            writeLine(currentBean.objectName + " = " + this.bean.daoObjectName + ".load" + currentBean.className + "(i);");
            writeLine(this.bean.stateManagerObjectName + ".checkCanDelete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("this." + this.bean.processorObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
    }
}
