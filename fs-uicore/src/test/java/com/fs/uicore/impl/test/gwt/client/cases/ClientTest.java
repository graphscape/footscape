/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import org.junit.Test;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ClientTest extends TestBase {

	@Test
	public void testClient() {
		this.finishing.add("authsuccess");
		this.delayTestFinish(30 * 1000);

	}

	@Override
	protected void onClientStart(AfterClientStartEvent e) {
		EndPointI ep = this.client.getEndpoint();
		ep.getMessageDispatcher().addHandler(Path.valueOf("/terminal/auth/success"), new MessageHandlerI() {

			@Override
			public void handle(MessageData t) {
				ClientTest.this.onAuthSuccess();
			}
		});
		MessageData req = new MessageData(Path.valueOf("/terminal/auth"));
		req.setPayload("", StringData.valueOf(""));

		ep.sendMessage(req);

	}

	public void onAuthSuccess() {
		this.tryFinish("authsuccess");
	}

}
