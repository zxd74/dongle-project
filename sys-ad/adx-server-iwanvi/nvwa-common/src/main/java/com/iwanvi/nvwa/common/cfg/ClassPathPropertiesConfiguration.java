package com.iwanvi.nvwa.common.cfg;


import com.iwanvi.nvwa.common.utils.ClassLoaderUtils;

public class ClassPathPropertiesConfiguration extends PropertiesConfiguration {
	public ClassPathPropertiesConfiguration(String propertiesFile) {
		this.properties = ClassLoaderUtils.getProperties(propertiesFile);
	}
}
