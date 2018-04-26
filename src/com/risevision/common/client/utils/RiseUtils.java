package com.risevision.common.client.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;

public class RiseUtils {
	public final static String REGEX_EMAIL = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
	public final static String SHORT_DATE_FORMAT = "MM/dd/yy";
	private final static int msecInOneDay = 1000 * 60 * 60 * 24;


	public static int strToInt(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public static long strToLong(String value, long defaultValue) {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
		
	public static double strToDouble(String value, double defaultValue) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static String doubleToString(double value, int decimalPoints) {
		String result;
		
		if (value == 0) {
			result = "0";
		}
		else {
		    result = NumberFormat.getFormat("0.####").format(value);
		}
		
		return result;
	}

	public static String dateToString(Date date) {
		try {
			return DateTimeFormat.getFormat(SHORT_DATE_FORMAT + " h:mm a").format(date);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public static Date stringToDate(String date) {
		try {
			return new Date(Date.parse(date));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String timeToString(Date date) {
		try {
			return DateTimeFormat.getFormat("h:mm a").format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date stringToTime(String date) {
		try {
			return DateTimeFormat.getFormat("h:mm a").parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToStringISO8061(Date date) {
		try {
			return DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date stringToDateISO8061(String date, Date defaultValue) {
		try {
			return DateTimeFormat.getFormat("yyyy-MM-dd").parse(date);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Date stringToDateISO8061(String date) {
		return stringToDateISO8061(date, null);
	}

	public static String timeToStringISO8061(Date date) {
		try {
			return DateTimeFormat.getFormat("hh:mm").format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date stringToTimeISO8061(String date) {
		try {
			return DateTimeFormat.getFormat("HH:mm").parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date strToDateRfc822(String value) {
		return strToDateRfc822(value, null);
	}
	
	public static Date strToDateRfc822(String value, Date defaultValue) {
		try {	
			return DateTimeFormat.getFormat("E, dd MMM yyyy HH:mm:ss Z").parse(value);
			
//			SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z"); // RFC-822 date-time with time zone  
//			return df.parse(value);        			
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static Date strToDateRfc822IgnoreTimezone(String value) {
		return strToDateRfc822IgnoreTimezone(value, null);
	}
	
	public static Date strToDateRfc822IgnoreTimezone(String value, Date defaultValue) {
		try {
			if (value != null) {
				int loc = value.indexOf(" +0000");
				if (loc != -1) {
					value = value.substring(0, loc);
				}
			}
			return DateTimeFormat.getFormat("E, dd MMM yyyy HH:mm:ss").parse(value);    			
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String dateToStringRfc822(Date date) {
    	if (date != null) {
			return DateTimeFormat.getFormat("E, dd MMM yyyy HH:mm:ss Z").format(date);

//    		SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z"); // RFC-822 date-time with time zone 
//    		return formatter.format(date);
    	} else {
    		return "";
    	}
	}
	
	public static String dateToStringRfc822IgnoreTimezone(Date date) {
    	if (date != null) {
			return DateTimeFormat.getFormat("E, dd MMM yyyy HH:mm:ss").format(date) + " +0000";
    	} else {
    		return "";
    	}
	}
	
	public static int diffDaysFromNow(Date fromDate) {
		return diffDays(fromDate, new Date());
	}
	
	public static int diffDays(Date fromDate, Date toDate) {
		if (fromDate == null || toDate == null) 
			return -1;

		return (int) ((toDate.getTime() - fromDate.getTime()) / msecInOneDay);
	}
	
	public static String listToString(List<String> list, String delimiter) {
		String result = "";
		if (list != null) {
			for (int i = 0; i < list.size(); i++)
				result += list.get(i) + delimiter;
			if (list.size() > 0)
				result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static List<String> stringToList(String listString,	String delimiter) {
		ArrayList<String> result = new ArrayList<String>();
		int last = -1;

		if (listString != null && !listString.isEmpty()) {
			while (listString.indexOf(delimiter, ++last) != -1) {
				result.add(listString.substring(last, listString.indexOf(
						delimiter, last)));

				last = listString.indexOf(delimiter, last);
			}
			result.add(listString.substring(last, listString.length()));
		}

		return result;
	}
	
	public static boolean strIsNullOrEmpty(String str) {
		return ((str == null) || (str.isEmpty()));
	}

	public static String replaceEmptyStr(String str, String newValue) {
		if (strIsNullOrEmpty(str))
			return newValue;
		else
			return str;
	}

	public static boolean isEmail(String emailString) {
		if (emailString != null && !emailString.isEmpty()) {
			return emailString.matches(REGEX_EMAIL);
		}
		return false;
	}
	
	public static String capitalizeFirstLetter(String str) {
		if (str != null && str.length() == 1) {
			return String.valueOf(str.charAt(0)).toUpperCase();
		}
		else if (str != null && str.length() > 1) {
			return String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1);
		}
		else 
			return str;
	}
	
	public static String getFromQueryString(String queryString, String param) {

		if(queryString != null && !queryString.isEmpty() && queryString.charAt(0) == '?') {
			queryString = "&" + queryString.substring(1);
			param = "&" + param;
			int paramIndex = queryString.toLowerCase().indexOf(param.toLowerCase());
			
			if (paramIndex != -1) {
				int endIndex = queryString.indexOf("&", paramIndex + 1);
				if (endIndex != -1)
					return URL.decode(queryString.substring(paramIndex + param.length(), endIndex));
				else
					return URL.decode(queryString.substring(paramIndex + param.length()));
			}

		}
			
		return null;
			
			
//TODO: delete below after testing
//		int paramIndex = queryString.toLowerCase().indexOf(param.toLowerCase());
//		
//		if(!queryString.isEmpty() && queryString.charAt(0) == '?' && paramIndex != -1){
//			int endIndex = queryString.indexOf("&", paramIndex);
//			if (endIndex != -1)
//				return URL.decode(queryString.substring(paramIndex + param.length(), endIndex));
//			else
//				return URL.decode(queryString.substring(paramIndex + param.length()));
//		}
//		
//		return null;
	}
}
