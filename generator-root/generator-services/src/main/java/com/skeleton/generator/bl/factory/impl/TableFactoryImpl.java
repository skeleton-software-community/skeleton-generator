package com.skeleton.generator.bl.factory.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.skeleton.generator.bl.factory.interfaces.TableFactory;
import com.skeleton.generator.bl.helper.naming.SQLNaming;
import com.skeleton.generator.model.enumeration.DataType;
import com.skeleton.generator.model.enumeration.Format;
import com.skeleton.generator.model.enumeration.RelationType;
import com.skeleton.generator.model.enumeration.Visibility;
import com.skeleton.generator.model.metadata.ColumnMetaData;
import com.skeleton.generator.model.metadata.TableMetaData;
import com.skeleton.generator.model.om.Column;
import com.skeleton.generator.model.om.Package;
import com.skeleton.generator.model.om.Table;

@Component
public class TableFactoryImpl implements TableFactory {

	public Table buildTable(TableMetaData tableMetaData, Package myPackage) {
		Table table = new Table();
		myPackage.tableList.add(table);
        table.myPackage = myPackage;
        table.originalName = tableMetaData.getName();
        table.name = SQLNaming.rename(table.originalName, myPackage.model.project.databaseEngine);
        table.cardinality = tableMetaData.getCardinality();
        table.columnList = new ArrayList<Column>();

        Column idColumn = new Column();
        idColumn.name = "ID";
        idColumn.originalName = "ID";
        idColumn.dataType = DataType.LONG;
        idColumn.relation = RelationType.PROPERTY;
        idColumn.nullable = false;
        idColumn.unique = true;
        table.columnList.add(idColumn);

        for (ColumnMetaData columnMetaData : tableMetaData.getColumnMetaDataList()) {
            Column column = new Column();
            column.originalName = columnMetaData.getName();
            column.name = SQLNaming.rename(column.originalName, table.myPackage.model.project.databaseEngine);
            column.dataType = DataType.byValue(columnMetaData.getDataType());
            column.nullable = (columnMetaData.isNullable());
            column.relation = RelationType.byValue(columnMetaData.getReferenceTableRelation());
            column.deleteCascade = (column.relation.equals(RelationType.MANY_TO_ONE_COMPONENT) || column.relation.equals(RelationType.ONE_TO_ONE_COMPONENT));
            column.referenceTable = myPackage.model.findTable(columnMetaData.getReferenceTableName());
            column.unique = (RelationType.isUnique(column.relation));
            column.format = Format.byValue(columnMetaData.getFormat());
            column.editable = columnMetaData.isEditable();
            Visibility visibility = Visibility.byValue(columnMetaData.getVisibility());
            column.listVisible = visibility.isListVisible();
            column.detailVisible = visibility.isDetailVisible();
            column.rendering = columnMetaData.getRendering();
            table.columnList.add(column);
        }

        return table;
    }
	
}
