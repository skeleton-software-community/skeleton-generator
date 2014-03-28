package org.skeleton.generator.util.metadata;

import java.util.HashMap;
import java.util.Map;

/**
 * For the current release, the data types that are supported are :
 * <li>TEXT
 * <li>STRING (varchar(255))
 * <li>LONG
 * <li>DOUBLE
 * <li>DATETIME (without timezone)
 * <li>BOOLEAN
 * @author Nicolas Thibault
 *
 */
public enum DataType {
	TEXT("TEXT"),
	STRING("STRING"),
	LONG("LONG"),
	DOUBLE("DOUBLE"),
	DATETIME("DATETIME"),
	BOOLEAN("BOOLEAN");

	private static final Map<String, DataType> reverseMap = new HashMap<String, DataType>();
	static {
		for (DataType dataType : values()) {
			reverseMap.put(dataType.getValue(), dataType);
		}
	}

	private String value;

	private DataType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static DataType byValue(String value) {
		DataType dataType = reverseMap.get(value);
		if (dataType == null) {
			throw new IllegalArgumentException("No DataType corresponding to value " + value);
		}
		return dataType;
	}

	public static String getJavaType(DataType dataType) {
		switch (dataType) {
		case TEXT:
			return "String";

		case STRING:
			return "String";

		case LONG:
			return "Long";

		case DOUBLE:
			return "Double";

		case DATETIME:
			return "Date";

		case BOOLEAN:
			return "Boolean";

		default:
			throw new IllegalArgumentException("Unhandled data type " + dataType.getValue());
		}
	}

	
	public static String getPostgresqlType(DataType dataType) {
		switch (dataType) {
		case TEXT:
			return "TEXT";

		case STRING:
			return "VARCHAR(255)";

		case LONG:
			return "BIGINT";

		case DOUBLE:
			return "DOUBLE PRECISION";

		case DATETIME:
			return "TIMESTAMP WITHOUT TIME ZONE";

		case BOOLEAN:
			return "BOOLEAN";

		default:
			throw new IllegalArgumentException("Unhandled data type " + dataType.getValue());
		}
	}

	public static String getOracleType(DataType dataType) {
		switch (dataType) {
		case TEXT:
			return "CLOB";

		case STRING:
			return "VARCHAR2(255)";

		case LONG:
			return "NUMBER(19,0)";

		case DOUBLE:
			return "FLOAT(24)";

		case DATETIME:
			return "DATE";

		case BOOLEAN:
			return "NUMBER(1,0)";

		default:
			throw new IllegalArgumentException("Unhandled data type " + dataType.getValue());
		}
	}

	public static String getPlOracleType(DataType dataType) {
		switch (dataType) {
		case TEXT:
			return "CLOB";

		case STRING:
			return "VARCHAR2";

		case LONG:
			return "NUMBER";

		case DOUBLE:
			return "FLOAT";

		case DATETIME:
			return "DATE";

		case BOOLEAN:
			return "NUMBER";

		default:
			throw new IllegalArgumentException("Unhandled data type " + dataType.getValue());
		}
	}
	
	public static String stringToBuildArg(String value, DataType dataType)
    {
        switch (dataType)
        {
            case DATETIME:
                return "new SimpleDateFormat(" + (char)34 + "yyyy-MM-dd" + (char)34 + ").parse(" + value + ")";

            case DOUBLE:
                return "Double.valueOf(" + value + ")";

            case LONG:
                return "Long.valueOf(" + value + ")";

            case BOOLEAN:
                return "Boolean.valueOf(" + value + ")";

            default:
                return value;
        }
    }
}
