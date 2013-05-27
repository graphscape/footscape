/**
 *  Dec 31, 2012
 */
package com.fs.commons.api.support;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.HasContainerI;
import com.fs.commons.api.config.support.ConfigurableSupport;

/**
 * @author wuzhen
 * 
 */
public class HasContainerSupport extends ConfigurableSupport implements HasContainerI {

	protected ContainerI internal;

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.internal = this.components.newComponent(this.spi, ContainerI.class).parent(this.container);

	}

	@Override
	protected void doAttach() {
		super.doAttach();
		this.internal.attach();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.HasContainerI#getInternal()
	 */
	@Override
	public ContainerI getInternal() {
		return this.internal;
	}

}
