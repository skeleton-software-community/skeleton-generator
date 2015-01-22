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

public class BaseServiceInterfaceFileWriteCommand extends JavaFileWriteCommand {

private Bean bean;
	
	public BaseServiceInterfaceFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-api\\src\\main\\java\\"
				+ bean.myPackage.baseServiceInterfacePackageName.replace(".", "\\"), bean.baseServiceInterfaceName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Date;");
        javaImports.add("import java.util.List;");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");        

        for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
        }

        for (OneToManyComponent uniqueComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");
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

        


        if (this.bean.hasComboBox)
        {
            writeLine("/**");
            writeLine(" * get key list");
            writeLine(" */");
            writeLine("List<" + this.bean.properties.get(1).beanDataType + "> get" + this.bean.className + this.bean.properties.get(1).capName + "List();");
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
        writeLine("List<" + this.bean.viewClassName + "> load" + this.bean.className + "List();");
        skipLine();

        for (Property property : this.bean.properties)
        {
            if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE))
            {
                writeLine("/**");
                writeLine(" * load object list from " + property.name);
                writeLine(" */");
                writeLine("List<" + this.bean.viewClassName + "> load" + this.bean.className + "ListFrom" + property.capName + " (Long " + property.name + "Id);");
                skipLine();
            }
        }

    }

    private void createLoadObject()
    {
        writeLine("/**");
        writeLine(" * load object");
        writeLine(" */");
        writeLine(this.bean.viewClassName + " load" + this.bean.className + "(Long id);");
        skipLine();

    }
    
    
    private void createFindObject()
    {
        List<Property> findPropertyList = this.bean.getFindProperties();

        writeLine("/**");
        writeLine(" * find object");
        writeLine(" */");
        write(this.bean.viewClassName + " find" + this.bean.className + "(" + findPropertyList.get(0).beanDataType + " " + findPropertyList.get(0).name);
        for (int i=1;i<findPropertyList.size();i++)
        {
            write("," + findPropertyList.get(i).beanDataType + " " + findPropertyList.get(i).name);
        }
        writeLine(");");
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
            writeLine(currentBean.viewClassName + " load" + currentBean.className + "(Long id);");
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
            writeLine("List<" + currentBean.viewClassName + "> load" + currentBean.className + "List(Long id);");
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
            writeLine(currentBean.viewClassName + " load" + currentBean.className + "(Long " + currentBean.objectName + "Id,Long id);");            
            skipLine();
        }
    }

    private void createCreateObject()
    {
        writeLine("/**");
        writeLine(" * create object");
        writeLine(" */");
        writeLine(this.bean.viewClassName + " create" + this.bean.className + "();");
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
            writeLine(currentBean.viewClassName + " create" + currentBean.className + "();");
            skipLine();
        }
    }

    private void createSaveObject()
    {
        writeLine("/**");
        writeLine(" * save object");        
        writeLine(" */");
        writeLine("Long save" + this.bean.className + "(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ");");
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
            writeLine("void save" + currentBean.className + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ", Long id);");
            skipLine();
        }
    }

    private void createUpdateObject()
    {
        writeLine("/**");        
        writeLine(" * update object");        
        writeLine(" */");
        writeLine("void update" + this.bean.className + "(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ");");
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
            writeLine("void update" + currentBean.className + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ", Long id);");
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
            writeLine("void update" + currentBean.className + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ", Long id);");
            skipLine();
        }
    }

    private void createDeleteObject()
    {
        writeLine("/**");        
        writeLine(" * delete object");        
        writeLine(" */");
        writeLine("void delete" + this.bean.className + "(Long id);");
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
            writeLine("void delete" + currentBean.className + "(Long " + currentBean.objectName + "Id,Long id);");
            skipLine();
        }
    }

    private void createDeleteObjectList()
    {
        writeLine("/**");        
        writeLine(" * delete object list");        
        writeLine(" */");
        writeLine("void delete" + this.bean.className + "List(List<Long> idList);");
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
            writeLine("void delete" + currentBean.className + "List(List<Long> " + currentBean.objectName + "IdList,Long id);");
            skipLine();
        }
    }
}
