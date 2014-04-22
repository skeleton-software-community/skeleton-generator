package org.skeleton.generator.model.metadata;

import javax.xml.bind.annotation.XmlEnum;

/**
 * A skeleton.xml file is a database like representation of meta data<br/>
 * Relations are therefore indicated in a reverse way i.e from the point of view of a child from its parent excepted for unique component which is the classical way to represent a hibernate one-to-one component<br/>
 * In the current release, the following relations are supported :
 * <li>many-to-one : the referenced bean will have a bidirectional one-to-many collection
 * <li>many-to-one : the referenced bean will have a unidirectional (not really if envers auditing is activated) one-to-many collection<br/>
 * the collection will be managed by the referenced bean
 * <li>simple unique property, not really a relation
 * <li>unique component : a one-to-one relation where the referenced bean is managed by the current bean
 * <li>one-to-one : a bi-directional one-to-one relation
 * <li>property : default behavior of a referenced bean
 * 
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum RelationType {
	MANY_TO_ONE,
    MANY_TO_ONE_COMPONENT,
    UNIQUE,
    UNIQUE_COMPONENT,
    ONE_TO_ONE,
    PROPERTY;
	
	
	public static Boolean isUnique(RelationType relationType)
    {
        switch (relationType)
        {
            case UNIQUE:
                return true;

            case ONE_TO_ONE:
                return true;

            default:
                return false;

        }
    }

    public static Boolean isComponentLink(RelationType relationType)
    {
        switch (relationType)
        {
            case UNIQUE_COMPONENT:
                return true;

            case MANY_TO_ONE_COMPONENT:
                return true;

            default:
                return false;

        }
    }
}
