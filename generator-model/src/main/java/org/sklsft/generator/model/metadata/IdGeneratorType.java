package org.sklsft.generator.model.metadata;

import jakarta.xml.bind.annotation.XmlEnum;

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
