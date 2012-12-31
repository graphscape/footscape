/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.mock;

import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jetty.websocket.client.WebSocketClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.mock.MockClient;

/**
 * @author wu
 * 
 */
public class MockClientImpl extends MockClientBase {

	private static final Logger LOG = LoggerFactory
			.getLogger(MockClientImpl.class);

	protected ExecutorService executor;

	protected MessageServiceI engine;

	protected String name;

	public MockClientImpl(String name, WebSocketClientFactory f, ContainerI c,
			URI uri) {
		super(f, c, uri);
		this.name = name;
		this.executor = Executors.newFixedThreadPool(1);// TODO
		this.engine = c.find(MessageServiceI.FactoryI.class, true).create(
				"mock-client-" + name);
	}

	@Override
	public MockClient connect() {
		this.executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				//
				MockClientImpl.this.run();
				return "stoped";
			}
		});
		super.connect();
		return this;
	}

	public void eachLoop(MessageI msg) throws Exception {
		String path = msg.getHeader(MessageI.HK_PATH);
		Path v = Path.valueOf(path);
		ResponseI res = this.engine.service(msg);
		if (res.getErrorInfos().hasError()) {
			LOG.error("", res);
		}
	}

	public void run() {
		try {
			this.runInternal();
		} catch (Throwable t) {
			LOG.error("exit loop with exception", t);
		}
	}

	public void runInternal() throws Exception {

		while (true) {
			MessageI msg = this.messageReceived.take();
			this.eachLoop(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.commons.api.mock.MockClient#getDispatcher()
	 */
	@Override
	public DispatcherI<MessageContext> getDispatcher() {
		return this.engine.getDispatcher();
	}

}
