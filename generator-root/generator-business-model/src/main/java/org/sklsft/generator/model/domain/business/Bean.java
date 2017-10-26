package org.sklsft.generator.model.domain.business;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.exception.PropertyNotFoundException;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.domain.ui.BasicViewBean;
import org.sklsft.generator.model.domain.ui.FormBean;
import org.sklsft.generator.model.domain.ui.FullViewBean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DetailMode;

/**
 * representation of a bean<br/>
 * Properties are willingly public because of their intensive use in file write
 * commands<br/>
 * 
 * @author Nicolas Thibault
 * 
 */
public class Bean {

	public Table table;
	public Package myPackage;
	public String className;
	public String objectName;

	public String baseDaoClassName;
	public String daoClassName;
	public String baseDaoInterfaceName;
	public String daoInterfaceName;
	public String daoObjectName;

	public String baseServiceClassName;
	public String serviceClassName;
	public String baseServiceInterfaceName;
	public String serviceInterfaceName;
	public String serviceObjectName;

	public String baseStateManagerClassName;
	public String stateManagerClassName;
	public String stateManagerObjectName;
	
	public String baseRightsManagerClassName;
	public String rightsManagerClassName;
	public String rightsManagerObjectName;
	
	public String baseProcessorClassName;
	public String processorClassName;
	public String processorObjectName;

	public String baseListControllerClassName;
	public String baseDetailControllerClassName;
	public String listControllerClassName;
	public String listControllerObjectName;
	public String detailControllerClassName;
	public String detailControllerObjectName;
	
	public String detailViewClassName;
	public String listViewClassName;
	public String detailViewObjectName;
	public String listViewObjectName;
	
	/**
	 * represents the number of fields that are needed to
	 * build the business key (and its associated unique constraint)
	 */
	public int cardinality;
	public String listRendering;
	public String detailRendering;
	public boolean createEnabled;
	public boolean updateEnabled;
	public boolean deleteEnabled;
	public DetailMode detailMode;
		
	public List<String> interfaces;
	public List<String> annotations;

	public List<Property> properties = new ArrayList<>();
	public List<OneToMany> oneToManyList = new ArrayList<>();
	public List<OneToManyComponent> oneToManyComponentList = new ArrayList<>();
	public List<OneToOne> oneToOneList = new ArrayList<>();
	public List<OneToOneComponent> oneToOneComponentList = new ArrayList<>();
	
	public boolean selectable = false;
	public SelectionBehavior selectionBehavior;

	public boolean isComponent = false;
	public boolean isEmbedded = false;
	public boolean isOneToOneComponent = false;
	public Bean parentBean = null;
	
	public List<ViewProperty> referenceViewProperties = new ArrayList<>();
	public BasicViewBean basicViewBean;
	public FullViewBean fullViewBean;
	public FormBean formBean;
	
	
	public Property findPropertyByColumnName(String columnName) {
		for (Property property:properties) {
			if (property.column.originalName.equals(columnName)) {
				return property;
			}
		}
		throw new PropertyNotFoundException("Property : " + columnName + " not found");
	}
}
