package org.skeleton.generator.bc.factory.model.interfaces;

import org.skeleton.generator.model.metadata.TableMetaData;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Table;

public interface TableFactory {
	
	Table scanTable(TableMetaData tableMetaData, Package myPackage);
	
	Table fillTable(TableMetaData tableMetaData, Package myPackage);
}
