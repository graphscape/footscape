/**
 * Jun 15, 2012
 */
package com.fs.commons.impl.config.support;

import com.fs.commons.api.Assert;
import com.fs.commons.api.wrapper.PropertiesWrapper;

/**
 * @author wuzhen
 * 
 */
public abstract class AbstractConfigurationProvider extends
		ConfigurationProviderSupport {

	private String[] prefix = new String[] { "provider", "user", "env" };// NOTE,env
																			// is
																			// not
																			// in
																			// classpath
																			// ,but
																			// in
																			// user.home/.fs/env

	@Override
	public PropertiesWrapper loadAlias(String id) {
		String resId = id;
		PropertiesWrapper rt = null;

		int idx = resId.lastIndexOf(".");
		do {
			if (idx >= 0) {// no dot
				resId = resId.substring(0, idx);
			}

			PropertiesWrapper pw = this.loadConfig("$" + resId);

			if (pw != null) {
				if (rt == null) {
					rt = pw;
				} else {
					rt = pw.mergeFrom(rt);
				}
			}
			idx = resId.lastIndexOf(".");
		} while (idx >= 0);
		return rt;
	}

	@Override
	public PropertiesWrapper loadConfig(String id) {
		Assert.assertNotNull(id, "config id is null.");
		PropertiesWrapper pw = null;
		for (String pre : this.prefix) {

			PropertiesWrapper next = this.loadResource(pre, id);
			if (pw == null) {
				pw = next;
			} else {
				pw = pw.mergeFrom(next);
			}
		}
		// alias
		return pw;

	}

	protected abstract PropertiesWrapper loadResource(String prefix, String id);

}
