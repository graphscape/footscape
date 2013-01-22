/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.api.mock;

import java.util.List;

import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.commons.api.mock.MockClient;
import com.fs.gridservice.commons.api.mock.ProxyMockClient;

/**
 * @author wuzhen
 * 
 */
public abstract class MockExpectorClient extends ProxyMockClient {

	/**
	 * @param c
	 */
	public MockExpectorClient(MockClient mc) {
		super(mc);
	}

	public abstract MessageI syncSendMessage(MessageI msg, int timeout);

	public abstract void signup(String email, String nick, String pass);

	public abstract void start(String email, String nick, String pass);

	public abstract String newExp(String body1);

	/**
	 * Dec 30, 2012
	 */
	public abstract String cooperRequest(String expId1, String expId2);

	/**
	 * Dec 30, 2012
	 */
	public abstract void cooperConfirm(String crid, boolean b);

	/**
	 * Dec 30, 2012
	 */
	public abstract List<MockActivity> refreshActivity();

	public abstract List<MockExpItem> search(boolean includeMine, int from, int max, String expId,
			String phrase, int slop);

	/**
	 * Dec 30, 2012
	 */
	public abstract MockActivityDetail getActivityDetail(String actId);
	
	public abstract void close();

}
