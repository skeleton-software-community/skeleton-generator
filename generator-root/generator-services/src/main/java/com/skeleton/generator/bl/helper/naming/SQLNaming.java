package com.skeleton.generator.bl.helper.naming;

import com.skeleton.generator.model.enumeration.DatabaseEngine;
import com.skeleton.generator.model.om.Table;

public class SQLNaming {
	
	private static final String separator = "_";

	public static String rename(String name, DatabaseEngine databaseEngine)
    {
        if (databaseEngine.equals(DatabaseEngine.ORACLE) && name.length() > 20)
        {
            
            String[] elements = name.toLowerCase().split(separator);
            String result = "";
            boolean start = true;
            for (String element : elements)
            {
                String firstCar = element.substring(0, 1);
                String lastCar = element.substring(element.length() - 1,element.length());
                String coreBody = element.substring(1, element.length() - 1);
                coreBody = coreBody.replace("a", "").replace("e", "").replace("i", "").replace("o", "").replace("u", "").replace("y", "");
                if (start == false)
                {
                    result += separator;
                }
                else
                {
                    start = false;
                }
                result += firstCar + coreBody + lastCar;
            }

            return result.toUpperCase();
        }
        else
        {
            return name;
        }
    }
	
	public static String getInsertProcedureName(Table table) {
		if (table.myPackage.model.project.databaseEngine.equals(DatabaseEngine.ORACLE)) {
			return "ins_" + table.name.toLowerCase() + "_bc";
		} else {
			return "ins_" + table.name.toLowerCase() + "_by_code";
		}
	}
	
}
