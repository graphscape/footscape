/**
 * Jun 19, 2012
 */
package com.fs.commons.impl.test.cases;

import org.junit.Test;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.factory.ConfigFactoryI;
import com.fs.commons.api.factory.ObjectConfigI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class ConfigTest extends TestBase {

	public void testConfigFactory() {
		ContainerI c = sm.getContainer();
		ConfigFactoryI cf = c.find(ConfigFactoryI.class, true);

		cf.finder().clazz(ObjectConfigI.class).describe("_TYPE", "testobject");

	}

	@Test
	public void testConfigProviderAndUser() {

		Configuration cfg = Configuration.properties("config-test");

		String value1 = cfg.getProperty("name.name1");
		assertEquals("value1 in provider", value1);

		String value2 = cfg.getProperty("name.name2");

		assertEquals("value2 in user", value2);

		String value3 = cfg.getProperty("name.name3");
		assertEquals("value3 in env", value3);
	}

}
