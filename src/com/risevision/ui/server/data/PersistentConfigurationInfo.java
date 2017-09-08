// Copyright © 2010 - May 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risevision.ui.server.data;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.risevision.ui.client.common.info.ConfigurationInfo;

@SuppressWarnings("serial")
@PersistenceCapable
public class PersistentConfigurationInfo implements Serializable {

//	private static final String SERVER_URL = "https://rvacore-test.appspot.com";
	private static final String SERVER_URL = "https://custom-rise-auth-dot-rvacore-test.appspot.com";
//	private static final String SERVER_URL = "https://rvacore-test2.appspot.com";
//	private static final String SERVER_URL = "https://rvaserver2.appspot.com";
	
	private static final String STORE_API_URL = "https://store-dot-rvaserver2.appspot.com";
	
	// URL of the Viewer (used for Preview functionality)
	private static final String VIEWER_URL = "http://rvaviewer-test.appspot.com/";
//	private static final String VIEWER_URL = "http://rvashow2.appspot.com/";
//	private static final String VIEWER_URL = "http://preview.risevision.com/"; //production
	
	private static final String MEDIA_LIBRARY_URL = "https://apps-stage-0.risevision.com";
//	private static final String MEDIA_LIBRARY_URL = "https://apps.risevision.com";
	
	// ID of Rise Vision Inc - Top tier PNO
	private static final String RISE_ID = "b428b4e8-c8b9-41d5-8a10-b4193c789443"; // Dev
//	private static final String RISE_ID = "f114ad26-949d-44b4-87e9-8528afc76ce4"; // Production

	// Link to the Rise Installer
	private static final String WINDOWS_INSTALLER_URL = SERVER_URL + "/player/download?os=win";
	private static final String LINUX_INSTALLER_URL = SERVER_URL + "/player/download?os=lnx";
	private static final String MAC_INSTALLER_URL = SERVER_URL + "/player/download?os=mac";
	
	private static final String INSTALLER_URL = "https://help.risevision.com/user/create-a-display";
	
	// Logout URL 
//	private static final String LOGOUT_URL = "/logout/"; // Logout servlet (redirects to login page)
	private static final String LOGOUT_URL = "http://www.risevision.com/logout/";
	
	// Terms and Conditions URL
//	private static final String TERMS_URL = "http://docs.google.com/document/pub?id=1YwrUFIBCKG4iIo34Q-jxHM-4yJdJ13ohb6v3wLXun1A";
//	private static final String TERMS_URL = "RiseVisionTermsofServiceandPrivacy.html";
//	private static final String TERMS_URL = "http://www.risevision.com/TermsOfServiceAndPrivacy/";
	private static final String TERMS_URL = "https://www.risevision.com/terms-of-service";
    
    public static final String GCS_ACCOUNT_EMAIL = "";
    
//    public static final String FINANCIAL_SERVER_URL = "http://contentfinancial2-test.appspot.com";
    public static final String FINANCIAL_SERVER_URL = "http://contentfinancial2.appspot.com";

//    private static final String STORE_URL = "http://store-stage-1.risevision.com/"; // Dev
    private static final String STORE_URL = "https://store.risevision.com/"; // Production
	
	@Persistent
	@PrimaryKey
	private String entityKey;
	@Persistent
	private String riseId;
	@Persistent
	private String serverURL;
	@Persistent
	private String viewerURL;
	@Persistent
	private String mediaLibraryURL;
	@Persistent
	private String windowsInstallerURL;
	@Persistent
	private String linuxInstallerURL;
	@Persistent
	private String macInstallerURL;
	@Persistent
	private String installerURL;
	@Persistent
	private String logoutURL;
	@Persistent
	private String termsURL;
	
	@Persistent
	private String financialServerURL;

	@Persistent
	private String storeURL;
	
	private String storeApiURL;

	public static final String ENTITY_KEY = "Config";

