/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.terminal;

import com.fs.uicommons.api.gwt.client.terminal.event.MessageEvent;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 *
 */
public interface TerminalI extends UiObjectI{
	
	public void sendMessage(MessageData req);
	
	public void addMessageHandler(String path, HandlerI<MessageEvent> hdl);
	
}
