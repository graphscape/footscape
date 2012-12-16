/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.datagrid.api.objects.DgMapI;
import com.fs.expector.api.GridedObjectI;
import com.fs.expector.api.GridedObjectManagerI;
import com.fs.expector.api.data.ObjectRefGd;
import com.fs.expector.impl.support.FacadeAwareConfigurableSupport;

/**
 * @author wuzhen
 * 
 */
public class GridedObjectManagerImpl<T extends GridedObjectI> extends FacadeAwareConfigurableSupport
		implements GridedObjectManagerI<T> {

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
		this.objectRefDgMap = this.facade.getDataGrid().getMap("map-object-ref-" + goItfClass.getName(),
				ObjectRefGd.class);
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
		T old = this.goMap.put(go.getId(), go);

		ObjectRefGd<T> ref = new ObjectRefGd<T>(id, this.facade.getLocalMember().getId());

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
		//
		return null;
	}

}
