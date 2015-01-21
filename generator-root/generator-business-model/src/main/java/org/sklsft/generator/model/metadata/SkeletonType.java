package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * For the current release, the following skeletons are supported :
 * <li>SPRING_HIBERNATE_RICHFACES : RichFaces web application with spring and hibernate
 * <li>BASIC_SPRING_HIBERNATE_RICHFACES : A simple version of a RichFaces web application with spring and hibernate
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum SkeletonType {
    SPRING_HIBERNATE_RICHFACES;
}
