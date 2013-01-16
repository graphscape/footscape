/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 12, 2013
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.support.CollectionHandler;

/**
 * @author wu
 * 
 */
public abstract class TestWorker {

	protected CollectionHandler<AllTaskDoneEvent> handlers;

	protected boolean isDone;

	public TestWorker() {
		this.handlers = new CollectionHandler<AllTaskDoneEvent>();
	}

	public void addHandler(HandlerI<AllTaskDoneEvent> h) {
		this.handlers.addHandler(h);
	}

	public boolean isDone() {
		return this.isDone;
	}

	public void allTaskDone() {
		AllTaskDoneEvent t = new AllTaskDoneEvent();
		this.handlers.handle(t);
		this.isDone = true;
	}

	public abstract void start(UiClientI client);

}
