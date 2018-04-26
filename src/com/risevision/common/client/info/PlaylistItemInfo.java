package com.risevision.common.client.info;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PlaylistItemInfo implements Serializable{

//	public static final String ID = "id";
//	public static final String NAME = "name";
	public static final String ITEM_NAME = "itemName";	

	public static final String TYPE_URL = "url";
	public static final String TYPE_WIDGET = "widget";
	public static final String TYPE_PRESENTATION = "presentation";
	public static final String TYPE_GADGET = "gadget";
	public static final String TYPE_TEXT = "text";
	public static final String TYPE_IMAGE = "image";
	public static final String TYPE_VIDEO = "video";
	public static final String TYPE_HTML = "HTML";
	
	protected String id;
	protected String name;
//	private String itemName;
	private String duration = "10";
	private boolean playUntilDone = false;
//	private String url;
	private String type;
	private String objectRef;
	private String objectData;
	private String additionalParams;

	private TimeLineInfo timeline;
	
	private boolean distributionToAll = true;
	private ArrayList<String> distribution;
	
//	protected String transition = Global.INHERITED;
//	protected String scale = Global.INHERITED;
//	protected String position = Global.INHERITED;

//	private String changedBy;
//	private Date changeDate;
//	private boolean changed = false;
	
	private boolean isPlaceholderItem = false;

	private String productCode;
	private String subscriptionStatus;


	public PlaylistItemInfo() {		
	}

	public PlaylistItemInfo(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

//	public void setChangedBy(String changedBy) {
//		this.changedBy = changedBy;
//	}

//	public String getChangedBy() {
//		return changedBy;
//	}

//	public void setChangeDate(Date changeDate) {
//		this.changeDate = changeDate;
//	}

//	public Date getChangeDate() {
//		return changeDate;
//	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDuration() {
		return duration;
	}

//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public String getUrl() {
//		return url;
//	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

//	public String getTransition() {
//		return transition;
//	}

//	public void setTransition(String transition) {
//		this.transition = transition;
//	}

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

//	public void setChanged(boolean changed) {
//		this.changed = changed;
//	}

//	public boolean isChanged() {
//		return changed;
//	}

	public void setObjectRef(String objectRef) {
		this.objectRef = objectRef;
	}

	public String getObjectRef() {
		return objectRef;
	}

	public void setObjectData(String objectData) {
		this.objectData = objectData;
	}

	public String getObjectData() {
		return objectData;
	}
	
	public void setAdditionalParams(String additionalParams) {
		this.additionalParams = additionalParams;
	}
	
	public String getAdditionalParams() {
		return additionalParams;
	}

	public void setDistributionToAll(boolean distributionToAll) {
		this.distributionToAll = distributionToAll;
	}

	public boolean getDistributionToAll() {
		return distributionToAll;
	}

	public void setDistribution(ArrayList<String> distribution) {
		this.distribution = distribution;
	}

	public ArrayList<String> getDistribution() {
		return distribution;
	}

	public void setTimeline(TimeLineInfo timeline) {
		this.timeline = timeline;
	}

	public TimeLineInfo getTimeline() {
		return timeline;
	}

	public void setPlayUntilDone(boolean playUntilDone) {
		this.playUntilDone = playUntilDone;
	}

	public boolean isPlayUntilDone() {
		return playUntilDone;
	}

//	public void setItemName(String itemName) {
//		this.itemName = itemName;
//	}

//	public String getItemName() {
//		return itemName;
//	}

	public boolean showPlayUntilDone() {
		if (type != null && (type.equals(TYPE_GADGET) || type.equals(TYPE_VIDEO) || type.equals(TYPE_PRESENTATION)
				|| type.equals(TYPE_WIDGET))) {
			return true;
		}
		return false;
	}
	
	public boolean isPlaceholderItem() {
		return isPlaceholderItem;
	}

	public void setPlaceholderItem(boolean isPlaceholderItem) {
		this.isPlaceholderItem = isPlaceholderItem;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	
	public PlaylistItemInfo copy() {
		PlaylistItemInfo copy = new PlaylistItemInfo();
		
		copy.setId(id);
		copy.setName(name);
		copy.setDuration(duration);
		copy.setPlayUntilDone(playUntilDone);
		copy.setType(type);
		copy.setObjectRef(objectRef);
		copy.setObjectData(objectData);
		copy.setAdditionalParams(additionalParams);
		
		copy.setTimeline(timeline.copy());
		
		copy.setDistributionToAll(distributionToAll);
		ArrayList<String> newDistribution = new ArrayList<String>();
		for (String s: distribution) {
			newDistribution.add(s);
		}
		copy.setDistribution(newDistribution);
		
//		copy.setTransition(transition);
//		copy.setScale(scale);
//		copy.setPosition(position);
		
//		copy.setChanged(changed);
		
		copy.setPlaceholderItem(isPlaceholderItem);
		
		return copy;
	}

}