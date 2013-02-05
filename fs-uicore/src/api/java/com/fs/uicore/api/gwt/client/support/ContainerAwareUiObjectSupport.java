/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;

/**
 * @author wu
 * 
 */
public class ContainerAwareUiObjectSupport extends StatefulUiObjectSupport implements ContainerI.AwareI {

	/**
	 * @param name
	 */
	public ContainerAwareUiObjectSupport(ContainerI c) {
		this(c, null);
	}

	public ContainerAwareUiObjectSupport(ContainerI c, String name) {
		super(c, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.ContainerAwareI#setContainer(com.fs.uicore
	 * .api.gwt.client.ContainerI)
	 */
	@Override
	public void init(ContainerI c) {
		this.container = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#getEventBus()
	 */
	@Override
	public EventBusI getEventBus(boolean force) {
		return this.container.getEventBus();
	}

}
