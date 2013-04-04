/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 28, 2013
 */
package com.fs.uicore.api.gwt.client.endpoint;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public interface MessageCacheI extends UiObjectI {

	public void addMessage(MessageData md);

	public void addMessage(MsgWrapper mw);

	public MessageData getMessage(String id);
	
	public MessageData removeMessage(String id);
	
	public int size();

	public void start();

	public void stop();

}
