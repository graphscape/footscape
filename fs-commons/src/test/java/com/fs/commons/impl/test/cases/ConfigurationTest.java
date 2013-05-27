/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 5, 2012
 */
package com.fs.commons.impl.test.cases;

import junit.framework.TestCase;

import com.fs.commons.api.config.Configuration;

/**
 * @author wuzhen
 * 
 */
public class ConfigurationTest extends TestCase {

	public void testParseSubset() {
		Configuration cfg = Configuration.properties(ConfigurationTest.class.getName());
		assertEquals("pvalue0",cfg.getProperty("pname0"));
		assertEquals("pvalue1",cfg.getProperty("pname1"));
		assertEquals("pvalue2",cfg.getProperty("pname2"));
		assertEquals("pvalue3",cfg.getProperty("pname3"));
		
	}

}
