/**
 * Jun 15, 2012
 */
package com.fs.commons.impl.config.properties;

import com.fs.commons.api.wrapper.PropertiesWrapper;
import com.fs.commons.impl.config.support.AbstractConfigurationProvider;

/**
 * @author wuzhen
 * 
 */
public class PropertiesConfigurationProvider extends
		AbstractConfigurationProvider {

	/* */
	@Override
	protected PropertiesWrapper loadResource(String prefix, String id) {
		String res = "/" + prefix + "/" + id + ".properties";
		PropertiesWrapper rt = PropertiesWrapper.load(res);

		return rt;

	}

}
