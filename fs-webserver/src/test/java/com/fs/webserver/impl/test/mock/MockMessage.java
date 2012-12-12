/**
 *  Dec 12, 2012
 */
package com.fs.webserver.impl.test.mock;

/**
 * @author wuzhen
 * 
 */
public class MockMessage {

	private String from;

	private String to;

	private String text;

	public MockMessage(String from, String to, String text) {
		this.from = from;
		this.to = to;
		this.text = text;
	}

	public static MockMessage parse(String message) {
		String[] ss = message.split(",");
		return new MockMessage(ss[0], ss[1], ss[2]);
	}

	public String forSending() {
		return this.from + "," + this.to + "," + this.text;
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
