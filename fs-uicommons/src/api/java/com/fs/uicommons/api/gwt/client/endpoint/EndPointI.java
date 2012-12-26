/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.endpoint;

import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public interface EndPointI extends UiObjectI {
	
	public static final String CP_WEBSOCKET_DISABLE = "websocket.enable";
	
	public static final String D_NAME = "endpoint";

	public void sendMessage(MessageData req);

	public void addMessageHandler(String path,
			EventHandlerI<EndpointMessageEvent> hdl);

	public boolean isOpen();

	public boolean isBond();

	public String getSessionId();

	public String getUri();

}
