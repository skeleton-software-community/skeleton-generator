package org.sklsft.generator.model.domain;

import java.util.List;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.database.Table;


/**
 * representation of a package as a group of entities<br/>
 * the java entities will be organized through this classification as well as their corresponding components<br/>
 * Properties are willingly public because of their intensive use in file write commands<br/>
 * Nicolas Thibault
 *
 */
public class Package {

	public Model model;
	public String name;
	public List<Table> tables;
	public List<Bean> beans;

	public String omPackageName;

	public String filtersPackageName;
	public String sortingsPackageName;
	public String basicViewsPackageName;
	public String fullViewsPackageName;
	public String formsPackageName;

	public String baseDAOInterfacePackageName;
	public String baseDAOImplPackageName;
	public String DAOInterfacePackageName;
	public String DAOImplPackageName;

	public String baseServiceInterfacePackageName;
	public String baseServiceImplPackageName;
	public String serviceInterfacePackageName;
	public String serviceImplPackageName;

	public String baseFormMapperPackageName;
	public String formMapperPackageName;
	public String fullViewMapperPackageName;
	public String baseBasicViewMapperPackageName;
	public String basicViewMapperPackageName;
	public String baseFullViewMapperPackageName;

	public String baseStateManagerImplPackageName;
	public String stateManagerImplPackageName;
	
	public String baseRightsManagerImplPackageName;
	public String rightsManagerImplPackageName;

	public String baseProcessorImplPackageName;
	public String processorImplPackageName;

	public String facadeImplPackageName;
	public String facadeInterfacesPackageName;

	public String baseListControllerPackageName;
	public String listControllerPackageName;	
	public String baseDetailControllerPackageName;
	public String detailControllerPackageName;
	
	public String listViewPackageName;
	public String detailViewPackageName;

	public String commandPackageName;
}
