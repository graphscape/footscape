/**
 * Jun 10, 2012
 */
package com.fs.commons.api.wrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wu
 * 
 */
public class MapWrapper<K, V> {

	private Map<K, V> target;

	/** */
	public MapWrapper() {
		this(new HashMap<K, V>());
	}

	public MapWrapper(Map<K, V> t) {
		this.target = t;
	}

	public MapWrapper<K, V> newInstance(Map<K, V> t) {
		return new MapWrapper<K, V>(t);
	}

	public Map<K, V> getTarget() {
		return this.target;
	}

}
