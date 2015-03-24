// Copyright © 2010 - May 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risevision.ui.client.common.controller;

import com.risevision.ui.client.common.info.ConfigurationInfo;

public class ConfigurationController {	
	private static ConfigurationController instance;
	private ConfigurationInfo configuration;
	public ConfigurationInfo getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(ConfigurationInfo configuration) {
		this.configuration = configuration;
	}
	
	public static ConfigurationController getInstance() {
		try {
			if (instance == null)
				instance = new ConfigurationController();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public boolean isRiseCompanyId(String id) {
		if (configuration != null && id != null) {
			return configuration.getRiseId().equals(id);
		}
		return false;
	}
}
