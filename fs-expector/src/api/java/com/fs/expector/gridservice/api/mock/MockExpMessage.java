/**
 *  Jan 21, 2013
 */
package com.fs.expector.gridservice.api.mock;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.ProxyMessageSupport;

/**
 * @author wuzhen
 * 
 */
public class MockExpMessage extends ProxyMessageSupport {

	public MockExpMessage(MessageI msg) {
		super(msg);
	}

	public String getExpId2() {
		return (String) this.getHeader("expId2", true);
	}

	public String getAccountId2() {
		return (String) this.getHeader("accountId2", true);
	}

	public String getBody() {
		return (String) this.getPayload("body", true);
	}

	public String getAccountId1() {
		return (String) this.getHeader("accountId1", true);
	}

	public String getTimestamp() {
		return (String) this.getPayload("timestamp", true);
	}
}
