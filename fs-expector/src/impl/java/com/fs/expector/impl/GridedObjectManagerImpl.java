/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.lang.FsException;
import com.fs.datagrid.api.objects.DgMapI;
import com.fs.expector.api.GridedObjectI;
import com.fs.expector.api.GridedObjectManagerI;
import com.fs.expector.api.data.ObjectRefGd;
import com.fs.expector.impl.support.FacadeAwareConfigurableSupport;

/**
 * @author wuzhen
 * 
 */
public class GridedObjectManagerImpl<T extends GridedObjectI> extends
		FacadeAwareConfigurableSupport implements GridedObjectManagerI<T> {

	private static final Logger LOG = LoggerFactory
			.getLogger(GridedObjectManagerImpl.class);

	private Map<String, T> goMap;// local container.

	private DgMapI<String, ObjectRefGd> objectRefDgMap;// remote map.

	protected Class<T> goItfClass;

	public GridedObjectManagerImpl() {
		super();
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);
		this.goItfClass = cfg.getPropertyAsClass("goItfClass", true);

	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.goMap = new HashMap<String, T>();
		this.objectRefDgMap = this.facade.getDataGrid().getMap(
				"map-object-ref-" + goItfClass.getName(), ObjectRefGd.class);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public String getName() {
		return this.config.getName();
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public T getGridedObject(String id) {
		//

		return (T) this.goMap.get(id);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public T addGridedObject(T go) {
		//
		String id = go.getId();
		T old = this.goMap.put(id, go);

		ObjectRefGd<T> ref = new ObjectRefGd<T>(id, this.facade
				.getLocalMember().getId());

		this.objectRefDgMap.put(id, ref);

		return old;

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public T removeGridedObject(String id) {
		//
		this.objectRefDgMap.remove(id);
		T old = this.goMap.remove(id);

		return old;

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public ObjectRefGd<T> getRef(String id) {

		return this.objectRefDgMap.getValue(id);
	}

	@Override
	public ObjectRefGd<T> getRef(String id, boolean force) {
		//
		ObjectRefGd<T> rt = this.getRef(id);
		if (rt == null && force) {
			throw new FsException("no objec ref for id: " + id);
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.expector.api.GridedObjectManagerI#getGridedObject(java.lang.String
	 * , boolean)
	 */
	@Override
	public T getGridedObject(String id, boolean force) {
		T rt = this.getGridedObject(id);
		if (force && rt == null) {

			if (LOG.isDebugEnabled()) {
				LOG.debug("not found id:" + id + "all object:"
						+ this.goMap.toString());
			}

			throw new FsException("no grided object found by id:" + id
					+ " in manager:" + this.getName());
		}
		return rt;

	}
}
