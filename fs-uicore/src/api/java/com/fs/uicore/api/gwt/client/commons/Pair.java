/**
 * Jul 2, 2012
 */
package com.fs.uicore.api.gwt.client.commons;

/**
 * @author wu
 * 
 */
public class Pair<K, V> {

	private K key;

	private V value;

	public static <K, V> Pair<K, V> valueOf(K k, V v) {
		return new Pair<K, V>(k, v);
	}

	private Pair(K k, V v) {
		this.key = k;
		this.value = v;
	}

	/**
	 * @return the key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

}
