package com.risevision.common.client.info;

import java.util.HashMap;

import com.google.gwt.http.client.URL;

public class ImageItemInfo {
	private String url = "";
	private String backgroundColor = "";
	private boolean scaleToFit;
	
	public ImageItemInfo(String gadgetUrl) {
		parseUrl(gadgetUrl);
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public boolean isScaleToFit() {
		return scaleToFit;
	}

	public void setScaleToFit(boolean scaleToFit) {
		this.scaleToFit = scaleToFit;
	}

	private void parseUrl(String gadgetUrl) {
		try {
			HashMap<String, String> urlParams = new HashMap<String, String>();

//			String xmlUrl = getGadgetXmlUrl();
//			String[] ray = gadgetUrl.substring(xmlUrl.length()+1).split("&");
			String[] ray = gadgetUrl.split("&");
			for (int i = 0; i < ray.length; i++) {
				String[] substrRay = ray[i].split("=");
				if (substrRay.length == 2)
					urlParams.put(substrRay[0], substrRay[1]);
			}
		
			if (urlParams.containsKey("up_url")) {
				url = URL.decodeQueryString(urlParams.get("up_url"));
			}
			
			if (urlParams.containsKey("up_backgroundColor")) {
				backgroundColor = URL.decodeQueryString(urlParams.get("up_backgroundColor"));
			}
			
			if (urlParams.containsKey("up_scaleToFit")) {
				scaleToFit = Boolean.parseBoolean(urlParams.get("up_scaleToFit"));
			}
		} 
		catch(Exception ex) {}
	}
	
	public String buildUrl() {
		String gadgetUrl;
		
		gadgetUrl = "up_url=" + URL.encodeQueryString(url);
		gadgetUrl += "&up_backgroundColor=" + URL.encodeQueryString(backgroundColor);
		gadgetUrl += "&up_scaleToFit=" + Boolean.toString(scaleToFit);
		
		return gadgetUrl;
	}
}
