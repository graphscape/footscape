/**
 *  Dec 26, 2012
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * TODO
 */
public final class ResetTestWorker extends AbstractTestWorker {

	public ResetTestWorker() {
		this.tasks.add("reset.done");
	}

	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
	}

	@Override
	public void start(UiClientI client) {
		super.start(client);
		this.doStart();
	}

	protected void doStart() {
		
		MsgWrapper req = new MsgWrapper(Path.valueOf("test/reset"));
		req.setPayload("credential", "reset-test-worker/password");

		//TODO this.endpoint.sendMessage(req);
	}

	@Override
	protected void onSuccessMessageEvent(SuccessMessageEvent e) {
		super.onSuccessMessageEvent(e);
	}

	/**
	 * 
	 */

}
