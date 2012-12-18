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
public interface DgSetI<V> extends DgCollectionI {

	public boolean contains(V value);

	public boolean remove(V value);

	public boolean add(V value);

	public List<V> valueList();

}
