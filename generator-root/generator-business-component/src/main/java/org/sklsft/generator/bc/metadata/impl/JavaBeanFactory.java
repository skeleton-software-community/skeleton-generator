package org.sklsft.generator.bc.metadata.impl;

import java.util.ArrayList;

import org.sklsft.generator.bc.metadata.interfaces.BeanFactory;
import org.sklsft.generator.bc.util.naming.JavaClassNaming;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Column;
import org.sklsft.generator.model.om.Model;
import org.sklsft.generator.model.om.OneToMany;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.OneToOne;
import org.sklsft.generator.model.om.Property;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.model.om.UniqueComponent;
import org.springframework.stereotype.Component;

@Component(value = "javaBeanFactory")
public class JavaBeanFactory implements BeanFactory {

	@Override
	public Bean scanBean(TableMetaData tableMetaData, Table table) {
		Bean bean = new Bean();
		bean.table = table;
		bean.myPackage = table.myPackage;
		bean.isComponent = false;

		bean.cardinality = table.cardinality;
		bean.interfaces = tableMetaData.getInterfaces();
		bean.annotations = tableMetaData.getAnnotations();
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
		bean.baseDaoInterfaceName = bean.className + "BaseDao";
		;
		bean.daoInterfaceName = bean.className + "Dao";
		bean.daoObjectName = bean.objectName + "Dao";

		bean.baseServiceClassName = bean.className + "BaseServiceImpl";
		bean.serviceClassName = bean.className + "ServiceImpl";
		bean.baseServiceInterfaceName = bean.className + "BaseService";
		bean.serviceInterfaceName = bean.className + "Service";
		bean.serviceObjectName = bean.objectName + "Service";

		bean.baseMapperName = bean.className + "BaseMapper";
		bean.mapperName = bean.className + "Mapper";
		bean.mapperObjectName = bean.objectName + "Mapper";

		bean.baseStateManagerClassName = bean.className + "BaseStateManagerImpl";
		bean.stateManagerClassName = bean.className + "StateManagerImpl";
		bean.baseStateManagerInterfaceName = bean.className + "BaseStateManager";
		bean.stateManagerInterfaceName = bean.className + "StateManager";
		bean.stateManagerObjectName = bean.objectName + "StateManager";

		bean.baseControllerClassName = bean.className + "BaseController";
		bean.controllerClassName = bean.className + "Controller";
		bean.controllerObjectName = bean.objectName + "Controller";
		
		bean.filterClassName = bean.className + "DataTableFilter";
		bean.filterObjectName = bean.objectName + "DataTableFilter";

		bean.properties = new ArrayList<Property>();
		bean.oneToManyComponentList = new ArrayList<OneToManyComponent>();
		bean.oneToManyList = new ArrayList<OneToMany>();
		bean.oneToOneList = new ArrayList<OneToOne>();
		bean.uniqueComponentList = new ArrayList<UniqueComponent>();
		
		return bean;
	}

	@Override
	public Bean fillBean(Table table, Model model) {
		Bean bean = model.findBean(table.originalName);
		
		for (Column column : table.columns) {
			if (column.name.toLowerCase().equals("status")) {
				bean.hasStatus = true;
			}
			Property property = null;

			if (!RelationType.isComponentLink(column.relation)) {
				property = new Property();
				property.column = column;
				if (column.referenceTable != null) {
					property.name = JavaClassNaming.getObjectName(column.originalName.replace("_ID", "").replace("_id", ""));
					property.capName = JavaClassNaming.getClassName(column.originalName.replace("_ID", "").replace("_id", ""));
					property.referenceBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
					property.beanDataType = property.referenceBean.className;
				} else {
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
				property.visibility = column.visibility;
				property.editable = column.editable;
				property.rendering = column.rendering;
				property.annotations = column.annotations;
				bean.properties.add(property);
			}

			if (column.relation.equals(RelationType.MANY_TO_ONE)) {
				OneToMany oneToMany = new OneToMany();
				oneToMany.referenceBean = bean;
				oneToMany.referenceProperty = property;
				oneToMany.collectionName = bean.objectName + "Collection";
				oneToMany.collectionGetterName = "get" + bean.className + "Collection";
				oneToMany.collectionSetterName = "set" + bean.className + "Collection";
				Bean targetBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
				targetBean.oneToManyList.add(oneToMany);
			}

			if (column.relation.equals(RelationType.MANY_TO_ONE_COMPONENT)) {
				bean.isComponent = true;
				bean.isManyToOneComponent = true;
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

			if (column.relation.equals(RelationType.ONE_TO_ONE)) {
				OneToOne oneToOne = new OneToOne();
				oneToOne.referenceBean = bean;
				oneToOne.referenceProperty = property;
				oneToOne.getterName = "get" + bean.className;
				oneToOne.setterName = "set" + bean.className;
				Bean targetBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
				targetBean.oneToOneList.add(oneToOne);
			}

			if (column.relation.equals(RelationType.UNIQUE_COMPONENT)) {
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
