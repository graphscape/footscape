/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.mock;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.client.WebSocketClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.event.ListenerI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.support.CollectionListener;

/**
 * @author wu
 * 
 */
public class MockClientImpl extends MockClientBase {

	private static final Logger LOG = LoggerFactory
			.getLogger(MockClientImpl.class);

	protected Map<String, CollectionListener<MessageI>> messageHandler;

	protected ListenerI<MessageI> defaultListener;

	protected ExecutorService executor;

	public MockClientImpl(WebSocketClientFactory f, ContainerI c, URI uri) {
		super(f, c, uri);
		this.executor = Executors.newFixedThreadPool(1);// TODO
		this.messageHandler = new HashMap<String, CollectionListener<MessageI>>();
		this.defaultListener = new ListenerI<MessageI>() {

			@Override
			public void handle(MessageI t) {
				LOG.info("default listener,message:" + t);
			}

		};
	}

	public Future<Object> startEventDrive() {
		return this.executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				//
				MockClientImpl.this.run();
				return "stoped";
			}
		});
	}

	@Override
	public void addListener(String path, ListenerI<MessageI> ml) {
		CollectionListener<MessageI> cl = this.messageHandler.get(path);
		if (cl == null) {
			cl = new CollectionListener<MessageI>();
			this.messageHandler.put(path, cl);
		}
		cl.addListener(ml);
	}

	public void eachLoop(MessageI msg) throws Exception {
		String path = msg.getHeader(MessageI.HK_PATH);
		ListenerI<MessageI> l = this.messageHandler.get(path);
		if (l == null) {
			this.defaultListener.handle(msg);
		} else {
			l.handle(msg);
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
			MessageI msg = this.receiveMessage().get();
			this.eachLoop(msg);
		}
	}

}