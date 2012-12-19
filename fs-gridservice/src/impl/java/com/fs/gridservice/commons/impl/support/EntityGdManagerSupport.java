/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.support;

import com.fs.commons.api.ActiveContext;
import com.fs.gridservice.commons.api.EntityGdManagerI;
import com.fs.gridservice.commons.api.data.EntityGd;
import com.fs.gridservice.core.api.objects.DgMapI;

/**
 * @author wuzhen
 * 
 */
public class EntityGdManagerSupport<T extends EntityGd> extends
		FacadeAwareConfigurableSupport implements EntityGdManagerI<T> {

	protected DgMapI<String, T> dgMap;

	protected Class<T> wrapperClass;

	protected String entityName;

	public EntityGdManagerSupport(String name, Class<T> wcls) {
		this.wrapperClass = wcls;
		this.entityName = name;
	}

	@Override
	public void active(ActiveContext ac) {
		// TODO Auto-generated method stub
		super.active(ac);
		this.dgMap = this.facade.getDataGrid().getMap(
				"entities-" + this.entityName, this.wrapperClass);

	}

	@Override
	public T getEntity(String id) {

		return this.dgMap.getValue(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.EntityGdManagerI#addEntity(com.fs.gridservice
	 * .commons.api.data.EntityGd)
	 */
	@Override
	public T addEntity(T eg) {

		return this.dgMap.put(eg.getId(), eg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.EntityGdManagerI#getEntity(java.lang.String
	 * , boolean)
	 */
	@Override
	public T getEntity(String id, boolean force) {
		return this.dgMap.getValue(id, force);
	}

	/* (non-Javadoc)
	 * @see com.fs.gridservice.commons.api.EntityGdManagerI#removeEntity(java.lang.String)
	 */
	@Override
	public T removeEntity(String id) {
		// TODO Auto-generated method stub
		return this.dgMap.remove(id);
		
	}

}
