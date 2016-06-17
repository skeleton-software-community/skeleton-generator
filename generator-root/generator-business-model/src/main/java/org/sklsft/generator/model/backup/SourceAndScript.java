package org.sklsft.generator.model.backup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * simple representation of a datasource reference and a script to execute on this datasource<br/>
 * This class will be used to fetch some information on backup xml files used to populate a database
 * @author Nicolas Thibault
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="backup")
@XmlType(name="backup")
public class SourceAndScript {
	
	/*
	 * properties
	 */
	@XmlElement(required=true)
	private String source;
	@XmlElement(required=true)
	private String script;
	
	
	/*
	 * getters and setters
	 */
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
}
