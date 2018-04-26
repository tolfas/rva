package com.risevision.common.client.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
public class ScheduleInfo implements Serializable{

//	public static final String ID = "id";
//	public static final String NAME = "name";
	
	protected String id;
	private String companyId;
	protected String name;
	private TimeLineInfo timeline;
	private boolean distributionToAll;
	private ArrayList<String> distribution;

//	protected String transition = "none";
//	protected String scale = "fit";
//	protected String position = "mc";

	private String content;
	private ArrayList<PlaylistItemInfo> playListItems;

	private String changedBy;
	private Date changeDate;

	public ScheduleInfo() {		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setPlayListItems(ArrayList<PlaylistItemInfo> playListItems) {
		this.playListItems = playListItems;
	}

	public ArrayList<PlaylistItemInfo> getPlayListItems() {
		return playListItems;
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

}