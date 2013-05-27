/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 5, 2012
 */
package com.fs.commons.impl.test;

import junit.framework.TestCase;

import com.fs.commons.api.config.Configuration;

/**
 * @author wuzhen
 * 
 */
public class PropertiesTest extends TestCase {

	public void testParseSubset() {
		Configuration cfg = Configuration.properties(PropertiesTest.class.getName());

	}

}
