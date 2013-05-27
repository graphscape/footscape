/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 9, 2012
 */
package com.fs.commons.impl.config.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.impl.config.support.AbstractConfigurationProvider;

/**
 * @author wuzhen
 * 
 */
public class XmlConfigurationProvider extends AbstractConfigurationProvider {

	private Map<String, XmlConfigElement> loadAllFor(String prefix, String id) {

		Map<String, XmlConfigElement> rt = new HashMap<String, XmlConfigElement>();
		XmlConfigElement xce = null;//

		while (true) {

			xce = XmlConfigElement.load(prefix, id, false);

			if (xce != null) {
				rt.put(id, xce);//
			}

			int idx = id.lastIndexOf(".");
			if (idx < 0) {
				break;
			}
			id = id.substring(0, idx);//
		}
		return rt;

	}
	
	/* */
	@Override
	protected PropertiesI<String> loadResource(String prefix, String id, Set<String> typeHolder,
			List<String> childIdSet, Map<String, String> crmap) {
		Map<String, XmlConfigElement> map = this.loadAllFor(prefix, id);
		// find the properties in all the map
		for (XmlConfigElement ce : map.values()) {
			XmlConfigElement xce = ce.findByConfigId(id, false);
			if (xce != null) {
				List<String> cidL = xce.getChildConfigNameList();
				childIdSet.addAll(cidL);
				crmap.putAll(xce.getConfigRefMap());
				if (typeHolder != null) {
					String type = xce.getType();
					typeHolder.add(type);
				}

				return MapProperties.valueOf(xce.getProperties().getAsMap());

			}
		}

		return new MapProperties<String>();

	}
}
