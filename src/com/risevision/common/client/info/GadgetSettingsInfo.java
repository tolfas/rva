package com.risevision.common.client.info;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.CharacterData;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risevision.common.client.json.JSOModel;

public class GadgetSettingsInfo {
	
	private final String GADGET_SCRIPT_SHORT = "" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/gadgets.min.js\"></script>" +
			"";
	
	private final String GADGET_SCRIPT_LONG = "" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/globals.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/base.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/string.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/urlparams.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/config.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/auth.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/auth-init.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/json-native.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/io.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/onload.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/prefs.js\"></script>" +
//			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/setprefs.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/log.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/wpm.transport.js\"></script>" +
			"<script type=\"text/javascript\" language=\"javascript\" src=\"/gadgets/rpc.js\"></script>" +
			"";
	
	private final String GADGET_INNER_HTML = "" +
			"<html><head>" +
			"<style>" +
			"body,td,div,span,p{font-family:arial,sans-serif;}a {color:#0000cc;}a:visited {color:#551a8b;}a:active {color:#ff0000;}body{margin: 0px;padding: 0px;background-color:white;}" +
			"</style>" +
			"%script%" +
			"<script>" +
//			"	// internals\n" +
			"	gadgets.Prefs.setMessages_({});\n" +
			"	gadgets.Prefs.setDefaultPrefs_(%s1%);\n" +
//			"	// Custom addition to initialize of the Gadget\n" +
			"	gadgets.Prefs.setUrlParams_(\"%s0%\");\n" +
			"	gadgets.io.preloaded_=[];\n" +
			"	gadgets.rpc.setRelayUrl(\"..\", \"%parent%\");\n" +
//			"" +
			"</script>" +
//			"<script type=\"text/javascript\" language=\"javascript\" src=\"scripts/widgetScripts.js\"></script>" +
			"	%head%" +
			"</head>" +
			"<body>" +
			"	%body%" +
			"<script>gadgets.util.runOnLoadHandlers();</script>" +
			"</body>" +
			"</html>"
			;
	
	private static boolean useShortScript = true;
	
	private boolean isRiseGadget;
	private boolean isCustomUI;
	private String titleUrl;
	private String htmlContent = "", editorContent = "";
	
	private HashMap<String, GadgetSetting> gadgetSettings = new HashMap<String, GadgetSetting>();
	
	private String gadgetUrl;
	
	private String additionalParams;
	
	public static void useLongScript() {
		useShortScript = false;
	}
	
	public GadgetSettingsInfo(String gadgetUrl, String additionalParams, String gadgetXml) throws GadgetXmlParserError {
		this(gadgetUrl, additionalParams);
		
		setGadgetXml(gadgetXml);
	}		
	
	public GadgetSettingsInfo(String gadgetUrl, String additionalParams) {
		if (gadgetUrl != null)
			gadgetUrl = gadgetUrl.trim();
		this.gadgetUrl = gadgetUrl;
		this.additionalParams = additionalParams;
	}
	
	public void setAdditionalParams(String additionalParams) {
		this.additionalParams = additionalParams;
	}
	
	public String getAdditionalParams() {
		return additionalParams;
	}
	
	public void setGadgetXml(String gadgetXml) throws GadgetXmlParserError {
		parseGadgetXml(gadgetXml);
		
		parseUrl();
	}
			
	private void parseGadgetXml(String gadgetXml) throws GadgetXmlParserError {
		Document d;
		try {
			d = XMLParser.parse(gadgetXml);
		} catch (Exception e) {
			throw new GadgetXmlParserError("Error parsing XML");
		}
		NodeList nodeList;
		NamedNodeMap attrMap;
		//check if Rise gadget
		isRiseGadget = false;
		// check if it has Custom UI
		isCustomUI = false;
		
		// check if this is a Gadget
		nodeList = d.getElementsByTagName("Module");
		if (nodeList.getLength() == 0)
			throw new GadgetXmlParserError("Error: Invalid XML file. <Module> tag is missing.");
		
		// check if Gadget has ModulePrefs and then parse them
		nodeList = d.getElementsByTagName("ModulePrefs");
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if ("ModulePrefs".equals(node.getNodeName())) {
				titleUrl = getNodeValue(node.getAttributes(), "title_url");
			}
		}
		
