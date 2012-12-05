/**
 * Jul 4, 2012
 */
package com.fs.engine.impl.support;

import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.RRContext;

/**
 * @author wu
 * 
 */
public abstract class ServiceSupport extends
		com.fs.commons.api.service.support.ServiceSupport<RequestI, ResponseI> {

	/* */
	@Override
	protected RequestI newRequest() {

		return RRContext.newRequest();

	}

	/* */
	@Override
	protected ResponseI newResponse() {

		return RRContext.newResponse();

	}

}
