/**
 *  Dec 28, 2012
 */
package com.fs.gridservice.commons.api.mock;

import java.util.concurrent.Future;

import com.fs.commons.api.event.ListenerI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public abstract class MockClient {

	public abstract Future<MockClient> connect() ;
	
	public abstract Future<Object> startEventDrive();

	public abstract void addListener(String path, ListenerI<MessageI> ml);

	public abstract MockClient auth(PropertiesI<Object> credential);

	public abstract Future<MessageI> receiveMessage();

	public abstract String getSessionId();

	public abstract String getTerminalId();

	public abstract void sendMessage(MessageI msg);

	public abstract String getAccountId();

}
