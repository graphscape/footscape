/**
 *  Dec 12, 2012
 */
package com.fs.webcomet.impl.test.mock;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.message.support.ProxyMessageSupport;

/**
 * @author wuzhen
 * 
 */
public class MockMessageWrapper extends ProxyMessageSupport {

	private String text;

	public MockMessageWrapper(MessageI t) {
		super(t);
		this.text = t.getString("text");
	}

	public static MockMessageWrapper valueOf(String path, String text) {
		MessageI m = new MessageSupport(path);
		m.setPayload("text", text);
		return new MockMessageWrapper(m);

	}

	public static MockMessageWrapper valueOf(MessageI msg) {
		return new MockMessageWrapper(msg);
	}

	public String getWsId(boolean force) {
		String rt = this.getHeader("wsId", force);

		return rt;

	}

	public String getText() {
		return this.text;
	}

}
