/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.impl.support;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.support.ServerSupport;
import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.engine.api.support.RRContext;
import com.fs.expector.api.EventDispatcherI;
import com.fs.expector.api.GridFacadeI;
import com.fs.expector.api.data.EventGd;

/**
 * @author wu
 * 
 */
public abstract class EventDispatcherSupport extends ServerSupport implements
		EventDispatcherI {

	protected static final Logger LOG = LoggerFactory
			.getLogger(EventDispatcherSupport.class);

	protected ServiceEngineI engine;

	protected DgQueueI<EventGd> eventQueue;

	protected ExecutorService executor;

	private int eventCounter;

	protected GridFacadeI facade;

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		this.facade = this.container.find(GridFacadeI.class, true);
		String engineName = this.config.getProperty("engine", true);

		this.engine = this.container.find(ServiceEngineI.class, engineName,
				true);//
		// handlers

		PopulatorI hp = this.engine.getDispatcher().populator("handler");

		hp.active(ac).cfgId(this.configId).force(true).populate();

		//

		this.eventQueue = this.resolveEventQueue();

	}

	protected abstract DgQueueI<EventGd> resolveEventQueue();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.server.ServerI#cmd(java.lang.String)
	 */
	@Override
	public void cmd(String cmd) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.ServerSupport#doStart()
	 */
	@Override
	protected void doStart() {
		this.executor = Executors.newFixedThreadPool(1);// TODO
		this.executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				//
				EventDispatcherSupport.this.run();
				return null;

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.ServerSupport#doShutdown()
	 */
	@Override
	protected void doShutdown() {
		this.executor.shutdown();//
	}

	public void run() {
		while (this.started || this.starting) {
			try {

				this.eachLoop();
			} catch (Throwable t) {
				this.onException(t);
			} finally {
				this.eventCounter++;
			}
		}
	}

	protected void onException(Throwable t) {
		LOG.error("", t);
	}

	public void eachLoop() {
		EventGd e = this.eventQueue.take();
		if (LOG.isDebugEnabled()) {
			LOG.debug("dispatcher:" + this.config.getName()
					+ " is processing event#" + this.eventCounter + "," + e);
		}
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
		res.assertNoError();
		// TODO? how many event as response?

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void deactive(ActiveContext ac) {
		//
		super.deactive(ac);
	}

}
