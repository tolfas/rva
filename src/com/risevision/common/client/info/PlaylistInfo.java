package com.risevision.common.client.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.risevision.common.client.utils.Global;

@SuppressWarnings("serial")
public class PlaylistInfo implements Serializable{

//	public static final String ID = "id";
//	public static final String NAME = "name";
	
	protected String id;
	protected String name;
	private TimeLineInfo timeline;
	private boolean distributionToAll = true;
	private ArrayList<String> distribution;

	protected String transition = "none";
	protected String scale = Global.INHERITED;
	protected String position = Global.INHERITED;
	
	private boolean listChanged;
//	private String parentId;

	private ArrayList<PlaylistItemInfo> playlistItems;

	private String changedBy;
	private Date changeDate;

	public PlaylistInfo() {		
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

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public String getTransition() {
		return transition;
	}

	public void setTransition(String transition) {
		this.transition = transition;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setPlaylistItems(ArrayList<PlaylistItemInfo> playlistItems) {
		this.playlistItems = playlistItems;
	}

	public ArrayList<PlaylistItemInfo> getPlaylistItems() {
		return playlistItems;
	}

	public void setDistribution(ArrayList<String> distribution) {
		this.distribution = distribution;
	}

	public ArrayList<String> getDistribution() {
		return distribution;
	}

	public void setDistributionToAll(boolean distributionToAll) {
		this.distributionToAll = distributionToAll;
	}

	public boolean getDistributionToAll() {
		return distributionToAll;
	}

	public void setTimeline(TimeLineInfo timeline) {
		this.timeline = timeline;
	}

	public TimeLineInfo getTimeline() {
		return timeline;
	}

	public void setListChanged(boolean listChanged) {
		this.listChanged = listChanged;
	}

	public boolean isListChanged() {
		return listChanged;
	}

//	public void setParentId(String parentId) {
//		this.parentId = parentId;
//	}
//
//	public String getParentId() {
//		return parentId;
//	}

}