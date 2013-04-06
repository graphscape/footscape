/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.support;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.event.ListenerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.server.ServerI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.support.ServerSupport;
import com.fs.commons.api.value.ErrorInfos;
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

	protected MessageServiceI engine;

	protected DgQueueI<EventGd> eventQueue;

	// receive event and dispatch
	protected ExecutorService mainExecutor;

	// processing event by engine.
	protected ExecutorService slaveExecutor;

	private int eventCounter;

	protected GridFacadeI facade;

	protected Future<Object> future;

	protected long jobTimeoutMs = 30 * 1000;// default timeout of message
											// processing.

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		this.facade = this.container.find(GridFacadeI.class, true);
		String engineName = this.config.getProperty("engine", true);

		this.engine = this.container.find(MessageServiceI.FactoryI.class, true).create(engineName);//
		// handlers
		List<String> names = this.config.getPropertyAsCsv("handlers");

		this.engine.getDispatcher().addHandlers(this.configId, names.toArray(new String[] {}));

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
	public MessageServiceI getEngine() {
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
		if (this.mainExecutor != null) {
			throw new FsException("already started?");
		}
		this.eventQueue = this.resolveEventQueue();
		this.mainExecutor = Executors.newFixedThreadPool(1);// TODO

		this.future = this.mainExecutor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				//
				EventDispatcherSupport.this.run();
				return null;

			}
		});
		// slave executor
		this.slaveExecutor = Executors.newFixedThreadPool(1);// NOTE?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.ServerSupport#doShutdown()
	 */
	@Override
	protected void doShutdown() {

		if (this.mainExecutor == null) {
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
		this.mainExecutor.shutdown();//
		this.mainExecutor = null;//
		this.slaveExecutor.shutdown();
		this.slaveExecutor = null;//

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

			int ec = this.eventCounter;
			if (LOG.isDebugEnabled()) {
				LOG.debug("dispatcher:" + this.config.getName() + " is processing event#" + ec + "," + e);
			}

			try {
				this.handleEvent(e);
			} catch (Throwable t) {
				this.onException(e, t);
			} finally {
				this.eventCounter++;
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("dispatcher:" + this.config.getName() + " have processed event#" + ec);
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

	public void handleEvent(final EventGd evt) {
		// final MessageI req = new MessageSupport();
		// req.setHeaders(evt.getHeaders());//
		// req.setHeader(MessageI.HK_PATH, p.toString());// override the path;
		// req.setPayload(evt);
		Future<ResponseI> fres = this.slaveExecutor.submit(new Callable<ResponseI>() {

			@Override
			public ResponseI call() throws Exception {
				return EventDispatcherSupport.this.handleInSlave(evt);
			}
		});

		ResponseI res = null;
		Throwable t = null;
		try {
			res = fres.get(this.jobTimeoutMs, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			t = e;
		} catch (ExecutionException e) {
			t = e;
		} catch (TimeoutException e) {
			t = e;
		}

		if (t == null) {// no error,got response
			this.tryResponse( evt, res);
		} else {// cannot got response
			fres.cancel(true);
			this.onException(evt, t);
		}
	}

	// public static final String HK_RESPONSE_HANDLER_ID =
	// "response_handler_id";

	protected ResponseI handleInSlave(EventGd req) {

		ResponseI rt = this.engine.service(req);
		// rt.setHeader(HK_RESPONSE_HANDLER_ID,
		// req.getHeader(HK_RESPONSE_HANDLER_ID));
		return rt;
	}

	protected void tryResponse(EventGd req, ResponseI res) {

		ErrorInfos eis = res.getErrorInfos();

		// process error
		if (eis.hasError()) {
			LOG.error("response contains error for request:" + req, res);
		}
		if (req.isSilence()) {// not response required by client.
			return;
		}
		Path spath = req.getSourcePath();
		// response path is depended on the success or failure
		Path path = spath.getSubPath(eis.hasError() ? "failure" : "success");
		// response path
		res.setHeader(MessageI.HK_PATH, path.toString());
		// translate error

		// send response to terminal
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

}
