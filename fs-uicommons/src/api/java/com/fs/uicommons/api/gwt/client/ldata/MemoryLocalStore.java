/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 9, 2012
 */
package com.fs.uicommons.api.gwt.client.ldata;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wu
 * 
 */
public class MemoryLocalStore extends LocalStore {

	private Map<String, String> map;

	public static MemoryLocalStore ME = new MemoryLocalStore();

	protected MemoryLocalStore() {
		this.map = new HashMap<String, String>();
	}

	@Override
	protected String getValue(String key) {
		return map.get(key);
	}

	/**
	 * Dec 9, 2012
	 */

	@Override
	protected void setValue(String key, String value) {
		this.map.put(key, value);
	}
}
