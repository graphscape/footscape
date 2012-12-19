/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.commons.impl.test.cases;

import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.impl.test.cases.support.TestBase;
import com.fs.gridservice.commons.impl.test.mock.MockClient;

/**
 * @author wuzhen
 * 
 */
public class GdSessionTest extends TestBase {

	public void testSession() throws Exception {

		// assert websocket is refed.
		MockClient client = this.newClientAndAuth("acc1");

		SessionManagerI sm = this.facade.getSessionManager();
		String sid = client.getSessionId();
		SessionGd s = sm.getSession(sid);
		assertNotNull("session is not in manager", s);

		TerminalManagerI tm = this.facade
				.getEntityManager(TerminalManagerI.class);
		String tId = s.getTerminalId();

		TerminalGd t = tm.getTerminal(tId);
		assertNotNull("terminal is not in manager", t);
		String text = "this is a text message from server.";
		tm.sendTextMessage(tId, text);

		// assert the client received.
		MessageI msg = client.receiveMessage().get();
		// TODO assert equal
		String text2 = (String) msg.getPayload("text");
		assertEquals("message received not equals to the sent.", text, text2);
	}

}
