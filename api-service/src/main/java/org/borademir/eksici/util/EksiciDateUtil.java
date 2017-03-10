package org.borademir.eksici.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author bora.demir
 */
public class EksiciDateUtil {
	
	private static final SimpleDateFormat DATE_FORMAT_DD_MM_YYYY_HH_MM = new SimpleDateFormat("dd.MM.yyyy hh:mm");
	
	public static Date formateEntryDate(String pEntryDate) throws ParseException{
		return DATE_FORMAT_DD_MM_YYYY_HH_MM.parse(pEntryDate);
	}
	
	public static String parseEntryDate(Date pEntryDate) throws ParseException{
		return DATE_FORMAT_DD_MM_YYYY_HH_MM.format(pEntryDate);
	}

}
