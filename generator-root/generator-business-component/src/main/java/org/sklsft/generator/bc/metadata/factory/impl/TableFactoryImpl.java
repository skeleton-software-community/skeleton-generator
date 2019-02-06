package org.sklsft.generator.bc.metadata.factory.impl;

import java.util.ArrayList;

import org.sklsft.generator.bc.metadata.factory.interfaces.TableFactory;
import org.sklsft.generator.bc.resolvers.DatabaseHandlerResolver;
import org.sklsft.generator.model.domain.Model;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.database.Column;
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
		table.name = DatabaseHandlerResolver.getDatabaseHandler(myPackage.model.project).rename(table.originalName);
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
		
		logger.trace("Table found : " + tableMetaData.getName());
        
        return table;
	}

	@Override
	public Table fillTable(TableMetaData tableMetaData, Model model) {
		Table table = model.findTable(tableMetaData.getName());
		
        for (ColumnMetaData columnMetaData : tableMetaData.getColumns()) {
            Column column = new Column();
            column.originalName = columnMetaData.getName();
            column.name = DatabaseHandlerResolver.getDatabaseHandler(model.project).rename(column.originalName);
            if (columnMetaData.getDataType() != null) {
            	column.dataType = columnMetaData.getDataType();
            }            
            column.nullable = (columnMetaData.getNullable());
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
            column.editable = columnMetaData.getEditable();
            if (columnMetaData.getVisibility()!=null) {
            	column.visibility = columnMetaData.getVisibility();
            } else {
            	column.visibility = Visibility.VISIBLE;
            }
            column.rendering = columnMetaData.getRendering();
            column.annotations = columnMetaData.getAnnotations();
            table.columns.add(column);
        }
        
        if (tableMetaData.getUniqueConstraints()!=null && !tableMetaData.getUniqueConstraints().isEmpty()) {
        	table.uniqueConstraints = new ArrayList<>();
        	for (UniqueConstraintMetaData uniqueConstraintMetaData:tableMetaData.getUniqueConstraints()) {
        		UniqueConstraint uniqueConstraint = new UniqueConstraint();
        		uniqueConstraint.name = uniqueConstraintMetaData.getName();
        		for (String columnName:uniqueConstraintMetaData.getFields()) {
        			uniqueConstraint.columns.add(table.findColumnByName(columnName));
        		}
        		table.uniqueConstraints.add(uniqueConstraint);
        	}
        }
        return table;
	}	
}
