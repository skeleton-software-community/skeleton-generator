package org.sklsft.generator.util.naming;

public class JavaClassNaming {
	
	private static final String DATABASE_NAMING_SEPARATOR = "_";
	
	
	/**
	 * converts a table name to the corresponding class name<br/>
	 * java conventions are used
	 */
	public static String getClassName(String tableName) {
        String[] elements = tableName.toLowerCase().split(DATABASE_NAMING_SEPARATOR);
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
    public static String getObjectName(String tableName) {
        String[] elements = tableName.toLowerCase().split(DATABASE_NAMING_SEPARATOR);
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
}
