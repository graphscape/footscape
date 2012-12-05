/**
 * Jun 14, 2012
 */
package com.fs.engine.impl.service;

import com.fs.commons.api.context.support.ContextSupport;
import com.fs.commons.api.filter.FilterI;
import com.fs.commons.api.filter.FilterI.Context;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;

/**
 * @author wuzhen
 * 
 */

public class HandleContextImpl extends ContextSupport implements HandleContextI {

	protected FilterI.Context<RequestI, ResponseI> filterContext;

	public HandleContextImpl(FilterI.Context<RequestI, ResponseI> filterContext) {
		this.filterContext = filterContext;
	}

	/* */
	@Override
	public Context<RequestI, ResponseI> getFilterContext() {

		return this.filterContext;

	}

	/* */
	@Override
	public RequestI getRequest() {

		return this.filterContext.getRequest();

	}

	/* */
	@Override
	public ResponseI getResponse() {

		return this.filterContext.getResponse();

	}

}
