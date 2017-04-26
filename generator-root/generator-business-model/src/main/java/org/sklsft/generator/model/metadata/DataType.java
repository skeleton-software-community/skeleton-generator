package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

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
@XmlEnum(String.class)
public enum DataType {
	TEXT,
	STRING,
	LONG,
	DOUBLE,
	DATETIME,
	BOOLEAN;

	public String getJavaType() {
		switch (this) {
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
				throw new IllegalArgumentException("Unhandled data type " + this);
		}
	}

	
	public String getPostgresqlType() {
		switch (this) {
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
				throw new IllegalArgumentException("Unhandled data type " + this);
		}
	}

	public String getOracleType() {
		switch (this) {
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
				throw new IllegalArgumentException("Unhandled data type " + this);
		}
	}

	public String getPlOracleType() {
		switch (this) {
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
				throw new IllegalArgumentException("Unhandled data type " + this);
		}
	}
	
	public String stringToBuildArg(String value)
    {
        switch (this) {
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
