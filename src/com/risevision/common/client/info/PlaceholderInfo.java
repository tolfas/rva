package com.risevision.common.client.info;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PlaceholderInfo implements Serializable {
	public static final String TYPE_GADGET = "gadget";
	public static final String TYPE_PLAYLIST = "playlist";
	public static final String TYPE_PRESENTATION = "presentation";
	
	public static final String[][] TRANSITION_TYPES = 
					{{"None", "none"},
					{"Fade", "fade"},
//					{"Iris", "iris"},
					{"Slide Right", "slide-right"},
					{"Slide Left", "slide-left"},
					{"Slide Up", "slide-up"},
					{"Slide Down", "slide-down"}};	
	
	private String id;
	private String newId;
//	private String name;
	private double height;
	private String heightUnits;
	private double width;
	private String widthUnits;
	private double top;
	private String topUnits;
	private double left;
	private String leftUnits;
	private int zIndex;
	private String type;
	private String objectRef;
	
	// from Playlist
	private TimeLineInfo timeline;
	private boolean distributionToAll = true;
	private ArrayList<String> distribution;
	
	private boolean isVisible = true;

	private String transition = "none";
//	private String scale = Global.INHERITED;
//	private String position = Global.INHERITED;
	private String backgroundStyle;
	private boolean backgroundScaleToFit;
	
	// Parameter identifying if this placeholder in fact contains placeholder="true" parameter
//	private boolean isPlaceholder = true;
	
	private ArrayList<PlaylistItemInfo> playlistItems;
	
//	private PlaylistInfo playlist;
//	private String url;
	
	private boolean deleted;
	
	public PlaceholderInfo(double height, double width) {
		this();
		this.height = height;
		this.width = width;
	}
	
	public PlaceholderInfo() {
		this.setType(TYPE_PLAYLIST);
		
		this.setHeightUnits("px");
		this.setWidthUnits("px");
		this.setTopUnits("px");
		this.setLeftUnits("px");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setNewId(String newId) {
		this.newId = newId;
	}

	public String getNewId() {
		return newId;
	}

//	public String getName() {
//		return name;
//	}
	
//	public void setName(String name) {
//		this.name = name;
//	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getHeightUnits() {
		return heightUnits;
	}

	public void setHeightUnits(String heightUnits) {
		this.heightUnits = heightUnits;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public String getWidthUnits() {
		return widthUnits;
	}

	public void setWidthUnits(String widthUnits) {
		this.widthUnits = widthUnits;
	}

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public String getTopUnits() {
		return topUnits;
	}

	public void setTopUnits(String topUnits) {
		this.topUnits = topUnits;
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public String getLeftUnits() {
		return leftUnits;
	}

	public void setLeftUnits(String leftUnits) {
		this.leftUnits = leftUnits;
	}

	public int getzIndex() {
		return zIndex;
	}

	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}

//	public String getUrl() {
//		return url;
//	}

//	public void setUrl(String url) {
//		this.url = url;
//	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setObjectRef(String objectRef) {
		this.objectRef = objectRef;
	}

	public String getObjectRef() {
		return objectRef;
	}

	public TimeLineInfo getTimeline() {
		return timeline;
	}

	public void setTimeline(TimeLineInfo timeline) {
		this.timeline = timeline;
	}

	public boolean getDistributionToAll() {
		return distributionToAll;
	}

	public void setDistributionToAll(boolean distributionToAll) {
		this.distributionToAll = distributionToAll;
	}

	public ArrayList<String> getDistribution() {
		return distribution;
	}

	public void setDistribution(ArrayList<String> distribution) {
		this.distribution = distribution;
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getTransition() {
		return transition;
	}

	public void setTransition(String transition) {
		this.transition = transition;
	}

//	public String getScale() {
//		return scale;
//	}

//	public void setScale(String scale) {
//		this.scale = scale;
//	}

//	public String getPosition() {
//		return position;
//	}

//	public void setPosition(String position) {
//		this.position = position;
//	}

	public void setBackgroundStyle(String backgroundStyle) {
		this.backgroundStyle = backgroundStyle;
	}

	public String getBackgroundStyle() {
		return backgroundStyle;
	}

	public boolean isBackgroundScaleToFit() {
		return backgroundScaleToFit;
	}

	public void setBackgroundScaleToFit(boolean backgroundScaleToFit) {
		this.backgroundScaleToFit = backgroundScaleToFit;
	}

	public ArrayList<PlaylistItemInfo> getPlaylistItems() {
		return playlistItems;
	}

	public void setPlaylistItems(ArrayList<PlaylistItemInfo> playlistItems) {
		this.playlistItems = playlistItems;
	}

//	public void setPlaceholder(boolean isPlaceholder) {
//		this.isPlaceholder = isPlaceholder;
//	}

//	public boolean isPlaceholder() {
//		return isPlaceholder;
//	}
	
	public void setPlaylist(PlaylistInfo playlist) {
//		this.playlist = playlist;
		
		this.playlistItems = playlist.getPlaylistItems();
		
		this.timeline = playlist.getTimeline();
		this.distributionToAll = playlist.getDistributionToAll();
		this.distribution = playlist.getDistribution();

		this.transition = playlist.getTransition();
//		this.scale = playlist.getScale();
//		this.position = playlist.getPosition();
		
//		isChanged = true;
	}

//	public PlaylistInfo getPlaylist() {
//		return playlist;
//	}
	
	public PlaceholderInfo copy() {
		PlaceholderInfo copy = new PlaceholderInfo();
		
		copy.setId(id);

		copy.setHeight(height);
		copy.setHeightUnits(heightUnits);
		copy.setWidth(width);
		copy.setWidthUnits(widthUnits);
		copy.setTop(top);
		copy.setTopUnits(topUnits);
		copy.setLeft(left);
		copy.setLeftUnits(leftUnits);
		copy.setzIndex(zIndex);
		copy.setType(type);
		copy.setObjectRef(objectRef);

		copy.setTimeline(timeline.copy());
		copy.setDistributionToAll(distributionToAll);

		ArrayList<String> newDistribution = new ArrayList<String>();
		for (String s: distribution) {
			newDistribution.add(s);
		}
		copy.setDistribution(newDistribution);

		copy.setTransition(transition);
//		copy.setScale(scale);
//		copy.setPosition(position);
		copy.setBackgroundStyle(backgroundStyle);

//		copy.setPlaceholder(isPlaceholder);
		
		ArrayList<PlaylistItemInfo> newPlaylistItems = new ArrayList<PlaylistItemInfo>();
		for (PlaylistItemInfo item: playlistItems) {
			newPlaylistItems.add(item.copy());
		}
		copy.setPlaylistItems(newPlaylistItems);
		
//		copy.setUrl(url);
		copy.setDeleted(deleted);
		
		return copy;
	}

}
