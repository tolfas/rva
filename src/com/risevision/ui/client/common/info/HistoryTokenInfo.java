// Copyright © 2010 - May 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risevision.ui.client.common.info;

import java.util.ArrayList;
import java.util.List;

import com.risevision.ui.client.common.utils.Utils;

public class HistoryTokenInfo {

	private String contentId;
	private String companyId;
	private String id;
	private String fromCompanyId;
	private String bookmark;
	private List<String> extraTokens;
	
	public HistoryTokenInfo() {
		extraTokens = new ArrayList<String>();
	}
	
	public String getContentId() {
		return contentId;
	}
	
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFromCompanyId() {
		return fromCompanyId;
	}
	
	public void setFromCompanyId(String fromCompanyId) {
		this.fromCompanyId = fromCompanyId;
	}
	
	public String getBookmark() {
		return bookmark;
	}
	
	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}

	public List<String> getExtraTokens() {
		return extraTokens;
	}

	public void setExtraTokens(List<String> extraTokens) {
		this.extraTokens = extraTokens;
	}
	
	public boolean isEqual(HistoryTokenInfo newToken) {
		return Utils.stringEquals(this.contentId, newToken.contentId) &&
				Utils.stringEquals(this.companyId, newToken.companyId) &&
				Utils.stringEquals(this.id, newToken.id) &&
				Utils.stringEquals(this.fromCompanyId, newToken.fromCompanyId) &&
				Utils.stringEquals(this.bookmark, newToken.bookmark);
	}
}
