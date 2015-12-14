package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * For the current release, the following skeletons are supported :
 * <li>SPRING_HIBERNATE_RICHFACES_3 : RichFaces 3 web application with spring and hibernate
 * <li>SPRING_HIBERNATE_RICHFACES_4 : RichFaces 4 web application with spring and hibernate
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum SkeletonType {
    SPRING_HIBERNATE_RICHFACES_3,
    SPRING_HIBERNATE_RICHFACES_4;
}

