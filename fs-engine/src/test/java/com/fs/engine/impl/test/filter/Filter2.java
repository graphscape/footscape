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
public class Filter2 extends FilterSupport<RequestI, ResponseI> {
	public static final String KEY = "filter.list";

	public static final String VALUE = Filter1.class.getName()
			+ Filter2.class.getName();

	/** */
	public Filter2() {
		super();

	}

	public static String payload() {
		return "this is payload from filter2.";
	}

	/* */
	@Override
	public boolean doFilter(Context<RequestI, ResponseI> chain) {

		String fl = chain.getResponse().getHeaders().getProperty(KEY);

		chain.getResponse().getHeaders()
				.setProperty(KEY, fl + Filter2.class.getName());

		return false;
	}

}