		if (nodeList.getLength() > 0) {
			Node featureNode = findNodeWithAttribute(nodeList.item(0).getChildNodes(), "Optional", "feature", "rise-api");
			if ((featureNode != null) && (featureNode.getChildNodes().getLength() > 0))
				isRiseGadget = (null != findNodeWithAttribute(featureNode.getChildNodes(), "Param", "name", "reports_ready"));
//			attrMap = nodeList.item(0).getAttributes();
//			isRiseGadget = "yes".equals(getNodeValue(attrMap,"reports_ready"));
		}
		
		nodeList = d.getElementsByTagName("Content");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			attrMap = node.getAttributes();

			if (getNodeValue(attrMap, "view").equals("editor")) {
				isCustomUI = true;
				editorContent = getCdataElement(node);
//				return;
			}
			else {
				htmlContent = getCdataElement(node);
			}
		}
		
		//parse UserPrefs
		nodeList = d.getElementsByTagName("UserPref");
		gadgetSettings.clear();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			attrMap = node.getAttributes();
			if (!(isRiseGadget && isRiseProperty(getNodeValue(attrMap,"name")))) {
				GadgetSetting gadgetSetting = new GadgetSetting();
				gadgetSetting.name = getNodeValue(attrMap,"name");
				gadgetSetting.displayName = getNodeValue(attrMap,"display_name");
				gadgetSetting.defaultValue = getNodeValue(attrMap,"default_value");
				gadgetSetting.datatype = getNodeValue(attrMap,"datatype");
				gadgetSetting.urlParam = getNodeValue(attrMap,"urlparam");
				gadgetSetting.required = getNodeValueBoolean(attrMap,"required");
				if ("enum".equals(gadgetSetting.datatype))
					gadgetSetting.enumValues = getNodeEnumValues(node);		
				gadgetSettings.put(gadgetSetting.name, gadgetSetting);
			}
		}
		
	}	
	
	private String getCdataElement(Node node) {
		String cdataContent = "";
		
		if (node.getNodeType() == Node.ELEMENT_NODE && node.hasChildNodes()) {
			XMLParser.removeWhitespace(node);
	
			NodeList nl = node.getChildNodes();
			for (int j = 0; j < nl.getLength(); j++) {
				Node childNode = nl.item(j);
	
				if (childNode.getNodeType() == Node.TEXT_NODE) {
					cdataContent += childNode.getNodeValue() + "\n";
				}
				else if (childNode.getNodeType() == Node.CDATA_SECTION_NODE) {
					CharacterData cd = (CharacterData) childNode;
					cdataContent += cd.getData() + "\n";
				}
			}
		}
		
		return cdataContent;
	}
		
	private Node findNodeWithAttribute(NodeList nodeList, String nodeName, String parameterName, String parameterValue) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (nodeName.equals(node.getNodeName())) {
				NamedNodeMap attrMap = node.getAttributes();
				if (parameterValue.equals(getNodeValue(attrMap, parameterName))) {
					return node;
				}
			}
		}
		return null;
	}

	private boolean isRiseProperty(String propertyName) {
		String[] sa = {"rsA","rsS"};
		for (String s:sa)
			if (s.equals(propertyName))
				return true;
		return false;
	}

	private String getNodeValue(NamedNodeMap attrMap, String name) {
		Node node = attrMap.getNamedItem(name);
		if (node != null)
			return node.getNodeValue();
		else
			return "";
	}

	private boolean getNodeValueBoolean(NamedNodeMap attrMap, String name) {
		Node node = attrMap.getNamedItem(name);
		if (node != null)
			return ("true".equals(node.getNodeValue()));
		else
			return false;
	}

	private ArrayList<GadgetSettingEnumValue> getNodeEnumValues(Node node) {
		ArrayList<GadgetSettingEnumValue> result = new ArrayList<GadgetSettingEnumValue>();

		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node2 = nodeList.item(i);
				if ((node2 != null)
						&& ("EnumValue".equals(node2.getNodeName()))) {
					NamedNodeMap attrMap = node2.getAttributes();
					result.add(new GadgetSettingEnumValue(getNodeValue(attrMap, "value"), getNodeValue(attrMap, "display_value")));
				}
			}
		}
		return result;
	}

	public class GadgetSetting  {
		private static final String DATATYPE_HIDDEN = "hidden";
		private String name = "";
		private String displayName = "";
		private String defaultValue = "";
		private String urlParam = "";
		private String datatype = "";
		private boolean required;
		private ArrayList<GadgetSettingEnumValue> enumValues;
		//helper properties
		private String value = null;
		private Widget widget = null;
		
		private GadgetSetting() {
			
		}
		
		private GadgetSetting(String name, String value) {
			this.name = name;
			this.value = value;
			
			this.datatype = DATATYPE_HIDDEN;
		}
		
		public boolean isVisible() {
			return !DATATYPE_HIDDEN.equals(datatype);
		}
		
		public String getValue() {
			return (value != null) ? value : defaultValue;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getDecodedValue() {
			String res = getValue();
			try {
				res = URL.decodeQueryString(res);
			} catch (Exception ex) { }
			return res;			
		}
		
		public String getHeader() {
			return displayName + (required ? "*" : "") + ":";
		}

		public String getName() {
			return name;
		}

		public String getDisplayName() {
			return displayName;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public String getUrlParam() {
			return urlParam;
		}

		public String getDatatype() {
			return datatype;
		}

		public boolean isRequired() {
			return required;
		}

		public ArrayList<GadgetSettingEnumValue> getEnumValues() {
			return enumValues;
		}

		public Widget getWidget() {
			return widget;
		}

		public void setWidget(Widget widget) {
			this.widget = widget;
		}

	}
	
	public class GadgetSettingEnumValue  {
		String value = "";
		String displayValue = "";

		public GadgetSettingEnumValue(String value, String displayValue) {
			this.value = value;
			this.displayValue = displayValue;
		}

		public String getValue() {
			return value;
		}

		public String getDisplayValue() {
			return displayValue;
		}

	}
	
	@SuppressWarnings("serial")
	public class GadgetXmlParserError extends Exception {
		public GadgetXmlParserError(String errorString) {
			super(errorString);
		}
	}

	public boolean isCustomUI() {
		return isCustomUI;
	}
	
	public String getTitleUrl() {
		return titleUrl;
	}
	
	public ArrayList<GadgetSetting> getGadgetSettings() {
		return new ArrayList<GadgetSetting>(gadgetSettings.values());
	}
	
	private void parseUrl() {
		String xmlUrl = getGadgetXmlUrl();
		
		if (gadgetUrl.length() > xmlUrl.length()) {
			String gadgetUrlParams = gadgetUrl.substring(xmlUrl.length() + 1);
					 
			updateGadgetSettings(gadgetUrlParams);
		}
			
//		for (GadgetSetting g: gadgetSettings) {
//			g.setValue(urlParams.get("up_" + g.getName()));
//		}
	}
	
	public void updateGadgetSettings(String gadgetUrlParams) {
		try {

			String[] ray = gadgetUrlParams.split("&");
			for (int i = 0; i < ray.length; i++) {
				String[] substrRay = ray[i].split("=");
				
				if (substrRay.length > 0) {
					String name = substrRay[0], value = "";
					
					if (name.indexOf("up_") != -1) {
						name = name.substring(3);
					}
					
					if (substrRay.length == 2) {
						value = substrRay[1];
					}
	
					if (gadgetSettings.get(name) != null) {
						gadgetSettings.get(name).value = value;
					}
					else {
						GadgetSetting setting = new GadgetSetting(name, value);
						gadgetSettings.put(name, setting);
					}
				}
			}
		} catch(Exception ex) {}

	}
	
	public String getGadgetUrl() {
//		if (!isCustomUI) {
			return (getGadgetXmlUrl() + getGadgetUrlParams()).trim();
//		}
//		else {
//			return gadgetUrl;
//		}
	}
	
	private String getGadgetUrlParams() {
		String params = "";
		
		for (GadgetSetting setting: gadgetSettings.values()) {
			params += "&" + "up_" + setting.getName() + "=" + setting.getValue();
		}

		if (params.length() > 0)
			params = "&" + params.substring(1);
		
		return params;
	}
	
	public String getGadgetXmlUrl() {
		//handle improperly url that have "?"
		String res = gadgetUrl.replaceFirst("\\?", "&");
		//get the gadget URL part
		int queryParamsStartPos = res.indexOf('&');
		if (queryParamsStartPos > 0)
			res = gadgetUrl.substring(0, queryParamsStartPos);
		return res;
	}
	
	public String getGadgetHtml() {
		return getGadgetHtml(false);
	}
	
	public String getGadgetHtml(boolean isEditor) {
		String html;
		String gadgetHtml = isEditor ? editorContent : htmlContent;
		String gadgetHead;
		String gadgetDefaultSettings = getGadgetSettingsJsonString();
		String gadgetUrlParams = getGadgetUrlParams();
		
		// Gadget XML needs to be parsed and parameters in the HTML updated
		// format: __UP_text_font-style__
		for (GadgetSetting setting: getGadgetSettings()) {
			gadgetHtml = gadgetHtml.replaceAll("__UP_" + setting.getName() + "__", setting.getDecodedValue());
		}
		
		int divider = getGadgetHeadDivider(gadgetHtml);
		gadgetHead = gadgetHtml.substring(0, divider);
		gadgetHtml = gadgetHtml.substring(divider);
		
		html = GADGET_INNER_HTML
				.replace("%script%", useShortScript ? GADGET_SCRIPT_SHORT : GADGET_SCRIPT_LONG)
				.replace("%s0%", gadgetUrlParams)
				.replace("%s1%", gadgetDefaultSettings)
				.replace("%head%", gadgetHead)
				.replace("%body%", gadgetHtml)
				.replace("%parent", Window.Location.getHref());
		
		return html;
	}
	
	private int getGadgetHeadDivider(String gadgetHtml) {
		final String SCRIPT_TAG = "script";
		final String STYLE_TAG = "style";
		
		int divider = 0;
		
		while (gadgetHtml.indexOf("<", divider) != -1) {
			int tag = gadgetHtml.indexOf("<", divider);
			
			if (gadgetHtml.indexOf(SCRIPT_TAG, tag) == (tag + 1) ||
					gadgetHtml.indexOf(STYLE_TAG, tag) == (tag + 1)) {
				tag = gadgetHtml.indexOf("</", tag);
				divider = gadgetHtml.indexOf(">", tag) + 1;
			}
			else {
				break;
			}
		}
		
		return divider;
	}
	
	private String getGadgetSettingsJsonString() {
		ArrayList<JSOModel> values = new ArrayList<JSOModel>();
		
		for (GadgetSetting setting: gadgetSettings.values()) {
			JSOModel value = JSOModel.create();
			value.set(setting.getName(), setting.getDefaultValue());
			values.add(value);
		}
		
		String response = JSOModel.arrayToString(values); 
		if (response.charAt(0) == '[') response = response.substring(1);
		if (response.charAt(response.length() - 1) == ']') response = response.substring(0, response.length() - 1);
		
		return response;
	}
	
}
