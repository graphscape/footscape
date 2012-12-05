/**
 * Jun 20, 2012
 */
package com.fs.commons.api.support;

import com.fs.commons.api.ContainerI;

/**
 * @author wu
 * 
 */
public class ContainerAwareSupport implements ContainerI.AwareI {
	protected ContainerI container;

	/* */
	@Override
	public void setContainer(ContainerI c) {
		this.container = c;
	}
}
