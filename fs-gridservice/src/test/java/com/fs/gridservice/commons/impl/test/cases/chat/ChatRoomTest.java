/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.test.cases.chat;

import com.fs.gridservice.commons.impl.test.cases.support.TestBase;
import com.fs.gridservice.commons.impl.test.mock.MockClientChatRoom;
import com.fs.gridservice.commons.impl.test.mock.MockEventDriveClient;

/**
 * @author wu
 * 
 */
public class ChatRoomTest extends TestBase {

	public void testGroupChat()throws Exception{
		
		MockEventDriveClient c1 = this.newEventDriveClient("acc1");
		MockEventDriveClient c2 = this.newEventDriveClient("acc2");
		
		MockClientChatRoom gc1 = joinChatRoom("gc1",c1);
		MockClientChatRoom gc2 = joinChatRoom("gc1",c2);
		
		gc1.sendMessage("from c1");
		
	}
	
	protected MockClientChatRoom joinChatRoom(String gcid, MockEventDriveClient mc) throws Exception {
		MockClientChatRoom rt =	new MockClientChatRoom(gcid,mc);
		rt.join();
		return rt;
		
	}
	
}
