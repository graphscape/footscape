/**
 *  Dec 28, 2012
 */
package com.fs.gridservice.commons.api.mock;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen TODO move to web socket
 */
public abstract class MockClient {

	public abstract DispatcherI<MessageContext> getDispatcher();

	public abstract MockClient connect();

	public abstract MockClient auth(PropertiesI<Object> credential);

	public abstract String getSessionId();

	public abstract String getTerminalId();

	public abstract void sendMessage(MessageI msg);

	public abstract String getAccountId();

	public abstract void close();

}
