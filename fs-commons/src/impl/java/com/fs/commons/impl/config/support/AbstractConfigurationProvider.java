/**
 * Jun 15, 2012
 */
package com.fs.commons.impl.config.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.StringProperties;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public abstract class AbstractConfigurationProvider extends ConfigurationProviderSupport {

	private String[] prefix = new String[] { "provider", "user", "env" };// NOTE,env
																			// is
																			// not
																			// in
																			// classpath
																			// ,but
																			// in
																			// user.home/.fs/env

	@Override
	public PropertiesI<String> loadAlias(String id) {
		String resId = id;
		PropertiesI<String> rt = null;

		int idx = resId.lastIndexOf(".");
		do {
			List<String> childIdSet = new ArrayList<String>();
			Map<String, String> crmap = new HashMap<String, String>();
			PropertiesI<String> pw = this.loadConfig("$" + resId, null, childIdSet, crmap);

			if (pw != null) {
				if (rt == null) {
					rt = pw;
				} else {
					rt = pw.mergeFrom(rt);
				}
			}

			idx = resId.lastIndexOf(".");
			if (idx >= 0) {// no dot
				resId = resId.substring(0, idx);
			}
		} while (idx >= 0);
		return rt;
	}

	@Override
	public PropertiesI<String> loadConfig(String id, Set<String> typeHolder, List<String> childIdSet,
			Map<String, String> crmap) {
		if (id == null) {
			throw new FsException("id is null");
		}
		PropertiesI<String> pw = null;
		for (String pre : this.prefix) {

			PropertiesI<String> next = this.loadResource(pre, id, typeHolder, childIdSet, crmap);
			if (pw == null) {
				pw = next;
			} else {
				pw = pw.mergeFrom(next);
			}
		}
		// alias
		return pw;

	}

	protected abstract PropertiesI<String> loadResource(String prefix, String id, Set<String> typeHolder,
			List<String> childIdSet, Map<String, String> crmap);

}
