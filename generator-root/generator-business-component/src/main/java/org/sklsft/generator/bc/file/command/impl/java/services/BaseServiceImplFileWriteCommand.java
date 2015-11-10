package org.sklsft.generator.bc.file.command.impl.java.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.business.UniqueComponent;
import org.sklsft.generator.model.metadata.RelationType;

public class BaseServiceImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	
	public BaseServiceImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-services\\src\\main\\java\\"
				+ bean.myPackage.baseServiceImplPackageName.replace(".", "\\"), bean.baseServiceClassName);

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
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
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
        
        javaImports.add("import " + this.bean.myPackage.mapperImplPackageName + "." + this.bean.mapperClassName + ";");
        javaImports.add("import " + this.bean.myPackage.processorImplPackageName + "." + this.bean.processorClassName + ";");
        javaImports.add("import " + this.bean.myPackage.stateManagerImplPackageName + "." + this.bean.stateManagerClassName + ";");
        
        javaImports.add("import " + this.bean.myPackage.baseServiceInterfacePackageName + "." + this.bean.baseServiceInterfaceName + ";");
        

        for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
            javaImports.add("import " + currentBean.myPackage.mapperImplPackageName + "." + currentBean.mapperClassName + ";");
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
            javaImports.add("import " + currentBean.myPackage.mapperImplPackageName + "." + currentBean.mapperClassName + ";");
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
        writeLine("protected " + this.bean.mapperClassName + " " + this.bean.mapperObjectName + ";");
        
        for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            writeLine("@Autowired");
            writeLine("protected " + currentBean.mapperClassName + " " + currentBean.mapperObjectName + ";");
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("@Autowired");
            writeLine("protected " + currentBean.mapperClassName + " " + currentBean.mapperObjectName + ";");
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
            writeLine(" * get key list");
            writeLine(" */");
            writeLine("@Transactional(readOnly=true, value=" + (char)34 + "transactionManager" + (char)34 + ")");
            writeLine("public List<" + this.bean.properties.get(1).beanDataType + "> get" + this.bean.className + this.bean.properties.get(1).capName + "List() {");
            writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".load" + this.bean.className + "List();");
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
        createLoadUniqueComponent();
        createLoadOneToManyComponentList();
        createLoadOneToManyComponent();
        createCreateObject();
        createCreateOneToManyComponent();
        createSaveObject();
        createSaveOneToManyComponent();
        createUpdateObject();
        createUpdateUniqueComponent();
        createUpdateOneToManyComponent();
        createDeleteObject();
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
        writeLine("@Transactional(readOnly=true, value=" + (char)34 + "transactionManager" + (char)34 + ")");
        writeLine("public List<" + this.bean.viewClassName + "> load" + this.bean.className + "List() {");
        writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".load" + this.bean.className + "ListEagerly();");
        writeLine("List<" + this.bean.viewClassName + "> " + this.bean.viewObjectName + "List = new ArrayList<>(" + this.bean.objectName + "List.size());");
        writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
        writeLine(this.bean.viewObjectName + "List.add(this." + bean.mapperObjectName + ".mapFrom(new " + this.bean.viewClassName + "()," + this.bean.objectName + "));");
        writeLine("}");
        writeLine("return " + this.bean.viewObjectName + "List;");
        writeLine("}");
        skipLine();

        for (Property property : this.bean.properties)
        {
            if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE))
            {
                writeLine("/**");
                writeLine(" * load object list from " + property.name);
                writeLine(" */");
                writeLine("@Transactional(readOnly=true, value=" + (char)34 + "transactionManager" + (char)34 + ")");
                writeLine("public List<" + this.bean.viewClassName + "> load" + this.bean.className + "ListFrom" + property.capName + " (Long " + property.name + "Id) {");
                writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".load" + this.bean.className + "ListEagerlyFrom" + property.capName + " (" + property.name + "Id);");
                writeLine("List<" + this.bean.viewClassName + "> " + this.bean.viewObjectName + "List = new ArrayList<>(" + this.bean.objectName + "List.size());");
                writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
                writeLine(this.bean.viewObjectName + "List.add(this." + bean.mapperObjectName + ".mapFrom(new " + this.bean.viewClassName + "()," + this.bean.objectName + "));");
                writeLine("}");
                writeLine("return " + this.bean.viewObjectName + "List;");
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
        writeLine("@Transactional(readOnly=true, value=" + (char)34 + "transactionManager" + (char)34 + ")");
        writeLine("public " + this.bean.viewClassName + " load" + this.bean.className + "(Long id) {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
        writeLine("return this." + bean.mapperObjectName + ".mapFrom(new " + this.bean.viewClassName + "()," + this.bean.objectName + ");");
        writeLine("}");
        skipLine();

    }
    
    
    private void createFindObject()
    {
        List<Property> findPropertyList = this.bean.getFindProperties();

        writeLine("/**");
        writeLine(" * find object");
        writeLine(" */");
        writeLine("@Transactional(readOnly=true, value=" + (char)34 + "transactionManager" + (char)34 + ")");
        write("public " + this.bean.viewClassName + " find" + this.bean.className + "(" + findPropertyList.get(0).beanDataType + " " + findPropertyList.get(0).name);
        for (int i=1;i<findPropertyList.size();i++)
        {
            write("," + findPropertyList.get(i).beanDataType + " " + findPropertyList.get(i).name);
        }
        writeLine(") {");
        write(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".find" + this.bean.className + "(" + findPropertyList.get(0).name);
        for (int i=1;i<findPropertyList.size();i++)
        {
            write(", " + findPropertyList.get(i).name);
        }
        writeLine(");");
        writeLine("return this." + bean.mapperObjectName + ".mapFrom(new " + this.bean.viewClassName + "()," + this.bean.objectName + ");");
        writeLine("}");
        skipLine();
    }
    

    private void createLoadUniqueComponent()
    {
        for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;

            writeLine("/**");
            writeLine(" * load unique component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Transactional(readOnly=true, value=" + (char)34 + "transactionManager" + (char)34 + ")");
            writeLine("public " + currentBean.viewClassName + " load" + currentBean.className + "(Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine("return this." + currentBean.mapperObjectName + ".mapFrom(new " + currentBean.viewClassName + "()," + this.bean.objectName + ".get" + currentBean.className + "());");
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
            writeLine("@Transactional(readOnly=true, value=" + (char)34 + "transactionManager" + (char)34 + ")");
            writeLine("public List<" + currentBean.viewClassName + "> load" + currentBean.className + "List(Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine("List<" + currentBean.viewClassName + "> " + currentBean.viewObjectName + "List = new ArrayList<>(" + this.bean.objectName + ".get" + currentBean.className + "Collection().size());");
            writeLine("for (" + currentBean.className + " " + currentBean.objectName + ":" + this.bean.objectName + ".get" + currentBean.className + "Collection()){");
            writeLine(currentBean.viewObjectName + "List.add(this." + currentBean.mapperObjectName + ".mapFrom(new " + currentBean.viewClassName + "()," + currentBean.objectName + "));");
            writeLine("}");
            writeLine("return " + currentBean.viewObjectName + "List;");
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
            writeLine("@Transactional(readOnly=true, value=" + (char)34 + "transactionManager" + (char)34 + ")");
            writeLine("public " + currentBean.viewClassName + " load" + currentBean.className + "(Long " + currentBean.objectName + "Id,Long id) {");            
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");            
            writeLine("for (" + currentBean.className + " collection" + currentBean.className + " : " + this.bean.objectName + ".get" + currentBean.className + "Collection()){");
            writeLine("if (collection" + currentBean.className + ".getId().equals(" + currentBean.objectName + "Id)){");
            writeLine("return this." + currentBean.mapperObjectName + ".mapFrom(new " + currentBean.viewClassName + "(),collection" + currentBean.className + ");");
            writeLine("}");
            writeLine("}");
            writeLine("throw new ObjectNotFoundException(" + (char)34 + "Invalid one to many component id" + (char)34 + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createCreateObject()
    {
        writeLine("/**");
        writeLine(" * create object");
        writeLine(" */");
        writeLine("public " + this.bean.viewClassName + " create" + this.bean.className + "() {");
        writeLine("return new " + this.bean.viewClassName + "();");
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
            writeLine("public " + currentBean.viewClassName + " create" + currentBean.className + "() {");
            writeLine("return new " + currentBean.viewClassName + "();");
            writeLine("}");
            skipLine();
        }
    }

    private void createSaveObject()
    {
        writeLine("/**");
        writeLine(" * save object");        
        writeLine(" */");
        writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + "transactionManager" + (char)34 + ")");
        writeLine("public Long save" + this.bean.className + "(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ") {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = this." + bean.mapperObjectName + ".mapTo(" + this.bean.viewObjectName + ", new " + this.bean.className + "());");
        writeLine(this.bean.stateManagerObjectName + ".checkBeforeSave(" + this.bean.objectName + ");");
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
        	        writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + "transactionManager" + (char)34 + ")");
        	        writeLine("public Long save" + this.bean.className + "From" + parentBean.className + "(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ", Long " + parentBean.objectName + "Id) {");
        	        writeLine(this.bean.className + " " + this.bean.objectName + " = this." + bean.mapperObjectName + ".mapTo(" + this.bean.viewObjectName + ", new " + this.bean.className + "());");
        	        writeLine(parentBean.className + " " + parentBean.objectName + " = this." + parentBean.daoObjectName + ".load" + parentBean.className + "(" + parentBean.objectName + "Id);");
        	        writeLine(this.bean.objectName + "." + property.setterName + "(" + parentBean.objectName + ");");
        	        writeLine(this.bean.stateManagerObjectName + ".checkBeforeSave(" + this.bean.objectName + ");");
        	        writeLine("return " + this.bean.processorObjectName + ".save(" + this.bean.objectName + ");");
        	        writeLine("}");
        	        skipLine();
        		}
        	}
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
            writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + "transactionManager" + (char)34 + ")");
            writeLine("public void save" + currentBean.className + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ", Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = this." + currentBean.mapperObjectName + ".mapTo(" + currentBean.viewObjectName + ", new " + currentBean.className + "());");
            writeLine(this.bean.stateManagerObjectName + ".checkBeforeSave" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
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
        writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + "transactionManager" + (char)34 + ")");
        writeLine("public void update" + this.bean.className + "(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ") {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load" + this.bean.className + "(" + this.bean.viewObjectName + ".getId());");
        writeLine(this.bean.stateManagerObjectName + ".checkBeforeUpdate(" + this.bean.objectName + ");");
        writeLine(this.bean.objectName + " = this." + bean.mapperObjectName + ".mapTo(" + this.bean.viewObjectName + ", " + this.bean.objectName + ");");
        writeLine(this.bean.processorObjectName + ".update" + "(" + bean.objectName + ");");
        writeLine("}");
        skipLine();
    }

    private void createUpdateUniqueComponent()
    {
        for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;

            writeLine("/**");
            writeLine(" * update unique component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + "transactionManager" + (char)34 + ")");
            writeLine("public void update" + currentBean.className + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ", Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine(this.bean.stateManagerObjectName + ".checkBeforeUpdate" + currentBean.className + "(" + this.bean.objectName + ");");
            writeLine(this.bean.objectName + ".set" + currentBean.className + "(this." + currentBean.mapperObjectName + ".mapTo(" + currentBean.viewObjectName + ", " + bean.objectName + ".get" + currentBean.className + "()));");
            writeLine(this.bean.processorObjectName + ".update" + currentBean.className + "(" + bean.objectName + ");");
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
            writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + "transactionManager" + (char)34 + ")");
            writeLine("public void update" + currentBean.className + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ") {");
            writeLine(currentBean.className + " " + currentBean.objectName + " = this." + this.bean.daoObjectName + ".load" + currentBean.className + "(" + currentBean.viewObjectName + ".getId());");
            writeLine(this.bean.stateManagerObjectName + ".checkBeforeUpdate" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine(currentBean.objectName + " = this." + currentBean.mapperObjectName + ".mapTo(" + currentBean.viewObjectName + ", " + currentBean.objectName + ");");
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
        writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + "transactionManager" + (char)34 + ")");
        writeLine("public void delete" + this.bean.className + "(Long id) {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
        writeLine(this.bean.stateManagerObjectName + ".checkBeforeDelete(" + this.bean.objectName + ");");
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
            writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + "transactionManager" + (char)34 + ")");
            writeLine("public void delete" + currentBean.className + "(Long id) {");
            writeLine(currentBean.className + " " + currentBean.objectName + " = " + this.bean.daoObjectName + ".load" + currentBean.className + "(id);");
            writeLine(this.bean.stateManagerObjectName + ".checkBeforeDelete" + currentBean.className + "(" + currentBean.objectName + ");");
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
        writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + "transactionManager" + (char)34 + ")");
        writeLine("public void delete" + this.bean.className + "List(List<Long> idList) {");
        writeLine(this.bean.className + " " + this.bean.objectName + ";");
        writeLine("if (idList != null){");
        writeLine("for (Long i:idList){");
        writeLine(this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(i);");
        writeLine(this.bean.stateManagerObjectName + ".checkBeforeDelete(" + this.bean.objectName + ");");
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
            writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + "transactionManager" + (char)34 + ")");
            writeLine("public void delete" + currentBean.className + "List(List<Long> idList) {");
            writeLine(currentBean.className + " " + currentBean.objectName + ";");
            writeLine("if (idList != null){");
            writeLine("for (Long i:idList){");
            writeLine(currentBean.objectName + " = " + this.bean.daoObjectName + ".load" + currentBean.className + "(i);");
            writeLine(this.bean.stateManagerObjectName + ".checkBeforeDelete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("this." + this.bean.processorObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("}");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
    }
}
