/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.engine.api.support.RRContext;
import com.fs.expector.api.EventDispatcherI;
import com.fs.expector.api.data.EventGd;
import com.fs.expector.impl.support.FacadeAwareConfigurableSupport;

/**
 * @author wu
 * 
 */
public abstract class EventDispatcherSupport extends FacadeAwareConfigurableSupport implements
		EventDispatcherI {

	protected static final Logger LOG = LoggerFactory.getLogger(EventDispatcherSupport.class);

	protected ServiceEngineI engine;

	protected DgQueueI<EventGd> eventQueue;

	protected ExecutorService executor;

	protected boolean running;

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

		this.engine = this.container.find(ServiceEngineI.class, engineName, true);//
		// handlers

		PopulatorI hp = this.engine.getDispatcher().populator("handler");

		hp.active(ac).cfgId(this.configId).force(true).populate();

		//

		this.eventQueue = this.resolveEventQueue();

		this.executor = Executors.newFixedThreadPool(1);// TODO
		this.running = true;
		this.executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				//
				EventDispatcherSupport.this.run();
				return null;

			}
		});

	}

	protected abstract DgQueueI<EventGd> resolveEventQueue();

	public void run() {
		while (this.running) {
			try {
				this.eachLoop();
			} catch (Throwable t) {
				this.onException(t);
			}
		}
	}

	protected void onException(Throwable t) {
		LOG.error("", t);
	}

	public void eachLoop() {
		EventGd e = this.eventQueue.take();
		String path = e.getPath();
		if (path.startsWith("/")) {
			path = "/events" + path;
		} else {
			path = "/events/" + path;
		}

		RequestI req = RRContext.newRequest();
		req.setPath(path);//
		req.setPayload(e);

		ResponseI res = this.engine.service(req);
		// TODO? how many event as response?

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void deactive(ActiveContext ac) {
		//
		super.deactive(ac);
		this.running = false;
		this.executor.shutdown();
	}

}
