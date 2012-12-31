/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights Served.
 *
 * Jul 3, 2012
 */
package com.fs.commons.api.service.support;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.service.ServiceContext;
import com.fs.commons.api.service.ServiceI;
import com.fs.commons.api.wrapper.Holder;

/**
 * @author wuzhen
 * 
 */
public abstract class ServiceSupport<R, S, C extends ServiceContext<R, S>>
		extends ConfigurableSupport implements ServiceI<R, S, C> {

	@Override
	public S service(R R) {
		final Holder<S> rt = new Holder<S>(null);
		this.service(R, new CallbackI<S, Object>() {

			@Override
			public Object execute(S i) {
				rt.setTarget(i);
				return null;
			}
		});
		return rt.getTarget();
	}

	@Override
	public void service(R R, CallbackI<S, Object> S) {
		S r = this.newResponse(R);
		this.service(R, r);
		S.execute(r);
	}

	protected abstract R newRequest();

	protected abstract S newResponse(R R);

}
