package com.risevision.common.client.utils;

import java.util.ArrayList;

import com.risevision.common.client.info.PresentationInfo;
import com.risevision.common.client.info.PlaceholderInfo;
import com.risevision.common.client.json.PresentationJsonParser;

public class PresentationParser extends HtmlParser {
	private static final String htmlTag = "<html";
	private static final String htmlEndTag = "</html>";
	private static final String headStartTag = "<head";
	private static final String headEndTag = "</head>";
	private static final String linkTag = "<link";
	private static final String scriptStartTag = "<script";
	private static final String scriptEndTag = "</script>";
	private static final String bodyTag = "<body";
	private static final String bodyEndTag = "</body>";

	private static final String divTag = "<div";
	private static final String divEndTag = "</div>";	
	
	private static final String dataVariableParam = "presentationdata";
	private static final String dataVariableString = "" +
		"<script language='javascript'>\n" +
		"\t<!--\n" +
		"\tvar presentationData = %data%;\n" +
		"\t//-->\n" +
		"\t</script>";
	
	private static final String relParam = " rel=";
	private static final String relHelpLinkParam = "help";
	private static final String hrefParam = " href=";
	
	private static final String idParam = " id=";
	private static final String placeholderParam = " placeholder=";
//	private static final String urlParam = " src=";
	private static final String styleParam = " style=";
	private static final String widthParam = "width";
	private static final String heightParam = "height";
	private static final String topParam = "top";
	private static final String leftParam = "left";
	private static final String zIndexParam = "z-index";
	private static final String backgroundParam = "background";
	private static final String backgroundSizeParam = "background-size";
	private static final String backgroundScaleValue = "contain";
//	private static final String overflowParam = "overflow";
	
	private static final String idPrefix = "ph";
	
