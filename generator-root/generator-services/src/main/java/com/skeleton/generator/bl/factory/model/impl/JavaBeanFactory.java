package com.skeleton.generator.bl.factory.model.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.skeleton.generator.bl.factory.model.interfaces.BeanFactory;
import com.skeleton.generator.bl.helper.naming.JavaClassNaming;
import com.skeleton.generator.model.enumeration.DataType;
import com.skeleton.generator.model.enumeration.RelationType;
import com.skeleton.generator.model.metadata.TableMetaData;
import com.skeleton.generator.model.om.Bean;
import com.skeleton.generator.model.om.Column;
import com.skeleton.generator.model.om.OneToMany;
import com.skeleton.generator.model.om.OneToManyComponent;
import com.skeleton.generator.model.om.OneToOne;
import com.skeleton.generator.model.om.OneToOneComponent;
import com.skeleton.generator.model.om.Property;
import com.skeleton.generator.model.om.Table;
import com.skeleton.generator.model.om.UniqueComponent;

@Component(value="javaBeanFactory")
public class JavaBeanFactory implements BeanFactory {

	@Override
	public Bean buildBean(TableMetaData tableMetaData, Table table) {

		Bean bean = new Bean();
        bean.table = table;
        table.myPackage.beanList.add(bean);
        bean.myPackage = table.myPackage;
        bean.isComponent = false;
        
        bean.cardinality = table.cardinality;
        bean.interfaces = tableMetaData.getInterfaceList();
        bean.annotations = tableMetaData.getAnnotationList();
        bean.createEnabled = tableMetaData.isCreateEnabled();
        bean.updateEnabled = tableMetaData.isUpdateEnabled();
        bean.deleteEnabled = tableMetaData.isDeleteEnabled();
        bean.hasComboBox = tableMetaData.isComboxable();
        bean.detailRendering = tableMetaData.getDetailRendering();
        bean.listRendering = tableMetaData.getListRendering();
        
        bean.className = JavaClassNaming.getClassName(table.originalName);
        bean.objectName = JavaClassNaming.getObjectName(table.originalName);
        bean.viewClassName = bean.className + "View";
        bean.viewObjectName = bean.objectName + "View";

        bean.baseDaoClassName = bean.className + "BaseDaoImpl";
        bean.daoClassName = bean.className + "DaoImpl";
        bean.baseDaoInterfaceName = bean.className + "BaseDao"; ;
        bean.daoInterfaceName = bean.className + "Dao";
        bean.daoObjectName = bean.objectName + "Dao";

        bean.baseServiceClassName = bean.className + "BaseServiceImpl";
        bean.serviceClassName = bean.className + "ServiceImpl";
        bean.baseServiceInterfaceName = bean.className + "BaseService";
        bean.serviceInterfaceName = bean.className + "Service";
        bean.serviceObjectName = bean.objectName + "Service";

        bean.baseMapperClassName = bean.className + "BaseMapperImpl";
        bean.mapperClassName = bean.className + "MapperImpl";
        bean.baseMapperInterfaceName = bean.className + "BaseMapper";
        bean.mapperInterfaceName = bean.className + "Mapper";
        bean.mapperObjectName = bean.objectName + "Mapper";

        bean.baseStateManagerClassName = bean.className + "BaseStateManagerImpl";
        bean.stateManagerClassName = bean.className + "StateManagerImpl";
        bean.baseStateManagerInterfaceName = bean.className + "BaseStateManager";
        bean.stateManagerInterfaceName = bean.className + "StateManager";
        bean.stateManagerObjectName = bean.objectName + "StateManager";

        bean.baseControllerClassName = bean.className + "BaseController";
        bean.controllerClassName = bean.className + "Controller";
        bean.controllerObjectName = bean.objectName + "Controller";

        bean.propertyList = new ArrayList<Property>();
        bean.oneToManyComponentList = new ArrayList<OneToManyComponent>();
        bean.oneToManyList = new ArrayList<OneToMany>();
        bean.oneToOneList = new ArrayList<OneToOne>();
        bean.oneToOneComponentList = new ArrayList<OneToOneComponent>();
        bean.uniqueComponentList = new ArrayList<UniqueComponent>();

        for (Column column : table.columnList)
        {
            if (column.name.toLowerCase().equals("status"))
            {
                bean.hasStatus = true;
            }
            Property property = null;

            if (!RelationType.isComponentLink(column.relation));
            {
                property = new Property();
                property.column = column;
                if (column.referenceTable != null)
                {
                    property.name = JavaClassNaming.getObjectName(column.originalName.replace("_ID", "").replace("_id", ""));
                    property.capName = JavaClassNaming.getClassName(column.originalName.replace("_ID", "").replace("_id", ""));
                    property.referenceBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
                    property.beanDataType = property.referenceBean.className;
                }
                else
                {
                    property.name = JavaClassNaming.getObjectName(column.originalName);
                    property.capName = JavaClassNaming.getClassName(column.originalName);
                    property.beanDataType = DataType.getJavaType(column.dataType);

                }
                property.getterName = "get" + property.capName;
                property.setterName = "set" + property.capName;
                property.fetchName = property.getterName + "()";
                property.dataType = column.dataType;
                property.nullable = column.nullable;
                property.relation = column.relation;
                property.unique = column.unique;
                property.format = column.format;
                property.listVisible = column.listVisible;
                property.detailVisible = column.detailVisible;
                property.editable = column.editable;
                property.rendering = column.rendering;
                bean.propertyList.add(property);
            }

            if (column.relation.equals(RelationType.MANY_TO_ONE))
            {
                OneToMany oneToMany = new OneToMany();
                oneToMany.referenceBean = bean;
                oneToMany.referenceProperty = property;
                oneToMany.collectionName = bean.objectName + "Collection";
                oneToMany.collectionGetterName = "get" + bean.className + "Collection";
                oneToMany.collectionSetterName = "set" + bean.className + "Collection";
                Bean targetBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
                targetBean.oneToManyList.add(oneToMany);
            }

            if (column.relation.equals(RelationType.MANY_TO_ONE_COMPONENT))
            {
                bean.isComponent = true;
                OneToManyComponent oneToManyComponent = new OneToManyComponent();
                oneToManyComponent.referenceBean = bean;
                oneToManyComponent.referenceColumn = column;
                oneToManyComponent.collectionName = bean.objectName + "Collection";
                oneToManyComponent.collectionGetterName = "get" + bean.className + "Collection";
                oneToManyComponent.collectionSetterName = "set" + bean.className + "Collection";
                Bean parentBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
                oneToManyComponent.parentBean = parentBean;
                parentBean.oneToManyComponentList.add(oneToManyComponent);
            }

            if (column.relation.equals(RelationType.ONE_TO_ONE))
            {
                OneToOne oneToOne = new OneToOne();
                oneToOne.referenceBean = bean;
                oneToOne.referenceProperty = property;
                oneToOne.getterName = "get" + bean.className;
                oneToOne.setterName = "set" + bean.className;
                Bean targetBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
                targetBean.oneToOneList.add(oneToOne);
            }

            if (column.relation.equals(RelationType.ONE_TO_ONE_COMPONENT))
            {
                bean.isComponent = true;
                OneToOneComponent oneToOne = new OneToOneComponent();
                oneToOne.referenceBean = bean;
                oneToOne.referenceColumn = column;
                oneToOne.getterName = "get" + bean.className;
                oneToOne.setterName = "set" + bean.className;
                Bean targetBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
                targetBean.oneToOneComponentList.add(oneToOne);
            }

            if (column.relation.equals(RelationType.UNIQUE_COMPONENT))
            {
                Bean targetBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
                targetBean.isComponent = true;
                UniqueComponent uniqueComponent = new UniqueComponent();
                uniqueComponent.parentBean = bean;
                uniqueComponent.referenceBean = targetBean;
                uniqueComponent.referenceColumn = column;
                uniqueComponent.getterName = "get" + targetBean.className;
                uniqueComponent.setterName = "set" + targetBean.className;
                bean.uniqueComponentList.add(uniqueComponent);
            }
        }

        return bean;
	}

}
