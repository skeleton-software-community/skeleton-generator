package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Different possibilities to filter on a string attribute
 * <li>STARTS_WITH
 * <li>CONTAINS
 * <li>CONTAINS_IGNORE_ACCENTS
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum TextFilterType {
	STARTS_WITH,
	CONTAINS,
	CONTAINS_IGNORE_ACCENTS;
}
