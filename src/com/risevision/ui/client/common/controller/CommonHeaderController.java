package com.risevision.ui.client.common.controller;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.risevision.ui.client.UiEntryPoint;

public class CommonHeaderController {

	public static void init() {
		exportStaticMethods();
	}
	
	public static native void exportStaticMethods() /*-{
		$wnd.rva__reportUserLoggedIn =
		$entry(@com.risevision.ui.client.common.controller.CommonHeaderController::reportUserLoggedIn());
		
		$wnd.rva__reportUserLoggedOut =
		$entry(@com.risevision.ui.client.common.controller.CommonHeaderController::reportUserLoggedOut());
		
		$wnd.rva__reportCompanyId =
		$entry(@com.risevision.ui.client.common.controller.CommonHeaderController::reportCompanyId(Ljava/lang/String;));
		
		$wnd.rva__resetCompany =
		$entry(@com.risevision.ui.client.common.controller.CommonHeaderController::resetCompany());
	}-*/;
	
	private static void reportUserLoggedIn() {
		UiEntryPoint.getInstance().loadPrerequisites();
	}
	
	private static void reportUserLoggedOut() {
		PrerequisitesController.getInstance().clear();
	}
	
	private static void reportCompanyId(String companyId) {
		SelectedCompanyController.getInstance().setSelectedCompany(companyId);
	}
	
	private static void resetCompany() {
		SelectedCompanyController.getInstance().reset();
	}
	
	public static int getContentHeight() {
		return Window.getClientHeight() - (31 +
		RootPanel.get("commonHeaderContainer").getElement().getClientHeight());
	}
}
