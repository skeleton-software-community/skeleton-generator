package org.skeleton.generator.util.jdbc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.skeleton.generator.util.exception.DateFormatException;
import org.skeleton.generator.util.metadata.DataType;

/**
 * A simple utility to convert a string to an object, given a {@link DataType}<br/>
 * Usefull to read backup csv files then to inject data in your project database using the spring {@link JdbcTemplate}
 * @author Nicolas Thibault
 *
 */
public class JdbcUtil {

	public static Object getObjectFromString(DataType dataType, String value) {
		
		if (value.equals("")) {
			return null;
		}

		switch (dataType)
        {
            case DATETIME:
			try {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
				return date;
			} catch (ParseException e) {
				throw new DateFormatException("Invalid string representation of a date", e);
			}

            case DOUBLE:
                return Double.valueOf(value);

            case LONG:
                return Long.valueOf(value);

            case BOOLEAN:
                return Boolean.valueOf(value);
                

            default:
                return value;
        }
	}
}
