/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.fs.commons.impl.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.lang.Duplicated;
import com.fs.commons.api.server.ServerI;
import com.fs.commons.api.session.SessionManagerI;
import com.fs.commons.api.session.SessionServerI;
import com.fs.commons.api.support.ServerSupport;

/**
 * @author wu
 * 
 */
public class SessionServerImpl extends ServerSupport implements SessionServerI {

	private static final Logger LOG = LoggerFactory.getLogger(SessionServerImpl.class);

	private Map<String, SessionManagerImpl> managers = new HashMap<String, SessionManagerImpl>();

	private ExecutorService executor;

	/*
	 * May 10, 2013
	 */
	@Override
	public void cmd(String cmd) {
		//

	}

	/*
	 * May 10, 2013
	 */
	@Override
	protected void doStart() {
		//
		this.executor = Executors.newSingleThreadExecutor();

		this.executor.submit(new Callable() {

			@Override
			public Object call() throws Exception {
				//
				SessionServerImpl.this.run();
				return null;
			}
		});

	}

	/**
	 * May 10, 2013
	 */
	protected void run() {
		try {

			while (this.isState(ServerI.RUNNING, ServerI.STARTING)) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {

				}
			}
		} catch (Throwable t) {
			LOG.error("excetion when monitoring the sessions,sessions may leak", t);
		}
	}

	/*
	 * May 10, 2013
	 */
	@Override
	protected void doShutdown() {
		//
		this.executor.shutdown();
		try {
			boolean rt = this.executor.awaitTermination(10, TimeUnit.SECONDS);
			if (!rt) {
				// warning?
			}
		} catch (InterruptedException e) {

		}

	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionManagerI createManager(String name) {
		//
		if (null != this.getManager(name)) {
			throw new Duplicated(name);
		}
		SessionManagerImpl rt = new SessionManagerImpl(name);
		this.managers.put(name, rt);
		return rt;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionManagerI getManager(String name) {
		//

		return this.managers.get(name);

	}

}
