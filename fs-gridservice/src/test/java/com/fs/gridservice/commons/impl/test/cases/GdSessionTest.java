/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.commons.impl.test.cases;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.GridedObjectManagerI;
import com.fs.gridservice.commons.api.data.ObjectRefGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.impl.test.cases.support.TestBase;
import com.fs.gridservice.commons.impl.test.mock.MockClient;
import com.fs.gridservice.commons.impl.test.mock.MockClientFactory;

/**
 * @author wuzhen
 * 
 */
public class GdSessionTest extends TestBase {

	public void testSession() throws Exception {

		

		// assert websocket is refed.
		MockClient client = this.newClient("acc1");

		ObjectRefGd<WebSocketGoI> wsr = smanager.getWebSocketRefBySessionId(client.getSessionId());

		assertNotNull("the web socket is not shared to grid", wsr);

		String mid1 = this.facade.getLocalMember().getId();
		String mid2 = wsr.getMemberId();

		assertEquals("the web socket grid member not correct.", mid1, mid2);
		String wsId = wsr.getId();//
		GridedObjectManagerI<WebSocketGoI> wsgm = this.facade.getWebSocketGridedObjectManager();

		WebSocketGoI wsg = wsgm.getGridedObject(wsId);
		assertNotNull("web socket go not found in local gomanager.", wsg);

		String text = "this is a text message from server.";

		wsg.sendTextMessage(text);
		// assert the client received.
		MessageI msg = client.receiveMessage().get();
		// TODO assert equal
		String text2 = (String) msg.getPayload();
		assertEquals("message received not equals to the sent.", text, text2);
	}

}
