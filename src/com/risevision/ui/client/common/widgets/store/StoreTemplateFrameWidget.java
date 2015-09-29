// Copyright © 2010 - May 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risevision.ui.client.common.widgets.store;

import java.util.List;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.risevision.ui.client.UiEntryPoint;
import com.risevision.ui.client.common.ContentId;
import com.risevision.ui.client.common.controller.ConfigurationController;
import com.risevision.ui.client.common.controller.SelectedCompanyController;
import com.risevision.ui.client.common.info.HistoryTokenInfo;
import com.risevision.ui.client.common.widgets.StatusBoxWidget;
import com.risevision.ui.client.common.widgets.iframe.CommandType;
import com.risevision.ui.client.common.widgets.iframe.RpcDialogBoxWidget;
import com.risevision.ui.client.gadget.GadgetCommandHelper;

public class StoreTemplateFrameWidget extends RpcDialogBoxWidget {
	private static StoreTemplateFrameWidget instance;
	
	private static final String HTML_STRING = 
			GadgetCommandHelper.HTML_STRING +
			"" +
			"<script type=\"text/javascript\">" +
			"	var callbackFunction;" +
			"	function saveSettings(data) {" +
			"		if (callbackFunction) {" +
			"			callbackFunction(data);" +
			"			callbackFunction = null;" +
			"		}" +
			"		else {" +
			"			var params = '';" +
			"			var additionalParams = null;" +
			"			if (data) {" +
			"				if (data.params) {" +
			"					params = data.params;" +
			"				}" +
			"" +
			"				if (data.additionalParams) {" +
			"					additionalParams = data.additionalParams;" +
			"				}" +
			"			}" +
			"			var paramsArray = [ params, additionalParams ];" +
			"			%save%" +
			"		}" +
			"	}" +
			"	function closeSettings() {" +
			"		%close%" +
			"	}" +
			"	function loadEditor() {" +
			"		gadgets.rpc.register(\"rscmd_saveSettings\", saveSettings);" +
			"		gadgets.rpc.register(\"rscmd_closeSettings\", closeSettings);" +
			"" +
			"		initCallbacks();" +
			"" +
			"		gadgets.rpc.setupReceiver('if_divEditor');" +
			"	}" +
			"	window.onload = loadEditor;" +
			"" +
			"	function getSettings(callback) {" +
			"		callbackFunction = function(data) {" +
			"				callback(data);" +
			"			};" +
			"		try {" +
			"			gadgets.rpc.call(\"if_divEditor\", \"rscmd_getSettings\", callbackFunction);" +
			"		} catch (err) {" +
			"		}" +
			"	}" +
			"</script>" +
			"";
	
	private StatusBoxWidget statusBox = StatusBoxWidget.getInstance();
	
	public static StoreTemplateFrameWidget getInstance() {
		try {
			if (instance == null) {
				instance = new StoreTemplateFrameWidget();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public StoreTemplateFrameWidget() {
		super();

		GadgetCommandHelper.init();
		
		String htmlString = HTML_STRING.replace("%save%", getButtonString(CommandType.SAVE_COMMAND, "paramsArray"))
										.replace("%close%", getButtonString(CommandType.CLOSE_COMMAND));
		
		init(htmlString);
		
	}

	public void showStore(String storePath){
		String url = ConfigurationController.getInstance().getConfiguration().getStoreURL();
//		String url = "https://store-stage-1.risevision.com/";
		url += storePath;
		url += "?inRVA=true&cat=Templates&cid=" + SelectedCompanyController.getInstance().getSelectedCompanyId();
		url += "&up_id=" + "if_divEditor";
		url += "&parent=" + URL.encodeQueryString(Window.Location.getHref());

		this.getElement().getStyle().clearBackgroundColor();
		
		this.getElement().addClassName("responsive-iframe");

		show(url);
	}
	
	@Override
	public void onMessage(String command, List<String> values) {

		if (command.equals(CommandType.SAVE_COMMAND)) {
			String[] tokens = values.get(0).split(",");
			
			if (tokens.length > 1) {
				HistoryTokenInfo tokenInfo = new HistoryTokenInfo();
				tokenInfo.setId(tokens[0]);
				tokenInfo.setFromCompanyId(tokens[1]);
				tokenInfo.setContentId(ContentId.PRESENTATION_MANAGE);
				
				UiEntryPoint.loadContentStatic(tokenInfo);	
			}
		}
		else if (command.equals(CommandType.CLOSE_COMMAND)) {

		}		
		hide();
		
	}

	protected void onFailure(String message) {
		statusBox.setStatus(StatusBoxWidget.Status.ERROR, message);
	}

}