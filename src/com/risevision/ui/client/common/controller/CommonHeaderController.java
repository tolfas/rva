package com.risevision.ui.client.common.controller;

import com.risevision.ui.client.UiEntryPoint;

public class CommonHeaderController {

	public static void init() {
		exportStaticMethods();
	}
	
	public static native void exportStaticMethods() /*-{
		$wnd.reportUserLoggedIn =
		$entry(@com.risevision.ui.client.common.controller.CommonHeaderController::reportUserLoggedIn());		
	}-*/;
	
	private static void reportUserLoggedIn() {
		UiEntryPoint.getInstance().loadPrerequisites();
	}
}