	// AD - overflow:hidden removed
//	private static final String nativeStyleString = "position:absolute;overflow:hidden;";
	private static final String nativeStyleString = "position:absolute;";
	
//	private static final String headerScript = "" +
//		"<link type='text/css' rel='stylesheet' href='style/viewer.css'>" +
//	    "<script type='text/javascript' language='javascript' src='scripts/jquery-1.7.1.min.js'></script>" +
//	    "<script type='text/javascript' language='javascript' src='scripts/jquery.timers-1.2.js'></script>" +
//	    
////	    "<script type='text/javascript' language='javascript' src='http://ig.gmodules.com/gadgets/js/rpc.js'></script>" +
//	    "<script type='text/javascript' language='javascript' src='http://www-open-opensocial.googleusercontent.com/gadgets/js/rpc.js'></script>" +
////	    "<script type='text/javascript' language='javascript' src='http://ec2-50-19-217-14.compute-1.amazonaws.com/shindig-server-2.5.0-beta1/gadgets/js/rpc.js'></script>" +
//
//	    "<script type='text/javascript' language='javascript' src='scripts/presentationScripts.js'></script>" +
//	    
////		"<link type='text/css' rel='stylesheet' href='slicebox/css/slicebox.css'>" +
////	    "<script type='text/javascript' language='javascript' src='slicebox/js/modernizr.custom.13303.js'></script>" +
//	    "<script type='text/javascript' language='javascript' src='slicebox/js/jquery.slicebox.js'></script>" +
//	    "<script type='text/javascript' language='javascript' src='slicebox/js/jquery.easing.1.3.js'></script>" +
//	    
//		"<script language='javascript'>" +
//		"var presFrame = '%s';" +
////		"" +
////		"function updateGadgets(params, containerName, htmlName) {" +
////		"    try {   	" +
////		"		if (htmlName != null) {" +
////		"	        myContainer = getElement(containerName);" +
////		"			createContainer(htmlName, containerName, myContainer.offsetHeight, myContainer.offsetWidth, 0, 0);" +
////		"			if (params != null) {" +
////		"				var head = document.getElementsByTagName('head')[0];" +
////		"				var scriptV = document.createElement('script');" +
////		"				scriptV.type = 'text/javascript';" +
////		"				scriptV.src = params;" +
////		"				head.appendChild(scriptV);" +
////		"			}" +
////		"		}" +
////		"    }" +
////		"    catch (err) {" +
////		"    	parent.writeToLog('updateGadgets - ' + htmlName + ' - ' + err.message);" +
////		"    }" +
////		"}" +
////		"function createContainer(frameName, containerName, height, width, top, left) {" +
////		"    try {" +
////		"  	" +
////		"        myFrame = getElement(frameName);" +
////		"        if (myFrame == null) {" +
////		"            var myDiv = createNewElement(frameName, 'div', height, width, top, left);" +
////		"" +
////		"            if (myDiv != null) {                myDiv.style['overflow'] = 'hidden';" +
////		"                myDiv.style['visibility'] = 'hidden';" +
////		"                myDiv.style['opacity'] = 0;" +
////		"" +
////		"                document.getElementById(containerName).appendChild(myDiv);" +
////		"            }" +
////		"        }" +
////		"    }" +
////		"    catch (err) {" +
////		"    	parent.writeToLog('createURL - ' + frameName + ' - ' + err.message);" +
////		"    }" +
////		"}" +
////		"" +
////		"function createNewElement(frameName, type, height, width, top, left) {" +
////		"    try {" +
////		"        var myFrame = document.createElement(type);" +
////		"        if (myFrame != null) {" +
////		"            myFrame.setAttribute('id', frameName);" +
////		"            myFrame.setAttribute('allowTransparency', true);" +
////		"" +
////		"            myFrame.style['position'] = 'absolute';" +
////		"" +
////		"            myFrame.style['left'] = left + 'px';" +
////		"            myFrame.style['top'] = top + 'px';" +
////		"            myFrame.style['width'] = width + 'px';" +
////		"            myFrame.style['height'] = height + 'px';" +
////		"" +
////		"            myFrame.setAttribute('frameBorder', '0');" +
////		"            myFrame.setAttribute('scrolling', 'no');" +
////		"        }" +
////		"        return myFrame;" +
////		"    }" +
////		"    catch (err) {" +
////		"    	parent.writeToLog('createNewElement - ' + frameName + ' - ' + err.message);" +
////		"    }" +
////		"}" +
////		"" +
////		"function getElement(elementName) {" +
////		"    try {" +
////		"        var myElement = document.getElementById(elementName);" +
////		"        return myElement;" +
////		"    }" +
////		"    catch (err) {" +
////		"        return null;" +
////		"    }" +
////		"}" +
//	    "</script>";
	
//	private static final String GADGET_WRAPPER_SCRIPT = "<script>try {" +
//			"updateGadgetWrapper('%s1', '%s2', %s3, %s4, '%s5');" +
//			"} catch(err) { parent.writeToLog('updateGadgetWrapper call - %s2 - ' + err.message); }" +
//			"</script>";
//	
//	private static final String GADGET_SCRIPT = "<script><!--\n" +
//			"try {" +
//			"updateGadgets('%s1', '%s2', '%s3', '%s4', '%s5', '%s6');" +
//			"} catch(err) { parent.writeToLog('updateGadgets call - %s3 - ' + err.message); }" +
//			"\n//-->" +
//			"</script>";
//	
//	private static final String WIDGET_SCRIPT = "<script><!--\n" +
//			"try {" +
//			"updateWidget('%s1', '%s3', '%s4', '%s5');" +
//			"gadgetRequest('%s4', '%s2');" +
//			"} catch(err) { parent.writeToLog('updateWidget call - %s3 - ' + err.message); }" +
//			"\n//-->" +
//			"</script>";
//	
//	private static final String TEXT_SCRIPT = "<script><!--\n" +
//			"try {" +
//			"updateText('%s1', '%s2', '%s3', '%s4', '%s5', '%s6');" +
//			"} catch(err) { parent.writeToLog('updateText call - %s3 - ' + err.message); }" +
//			"\n//-->" +
//			"</script>";
//	
//	private static final String EMBED_SCRIPT = "<script><!--\n" +
//			"try {" +
//			"updateEmbed('%s1', '%s2', '%s3', '%s4', '%s5', '%s6');" +
//			"} catch(err) { parent.writeToLog('updateEmbed call - %s3 - ' + err.message); }" +
//			"\n//-->" +
//			"</script>";
//	
//	private static final String IMAGE_SCRIPT = "<script><!--\n" +
//			"try {" +
//			"updateImage('%s1', '%s2', '%s3', '%s4', '%s5', '%s6');" +
//			"} catch(err) { parent.writeToLog('updateImage call - %s2 - ' + err.message); }" +
//			"\n//-->" +
//			"</script>";
//	
//	private static final String VIDEO_SCRIPT = "<script><!--\n" +
//			"try {" +
//			"updateVideo('%s1', '%s2', '%s3', '%s4');" +
//			"} catch(err) { parent.writeToLog('updateVideo call - %s2 - ' + err.message); }" +
//			"\n//-->" +
//			"</script>";
	
	private static final String HIDE_SCRIPT = "<script>try {" +
			"setVisible('%s1', false);" +
			"} catch(err) { parent.writeToLog('setVisible call - %s1 - ' + err.message); }" +
			"</script>";
	
	public static void parsePresentation(PresentationInfo presentation) {		
		int start, end;
		String htmlString = presentation.getLayout();
		
		if (htmlString == null || htmlString.isEmpty())
			return;

		start = htmlString.toLowerCase().indexOf(htmlTag);
		end = htmlString.toLowerCase().indexOf(htmlEndTag, start);
		if (start == -1 || end == -1)
			return;
		
		// process head for help link
		start = htmlString.toLowerCase().indexOf(headStartTag, start);
		end = htmlString.toLowerCase().indexOf(headEndTag, start);
		
		if (start == -1 || end < start)
			return;
		
		presentation.setHelpURL(parseHelpLink(htmlString.substring(start, end + 1)));

		// process body next
		start = htmlString.toLowerCase().indexOf(bodyTag, start);
		end = htmlString.indexOf(">", start);
		
		if (start == -1 || end < start)
			return;

		parseBodyStyle(presentation, stripOuterGarbage(getPropertyValue(htmlString.substring(start, end + 1), styleParam)));
		
		end = htmlString.toLowerCase().indexOf(bodyEndTag, start);
		if (start == -1 || end == -1)
			return;

		// process data
		parsePresentationData(presentation);
		
		parsePlaceholders(presentation.getPlaceholders(), htmlString.substring(start, end + bodyEndTag.length()));
	}
	
