package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * The format will be used for UI generated files to define how to display data
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum Format {
	DEFAULT,
	TWO_DECIMALS,
	FOUR_DECIMALS,
	DATE,
	DATE_AND_TIME;
	
}
