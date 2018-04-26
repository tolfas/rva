package com.risevision.common.client.json;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.risevision.common.client.attribute.PlaylistItemAttribute;
import com.risevision.common.client.info.PlaylistItemInfo;

public class PlaylistItemJsonParser {
	
	public static ArrayList<PlaylistItemInfo> parsePlaylistItems(String jsonString) {	
		try {		
			if (jsonString != null && !jsonString.isEmpty()) {
				JsArray<JSOModel> jsPlaylistItems = JSOModel.arrayFromJson(jsonString);
				ArrayList<PlaylistItemInfo> playlistItems = new ArrayList<PlaylistItemInfo>();
				
				if (jsPlaylistItems != null) {
					for (int i = 0; i < jsPlaylistItems.length(); i++) {
						PlaylistItemInfo item = new PlaylistItemInfo();
						parseJsPlaylistItem(item, jsPlaylistItems.get(i));
						playlistItems.add(item);
					}
				}
				
				return playlistItems;
			}
		}
		catch (Exception e) {
			return null;
		}

		return null;
	}
	
	public static String updatePlaylistItems(ArrayList<PlaylistItemInfo> playlistItems) {
		try {		
			ArrayList<JSOModel> jsPlaylistItems = new ArrayList<JSOModel>();
			
			for (PlaylistItemInfo item : playlistItems) {
				jsPlaylistItems.add(updateJsPlaylistItem(item));			
			}
			
			return JSOModel.arrayToString(jsPlaylistItems);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/*
	private static PlaylistItemInfo parseJsPlaylistItem(JSOModel jsItem) {
		PlaylistItemInfo playlistItem = new PlaylistItemInfo();
		
//		"changeDate":"22032010192830055",
		
		playlistItem.setDistributionToAll(jsItem.getBoolean(PlaylistItemAttribute.DISTRIBUTE_TO_ALL));
		playlistItem.setDistribution(jsItem.getArrayList(PlaylistItemAttribute.DISTRIBUTION));
		playlistItem.setId(jsItem.get(PlaylistItemAttribute.INDEX));
//		playlistItem.setPosition(jsItem.get(PlaylistItemAttribute.POSITION));
//		playlistItem.setScale(jsItem.get(PlaylistItemAttribute.SCALE));
		playlistItem.setTimeline(TimelineJsonParser.parseTimeline(jsItem));
//		playlistItem.setTransition(jsItem.get(PlaylistItemAttribute.TRANSITION));
		
		playlistItem.setDuration(jsItem.get(PlaylistItemAttribute.DURATION));
		playlistItem.setType(jsItem.get(PlaylistItemAttribute.TYPE));
		playlistItem.setObjectData(jsItem.get(PlaylistItemAttribute.URL));
		playlistItem.setObjectRef(jsItem.get(PlaylistItemAttribute.OBJECT_REFERENCE));
		
		return playlistItem;
	}
	*/
	
	public static void parseJsPlaylistItem(PlaylistItemInfo playlistItem, JSOModel jsItem) {
		
		playlistItem.setName(jsItem.get(PlaylistItemAttribute.NAME));
//		playlistItem.setItemName(jsItem.get(PlaylistItemInfo.ITEM_NAME));
//		if ("undefined".equals(playlistItem.getItemName())) {
//			playlistItem.setItemName(playlistItem.getName());
//		}
		
		if (jsItem.get(PlaylistItemAttribute.DISTRIBUTION) != null) {
			if (jsItem.get(PlaylistItemAttribute.DISTRIBUTE_TO_ALL) != null) {
				playlistItem.setDistributionToAll(jsItem.getBoolean(PlaylistItemAttribute.DISTRIBUTE_TO_ALL));
			}
			playlistItem.setDistribution(jsItem.getArrayList(PlaylistItemAttribute.DISTRIBUTION));
		}
		else if (playlistItem.getDistribution() == null) {
			playlistItem.setDistribution(new ArrayList<String>());
		}
		
		playlistItem.setId(jsItem.get(PlaylistItemAttribute.INDEX));
//		playlistItem.setPosition(jsItem.get(PlaylistItemAttribute.POSITION));
//		playlistItem.setScale(jsItem.get(PlaylistItemAttribute.SCALE));
		playlistItem.setTimeline(TimelineJsonParser.parseTimeline(jsItem));
//		playlistItem.setTransition(jsItem.get(PlaylistItemAttribute.TRANSITION));
		
		playlistItem.setDuration(jsItem.get(PlaylistItemAttribute.DURATION));
		playlistItem.setPlayUntilDone(jsItem.getBoolean(PlaylistItemAttribute.PLAY_UNTIL_DONE));
		playlistItem.setType(jsItem.get(PlaylistItemAttribute.TYPE));
		playlistItem.setObjectRef(jsItem.get(PlaylistItemAttribute.OBJECT_REFERENCE));
		
		playlistItem.setObjectData(jsItem.get(PlaylistItemAttribute.URL));
		if (playlistItem.getObjectData() == null) {
			playlistItem.setObjectData(jsItem.get(PlaylistItemAttribute.OBJECT_DATA, ""));
		}
		playlistItem.setAdditionalParams(jsItem.get(PlaylistItemAttribute.ADDITIONAL_PARAMS));

	}
	
	public static JSOModel updateJsPlaylistItem(PlaylistItemInfo playlistItem) {
		JSOModel jsItem = JSOModel.create();
		
		jsItem.set(PlaylistItemAttribute.NAME, playlistItem.getName());
//		if (playlistItem.getItemName() == null || playlistItem.getItemName().isEmpty()) {
//			jsItem.set(PlaylistItemInfo.ITEM_NAME, playlistItem.getName());
//		}
//		else {
//			jsItem.set(PlaylistItemInfo.ITEM_NAME, playlistItem.getItemName());
//		}
		
		jsItem.set(PlaylistItemAttribute.DURATION, playlistItem.getDuration());
		jsItem.set(PlaylistItemAttribute.TYPE, playlistItem.getType());
		jsItem.set(PlaylistItemAttribute.OBJECT_REFERENCE, playlistItem.getObjectRef());
		
		if (!playlistItem.isPlaceholderItem()) {
			jsItem.set(PlaylistItemAttribute.DISTRIBUTE_TO_ALL, playlistItem.getDistributionToAll());
			jsItem.set(PlaylistItemAttribute.DISTRIBUTION, playlistItem.getDistribution());
		}
		else {
			jsItem.set(PlaylistItemAttribute.INDEX, playlistItem.getId());
			jsItem.set(PlaylistItemAttribute.PLAY_UNTIL_DONE, playlistItem.isPlayUntilDone());
			jsItem.set(PlaylistItemAttribute.OBJECT_DATA, playlistItem.getObjectData());
			jsItem.set(PlaylistItemAttribute.ADDITIONAL_PARAMS, playlistItem.getAdditionalParams());
		}

		TimelineJsonParser.updateTimeline(jsItem, playlistItem.getTimeline());
		
//		jsItem.set(PlaylistItemAttribute.POSITION, playlistItem.getPosition());
//		jsItem.set(PlaylistItemAttribute.SCALE, playlistItem.getScale());
//		jsItem.set(PlaylistItemAttribute.TRANSITION, playlistItem.getTransition());
		
//		jsItem.set(PlaylistItemAttribute.URL, playlistItem.getUrl());		
		
		return jsItem;
	}
}
