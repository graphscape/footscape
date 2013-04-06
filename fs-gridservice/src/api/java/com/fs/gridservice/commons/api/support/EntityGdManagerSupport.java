/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.api.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.gridservice.commons.api.EntityGdManagerI;
import com.fs.gridservice.commons.api.data.EntityGd;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.core.api.objects.DgMapI;

/**
 * @author wuzhen
 * 
 */
public class EntityGdManagerSupport<T extends EntityGd> extends FacadeAwareConfigurableSupport implements
		EntityGdManagerI<T> {

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
		this.dgMap = this.facade.getDataGrid().getMap("entities-" + this.entityName, this.wrapperClass);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.EntityGdManagerI#removeEntity(java.lang
	 * .String)
	 */
	@Override
	public T removeEntity(String id) {
		// TODO Auto-generated method stub
		return this.dgMap.remove(id);

	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public List<T> getEntityList(List<String> idL) {
		//
		List<T> rt = new ArrayList<T>();
		for (String id : idL) {
			T t = this.getEntity(id);
			rt.add(t);
		}
		return rt;
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public List<T> getEntityListByField(String fname, Object fvalue) {
		//

		List<String> kl = this.dgMap.keyList();
		List<T> rt = new ArrayList<T>();
		for (String k : kl) {
			T t = this.dgMap.getValue(k);
			if (fvalue.equals(t.getProperty(fname))) {
				rt.add(t);//
			}
		}
		return rt;
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public T getEntityByField(String fname, Object fvalue, boolean force) {
		//
		List<T> rt = this.getEntityListByField(fname, fvalue);
		if (rt.isEmpty()) {
			if (force) {
				throw new FsException("no entity for " + fname + "=" + fvalue);
			} else {
				return null;
			}
		} else if (rt.size() == 1) {
			return rt.get(0);
		} else {
			throw new FsException("to many terminal for " + fname + "=" + fvalue + ",all are:" + rt);
		}
	}

	/*
	 *Apr 6, 2013
	 */
	@Override
	public List<T> getAllEntity() {
		// 
		return this.dgMap.valueList();
	}

}
