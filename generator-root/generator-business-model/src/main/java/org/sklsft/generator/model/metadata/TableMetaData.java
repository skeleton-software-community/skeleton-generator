package org.sklsft.generator.model.metadata;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="table")
public class TableMetaData {

	/*
	 * properties
	 */
	@XmlAttribute(required=true)
	private String name;
	@XmlAttribute(required=true)
	private int cardinality;
	@XmlAttribute
	private String listRendering;
	@XmlAttribute
	private String detailRendering;
	@XmlAttribute
	private Boolean comboxable = false;
	@XmlAttribute
	private Boolean createEnabled = true;
	@XmlAttribute
	private Boolean updateEnabled = true;
	@XmlAttribute
	private Boolean deleteEnabled = true;
	@XmlElement
	private DetailMode detailMode;
	
	@XmlElementWrapper(name="interfaces")
	@XmlElement(name="interface")
	private List<String> interfaces;
	
	@XmlElementWrapper(name="annotations")
	@XmlElement(name="annotation")
	private List<String> annotations;
	
	@XmlElementWrapper(name="columns")
	@XmlElement(name="column")
	private List<ColumnMetaData> columns;
	
	
	/*
	 * getters and setters
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public int getCardinality() {
		return cardinality;
	}
	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}
	public String getListRendering() {
		return listRendering;
	}
	public void setListRendering(String listRendering) {
		this.listRendering = listRendering;
	}
	public String getDetailRendering() {
		return detailRendering;
	}
	public void setDetailRendering(String detailRendering) {
		this.detailRendering = detailRendering;
	}
	public Boolean getComboxable() {
		return comboxable;
	}
	public void setComboxable(Boolean comboxable) {
		this.comboxable = comboxable;
	}
	public Boolean getCreateEnabled() {
		return createEnabled;
	}
	public void setCreateEnabled(Boolean createEnabled) {
		this.createEnabled = createEnabled;
	}
	public Boolean getUpdateEnabled() {
		return updateEnabled;
	}
	public void setUpdateEnabled(Boolean updateEnabled) {
		this.updateEnabled = updateEnabled;
	}
	public Boolean getDeleteEnabled() {
		return deleteEnabled;
	}
	public void setDeleteEnabled(Boolean deleteEnabled) {
		this.deleteEnabled = deleteEnabled;
	}
	public DetailMode getDetailMode() {
		return detailMode;
	}
	public void setDetailMode(DetailMode detailMode) {
		this.detailMode = detailMode;
	}
	public List<String> getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}
	public List<String> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}
	public List<ColumnMetaData> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnMetaData> columns) {
		this.columns = columns;
	}
}
