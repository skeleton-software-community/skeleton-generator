package org.sklsft.generator.skeletons.core.database;

import org.sklsft.generator.model.metadata.datasources.DataSourceMetaData;
import org.sklsft.generator.skeletons.core.layers.database.OracleDatabaseLayer;
import org.sklsft.generator.skeletons.database.DatabaseHandler;
import org.sklsft.generator.skeletons.layers.Layer;

public class OracleHandler implements DatabaseHandler {

	private static final int MAX_TABLE_LENGTH = 22;

	private static final String separator = "_";
	
	public static final String NAME = "ORACLE";
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getDriverClassName() {
		return "oracle.jdbc.driver.OracleDriver";
	}
	
	@Override
	public String getDialect() {
		return "org.hibernate.dialect.OracleDialect";
	}

	@Override
	public String getUrl(DataSourceMetaData datasource) {
		return "jdbc:oracle:thin:@" + datasource.getHost() + ":" + datasource.getPort() + ":" + datasource.getDatabaseName();
	}
	
	@Override
	public String rename(String name) {
        if (name.length() > MAX_TABLE_LENGTH) {            
            String[] elements = name.toLowerCase().split(separator);
            String result = "";
            boolean start = true;
            for (String element : elements) {
            	if (element.length()>2) {
	                String firstCar = element.substring(0, 1);
	                String lastCar = element.substring(element.length() - 1,element.length());
	                String coreBody = element.substring(1, element.length() - 1);
	                coreBody = coreBody.replace("a", "").replace("e", "").replace("i", "").replace("o", "").replace("u", "").replace("y", "");
	                if (start == false) {
	                    result += separator;
	                } else {
	                    start = false;
	                }
	                result += firstCar + coreBody + lastCar;
            	} else {
            		if (start == false) {
	                    result += separator;
	                } else {
	                    start = false;
	                }
	                result += element;
            	}
            }
            
            if (result.length() > MAX_TABLE_LENGTH) {
            	result = "";
                start = true;
            	for (String element : elements) {
                    if (start == false) {
                        result += separator;
                    }
                    else  {
                        start = false;
                    }
                    result += element.substring(0,Math.min(element.length(), 3));
                }
            }

            return result.toUpperCase();
        }
        else  {
            return name;
        }
    }
	
	@Override
	public Layer getLayer() {
		return new OracleDatabaseLayer();
	}

	
}
