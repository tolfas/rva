package com.risevision.common.client.json;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.risevision.common.client.info.PlaylistItemInfo;
import com.risevision.common.client.info.PresentationInfo;
import com.risevision.common.client.info.PlaceholderInfo;
import com.risevision.common.client.utils.RiseUtils;

public class DistributionJsonParser {
	
	public static boolean parseDistributionData(PresentationInfo presentation) {	
		try {		
			if (presentation.getDistributionString() != null && !presentation.getDistributionString().isEmpty()) {
//				String distributionString = presentation.getDistributionString().replace(",,", ",");
//				distributionString = distributionString.replace("[,", "[");
//				distributionString = distributionString.replace(",]", "]");		
//				JsArray<JSOModel> jsDistribution = JSOModel.arrayFromJson(distributionString);
				
				JsArray<JSOModel> jsDistribution = JSOModel.arrayFromJson(presentation.getDistributionString());
		
				parseJsDistribution(presentation, jsDistribution);
			}
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}
	
	private static void parseJsDistribution(PresentationInfo presentation, JsArray<JSOModel> jsDistribution) {	
		if (jsDistribution != null) {	
			for (int i = 0; i < jsDistribution.length(); i++) {
				String id = jsDistribution.get(i).get("itemId");
				
				if (id != null && !id.isEmpty()) {
					String[] tokens = id.split("#");
					ArrayList<String> distribution = jsDistribution.get(i).getArrayList("displayIds");
					
					if (tokens.length > 0) {
						String placeholderId = tokens[0];
						
						for (PlaceholderInfo placeholder: presentation.getPlaceholders()) {
							if (placeholder.getId().equals(placeholderId)) {
								if (tokens.length == 1) {
									placeholder.setDistribution(distribution);
									placeholder.setDistributionToAll(distribution == null);
								}
								else {
									int itemNumber = RiseUtils.strToInt(tokens[1], -1);
									if (itemNumber != -1 && itemNumber < placeholder.getPlaylistItems().size()) {
										placeholder.getPlaylistItems().get(itemNumber).setDistribution(distribution);
										placeholder.getPlaylistItems().get(itemNumber).setDistributionToAll(distribution == null);
									}
								}
								break;
							}
						}
					}
				}	
			}
		}
	}
	
	public static boolean updateDistributionData(PresentationInfo presentation) {
		try {
			ArrayList<JSOModel> jsDistribution = updateJsDistribution(presentation);
			
			presentation.setDistributionString(JSOModel.arrayToString(jsDistribution));
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private static ArrayList<JSOModel> updateJsDistribution(PresentationInfo presentation) {
		ArrayList<JSOModel> jsDistribution = new ArrayList<JSOModel>();
		
		if (presentation.getPlaceholders() != null) {
			for (PlaceholderInfo placeholder : presentation.getPlaceholders()) {
				JSOModel phDistribution = createJsDistribution(placeholder.getId(), placeholder.getDistribution(), placeholder.getDistributionToAll());
				if (phDistribution != null) {
					jsDistribution.add(phDistribution);
				}
				
				if (placeholder.getPlaylistItems() != null) {
					for (PlaylistItemInfo playlistItem: placeholder.getPlaylistItems()) {
						String id = placeholder.getId() + "#" + placeholder.getPlaylistItems().indexOf(playlistItem);
						JSOModel pliDistribution = createJsDistribution(id, playlistItem.getDistribution(), playlistItem.getDistributionToAll());
						if (pliDistribution != null) {
							jsDistribution.add(pliDistribution);
						}
					}
				}
			}
		}
		
		return jsDistribution;
	}
	
	private static JSOModel createJsDistribution(String id, ArrayList<String> distribution, boolean distributeToAll) {
		if (distributeToAll) {
			return null;
		}
		else {
			JSOModel jsDistribution = JSOModel.create();
			jsDistribution.set("itemId", id);

			if (distribution == null) {
				jsDistribution.set("displayIds", new ArrayList<String>());
			} else {
				jsDistribution.set("displayIds", distribution);				
			}
			
			return jsDistribution;
		}
	}
}
