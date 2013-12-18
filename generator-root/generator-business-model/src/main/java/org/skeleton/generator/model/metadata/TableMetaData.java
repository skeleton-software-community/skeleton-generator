package org.skeleton.generator.model.metadata;

import java.util.List;

public class TableMetaData {

	/*
	 * properties
	 */
	private String name;
	private int cardinality;
	private String listRendering;
	private String detailRendering;
	private boolean comboxable;
	private boolean createEnabled;
	private boolean updateEnabled;
	private boolean deleteEnabled;
	private String interfaceList;
	private String annotationList;
	
	private List<ColumnMetaData> columnMetaDataList;
	
	
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
	public String getInterfaceList() {
		return interfaceList;
	}
	public void setInterfaceList(String interfaceList) {
		this.interfaceList = interfaceList;
	}
	public List<ColumnMetaData> getColumnMetaDataList() {
		return columnMetaDataList;
	}
	public void setColumnMetaDataList(List<ColumnMetaData> columnMetaDataList) {
		this.columnMetaDataList = columnMetaDataList;
	}
	public String getAnnotationList() {
		return annotationList;
	}
	public void setAnnotationList(String annotationList) {
		this.annotationList = annotationList;
	}
	@Override
	public String toString() {
		return "TableMetaData [name=" + name + ", cardinality=" + cardinality
				+ ", listRendering=" + listRendering + ", detailRendering="
				+ detailRendering + ", comboxable=" + comboxable
				+ ", createEnabled=" + createEnabled + ", updateEnabled="
				+ updateEnabled + ", deleteEnabled=" + deleteEnabled
				+ ", interfaceList=" + interfaceList + ", annotationList="
				+ annotationList + ", columnMetaDataList=" + columnMetaDataList
				+ "]";
	}
}
