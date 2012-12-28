/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.support;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.event.ListenerI;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.server.ServerI;
import com.fs.commons.api.support.ServerSupport;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.engine.api.support.RRContext;
import com.fs.gridservice.commons.api.EventDispatcherI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.core.api.event.BeforeDgCloseEvent;
import com.fs.gridservice.core.api.objects.DgQueueI;

/**
 * @author wu
 * 
 */
public abstract class EventDispatcherSupport extends ServerSupport implements EventDispatcherI {

	protected static final Logger LOG = LoggerFactory.getLogger(EventDispatcherSupport.class);

	protected ServiceEngineI engine;

	protected DgQueueI<EventGd> eventQueue;

	protected ExecutorService executor;

	private int eventCounter;

	protected GridFacadeI facade;

	protected Future<Object> future;

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		this.facade = this.container.find(GridFacadeI.class, true);
		String engineName = this.config.getProperty("engine", true);

		this.engine = this.container.find(EngineFactoryI.class, true).getEngine(engineName);//

		// handlers

		PopulatorI hp = this.engine.getDispatcher().populator("handler");

		hp.active(ac).cfgId(this.configId).force(true).populate();

		//
		this.container.getEventBus().addListener(BeforeDgCloseEvent.class,
				new ListenerI<BeforeDgCloseEvent>() {

					@Override
					public void handle(BeforeDgCloseEvent t) {
						EventDispatcherSupport.this.handleBeforeDgCloseEvent(t);
					}
				});

	}

	/*
	 * Dec 28, 2012
	 */
	@Override
	public ServiceEngineI getEngine() {
		//
		return this.engine;
	}

	/**
	 * Dec 17, 2012
	 */
	protected void handleBeforeDgCloseEvent(BeforeDgCloseEvent t) {

		// before close of grid,shutdown now.
		this.shutdown();//

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
		if (this.executor != null) {
			throw new FsException("already started?");
		}
		this.eventQueue = this.resolveEventQueue();
		this.executor = Executors.newFixedThreadPool(1);// TODO
		this.future = this.executor.submit(new Callable<Object>() {

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

		if (this.executor == null) {
			throw new FsException("already shutdown?");
		}
		try {
			LOG.info("waiting task return.");
			this.future.get();
			LOG.info("done of task waiting.");

		} catch (InterruptedException e) {
			throw new FsException(e);
		} catch (ExecutionException e) {
			this.onException(e.getCause());
		}
		this.executor.shutdown();//
		this.executor = null;//

	}

	public void run() {
		try {
			this.runInternal();
		} catch (Throwable t) {
			this.onException(t);

			if (this.isState(ServerI.RUNNING)) {
				LOG.warn("shutdown event dispatcher:" + this.getConfiguration().getName()
						+ " for the task abnormally return");
			}
			this.shutdown();
		}

	}

	public void runInternal() {

		while (this.isState(ServerI.RUNNING, ServerI.STARTING)) {

			EventGd e = this.nextEvent();

			if (e == null) {// shutdown?
				if (!this.isState(ServerI.SHUTINGDOWN)) {

					throw new FsException("running, but event is null");
				} else {

					LOG.info("stop event processing loop,since this server is shutdown. ");

					break;//
				}
			}
			try {
				this.handleEvent(e);
			} catch (Throwable t) {
				this.onException(e, t);
			} finally {
				this.eventCounter++;
			}
		}
	}

	protected EventGd nextEvent() {

		while (true) {

			EventGd rt = this.eventQueue.poll(2000, TimeUnit.MILLISECONDS);

			if (rt != null) {
				return rt;
			}
			if (this.isState(ServerI.SHUTINGDOWN)) {
				return null;

			}
		}
	}

	protected void onException(Throwable t) {
		LOG.error("exeception got,eventQueue:" + this.getConfiguration().getName(), t);
	}

	protected void onException(EventGd evt, Throwable t) {
		LOG.error("exception got,eventQueue:" + this.getConfiguration().getName() + ", event:" + evt, t);
	}

	public void handleEvent(EventGd evt) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("dispatcher:" + this.config.getName() + " is processing event#" + this.eventCounter
					+ "," + evt);
		}
		String path = evt.getPath();

		if (path.startsWith("/")) {
			path = "/events" + path;
		} else {
			path = "/events/" + path;
		}

		RequestI req = RRContext.newRequest(path);
		req.setHeaders(evt.getHeaders());//
		req.setPayload(evt);

		ResponseI res = this.engine.service(req);
		this.onResponse(req, res);
	}

	protected void onResponse(RequestI req, ResponseI res) {

		ErrorInfos eis = res.getErrorInfos();

		// process error
		if (!eis.hasError()) {
			LOG.error("response contains error for request:" + req, res);
		}

		String ra = req.getResponseAddress();
		if (ra == null) {//
			LOG.warn("no response address for request:" + req);
		} else {
			if (!ra.startsWith("tid://")) {
				throw new FsException("address not supported:" + ra + ", only 'tid://' is supported for now");
			}
			String tid = ra.substring("tid://".length());

			TerminalManagerI tm = this.facade.getEntityManager(TerminalManagerI.class);

			tm.sendMessage(tid, res);

		}

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
