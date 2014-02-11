package org.skeleton.generator.model.om;

import java.util.List;

import org.skeleton.generator.exception.BeanNotFoundException;
import org.skeleton.generator.exception.TableNotFoundException;


/**
 * Representation of a model associated to a project<br/>
 * Properties are willingly public because of their intensive use in file write commands<br/>
 * @author Nicolas Thibault
 *
 */
public class Model {

	public Project project;
	
	public List<Package> packages;

    public String serviceExceptionPackageName;
    public String daoExceptionPackageName;
    public String testExceptionPackageName;
    public String controllerPackageName;
    public String commandPackageName;

    
    /**
     * run through all the table until it is found
     * @param tableName
     * @return
     * @throws TableNotFoundException if no table with the corresponding name is found
     */
	public Table findTable(String tableName) {
		
		if (tableName == null) {
			return null;
		}
		if (tableName.isEmpty()) {
			return null;
		}
		for (Package myPackage : this.packages) {
			for (Table table : myPackage.tables) {
				if (table.originalName.equals(tableName)) {
					return table;
				}
			}
		}
		throw new TableNotFoundException("Invalid table reference : "
				+ tableName);
	}

	
	/**
	 * run through all the bean until it is found
	 * @param tableName
	 * @return
	 * @throws BeanNotFoundException if no bean which associated table has the corresponding name
	 */
	public Bean findBean(String tableName) {
		for (Package myPackage : this.packages) {
			for (Bean bean : myPackage.beans) {
				if (bean.table.originalName.equals(tableName)) {
					return bean;
				}
			}
		}
		throw new BeanNotFoundException("invalid table reference : " + tableName);
	}
}
