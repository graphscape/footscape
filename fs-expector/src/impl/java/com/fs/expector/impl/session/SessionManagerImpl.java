/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.session;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.value.PropertiesI;
import com.fs.datagrid.api.objects.DgMapI;
import com.fs.expector.api.data.ObjectRefGd;
import com.fs.expector.api.data.SessionGd;
import com.fs.expector.api.gobject.WebSocketGoI;
import com.fs.expector.api.session.SessionManagerI;
import com.fs.expector.impl.support.FacadeAwareConfigurableSupport;

/**
 * @author wuzhen
 * 
 */
public class SessionManagerImpl extends FacadeAwareConfigurableSupport implements SessionManagerI {

	public SessionManagerImpl() {

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public SessionGd createSession(PropertiesI<Object> pts) {
		//
		SessionGd rt = new SessionGd(pts);

		this.facade.getSessionMap().put(rt.getId(), rt);

		return rt;
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public ObjectRefGd<WebSocketGoI> getWebSocketRefBySessionId(String sid) {
		DgMapI<String, String> map = this.facade.getSessionWebSocketIdMap();
		String wsId = map.getValue(sid);
		ObjectRefGd<WebSocketGoI> wsr = this.facade.getWebSocketGridedObjectManager().getRef(wsId);

		return wsr;
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public SessionGd getSession(String id) {
		//
		return this.facade.getSessionMap().getValue(id);
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public ObjectRefGd<WebSocketGoI> bindingWebSocket(String sid, String wsId) {
		//
		ObjectRefGd<WebSocketGoI> wso = this.facade.getWebSocketGridedObjectManager().getRef(wsId);
		if (wso == null) {
			throw new FsException("No websocket object:" + wsId);
		}
		this.facade.getSessionWebSocketIdMap().put(sid, wsId);

		return wso;
	}

}
