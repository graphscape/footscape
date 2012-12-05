/**
 * Jul 9, 2012
 */
package com.fs.commons.impl.test.cases;

import junit.framework.TestCase;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.impl.config.MultiConfigurationProvider;

/**
 * @author wu
 * 
 */
public class MultiProviderTest extends TestCase {

	public void test() {

		MultiConfigurationProvider cp = new MultiConfigurationProvider();
		{
			Configuration cfg = cp.getConfiguration("config.multi.test");
			assertNotNull(cfg);
			assertEquals("", "pvalue1-in-xml", cfg.getProperty("pname1"));
			assertEquals("", "pvalue2-in-properties", cfg.getProperty("pname2"));
			assertEquals("", "pvalue3-in-alias-properties", cfg.getProperty("pname3"));
			assertEquals("", "pvalue4-in-alias-properties", cfg.getProperty("pname4"));
			assertEquals("", "pvalue5-in-alias-xml", cfg.getProperty("pname5"));
			
			
		} //
		
	}
}
