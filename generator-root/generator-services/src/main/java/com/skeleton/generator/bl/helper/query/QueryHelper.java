package com.skeleton.generator.bl.helper.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.skeleton.generator.exception.DateFormatException;
import com.skeleton.generator.model.enumeration.DataType;
import com.skeleton.generator.model.om.Column;
import com.skeleton.generator.model.om.Table;


/**
 * helper to provide methods used to build jdbc commands
 * @author Nicolas Thibault
 *
 */
public class QueryHelper {

	/**
	 * static method used to build object args for using a spring SimpleJdbcCall
	 * @param table
	 * @param args
	 * @return
	 */
	public static Object[] getInsertAgrs(Table table, String[] args) {
		
		List<Object> result = new ArrayList<Object>();
		List<DataType> dataTypeList = getInsertDataTypes(table);
		for (int i=0;i<dataTypeList.size();i++) {
			result.add(getStringToObject(dataTypeList.get(i), args[i]));
		}
		return result.toArray();
	}

	private static List<DataType> getInsertDataTypes(Table table) {
		
		List<DataType> dataTypeList = new ArrayList<DataType>();
		for (int i = 1; i<table.columnList.size(); i++) {
			Column column = table.columnList.get(i);
			if (column.referenceTable == null) {
				dataTypeList.add(column.dataType);
			} else {
				dataTypeList.addAll(getFindDataTypes(column.referenceTable));
			}
		}
		return dataTypeList;
	}
	
	private static List<DataType> getFindDataTypes(Table table) {
		
		List<DataType> dataTypeList = new ArrayList<DataType>();
		for (int i=1;i<=table.cardinality;i++) {
			Column column = table.columnList.get(i);
			if (column.referenceTable == null) {
				dataTypeList.add(column.dataType);
			} else {
				dataTypeList.addAll(getFindDataTypes(column.referenceTable));
			}
		}
		return dataTypeList;
	}
	
	private static Object getStringToObject(DataType dataType, String value) {
		
		if (value.equals("")) {
			return null;
		}

		switch (dataType)
        {
            case DATETIME:
			try {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
				return date;
			} catch (ParseException e) {
				throw new DateFormatException("Invalid string representation of a date", e);
			}

            case DOUBLE:
                return Double.valueOf(value);

            case LONG:
                return Long.valueOf(value);

            case BOOLEAN:
                return Boolean.valueOf(value);
                

            default:
                return value;
        }
	}
	
	public static int getInsertArgsNumber(Table table) {
		return getInsertDataTypes(table).size();
	}
}
