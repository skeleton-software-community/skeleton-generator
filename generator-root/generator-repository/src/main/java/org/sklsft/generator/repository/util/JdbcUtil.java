package org.sklsft.generator.repository.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;

import org.sklsft.generator.exception.DateFormatException;
import org.sklsft.generator.model.metadata.DataType;
import org.springframework.jdbc.core.JdbcTemplate;

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
				Date date = null;
				switch (value) {
					case "now" : {
						date = new Date(Calendar.getInstance().getTime().getTime());
						break;
					}
					case "tomorrow" : {
						Calendar c = Calendar.getInstance();
						c.add(Calendar.DATE, 1);
						date = new Date(c.getTime().getTime());
						break;
					}
					default : {
						try {
							date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(value).getTime());
						} catch (ParseException e) {
							throw new DateFormatException("Invalid string representation of a date", e);
						}
					}
				}
				return date;

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
