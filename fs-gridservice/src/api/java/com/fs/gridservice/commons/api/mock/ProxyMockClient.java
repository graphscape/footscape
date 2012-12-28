/**
 *  Dec 28, 2012
 */
package com.fs.gridservice.commons.api.mock;

import java.util.concurrent.Future;

import com.fs.commons.api.event.ListenerI;
import com.fs.commons.api.message.MessageI;

/**
 * @author wuzhen
 * 
 */
public class ProxyMockClient extends MockClient {

	protected MockClient target;

	public ProxyMockClient(MockClient target) {
		this.target = target;
	}

	public Future<Object> startEventDrive() {
		return this.target.startEventDrive();
	}

	public void addListener(String path, ListenerI<MessageI> ml) {
		this.target.addListener(path, ml);
	}

	public MockClient auth(String accId) {
		this.target.auth(accId);
		return this;
	}

	public Future<MessageI> receiveMessage() {
		return this.target.receiveMessage();
	}

	public String getSessionId() {
		return this.target.getSessionId();
	}

	public String getTerminalId() {
		return this.target.getTerminalId();
	}

	public void sendMessage(MessageI msg) {
		this.target.sendMessage(msg);
	}

	public String getAccountId() {
		return this.target.getAccountId();
	}

}
