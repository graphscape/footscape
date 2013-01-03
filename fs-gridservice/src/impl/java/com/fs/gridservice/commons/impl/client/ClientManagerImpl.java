/**
 *  Dec 25, 2012
 */
package com.fs.gridservice.commons.impl.client;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.client.ClientManagerI;
import com.fs.gridservice.commons.api.data.ClientGd;
import com.fs.gridservice.commons.api.support.EntityGdManagerSupport;

/**
 * @author wuzhen
 * 
 */
public class ClientManagerImpl extends EntityGdManagerSupport<ClientGd>
		implements ClientManagerI {

	/**
	 * @param name
	 * @param wcls
	 */
	public ClientManagerImpl() {
		super("client", ClientGd.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.client.ClientManagerI#createClient(java
	 * .lang.String)
	 */
	@Override
	public ClientGd createClient(String tid) {
		return this.createClient(tid, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.client.ClientManagerI#createClient(java
	 * .lang.String, com.fs.commons.api.value.PropertiesI)
	 */
	@Override
	public ClientGd createClient(String tid, PropertiesI<Object> pts) {
		ClientGd rt = new ClientGd(pts);
		rt.setProperty(ClientGd.TERMIANAlID, tid);
		this.addEntity(rt);
		return rt;
	}

	/* (non-Javadoc)
	 * @see com.fs.gridservice.commons.api.client.ClientManagerI#bindingSession(java.lang.String, java.lang.String)
	 */
	@Override
	public void bindingSession(String cid, String sid) {
		ClientGd c = this.getEntity(cid);
		c.setProperty(ClientGd.SESSIONID, sid);
		this.addEntity(c);//
	}

	/*
	 *Jan 2, 2013
	 */
	@Override
	public void unBindingSession(String cid) {
		// 
		this.bindingSession(cid, null);
	}

}
