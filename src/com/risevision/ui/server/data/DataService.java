// Copyright © 2010 - May 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risevision.ui.server.data;

import com.risevision.ui.server.data.PersistentOAuthInfo.OAuthType;

public class DataService {
	private PersistentHandler persistentHandler = new PersistentHandler();
	private static DataService instance;
	
	private DataService() {
	
	}
	
	public static synchronized DataService getInstance() {
		if (instance == null) {
			instance = new DataService();
		}
		return instance;
	}
	
	public void saveConfig() {
		persistentHandler.saveConfig();
	}
		
	public PersistentConfigurationInfo getConfig() {
		CacheHandler cache = CacheHandler.getInstance();
		
		PersistentConfigurationInfo config = (PersistentConfigurationInfo) cache.findInCache(PersistentConfigurationInfo.ENTITY_KEY);
		
		if (config == null) {
			config = persistentHandler.getConfig();
			
			if (config == null) {
				config = new PersistentConfigurationInfo();
				persistentHandler.saveConfig(config);
			}
			
			cache.putInCache(PersistentConfigurationInfo.ENTITY_KEY, config);
		}
		
		return new PersistentConfigurationInfo();
//		return config;
	}
		
	public void saveOAuth(PersistentOAuthInfo oAuth) {
		persistentHandler.saveOAuth(oAuth);
	}
		
	public PersistentOAuthInfo getOAuth(OAuthType oAuthType) {
		CacheHandler cache = CacheHandler.getInstance();
		
		PersistentOAuthInfo oAuth = (PersistentOAuthInfo) cache.findInCache(oAuthType.getEntityKey());
		
		if (oAuth == null) {
			oAuth = persistentHandler.getOAuth(oAuthType);
			
			if (oAuth == null) {
				oAuth = new PersistentOAuthInfo(oAuthType);
				persistentHandler.saveOAuth(oAuth);
			}
			cache.putInCache(oAuthType.getEntityKey(), oAuth);
		}
		
		return oAuth;
	}

}
