package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Different possibilities to display the detail of an object
 * <li>MODAL : displayed in a popup
 * <li>PAGE : displayed in another page
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum DetailMode {
    MODAL,
    PAGE;
}
