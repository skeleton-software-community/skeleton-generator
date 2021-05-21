package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * 
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum IdGeneratorType {
	NONE,
	SEQUENCE,
	UUID;
}
