package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * For the current release, the only databases that are supported are :
 * <li>Postgresql
 * <li>Oracle
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum DatabaseEngine {
    POSTGRESQL,
    ORACLE;
}
