package com.skeleton.generator.bl.factory.model.interfaces;

import com.skeleton.generator.model.metadata.TableMetaData;
import com.skeleton.generator.model.om.Package;
import com.skeleton.generator.model.om.Table;

public interface TableFactory {
	
	Table buildTable (TableMetaData tableMetaData, Package myPackage);
}
