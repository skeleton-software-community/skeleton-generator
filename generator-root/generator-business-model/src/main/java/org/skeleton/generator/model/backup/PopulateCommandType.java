package org.skeleton.generator.model.backup;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum(String.class)
public enum PopulateCommandType {
	INSERT,
	UPDATE;
}
