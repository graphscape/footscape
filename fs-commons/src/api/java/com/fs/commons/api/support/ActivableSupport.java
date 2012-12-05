/**
 * Jun 20, 2012
 */
package com.fs.commons.api.support;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;

/**
 * @author wu
 * 
 */
public class ActivableSupport implements ActivableI {

	protected ContainerI container;

	/* */
	@Override
	public void active(ActiveContext ac) {
		this.container = ac.getContainer();
	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {

	}

	public ContainerI getContainer() {
		return container;
	}

}
