package com.skeleton.generator.model.om;

import java.util.List;


/**
 * representation of a bean<br/>
 * Properties are willingly public because of their intensive use in file write commands<br/>
 * @author Nicolas Thibault
 *
 */
public class Bean {

	public Table table;
    public Package myPackage;
    public String className;
    public String objectName;
    public String viewClassName;
    public String viewObjectName;

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
    public String baseStateManagerInterfaceName;
    public String stateManagerInterfaceName;
    public String stateManagerObjectName;

    public String baseMapperClassName;
    public String mapperClassName;
    public String baseMapperInterfaceName;
    public String mapperInterfaceName;
    public String mapperObjectName;

    public String baseControllerClassName;
    public String controllerClassName;
    public String controllerObjectName;

    public int cardinality;
    public String listRendering;
    public String detailRendering;
    public boolean hasComboBox;
    public boolean createEnabled;
    public boolean updateEnabled;
    public boolean deleteEnabled;
    public String interfaces;

    public List<Property> propertyList;
    public List<OneToMany> oneToManyList;
    public List<OneToManyComponent> oneToManyComponentList;
    public List<OneToOne> oneToOneList;
    public List<OneToOneComponent> oneToOneComponentList;
    public List<UniqueComponent> uniqueComponentList;

    public boolean isComponent = false;
    public boolean hasStatus = false;
}
