/**
 * Jul 28, 2012
 */
package com.fs.expector.gridservice.api.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.jexl.ExpressionI;
import com.fs.commons.api.jexl.JexlEngineI;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.expector.gridservice.api.Constants;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.client.ClientManagerI;
import com.fs.gridservice.commons.api.data.ClientGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.support.TerminalMsgReseiveEventHandler;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;

/**
 * @author wu
 * 
 */
public class ExpectorTMREHSupport extends TerminalMsgReseiveEventHandler {

	protected DataServiceI dataService;
	
	protected JexlEngineI jexl;
	
	protected GridFacadeI facade;

	protected ClientManagerI clientManager;

	protected TerminalManagerI terminalManager;

	protected SessionManagerI sessionManager;
	
	protected String prefix = "payloads.property['_default'].payloads.property['_message'].payloads.property";
	

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		this.dataService = ac.getContainer().find(DataServiceI.class, true);

		jexl = this.container.find(JexlEngineI.class, true);

		this.facade = this.container.find(GridFacadeI.class, true);

		this.clientManager = this.facade.getEntityManager(ClientManagerI.class);
		this.terminalManager = this.facade
				.getEntityManager(TerminalManagerI.class);
		this.sessionManager = this.facade.getSessionManager();
	}

	protected <T extends NodeWrapper> T createNode(String expPrefix,
			HandleContextI hc, Class<T> cls) {
		return this.createNode(expPrefix, hc, cls,
				new HashMap<String, String>());
	}

	protected <T extends NodeWrapper> T createNode(String expPrefix,
			HandleContextI hc, Class<T> cls, Map<String, String> payloadKeyMap) {
		T rt = ClassUtil.newInstance(cls);
		rt.forCreate(this.dataService);
		for (Map.Entry<String, String> e : payloadKeyMap.entrySet()) {
			String exp = e.getKey();
			String nodeProperty = e.getValue();
			exp = expPrefix + exp;
			ExpressionI ex = jexl.createExpression(exp);
			Object obj = ex.evaluate(hc);
			rt.setProperty(nodeProperty, obj);// TODO null?
		}

		return rt;

	}

	protected <T extends NodeWrapper> NodeQueryResultI findNodeList(
			String expPrefix, HandleContextI hc, NodeType type, Class<T> cls,
			Map<String, String> payloadKeyMap) {
		return this.findNodeList(expPrefix, hc, type, cls, payloadKeyMap, true);//
	}

	protected <T extends NodeWrapper> NodeQueryResultI<T> findNodeList(
			String expPrefix, HandleContextI hc, NodeType type, Class<T> cls,
			Map<String, String> payloadKeyMap, boolean sortTimeStampDesc) {
		NodeQueryOperationI<T> dbo = this.dataService
				.prepareOperation(NodeQueryOperationI.class);
		dbo.nodeType(type);

		for (Map.Entry<String, String> e : payloadKeyMap.entrySet()) {
			String exp = e.getKey();
			String nodeProperty = e.getValue();
			exp = expPrefix + exp;
			ExpressionI ex = jexl.createExpression(exp);
			Object obj = ex.evaluate(hc);
			dbo.propertyEq(nodeProperty, obj);// TODO null?
		}

		if (sortTimeStampDesc) {
			dbo.sortTimestamp(true);
		}

		NodeQueryResultI<T> rt = dbo.execute().getResult().assertNoError();// TODO
																			// error
																			// info
																			// pass
																			// in
		return rt;
	}

	/**
	 * query newest login with sessionId.to see if the session is logged in.
	 * <p>
	 * Nov 29, 2012
	 */
	protected String getSessionId(HandleContextI hc) {

		ClientGd c = this.getClient(hc);
		return c.getSessionId(true);

	}

	protected String getLocale(HandleContextI hc) {
		ClientGd s = this.getClient(hc);
		return s.getString("locale", true);
	}

	// from header
	protected String getClientId(HandleContextI hc) {
		String rt = hc.getRequest().getHeader(Constants.HK_CLIENT_ID, true);
		return rt;

	}

	protected ClientGd getClient(HandleContextI hc) {
		String sid = this.getClientId(hc);
		return this.clientManager.getEntity(sid);// client from grid.

	}

	//
	// protected String getLoginEmail(HandleContextI hc, boolean force) {
	// Session li = this.getLogin(hc, force);
	//
	// if (li == null) {
	// return null;
	// }
	// return (String) li.getPropertyAsString(Login.PK_EMAIL);
	// }

	protected SessionGd getSession(HandleContextI hc, boolean force) {
		ClientGd c = this.getClient(hc);
		String sid = c.getSessionId(false);
		if (sid == null) {
			if (force) {
				throw new FsException("no session binding to client:"
						+ c.getId());
			}
			return null;
		}

		SessionGd rt = this.sessionManager.getSession(sid);

		if (rt == null && force) {
			throw new FsException("no session found by id:" + sid
					+ " from clientId:" + c.getId());
		}
		return rt;

	}

	protected <T extends NodeWrapper> void processGetNewestListById(
			Class<T> cls, HandleContextI hc) {
		this.processGetNewestListById(cls, hc, "idList", "nodeList");
	}

	protected <T extends NodeWrapper> void processGetNewestListById(
			Class<T> cls, HandleContextI hc, String reqKey, String resKey) {
		RequestI req = hc.getRequest();
		ResponseI res = hc.getResponse();
		List<String> idL = (List<String>) req.getPayload(reqKey);
		List<T> rtL = this.dataService.getNewestListById(cls, idL, true, true);

		res.setPayload(resKey, rtL);
	}

}
