/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.test.cases;

import java.net.URI;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.api.GridedObjectManagerI;
import com.fs.expector.api.data.ObjectRefGd;
import com.fs.expector.api.data.SessionGd;
import com.fs.expector.api.gobject.WebSocketGoI;
import com.fs.expector.api.session.SessionManagerI;
import com.fs.expector.impl.test.cases.support.TestBase;
import com.fs.expector.impl.test.mock.MockClient;
import com.fs.expector.impl.test.mock.MockClientFactory;

/**
 * @author wuzhen
 * 
 */
public class GdSessionTest extends TestBase {

	public void testSession() throws Exception {
		MockClientFactory cf = new MockClientFactory().start();
		SessionManagerI sm = this.container.find(SessionManagerI.class, true);
		PropertiesI<Object> pts = new MapProperties<Object>();
		SessionGd s = sm.createSession(pts);
		// assertThe Session is shared with Grid.
		String sid = s.getId();
		//
		URI uri = new URI("ws://localhost:8080/wsa/default");// default
																// wsManager.
		MockClient client = cf.newClient(sid, this.container, uri);
		client = client.connect().get();
		//
		client.binding(sid);

		// assert websocket is refed.

		ObjectRefGd<WebSocketGoI> wsr = sm.getWebSocketRefBySessionId(sid);

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
