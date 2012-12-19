/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.test.cases.chat;

import java.util.concurrent.TimeUnit;

import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.commons.impl.test.cases.support.TestBase;
import com.fs.gridservice.commons.impl.test.mock.MockClientChatGroup;
import com.fs.gridservice.commons.impl.test.mock.MockEventDriveClient;

/**
 * @author wu
 * 
 */
public class GroupChatTest extends TestBase {

	public void testGroupChat() throws Exception {

		MockEventDriveClient c1 = this.newEventDriveClient("acc1");
		MockEventDriveClient c2 = this.newEventDriveClient("acc2");

		MockClientChatGroup gc1 = joinChatRoom("gc1", c1);
		MockClientChatGroup gc2 = joinChatRoom("gc1", c2);

		gc1.sendMessage("from c1");
		MessageI msg = gc2.receiveMessage(5000, TimeUnit.MILLISECONDS);
	}

	protected MockClientChatGroup joinChatRoom(String gcid, MockEventDriveClient mc) throws Exception {
		MockClientChatGroup rt = new MockClientChatGroup(gcid, mc);
		rt.join();
		return rt;

	}

}
