package com.risevision.ui.server.utils;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequestFactory;

public class OAuth2Utils {

	static final UrlFetchTransport HTTP_TRANSPORT = new UrlFetchTransport();
	
	public static HttpRequestFactory createRequestFactoryWithAccessTokenOnly(String accessToken) {
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setAccessToken(accessToken);
		
		return HTTP_TRANSPORT.createRequestFactory(
				new GoogleCredential().setFromTokenResponse(tokenResponse));
	}
}