package org.sklsft.generator.model.util.naming;

import java.io.StringWriter;

public class JavaClassNaming {
	
	private static final String DATABASE_NAMING_SEPARATOR = "_";
	private static final String URL_NAMING_SEPARATOR = "-";
	
	
	/**
	 * converts a table name to the corresponding url piece<br/>
	 * Conventions are of type : abc-def
	 */
	public static String getUrlPiece(String tableName) {
        return tableName.toLowerCase().replace(DATABASE_NAMING_SEPARATOR, URL_NAMING_SEPARATOR);
    }
	
	/**
	 * converts a table name to the corresponding class name<br/>
	 * java conventions are used
	 */
	public static String toClassName(String arg) {
        String[] elements = arg.toLowerCase().split(DATABASE_NAMING_SEPARATOR);
        String result = "";
        for (int i = 0; i < elements.length; i++) {
            elements[i] = elements[i].substring(0, 1).toUpperCase() + elements[i].substring(1, elements[i].length());
            result = result + elements[i];
        }

        return result;
    }
	
	
	/**
	 * converts a table name to the corresponding object name<br/>
	 * java conventions are used
	 */
    public static String toObjectName(String arg) {
        String[] elements = arg.toLowerCase().split(DATABASE_NAMING_SEPARATOR);
        String result = elements[0];
        for (int i = 1; i < elements.length; i++) {
            elements[i] = elements[i].substring(0, 1).toUpperCase() + elements[i].substring(1, elements[i].length());
            result = result + elements[i];
        }

        return result;
    }
    
    
	public static String getClassNameFromObjectName(String arg) {
		return arg.substring(0, 1).toUpperCase() + arg.substring(1, arg.length());
    }
	
	public static String toDatabaseName(String arg) {
		if (arg == null) {
			return null;
		}
		if (arg.isEmpty()) {
			return "";
		}
		StringWriter writer = new StringWriter();
		boolean start = true;
		
		for (char x:arg.toCharArray()) {			
			if (start) {
				start = false;
			} else {
				if (Character.isUpperCase(x)) {
					writer.append("_");
				}				
			}
			writer.append(Character.toUpperCase(x));
		}
		
		return writer.toString();
	}
}
