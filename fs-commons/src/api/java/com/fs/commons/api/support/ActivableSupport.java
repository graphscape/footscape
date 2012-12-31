/**
 * Jun 20, 2012
 */
package com.fs.commons.api.support;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;

/**
 * @author wu
 * 
 */
public class ActivableSupport extends AttachableSupport implements ActivableI {

	protected ContainerI container;

	protected ContainerI top;

	protected SPI spi;

	protected ActiveContext activeContext;

	/* */
	@Override
	public void active(ActiveContext ac) {
		this.activeContext = ac;
		this.container = ac.getContainer();
		this.top = this.container.getTop();//
		this.spi = ac.getSpi();
	}

	protected ActiveContext newActiveContext() {
		return new ActiveContext(this.container, this.spi);
	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.AttachableSupport#doDettach()
	 */
	@Override
	protected void doDettach() {

	}

}
