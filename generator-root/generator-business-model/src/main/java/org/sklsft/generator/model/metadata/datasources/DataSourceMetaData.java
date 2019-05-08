package org.sklsft.generator.model.metadata.datasources;

public class DataSourceMetaData {

	/*
	 * properties
	 */
	private String databaseName;
	private String host;
	private String port;
	private String user;
	private String password;
	
	
	/*
	 * getters and setters
	 */
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
