package org.sklsft.generator.bc.metadata.interfaces;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.TableMetaData;

public interface TableFactory {
	
	Table scanTable(TableMetaData tableMetaData, Package myPackage);
	
	Table fillTable(TableMetaData tableMetaData, Package myPackage);
}
