/**
 * Jun 20, 2012
 */
package com.fs.commons.api.support;

import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.FinderI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.describe.Describe;

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
	 *Dec 11, 2012
	 */
	@Override
	public void attach() {
		this.target.attach();
	}

	/*
	 *Dec 11, 2012
	 */
	@Override
	public boolean isAttached() {
		// 
		return this.target.isAttached();
	}

}