	private static String parseHelpLink(String htmlString) {
		int start, end;

		start = htmlString.toLowerCase().indexOf(linkTag);
		while (start != -1) {
			// search for the end of the tag
			end = htmlString.indexOf(">", start);
			
			if (relHelpLinkParam.equals(stripGarbage(getPropertyValue(htmlString.substring(start, end + 1), relParam)).toLowerCase())) {
				return stripGarbage(getPropertyValue(htmlString.substring(start, end + 1), hrefParam));
			}
			
			start = htmlString.toLowerCase().indexOf(linkTag, end);
		}
		
		return "";
	}
	
	private static void parsePresentationData(PresentationInfo presentation) {
		int start, end;
		String htmlString = presentation.getLayout();

		start = htmlString.toLowerCase().indexOf(dataVariableParam);
		if (start == -1) {
		}
		else {
			// find if the next character is a quote or a bracket
			if (htmlString.indexOf("'", start) == -1 || htmlString.indexOf("{", start) < htmlString.indexOf("'", start)) {
				start = htmlString.indexOf("{", start);
				
				end = htmlString.lastIndexOf("};");
				
				// increment end only if end string was found
				if (end != -1) {
					// do not include the ;
					end = end + 1;
				}
			}
			else {
				String quote = getNextQuote(htmlString.substring(start));
				
				start = htmlString.indexOf(quote, start) + 1;
				end = htmlString.indexOf(quote, start);
				while (end != -1 && htmlString.charAt(end - 1) == '\\') {
					end = htmlString.indexOf(quote, end + 1);
				}
			}
			
			if (end != -1) {
				String json = htmlString.substring(start, end);

				json = json.replace("\\'", "\'");
				
				PresentationJsonParser.parsePresentationData(presentation, json);
			}
			else {
//				throw new ParserError();
			}
		}
	}
	
	private static void parseBodyStyle(PresentationInfo presentation,
			String htmlString) {
		String[] tokens = htmlString.split(";");
		for (int x = 0; x < tokens.length; x++) {
			if (tokens[x].indexOf(":") != -1){
				String param = tokens[x].substring(0, tokens[x].indexOf(":")).trim();
				String value = tokens[x].substring(tokens[x].indexOf(":") + 1).trim();
				
				if (param.equalsIgnoreCase(widthParam)){
					presentation.setWidth((int)getValue(value));
					presentation.setWidthUnits(getUnits(value));
				}
				else if (param.equalsIgnoreCase(heightParam)){
					presentation.setHeight((int)getValue(value));
					presentation.setHeightUnits(getUnits(value));
				}	
				else if (param.equalsIgnoreCase(backgroundParam)){
					presentation.setBackgroundStyle(value);
				}	
				else if (param.equalsIgnoreCase(backgroundSizeParam)) {
					presentation.setBackgroundScaleToFit(value.equals(backgroundScaleValue));
				}
			}
		}
	}

	private static boolean parsePlaceholders(
			ArrayList<PlaceholderInfo> placeholders,
			String htmlString) {
		int start, end;
		ArrayList<PlaceholderInfo> newPlaceholders = new ArrayList<PlaceholderInfo>();

		start = htmlString.indexOf(divTag);
		while (start != -1) {
			// search for the end of the div
			end = htmlString.indexOf(">", start);

			PlaceholderInfo placeholder = parseDiv(htmlString.substring(start, end + 1));
			
			if (placeholder != null) {
				newPlaceholders.add(placeholder);
			}

			start = htmlString.indexOf(divTag, end);
		}
		
		boolean listChanged = false;
		for (int i = 0; i < placeholders.size(); i++) { 
			boolean found = false;
			for (PlaceholderInfo newPh: newPlaceholders) {
				if (newPh.getId().equals(placeholders.get(i).getId())) {
					// temporary code for deleting all placeholders that don't have placeholder="true" from the Core
//					if (placeholders.get(i).isPlaceholder() 
//							||	placeholders.get(i).getType().equals(PlaceholderInfo.TYPE_GADGET) 
//							|| (placeholders.get(i).getType().equals(PlaceholderInfo.TYPE_PLAYLIST)
//							&& placeholders.get(i).getObjectRef() != null && !placeholders.get(i).getObjectRef().isEmpty())) {
//						newPh.setPlaceholder(true);
//					}
//					else {
//						listChanged = true;
//						placeholders.get(i).setDeleted(true);
//					}
					found = true;
					break;
				}
			}
			if (!found) {
				listChanged = true;
				placeholders.get(i).setDeleted(true);
			}
		}

		removeDeletedPlaceholders(placeholders);
		
		for (PlaceholderInfo newPh: newPlaceholders) {
			boolean found = false;
			for (PlaceholderInfo ph: placeholders) {
				if (newPh.getId().equals(ph.getId())) {
					found = true;
					ph.setWidth(newPh.getWidth());
					ph.setWidthUnits(newPh.getWidthUnits());
					ph.setHeight(newPh.getHeight());
					ph.setHeightUnits(newPh.getHeightUnits());
					ph.setLeft(newPh.getLeft());
					ph.setLeftUnits(newPh.getLeftUnits());
					ph.setTop(newPh.getTop());
					ph.setTopUnits(newPh.getTopUnits());
					ph.setzIndex(newPh.getzIndex());
					ph.setBackgroundStyle(newPh.getBackgroundStyle());
					ph.setBackgroundScaleToFit(newPh.isBackgroundScaleToFit());
					
//					ph.setPlaceholder(newPh.isPlaceholder());
				}
			}
			
			if (!found /* && newPh.isPlaceholder() */)	{
				listChanged = true;
				placeholders.add(newPh);
			}
		}
		
		return listChanged;
	}

