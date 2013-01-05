/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.commons.impl.test.cases;

import java.util.ArrayList;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.QueueMessageHandler;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.data.ClientGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.mock.MockClient;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class GdSessionTest extends TestBase {

	public void testSession() throws Exception {
		TerminalManagerI tm = this.facade.getEntityManager(TerminalManagerI.class);
		SessionManagerI sm = this.facade.getSessionManager();

		// assert websocket is refed.
		MockClient client = this.newClientAndAuth("acc1");
		String sid = client.getSessionId();
		String tid = client.getTerminalId();
		{
			//
			SessionGd s = sm.getSession(sid);
			assertNotNull("session is not in manager", s);
			//
			String cid = s.getClientId();
			ClientGd c = this.cmanager.getEntity(cid, false);
			assertNotNull("client not found with id:" + cid + " from session:" + sid, c);

			String tid2 = c.getTerminalId();
			assertEquals(tid, tid2);
			TerminalGd t = tm.getTerminal(tid);
			assertNotNull("terminal is not in manager for tid:" + tid, t);

			// session->terminal
			TerminalGd t2 = tm.getTerminalBySessionId(sid, false);
			assertNotNull("terminal not found with sessionid:" + sid);
			assertEquals("terminal by session should be same as that from client.", t.getId(), t2.getId());
			// send message to client side
			String text = "this is a text message from server.";

			QueueMessageHandler mh = new QueueMessageHandler();
			client.getDispatcher().addHandler(Path.ROOT, mh);
			tm.sendTextMessage(tid, text);

			// assert the client received.
			MessageI msg = mh.take().getRequest();//
			// TODO assert equal
			String text2 = (String) msg.getPayload("text");
			assertEquals("message received not equals to the sent.", text, text2);
		}
		// auth to another acc
		PropertiesI<Object> cre = new MapProperties<Object>();
		cre.setProperty(SessionGd.ACCID, "acc2");//
		client.auth(cre);
		String sid2 = client.getSessionId();
		assertNotSame("client session id should changed", sid, sid2);

		{
			SessionGd old = sm.getSession(sid);
			assertNull("old session should removed", old);

			String tid2 = client.getTerminalId();
			assertEquals("terminal should not chage", tid, tid2);
			SessionGd s = sm.getSession(sid2);
			assertNotNull("new session not exist", s);
			String accId = s.getAccountId();
			assertNotSame("acc1", accId);
			assertEquals("account should be new", "acc2", accId);
		}

	}

}
