package com.risevision.ui.client.common.service.oauth2;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.risevision.ui.client.common.service.CompanyService;
import com.risevision.ui.client.common.service.CompanyServiceAsync;
import com.risevision.ui.client.common.service.DisplayService;
import com.risevision.ui.client.common.service.DisplayServiceAsync;
import com.risevision.ui.client.common.service.GadgetService;
import com.risevision.ui.client.common.service.GadgetServiceAsync;
import com.risevision.ui.client.common.service.PresentationService;
import com.risevision.ui.client.common.service.PresentationServiceAsync;
import com.risevision.ui.client.common.service.ScheduleService;
import com.risevision.ui.client.common.service.ScheduleServiceAsync;
import com.risevision.ui.client.common.service.SocialConnectorService;
import com.risevision.ui.client.common.service.SocialConnectorServiceAsync;
import com.risevision.ui.client.common.service.UserService;
import com.risevision.ui.client.common.service.UserServiceAsync;

public class OAuth2ServiceWrapper {
	private static final RpcRequestBuilder rpcReqBuilder = new RpcRequestBuilder() {

	    @Override
	    protected RequestBuilder doCreate(String serviceEntryPoint) {

	        RequestBuilder builder = super.doCreate(serviceEntryPoint);
	        builder.setHeader("rv_access_token", getAccessToken()); 
	        return builder;

	    }
	};

	public static CompanyServiceAsync getCompanyService() {
		CompanyServiceAsync service = GWT.create(CompanyService.class);
		((ServiceDefTarget) service).setRpcRequestBuilder(rpcReqBuilder);

		return service;
	}

	public static DisplayServiceAsync getDisplayService() {
		DisplayServiceAsync service = GWT.create(DisplayService.class);
		((ServiceDefTarget) service).setRpcRequestBuilder(rpcReqBuilder);

		return service;
	}
	
	public static GadgetServiceAsync getGadgetService() {
		GadgetServiceAsync service = GWT.create(GadgetService.class);
		((ServiceDefTarget) service).setRpcRequestBuilder(rpcReqBuilder);

		return service;
	}
	
	public static PresentationServiceAsync getPresentationService() {
		PresentationServiceAsync service = GWT.create(PresentationService.class);
		((ServiceDefTarget) service).setRpcRequestBuilder(rpcReqBuilder);

		return service;
	}
	
	public static ScheduleServiceAsync getScheduleService() {
		ScheduleServiceAsync service = GWT.create(ScheduleService.class);
		((ServiceDefTarget) service).setRpcRequestBuilder(rpcReqBuilder);

		return service;
	}
	
	public static SocialConnectorServiceAsync getSocialConnectorService() {
		SocialConnectorServiceAsync service = GWT.create(SocialConnectorService.class);
		((ServiceDefTarget) service).setRpcRequestBuilder(rpcReqBuilder);

		return service;
	}
	
	public static UserServiceAsync getUserService() {
		UserServiceAsync service = GWT.create(UserService.class);
		((ServiceDefTarget) service).setRpcRequestBuilder(rpcReqBuilder);

		return service;
	}
	
	public static native String getAccessToken() /*-{
		var authInstance = $wnd.gapi.auth2.getAuthInstance();
		if (authInstance) {
			var currentUser = authInstance.currentUser.get();
			if (currentUser) {
				var token = currentUser.getAuthResponse(true).access_token;
				
				return token ? token : "";
			}
		}

		$wnd.console.error("Access Token Not Available");
		
		return "";
	}-*/;	
}
