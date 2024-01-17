package org.sklsft.generator.model.metadata;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

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
	
	@XmlElementWrapper(name="packages")
	@XmlElement(name="package")
	private List<PackageMetaData> packages;

	
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
	public List<PackageMetaData> getPackages() {
		return packages;
	}
	public void setPackages(List<PackageMetaData> packages) {
		this.packages = packages;
	}
}
