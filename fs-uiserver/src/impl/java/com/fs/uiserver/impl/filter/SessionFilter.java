/**
 * Jun 22, 2012
 */
package com.fs.uiserver.impl.filter;

import com.fs.commons.api.filter.support.FilterSupport;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;

/**
 * @author wu
 * 
 *         ?No matter which request, create session for it when there is no
 *         session id?
 * 
 */
public class SessionFilter extends FilterSupport<RequestI, ResponseI> {

	/* */
	@Override
	protected boolean doFilter(Context<RequestI, ResponseI> fc) {
		//TODO
		return false;

	}

}
