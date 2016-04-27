package org.sklsft.generator.model.metadata;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="package")
public class PackageMetaData {

	/*
	 * properties
	 */
	@XmlAttribute(required=true)
	private String name;
	
	@XmlElementWrapper(name="tables")
	@XmlElement(name="table")
	private List<TableMetaData> tables;

	
	/*
	 * getters and setters
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public List<TableMetaData> getTables() {
		return tables;
	}
	public void setTables(List<TableMetaData> tables) {
		this.tables = tables;
	}
}
