/**
 *  Dec 13, 2012
 */
package com.fs.gridservice.core.api.objects;

import java.util.List;

import com.fs.gridservice.core.api.DgCollectionI;

/**
 * @author wuzhen
 * 
 */
public interface DgMapI<K, V> extends DgCollectionI {

	public V getValue(K key);
	
	public V getValue(K key, boolean force);
	
	public V remove(K key);

	public V put(K key, V value);
	
	public List<K> keyList();
	
	public List<V> valueList();

}
