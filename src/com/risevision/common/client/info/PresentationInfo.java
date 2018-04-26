package com.risevision.common.client.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.risevision.common.client.utils.RiseUtils;

@SuppressWarnings("serial")
public class PresentationInfo implements Serializable {

//	public static final String ID = "id";
//	public static final String NAME = "name";
	public static final String POINTER = "hidePointer";
	public static final String DONE_PLACEHOLDER = "donePlaceholder";
	
	public static final String STORE_PRODUCT = "isStoreProduct";
	
//	public static final int EVERYONE = 1;
//	public static final int SIGNED_IN_VIEWERS = 0;
	
	private static final String DEFAULT_LAYOUT = "" +
			"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
			"<html>\n" +
			"\t<head>\n" +
			"\t\t<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n" +
			"\t\t<title></title>\n" +
			"\t</head>\n" +
			"\n" +
			"\t<body style=\"width:1920px;height:1080px; margin: 0; overflow: hidden;\" >\n" +
			"\t</body>\n" +
			"</html>\n";
	
	private String id;
	private String companyId;
	private String name;

	private String layout;
//	private int publishType;
	private boolean template;
	private boolean storeProduct;
	private int revisionStatus;

	private String helpURL;
	
	private String distributionString;
	
	private String changedBy;
	private Date changeDate;

	private int height;
	private String heightUnits;
	private int width;
	private String widthUnits;
	
	private String backgroundStyle;
	private boolean backgroundScaleToFit;
	private boolean hidePointer;
	private String donePlaceholder;
	
	private ArrayList<PlaceholderInfo> placeholders = new ArrayList<PlaceholderInfo>();
	
	public PresentationInfo(){
		id = null;
		name = "New Presentation";
		layout = DEFAULT_LAYOUT;
//		setPublishType(PresentationPublishType.SIGNED_IN_VIEWERS);
		template = false;
		
		width = 1920;
		height = 1080;
		
		hidePointer = true;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

//	public int getPublishType() {
//		return publishType;
//	}
//
//	public void setPublishType(int publishType) {
//		this.publishType = publishType;
//	}

	public boolean isTemplate() {
		return template;
	}

	public void setTemplate(boolean template) {
		this.template = template;
	}
	
	public boolean isStoreProduct() {
		return storeProduct;
	}

	public void setStoreProduct(boolean storeProduct) {
		this.storeProduct = storeProduct;
	}

	public void setRevisionStatus(int revisionStatus) {
		this.revisionStatus = revisionStatus;
	}

	public int getRevisionStatus() {
		return revisionStatus;
	}

	public void setHelpURL(String helpURL) {
		this.helpURL = helpURL;
	}

	public String getHelpURL() {
		return helpURL;
	}

	public void setDistributionString(String distributionString) {
		this.distributionString = distributionString;
	}

	public String getDistributionString() {
		return distributionString;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getHeightUnits() {
		return heightUnits;
	}

	public void setHeightUnits(String heightUnits) {
		this.heightUnits = heightUnits;
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public String getWidthUnits() {
		return widthUnits;
	}

	public void setWidthUnits(String widthUnits) {
		this.widthUnits = widthUnits;
	}
	
	public String getBackgroundStyle() {
		return backgroundStyle;
	}

	public void setBackgroundStyle(String backgroundStyle) {
		this.backgroundStyle = backgroundStyle;
	}

	public boolean isBackgroundScaleToFit() {
		return backgroundScaleToFit;
	}

	public void setBackgroundScaleToFit(boolean backgroundScaleToFit) {
		this.backgroundScaleToFit = backgroundScaleToFit;
	}

	public boolean getHidePointer() {
		return hidePointer;
	}

	public void setHidePointer(boolean hidePointer) {
		this.hidePointer = hidePointer;
	}
	
	public String getDonePlaceholder() {
		return donePlaceholder;
	}

	public void setDonePlaceholder(String donePlaceholder) {
		this.donePlaceholder = donePlaceholder;
	}

	public ArrayList<PlaceholderInfo> getPlaceholders() {
		return placeholders;
	}

	public void setPlaceholders(ArrayList<PlaceholderInfo> placeholders) {
		this.placeholders = placeholders;
	}
	
	public ArrayList<String> getEmbeddedIds() {
		ArrayList<String> embeddedIds = new ArrayList<String>();
		
		for (PlaceholderInfo placeholder: placeholders) {
			if (placeholder.getPlaylistItems() != null) {
				for (PlaylistItemInfo item: placeholder.getPlaylistItems()) {
					if (item != null) {
						if (PlaylistItemInfo.TYPE_PRESENTATION.equals(item.getType()) && !embeddedIds.contains(item.getObjectData())) {
							embeddedIds.add(item.getObjectData());
						}
					}
				}
			}
		}
		
		return embeddedIds;
	}
	
	public void setDonePlaceholderFirst() {
		if (!RiseUtils.strIsNullOrEmpty(getDonePlaceholder())) {
			for (PlaceholderInfo ph: placeholders) {
				if (ph.getId().equals(getDonePlaceholder())) {
					placeholders.remove(ph);
					placeholders.add(0, ph);
					return;
				}
			}
		}
	}

}
