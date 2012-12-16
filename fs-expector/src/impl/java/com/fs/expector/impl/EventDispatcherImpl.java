/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.engine.api.support.RRContext;
import com.fs.expector.api.EventDispatcherI;
import com.fs.expector.api.data.EventGd;

/**
 * @author wu
 * 
 */
public class EventDispatcherImpl extends ConfigurableSupport implements EventDispatcherI {

	protected ServiceEngineI engine;

	@Override
	public void dispatch(EventGd evt) {
		RequestI req = RRContext.newRequest();

		req.setMessage(evt);// convert

		ResponseI res = this.engine.service(req);

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		String engineName = this.config.getProperty("engine", true);
		this.engine = this.container.find(ServiceEngineI.class, engineName);//
	}

}
