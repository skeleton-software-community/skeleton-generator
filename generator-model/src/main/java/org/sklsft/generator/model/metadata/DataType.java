package org.sklsft.generator.model.metadata;

import jakarta.xml.bind.annotation.XmlEnum;

/**
 * For the current release, the data types that are supported are :
 * <li>TEXT
 * <li>STRING (varchar(255))
 * <li>INT
 * <li>LONG
 * <li>DOUBLE
 * <li>BIG_DECIMAL
 * <li>DATE
 * <li>DATETIME
 * <li>BOOLEAN
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum DataType {
	TEXT("String", "string", false, false, IdGeneratorType.NONE),
	STRING("String", "string", false, true, IdGeneratorType.UUID),
	SHORT("Short", "number", true, true, IdGeneratorType.SEQUENCE),
	INTEGER("Integer", "number", true, true, IdGeneratorType.SEQUENCE),
	LONG("Long", "number", true, true, IdGeneratorType.SEQUENCE),
	DOUBLE("Double", "number", true, false, IdGeneratorType.NONE),
	BIG_DECIMAL("BigDecimal", "number", true, false, IdGeneratorType.NONE),
	DATE("LocalDate", "string", true, false, IdGeneratorType.NONE),
	DATETIME("Date", "Date", true, false, IdGeneratorType.NONE),
	BOOLEAN("Boolean", "boolean", false, false, IdGeneratorType.NONE);	
	

	
	
	
	private DataType(String javaType, String tsType, boolean limitable, boolean idElligible, IdGeneratorType defaultGenerator) {
		this.javaType = javaType;
		this.tsType = tsType;
		this.limitable = limitable;
		this.idElligible = idElligible;
		this.defaultGenerator = defaultGenerator;
	}

	private String javaType;
	private String tsType;
	private boolean limitable;
	private boolean idElligible;
	private IdGeneratorType defaultGenerator;


	public String getJavaType() {
		return javaType;
	}
	
	public String getTsType() {
		return tsType;
	}

	public boolean isLimitable() {
		return limitable;
	}

	public boolean isIdElligible() {
		return idElligible;
	}

	public IdGeneratorType getDefaultGenerator() {
		return defaultGenerator;
	}
}
