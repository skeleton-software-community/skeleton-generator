package org.skeleton.generator.model.metadata;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class PackageMetaData {

	/*
	 * properties
	 */
	
	private String name;
	

	private List<TableMetaData> tableMetaDataList;

	
	/*
	 * getters and setters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<TableMetaData> getTableMetaDataList() {
		return tableMetaDataList;
	}	

	public void setTableMetaDataList(List<TableMetaData> tableMetaDataList) {
		this.tableMetaDataList = tableMetaDataList;
	}

	@Override
	public String toString() {
		return "PackageMetaData [name=" + name + ", tableMetaDataList="
				+ tableMetaDataList + "]";
	}
	
	
}
