/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 5, 2012
 */
package com.fs.commons.api.filter.support;

import com.fs.commons.api.trace.TracableI;
import com.fs.commons.api.trace.Trace;
import com.fs.commons.api.trace.Trace.Before;

/**
 * @author wuzhen
 * 
 */
public abstract class TracableFilterSupport<REQ, RES> extends
		FilterSupport<REQ, RES> {

	public TracableFilterSupport() {
		super();
	}

	public TracableFilterSupport(int p) {
		super(p);
	}

	@Override
	public void filter(Context<REQ, RES> fc) {
		RES res = fc.getResponse();
		if (res instanceof TracableI) {
			Trace t = ((TracableI) res).getTrace();
			Before b = t.before(this);
			try {
				this.filterInternal(fc);
			} finally {
				b.after();
			}

		} else {
			this.filterInternal(fc);
		}
	}

	protected void filterInternal(Context<REQ, RES> fc) {
		super.filter(fc);

	}

}
