/**
 *  Dec 28, 2012
 */
package com.fs.gridservice.commons.api.mock;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public class ProxyMockClient extends MockClient {

	protected MockClient target;

	public ProxyMockClient(MockClient target) {
		this.target = target;
	}

	public MockClient auth(PropertiesI<Object> accId) {
		this.target.auth(accId);
		return this;
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

	/*
	 * Dec 28, 2012
	 */
	@Override
	public MockClient connect() {
		//
		this.target.connect();
		return this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.commons.api.mock.MockClient#getDispatcher()
	 */
	@Override
	public DispatcherI<MessageContext> getDispatcher() {
		// TODO Auto-generated method stub
		return this.target.getDispatcher();
	}

}
