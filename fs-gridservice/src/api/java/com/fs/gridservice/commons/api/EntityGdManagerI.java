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

	public List<T> getAllEntity();
	
	public T getEntity(String id);

	public T addEntity(T eg);

	public T removeEntity(String id);

	public T getEntity(String id, boolean force);

	public List<T> getEntityList(List<String> idL);
	
	public List<T> getEntityListByField(String fname, Object fvalue);
	
	public T getEntityByField(String fname, Object fvalue, boolean force);

}
