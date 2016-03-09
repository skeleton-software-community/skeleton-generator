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
	@XmlAttribute
	private int cardinality;
	@XmlAttribute
	private String listRendering;
	@XmlAttribute
	private String detailRendering;
	@XmlAttribute
	private boolean comboxable;
	@XmlAttribute
	private boolean createEnabled = true;
	@XmlAttribute
	private boolean updateEnabled = true;
	@XmlAttribute
	private boolean deleteEnabled = true;
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
	public boolean isComboxable() {
		return comboxable;
	}
	public void setComboxable(boolean comboxable) {
		this.comboxable = comboxable;
	}
	public boolean isCreateEnabled() {
		return createEnabled;
	}
	public void setCreateEnabled(boolean createEnabled) {
		this.createEnabled = createEnabled;
	}
	public boolean isUpdateEnabled() {
		return updateEnabled;
	}
	public void setUpdateEnabled(boolean updateEnabled) {
		this.updateEnabled = updateEnabled;
	}
	public boolean isDeleteEnabled() {
		return deleteEnabled;
	}
	public void setDeleteEnabled(boolean deleteEnabled) {
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
	
	@Override
	public String toString() {
		return "TableMetaData [name=" + name + ", cardinality=" + cardinality
				+ ", listRendering=" + listRendering + ", detailRendering="
				+ detailRendering + ", comboxable=" + comboxable
				+ ", createEnabled=" + createEnabled + ", updateEnabled="
				+ updateEnabled + ", deleteEnabled=" + deleteEnabled
				+ ", interfaces=" + interfaces + ", annotations="
				+ annotations + ", columns=" + columns
				+ "]";
	}
}
