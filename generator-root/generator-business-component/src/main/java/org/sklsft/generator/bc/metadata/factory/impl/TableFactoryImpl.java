package org.sklsft.generator.bc.metadata.factory.impl;

import org.sklsft.generator.bc.metadata.factory.interfaces.TableFactory;
import org.sklsft.generator.bc.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.model.domain.Model;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.domain.database.Index;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.domain.database.UniqueConstraint;
import org.sklsft.generator.model.metadata.ColumnMetaData;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.metadata.UniqueConstraintMetaData;
import org.sklsft.generator.model.metadata.Visibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class TableFactoryImpl implements TableFactory {

	private static final Logger logger = LoggerFactory.getLogger(TableFactory.class);

	@Override
	public Table scanTable(TableMetaData tableMetaData, Package myPackage) {
		Table table = new Table();
		table.myPackage = myPackage;
		table.originalName = tableMetaData.getName();
		table.name = DatabaseHandlerDiscovery.getDatabaseHandler(myPackage.model.project).rename(table.originalName);
		if (tableMetaData.getIdType() != null) {
			table.idType = tableMetaData.getIdType();
		} else {
			table.idType = DataType.LONG;
		}
		if (table.idGeneratorType != null) {
			table.idGeneratorType = tableMetaData.getIdGeneratorType();
		} else {
			table.idGeneratorType = table.idType.getDefaultGenerator();
		}
		table.cardinality = tableMetaData.getCardinality();
		table.sequenceName = table.name + "_SEQ";
		logger.trace("Table found : " + tableMetaData.getName());
        
        return table;
	}

	@Override
	public Table fillTable(TableMetaData tableMetaData, Model model) {
		Table table = model.findTable(tableMetaData.getName());
		
        for (ColumnMetaData columnMetaData : tableMetaData.getColumns()) {
            Column column = new Column();
            column.originalName = columnMetaData.getName();
            column.name = DatabaseHandlerDiscovery.getDatabaseHandler(model.project).rename(column.originalName);
            if (columnMetaData.getDataType() != null) {
            	column.dataType = columnMetaData.getDataType();
            }            
            
            if (columnMetaData.getReferenceTableRelation() != null) {
            	column.relation = columnMetaData.getReferenceTableRelation();
            } else {
            	column.relation = RelationType.PROPERTY;
            }
            
            column.deleteCascade = (column.relation.equals(RelationType.MANY_TO_ONE_COMPONENT));
            column.referenceTable = model.findTable(columnMetaData.getReferenceTableName());
            
            if (column.referenceTable != null) {
            	column.dataType = column.referenceTable.idType;
            }
            
            column.unique = columnMetaData.getUnique() || column.relation.isUnique();
            column.nullable = columnMetaData.getNullable();
            if (column.relation.isNotNullable()) {
            	column.nullable = false;
            }
            column.editable = columnMetaData.getEditable();
            column.filterable = columnMetaData.getFilterable();
            if (columnMetaData.getVisibility()!=null) {
            	column.visibility = columnMetaData.getVisibility();
            } else {
            	column.visibility = Visibility.VISIBLE;
            }
            column.rendering = columnMetaData.getRendering();
            column.annotations = columnMetaData.getAnnotations();
            table.columns.add(column);
        }
        
        createConstraintsAndIndexes(tableMetaData, table);
        
        return table;
	}

	private void createConstraintsAndIndexes(TableMetaData tableMetaData, Table table) {
		// unique constraint corresponding to the business key
        if (table.cardinality > 0) {
        	UniqueConstraint uniqueConstraint = new UniqueConstraint();
        	Index index = new Index();
        	uniqueConstraint.index = index;
        	index.uniqueConstraint = uniqueConstraint;
    		uniqueConstraint.name = "UC_" + table.name;
    		index.name = "IDX_" + table.name + "_UC";
    		for (int i = 0; i < table.cardinality; i++) {
    			uniqueConstraint.columns.add(table.columns.get(i));
    			index.columns.add(table.columns.get(i));
    		}
    		table.uniqueConstraints.add(uniqueConstraint);
    		table.indexes.add(index);
        }
        // unique constraints on fields declared as unique or indexes on fk
        for (int i = 0;i<table.columns.size();i++) {
        	Column column = table.columns.get(i);
        	if (column.unique) {
        		UniqueConstraint uniqueConstraint = new UniqueConstraint();
        		Index index = new Index();
        		uniqueConstraint.index = index;
            	index.uniqueConstraint = uniqueConstraint;
            	
        		uniqueConstraint.name = "UC_" + table.name + "_C" + i;
        		uniqueConstraint.columns.add(column);        		
        		
        		index.name = "IDX_" + table.name + "_C" + i;
        		index.columns.add(column);
        		
        		table.uniqueConstraints.add(uniqueConstraint);
        		table.indexes.add(index);
        	} else {
        		if (column.referenceTable != null) {
        			Index index = new Index();
            		index.name = "IDX_" + table.name + "_C" + i;
            		index.columns.add(column);
            		table.indexes.add(index);
        		}
        	}
        }
        
        // declared unique constraints
        if (tableMetaData.getUniqueConstraints()!=null) {
        	for (UniqueConstraintMetaData uniqueConstraintMetaData:tableMetaData.getUniqueConstraints()) {
        		UniqueConstraint uniqueConstraint = new UniqueConstraint();       		
        		Index index = new Index();        		
        		uniqueConstraint.index = index;
            	index.uniqueConstraint = uniqueConstraint;
            	
            	index.name = "IDX_" + uniqueConstraintMetaData.getName();
            	uniqueConstraint.name = "UC_" + uniqueConstraintMetaData.getName();
        		
        		for (String columnName:uniqueConstraintMetaData.getFields()) {
        			Column column = table.findColumnByName(columnName);
        			uniqueConstraint.columns.add(column);
        			index.columns.add(column);
        		}
        		table.uniqueConstraints.add(uniqueConstraint);
        		table.indexes.add(index);
        	}
        }
	}
}
