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
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.basicViewBean.className + ";");
        javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.fullViewBean.className + ";");
        
        for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.fullViewBean.className + ";");
        }

        for (OneToManyComponent uniqueComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = uniqueComponent.referenceBean;
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.basicViewBean.className + ";");
            javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.fullViewBean.className + ";");
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
        writeLine("List<" + this.bean.basicViewBean.className + "> load" + this.bean.className + "List();");
        skipLine();

        for (Property property : this.bean.properties)
        {
            if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE))
            {
                writeLine("/**");
                writeLine(" * load object list from " + property.name);
                writeLine(" */");
                writeLine("List<" + this.bean.basicViewBean.className + "> load" + this.bean.className + "ListFrom" + property.capName + " (Long " + property.name + "Id);");
                skipLine();
            }
        }

    }

    private void createLoadObject()
    {
        writeLine("/**");
        writeLine(" * load object");
        writeLine(" */");
        writeLine(this.bean.fullViewBean.className + " load" + this.bean.className + "(Long id);");
        skipLine();

    }
    
    
    private void createFindObject()
    {
        List<Property> findPropertyList = this.bean.getReferenceProperties();

        writeLine("/**");
        writeLine(" * find object");
        writeLine(" */");
        write(this.bean.fullViewBean.className + " find" + this.bean.className + "(" + findPropertyList.get(0).beanDataType + " " + findPropertyList.get(0).name);
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
            writeLine(currentBean.fullViewBean.className + " load" + currentBean.className + "(Long id);");
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
            writeLine("List<" + currentBean.basicViewBean.className + "> load" + currentBean.className + "List(Long id);");
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
            writeLine(currentBean.fullViewBean.className + " load" + currentBean.className + "(Long " + currentBean.objectName + "Id,Long id);");            
            skipLine();
        }
    }

    private void createCreateObject()
    {
        writeLine("/**");
        writeLine(" * create object");
        writeLine(" */");
        writeLine(this.bean.fullViewBean.className + " create" + this.bean.className + "();");
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
            writeLine(currentBean.fullViewBean.className + " create" + currentBean.className + "();");
            skipLine();
        }
    }

    private void createSaveObject()
    {
        writeLine("/**");
        writeLine(" * save object");        
        writeLine(" */");
        writeLine("Long save" + this.bean.className + "(" + this.bean.fullViewBean.className + " " + this.bean.fullViewBean.objectName + ");");
        skipLine();
        
        for (Property property:bean.properties) {
        	if (property.referenceBean!=null) {
        		if (property.relation.equals(RelationType.MANY_TO_ONE)) {
        			
        			Bean parentBean = property.referenceBean;
        			
        			writeLine("/**");
        	        writeLine(" * save object from parent " + parentBean.className);        
        	        writeLine(" */");
        	        writeLine("Long save" + this.bean.className + "From" + parentBean.className + "(" + this.bean.fullViewBean.className + " " + this.bean.fullViewBean.objectName + ", Long " + parentBean.objectName + "Id);");
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
            writeLine("void save" + currentBean.className + "(" + currentBean.fullViewBean.className + " " + currentBean.fullViewBean.objectName + ", Long id);");
            skipLine();
        }
    }

    private void createUpdateObject()
    {
        writeLine("/**");        
        writeLine(" * update object");        
        writeLine(" */");
        writeLine("void update" + this.bean.className + "(" + this.bean.fullViewBean.className + " " + this.bean.fullViewBean.objectName + ");");
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
            writeLine("void update" + currentBean.className + "(" + currentBean.fullViewBean.className + " " + currentBean.fullViewBean.objectName + ", Long id);");
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
            writeLine("void update" + currentBean.className + "(" + currentBean.fullViewBean.className + " " + currentBean.fullViewBean.objectName + ");");
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
            writeLine("void delete" + currentBean.className + "(Long id);");
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
            writeLine("void delete" + currentBean.className + "List(List<Long> idList);");
            skipLine();
        }
    }
}
