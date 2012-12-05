/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 3, 2012
 */
package com.fs.commons.api.service.support;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.service.ServiceI;
import com.fs.commons.api.wrapper.Holder;

/**
 * @author wuzhen
 * 
 */
public abstract class ServiceSupport<REQ, RES> extends ConfigurableSupport
		implements ServiceI<REQ, RES> {

	@Override
	public RES service(REQ req) {
		final Holder<RES> rt = new Holder<RES>(null);
		this.service(req, new CallbackI<RES, Object>() {

			@Override
			public Object execute(RES i) {
				rt.setTarget(i);
				return null;
			}
		});
		return rt.getTarget();
	}

	@Override
	public void service(REQ req, CallbackI<RES, Object> res) {
		RES r = this.newResponse();
		this.service(req, r);
		res.execute(r);
	}

	protected abstract REQ newRequest();

	protected abstract RES newResponse();

}
