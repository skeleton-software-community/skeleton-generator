package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Different possibilities to select an object an object
 * <li>DROPDOWN_OPTIONS : selection through a combo box with dropdown options
 * <li>AUTO_COMPLETE : selection with auto completion
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum SelectionMode {
    DROPDOWN_OPTIONS,
    AUTO_COMPLETE;
}
