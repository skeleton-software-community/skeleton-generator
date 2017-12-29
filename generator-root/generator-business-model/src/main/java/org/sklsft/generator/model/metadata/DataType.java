package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * For the current release, the data types that are supported are :
 * <li>TEXT
 * <li>STRING (varchar(255))
 * <li>LONG
 * <li>DOUBLE
 * <li>BIG_DECIMAL
 * <li>DATETIME
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
	BIG_DECIMAL("BigDecimal", true),
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
}
