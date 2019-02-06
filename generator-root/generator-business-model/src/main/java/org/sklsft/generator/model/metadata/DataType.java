package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

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
	TEXT("String", false, false, IdGeneratorType.NONE),
	STRING("String", false, true, IdGeneratorType.UUID),
	SHORT("Short", true, true, IdGeneratorType.SEQUENCE),
	INTEGER("Integer", true, true, IdGeneratorType.SEQUENCE),
	LONG("Long", true, true, IdGeneratorType.SEQUENCE),
	DOUBLE("Double", true, false, IdGeneratorType.NONE),
	BIG_DECIMAL("BigDecimal", true, false, IdGeneratorType.NONE),
	DATE("Date", true, false, IdGeneratorType.NONE),
	DATETIME("Date", true, false, IdGeneratorType.NONE),
	BOOLEAN("Boolean", false, false, IdGeneratorType.NONE);	
	

	private DataType(String javaType, boolean limitable, boolean idElligible, IdGeneratorType defaultGenerator) {
		this.javaType = javaType;
		this.limitable = limitable;
		this.idElligible = idElligible;
		this.defaultGenerator = defaultGenerator;
	}
	
	
	private String javaType;
	private boolean limitable;
	private boolean idElligible;
	private IdGeneratorType defaultGenerator;


	public String getJavaType() {
		return javaType;
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
