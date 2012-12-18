/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.gridservice.commons.api.session;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.data.ObjectRefGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;

/**
 * @author wu
 * 
 */
public interface SessionManagerI {

	public SessionGd createSession(PropertiesI<Object> pts);

	public SessionGd getSession(String id);

	public ObjectRefGd<WebSocketGoI> bindingWebSocket(String sid, String wsId);

	public ObjectRefGd<WebSocketGoI> getWebSocketRefBySessionId(String sid);

}
