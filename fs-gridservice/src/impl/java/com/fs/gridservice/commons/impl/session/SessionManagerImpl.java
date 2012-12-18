/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.commons.impl.session;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.data.ObjectRefGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.impl.support.FacadeAwareConfigurableSupport;
import com.fs.gridservice.core.api.objects.DgMapI;

/**
 * @author wuzhen
 * 
 */
public class SessionManagerImpl extends FacadeAwareConfigurableSupport
		implements SessionManagerI {

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
		ObjectRefGd<WebSocketGoI> wsr = this.facade
				.getWebSocketGridedObjectManager().getRef(wsId);

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
	public ObjectRefGd<WebSocketGoI> bindingWebSocket(String sid, String wsoId) {
		//
		ObjectRefGd<WebSocketGoI> wso = this.facade
				.getWebSocketGridedObjectManager().getRef(wsoId, true);

		this.facade.getSessionWebSocketIdMap().put(sid, wsoId);

		return wso;
	}

}
