/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 5, 2012
 */
package com.fs.commons.impl.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public class ConfigurationTest extends TestCase {

	public void testParseSubset() {
		Configuration cfg = Configuration.properties(ConfigurationTest.class
				.getName());
		PropertiesI<String> pts = cfg.parseAsProperties("prefix", "keySuffix",
				"valueSuffix");
		PropertiesI<String> expected = new MapProperties<String>();
		expected.setProperties("key0", "value0", "key1", "value1", "key3",
				"value3", "key5", "value5");
		Assert.assertEquals("", expected, pts);
	}

}
