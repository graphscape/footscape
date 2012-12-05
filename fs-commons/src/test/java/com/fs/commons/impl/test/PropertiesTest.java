/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 5, 2012
 */
package com.fs.commons.impl.test;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public class PropertiesTest extends TestCase {

	public void testParseSubset() {
		Configuration cfg = Configuration.properties(PropertiesTest.class
				.getName());
		List<PropertiesI<String>> ptsL = cfg.parseAsPropertiesList("prefix",
				new String[] { "suffix1", "suffix2" });
		assertEquals(3, ptsL.size());
		{
			PropertiesI<String> ep1 = new MapProperties<String>();
			ep1.setProperty("suffix1", "key0");
			ep1.setProperty("suffix2", "value0");
			Assert.assertTrue(ptsL.contains(ep1));

		}
		{
			PropertiesI<String> ep1 = new MapProperties<String>();
			ep1.setProperty("suffix1", "key1");
			ep1.setProperty("suffix2", "value1");
			Assert.assertTrue(ptsL.contains(ep1));
		}
		{
			PropertiesI<String> ep1 = new MapProperties<String>();
			ep1.setProperty("suffix1", "key1");
			ep1.setProperty("suffix2", "value2");
			Assert.assertTrue(ptsL.contains(ep1));
		}

	}

}
