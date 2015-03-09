// Copyright © 2010 - May 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risevision.ui.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.UrlEncodedContent;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risevision.ui.client.common.exception.ServiceFailedException;
import com.risevision.ui.server.utils.OAuth2Utils;
import com.risevision.ui.server.utils.ServerUtils;

import org.restlet.data.Form;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RiseRemoteServiceServlet extends RemoteServiceServlet {
	private static boolean isDevelopmentMode = false;
	
	public static final String URL_PATH_V1 = "v1";
	public static final String URL_PATH_V2 = "v2";
	
	protected static final Logger log = Logger.getAnonymousLogger();
	
	private static String token = "";

	public void init() {
		String serverInfo = getServletContext().getServerInfo();
		/* ServletContext.getServerInfo() will return "Google App Engine Development/x.x.x"
		* if will run locally, and "Google App Engine/x.x.x" if run on production environment */
		if (serverInfo.contains("Development")) {
			isDevelopmentMode = true;
		}else {
			isDevelopmentMode = false;
		}		
	}
	
	@Override
	public String processCall(String payload) throws SerializationException {		
		HttpServletRequest request = this.getThreadLocalRequest();
		token = request.getHeader("rv_access_token");
		
		return super.processCall(payload);
	}
	
	public Document get(String uri) throws ServiceFailedException {
		return get(uri, true);
	}
	
	public Document get(String uri, boolean userAuthentication) throws ServiceFailedException {
		HttpRequestFactory requestFactory = OAuth2Utils.createRequestFactoryWithAccessTokenOnly(token);

		String fullUri = ServerUtils.formatUrl(uri, URL_PATH_V1);

		GenericUrl url = new GenericUrl(fullUri);
		
		try {	
			final HttpRequest request = requestFactory.buildGetRequest(url);

			try {
				HttpResponse response = request.execute();	
				
				if (isDevelopmentMode) {
					ServerUtils.writeDebugInfo(response);
					response = request.execute();
				}
				
				if (response.isSuccessStatusCode()) {
					return getDocument(response.getContent());
				}
			}
			catch (HttpResponseException e) {
				handleResourceException(e);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
	
	public Document post(String uri, Form form) throws ServiceFailedException {
		HttpRequestFactory requestFactory = OAuth2Utils.createRequestFactoryWithAccessTokenOnly(token);

		String fullUri = ServerUtils.formatUrl(uri, URL_PATH_V1);

		GenericUrl url = new GenericUrl(fullUri);
		
		try {	
			final HttpRequest request = requestFactory.buildPostRequest(url, new UrlEncodedContent(form.getValuesMap()));

			try {
				HttpResponse response = request.execute();

				if (isDevelopmentMode) {
					ServerUtils.writeDebugInfo(response);
					response = request.execute();
				}
				
				if (response.isSuccessStatusCode()) {
					return getDocument(response.getContent());
				}
			}
			catch (HttpResponseException e) {
				handleResourceException(e);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			

		return null;
	}
	
	public void put(String url, Form form) throws ServiceFailedException {
		put(url, URL_PATH_V1, form);
	}
	
	public void put(String uri, String urlPath, Form form) throws ServiceFailedException {
		HttpRequestFactory requestFactory = OAuth2Utils.createRequestFactoryWithAccessTokenOnly(token);
		
//		if (retryAttempts != -1) {
//			clientResource.setRetryAttempts(retryAttempts);
//		}
		
		String fullUri = ServerUtils.formatUrl(uri, urlPath);

		GenericUrl url = new GenericUrl(fullUri);
		
		try {	
			final HttpRequest request = requestFactory.buildPutRequest(url, new UrlEncodedContent(form.getValuesMap()));

			try {
				HttpResponse response = request.execute();

				if (isDevelopmentMode) {
					ServerUtils.writeDebugInfo(response);
				}
			}
			catch (HttpResponseException e) {
				handleResourceException(e);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete(String uri) throws ServiceFailedException {
		HttpRequestFactory requestFactory = OAuth2Utils.createRequestFactoryWithAccessTokenOnly(token);

		String fullUri = ServerUtils.formatUrl(uri, URL_PATH_V1);

		GenericUrl url = new GenericUrl(fullUri);
		
		try {	
			final HttpRequest request = requestFactory.buildDeleteRequest(url);

			try {
				HttpResponse response = request.execute();

				if (isDevelopmentMode) {
					ServerUtils.writeDebugInfo(response);
				}
			}
			catch (HttpResponseException e) {
				handleResourceException(e);
			}
			
//			if (isDevelopmentMode) {
//				ServerUtils.writeDebugInfo(clientResource);
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private Document getDocument(InputStream in) {
        DocumentBuilder builder = null;
        
        try {
        	builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        	

		Document doc = null;
				
		try {
			doc = builder.parse(in);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
        return doc;
	}
	
	private void handleResourceException(HttpResponseException e) throws ServiceFailedException {
		if (isDevelopmentMode) {
			System.out.printf("Status: %s %s", e.getStatusCode(), 
					e.getStatusMessage());
			System.out.println();
		}
		
		if (e.getStatusCode() >= 500) {
			log.severe(e.getStatusCode() + " - " + e.getStatusMessage());
		}
		else {
			log.warning(e.getStatusCode() + " - " + e.getStatusMessage());
		}
		
		throw new ServiceFailedException(e.getStatusCode());
	}
}
