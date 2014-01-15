package org.skeleton.generator.model.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="column")
public class ColumnMetaData {

	/*
	 * properties
	 */
	@XmlAttribute(required=true)
	private String name;
	@XmlAttribute(required=true)
	private String dataType;
	@XmlAttribute
	private boolean nullable = true;
	@XmlAttribute
	private String referenceTableName;
	@XmlAttribute
	private String referenceTableRelation;
	@XmlAttribute
	private String format;
	@XmlAttribute
	private boolean editable = true;
	@XmlAttribute
	private String visibility;
	@XmlAttribute
	private String rendering;
	
	/*
	 * getters and setters
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public String getReferenceTableName() {
		return referenceTableName;
	}
	public void setReferenceTableName(String referenceTableName) {
		this.referenceTableName = referenceTableName;
	}
	public String getReferenceTableRelation() {
		return referenceTableRelation;
	}
	public void setReferenceTableRelation(String referenceTableRelation) {
		this.referenceTableRelation = referenceTableRelation;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getRendering() {
		return rendering;
	}
	public void setRendering(String rendering) {
		this.rendering = rendering;
	}
	@Override
	public String toString() {
		return "ColumnMetaData [name=" + name + ", dataType=" + dataType
				+ ", nullable=" + nullable + ", referenceTableName="
				+ referenceTableName + ", referenceTableRelation="
				+ referenceTableRelation + ", format=" + format + ", editable="
				+ editable + ", visibility=" + visibility + ", rendering="
				+ rendering + "]";
	}	
}
