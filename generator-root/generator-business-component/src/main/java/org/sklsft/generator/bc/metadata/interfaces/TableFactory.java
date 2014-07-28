package org.sklsft.generator.bc.metadata.interfaces;

import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Table;

public interface TableFactory {
	
	Table scanTable(TableMetaData tableMetaData, Package myPackage);
	
	Table fillTable(TableMetaData tableMetaData, Package myPackage);
}
