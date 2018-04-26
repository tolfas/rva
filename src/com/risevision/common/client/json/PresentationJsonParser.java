package com.risevision.common.client.json;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.risevision.common.client.attribute.PlaylistItemAttribute;
import com.risevision.common.client.info.PlaylistItemInfo;
import com.risevision.common.client.info.PresentationInfo;
import com.risevision.common.client.info.PlaceholderInfo;
import com.risevision.core.api.attributes.PresentationAttribute;

public class PresentationJsonParser {
	
	public static boolean parsePresentationData(PresentationInfo presentation, String jsonString) {	
		try {		
			if (jsonString != null && !jsonString.isEmpty()) {
				JSOModel jsoModel = JSOModel.fromJson(jsonString);
		
				parsePresentationData(presentation, jsoModel);
			}
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}
	
	private static void parsePresentationData(PresentationInfo presentation, JSOModel object) {	
		if (object != null) {	
			JSOModel jsPresentation = object.getObject("presentationData");
			
			if (jsPresentation != null) {
				parseJsPresentation(presentation, jsPresentation);
			}
		}
	}
	
	public static String updatePresentationData(PresentationInfo presentation) {
		try {
			JSOModel jsPresentation = updateJsPresentation(presentation);
			
			JSOModel jsObject = JSOModel.create();
			jsObject.set("presentationData", jsPresentation);

			return jsObject.getAsStringBeautified();
		}
		catch (Exception e) {
			return null;
		}
	}

	private static void parseJsPresentation(PresentationInfo presentation, JSOModel jsPresentation) {
//		presentation.setId(jsPresentation.get(PresentationAttribute.ID));
		presentation.setHidePointer(jsPresentation.getBoolean(PresentationInfo.POINTER, true));
		presentation.setDonePlaceholder(jsPresentation.get(PresentationInfo.DONE_PLACEHOLDER));
			
		JsArray<JSOModel> jsPlaceholders = jsPresentation.getArray("placeholders");
		
		if (jsPlaceholders != null) {
			if (presentation.getPlaceholders() == null) {
				presentation.setPlaceholders(new ArrayList<PlaceholderInfo>());
			}
			
			for (int i = 0; i < jsPlaceholders.length(); i++) {
				String id = jsPlaceholders.get(i).get(PlaylistItemAttribute.ID);
				
				PlaceholderInfo currentPlaceholder = null;
				if (id != null && !id.isEmpty()) {
					for (PlaceholderInfo placeholder : presentation.getPlaceholders()) {
						if (id.equals(placeholder.getId())) {
							currentPlaceholder = placeholder;
							break;
						}
					}
				}
				
				if (currentPlaceholder == null) {
					currentPlaceholder = new PlaceholderInfo();
					presentation.getPlaceholders().add(currentPlaceholder);
				}

				parseJsPlaceholder(currentPlaceholder, jsPlaceholders.get(i));
			}
			
		}
	}
	
	private static JSOModel updateJsPresentation(PresentationInfo presentation) {
		JSOModel jsPresentation = JSOModel.create();
		
		jsPresentation.set(PresentationAttribute.ID, presentation.getId());
		jsPresentation.set(PresentationInfo.POINTER, presentation.getHidePointer());
		jsPresentation.set(PresentationInfo.DONE_PLACEHOLDER, presentation.getDonePlaceholder());
		
		if (presentation.getPlaceholders() != null) {
			ArrayList<JSOModel> jsPlaceholders = new ArrayList<JSOModel>();
			for (PlaceholderInfo item : presentation.getPlaceholders()) {
				jsPlaceholders.add(updateJsPlaceholder(item));			
			}

			jsPresentation.setArray("placeholders", jsPlaceholders);
		}
		
		return jsPresentation;
	}
	
	private static void parseJsPlaceholder(PlaceholderInfo placeholder, JSOModel jsItem) {		
		
		placeholder.setId(jsItem.get(PlaylistItemAttribute.ID));
		
		if (placeholder.getId() == null || placeholder.getId().isEmpty() || placeholder.getId().equals("undefined")) {
			placeholder.setId("ph" + jsItem.get("index"));
		}
		
		placeholder.setType(jsItem.get(PlaylistItemAttribute.TYPE));
//		placeholder.setObjectRef(jsItem.get(PlaceholderAttribute.OBJECT_REFERENCE));
		placeholder.setObjectRef("");

		if (jsItem.get(PlaylistItemAttribute.DISTRIBUTION) != null) {
			if (jsItem.get(PlaylistItemAttribute.DISTRIBUTE_TO_ALL) != null) {
				placeholder.setDistributionToAll(jsItem.getBoolean(PlaylistItemAttribute.DISTRIBUTE_TO_ALL));
			}
			placeholder.setDistribution(jsItem.getArrayList(PlaylistItemAttribute.DISTRIBUTION));
		}
		else if (placeholder.getDistribution() == null) {
			placeholder.setDistribution(new ArrayList<String>());
		}
		
		placeholder.setId(jsItem.get(PlaylistItemAttribute.ID));
//		placeholder.setPosition(jsItem.get(ScheduleAttribute.POSITION));
//		placeholder.setScale(jsItem.get(ScheduleAttribute.SCALE));
		placeholder.setTimeline(TimelineJsonParser.parseTimeline(jsItem));
		
		placeholder.setVisible(jsItem.getBoolean(PlaylistItemAttribute.VISIBILITY, true));
		
		String transition = jsItem.get(PlaylistItemAttribute.TRANSITION);
		boolean found = false;
		for (String[] s: PlaceholderInfo.TRANSITION_TYPES) {
			if (s[1].equals(transition)) {
				found = true;
				break;
			}
		}
		
		if (!found || transition == null || transition.isEmpty()) {
			transition = PlaceholderInfo.TRANSITION_TYPES[0][1];
		}
		
		placeholder.setTransition(transition);		
		
		JsArray<JSOModel> jsPlaylistItems = jsItem.getArray("items");
		
//		if (jsPlaylistItems != null) {
//			ArrayList<PlaylistItemInfo> playlistItems = new ArrayList<PlaylistItemInfo>();
//			for (int i = 0; i < jsPlaylistItems.length(); i++) {
//				PlaylistItemInfo item = parseJsPlaylistItem(jsPlaylistItems.get(i));
//				
////				if (item.getDistributionToAll() || ViewerEntryPoint.checkDistribution(item.getDistribution())) {
//					playlistItems.add(item);
////				}
//			}
//			
//			placeholder.setPlaylistItems(playlistItems);
//		}
				
		placeholder.setPlaylistItems(new ArrayList<PlaylistItemInfo>());
		
		if (jsPlaylistItems != null) {
			for (int i = 0; i < jsPlaylistItems.length(); i++) {
//				String id = jsPlaylistItems.get(i).get(PlaylistItemAttribute.INDEX);
				
				PlaylistItemInfo currentPlaylistItem = null;
//				if (id != null && !id.isEmpty()) {
//					for (PlaylistItemInfo playlistItem : placeholder.getPlaylistItems()) {
//						if (id.equals(playlistItem.getId())) {
//							currentPlaylistItem = playlistItem;
//							break;
//						}
//					}
//				}
				
				if (currentPlaylistItem == null) {
					currentPlaylistItem = new PlaylistItemInfo();
					placeholder.getPlaylistItems().add(currentPlaylistItem);
				}

				PlaylistItemJsonParser.parseJsPlaylistItem(currentPlaylistItem, jsPlaylistItems.get(i));
				currentPlaylistItem.setPlaceholderItem(true);
			}
			
		}
	}

	private static JSOModel updateJsPlaceholder(PlaceholderInfo placeholder) {
		JSOModel jsPlaceholder = JSOModel.create();
		
		jsPlaceholder.set(PlaylistItemAttribute.ID, placeholder.getId());
		
		jsPlaceholder.set(PlaylistItemAttribute.TYPE, placeholder.getType());
//		jsPlaceholder.set(PlaceholderAttribute.OBJECT_REFERENCE, placeholder.getObjectRef());
		
//		jsPlaceholder.set(ScheduleAttribute.DISTRIBUTE_TO_ALL, placeholder.getDistributionToAll());
//		jsPlaceholder.set(ScheduleAttribute.DISTRIBUTION, placeholder.getDistribution());
//		jsPlaceholder.set(ScheduleAttribute.POSITION, placeholder.getPosition());
//		jsPlaceholder.set(ScheduleAttribute.SCALE, placeholder.getScale());
		
		TimelineJsonParser.updateTimeline(jsPlaceholder, placeholder.getTimeline());
		
		jsPlaceholder.set(PlaylistItemAttribute.VISIBILITY, placeholder.isVisible());
		
		jsPlaceholder.set(PlaylistItemAttribute.TRANSITION, placeholder.getTransition());
		
		if (placeholder.getPlaylistItems() != null) {
			ArrayList<JSOModel> jsPlaylistItems = new ArrayList<JSOModel>();
			for (PlaylistItemInfo item : placeholder.getPlaylistItems()) {
				jsPlaylistItems.add(PlaylistItemJsonParser.updateJsPlaylistItem(item));			
			}

			jsPlaceholder.setArray("items", jsPlaylistItems);
		}
		
		return jsPlaceholder;
	}

}
