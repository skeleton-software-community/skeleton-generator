package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="selectionBehavior")
public class SelectionBehaviorMetaData {

	/*
	 * properties
	 */
	@XmlAttribute(required=true)
    private SelectionMode selectionMode;
	@XmlAttribute
	private String labelColumn;
	
	
	/*
	 * getters and setters
	 */
	public SelectionMode getSelectionMode() {
		return selectionMode;
	}
	public void setSelectionMode(SelectionMode selectionMode) {
		this.selectionMode = selectionMode;
	}
	public String getLabelColumn() {
		return labelColumn;
	}
	public void setLabelColumn(String labelColumn) {
		this.labelColumn = labelColumn;
	}
}
