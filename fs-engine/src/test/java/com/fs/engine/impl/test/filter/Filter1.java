/**
 * Jun 19, 2012
 */
package com.fs.engine.impl.test.filter;

import com.fs.commons.api.filter.support.FilterSupport;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;

/**
 * @author wu
 * 
 */
public class Filter1 extends FilterSupport<RequestI, ResponseI> {

	/** */
	public Filter1() {
		super();

	}

	public static final String payload() {
		return "this is payload from filter1.";
	}

	/* */
	@Override
	public boolean doFilter(Context<RequestI, ResponseI> chain) {
		chain.getResponse().getHeaders()
				.setProperty("filter.list", Filter1.class.getName());

		return false;
	}

}