	public PersistentConfigurationInfo() {
		//Run this for Dev.
		entityKey = ENTITY_KEY;
		riseId = RISE_ID;
		serverURL = SERVER_URL;
		viewerURL = VIEWER_URL;
		mediaLibraryURL = MEDIA_LIBRARY_URL;
		windowsInstallerURL = WINDOWS_INSTALLER_URL;
		linuxInstallerURL = LINUX_INSTALLER_URL;
		macInstallerURL = MAC_INSTALLER_URL;
		installerURL = INSTALLER_URL;
		logoutURL = LOGOUT_URL;
		termsURL = TERMS_URL;
		financialServerURL = FINANCIAL_SERVER_URL;
		storeURL = STORE_URL;
		storeApiURL = STORE_API_URL;
	}
	
	public String getEntityKey() {
		return entityKey;
	}

	public String getRiseId() {
		return riseId;
	}

	public void setRiseId(String riseId) {
		this.riseId = riseId;
	}
	
	public String getServerURL() {
		return serverURL;
	}
	
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getViewerURL() {
		return viewerURL;
	}

	public void setViewerURL(String viewerURL) {
		this.viewerURL = viewerURL;
	}
	
	public String getMediaLibraryURL() {
		return mediaLibraryURL;
	}
	
	public void setMediaLibraryURL(String mediaLibraryURL) {
		this.mediaLibraryURL = mediaLibraryURL;
	}
	
	public String getWindowsInstallerURL() {
		return windowsInstallerURL;
	}

	public void setWindowsInstallerURL(String windowsInstallerURL) {
		this.windowsInstallerURL = windowsInstallerURL;
	}
	
	public String getLinuxInstallerURL() {
		return linuxInstallerURL;
	}

	public void setLinuxInstallerURL(String linuxInstallerURL) {
		this.linuxInstallerURL = linuxInstallerURL;
	}
	
	public void setMacInstallerURL(String macInstallerURL) {
		this.macInstallerURL = macInstallerURL;
	}

	public String getMacInstallerURL() {
		return macInstallerURL;
	}
	
	public void setInstallerURL(String installerURL) {
		this.installerURL = installerURL;
	}
	
	public String getInstallerURL() {
		return installerURL;
	}

	public String getLogoutURL() {
		return logoutURL;
	}

	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}
	
	public String getTermsURL() {
		return termsURL;
	}

	public void setTermsURL(String termsURL) {
		this.termsURL = termsURL;
	}
	
	public String getFinancialServerURL() {
		return financialServerURL;
	}

	public void setFinancialServerURL(String financialServerURL) {
		this.financialServerURL = financialServerURL;
	}

	public String getStoreApiURL() {
		return storeApiURL;
	}

	public void setStoreApiURL(String storeApiURL) {
		this.storeApiURL = storeApiURL;
	}

	public ConfigurationInfo getConfigurationInfo() {
		ConfigurationInfo config = new ConfigurationInfo();
		
		config.setRiseId(this.getRiseId());
		config.setServerURL(this.getServerURL());
		config.setViewerURL(this.getViewerURL());
		config.setMediaLibraryURL(this.getMediaLibraryURL());
		config.setWindowsInstallerURL(this.getWindowsInstallerURL());
		config.setLinuxInstallerURL(this.getLinuxInstallerURL());
		config.setMacInstallerURL(this.getMacInstallerURL());
		config.setInstallerURL(this.getInstallerURL());
		config.setLogoutURL(this.getLogoutURL());
		config.setTermsURL(this.getTermsURL());	
		config.setStoreApiURL(this.getStoreApiURL());	
		
		config.setFinancialServerURL(this.getFinancialServerURL());
		config.setStoreURL(this.getStoreURL());
		
		return config;
	}

	public String getStoreURL() {
		return storeURL;
	}

	public void setStoreURL(String storeURL) {
		this.storeURL = storeURL;
	}
}

