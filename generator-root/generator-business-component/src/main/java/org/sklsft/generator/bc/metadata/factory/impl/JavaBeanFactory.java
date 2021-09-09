package org.sklsft.generator.bc.metadata.factory.impl;

import org.sklsft.generator.bc.metadata.factory.interfaces.BeanFactory;
import org.sklsft.generator.model.domain.Model;
import org.sklsft.generator.model.domain.business.AccessRoles;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOne;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.business.SelectionBehavior;
import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.util.naming.JavaClassNaming;
import org.springframework.stereotype.Component;

@Component("javaBeanFactory")
public class JavaBeanFactory implements BeanFactory {

	@Override
	public Bean scanBean(TableMetaData tableMetaData, Table table) {
		Bean bean = new Bean();
		bean.table = table;
		bean.myPackage = table.myPackage;
		bean.isComponent = false;

		bean.cardinality = table.cardinality;
		bean.idType = table.idType.getJavaType();
		bean.detailMode = tableMetaData.getDetailMode();
		bean.interfaces = tableMetaData.getInterfaces();
		bean.annotations = tableMetaData.getAnnotations();
		bean.createEnabled = tableMetaData.getCreateEnabled();
		bean.updateEnabled = tableMetaData.getUpdateEnabled();
		bean.deleteEnabled = tableMetaData.getDeleteEnabled();
		bean.detailRendering = tableMetaData.getDetailRendering();
		bean.listRendering = tableMetaData.getListRendering();
		
		if (tableMetaData.getSelectionBehavior() != null) {
			SelectionBehavior selectionBehavior = new SelectionBehavior();
			selectionBehavior.selectionMode = tableMetaData.getSelectionBehavior().getSelectionMode();
			bean.selectionBehavior = selectionBehavior;
			bean.selectable = true;
		}
		
		if (tableMetaData.getAccessRoles() != null) {
			AccessRoles accessRoles = new AccessRoles();
			accessRoles.readRole = tableMetaData.getAccessRoles().getReadRole();
			accessRoles.createRole = tableMetaData.getAccessRoles().getCreateRole();
			accessRoles.saveRole = tableMetaData.getAccessRoles().getSaveRole();
			accessRoles.updateRole = tableMetaData.getAccessRoles().getUpdateRole();
			accessRoles.deleteRole = tableMetaData.getAccessRoles().getDeleteRole();
			bean.accessRoles = accessRoles;
		}

		bean.className = JavaClassNaming.toClassName(table.originalName);
		bean.objectName = JavaClassNaming.toObjectName(table.originalName);
		bean.urlPiece = JavaClassNaming.getUrlPiece(table.originalName);

		bean.baseDaoClassName = bean.className + "BaseDaoImpl";
		bean.daoClassName = bean.className + "DaoImpl";
		bean.baseDaoInterfaceName = bean.className + "BaseDao";
		;
		bean.daoInterfaceName = bean.className + "Dao";
		bean.daoObjectName = bean.objectName + "Dao";

		bean.baseServiceClassName = bean.className + "BaseServiceImpl";
		bean.serviceClassName = bean.className + "ServiceImpl";
		bean.baseRestClientClassName = bean.className + "BaseRestClient";
		bean.restClientClassName = bean.className + "RestClient";
		bean.baseServiceInterfaceName = bean.className + "BaseService";
		bean.serviceInterfaceName = bean.className + "Service";
		bean.serviceObjectName = bean.objectName + "Service";

		bean.baseStateManagerClassName = bean.className + "BaseStateManager";
		bean.stateManagerClassName = bean.className + "StateManager";
		bean.stateManagerObjectName = bean.objectName + "StateManager";

		bean.baseRightsManagerClassName = bean.className + "BaseRightsManager";
		bean.rightsManagerClassName = bean.className + "RightsManager";
		bean.rightsManagerObjectName = bean.objectName + "RightsManager";

		bean.baseProcessorClassName = bean.className + "BaseProcessor";
		bean.processorClassName = bean.className + "Processor";
		bean.processorObjectName = bean.objectName + "Processor";
		
		bean.baseRestControllerClassName = bean.className + "BaseController";
		bean.restControllerClassName = bean.className + "Controller";

		bean.baseListControllerClassName = bean.className + "BaseListController";
		bean.listControllerClassName = bean.className + "ListController";
		bean.listControllerObjectName = bean.objectName + "ListController";

		bean.baseDetailControllerClassName = bean.className + "BaseDetailController";
		bean.detailControllerClassName = bean.className + "DetailController";
		bean.detailControllerObjectName = bean.objectName + "DetailController";

		bean.detailViewClassName = bean.className + "DetailView";
		bean.detailViewObjectName = bean.objectName + "DetailView";
		bean.listViewClassName = bean.className + "ListView";
		bean.listViewObjectName = bean.objectName + "ListView";

		return bean;
	}

