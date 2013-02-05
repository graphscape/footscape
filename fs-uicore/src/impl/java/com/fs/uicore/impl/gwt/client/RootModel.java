/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 10, 2012
 */
package com.fs.uicore.impl.gwt.client;

import com.fs.uicore.api.gwt.client.support.ModelSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;

/**
 * @author wu
 * 
 */
public class RootModel extends ModelSupport implements ContainerI.AwareI{

	private ContainerI container;
	/**
	 * @param name
	 */
	public RootModel(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see com.fs.uicore.api.gwt.client.ContainerI.AwareI#setContainer(com.fs.uicore.api.gwt.client.ContainerI)
	 */
	@Override
	public void init(ContainerI c) {
		this.container = c;
	}

	/* (non-Javadoc)
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#getContainer()
	 */
	@Override
	public ContainerI getContainer() {
		return this.container;
	}

	@Override
	public UiClientI getClient(boolean force) {
		return this.container.get(UiClientI.class, force);
	}
	
	

}
