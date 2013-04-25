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
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.operations.NodeSearchOperationI;
import com.fs.dataservice.api.core.result.NodeSearchResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.ExpectorDsFacadeI;
import com.fs.expector.gridservice.api.OnlineNotifyServiceI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.client.ClientManagerI;
import com.fs.gridservice.commons.api.data.ClientGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.support.TerminalMsgReseiveEventHandler;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

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

	protected OnlineNotifyServiceI onlineNotifyService;

	protected ExpectorDsFacadeI efacade;

	protected String prefix = "payloads.property['_default'].payloads.property['_message'].payloads.property";

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		DataServiceFactoryI dsf = top.find(DataServiceFactoryI.class, true);
		this.dataService = dsf.getDataService();//

		jexl = this.top.find(JexlEngineI.class, true);

		this.facade = this.top.find(GridFacadeI.class, true);

		this.clientManager = this.facade.getEntityManager(ClientManagerI.class);
		this.terminalManager = this.facade.getEntityManager(TerminalManagerI.class);
		this.sessionManager = this.facade.getSessionManager();
		this.onlineNotifyService = top.find(OnlineNotifyServiceI.class, true);
		this.efacade = this.top.find(ExpectorDsFacadeI.class, true);
	}

	protected <T extends NodeWrapper> T createNode(String expPrefix, MessageContext hc, Class<T> cls) {
		return this.createNode(expPrefix, hc, cls, new HashMap<String, String>());
	}

	protected <T extends NodeWrapper> T createNode(String expPrefix, MessageContext hc, Class<T> cls,
			Map<String, String> payloadKeyMap) {
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

	protected <T extends NodeWrapper> NodeSearchResultI findNodeList(String expPrefix, MessageContext hc,
			NodeType type, Class<T> cls, Map<String, String> payloadKeyMap) {
		return this.findNodeList(expPrefix, hc, type, cls, payloadKeyMap, true);//
	}

	protected <T extends NodeWrapper> NodeSearchResultI<T> findNodeList(String expPrefix, MessageContext hc,
			NodeType type, Class<T> cls, Map<String, String> payloadKeyMap, boolean sortTimeStampDesc) {
		NodeSearchOperationI<T> dbo = this.dataService.prepareOperation(NodeSearchOperationI.class);
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

		NodeSearchResultI<T> rt = dbo.execute().getResult().assertNoError();// TODO
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
	protected String getSessionId(TerminalMsgEW hc) {

		ClientGd c = this.getClient(hc);
		return c.getSessionId(true);

	}

	protected String getLocale(TerminalMsgEW ew) {
		ClientGd s = this.getClient(ew);
		return s.getString("locale", true);
	}

	protected ClientGd getClient(TerminalMsgEW ew) {
		String cid = ew.getClientId();
		if (cid == null) {
			return null;
		}
		return this.clientManager.getEntity(cid);// client from grid.

	}

	//
	// protected String getLoginEmail(MessageContext hc, boolean force) {
	// Session li = this.getLogin(hc, force);
	//
	// if (li == null) {
	// return null;
	// }
	// return (String) li.getPropertyAsString(Login.PK_EMAIL);
	// }

	protected SessionGd getSession(TerminalMsgEW hc, boolean force) {
		ClientGd c = this.getClient(hc);
		String sid = c.getSessionId(false);
		if (sid == null) {
			if (force) {
				throw new FsException("no session binding to client:" + c.getId());
			}
			return null;
		}

		SessionGd rt = this.sessionManager.getSession(sid);

		if (rt == null && force) {
			throw new FsException("no session found by id:" + sid + " from clientId:" + c.getId());
		}
		return rt;

	}

	/**
	 * TODO move to converter. Dec 30, 2012
	 */
	protected String getAccountId(TerminalMsgReceiveEW ew, boolean force) {
		//
		SessionGd s = this.getSession(ew, false);
		if (s == null) {
			if (force) {
				throw new FsException("no session binding with terminal:" + ew.getTerminalId());
			} else {
				return null;
			}
		}
		return s.getAccountId();
	}

	protected boolean isAccount(TerminalMsgReceiveEW ew, String aid) {
		String accId = this.getAccountId(ew, true);
		return accId.equals(aid);
	}

	protected boolean assertAccout(TerminalMsgReceiveEW ew, String aid, ErrorInfos ies) {
		boolean rt = this.isAccount(ew, aid);

		if (!rt) {
			ies.addError("account is not match");
		}
		return rt;
	}

	protected <T extends NodeWrapper> void processGetNewestListById(Class<T> cls, MessageContext hc) {
		this.processGetNewestListById(cls, hc, "idList", "nodeList");
	}

	protected <T extends NodeWrapper> void processGetNewestListById(Class<T> cls, MessageContext hc,
			String reqKey, String resKey) {
		MessageI req = hc.getRequest();
		ResponseI res = hc.getResponse();
		List<String> idL = (List<String>) req.getPayload(reqKey);
		List<T> rtL = this.dataService.getNewestListById(cls, idL, true, true);

		res.setPayload(resKey, rtL);
	}

}