	@Override
	public Bean fillBean(TableMetaData tableMetaData, Table table, Model model) {
		Bean bean = model.findBean(table.originalName);

		for (Column column : table.columns) {

			Property property = new Property();
			property.column = column;

			if (column.referenceTable != null) {
				property.name = JavaClassNaming
						.toObjectName(column.originalName.replaceAll("_ID$", "").replaceAll("_id$", ""));
				property.capName = JavaClassNaming
						.toClassName(column.originalName.replaceAll("_ID$", "").replaceAll("_id$", ""));
				property.referenceBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
				property.beanDataType = property.referenceBean.className;
			} else {
				property.name = JavaClassNaming.toObjectName(column.originalName);
				property.capName = JavaClassNaming.toClassName(column.originalName);
				property.beanDataType = column.dataType.getJavaType();

			}
			property.getterName = "get" + property.capName;
			property.setterName = "set" + property.capName;

			property.dataType = column.dataType;
			property.nullable = column.nullable;
			property.relation = column.relation;
			property.embedded = property.relation.isEmbedded();
			property.unique = column.unique;
			property.visibility = column.visibility;
			property.editable = column.editable;
			property.filterable = column.filterable;
			property.rendering = column.rendering;
			property.annotations = column.annotations;
			bean.properties.add(property);

			if (column.relation.equals(RelationType.MANY_TO_ONE)) {
				OneToMany oneToMany = new OneToMany();
				oneToMany.referenceBean = bean;
				oneToMany.referenceProperty = property;
				oneToMany.collectionName = bean.objectName + "Collection";
				oneToMany.collectionGetterName = "get" + bean.className + "Collection";
				oneToMany.collectionSetterName = "set" + bean.className + "Collection";
				Bean parentBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
				bean.parentBean = parentBean;
				parentBean.oneToManyList.add(oneToMany);
				oneToMany.parentBean = parentBean;
			}

			if (column.relation.equals(RelationType.MANY_TO_ONE_COMPONENT)) {
				bean.isComponent = true;
				OneToManyComponent oneToManyComponent = new OneToManyComponent();
				oneToManyComponent.referenceBean = bean;
				oneToManyComponent.referenceProperty = property;
				oneToManyComponent.collectionName = bean.objectName + "Collection";
				oneToManyComponent.collectionGetterName = "get" + bean.className + "Collection";
				oneToManyComponent.collectionSetterName = "set" + bean.className + "Collection";
				Bean parentBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
				bean.parentBean = parentBean;
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

			if (column.relation.equals(RelationType.ONE_TO_ONE_COMPONENT)) {
				bean.isComponent = true;
				bean.isOneToOneComponent = true;
				OneToOneComponent oneToOneComponent = new OneToOneComponent();
				oneToOneComponent.referenceBean = bean;
				oneToOneComponent.referenceProperty = property;
				oneToOneComponent.getterName = "get" + bean.className;
				oneToOneComponent.setterName = "set" + bean.className;
				Bean parentBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
				bean.parentBean = parentBean;
				oneToOneComponent.parentBean = parentBean;
				parentBean.oneToOneComponentList.add(oneToOneComponent);
			}

			if (column.relation.equals(RelationType.EMBEDDED)) {
				Bean targetBean = bean.myPackage.model.findBean(column.referenceTable.originalName);
				targetBean.isEmbedded = true;
				targetBean.isComponent = true;
			}
		}
		
		if (bean.selectable) {
			bean.selectionBehavior.targetProperty = bean.properties.get(0);
			if (tableMetaData.getSelectionBehavior().getLabelColumn()!=null) {
				bean.selectionBehavior.labelProperty = bean.findPropertyByColumnName(tableMetaData.getSelectionBehavior().getLabelColumn());
			} else {
				bean.selectionBehavior.labelProperty = bean.properties.get(0);
			}
		}

		return bean;
	}

}
