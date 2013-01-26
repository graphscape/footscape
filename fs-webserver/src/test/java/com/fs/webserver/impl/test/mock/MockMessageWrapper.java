/**
 *  Dec 12, 2012
 */
package com.fs.webserver.impl.test.mock;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.message.support.ProxyMessageSupport;

/**
 * @author wuzhen
 * 
 */
public class MockMessageWrapper extends ProxyMessageSupport {

	private String from;

	private String to;

	private String text;

	public MockMessageWrapper(MessageI t) {
		super(t);
		this.from = t.getString("from");
		this.to = t.getString("to");
		this.text = t.getString("text");
	}

	public static MockMessageWrapper valueOf(String from, String to, String text) {
		MessageI m = new MessageSupport();
		m.setPayload("from", from);
		m.setPayload("to", to);
		m.setPayload("text", text);
		return new MockMessageWrapper(m);

	}

	public static MockMessageWrapper valueOf(MessageI msg) {
		return new MockMessageWrapper(msg);
	}

	@Override
	public String toString() {
		return this.from + "," + this.to + "," + this.text;
	}

	public void assertEquals(String from, String to, String text) {
		if (ObjectUtil.nullSafeEquals(from, this.from) && ObjectUtil.nullSafeEquals(to, this.to)
				&& ObjectUtil.nullSafeEquals(text, this.text)) {
			return;
		}
		throw new FsException("expected:" + from + ",to:" + to + ",text:" + text + ",but was:" + this);
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getText() {
		return text;
	}

}
