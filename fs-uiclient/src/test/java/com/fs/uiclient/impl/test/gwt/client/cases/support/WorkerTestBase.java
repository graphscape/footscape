/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.support;

import com.fs.uiclient.impl.gwt.client.testsupport.TestWorker;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * 
 */
public abstract class WorkerTestBase<T extends TestWorker> extends TestBase {
	T worker;

	protected void onAnonymousUserLogin(UserLoginEvent le) {
		worker = this.newWorker(le);
		worker.start(this.client);

	}

	protected abstract T newWorker(UserLoginEvent le);

	/*
	 * Jan 12, 2013
	 */
	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		if (this.worker == null) {
			return;
		}

		if (this.worker.isDone()) {
			this.done();
		}
	}

	protected abstract void done();

}
