/**
 * Jun 20, 2012
 */
package com.fs.commons.api.support;

import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.FinderI;
import com.fs.commons.api.HasIdI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.describe.Describe;
import com.fs.commons.api.event.EventBusI;
import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * 
 */
public class ProxyContainerSupport implements ContainerI {

	protected ContainerI target;

	public ProxyContainerSupport(ContainerI t) {
		this.target = t;
	}

	/*
	
	 */
	@Override
	public void addObject(SPI spi, String name, Object o) {
		this.target.addObject(spi, name, o);
	}

	/*
	
	 */
	@Override
	public <T> T find(Class<T> cls) {

		return this.target.find(cls);

	}

	/*
	
	 */
	@Override
	public <T> T find(Class<T> cls, boolean force) {
		return this.target.find(cls, force);

	}

	/*
	
	 */
	@Override
	public <T> FinderI<T> finder(Class<T> cls) {
		// TODO Auto-generated method stub
		return this.target.finder(cls);
	}

	/*
	
	 */
	@Override
	public void forEach(CallbackI<ObjectEntryI, Boolean> cb) {
		this.target.forEach(cb);
	}

	/*
	
	 */
	@Override
	public ContainerI getParent() {

		return this.target.getParent();
	}

	/*
	
	 */
	@Override
	public <T> List<T> find(Describe des) {

		return this.target.find(des);
	}

	/*
	
	 */
	@Override
	public ContainerI getTop() {
		return this.target.getTop();
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void attach() {
		this.target.attach();
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public boolean isAttached() {
		//
		return this.target.isAttached();
	}

	/*
	 * Dec 14, 2012
	 */
	@Override
	public <T> T find(Class<T> cls, String name) {
		//
		return this.target.find(cls, name);

	}

	/*
	 * Dec 14, 2012
	 */
	@Override
	public <T> T find(Class<T> cls, String name, boolean force) {
		//
		return this.target.find(cls, null, force);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public <T extends HasIdI> T find(String id) {
		//
		return this.target.find(id);

	}

	@Override
	public <T extends HasIdI> T find(String id, boolean force) {
		//
		T rt = this.target.find(id);
		if (force && rt == null) {
			throw new FsException("no object with id:" + id);
		}
		return rt;

	}

	/*
	 * Dec 17, 2012
	 */
	@Override
	public EventBusI getEventBus() {
		//
		return this.target.getEventBus();
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.api.ContainerI#addObject(com.fs.commons.api.SPI, java.lang.Object)
	 */
	@Override
	public void addObject(SPI spi, Object o) {
		this.target.addObject(spi, o);
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.api.ContainerI#parent(com.fs.commons.api.ContainerI)
	 */
	@Override
	public ContainerI parent(ContainerI prt) {
		this.target.parent(prt);
		return this;		
	}

}
