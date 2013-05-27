/**
 * Jun 20, 2012
 */
package com.fs.commons.api.support;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.components.ComponentFactoryI;

/**
 * @author wu
 * 
 */
public class ActivableSupport extends AttachableSupport implements ActivableI {

	protected ContainerI container;

	protected ContainerI top;

	protected SPI spi;

	protected ActiveContext activeContext;

	protected ComponentFactoryI components;

	/* */
	@Override
	public void active(ActiveContext ac) {
		this.activeContext = ac;
		this.container = ac.getContainer();
		this.top = this.container.getTop();//
		this.spi = ac.getSpi();
		this.components = this.top.find(ComponentFactoryI.class, true);
	}

	protected ActiveContext newActiveContext() {
		return new ActiveContext(this.container, this.spi);
	}

	public ContainerI getContainer() {
		return container;
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	protected void doAttach() {

	}

}
