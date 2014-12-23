package org.sklsft.generator.bc.file.command.impl.java.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Property;
import org.sklsft.generator.model.om.UniqueComponent;

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
        javaImports.add("import " + this.bean.myPackage.model.daoExceptionPackageName + ".ObjectNotFoundException;");
        javaImports.add("import " + this.bean.myPackage.stateManagerImplPackageName + "." + this.bean.stateManagerClassName + ";");
        
        javaImports.add("import org.springframework.dao.RecoverableDataAccessException;");
        javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
        javaImports.add("import org.springframework.transaction.annotation.Transactional;");
        javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
        javaImports.add("import " + this.bean.myPackage.DAOInterfacePackageName + "." + this.bean.daoInterfaceName + ";");
        javaImports.add("import " + this.bean.myPackage.mapperImplPackageName + "." + this.bean.mapperClassName + ";");
        
        javaImports.add("import " + this.bean.myPackage.baseServiceInterfacePackageName + "." + this.bean.baseServiceInterfaceName + ";");
        javaImports.add("import " + this.bean.myPackage.model.stateExceptionPackageName + ".InvalidStateException;");

        for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
        }

        for (OneToManyComponent uniqueComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
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
        writeLine("@Autowired");
        writeLine("protected " + this.bean.mapperClassName + " " + this.bean.mapperObjectName + ";");
        writeLine("@Autowired");
        writeLine("protected " + this.bean.stateManagerClassName + " " + this.bean.stateManagerObjectName + ";");
        skipLine();


        if (this.bean.hasComboBox)
        {
            writeLine("/**");
            writeLine(" * get key list");
            writeLine(" */");
            writeLine("@Transactional(readOnly=true, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
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
        writeLine("@Transactional(readOnly=true, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
        writeLine("public List<" + this.bean.viewClassName + "> load" + this.bean.className + "List() {");
        writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".load" + this.bean.className + "ListEagerly();");
        writeLine("List<" + this.bean.viewClassName + "> " + this.bean.viewObjectName + "List = new ArrayList<>(" + this.bean.objectName + "List.size());");
        writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
        writeLine(this.bean.viewObjectName + "List.add(this." + bean.mapperObjectName + ".map" + this.bean.viewClassName + "(new " + this.bean.viewClassName + "()," + this.bean.objectName + "));");
        writeLine("}");
        writeLine("return " + this.bean.viewObjectName + "List;");
        writeLine("}");
        skipLine();

        for (Property property : this.bean.properties)
        {
            if (property.referenceBean != null && !property.relation.equals(RelationType.PROPERTY))
            {
                writeLine("/**");
                writeLine(" * load object list from list of " + property.name);
                writeLine(" */");
                writeLine("@Transactional(readOnly=true, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
                writeLine("public List<" + this.bean.viewClassName + "> load" + this.bean.className + "ListFrom" + property.capName + "List (List<Long> " + property.name + "IdList) {");
                writeLine("List<" + this.bean.className + "> " + this.bean.objectName + "List = " + this.bean.daoObjectName + ".load" + this.bean.className + "ListEagerlyFrom" + property.capName + "List (" + property.name + "IdList);");
                writeLine("List<" + this.bean.viewClassName + "> " + this.bean.viewObjectName + "List = new ArrayList<>(" + this.bean.objectName + "List.size());");
                writeLine("for (" + this.bean.className + " " + this.bean.objectName + " : " + this.bean.objectName + "List) {");
                writeLine(this.bean.viewObjectName + "List.add(this." + bean.mapperObjectName + ".map" + this.bean.viewClassName + "(new " + this.bean.viewClassName + "()," + this.bean.objectName + "));");
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
        writeLine("@Transactional(readOnly=true, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
        writeLine("public " + this.bean.viewClassName + " load" + this.bean.className + "(Long id) {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
        writeLine("return this." + bean.mapperObjectName + ".map" + this.bean.viewClassName + "(new " + this.bean.viewClassName + "()," + this.bean.objectName + ");");
        writeLine("}");
        skipLine();

    }

    private void createFindObject()
    {
        List<Property> findPropertyList = this.bean.getFindProperties();

        writeLine("/**");
        writeLine(" * find object");
        writeLine(" */");
        writeLine("@Transactional(readOnly=true, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
        write("public " + this.bean.viewClassName + " find" + this.bean.className + "(" + findPropertyList.get(0).beanDataType + " " + findPropertyList.get(0).name);
        for (int i=1;i<findPropertyList.size();i++)
        {
            write("," + findPropertyList.get(i).beanDataType + " " + findPropertyList.get(i).name);
        }
        writeLine(") throws ObjectNotFoundException {");
        write(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".find" + this.bean.className + "(" + findPropertyList.get(0).name);
        for (int i=1;i<findPropertyList.size();i++)
        {
            write(", " + findPropertyList.get(i).name);
        }
        writeLine(");");
        writeLine("return this." + bean.mapperObjectName + ".map" + this.bean.viewClassName + "(new " + this.bean.viewClassName + "()," + this.bean.objectName + ");");
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
            writeLine("@Transactional(readOnly=true, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
            writeLine("public " + currentBean.viewClassName + " load" + currentBean.className + "(Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine("return this." + bean.mapperObjectName + ".map" + currentBean.viewClassName + "(new " + currentBean.viewClassName + "()," + this.bean.objectName + ".get" + currentBean.className + "());");
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
            writeLine("@Transactional(readOnly=true, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
            writeLine("public List<" + currentBean.viewClassName + "> load" + currentBean.className + "List(Long id) {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine("List<" + currentBean.viewClassName + "> " + currentBean.viewObjectName + "List = new ArrayList<>(" + this.bean.objectName + ".get" + currentBean.className + "Collection().size());");
            writeLine("for (" + currentBean.className + " " + currentBean.objectName + ":" + this.bean.objectName + ".get" + currentBean.className + "Collection()){");
            writeLine(currentBean.viewObjectName + "List.add(this." + bean.mapperObjectName + ".map" + currentBean.viewClassName + "(new " + currentBean.viewClassName + "()," + currentBean.objectName + "));");
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
            writeLine("@Transactional(readOnly=true, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
            writeLine("public " + currentBean.viewClassName + " load" + currentBean.className + "(Long " + currentBean.objectName + "Id,Long id) {");            
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");            
            writeLine("for (" + currentBean.className + " collection" + currentBean.className + " : " + this.bean.objectName + ".get" + currentBean.className + "Collection()){");
            writeLine("if (collection" + currentBean.className + ".getId().equals(" + currentBean.objectName + "Id)){");
            writeLine("return this." + bean.mapperObjectName + ".map" + currentBean.viewClassName + "(new " + currentBean.viewClassName + "(),collection" + currentBean.className + ");");
            writeLine("}");
            writeLine("}");
            writeLine("throw new RecoverableDataAccessException(" + (char)34 + "Invalid one to many component id" + (char)34 + ");");
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
        writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
        writeLine("public Long save" + this.bean.className + "(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ") throws ObjectNotFoundException, InvalidStateException {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = this." + bean.mapperObjectName + ".map" + this.bean.className + "(new " + this.bean.className + "()," + this.bean.viewObjectName + ");");
        writeLine("this." + this.bean.stateManagerObjectName + ".setDefault(" + this.bean.objectName + ");");
        writeLine("this." + this.bean.stateManagerObjectName + ".checkBeforeSave(" + this.bean.objectName + ");");
        writeLine("return " + this.bean.daoObjectName + ".save" + this.bean.className + "(" + this.bean.objectName + ");");
        writeLine("}");
        skipLine();
    }

    private void createSaveOneToManyComponent()
    {
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;

            writeLine("/**");
            writeLine(" * save one to many component " + currentBean.objectName);
            writeLine(" */");
            writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
            writeLine("public void save" + currentBean.className + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ", Long id) throws ObjectNotFoundException, InvalidStateException {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine(currentBean.className + " " + currentBean.objectName + " = this." + bean.mapperObjectName + ".map" + currentBean.className + "(new " + currentBean.className + "()," + currentBean.viewObjectName + ");");
            writeLine("this." + this.bean.stateManagerObjectName + ".setDefault" + currentBean.className + "(" + currentBean.objectName + ");");
            writeLine("this." + this.bean.stateManagerObjectName + ".checkBeforeSave" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine(this.bean.daoObjectName + ".save" + currentBean.className + "(" + this.bean.objectName + ", " + currentBean.objectName + ");");
            writeLine("}");
            skipLine();
        }
    }

    private void createUpdateObject()
    {
        writeLine("/**");        
        writeLine(" * update object");        
        writeLine(" */");
        writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
        writeLine("public void update" + this.bean.className + "(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ") throws ObjectNotFoundException, InvalidStateException {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load" + this.bean.className + "(" + this.bean.viewObjectName + ".getId());");
        writeLine("this." + this.bean.stateManagerObjectName + ".checkBeforeUpdate(" + this.bean.objectName + ", " + this.bean.viewObjectName + ");");
        writeLine(this.bean.objectName + " = this." + bean.mapperObjectName + ".map" + this.bean.className + "(" + this.bean.objectName + ", " + this.bean.viewObjectName + ");");
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
            writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
            writeLine("public void update" + currentBean.className + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ", Long id) throws ObjectNotFoundException, InvalidStateException {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine("this." + this.bean.stateManagerObjectName + ".checkBeforeUpdate" + currentBean.className + "(" + this.bean.objectName + ", " + currentBean.viewObjectName + ");");
            writeLine(this.bean.objectName + ".set" + currentBean.className + "(this." + bean.mapperObjectName + ".map" + currentBean.className + "(new " + currentBean.className + "(), " + currentBean.viewObjectName + "));");
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
            writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
            writeLine("public void update" + currentBean.className + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ", Long id) throws ObjectNotFoundException, InvalidStateException {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = this." + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine("this." + this.bean.stateManagerObjectName + ".checkBeforeUpdate" + currentBean.className + "(" + this.bean.objectName + "," + currentBean.viewObjectName + ");");
            writeLine("for (" + currentBean.className + " " + currentBean.objectName + " : " + this.bean.objectName + ".get" + currentBean.className + "Collection()){");
            writeLine("if (" + currentBean.objectName + ".getId().equals(" + currentBean.viewObjectName + ".getId())){");
            writeLine(currentBean.objectName + "  = this." + bean.mapperObjectName + ".map" + currentBean.className + "(" + currentBean.objectName + "," + currentBean.viewObjectName + ");");
            writeLine("break;");
            writeLine("}");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
    }

    private void createDeleteObject()
    {
        writeLine("/**");        
        writeLine(" * delete object");        
        writeLine(" */");
        writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
        writeLine("public void delete" + this.bean.className + "(Long id) throws InvalidStateException {");
        writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
        writeLine("this." + this.bean.stateManagerObjectName + ".checkBeforeDelete(" + this.bean.objectName + ");");
        writeLine(this.bean.daoObjectName + ".delete" + this.bean.className + "(" + this.bean.objectName + ");");
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
            writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
            writeLine("public void delete" + currentBean.className + "(Long " + currentBean.objectName + "Id,Long id) throws InvalidStateException {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine("Collection<" + currentBean.className + "> " + currentBean.objectName + "Collection = " + this.bean.objectName + ".get" + currentBean.className + "Collection();");
            writeLine("for (" + currentBean.className + " " + currentBean.objectName + " : " + currentBean.objectName + "Collection){");
            writeLine("if (" + currentBean.objectName + ".getId().equals(" + currentBean.objectName + "Id)){");
            writeLine("this." + this.bean.stateManagerObjectName + ".checkBeforeDelete" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine(currentBean.objectName + "Collection.remove(" + currentBean.objectName + ");");
            writeLine("break;");
            writeLine("}");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
    }

    private void createDeleteObjectList()
    {
        writeLine("/**");        
        writeLine(" * delete object list");        
        writeLine(" */");
        writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
        writeLine("public void delete" + this.bean.className + "List(List<Long> idList) throws InvalidStateException {");
        writeLine(this.bean.className + " " + this.bean.objectName + ";");
        writeLine("if (idList != null){");
        writeLine("for (Long i:idList){");
        writeLine(this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(i);");
        writeLine("this." + this.bean.stateManagerObjectName + ".checkBeforeDelete(" + this.bean.objectName + ");");
        writeLine(this.bean.daoObjectName + ".delete" + this.bean.className + "(" + this.bean.objectName + ");");
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
            writeLine("@Transactional(rollbackFor=Exception.class, value=" + (char)34 + bean.myPackage.model.project.projectName + "TransactionManager" + (char)34 + ")");
            writeLine("public void delete" + currentBean.className + "List(List<Long> " + currentBean.objectName + "IdList,Long id) throws InvalidStateException {");
            writeLine(this.bean.className + " " + this.bean.objectName + " = " + this.bean.daoObjectName + ".load" + this.bean.className + "(id);");
            writeLine("Collection<" + currentBean.className + "> " + currentBean.objectName + "Collection = " + this.bean.objectName + ".get" + currentBean.className + "Collection();");
            writeLine("if (" + currentBean.objectName + "IdList != null){");
            writeLine("for (Long " + currentBean.objectName + "Id:" + currentBean.objectName + "IdList){");
            writeLine("for (" + currentBean.className + " " + currentBean.objectName + " : " + currentBean.objectName + "Collection){");
            writeLine("if (" + currentBean.objectName + ".getId().equals(" + currentBean.objectName + "Id)){");
            writeLine("this." + this.bean.stateManagerObjectName + ".checkBeforeDelete" + currentBean.className + "(" + currentBean.objectName + "," + this.bean.objectName + ");");
            writeLine(currentBean.objectName + "Collection.remove(" + currentBean.objectName + ");");
            writeLine("break;");
            writeLine("}");
            writeLine("}");
            writeLine("}");
            writeLine("}");
            writeLine("}");
            skipLine();
        }
    }
}
