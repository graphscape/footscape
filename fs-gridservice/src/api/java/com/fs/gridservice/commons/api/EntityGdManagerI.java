/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.api;

import java.util.List;

import com.fs.gridservice.commons.api.data.EntityGd;

/**
 * @author wuzhen
 * 
 */
public interface EntityGdManagerI<T extends EntityGd> {

	public T getEntity(String id);

	public T addEntity(T eg);

	public T removeEntity(String id);

	public T getEntity(String id, boolean force);

	public List<T> getEntityList(List<String> idL);

}
