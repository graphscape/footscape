/**
 * Jul 28, 2012
 */
package com.fs.uiserver.impl.handler.support;

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
import com.fs.dataservice.api.expapp.wrapper.Activity;
import com.fs.dataservice.api.expapp.wrapper.Login;
import com.fs.dataservice.api.expapp.wrapper.Session;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.ValidatorHandlerSupport;
import com.fs.uiserver.Constants;

/**
 * @author wu
 * 
 */
public class UiHandlerSupport extends ValidatorHandlerSupport {

	protected DataServiceI dataService;
	protected JexlEngineI jexl;

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		this.dataService = ac.getContainer().find(DataServiceI.class, true);

		jexl = this.container.find(JexlEngineI.class, true);

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
	protected String getLoginId(HandleContextI hc) {

		String sid = this.getSessionId(hc);

		Login li = this.dataService.getNewest(Login.class, Login.PK_SESSION_ID,
				sid, false);

		if (li == null) {
			throw new FsException("session" + sid + " not login");
		}
		String rt = li.getId();

		return rt;

	}

	protected String getLocale(HandleContextI hc) {
		Session s = this.getSession(hc);
		return s.getLocale();
	}

	protected String getSessionId(HandleContextI hc) {
		String rt = hc.getRequest().getHeader(Constants.HK_SESSION_ID, true);
		return rt;

	}

	protected Session getSession(HandleContextI hc) {
		String sid = this.getSessionId(hc);

		return this.dataService.getNewestById(Session.class, sid, true);

	}

	//
	// protected String getLoginEmail(HandleContextI hc, boolean force) {
	// Login li = this.getLogin(hc, force);
	//
	// if (li == null) {
	// return null;
	// }
	// return (String) li.getPropertyAsString(Login.PK_EMAIL);
	// }

	protected Login getLogin(HandleContextI hc, boolean force) {
		String uid = this.getLoginId(hc);
		Login rt = this.dataService.getNewestById(Login.class, uid, force);
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