	private static PlaceholderInfo parseDiv(String htmlString) {
		PlaceholderInfo placeholder = null;
		
		String id = getPropertyValue(htmlString, idParam);
		if (!id.isEmpty() && !getPropertyValue(htmlString, placeholderParam).isEmpty()) {
//			PresentationPlaceholderInfo placeholder = null;
//			boolean found = false;
//			for (PresentationPlaceholderInfo ph: placeholders) {
//				if (ph.getId().equals(id)) {
//					found = true;
//					placeholder = ph;
//					break;
//				}
//			}
//
//			if (!found) {
//				placeholder = new PresentationPlaceholderInfo();
//				placeholders.add(placeholder);
//			}
	
			placeholder = new PlaceholderInfo();
			
//			placeholder.setPlaceholder(!getPropertyValue(htmlString, placeholderParam).isEmpty());
			
			placeholder.setId(id);
			
			parseStyle(placeholder, stripOuterGarbage(getPropertyValue(htmlString, styleParam)));
		}
		return placeholder;
	}
	
	private static void parseStyle(PlaceholderInfo placeholder,
			String htmlString) {
		String[] tokens = htmlString.split(";");
		for (int x = 0; x < tokens.length; x++) {
			if (tokens[x].indexOf(":") != -1){
				String param = tokens[x].substring(0, tokens[x].indexOf(":")).trim();
				String value = tokens[x].substring(tokens[x].indexOf(":") + 1).trim();
				
				if (param.equalsIgnoreCase(widthParam)){
					placeholder.setWidth(getValue(value));
					placeholder.setWidthUnits(getUnits(value));
				}
				else if (param.equalsIgnoreCase(heightParam)){
					placeholder.setHeight(getValue(value));
					placeholder.setHeightUnits(getUnits(value));
				}
				else if (param.equalsIgnoreCase(topParam)){
					placeholder.setTop(getValue(value));
					placeholder.setTopUnits(getUnits(value));
				}
				else if (param.equalsIgnoreCase(leftParam)){
					placeholder.setLeft(getValue(value));
					placeholder.setLeftUnits(getUnits(value));
				}
				else if (param.equalsIgnoreCase(zIndexParam)){
					placeholder.setzIndex((int)getValue(value));
				}
				else if (param.equalsIgnoreCase(backgroundParam)){
					placeholder.setBackgroundStyle(value);
				}	
				else if (param.equalsIgnoreCase(backgroundSizeParam)) {
					placeholder.setBackgroundScaleToFit(value.equals(backgroundScaleValue));
				}
			}
		}
	}

	public static void updatePresentation(PresentationInfo presentation){
		int start, end;
		String htmlString = presentation.getLayout();
		
		if (htmlString == null || htmlString.isEmpty())
			return;

		start = htmlString.toLowerCase().indexOf(htmlTag);
		end = htmlString.toLowerCase().indexOf(htmlEndTag, start);
		if (start == -1 || end == -1)
			return;

		// process body first
		start = htmlString.toLowerCase().indexOf(bodyTag, start);
		end = htmlString.toLowerCase().indexOf(bodyEndTag, start);
		if (start == -1 || end == -1)
			return;
		
		end += + bodyEndTag.length();
		
		String newPlaceholders = updatePlaceholders(presentation.getPlaceholders(), htmlString.substring(start, end));
		
		htmlString = htmlString.substring(0, start) + newPlaceholders + htmlString.substring(end, htmlString.length());
		
		htmlString = updatePresentationData(presentation, htmlString);
		
		presentation.setLayout(htmlString);
	}
	
	private static String updatePresentationData(PresentationInfo presentation, String htmlString) {
		String presentationData = PresentationJsonParser.updatePresentationData(presentation);
//		presentationData = presentationData.replace("'", "\\'");
		
		String modifiedDataVariableString = dataVariableString.replace("%data%", presentationData);
		
		return updatePresentationObject(htmlString, modifiedDataVariableString);
	}
	
