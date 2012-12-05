/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 6, 2012
 */
package com.fs.commons.impl.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.fs.commons.api.config.Configuration;

/**
 * @author wuzhen
 * 
 */
public class AliasTest extends TestCase {

	@Test
	public void test() {
		{
			Configuration cfg = Configuration
					.properties("Test.SPI.testalias.package1");
			assertEquals("testalias-value1", cfg.getProperty("property1"));
			assertEquals("testalias-value2", cfg.getProperty("property2"));
			assertEquals("SPI-value3", cfg.getProperty("property3"));

		}

	}

}
