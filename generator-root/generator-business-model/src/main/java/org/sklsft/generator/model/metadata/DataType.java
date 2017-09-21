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
	TEXT("String", false),
	STRING("String", false),
	LONG("Long", true),
	DOUBLE("Double", true),
	DATETIME("Date", true),
	BOOLEAN("Boolean", false);	
	

	private DataType(String javaType, boolean limitable) {
		this.javaType = javaType;
		this.limitable = limitable;		
	}
	
	
	private String javaType;
	private boolean limitable;
	


	public String getJavaType() {
		return javaType;
	}
	
	public boolean isLimitable() {
		return limitable;
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
				return "TIMESTAMP WITH TIME ZONE";
	
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
}