	private static String updatePresentationObject(String htmlString, String modifiedDataVariableString) {
		int start, end = -1;

		start = htmlString.toLowerCase().indexOf(dataVariableParam);
		if (start == -1) {
			end = htmlString.indexOf(htmlEndTag);
			start = end;
			
			// Add spacing for the data variable
			modifiedDataVariableString = "" +
					"\n<!-- Warning - Editing the Presentation Data Object incorrectly may result in the Presentation not functioning correctly -->" +
					"\n\t" + modifiedDataVariableString + "" +
					"\n<!-- No scripts after this point -->" +
					"\n";
			
//			if (bodyEnd != -1){
//				String modifiedDataVariableString = dataVariableString.replace("%data%", presentationData);
//				htmlString = htmlString.substring(0, bodyEnd) + modifiedDataVariableString + htmlString.substring(bodyEnd, htmlString.length());
//			}
		}
		else {
			if (htmlString.indexOf("'", start) == -1 || htmlString.indexOf("{", start) < htmlString.indexOf("'", start)) {
				start = htmlString.indexOf("{", start);
				
				end = htmlString.lastIndexOf("};");
						
				// increment end only if end string was found
				if (end != -1) {
					end = end + 2;
				}
			}
			else {
				String quote = getNextQuote(htmlString.substring(start));
				
				start = htmlString.indexOf(quote, start);
				end = htmlString.indexOf(quote, start + 1);
				while (end != -1 && htmlString.charAt(end - 1) == '\\') {
					end = htmlString.indexOf(quote, end + 1);
				}
				end++;
			}
			
			if (end != -1) {
				start = htmlString.lastIndexOf(scriptStartTag, start);
				end = htmlString.lastIndexOf(scriptEndTag) + scriptEndTag.length();
			}
			
//			if (end != -1)
//				htmlString = htmlString.substring(0, start) + presentationData + htmlString.substring(end, htmlString.length());
		}

		if (start != -1 && end != -1) {
			htmlString = htmlString.substring(0, start) + modifiedDataVariableString + htmlString.substring(end, htmlString.length());
		}

		return htmlString;
	}
	
	public static void updatePresentationHeader(PresentationInfo presentation){
		int start, end;
		String htmlString = presentation.getLayout();
		
		if (htmlString == null || htmlString.isEmpty())
			return;

		start = htmlString.toLowerCase().indexOf(htmlTag);
		end = htmlString.toLowerCase().indexOf(htmlEndTag, start);
		if (start == -1 || end == -1)
			return;

		// process body first
		start = htmlString.toLowerCase().indexOf(bodyTag, start);
		end = htmlString.indexOf(">", start);
		
		if (start == -1 || end < start)
			return;
		
		String newBody = updateBodyTag(presentation, htmlString.substring(start, end));
		htmlString = htmlString.substring(0, start) + newBody + htmlString.substring(end, htmlString.length());
				
		presentation.setLayout(htmlString);
	}
	
	private static String updateBodyTag(PresentationInfo presentation,
			String htmlString) {
		int start = 0, end = 0;
		String styleString;
		boolean found = false;

		styleString = getPropertyValue(htmlString, styleParam);
		if (styleString != ""){
			start = htmlString.indexOf(styleString);
			end = start + styleString.length();
			found = true;
		}
		
		styleString = updateStyle(widthParam, presentation.getWidth() + presentation.getWidthUnits(), styleString);
		styleString = updateStyle(heightParam, presentation.getHeight() + presentation.getHeightUnits(), styleString);

		if (presentation.getBackgroundStyle() != null) {
			styleString = updateStyle(backgroundParam, presentation.getBackgroundStyle(), styleString);	
		}
		if (presentation.isBackgroundScaleToFit()) {
			styleString = updateStyle(backgroundSizeParam, backgroundScaleValue, styleString);
		}
		else {
			styleString = removeStyle(backgroundSizeParam, styleString);
		}
		
		if (found)
			htmlString = htmlString.substring(0, start) + styleString + htmlString.substring(end, htmlString.length());
		else
			htmlString += styleParam + "\"" + styleString + "\"";
		
		return htmlString;
	}

