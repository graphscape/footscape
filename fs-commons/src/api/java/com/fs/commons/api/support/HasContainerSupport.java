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
public class HasContainerSupport extends ConfigurableSupport implements
		HasContainerI {

	protected ContainerI internal;

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		ContainerI.FactoryI cf = this.top.find(ContainerI.FactoryI.class, true);
		this.internal = cf.newContainer(this.container);
	}

	@Override
	public void deactive(ActiveContext ac) {
		super.deactive(ac);

	}

	@Override
	protected void doAttach() {
		super.doAttach();
		this.internal.attach();
	}

	@Override
	protected void doDettach() {
		super.doDettach();
		this.internal.dettach();//
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