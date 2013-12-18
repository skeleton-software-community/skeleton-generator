package org.skeleton.generator.model.metadata;

public class ColumnMetaData {

	/*
	 * properties
	 */
	private String name;
	private String dataType;
	private boolean nullable;
	private String referenceTableName;
	private String referenceTableRelation;
	private String format;
	private boolean editable;
	private String visibility;
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
