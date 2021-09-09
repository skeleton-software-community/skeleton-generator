package org.sklsft.generator.model.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="accessRoles")
public class AccessRolesMetaData {

	/*
	 * properties
	 */
	@XmlAttribute
    private String readRole;
	@XmlAttribute
	private String createRole;
	@XmlAttribute
	private String saveRole;
	@XmlAttribute
	private String updateRole;
	@XmlAttribute
	private String deleteRole;
	
	
	/*
	 * getters and setters
	 */
	public String getReadRole() {
		return readRole;
	}
	public void setReadRole(String readRole) {
		this.readRole = readRole;
	}
	public String getCreateRole() {
		return createRole;
	}
	public void setCreateRole(String createRole) {
		this.createRole = createRole;
	}
	public String getSaveRole() {
		return saveRole;
	}
	public void setSaveRole(String saveRole) {
		this.saveRole = saveRole;
	}
	public String getUpdateRole() {
		return updateRole;
	}
	public void setUpdateRole(String updateRole) {
		this.updateRole = updateRole;
	}
	public String getDeleteRole() {
		return deleteRole;
	}
	public void setDeleteRole(String deleteRole) {
		this.deleteRole = deleteRole;
	}
}
