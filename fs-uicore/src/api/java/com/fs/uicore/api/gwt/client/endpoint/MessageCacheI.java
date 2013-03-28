/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 28, 2013
 */
package com.fs.uicore.api.gwt.client.endpoint;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public interface MessageCacheI {
	
	public void addMessage(MessageData md);

	public void addMessage(MsgWrapper mw);
	
	public MessageData getMessage(String id);
	
	public void start();
	
	public void stop();
	
}
