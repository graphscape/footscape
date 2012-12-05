/**
 * Jul 9, 2012
 */
package com.fs.commons.impl.test.cases;

import junit.framework.TestCase;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.impl.config.xml.XmlConfigurationProvider;

/**
 * @author wu
 * 
 */
public class XmlProviderTest extends TestCase {

	public void test() {
		XmlConfigurationProvider cp = new XmlConfigurationProvider();
		{
			Configuration cfg = cp.getConfiguration("config.xml.test");
			assertNotNull(cfg);
			assertEquals("", "pvalue0", cfg.getProperty("pname0"));
			assertEquals("", "pvalue1", cfg.getProperty("pname1"));
			assertEquals("", "pvalue2", cfg.getProperty("pname2"));
		} //
		{
			Configuration cfg = cp.getConfiguration("config.xml.test" + "."
					+ "sub1.config1");
			assertEquals("", "pvalue3", cfg.getProperty("pname3"));
			assertEquals("", "pvalue4", cfg.getProperty("pname4"));
		}
		{
			Configuration cfg = cp.getConfiguration("config.xml.test" + "."
					+ "sub1.config2");
			assertEquals("", "pvalue5", cfg.getProperty("pname5"));
		}
		{
			Configuration cfg = cp.getConfiguration("config.xml.test" + "."
					+ "sub1.config2" + "." + "sub2.config1");
			assertEquals("", "pvalue6", cfg.getProperty("pname6"));
		}
		{
			Configuration cfg = cp.getConfiguration("config.xml.test" + "."
					+ "sub1.config3");

			assertEquals("", "pvalue7", cfg.getProperty("sub2.pname7"));
			assertEquals("", "sub2.config1", cfg.getProperty("sub2.pname8"));
			assertEquals("", "pvalue10 of long text",
					cfg.getProperty("pname10"));

		}
		{
			Configuration cfg = cp.getConfiguration("config.xml.test" + "."
					+ "sub1.config3" + "." + "sub2.config1");

			assertEquals("", "pvalue9", cfg.getProperty("pname9"));

		}
	}
}
