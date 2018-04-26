package com.risevision.common.client.info;

import java.util.HashMap;

import com.google.gwt.http.client.URL;

public class VideoItemInfo {
	private String videoUrl = "";
	private String videoExtension = "";
	private String videoVolume = "100";
	private boolean carryOn;
	private boolean autoHide = true;
	
	public VideoItemInfo(String gadgetUrl) {
		parseUrl(gadgetUrl);
	}
	
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	public String getVideoExtension() {
		return videoExtension;
	}
	
	public void setVideoExtension(String videoExtension) {
		this.videoExtension = videoExtension;
	}

	public String getVideoVolume() {
		return videoVolume;
	}

	public void setVideoVolume(String videoVolume) {
		this.videoVolume = videoVolume;
	}

	public boolean isCarryOn() {
		return carryOn;
	}

	public void setCarryOn(boolean carryOn) {
		this.carryOn = carryOn;
	}

	public boolean isAutoHide() {
		return autoHide;
	}

	public void setAutoHide(boolean autoHide) {
		this.autoHide = autoHide;
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
				videoUrl = URL.decodeQueryString(urlParams.get("up_url"));
			}
			
			if (urlParams.containsKey("up_extension")) {
				videoExtension = URL.decodeQueryString(urlParams.get("up_extension"));
			}
			
			if (urlParams.containsKey("up_volume")) {
				videoVolume = URL.decodeQueryString(urlParams.get("up_volume"));
			}
			
			if (urlParams.containsKey("up_carryOn")) {
				carryOn = Boolean.parseBoolean(urlParams.get("up_carryOn"));
			}
			
			if (urlParams.containsKey("up_autoHide")) {
				autoHide = Boolean.parseBoolean(urlParams.get("up_autoHide"));
			}

		} 
		catch(Exception ex) {}
	}
	
	public String buildUrl() {
		String gadgetUrl;
		
		gadgetUrl = "up_url=" + URL.encodeQueryString(videoUrl);
		gadgetUrl += "&up_extension=" + URL.encodeQueryString(videoExtension);
		gadgetUrl += "&up_volume=" + URL.encodeQueryString(videoVolume);
		gadgetUrl += "&up_carryOn=" + Boolean.toString(carryOn);
		gadgetUrl += "&up_autoHide=" + Boolean.toString(autoHide);
		
		return gadgetUrl;
	}
}
