package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * A skeleton.xml file is a database like representation of meta data<br/>
 * Relations are therefore indicated in a reverse way i.e from the point of view of a child from its parent excepted for unique component which is the classical way to represent a hibernate one-to-one component<br/>
 * In the current release, the following relations are supported :
 * <li>MANY_TO_ONE : the referenced bean will have a bidirectional one-to-many collection
 * <li>MANY_TO_ONE_COMPONENT : the referenced bean will have a unidirectional (not really for hibernate envers) one-to-many collection<br/>
 * the collection will be managed by the referenced bean
 * <li>EMBEDDED : a one-to-one relation where the referenced bean is managed by the current bean
 * <li>ONE_TO_ONE : a bi-directional one-to-one relation
 * <li>ONE_TO_ONE_COMPONENT : a unidirectional one-to-one relation (not really for hibernate envers)
 * <li>PROPERTY : default behavior of a referenced bean
 * 
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum RelationType {
	MANY_TO_ONE,
    MANY_TO_ONE_COMPONENT,
    EMBEDDED,
    ONE_TO_ONE,
    ONE_TO_ONE_COMPONENT,
    PROPERTY;
	
	
	public boolean isUnique() {
        switch (this) {
            case ONE_TO_ONE:
                return true;
                
            case ONE_TO_ONE_COMPONENT:
                return true;

            default:
                return false;
        }
    }
	
	public boolean isEmbedded() {
        switch (this) {
            case EMBEDDED:
                return true;

            default:
                return false;
        }
    }

    public boolean isComponentLink() {
        switch (this) {
            case ONE_TO_ONE_COMPONENT:
                return true;

            case MANY_TO_ONE_COMPONENT:
                return true;

            default:
                return false;
        }
    }
}