	private static String updatePlaceholders(ArrayList<PlaceholderInfo> placeholders, String htmlString) {
		int start, end;

		for (PlaceholderInfo placeholder: placeholders){
			if (placeholder.getId() == null || placeholder.getId().isEmpty()) {
				if (!placeholder.isDeleted()) {
					setPlaceholderId(placeholder, placeholders);
				}
				start = -1;
			}
			else {
				start = findPlaceholder(htmlString, placeholder.getId());
			}
			
			if (start != -1) {
				// [AD] Inefficiency + this:	htmlString.substring(0, start).length() == start
//				start = htmlString.substring(0, start).length() - reverseString(htmlString.substring(0, start)).indexOf("<") - 1;
				start = htmlString.lastIndexOf("<", start);

				if (!placeholder.isDeleted()){
					start += divTag.length();
					end = htmlString.indexOf(">", start);
					
					String newDiv = updateDiv(placeholder, htmlString.substring(start, end));
					
					htmlString = htmlString.substring(0, start) + newDiv + htmlString.substring(end, htmlString.length());
				}
				else /* if (placeholder.isPlaceholder()) */ {
					end = htmlString.indexOf(">", start);
					int nextTag = htmlString.indexOf(divTag, end);
					end = htmlString.indexOf(divEndTag, end);
					
					if (end != -1 && (nextTag == -1 || end < nextTag)){
						end += divEndTag.length();
						htmlString = htmlString.substring(0, start).trim() + "\n\t" + htmlString.substring(end, htmlString.length()).trim();
					}
					
					placeholder.setId("");
				}
			}
			else if (!placeholder.isDeleted()){
				String newTag = "" + divTag + " " + updateDiv(placeholder, "") + ">" + divEndTag + "\n\t";
				end = htmlString.indexOf(bodyEndTag);
				htmlString = htmlString.substring(0, end) + newTag + htmlString.substring(end);
			}
		}
		
		removeDeletedPlaceholders(placeholders);
		
		return htmlString;
	}
	
