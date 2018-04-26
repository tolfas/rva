package com.risevision.common.client.utils;

public class HtmlParser {
	public static final String UNIT_PIXEL = "px";
	public static final String UNIT_PERCENT = "%";
	
	private static final String numbers = "0123456789.-";
	
	protected static String getUnitString(double value, String unit) {
		int decimalPoints = 0;
		
		if (UNIT_PERCENT.equals(unit)) {
			decimalPoints = 5;
		}
		
		return RiseUtils.doubleToString(value, decimalPoints) + unit;
	}
	
	protected static String updateStyle(String param, String value, String styleToken) {
		String[] tokens = styleToken.split(";");
		boolean found = false;
		for (int x = 0; x < tokens.length; x++) {
			if (tokens[x].indexOf(":") != -1){			
				if (tokens[x].substring(0, tokens[x].indexOf(":")).trim().equalsIgnoreCase(param)){				
					tokens[x] = param + ":" + value;
					found = true;
				}
			}
		}
		if (!found)
			return toString(tokens, ";") + param + ":" + value + ";";
		return toString(tokens, ";");
	}
	
	protected static String removeStyle(String param, String styleToken) {
		String[] tokens = styleToken.split(";");
		for (int x = 0; x < tokens.length; x++) {
			if (tokens[x].indexOf(":") != -1){			
				if (tokens[x].substring(0, tokens[x].indexOf(":")).trim().equalsIgnoreCase(param)){				
					tokens[x] = "";
				}
			}
		}
		return toString(tokens, ";");
	}
	
	public static String getPropertyValue(String htmlString, String property){
		int start, end;
		
		start = htmlString.indexOf(property);
		if (start != -1){		
			String quote = getNextQuote(htmlString.substring(start));
			
			start = htmlString.indexOf(quote, start) + 1;
			end = htmlString.indexOf(quote, start);
			
			if (end != -1)
				return htmlString.substring(start, end);
		}
		
		return "";
	}
	
	protected static String getUnits(String token){
		String result = "";
		token = stripGarbage(token);
		
		for (int i = 0; i < token.length(); i++){
			if (numbers.indexOf(token.charAt(i)) == -1)
				result += token.charAt(i);
		}

		return result;
	}
	
	protected static double getValue(String token){
		String resultString = "";
		double result = -1;
		token = stripGarbage(token);
		
		for (int i = 0; i < token.length(); i++){
			if (numbers.indexOf(token.charAt(i)) != -1)
				resultString += token.charAt(i);
			else
				break;
		}

//		result = RiseUtils.strToInt(resultString, 0);
		result = RiseUtils.strToDouble(resultString, 0);
		
		return result;
	}
	
	protected static String getNextQuote(String htmlString) {
		String quote;
		if (htmlString.indexOf("\"") != -1 && (htmlString.indexOf("'") == -1 || htmlString.indexOf("\"") < htmlString.indexOf("'")))
			quote = "\"";
		else
			quote = "'";
		
		return quote;
	}
	
	private static String toString(String[] tokens, String divider) {
		String result = "";
		for (String token: tokens){
			if (!token.isEmpty())
				result += token + divider;
		}
		return result;
	}
	
//	private static String reverseString(String source) {
//		int i, len = source.length();
//		StringBuffer dest = new StringBuffer(len);
//
//		for (i = (len - 1); i >= 0; i--)
//			dest.append(source.charAt(i));
//		return dest.toString();
//	}

	protected static String stripGarbage(String s) {
		String bad = "\"' ";
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			if (bad.indexOf(s.charAt(i)) == -1)
				result += s.charAt(i);
		}
		return result;
	}
	
	protected static String stripOuterGarbage(String s) {
		s = s.trim();
		if (s.startsWith("'")) {
			s = s.substring(1, s.length());
		}
		if (s.endsWith("'")) {
			s = s.substring(0, s.length() - 1);
		}
		if (s.startsWith("\"")) {
			s = s.substring(1, s.length());
		}
		if (s.endsWith("\"")) {
			s = s.substring(0, s.length() - 1);
		}
		return s.trim();
	}
}