	private static void setPlaceholderId(PlaceholderInfo placeholder, ArrayList<PlaceholderInfo> placeholders) {
		for (int i = 0; i < placeholders.size(); i++) {
			boolean found = false;
			for (PlaceholderInfo ph: placeholders) {
				if (!ph.equals(placeholder) && !ph.isDeleted() && ph.getId() != null && !ph.getId().isEmpty()) {
					if (ph.getId().equals(idPrefix + i)) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				placeholder.setId(idPrefix + i);
				break;
			}
		}
		
	}

	private static int findPlaceholder(String htmlString, String id){
		int start = -1;
		
		start = htmlString.indexOf("\"" + id + "\"");
		if (start == -1){
			start = htmlString.indexOf("'" + id + "'");
		}
		
		if (start != -1)
			start++;
		
		return start;
	}

	private static String updateDiv(PlaceholderInfo placeholder, String htmlString) {
		
		int start = 0, end = 0;
		String idString, styleString;
//		String urlString;
		boolean found = false;

		idString = getPropertyValue(htmlString, idParam);
		if (!idString.isEmpty()){
			start = htmlString.indexOf(idString);
			end = start + idString.length();

			// now is the time to re-name the placeholder (if needed); and reset newId variable
			if (placeholder.getNewId() != null && !placeholder.getNewId().isEmpty()) {
				placeholder.setId(placeholder.getNewId());
				placeholder.setNewId(null);
			}
			htmlString = htmlString.substring(0, start) + placeholder.getId() + htmlString.substring(end, htmlString.length());
		}
		else {
			htmlString += idParam + "\"" + placeholder.getId() + "\"";
		}
		
		String phString = getPropertyValue(htmlString, placeholderParam);
		if (phString.isEmpty()) {
			htmlString += placeholderParam + "\"true\"";
		}
		
//		if (placeholder.getType().equals(PresentationPlaceholderInfo.TYPE_PLAYLIST)) {
//			placeholder.setUrl(playlistParam + placeholder.getObjectRef()); 
//		}
//		
//		urlString = getPropertyValue(htmlString, urlParam);
//		if (urlString != ""){
//			start = htmlString.indexOf(urlString);
//			end = start + urlString.length();
//			
//			htmlString = htmlString.substring(0, start) + placeholder.getUrl() + htmlString.substring(end, htmlString.length());
//		}
//		else{
//				htmlString += urlParam + "\"" + placeholder.getUrl() + "\"";
//		}
		
		styleString = getPropertyValue(htmlString, styleParam);
		if (!styleString.isEmpty()){
			start = htmlString.indexOf(styleString);
			end = start + styleString.length();
			found = true;
		}
		
		styleString = updateStyle(widthParam, getUnitString(placeholder.getWidth(), placeholder.getWidthUnits()), styleString);
		styleString = updateStyle(heightParam, getUnitString(placeholder.getHeight(), placeholder.getHeightUnits()), styleString);
		styleString = updateStyle(leftParam, getUnitString(placeholder.getLeft(), placeholder.getLeftUnits()), styleString);
		styleString = updateStyle(topParam, getUnitString(placeholder.getTop(), placeholder.getTopUnits()), styleString);
		styleString = updateStyle(zIndexParam, placeholder.getzIndex() + "", styleString);	
		if (placeholder.getBackgroundStyle() != null) {
			styleString = updateStyle(backgroundParam, placeholder.getBackgroundStyle(), styleString);	
		}
		if (placeholder.isBackgroundScaleToFit()) {
			styleString = updateStyle(backgroundSizeParam, backgroundScaleValue, styleString);
		}
		else {
			styleString = removeStyle(backgroundSizeParam, styleString);
		}
		
		if (found)
			htmlString = htmlString.substring(0, start) + styleString + htmlString.substring(end, htmlString.length());
		else
			htmlString += styleParam + "\"" + styleString + nativeStyleString + "\"";
		
		return htmlString;
	}
	
	private static void removeDeletedPlaceholders(ArrayList<PlaceholderInfo> placeholders) {
		// remove deleted placeholders from the list
		for (int i = 0; i < placeholders.size(); i++){
			if (placeholders.get(i).isDeleted()) {
				placeholders.remove(i);
				i--;
			}
		}
	}
	
	public static void addHeaderScripts(PresentationInfo presentation, String headerScript){
		String myHtml = presentation.getLayout();
		
//		if (myHtml.contains(htmlTag)) {
//			myHtml.replaceFirst(htmlTag, htmlTag + " manifest=\"offline.manifest\"");
//		}
		
		int headEnd = myHtml.toLowerCase().indexOf(headStartTag);
		
		if (headEnd != -1) {
			headEnd = myHtml.indexOf(">", headEnd);
//			int headEnd = myHtml.indexOf(headEndTag);
			
			if (headEnd != -1){
				headEnd++;
//				String modifiedHeader = headerScript.replace("%s", containerName);
				myHtml = myHtml.substring(0, headEnd) + headerScript + myHtml.substring(headEnd, myHtml.length());
			}
		}
		
		presentation.setLayout(myHtml);
	}
	
	public static void removePresentationObject(PresentationInfo presentation) {
		presentation.setLayout(updatePresentationObject(presentation.getLayout(), ""));
	}
		
//	public static void setScrollEnabled(PresentationInfo presentation) {
//		String htmlString = presentation.getLayout();
//		int start, end;
//		
//		start = htmlString.toLowerCase().indexOf(bodyTag);
//		end = htmlString.indexOf(">", start);
//		
//		if (start == -1 || end < start)
//			return;
//		
//		String newBody = updateBodyScrollEnabled(htmlString.substring(start, end));
//		htmlString = htmlString.substring(0, start) + newBody + htmlString.substring(end, htmlString.length());
//		
//		presentation.setLayout(htmlString);
//	}
	
//	private static String updateBodyScrollEnabled(String htmlString) {
//		int start = 0, end = 0;
//		String styleString;
//		boolean found = false;
//
//		styleString = getPropertyValue(htmlString, styleParam);
//		if (!styleString.isEmpty()){
//			start = htmlString.indexOf(styleString);
//			end = start + styleString.length();
//			found = true;
//		}
//		
//		styleString = updateStyle(overflowParam, "auto", styleString);
//
//		if (found)
//			htmlString = htmlString.substring(0, start) + styleString + htmlString.substring(end, htmlString.length());
//		else
//			htmlString += styleParam + "\"" + styleString + "\"";
//		
//		return htmlString;
//	}
	
	public static void addScriptTag(PresentationInfo presentation, String tagHtml){
		String myHtml = presentation.getLayout();
		int bodyEnd = myHtml.lastIndexOf(bodyEndTag);
		
		if (bodyEnd != -1){
			myHtml = myHtml.substring(0, bodyEnd) + tagHtml + myHtml.substring(bodyEnd, myHtml.length());
		}
		
		presentation.setLayout(myHtml);
	}
	
//	public static void addGadgetWrapper(PresentationInfo presentation, String containerName, String htmlName, int width, int height, String transition){
//		String myHtml = presentation.getLayout();
//		int bodyEnd = myHtml.lastIndexOf(bodyEndTag);
//		
//		if (bodyEnd != -1){
//			String modifiedGadgetScript = GADGET_WRAPPER_SCRIPT.replace("%s1", containerName);
//			modifiedGadgetScript = modifiedGadgetScript.replace("%s2", htmlName);
//			modifiedGadgetScript = modifiedGadgetScript.replace("%s3", Integer.toString(width));
//			modifiedGadgetScript = modifiedGadgetScript.replace("%s4", Integer.toString(height));
//			modifiedGadgetScript = modifiedGadgetScript.replace("%s5", transition);
//			myHtml = myHtml.substring(0, bodyEnd) + modifiedGadgetScript + myHtml.substring(bodyEnd, myHtml.length());
//		}
//		
//		presentation.setLayout(myHtml);
//	}
//	
//	public static void addGadgetScript(PresentationInfo presentation, String url, String containerName, String htmlName, String width, String height, String transition){
//		String myHtml = presentation.getLayout();
//		int bodyEnd = myHtml.lastIndexOf(bodyEndTag);
//		
//		if (bodyEnd != -1){
//			String modifiedGadgetScript = GADGET_SCRIPT.replace("%s1", url);
//			modifiedGadgetScript = modifiedGadgetScript.replace("%s2", containerName);
//			modifiedGadgetScript = modifiedGadgetScript.replace("%s3", htmlName);
//			modifiedGadgetScript = modifiedGadgetScript.replace("%s4", width);
//			modifiedGadgetScript = modifiedGadgetScript.replace("%s5", height);
//			modifiedGadgetScript = modifiedGadgetScript.replace("%s6", transition);
//			myHtml = myHtml.substring(0, bodyEnd) + modifiedGadgetScript + myHtml.substring(bodyEnd, myHtml.length());
//		}
//		
//		presentation.setLayout(myHtml);
//	}
//	
//	public static void addWidgetScript(PresentationInfo presentation, String html, String gadgetUrl, String containerName, String htmlName, String transition){
//		String myHtml = presentation.getLayout();
//		int bodyEnd = myHtml.lastIndexOf(bodyEndTag);
//		
//		if (bodyEnd != -1){
//			String modifiedWidgetScript = WIDGET_SCRIPT.replace("%s1", html)
//							.replace("%s2", gadgetUrl)
//							.replace("%s3", containerName)
//							.replace("%s4", htmlName)
//							.replace("%s5", transition);
//			myHtml = myHtml.substring(0, bodyEnd) + modifiedWidgetScript + myHtml.substring(bodyEnd, myHtml.length());
//		}
//		
//		presentation.setLayout(myHtml);
//	}
//	
//	public static void addTextScript(PresentationInfo presentation, String text, String containerName, String htmlName, String width, String height, String transition) {
//		String myHtml = presentation.getLayout();
//		int bodyEnd = myHtml.lastIndexOf(bodyEndTag);
//		text = text.replace("'", "\\'");
//		
//		if (bodyEnd != -1){
//			String modifiedTextScript = TEXT_SCRIPT.replace("%s1", text);
//			modifiedTextScript = modifiedTextScript.replace("%s2", containerName);
//			modifiedTextScript = modifiedTextScript.replace("%s3", htmlName);
//			modifiedTextScript = modifiedTextScript.replace("%s4", width);
//			modifiedTextScript = modifiedTextScript.replace("%s5", height);
//			modifiedTextScript = modifiedTextScript.replace("%s6", transition);
//			myHtml = myHtml.substring(0, bodyEnd) + modifiedTextScript + myHtml.substring(bodyEnd, myHtml.length());
//		}
//		
//		presentation.setLayout(myHtml);
//	}
//	
//	public static void addEmbedScript(PresentationInfo presentation, String url, String containerName, String htmlName, String width, String height, String transition) {
//		String myHtml = presentation.getLayout();
//		int bodyEnd = myHtml.lastIndexOf(bodyEndTag);
//		
//		if (bodyEnd != -1){
//			String modifiedTextScript = EMBED_SCRIPT.replace("%s1", url);
//			modifiedTextScript = modifiedTextScript.replace("%s2", containerName);
//			modifiedTextScript = modifiedTextScript.replace("%s3", htmlName);
//			modifiedTextScript = modifiedTextScript.replace("%s4", width);
//			modifiedTextScript = modifiedTextScript.replace("%s5", height);
//			modifiedTextScript = modifiedTextScript.replace("%s6", transition);
//			myHtml = myHtml.substring(0, bodyEnd) + modifiedTextScript + myHtml.substring(bodyEnd, myHtml.length());
//		}
//		
//		presentation.setLayout(myHtml);
//	}
//	
//	public static void addImageScript(PresentationInfo presentation, String html, String containerName, String htmlName, String width, String height, String transition) {
//		String myHtml = presentation.getLayout();
//		int bodyEnd = myHtml.lastIndexOf(bodyEndTag);
//		html = html.replace("'", "\\'");
//		
//		if (bodyEnd != -1){
//			String modifiedScript = IMAGE_SCRIPT.replace("%s1", html);
//			modifiedScript = modifiedScript.replace("%s2", containerName);
//			modifiedScript = modifiedScript.replace("%s3", htmlName);
//			modifiedScript = modifiedScript.replace("%s4", width);
//			modifiedScript = modifiedScript.replace("%s5", height);
//			modifiedScript = modifiedScript.replace("%s6", transition);
//			myHtml = myHtml.substring(0, bodyEnd) + modifiedScript + myHtml.substring(bodyEnd, myHtml.length());
//		}
//		
//		presentation.setLayout(myHtml);
//	}
//	
//	public static void addVideoScript(PresentationInfo presentation, String html, String containerName, String htmlName, String transition) {
//		String myHtml = presentation.getLayout();
//		int bodyEnd = myHtml.lastIndexOf(bodyEndTag);
//		html = html.replace("'", "\\'");
//		
//		if (bodyEnd != -1){
//			String modifiedScript = VIDEO_SCRIPT.replace("%s1", html);
//			modifiedScript = modifiedScript.replace("%s2", containerName);
//			modifiedScript = modifiedScript.replace("%s3", htmlName);
//			modifiedScript = modifiedScript.replace("%s4", transition);
//			myHtml = myHtml.substring(0, bodyEnd) + modifiedScript + myHtml.substring(bodyEnd, myHtml.length());
//		}
//		
//		presentation.setLayout(myHtml);
//	}
	
	public static void hidePlaceholder(PresentationInfo presentation, String placeholderId) {
		String myHtml = presentation.getLayout();
		int bodyEnd = myHtml.lastIndexOf(bodyEndTag);
		
		if (bodyEnd != -1){
			String modifiedTextScript = HIDE_SCRIPT.replace("%s1", placeholderId);
			myHtml = myHtml.substring(0, bodyEnd) + modifiedTextScript + myHtml.substring(bodyEnd, myHtml.length());
		}
		
		presentation.setLayout(myHtml);
	}
}
